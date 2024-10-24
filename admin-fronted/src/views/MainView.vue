<template>
    <div >

        <el-container>
            <el-container>

                <el-aside width="200px">
                    <el-row class="tac">
                        <el-col :span="24">

                            <el-menu
                                v-for="(item,index) in menuData" 
                                :key="item.menuId"
                                default-active="0"
                                class="el-menu-vertical-demo"
                                @open="handleOpen"
                                @close="handleClose"
                                background-color="#545c64"
                                text-color="#fff"
                                active-text-color="#ffd04b">

                                <el-submenu :index="index +''" v-if="item.children.length > 0">
                                    <template slot="title">
                                        <i class="el-icon-location"></i>
                                        <span>{{ item.menuName }}</span>
                                    </template>

                                    <el-menu-item 
                                        :index="index+ '-'+subIndex" 
                                        v-for="(subItem,subIndex) in item.children" 
                                        :key="subItem.menuId" @click="change(subItem)">
                                        {{ subItem.menuName }}
                                    </el-menu-item>

                                </el-submenu> 

                                <el-menu-item :index="index+''" v-else>
                                    <i class="el-icon-menu" @click="change(item)"></i>
                                    <span slot="title">{{ item.menuName }}</span>
                                </el-menu-item>
                                
                            </el-menu>
                          </el-col>
                    </el-row>
                </el-aside>

                <el-container>
                  <el-header>{{head}}</el-header>
                    
                  <el-main>
                    <router-view></router-view>
                  </el-main>

                  <el-footer>
                    <div>
                        欢迎您，{{ $store.state.staff.nickname }}
                    </div>
                  </el-footer>
                </el-container>
              </el-container>
        </el-container>
    </div>
</template>

<script>
    export default{
        data(){
            return{
                menuData:null,
                head:"",
                nowItem:{
                    menuName:"",
                },
            }
        },
        created(){
            this.getMenusByRoleId()
        },
        methods:{
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            },
            getMenusByRoleId(){
                
                this.$axios.get(
                    "/menu/getMenusByRoleId",{
                        params:{
                            roleId:this.$store.state.staff.roleId,
                        }
                    }
        
                ).then(res=>{
                    // console.log(res);
                    this.menuData = res.data;
                    console.log(this.menuData);
                    
                })
            },
            change(item){
                if (this.nowItem.menuName != item.menuName) {
                    this.head=item.menuName;
                    this.$router.push(item.menuRoute);
                    this.nowItem = item;
                }



            }
        }


    }

</script>

<style scoped>
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
    width: aoto;
  }
  
  .el-aside {
    height: 100vh;
    background-color: #545c64;
    color: #333;
    text-align: left;
    line-height: 200px;
  }
  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 120px;
    height: 70vh;
    width: auto;
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }
  
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
</style>