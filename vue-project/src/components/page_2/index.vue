<template>
  <el-row id="page_2" style="height: 100%">
  	<el-col >
			<el-row>
				<el-button type="primary"
					@click="addDietitian">
					创建营养师
				</el-button>
			</el-row>
			<addSystemUserDialog 
				:dialogVisible="addDietitianDialogVisible"
				:formVis_dietitian="formVis_dietitian"
				v-on:closeDialog="closeAddDietitianDialog">
			</addSystemUserDialog>
		  <el-table @row-dblclick="handleRowClick"
		    :data="tableData2"
		    style="width: 100%"
		    :row-class-name="tableRowClassName">
		    <el-table-column
		      type="index"
		      label="序号"
		      width="80">
		    </el-table-column>
		    <el-table-column
		      prop="name"
		      label="营养师姓名"
		      width="140">
		    </el-table-column>
		    <el-table-column
		      prop="phoneNo"
		      label="APP登陆账号"
					width="140">
		    </el-table-column>
				<el-table-column
		      prop="passwd"
		      label="初始密码"
					width="200">
		    </el-table-column>
		    <el-table-column
		      prop="beingReduced"
		      label="正在减脂人数"
					width="140">
		    </el-table-column>
				<el-table-column
		      label="重置密码"
					width="140">
					<template slot-scope="scope">
					<el-button
						round
						size="mini"
						type="danger"
						@click="resetPsd(scope.$index, scope.row)">
						重置密码
						</el-button>
					</template>
		    </el-table-column>
				<el-table-column
		      label="修改"
					width="140">
					<template slot-scope="scope">
					<el-button
						round
						size="mini"
						type="primary"
						@click="handleEdit(scope.$index, scope.row)">
						修改
						</el-button>
					</template>
		    </el-table-column>
		  </el-table>
  	</el-col>
	</el-row>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>