<template>
   <el-row id="page_5" style="height: 100%">			
		<el-row>
			<el-card>
				<el-form ref="form" :model="form" label-width="200px">
					<el-form-item label="年龄占比：">
						<el-radio-group v-model="radio" @change="sexChange">
							<el-radio-button label="2">全部</el-radio-button>
							<el-radio-button label="0">男性</el-radio-button>
							<el-radio-button label="1">女性</el-radio-button>
						</el-radio-group>
					</el-form-item>
				</el-form>
				<div id="piePhase" style="width: 620px; height: 280px;"></div>
			</el-card>
		</el-row>
		<el-row>
			<el-card>     
				<el-form ref="form" :inline="true" :model="form" label-width="100px">
					<el-form-item label="图表类型：">
						<el-radio-group v-model="radio1" @change="phaseChange">
							<el-radio-button label="0">平均减重</el-radio-button>
							<el-radio-button label="1">客户人数</el-radio-button>
						</el-radio-group>
						<el-form-item label="日期选择:">
							<el-date-picker
								v-model="value1"
								type="daterange"
								align="right"
								unlink-panels
								range-separator="至"
								start-placeholder="开始日期"
								end-placeholder="结束日期"
								@change="dateChange"
								:picker-options="pickerOptions1">
							</el-date-picker>
						</el-form-item>
					</el-form-item>
				</el-form>
				<div id="dietitianInfoChart" style="width: 800px; height: 280px;"></div>
			</el-card>
		</el-row>

		<el-row>
			<el-card>     
				<div id="participantNumberChart" style="width: 800px; height: 280px;"></div>
			</el-card>
		</el-row>
	</el-row>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>
