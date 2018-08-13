<template>
    <el-row id="page_2" style="height: 100%">
        <el-col :span="24">
            <el-row>
                客户名称：{{pageInfo.name}}<br><br>
            </el-row>
            <el-row :gutter="12">
                <el-col :span="12">
                    <el-card>
                        <el-input :readonly="true" v-model="pageInfo.phoneNumber"><template slot="prepend">手机号</template></el-input>
                        <el-input :readonly="true" v-model="pageInfo.birthDate"><template slot="prepend">出生年月</template></el-input>
                        <el-input :readonly="true" v-model="pageInfo.gender"><template slot="prepend">性别</template></el-input>
                        <el-input :readonly="true" v-model="pageInfo.height"><template slot="prepend">身高</template></el-input>
                        <el-input :readonly="true" v-model="pageInfo.weight"><template slot="prepend">体重</template></el-input>
                    </el-card>

                </el-col>
                    
                <el-col :span="12" v-if="personDetail.addFlag === 1">
                    <el-card>
                        <el-input :readonly="true" v-model="pageInfo.bloodPressure"><template slot="prepend">血压</template></el-input>
                        <el-input :readonly="true" v-model="pageInfo.bloodFat"><template slot="prepend">血脂</template></el-input>
                        <el-input :readonly="true" v-model="pageInfo.bloodSugar"><template slot="prepend">血糖</template></el-input>
                        <el-input :readonly="true" v-model="pageInfo.bloodUricAcid"><template slot="prepend">尿酸</template></el-input>
                        <el-input :readonly="true" v-model="pageInfo.hepaticAdiposeInfiltration"><template slot="prepend">脂肪肝</template></el-input>
                    </el-card>
                </el-col>
            </el-row>

            <br>
            <el-row :gutter="12">
                <el-col :span="12">
                    <el-card>
                    <el-form 
                        :model="pageInfo"
                        label-position="left">
                        <el-form-item label = "开始减重日期">
                            <el-date-picker style="width: 100%;" v-model="pageInfo.startDate" @input="editStartDate"></el-date-picker>
                        </el-form-item>
                        <el-form-item label = "结束减重日期">
                            <el-date-picker style="width: 100%;" v-model="pageInfo.endDate" @input="editEndDate"></el-date-picker>
                        </el-form-item>
                    </el-form>
                    </el-card>
                </el-col>

                <el-col :span="12">
                    <el-card>
                    <el-form
                        :model="pageInfo"
                        label-position="left">
                        <el-form-item label="备注">
                            <el-input type="textarea" :readonly="true" v-model="pageInfo.note"></el-input>
                        </el-form-item>
                    </el-form>
                    <el-button type="primary" icon="el-icon-edit" @click="editNote">修改</el-button>
                    </el-card>
                </el-col>

            </el-row>
        </el-col>
    </el-row>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>