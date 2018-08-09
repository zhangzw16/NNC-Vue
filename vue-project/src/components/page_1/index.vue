<template>
  <el-row id="page_1" style="height: 100%">
	  <el-col :span="24">
			<el-row>
				<el-button type="primary" @click="addSystemUser">
					创建管理员
				</el-button>
				<editDialog :dialogVisible="dialogVisible"
	 				:formVis_addUser="formVis_addUser"
					:formVis_editUser="formVis_editUser"
					:form3="form3"
					v-on:addUserSuccess="refresh"
					v-on:closeDialog="closeUserDialog">
					</editDialog>
			</el-row>
   	  <el-table
	      :data="tableData"
	      style="width: 100%">
	      <el-table-column
	        type="index"
	        label="序号"
	        width="180">
	      </el-table-column>
	      <el-table-column
	        prop="role"
	        label="权限"
	        width="180">
	      </el-table-column>
	      <el-table-column
	        prop="loginId"
	        label="账户名称">
	      </el-table-column>
				<el-table-column
					prop="passwd"
					label="初始密码">
				</el-table-column>
				<el-table-column
		      label="修改"
					width="140">
					<template slot-scope="scope">
					<el-button
						round
						size="mini"
						type="primary"
						@click="editSystemUser(scope.$index, scope.row)">
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