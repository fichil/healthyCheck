<template>
    
    <div>
      <el-table
      :data="tableData"
      style="width: 100%"
      height="55vh">
      <el-table-column
        fixed
        prop="staff_id"
        label="ID"  
        width="150">
      </el-table-column>
      <el-table-column
        prop="account"
        label="账号"
        width="150">
      </el-table-column>
      <el-table-column
        prop="nickname"
        label="昵称"
        width="150">
      </el-table-column>
      <el-table-column
        prop="role_id"
        label="是否删除"
        width="150">
      </el-table-column>
      <el-table-column
        prop="department_id"
        label="角色"
        width="150">
      </el-table-column>
      <el-table-column
          prop="ststus"
          label="状态"
          width="150">
      </el-table-column>
  
      <el-pagination
          background
          layout="prev, pager, next"
          :total="1000"
        >
      </el-pagination>
    </el-table>
  
    <el-pagination
    background
    layout="prev, pager, next"
    :total="total"
    :page-size="pageSize"
    :current-page.sync="pageCurrent"
    @current-change="handlePageChange"
    hight="auto">
  </el-pagination>
  
    </div>
  
  
    </template>
  
    <script>
    export default {
      data() {
        return {
          tableData: [],
          total: 0,
          pageSize: 3,
          pageCurrent: 1
        }
      },
      methods:{
          getAdmins(){
              this.$axios.post(
              "/admin/getAdmins",
              {
                  pageCurrent: this.pageCurrent,
                  pageSize: this.pageSize
              }
          ).then(res=>{
              console.log(res);
              this.tableData = res.data.list;
              this.total = res.data.total;
              console.log(this.tableData);
          })
          },
          handlePageChange(page) {
              this.pageCurrent = page;
              this.getAdmins(); // 重新获取数据
          }
      },
      created(){
          this.getAdmins();
          
      }
    }
  </script>