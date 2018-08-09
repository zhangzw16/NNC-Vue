<template>
    <el-dialog title="用户信息" 
        :visible.sync="dialogVisible"
        :before-close="handleClose">
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="基本信息" name="first">基本信息</el-tab-pane>
            <el-tab-pane label="日常数据" name="second">日常数据</el-tab-pane>
            <el-tab-pane label="体重走势" name="third">体重走势</el-tab-pane>
            <el-tab-pane label="每周食物和运动指导" name="fourth">每周食物和运动指导</el-tab-pane>
            <el-tab-pane label="历史数据" name="fifth">历史数据</el-tab-pane>           
        </el-tabs>
    </el-dialog>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>


