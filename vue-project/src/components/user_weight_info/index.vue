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
            <el-row>
                <el-table :data="tableData" style="width: 100%">
                    <el-table-column
                        type="index"
                        label="星期">
                    </el-table-column>
                    <el-table-column
                        prop="date"
                        label="日期">
                    </el-table-column>
                    <el-table-column
                        prop="weight"
                        label="当日体重">
                    </el-table-column>
                    <el-table-column
                        prop="dietWeight"
                        label="体重变化">
                    </el-table-column>
                </el-table>
            </el-row>
        </el-col>
    </el-row>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>