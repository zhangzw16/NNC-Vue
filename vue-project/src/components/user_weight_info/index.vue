<template>
    <el-row id="user_weight_info" style="height: 100%">
        <el-col :span="24">
            <el-row>
                客户名称：{{pageInfo.name}}<br><br>
            </el-row>
            <el-row>
                <el-form
                    id = "today_info"
                    :model="pageInfo"
                    :label-position="left">
                    <el-col>
                    <p>选择日期</p>
                    <div class="block">
                        <el-date-picker
                        v-model="pageInfo.date"
                        type="date"
                        placeholder="选择日期"
                        @input="getData">
                        </el-date-picker>
                    </div>
                    </el-col>
                </el-form>
            </el-row>
            <br>
            <el-row :gutter="12">
                <el-col :span="12">
                    <el-card>
                        <el-table :data="tableData" style="width: 100%; font-size: 12px" row-style="height:0" cell-style="padding:0" border>
                            <el-table-column
                                prop="day"
                                label="星期"
                                width="60">
                            </el-table-column>
                            <el-table-column
                                prop="date"
                                label="日期"
                                width="90">
                            </el-table-column>
                            <el-table-column
                                prop="weight"
                                label="当日体重"
                                width="80">
                            </el-table-column>
                            <el-table-column
                                prop="dietWeight"
                                label="体重变化"
                                width="80">
                            </el-table-column>
                        </el-table>
                    </el-card>
                </el-col>
            </el-row>
                <el-col :span="12">
                    <el-card>
                        <div id="myChart" style="{width: '300px', height: '300px'}"></div>
                    </el-card>
                </el-col>
        </el-col>
    </el-row>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>