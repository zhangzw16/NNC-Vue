<template>
   <el-container id="home">
    <el-header>
      <headers>
      </headers>
    </el-header>  

    <el-dialog
      title="尼基营养后台管理"
      :visible.sync="loginDialogVisible"
      width="25%"
      :before-close="closeLogin"
      center>
      <br>
      <br>
      <el-form :model="loginForm" label-width="50px">
        <el-form-item label="账号">
            <el-input v-model="loginForm.loginId" auto-complete="off"></el-input>
        </el-form-item>
        <br>
        <el-form-item label="密码">
            <el-input v-model="loginForm.passWord" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="login">登陆</el-button>
      </span>
    </el-dialog>

    <el-container >
        <el-aside>
          <asideBar ref="asideBar" :visible.sync="mainWindowVisible"></asideBar>
        </el-aside>
        <el-main>
          <router-view id="routerInner" :visible.sync="mainWindowVisible"></router-view>
        </el-main>
      </el-container>
    </el-container>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>