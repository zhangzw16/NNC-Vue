<template>
    <el-dialog title="用户信息" 
        :visible.sync="dialogVisible"
        :before-close="handleClose">
        <el-tabs 
            v-model="activeName"
            @tab-click="handleClick">
            <el-tab-pane label="基本信息" name="first">
                <userMainInfo 
                    :personDetail="personDetail" 
                    ref="mainInfo"
                    v-on:mainInfoChanged="detailDataChanged"
                ></userMainInfo>
            </el-tab-pane>
            <el-tab-pane label="日常数据" name="second">
                <userDataInfo
                    :personFoodData="personFoodData"
                    ref="dataInfo"
                    v-on:noteChanged="dataChanged"
                ></userDataInfo>
            </el-tab-pane>
            <el-tab-pane label="体重走势" name="third">
                <userWeightInfo
                    :personWeightData="personWeightData"
                    ref="weightInfo">
                </userWeightInfo>
            </el-tab-pane>
            <el-tab-pane label="每周食物和运动指导" name="fourth">
                <userGuideInfo
                    :personGuideData="personGuideData"
                    ref="guideInfo">
                </userGuideInfo>
            </el-tab-pane>
            <el-tab-pane label="历史数据" name="fifth">历史数据</el-tab-pane>           
        </el-tabs>
    </el-dialog>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>


