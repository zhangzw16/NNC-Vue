<template>
  <el-row id="page_2" style="height: 100%">
  	<el-col >
			<el-row>
				<el-button type="primary"
					@click="dialogVisible = true">
					创建营养师
				</el-button>
			</el-row>
			<el-dialog title="营养师信息"
				:visible.sync="dialogVisible">
				<el-form :model="form" label-width="120px">
					<el-form-item label="营养师姓名" :label-width="formLabelWidth">
							<el-input v-model="form.dieName" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item label="APP登陆账号" :label-width="formLabelWidth">
							<el-input v-model="form.number" auto-complete="off" placeholder="手机号码"></el-input>
					</el-form-item>
					<el-form-item label="上岗日期" :label-width="formLabelWidth">
							<el-date-picker type="date" placeholder="上岗日期" v-model="form.date1" style="width: 100%;"></el-date-picker>
					</el-form-item>
					<el-form-item label="下岗日期" :label-width="formLabelWidth">
							<el-date-picker type="date" placeholder="下岗日期" v-model="form.date2" style="width: 100%;"></el-date-picker>
					</el-form-item>
					<el-form-item label="备注" :label-width="formLabelWidth">
							<el-input v-model="form.note" auto-complete="off"></el-input>
					</el-form-item>
				</el-form>
				<div slot="footer" class="dialog-footer">
					<el-button type="primary" @click="dialogVisible = false">取消</el-button>
					<el-button type="primary" @click="editUser">保存</el-button>
				</div>
			</el-dialog>

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