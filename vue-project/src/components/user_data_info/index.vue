<template>
    <el-row id="user_main_info" style="height: 100%">
        <el-col :span="24">
            <el-row>
                客户名称：{{pageInfo.name}}<br><br>
            </el-row>
            <el-row>
                <el-col :span="12">

                </el-col>
                <el-col :span="12">

                </el-col>
                <el-col :span="12">

                </el-col>
            </el-row>
            <el-row>

                <el-form
                    id = "today_info"
                    :model="pageInfo"
                    :label-position="left">

                    <el-col>

                    </el-col>

                    <el-col>
                    <p>选择日期</p>
                    <div class="block">
                        <el-date-picker
                        v-model="value"
                        type="date"
                        placeholder="选择日期"
                        @change="datePicked"
                        :picker-options="pickerOptions">
                        </el-date-picker>
                    </div>
                    </el-col>

                    <el-col>
                    <el-form-item label="备注">
                        <el-input type="textarea" :disabled="true" v-model="pageInfo.note"></el-input>
                        <el-button type="primary" icon="el-icon-edit" @click="editNote">修改</el-button>
                    </el-form-item>
                    </el-col>
                </el-form>

            </el-row>

        </el-col>
    </el-row>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>