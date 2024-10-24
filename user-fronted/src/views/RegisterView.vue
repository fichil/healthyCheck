<template>
    <div class="register-container">
      <el-form
        :model="registerForm"
        ref="registerForm"
        label-width="100px"
        class="register-form"
        @submit.native.prevent="submitForm"
      >
        <div>
          <h1>用户注册</h1>
        </div>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="registerForm.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="idNumber">
          <el-input v-model="registerForm.idNumber" placeholder="请输入身份证号"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="registerForm.name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input
            type="password"
            v-model="registerForm.pass"
            placeholder="请输入密码"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input
            type="password"
            v-model="registerForm.checkPass"
            placeholder="确认密码"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input
            v-model.number="registerForm.age"
            placeholder="请输入年龄"
          ></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="registerForm.gender" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="register-button">
          <el-button type="primary" @click="goLogin">登陆</el-button>
          <el-button type="success" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        registerForm: {
          phone: "",
          idNumber: "",
          name: "",
          pass: "",
          checkPass: "",
          age: "",
          gender: "",
        },
        rules: {
          phone: [
            { required: true, message: "手机号不能为空", trigger: "blur" },
            { pattern: /^[1][3-9]\d{9}$/, message: "请输入有效的手机号", trigger: "blur" }
          ],
          idNumber: [
            { required: true, message: "身份证号不能为空", trigger: "blur" },
            { pattern: /^[1-9]\d{5}(18|19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\dXx]$/, message: "请输入有效的身份证号", trigger: "blur" }
          ],
          pass: [
            { required: true, message: "请输入密码", trigger: "blur" },
          ],
          checkPass: [
            { required: true, message: "请再次输入密码", trigger: "blur" },
            { validator: (rule, value, callback) => {
                if (value !== this.registerForm.pass) {
                  callback(new Error("两次输入密码不一致"));
                } else {
                  callback();
                }
              }, trigger: "blur" }
          ],
          age: [
            { required: true, message: "年龄不能为空", trigger: "blur" },
            { type: "number", message: "年龄必须为数字", trigger: "blur" },
          ],
        }
      };
    },
    methods: {
      goLogin() {
        this.$router.push("/login");
      },
      submitForm() {
        this.$refs.registerForm.validate((valid) => {
          if (valid) {
            this.registerUser();
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      },
      registerUser() {
        this.$axios
          .post("/user/register", {
            phone: this.registerForm.phone,
            idNumber: this.registerForm.idNumber,
            name: this.registerForm.name,
            password: this.registerForm.pass,
            age: this.registerForm.age,
            gender: this.registerForm.gender,
          })
          .then((res) => {
            if (res.code === 1) {
              this.$alert("注册成功", "温馨提示", {
                confirmButtonText: "确定",
                callback: (action) => {
                    console.log(action);
                    
                  this.$router.push("/login"); // 注册成功后跳转到登录页面
                },
              });
            } else {
              this.$alert(res.message || "注册失败", "温馨提示", {
                confirmButtonText: "确定",
              });
            }
          })
          .catch((error) => {
            console.error(error);
          });
      },
      resetForm() {
        this.$refs.registerForm.resetFields();
      },
    },
  };
  </script>
  
  <style scoped>
  html,
  body,
  #app {
    height: 100%;
    margin: 0;
  }
  
  .register-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f0f2f5;
  }
  
  .register-form {
    width: 400px;
    padding: 20px;
    background-color: white;
    box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
  }
  
  .el-form-item {
    margin-bottom: 20px;
  }
  
  .register-button {
    text-align: center;
    margin-top: 20px;
  }
  </style>