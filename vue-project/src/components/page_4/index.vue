<template>
    <el-row id="page_1">
        <el-col :span="24">
            <el-row>
                营养师：{{dietitianName}}
                手机号：{{dietitianPhone}}
            </el-row>
            <detailDialog
                :dialogVisible="detailDialogVisible"
                v-on:closeDialog="closeDetail"
                v-on:detailDataChanged="emitRefresh"
                :personDetail="personDetail"
                ref = "detailDialog">
            </detailDialog>
            <el-row>
                <el-button type="primary"
                    size="medium"
                    @click="userFilter(1)">
                    正在减脂客户
                </el-button>
                <el-button type="primary"
                    size="medium"
                    @click.native="userFilter(2)">
                    过渡期客户
                </el-button>
                <el-button type="primary"
                    size="medium"
                    @click.native="userFilter(3)">
                    已完成客户
                </el-button>
            </el-row>
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
                        @click="detailCheck(scope.$index, scope.row)">查看</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
    </el-row>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>


