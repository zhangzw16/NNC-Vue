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
            <el-row :gutter="12">
                <el-col :span="8">
                    <el-card shadow="hover">
                    <div slot="header" class="clearfix">
                        <span>早餐</span>
                    </div>
                    <div class="text item" >
                        {{ breakfast }}
                    </div>
                    </el-card>
                </el-col>
                <el-col :span="8">
                    <el-card shadow="hover">
                    <div slot="header" class="clearfix">
                        <span>午餐</span>
                    </div>
                    <div class="text item">
                        {{ lunch }}
                    </div>
                    </el-card>
                </el-col>
                <el-col :span="8">
                    <el-card shadow="hover">
                    <div slot="header" class="clearfix">
                        <span>晚餐</span>
                    </div>
                    <div class="text item">
                        {{ dinner }}
                    </div>
                    </el-card>
                </el-col>
            </el-row>
            <br>
            <el-row :gutter="12">
                <el-col :span="12">
                    <el-card class="box-card">
                        <el-input :readonly="true" v-model="pageInfo.exercise"><template slot="prepend">今天的运动</template></el-input>
                        <el-input :readonly="true" v-model="pageInfo.drankWater"><template slot="prepend">饮水量</template></el-input>
                        <el-input :readonly="true" v-model="pageInfo.comfortLevel"><template slot="prepend">舒适度</template></el-input>
                        <el-input :readonly="true" v-model="pageInfo.testPaperValue"><template slot="prepend">试纸等级</template></el-input>
                        <el-input :readonly="true" v-model="pageInfo.weight"><template slot="prepend">体重</template></el-input>
                    </el-card>
                </el-col>

                <el-col :span="12">
                    <el-card class="box-card">
                        <el-form
                            id = "today_info"
                            :model="pageInfo"
                            :label-position="left">
                            <el-form-item label="备注">
                                <el-input type="textarea" :disabled="true" v-model="pageInfo.comments"></el-input>
                            </el-form-item>
                            <el-button type="primary" icon="el-icon-edit" @click="editComment">修改</el-button>
                        </el-form>
                    </el-card>
                </el-col>
            </el-row>

        </el-col>
    </el-row>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>