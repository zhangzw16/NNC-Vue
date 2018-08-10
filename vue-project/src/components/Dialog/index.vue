<template>
    <el-dialog title="用户信息" 
        :visible.sync="dialogVisible"
        :before-close="handleClose">

        <!-- dietitian -->
        <el-form :model="form1"
            v-show="formVis_dietitian">
            <el-form-item label="营养师姓名" :label-width="formLabelWidth">
                <el-input v-model="form1.dieName" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="APP登陆账号" :label-width="formLabelWidth">
                <el-input v-model="form1.number" auto-complete="off" placeholder="手机号码"></el-input>
            </el-form-item>
            <el-form-item label="上岗日期" :label-width="formLabelWidth">
                <el-date-picker type="date" placeholder="上岗日期" v-model="form1.date1" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="下岗日期" :label-width="formLabelWidth">
                <el-date-picker type="date" placeholder="下岗日期" v-model="form1.date2" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="备注" :label-width="formLabelWidth">
                <el-input v-model="form1.note" auto-complete="off"></el-input>
            </el-form-item>
        </el-form>

        <!-- addUser -->
        <el-form :model="form2" label-width="80px"
            v-show="formVis_addUser">
            <el-form-item label="账户名称">
                <el-input v-model="form2.systemUserName" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="账户权限">
                <el-select v-model="form2.systemUserAuthority" placeholder="总监">
                <el-option label="总监" value="总监"></el-option>
                <el-option label="文档管理员" value="文档管理员"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="备注">
                <el-input v-model="form2.systemUserRemark" auto-complete="off"></el-input>
            </el-form-item>
        </el-form>

        <!-- edit -->
        <el-form :model="form3" label-width="80px"
            v-show="formVis_editUser">
            <el-form-item label="账户名称">
                <el-input v-model="form3.systemUserName" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="账户权限">
                <el-select v-model="form3.systemUserAuthority" placeholder="总监">
                <el-option label="总监" value="总监"></el-option>
                <el-option label="文档管理员" value="文档管理员"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="备注">
                <el-input v-model="form3.systemUserRemark" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="danger" @click="resetUserPasswd">重置密码</el-button>
                <el-button type="danger" @click="deleteSysUser">删除管理员</el-button>
            </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer" v-show="formVis_dietitian">
        <el-button type="primary" @click="handleClose">取消</el-button>
        <el-button type="primary" @click="handleClose">保存</el-button>
        </div>

        <div slot="footer" class="dialog-footer" v-show="formVis_addUser">
        <el-button type="primary" @click="handleClose">取消</el-button>
        <el-button type="primary" @click="saveUser">保存</el-button>
        </div>

        <div slot="footer" class="dialog-footer" v-show="formVis_editUser">
        <el-button type="primary" @click="handleClose">取消</el-button>
        <el-button type="primary" @click="editUser">保存</el-button>
        </div>

    </el-dialog>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>


