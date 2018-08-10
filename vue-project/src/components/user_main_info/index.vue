<template>
    <el-row id="page_2" style="height: 100%">
        <el-col :span="24">
            <el-row>
                客户名称：
            </el-row>
            <el-table
            border
            style="width: 480px">
                <el-table-column prop = "phoneNo" label="手机号" width="120">
                </el-table-column>
                <el-table-column prop = "birthDate" label="出生年月" width="120">                   
                </el-table-column>
                <el-table-column label="性别" width="80">                  
                </el-table-column>
                <el-table-column label="身高" width="80">                   
                </el-table-column>
                <el-table-column label="体重" width="80">                    
                </el-table-column>
            </el-table>
            <el-form 
                :label-position="left">
                <el-form-item label = "开始减重日期">
                    <el-date-picker style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item label = "结束减重日期">
                    <el-date-picker style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input type="textarea" :disabled="true"></el-input>
                    <el-button type="primary" icon="el-icon-edit" >修改</el-button>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>