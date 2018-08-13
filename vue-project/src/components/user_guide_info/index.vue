<template>
    <el-row id="user_main_info" style="height: 100%">
        <el-col :span="24" :gutter="12">
            <el-row>
                客户名称：{{pageInfo.name}}<br><br>
            </el-row>

            <el-row>
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
            </el-row>
            <br>
            <el-row>
                <el-card class="box-card">
                    <el-form
                        :model="pageInfo"
                        :label-position="left">
                        <el-form-item label="本周运动指导">
                            <el-input type="textarea" :disabled="true" v-model="sport"></el-input>
                        </el-form-item>
                        <el-button type="primary" icon="el-icon-edit" @click="editRecommend(1)">修改</el-button>
                    </el-form>
                </el-card>
            </el-row>
            <br>
            <el-row>
                <el-card class="box-card">
                    <el-form
                        :model="pageInfo"
                        :label-position="left">
                        <el-form-item label="本周饮食指导">
                            <el-input type="textarea" :disabled="true" v-model="menu"></el-input>
                        </el-form-item>
                        <el-button type="primary" icon="el-icon-edit" @click="editRecommend(2)">修改</el-button>
                    </el-form>
                </el-card>
            </el-row>

        </el-col>
    </el-row>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>