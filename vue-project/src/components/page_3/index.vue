<template>
	<div id="page_3">
		<detailDialog
			:dialogVisible="detailDialogVisible"
			v-on:closeDialog="closeDetail"
			:personDetail="personDetail"
			>
		</detailDialog>
		<el-row>
			<el-col :span="24">
				<el-table
					:data="tableData3"
					style="width: 100%"
					:row-class-name="tableRowClassName">
					<el-table-column
						type="index"
						label="序号"
						width="129">
					</el-table-column>
					<el-table-column
						prop="weight"
						label="体重"
						width="180">
					</el-table-column>
					<el-table-column
						prop="name"
						label="姓名"
						width="180">
					</el-table-column>
					<el-table-column
						prop="createTime"
						label="注册时间">
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
				:data="pageData3"
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