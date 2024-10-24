package com.cykj.controller;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.User;

import com.cykj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;


/**
 * Created by zhangzhenhua on 2024/8/20.
 * 用户控制类
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;



    /**
     * 添加用户
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("addUser")
    public ResponseDto addItem(@RequestBody User user){
        return userService.addUser(user);
    }


    /**
     * 根据用户姓名模糊搜索
     * @param userName
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("getUserByNameLike")
    public ResponseDto getUserByNameLike(String userName,int pageCurrent, int pageSize){
//        System.out.println("模糊搜索："+userName);
        return userService.getUserByNameLike(userName, pageCurrent, pageSize);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("updateUserById")
    public ResponseDto updateUserById(@RequestBody User user){
        return userService.updateUserById(user);
    }

    /**
     * 下载批量导入用的模版
     * @return
     */
    @GetMapping("/downloadTemplate")
    @ResponseBody
    public ResponseEntity<Resource> downloadTemplate() {
        Resource resource = new ClassPathResource("Template/template.xlsx");
        if (resource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=template.xlsx")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    /**
     * 上传批量导入用户
     * @param file
     * @return
     */
    @PostMapping("/importUsers")
    public ResponseEntity<String> importUsers(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("未接收到文件");
        }

        try {
            // 调用服务层处理文件的逻辑
            int i = userService.importUsersFromExcel(file);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件上传失败：" + e.getMessage());
        }
    }


    /**
     * 管理员给用户开单
     * @param userId
     * @param packageId
     * @return
     */
    @ResponseBody
    @RequestMapping("purchase")
    public ResponseDto purchase(int userId,int packageId){

        return userService.purchase(userId, packageId);
    }
}
