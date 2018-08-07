<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
	.common_layer_tr_div{
		margin-bottom:18px !important;
	}
	.common_layer_td_right_input{
		width:200px !important;
	}
	.common_layer_td_left_label{
		width:147px !important;
		text-align:left !important;
		padding-left:44px !important;
	}
</style>
<script type="text/javascript">
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        
        document.getElementById('work_start_date').onclick = function(){
          laydate({elem: this, istime: false, format: 'YYYY-MM-DD'});
        };
        
        document.getElementById('work_end_date').onclick = function(){
          laydate({elem: this, istime: false, format: 'YYYY-MM-DD'});
        };
    });
</script>

<div class="common_layer_frame">
    <form id="add_dietitian_from" method="post">
        <input class="common_layer_td_right_input" name="id" type="hidden"/>
        <input class="common_layer_td_right_input" name="createId" value="${system_user.id}" type="hidden"/>
        <div class="common_layer_tr_div" style="margin-top:20px;">
          <label class="common_layer_td_left_label">营养师姓名</label>
          <input class="common_layer_td_right_input" name="name" type="text"/>
        </div>
        <div class="common_layer_tr_div">
          <label class="common_layer_td_left_label">APP登录帐号</label>
          <input class="common_layer_td_right_input" name="loginId" type="text" placeholder="手机号码"/>
        </div>
        <div class="common_layer_tr_div">
          <label class="common_layer_td_left_label">上岗时间</label>
          <input class="common_layer_td_right_input" id="work_start_date" name="workStartDate" type="text"/>
        </div>
        <div class="common_layer_tr_div">
          <label class="common_layer_td_left_label">下岗时间</label>
          <input class="common_layer_td_right_input" id="work_end_date" name="workEndDate" type="text"/>
        </div>
        <div class="common_layer_tr_div" style="position:relative">
          <label class="common_layer_td_left_label" style="position:absolute;left:0;top:0">备注</label>
          <textarea class="common_layer_td_right_input" name="note" style="position:absolute;right:57px;top:0;"></textarea>
        </div>
    </form>
</div>