<template>
    <el-dialog title="用户信息" 
        :visible.sync="dialogVisible"
        :before-close="handleClose">

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


