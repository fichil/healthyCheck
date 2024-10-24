package com.cykj.service.impl;

import com.cykj.dto.ResponseDto;
import com.cykj.mapper.*;
import com.cykj.pojo.*;
import com.cykj.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/20.
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private PackageMapper packageMapper;
    @Autowired
    private ResultMapper resultMapper;


    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private DetailItemMapper detailItemMapper;


    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public ResponseDto updateUserById(User user) {
        int i = userMapper.updateUserById(user);
        if (i > 0) {
            return ResponseDto.success("修改用户信息成功",null);
        } else {
            return ResponseDto.fail("修改用户信息失败");
        }
    }

    /**
     * 根据用户姓名模糊搜索套餐
     * @param userName
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @Override
    public ResponseDto getUserByNameLike(String userName, int pageCurrent, int pageSize) {
        //启动分页拦截器
        Page<Object> pages = PageHelper.startPage(pageCurrent, pageSize);
        List<User> packages = userMapper.getUserByNameLike(userName);
        PageInfo<Object> pageInfo = new PageInfo<>(pages);

        return ResponseDto.success("根据套餐名称模糊搜索成功", pageInfo);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public ResponseDto addUser(User user) {
        int i = userMapper.addUser(user);
        if (i > 0) {
            return ResponseDto.success("添加用户成功",null);
        } else {
            return ResponseDto.fail("添加用户失败");
        }
    }

    /**
     * 导入Excel中的用户数据
     * @param file
     */
    @Override
    public int importUsersFromExcel(MultipartFile file) {
        List<User> users = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        int count = 0;

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // 获取Excel文件中的第一个Sheet
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 从第二行开始读取
                Row row = sheet.getRow(i);

                if (row != null) {
                    User user = new User();
                    user.setName(getCellValue(row.getCell(0)));
                    System.out.println(user.getName());
                    user.setPhone(formatter.formatCellValue(row.getCell(1)));
                    System.out.println("手机号是"+user.getPhone());
                    user.setIdNumber(getCellValue(row.getCell(2)));
                    System.out.println(user.getIdNumber());
                    user.setGender(getCellValue(row.getCell(3)));
                    System.out.println(user.getGender());
                    user.setAge(Integer.parseInt(getCellValue(row.getCell(4))));
                    System.out.println(user.getAge());
                    user.setPassword(getCellValue(row.getCell(5)));
                    System.out.println(user.getPassword());
                    users.add(user);
                }
            }

            // 保存用户列表到数据库
            for (User user : users) {
                // 调用保存用户的方法
                System.out.println("正在注册"+user.getName());
                count = userMapper.addUser(user);
                System.out.println("共注册人数："+count);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    /**
     * 管理员给用户开单
     * @param userId
     * @param packageId
     * @return
     */
    @Override
    public ResponseDto purchase(int userId, int packageId) {
        //生成订单
        Orders order = new Orders();
        String orderNumber = "ORDER_" + new Date().getTime() + (long)Math.random() * 10000;
        order.setOrderNumber(orderNumber);
        order.setUserId(userId);
        double totalAmount = packageMapper.getPackageById(packageId).getPrice();
        order.setTotalAmount(totalAmount);
        order.setPaymentStatus("已支付");
        int createOrderNumber = orderMapper.createOrder(order);
        //用户扣款
        User user = new User();
        user.setUserId(userId);
        user.setBalance(userMapper.getUserById(userId).getBalance()-totalAmount);
        int setUserBalanceNumber = userMapper.updateUserBalance(user);
        //生成订单详情
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderNumber(orderNumber);
        orderDetail.setPackageId(packageId);
        orderDetail.setType("套餐");
        int creatOrderDetailNumber = orderDetailMapper.createOrderDetail(orderDetail);
        boolean resultForm = createResultForm(orderNumber);
        if (resultForm && createOrderNumber > 0 && setUserBalanceNumber > 0 && creatOrderDetailNumber > 0) {
            return ResponseDto.success("success", null);
        } else {
            return ResponseDto.fail("error");
        }
    }

    /**
     * 创建3个结果表
     * @param orderNumber
     * @return
     */
    private boolean createResultForm(String orderNumber){
        //通过订单id查到订单号
        Long orderId = orderMapper.getOrderIdByOrderNumber(orderNumber);
        //插入总结检查单
        Summary summary = new Summary();
        summary.setOrderId(orderId);
        int summaryCount = resultMapper.insertSummary(summary);
        //插入项目检查单
        ItemSummary itemSummary = new ItemSummary();
        itemSummary.setOrderId(orderId);
        int itemSummaryCount = 0;
        //根据订单订单号查套餐
        Packages packages = packageMapper.getPackageByOrderNumber(orderNumber);
        //通过套餐id查询项目
        List<Item> itemsByPackageId = itemMapper.getItemsByPackageId(packages.getPackageId());
        //循环所有项目插入羡慕检查单
        for (Item item : itemsByPackageId) {
            itemSummary.setItemId(item.getItemId());
            resultMapper.insertItemSummary(itemSummary);
            itemSummaryCount++;
        }
        //插入细项检查单
        DetailResult detailResult = new DetailResult();
        detailResult.setOrderId(orderId);
        int detailResultCount = 0;
        //遍历套餐中的所有项目，在遍历中每一细项插入细项检查单
        for (Item item : itemsByPackageId) {
            long itemId = item.getItemId();
            List<DetailItem> detailItems = detailItemMapper.getDetailItemsByItemId(itemId);
            for (DetailItem detailItem : detailItems) {
                detailResult.setDetailItemId(detailItem.getDetailItemId());
                resultMapper.insertDetailResult(detailResult);
                detailResultCount++;
            }
        }
        return summaryCount > 0 && itemSummaryCount > 0 && detailResultCount > 0;
    }
}
