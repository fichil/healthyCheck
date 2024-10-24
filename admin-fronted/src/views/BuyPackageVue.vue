<template>
    
    <div>
        <!-- 搜索和按钮 -->
        <el-row :gutter="20" style="margin-bottom: 0px;">
            <el-col :span="8">
                <el-input
                    placeholder="请输入用户姓名进行搜索"
                    v-model="searchName"
                    clearable>
                </el-input>
            </el-col>
            <el-col :span="4">
                <el-button type="primary" @click="getbyNameLike()">搜索</el-button>
                <el-button @click="clearSearch()">清除</el-button>
            </el-col>
        </el-row>

        <!-- 用户列表 -->
        <el-table
            :data="tableData"
            style="width: 100%"
            height="55vh">
            <el-table-column
                fixed
                prop="userId"
                label="ID"  
                width="50">
            </el-table-column>
            <el-table-column
                prop="name"
                label="姓名"
                width="100">
            </el-table-column>

            <el-table-column
                prop="phone"
                label="联系方式"
                width="150">
            </el-table-column>

            <el-table-column
                prop="idNumber"
                label="身份证号码"
                width="300">
            </el-table-column>

            <el-table-column
                prop="gender"
                label="性别"
                width="100">
            </el-table-column>

            <el-table-column
                prop="age"
                label="年龄"
                width="100">
            </el-table-column>

            <el-table-column
                prop="balance"
                label="余额"
                width="100">
            </el-table-column>

            <el-table-column
                prop="isDeleted"
                label="是否删除"
                width="50">
            </el-table-column>

            <el-table-column
                label="操作"
                width="450">
                <template slot-scope="scope">
                    <el-button type="primary" @click="buyPackage(scope.row)">购买套餐</el-button>
                </template>
            </el-table-column>
    
        </el-table>
        <!-- 分页 -->
        <el-pagination
            background
            layout="prev, pager, next"
            :total="total"
            :page-size="pageSize"
            :current-page.sync="pageCurrent"
            @current-change="handlePageChange"
            hight="auto">
        </el-pagination>

        <el-dialog 
            title="选择套餐"
            :visible.sync="packageDialogVisible">
            <el-row>
                <el-col :span="24">
                    <el-radio-group v-model="selectedPackage" style="display: block;">
                        <el-radio 
                            v-for="pkg in availablePackages" 
                            :key="pkg.id" 
                            :label="pkg" 
                            style="display: block; text-align: left; margin-bottom: 50px; ">
                            {{ pkg.packageName }} : {{ pkg.price }}元
                        </el-radio>
                    </el-radio-group>
                </el-col>
            </el-row>
            <div slot="footer" class="dialog-footer">
                <el-button @click="packageDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="confirmPackagePurchase">确 定</el-button>
            </div>
        </el-dialog>
    
    
    </div>
</template>
  
<script>
    export default {
        data() {
        return {
            form: {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: ''
            },
            formLabelWidth: '120px',
            tableData: [],
            total: 0,
            pageSize: 4,
            pageCurrent: 1,
            nowRow : {},
            searchName: '',
            packageDialogVisible: false,
            availablePackages: [], // 存储可用的套餐
            selectedPackage: null, // 存储用户选择的套餐
        }
        },
        methods:{
            buyPackage(row) {
                this.nowRow = row; // 保存当前用户数据
                this.getAvailablePackages(row.balance); // 根据用户余额获取可用套餐
            },
            getAvailablePackages(balance) {
                this.$axios.get('/package/getAvailablePackages', {
                    params: { maxPrice: balance }
                }).then(res => {
                    this.availablePackages = res.data;
                    this.packageDialogVisible = true;
                }).catch(error => {
                    this.$message.error('获取套餐失败');
                    console.error('获取套餐失败', error);
                });
            },
            confirmPackagePurchase() {
                if (!this.selectedPackage) {
                    this.$message.warning('请选择一个套餐');
                    return;
                }

                this.$axios.get('/user/purchase', {
                    params:{
                        userId: this.nowRow.userId,
                        packageId: this.selectedPackage.packageId
                    }

                }).then(res => {
                    if (res.message=="success") {
                        this.$message.success('购买成功');
                        this.packageDialogVisible = false;
                        this.getbyNameLike(); // 刷新用户列表
                    } else {
                        this.$message.error('购买失败');
                    }
                }).catch(error => {
                    this.$message.error('购买失败，请稍后重试');
                    console.error('购买失败', error);
                });
            }, 
            getbyNameLike() {
                // this.pageCurrent = 1; // 搜索时重置为第一页
                console.log(this.searchName);
                
                this.$axios.get(
                "/user/getUserByNameLike",
                {
                    params:{
                        userName : this.searchName,
                        pageCurrent: this.pageCurrent,
                        pageSize: this.pageSize
                    }

                }
            ).then(res=>{
                // console.log(res);
                this.tableData = res.data.list;
                this.total = res.data.total;
                // console.log(this.tableData);
            })    
            },
            clearSearch() {
                this.searchName = '';
                this.getbyNameLike();
            },
            handlePageChange(page,) {
                this.pageCurrent = page;

                this.getbyNameLike(); // 重新获取数据
            },
        },
        created(){
            this.getbyNameLike();
            
        }
    }
</script>