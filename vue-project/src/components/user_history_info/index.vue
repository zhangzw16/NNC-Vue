<template>
    <el-row id="user_history_info" style="height: 100%">
        <el-col :span="24" :gutter="12">
            <el-row>
                客户名称：{{pageInfo.name}}<br><br>
            </el-row>

            <el-dialog title="详细信息" :visible.sync="historyDialogVisible"
                append-to-body>
                <el-row>
                    <el-card>                  
                        <div>尼基营养干预体重走势</div>
                        <br>
                        <div id="Chart" style="width: 580px; height: 280px;"></div>
                    </el-card>
                </el-row>
                <br>
                <el-row>
                    <el-card>
                        <el-input :readonly="true" v-model="rowData.period"><template slot="prepend">减重期间</template></el-input>
                        <el-input :readonly="true" v-model="rowData.startWeight"><template slot="prepend">初始体重</template></el-input>
                        <el-input :readonly="true" v-model="rowData.endWeight"><template slot="prepend">减脂后体重</template></el-input>
                        <el-input :readonly="true" v-model="rowData.dietitianName"><template slot="prepend">营养师</template></el-input>
                    </el-card>
                </el-row>
            </el-dialog>

            <el-row>
                <el-table
                    :data="historyTableData"
                    style="width: 100%"
                    :row-class-name="tableRowClassName">
                    <el-table-column
                        type="index"
                        label="序号">
                    </el-table-column>
                    <el-table-column
                        prop="phaseCount"
                        label="第几期">
                    </el-table-column>
                    <el-table-column
                        prop="transitionStartDate"
                        :formatter="formatDate"
                        label="开始时间">
                    </el-table-column>
                    <el-table-column
                        prop="transitionEndDate"
                        :formatter="formatDate"
                        label="结束时间">
                    </el-table-column>
                    <el-table-column
                        label="详细">
                        <template slot-scope="scope">
							<el-button 
							    round size="mini"
                                type="primary"
							    @click="showHistoryDetail(scope.$index, scope.row)">
                                查看
                            </el-button>
						</template>
                    </el-table-column>
                </el-table>
            </el-row>
        </el-col>
    </el-row>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>