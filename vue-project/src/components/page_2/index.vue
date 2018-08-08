<template>
  <el-row id="page_2">
  	<el-col :span="24">
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
		  </el-table>
  	</el-col>
	</el-row>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>