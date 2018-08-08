<template>
   <el-table 
        :data="tableData4"
        style="width: 100%"
        :row-class-name="tableRowClassName">
        <el-table-column
            prop="id"
            label="序号"
            width="80">
        </el-table-column>
        <el-table-column
            prop="name"
            label="客户姓名"
            width="140">
        </el-table-column>
        <el-table-column
            prop="phoneNo"
            label="手机号"
            width="140">
        </el-table-column>
            <el-table-column
            prop="passwd"
            label="减重日"
            width="200">
        </el-table-column>
        <el-table-column
            prop=""
            label="详细数据"
            width="140">
            <template slot-scope="scope">
            <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)">查看</el-button>
            </template>
        </el-table-column>
        </el-table>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>