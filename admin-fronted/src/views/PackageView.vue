<template>
    
    <div>
        <!-- 搜索和添加细项按钮 -->
        <el-row :gutter="20" style="margin-bottom: 0px;">
            <el-col :span="8">
                <el-input
                    placeholder="请输入套餐名称进行搜索"
                    v-model="searchName"
                    clearable>
                </el-input>
            </el-col>
            <el-col :span="4">
                <el-button type="primary" @click="getbyNameLike()">搜索</el-button>
                <el-button @click="clearSearch()">清除</el-button>
            </el-col>
            <el-col :span="4" style="text-align: right;">
                <el-button type="success" @click="add()">添加项目</el-button>
            </el-col>
        </el-row>

        <!-- 套餐列表 -->
        <el-table
            :data="tableData"
            style="width: 100%"
            height="55vh">
            <el-table-column
                fixed
                prop="packageId"
                label="ID"  
                width="150">
            </el-table-column>
            <el-table-column
                prop="packageName"
                label="项目名称"
                width="350">
            </el-table-column>

            <el-table-column
                prop="price"
                label="单价"
                width="150">
            </el-table-column>

            <el-table-column
                prop="isDeleted"
                label="是否删除"
                width="250">
            </el-table-column>

            <el-table-column
                label="操作"
                width="450">
                <template slot-scope="scope">
                    <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
                    <el-button type="danger" @click="deleteThis(scope.row)">删除</el-button>
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

        <!-- 编辑项目弹窗 -->
        <el-dialog 
        title="编辑套餐" 
        :visible.sync="dialogFormVisible">
        <el-form :model="form">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="套餐名称" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.packageName" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="价格" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.price" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="是否删除" :label-width="formLabelWidth">
                        <el-select v-model="nowRow.isDeleted" autocomplete="off">
                            <el-option label="0" :value="0"></el-option>
                            <el-option label="1" :value="1"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirmEdit()">确 定</el-button>
        </div>
        </el-dialog>
       <!-- 添加套餐弹窗 -->
        <el-dialog 
        title="添加项目" 
        :visible.sync="addDialogFormVisible">
        <el-form :model="form">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="套餐名称" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.packageName" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="价格" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.price" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="是否删除" :label-width="formLabelWidth">
                        <el-select v-model="nowRow.isDeleted" autocomplete="off">
                            <el-option label="0" :value="0"></el-option>
                            <el-option label="1" :value="1"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="addDialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirmAdd()">确 定</el-button>
        </div>
        </el-dialog>
    </div>
</template>
  
<script>
    export default {
        data() {
        return {
            dialogFormVisible: false,
            addDialogFormVisible:false,
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
        }
        },
        methods:{
            handlePageChange(page,) {
                this.pageCurrent = page;

                this.getbyNameLike(); // 重新获取数据
            },
            edit(row){
                this.nowRow = row;
                this.dialogFormVisible = true;

            },
            deleteThis(row){
                this.nowRow = row;
                this.nowRow.isDeleted = 1;
                this.confirmEdit();
            },
            confirmEdit(){
                this.dialogFormVisible = false,
                this.$axios.post(
                "/package/updatePackageById",
                this.nowRow
                ).then(res=>{
                    console.log(res);
                    this.getbyNameLike();
                    this.$notify({
                        title: '成功',
                        message: '编辑成功',
                        type: 'success'
                    });
                })
            },
            confirmAdd(){
                this.addDialogFormVisible = false,
                this.$axios.post(
                "/package/addPackage",
                this.nowRow
                ).then(res=>{
                    console.log(res);
                    this.getbyNameLike();
                    this.$notify({
                        title: '成功',
                        message: '添加成功',
                        type: 'success'
                    });
                })
            },
            getbyNameLike() {
                // this.pageCurrent = 1; // 搜索时重置为第一页
                this.$axios.get(
                "/package/getPackageByNameLike",
                {
                    params:{
                        packageName : this.searchName,
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
            add() {
                this.nowRow = {};
                this.addDialogFormVisible = true;

            }
        },
        created(){
            this.getbyNameLike();
            
        }
    }
</script>