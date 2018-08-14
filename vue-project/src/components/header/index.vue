<template>
  <el-row :gutter="20" id="header">
    <el-col class="left_header" :span="22">
      <img alt src="../../../res/ncclogo.png" 
        width="36" 
        height="36" 
        class="ncc_logo">
      <span class="default_company_name_boco">尼基营养</span>
      <span class="default_company_name_title">管理平台</span>
    </el-col>
    <el-col class="right_header" span="2">
      <span class="header_date">{{date}}</span>
    </el-col>
  </el-row>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>