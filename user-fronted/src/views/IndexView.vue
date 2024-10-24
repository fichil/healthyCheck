<template>
  <el-container class="home-page">
    <el-header class="navbar">
      <div class="logo">
        <h1>医院主页</h1>
      </div>
      <div v-if="!isLoggedIn" class="login-buttons">
        <el-button type="primary" @click="userLogin">用户登录</el-button>
      </div>
      <div v-else>
        <span @click="goToProfile" class="user-name">欢迎：{{ userName }}</span>
        <el-button type="danger" @click="logout">退出</el-button>
      </div>
    </el-header>
    <el-main class="content">
      <h2>欢迎来到我们的医院</h2>
      <p>我们提供优质的医疗服务和关怀。</p>

      <div class="package-section">
        <el-row :gutter="20">
          <el-col :span="8" v-for="packag in packages" :key="packag.id">
            <el-card :body-style="{ padding: '20px' }">

              <img :src="require('@/assets'+packag.img)" class="package-image" alt="套餐图片">
              <div class="package-content">
                <h4>{{ packag.packageName }}</h4>

                <div class="package-price">价格: ¥{{ packag.price }}</div>
                <el-button type="primary" @click="buyPackage(packag)">立即购买</el-button>
                <el-popover
                placement="right"
                width="400"
                trigger="click"
                popper-class="custom-popover"
              >
                <el-table :data="gridData" height="200">
                  <el-table-column width="150" property="itemName" label="项目名"></el-table-column>
                  <el-table-column width="100" property="price" label="价格"></el-table-column>
                </el-table>
                <el-button slot="reference" type="primary" @click="showItems(packag)" style="margin-left: 3%;">套餐详情</el-button>
              </el-popover>
                <!-- <el-button type="primary" @click="showItems(packag)">套餐详情</el-button> -->
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>

    </el-main>
  </el-container>
</template>

<script>
import { getToken } from '@/utils/authToken'; // 用于获取token的工具函数


export default {
  name: "IndexView",
  data() {
    return {
      packages: [] ,// 用于存储从后端获取的套餐数据
      gridData:[],
    };
  },
  computed: {
    isLoggedIn() {
      return !!this.$store.state.user && this.$store.state.user.userId; // 检查用户是否已登录
    },
    userName() {
      return this.$store.state.user.name ? this.$store.state.user.name : ''; // 获取用户名称
    }
  },
  methods: {
    showItems(packeg){
      this.gridData = packeg.items;
      console.log(this.gridData);
      
    },
    userLogin() {
      this.$router.push('/login');
    },
    logout() {
      this.$store.dispatch('logoutUser');
    },
    goToProfile() {
      this.$router.push('/profile');
    },
    getPackageList() {
      // 通过axios从后端获取套餐数据
      this.$axios.get('/packages/getAllPackages').then(response => {
        console.log(response);
        
        this.packages = response.data;
      }).catch(error => {
        console.error("获取套餐数据失败:", error);
      });
    },
    buyPackage(packag) {
      if (!this.isLoggedIn) {
        this.$message.error('请先登录再进行购买');
        return;
      }

      if (this.$store.state.user.balance >= packag.price) {
        var order = {
          orderNumber : 'ORDER_' + new Date().getTime() + Math.floor(Math.random() * 10000),
          userId : this.$store.state.user.userId,
          totalAmount : packag.price,
          orderDetail:{
            type:"套餐",
            packageId:packag.packageId,
          }
        }
        this.$axios
          .post("/user/buyPackage", order)
          .then((res) => {
            if (res.code === 1) { // 假设返回code为1表示成功
              this.$message.success('购买成功');
              // 可以考虑跳转到订单详情或更新余额显示
              this.$store.dispatch('getUserInfo'); // 更新用户信息（包括余额）
            } else {
              this.$message.error('购买失败: ' + res.message);
            }
          })
          .catch((error) => {
            console.error(error);
            this.$message.error('请求失败，请稍后再试');
          });
      } else {
        this.$message.error('余额不足，请充值后再试');
      }
}
  },
  created() {
    console.log("进入判断是否存在user");
    
    if (!this.$store.state.user.nserId) {
      console.log("不存在user，进入判断是否存在token");
      
      if (getToken()) {
        console.log("存在token");
        
        // 如果有token，则发送请求解析token
        this.$store.dispatch("getUserInfo");
      }
    }
    this.getPackageList();
  }
};
</script>

<style scoped>

.custom-popover {
  max-height: 300px; /* 设置弹出框的最大高度 */
  overflow-y: auto;  /* 启用垂直滚动 */
}
.home-page {
  font-family: 'Arial', sans-serif;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  background-color: #f5f5f5;
  min-height: 100vh;
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #2c3e50;
  padding: 10px 20px;
  color: #ecf0f1;
}

.logo h1 {
  margin: 0;
  font-size: 24px;
}

.login-buttons {
  display: flex;
  gap: 10px;
}

.user-name {
  cursor: pointer;
  margin-right: 10px;
}

.content {
  text-align: center;
  padding: 50px 20px;
}

.content h2 {
  font-size: 28px;
  color: #34495e;
}

.content p {
  font-size: 18px;
  color: #7f8c8d;
}
.package-section {
  margin-top: 40px;
}

.package-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.package-content {
  text-align: left;
  padding: 10px 0;
}

.package-price {
  font-weight: bold;
  color: #e67e22;
  margin: 10px 0;
}
</style>