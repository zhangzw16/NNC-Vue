<template>
  <el-row id="page_3">
  	<el-col :span="24">
		  <el-table
		    :data="tableData3"
		    style="width: 100%"
		    :row-class-name="tableRowClassName">
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
		      label="查看">
             
		    </el-table-column>
		  </el-table>
  	</el-col>
	</el-row>
</template>
<style src="./index.less" lang="less"></style>
<script src="./index.js"></script>