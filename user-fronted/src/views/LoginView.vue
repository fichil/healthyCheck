<template>
    <div class="login-container">
      <el-form class="login-form" @submit.native.prevent="doLogin">
        <div>
          <h1>用户登陆</h1>
        </div>
        <el-form-item :error="accountError">
          <el-input v-model="idOrPhone" placeholder="请输入手机号或身份证号" id="idOrPhone"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            type="password"
            placeholder="请输入密码"
            v-model="password"
            show-password
            id="password"
          ></el-input>
        </el-form-item>
  
        <el-form-item>
          <el-input v-model="code" placeholder="请输入验证码" id="captcha"></el-input>
        </el-form-item>
        <div class="demo-image">
          <img :src="captchaUrl" @click="refreshCaptcha" alt="验证码" />
        </div>
        <el-form-item class="login-button">
          <el-button type="primary"  @click="doLogin" id="login">登陆</el-button>
          <el-button type="success" @click="doRegister" id="register">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  
  <script>

import { setToken } from "@/utils/authToken";
  export default {
    data() {
      return {
        idOrPhone: "", // 合并idNumber和phone为account字段
        password: "",
        code: "",
        captchaUrl: "/api/captcha",
        accountError: "", // 用于显示账号输入错误信息
      };
    },
    methods: {
      refreshCaptcha() {
        this.captchaUrl = "/api/captcha?time=" + new Date().getTime();
      },
      validateAccount() {
        // 账号的校验逻辑
        const phoneRegex = /^[1][3-9]\d{9}$/; // 简单的手机号正则
        const idNumberRegex = /^[1-9]\d{5}(18|19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\dXx]$/; // 身份证号正则
        if (!this.idOrPhone) {
          this.accountError = "账号不能为空";
          return false;
        } else if (!phoneRegex.test(this.idOrPhone) && !idNumberRegex.test(this.idOrPhone)) {
          this.accountError = "请输入有效的手机号或身份证号";
          return false;
        }
        this.accountError = "";
        return true;
      },
      doLogin() {
        if (!this.validateAccount()) {
          return;
        }
        this.$axios
          .get("/user/login", {
            params:{
                idOrPhone: this.idOrPhone, 
                password: this.password,
                code: this.code,
            }

          })
          .then((res) => {
            if (res.code == 1) {
              console.log(res);
              
              setToken(res.data);
              this.$alert("登陆成功", "温馨提示", {
                confirmButtonText: "确定",
                callback: (action) => {
                  this.$message({
                    type: "info",
                    message: `action: ${action}`,
                  });
                  this.$store.dispatch("getUserInfo");
                  this.$router.push("/index"); // 在用户点击确定后跳转
                },
              });
            } else if (res.code == -1) {
              this.$alert("账号或密码错误", "温馨提示", {
                confirmButtonText: "确定",
                callback: (action) => {
                  this.$message({
                    type: "info",
                    message: `action: ${action}`,
                  });
                },
              });
            } else if (res.code == 0) {
              this.$alert("验证码错误", "温馨提示", {
                confirmButtonText: "确定",
                callback: (action) => {
                  this.$message({
                    type: "info",
                    message: `action: ${action}`,
                  });
                },
              });
            }
          });
      },
      doRegister(){
        this.$router.push("/register");
      },
    },
    created() {},
  };
  </script>
  
  <style scoped>
  html,
  body,
  #app {
    height: 100%;
    margin: 0;
  }
  
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f0f2f5;
  }
  
  .login-form {
    width: 300px;
    padding: 20px;
    background-color: white;
    box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
  }
  
  .el-form-item {
    margin-bottom: 20px;
  }
  
  .demo-image {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: -10px;
  }
  
  .demo-image img {
    cursor: pointer;
    margin-right: 70px;
  }
  
  .login-button {
    text-align: center;
    margin-top: 20px;
  }
  </style>