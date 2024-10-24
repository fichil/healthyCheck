<template>
    
    <div>
        <!-- 搜索和添加细项按钮 -->
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
            <el-col :span="8" style="text-align: right;">
                <el-button type="success" @click="add()">添加用户</el-button>
                <el-button type="success" @click="openImportDialog">批量导入用户</el-button>
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

        <!-- 编辑用户弹窗 -->
        <el-dialog 
        title="编辑用户信息" 
        :visible.sync="dialogFormVisible">
        <el-form :model="form">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="姓名" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.name" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="联系方式" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.phone" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="身份证号码" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.idNumber" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="性别" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.gender" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="年龄" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.age" autocomplete="off"></el-input>
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
       <!-- 添加用户弹窗 -->
        <el-dialog 
        title="添加用户" 
        :visible.sync="addDialogFormVisible">
        <el-form :model="form">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="姓名" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.name" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="联系方式" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.phone" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="身份证号码" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.idNumber" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="性别" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.gender" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="年龄" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.age" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="密码" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.password" autocomplete="off"></el-input>
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

        <!-- 批量导入用户弹窗 -->
        <el-dialog title="批量导入用户" :visible.sync="importDialogVisible">
            <el-row>
                <el-col :span="12">
                    <el-button  type="primary" @click="downloadTemplate">下载模板</el-button>
                </el-col>
                <el-col :span="12">
                    <el-upload
                    :action="''"
                    :before-upload="beforeUpload"
                    :on-success="handleImportSuccess"
                    :on-error="handleImportError"
                    :show-file-list="false"
                    accept=".xls, .xlsx"
                >
                    <el-button  type="primary">上传Excel文件</el-button>
                    <div slot="tip" class="el-upload__tip">请上传Excel文件，支持xls和xlsx格式</div>
                </el-upload>
                
                </el-col>
            </el-row>
        </el-dialog>
    </div>
</template>
  
<script>
    export default {
        data() {
        return {
            dialogFormVisible: false,
            addDialogFormVisible:false,
            importDialogVisible: false,
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
            openImportDialog() {
                this.importDialogVisible = true;
            },
            downloadTemplate() {
                this.$axios({
                    url: "/user/downloadTemplate",
                    method: 'GET',
                    responseType: 'blob' // 确保接收的是二进制文件
                }).then(res => {
                    console.log(res);
                    
                    // 处理 blob 数据
                    const blob = new Blob([res], { type: res['content-type'] || 'application/octet-stream' });
                    const downloadUrl = window.URL.createObjectURL(blob);
                    const link = document.createElement('a');
                    link.href = downloadUrl;
                    link.download = 'template.xlsx'; // 设置下载的文件名
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);
                }).catch(error => {
                    console.error("下载模板失败", error);
                });
            },
            // 文件上传前的处理逻辑
            beforeUpload(file) {
                this.uploadUsers(file); // 调用上传方法
                return false; // 阻止默认上传行为
            },
            // 上传成功的回调
            handleImportSuccess(response) {
                console.log(response);
                
                this.$message.success('用户导入成功');
                this.importDialogVisible = false;
                this.getbyNameLike(); // 刷新用户列表
            },
            // 上传失败的回调
            handleImportError(error) {
                this.$message.error('用户导入失败，请稍后重试');
                console.error("文件上传失败", error);
            },
            // 上传用户文件的逻辑
            uploadUsers(file) {
                    const formData = new FormData();
                    formData.append('file', file);

                    this.$axios.post("/user/importUsers", formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(res => {
                        if (res=="success") {
                            this.handleImportSuccess(res);
                        } else {
                            this.handleImportError(res);
                        }
                    }).catch(error => {
                        this.handleImportError(error);
                    });
                },
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
                "/user/updateUserById",
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
                "/user/addUser",
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