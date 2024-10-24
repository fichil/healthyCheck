<template>
    
    <div>
        <!-- 搜索和添加细项按钮 -->
        <el-row :gutter="20" style="margin-bottom: 0px;">
            <el-col :span="8">
                <el-input
                    placeholder="请输入细项名称进行搜索"
                    v-model="searchName"
                    clearable>
                </el-input>
            </el-col>
            <el-col :span="4">
                <el-button type="primary" @click="searchDetailItem">搜索</el-button>
                <el-button @click="clearSearch">清除</el-button>
            </el-col>
            <el-col :span="4" style="text-align: right;">
                <el-button type="success" @click="addDetailItem">添加细项</el-button>
            </el-col>
        </el-row>

        <!-- 项列表 -->
        <el-table
            :data="tableData"
            style="width: 100%"
            height="55vh">
            <el-table-column
                fixed
                prop="detailItemId"
                label="ID"  
                width="100">
            </el-table-column>
            <el-table-column
                prop="detailItemName"
                label="细项名称"
                width="150">
            </el-table-column>

            <el-table-column
                prop="unit"
                label="单位"
                width="150">
            </el-table-column>

            <el-table-column
                prop="upperLimit"
                label="上限"
                width="150">
            </el-table-column>

            <el-table-column
                prop="lowerLimit"
                label="下限"
                width="150">
            </el-table-column>

            <el-table-column
                prop="isDeleted"
                label="是否删除"
                width="150">
            </el-table-column>

            <el-table-column
                label="操作"
                width="450">
                <template slot-scope="scope">
                    <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
                    <el-button type="danger" @click="deleteDetailItem(scope.row)">删除</el-button>
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

        <!-- 编辑细项对弹窗 -->
        <el-dialog 
        title="编辑细项" 
        :visible.sync="dialogFormVisible">
        <el-form :model="form">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="细项名称" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.detailItemName" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="单位" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.unit" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="上线" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.upperLimit" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="下限" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.lowerLimit" autocomplete="off"></el-input>
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
       <!-- 添加细项弹窗 -->
        <el-dialog 
        title="添加细项" 
        :visible.sync="addDialogFormVisible">
        <el-form :model="form">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="细项名称" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.detailItemName" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="单位" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.unit" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="上线" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.upperLimit" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="下限" :label-width="formLabelWidth" >
                        <el-input v-model="nowRow.lowerLimit" autocomplete="off"></el-input>
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
            getAllDetailItem(){
                this.$axios.get(
                "/detailItem/getAllDetailItem",
                {
                    params:{
                        pageCurrent: this.pageCurrent,
                        pageSize: this.pageSize
                    }

                }
            ).then(res=>{
                console.log(res);
                this.tableData = res.data.list;
                this.total = res.data.total;
                // console.log(this.tableData);
            })
            },
            handlePageChange(page,) {
                this.pageCurrent = page;

                this.searchDetailItem(); // 重新获取数据
            },
            edit(row){
                this.nowRow = row;
                this.dialogFormVisible = true;

            },
            deleteDetailItem(row){
                this.nowRow = row;
                this.nowRow.isDeleted = 1;
                this.confirmEdit();
            },
            confirmEdit(){
                this.dialogFormVisible = false,
                this.$axios.post(
                "/detailItem/updateDetailItemById",
                this.nowRow
            ).then(res=>{
                console.log(res);
                this.searchDetailItem();
            })
            },
            confirmAdd(){
                this.addDialogFormVisible = false,
                this.$axios.post(
                "/detailItem/addDetailItem",
                this.nowRow
            ).then(res=>{
                console.log(res);
                this.getAllDetailItem();
            })
            },
            searchDetailItem() {
                // this.pageCurrent = 1; // 搜索时重置为第一页
                this.$axios.get(
                "/detailItem/getDetailItemByNameLike",
                {
                    params:{
                        detailItemName : this.searchName,
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
                this.getAllDetailItem();
            },
            addDetailItem() {
                this.nowRow = {};
                this.addDialogFormVisible = true;

            }
        },
        created(){
            this.getAllDetailItem();
            
        }
    }
</script>