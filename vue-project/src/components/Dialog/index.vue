<template>
    <el-dialog title="用户信息" 
        :visible.sync="dialogVisible"
        :before-close="handleClose">

        <!-- dietitian -->
        <el-form :model="form"
            v-show="formVis_dietitian">
            <el-form-item label="营养师姓名" :label-width="formLabelWidth">
                <el-input v-model="form.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="APP登陆账号" :label-width="formLabelWidth">
                <el-input v-model="form.number" auto-complete="off" placeholder="手机号码"></el-input>
            </el-form-item>
        </el-form>

        <!-- addUser -->
        <el-form :model="form" label-width="80px"
            v-show="formVis_addUser">
            <el-form-item label="账户名称">
                <el-input v-model="form.systemUserName" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="账户权限">
                <el-select v-model="form.systemUserAuthority" placeholder="总监">
                <el-option label="总监" value="总监"></el-option>
                <el-option label="文档管理员" value="文档管理员"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="备注">
                <el-input v-model="form.systemUserRemark" auto-complete="off"></el-input>
            </el-form-item>
        </el-form>

        <!-- edit -->
        <el-form :model="form" label-width="80px"
            v-show="formVis_editUser">
            <el-form-item label="账户名称">
                <el-input v-model="form.systemUserName" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="账户权限">
                <el-select v-model="form.systemUserAuthority" placeholder="总监">
                <el-option label="总监" value="总监"></el-option>
                <el-option label="文档管理员" value="文档管理员"></el-option>
                </el-select>
            </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="handleClose">取消</el-button>
            <el-button type="primary" @click="handleClose">保存</el-button>
        </div>
    </el-dialog>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>


