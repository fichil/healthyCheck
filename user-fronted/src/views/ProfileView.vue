<template>
    <div class="profile-container">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>个人信息</span>
          <el-button style="float: left; padding: 3px 0" type="text" @click="goHome">主页</el-button>
          <el-button style="float: right; padding: 3px 0" type="text" @click="editProfile">编辑</el-button>
        </div>
        <el-row>
          <el-col :span="12">
            <el-form :model="user" label-width="80px" class="profile-form">
              <el-form-item label="姓名">
                <span>{{ user.name }}</span>
              </el-form-item>
              <el-form-item label="手机号">
                <span>{{ user.phone }}</span>
              </el-form-item>
              <el-form-item label="身份证号">
                <span>{{ user.idNumber }}</span>
              </el-form-item>
              <el-form-item label="性别">
                <span>{{ user.gender }}</span>
              </el-form-item>
              <el-form-item label="年龄">
                <span>{{ user.age }}</span>
              </el-form-item>
              <el-form-item label="余额">
                <div class="balance-container">
                  <span>{{ user.balance }}</span>
                  <el-button type="primary" size="small" @click="recharge" class="recharge-button">充值</el-button>
                </div>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </el-card>
  
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>体检信息</span>
        </div>

        <!-- 订单列表展示 -->
      <el-collapse v-model="activeOrders">
        <el-collapse-item 
          v-for="order in orders" 
          :key="order.orderNumber" 
          :title="`订单号: ${order.orderNumber} (¥${order.totalAmount})`">
          
          <el-button type="primary" size="small" @click="viewSummary(order.orderNumber)">查看体检小结</el-button>
          <el-button type="primary" size="small" @click="viewOverallSummary(order.orderNumber)">查看体检总结</el-button>
          <el-button type="primary" size="small" @click="viewReport(order.orderNumber)">查看体检报告</el-button>
          
        </el-collapse-item>
      </el-collapse>

      </el-card>
    </div>
  </template>
  
  <script>
  import { setToken } from "@/utils/authToken";
  export default {
    computed: {},
    data() {
      return {
        user: {},
        orders: [], // 用于存储订单列表
        activeOrders: [] // 用于控制展开的订单
      };
    },
    
    methods: {
      getUser(){
          this.user = this.$store.state.user;
      },
      editProfile() {
        this.$message("编辑功能开发中...");
      },
      goHome(){
        this.$router.push('/index')
      },
      recharge() {
        // 使用支付宝沙箱支付
        // this.$message("跳转到支付宝沙箱支付页面...");
        this.$prompt('请输入冲值金额', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /^(?:[1-9]\d{0,5}|1000000)$/,
          inputErrorMessage: '充值金额在0-100万之间'
        }).then(({ value }) => {
          this.$message({
            type: 'success',
            message: '前往支付冲值: ' + value+"元"
          });
          this.confirmRecharge(value);
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });       
        });
      },
      confirmRecharge(value) {
        // 生成唯一的订单号 (可以根据当前时间戳 + 随机数生成)
        const outTradeNo = 'ORDER_' + new Date().getTime() + Math.floor(Math.random() * 10000);
        
        // 设置交易主题和描述
        const subject = this.user.userId+':giveMeMoney';
        const body = 'userRecharge : ${value}';

        this.$axios
          .post("/pay/alipay", {
            out_trade_no: outTradeNo, // 唯一订单号
            subject: subject, // 交易主题
            total_amount: value, // 充值金额
            body: body, // 交易描述
          })
          .then((res) => {
            console.log(res);
            
            if (res.code === 1) {

              //删除页面内容
              const divForm = document.getElementsByTagName('div')
              if(divForm.length){
                document.body.removeChild(divForm[0])
              }
              const div = document.createElement('div')
              div.innerHTML = res.data
              // console.log(div);
              document.body.appendChild(div)
              document.forms[0].setAttribute('target','_blank');
              // document.forms[0].setAttribute('enctype', 'application/x-www-form-urlencoded');
              document.forms[0].submit()
              // window.close();
            } else {
              // 如果充值失败
              this.$alert(res.message || "充值失败", "温馨提示", {
                confirmButtonText: "确定",
              });
            }
          })
          .catch((error) => {
            // 捕获并处理请求错误
            console.error(error);
              this.$alert("请求失败，请稍后再试", "温馨提示", {
                confirmButtonText: "确定",
              });
            });
      },
      autoLogin() {
        const phone = this.user.phone;
        if (phone) {
          this.$axios
            .get('/user/login', {
              params:
              {             
                idOrPhone : phone,
                password: this.user.password,
              }
            })
            .then((res) => {
              if (res.code === 1) {
                setToken(res.data);
                this.$message({
                  type: 'success',
                  message: '自动登录成功',
                });
              } else {
                this.$message({
                  type: 'error',
                  message: '自动登录失败',
                });
              }
            })
            .catch((error) => {
              console.error(error);
              this.$message({
                type: 'error',
                message: '登录请求失败，请稍后再试',
              });
            });
        } else {
          this.$message({
            type: 'error',
            message: '用户手机号未找到，无法自动登录',
          });
        }
      },
      getOrders() {
        this.$axios.get('/user/getOrdersByUserId', {
          params: { userId: this.user.userId }
        })
        .then(res => {
          console.log(res);
          
          if (res.code === 1) {
            this.orders = res.data;
          } else {
            this.$message.error("获取订单列表失败");
          }
        })
        .catch(error => {
          console.error(error);
          this.$message.error("请求失败，请稍后再试");
        });
      },
      viewSummary(orderNumber) {
        this.$message(`查看体检小结: ${orderNumber}`);
        // 可以在这里添加逻辑来获取并展示体检小结
      },
      viewOverallSummary(orderNumber) {
        this.$message(`查看体检总结: ${orderNumber}`);
        // 可以在这里添加逻辑来获取并展示体检总结
      },
      viewReport(orderNumber) {
        this.$message(`查看体检报告: ${orderNumber}`);
        // 可以在这里添加逻辑来获取并展示体检报告
      }



    },
    created() {

    this.getUser();
    this.autoLogin();
    this.getOrders();

  }
    
  }
  
  </script>
  
  <style scoped>
  .profile-container {
    padding: 20px;
    display: flex;
    flex-direction: column;
    gap: 20px;
  }
  
  .box-card {
    width: 100%;
  }
  
  .profile-form .el-form-item {
    margin-bottom: 20px;
  }
  .balance-container {
    display: flex;
    align-items: center;
  }
  
  .recharge-button {
    margin-left: 270px; /* 调整间距的大小 */
  }
  </style>