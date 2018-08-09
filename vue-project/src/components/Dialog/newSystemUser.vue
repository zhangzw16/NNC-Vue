<template>
    <el-dialog title="用户信息" 
        :visible.sync="dialogFormVisible"
        :before-close="handleClose">
        <el-form :model="form">
            <el-form-item label="营养师姓名" :label-width="formLabelWidth">
                <el-input v-model="form.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="APP登陆账号" :label-width="formLabelWidth">
                <el-input v-model="form.number" auto-complete="off" placeholder="手机号码"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="closeDialog">取消</el-button>
            <el-button type="primary" @click="closeDialog">保存</el-button>
        </div>
    </el-dialog>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>