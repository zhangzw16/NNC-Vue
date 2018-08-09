<template>
    <el-dialog title="创建管理员" 
        :visible.sync="dialogFormVisible"
        :before-close="handleClose"
        width = "300px">
        <el-form :model="form" label-width="80px">
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
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="handleClose">取消</el-button>
            <el-button type="primary" @click="handleClose">保存</el-button>
        </div>
    </el-dialog>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>