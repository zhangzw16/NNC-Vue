<template>
	<div id="page_3" style="height:100%">
		<detailDialog
			:dialogVisible="detailDialogVisible"
			v-on:closeDialog="closeDetail"
			v-on:detailDataChanged="emitRefresh"
			:personDetail="personDetail"
			ref = "detailDialog"
			>
		</detailDialog>
		<el-row>
			<el-form ref="form" :model="form" label-width="130px">
				<el-form-item label="减重状态：">
					<el-radio-group v-model="radio1" @change="handleRadioChange">
						<el-radio-button label="">全部</el-radio-button>
						<el-radio-button label="0">准备期</el-radio-button>
						<el-radio-button label="1">正在期</el-radio-button>
						<el-radio-button label="2">过渡期</el-radio-button>
						<el-radio-button label="3">完成期</el-radio-button>
					</el-radio-group>		
				</el-form-item>
				<el-form-item label="营养师：">
					<el-select v-model="value" placeholder="请选择"
						@change="SearchSelectChange($event)">
						<el-option	
						v-for="item in optionsDietitian"
						:key="item.value"
						:label="item.label"
						:value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="客户姓名或联系方式：" size="medium">
					<el-input v-model="message" placeholder="请输入内容"></el-input>
				</el-form-item>
			</el-form>
			<el-button type="primary" @click="Search">查询</el-button>
		</el-row>
		<el-row>
			<el-col :span="24">
				<el-table
					:data="tableData3"
					style="width: 100%"
					:row-class-name="tableRowClassName">
					<el-table-column
						type="index"
						label="序号">
					</el-table-column>
					<el-table-column
						prop="name"
						label="姓名">
					</el-table-column>
					<el-table-column
						prop="createTime"
						:formatter="formatDate"
						label="注册时间">
					</el-table-column>
					<el-table-column
						prop="dietitianName"
						label="指定营养师">
					</el-table-column>
					<!-- <el-table-column
						prop="dietitianName"
		      			label="详细"
						width="140">
						<template slot-scope="scope">
							<el-select v-model="value8"
								size="mini"
								filterable
								@change="change($event, scope.row)"
								:placeholder="scope.row.dietitianName">
								<el-option
								v-for="item in optionsDietitian"
								:key="item.value"
								:label="item.label"
								:value="item.value">
								</el-option>
							</el-select>
						</template>
		    		</el-table-column> -->
					<el-table-column
						prop="loginFlag"
						:formatter="formatLoginFlag"
						label="注册方式">
					</el-table-column>
					<el-table-column
						prop="contactWay"
						label="联系方式">
					</el-table-column>
					<el-table-column
						prop="userStatus"
						:formatter="formatUserStatus"
						label="减重状态">
					</el-table-column>
					<el-table-column
						prop="addFlag"
						:formatter="formatAddFlag"
						label="参加尼基计划">
					</el-table-column>
					<el-table-column
						prop="buyFlag"
						:formatter="formatAddFlag"
						label="购买尼基营养缓释多糖">
					</el-table-column>
					<el-table-column
						prop="vitality"
						label="活跃度">
					</el-table-column>
					<!-- <el-table-column
						prop="agreeFlag"
						:formatter="formatAgreeFlag"
						label="登陆许可">
					</el-table-column> -->
					<el-table-column
						prop="agreeFlag"
						label="登陆许可"
						:filter-method="filterAgreeFlag"
      					filter-placement="bottom-end"
						:filters="[{ text: '禁止', value: 1 }, { text: '允许', value: 0 }]">
						<template slot-scope="scope">
							<el-button 
							round size="mini"
							@click.native="changeUserAgree(scope.$index, scope.row)"
							:type="scope.row.agreeFlag === 1 ? 'danger' : 'success'"
							disable-transitions>{{scope.row.agreeFlag===1?"禁止":"允许"}}</el-button>
						</template>
					</el-table-column>
					<el-table-column
		      			label="详细"
						width="140">
						<template slot-scope="scope">
						<el-button
							round
							size="mini"
							type="primary"
							@click="detailCheck(scope.$index, scope.row)">
							查看
						</el-button>
						</template>
		    		</el-table-column>
				</el-table>
			</el-col>
		</el-row>
		<el-row>
			<el-pagination
				@size-change="handleSizeChange"
				@current-change="handleCurrentChange"
				:current-page="currentPage1"
				layout="prev, pager, next, jumper"
				:page-count="pages">
			</el-pagination>
		</el-row>
	</div>
</template>

<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>  