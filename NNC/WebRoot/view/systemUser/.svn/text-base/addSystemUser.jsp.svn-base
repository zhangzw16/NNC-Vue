<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
	#add_system_user_from .common_layer_td_right_input{
		width:300px;
	}
	#add_system_user_from .common_layer_td_left_label{
		width:80px;
	}
</style>
<script type="text/javascript">
</script>
<div class="common_layer_frame">
    <form id="add_system_user_from" method="post">
        <input class="common_layer_td_right_input" name="id" type="hidden"/>
        <input class="common_layer_td_right_input" name="delFlag" type="hidden"/>
        <div class="common_layer_tr_div" style="margin-top:50px;">
          <label class="common_layer_td_left_label">账户名称</label>
          <input class="common_layer_td_right_input" name="loginId" type="text"/>
        </div>
        <div class="common_layer_tr_div">
          <label class="common_layer_td_left_label">账户权限</label>
          <select id="computer_type" class="common_layer_td_right_input" name ="role">
                <option value="2">总监</option>
                <option value="3">文档管理员</option>
          </select>
        </div>
        <div class="common_layer_tr_div" style="position:relative">
          <label class="common_layer_td_left_label" style="position:absolute;top:0;left:0;">备注</label>
          <textarea class="common_layer_td_right_input" name="note" style="position:absolute;right:27px;top:0;line-height:36px"></textarea>
        </div>
    </form>
</div>