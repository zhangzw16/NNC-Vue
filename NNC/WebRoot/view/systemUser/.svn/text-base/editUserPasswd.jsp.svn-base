<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
	.layui-layer-rim{
		
	}
	
</style>
<script type="text/javascript">
  function user_edit_passwd_check() {
      var new_password = $("#edit_user_new_passwd").val();
      var reg = new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/);
      if (!reg.test(new_password) || (new_password.length) != 8){
          nickNutrition.util.pup_tip('密码格式不正确',2);
      }
  }
  
  function user_same_passwd_check() {
      var new_password = $("#edit_user_new_passwd").val();
      var same_passwd = $("#edit_user_same_passwd").val();
      if (same_passwd != new_password){
          nickNutrition.util.pup_tip('两次输入的密码不一致',2);
      }
  }
  
  function user_passwd_check() {
    nickNutrition.systemUser.check_user_passwd();
  }
</script>
<div class="common_layer_frame" style="padding-top:20px">
    <form id="edit_user_passwd_from" method="post">
        <input class="common_layer_td_right_input" name="id" value="${system_user.id}" type="hidden"/>
        <input class="common_layer_td_right_input" name="loginId" value="${system_user.loginId}" type="hidden"/>
        <input class="common_layer_td_right_input" name="delFlag" value="${system_user.delFlag}" type="hidden"/>
        <input class="common_layer_td_right_input" name="role" value="${system_user.role}" type="hidden"/>
        <input class="common_layer_td_right_input" name="createId" value="${system_user.createId}" type="hidden"/>
        <input class="common_layer_td_right_input" name="createTime" value="${system_user.createTime}" type="hidden"/>
        <input id="system_user_passwd" value="${system_user.passwd}" type="hidden"/>
        <input id="system_user_flag" value="${system_user_flag}" type="hidden"/>
        <div class="common_layer_tr_div" style="margin-bottom:10px">
          <label class="common_layer_td_left_label">账号名称</label>
          <input class="common_layer_td_right_input" name="name" value="${system_user.loginId}" readonly="readonly" style="border:none;color:#999"/>
        </div>
        <div class="common_layer_tr_div">
          <label class="common_layer_td_left_label">原始密码</label>
          <input class="common_layer_td_right_input" id="edit_user_first_passwd" onchange="user_passwd_check()" type="password"/>
        </div>
        <div class="common_layer_tr_div" style="margin-bottom:0px">
          <label class="common_layer_td_left_label">新密码&nbsp;&nbsp;&nbsp;</label>
          <input class="common_layer_td_right_input" id="edit_user_new_passwd" name="passwd" type="password"/>
        </div>
        <div class="common_layer_tr_div error">
          <label class="common_layer_td_left_label"></label>
          <span class="common_layer_td_right_input" style="color: red;">密码由数字和字母自由组合，长度为8</span>
        </div>
        <div class="common_layer_tr_div">
          <label class="common_layer_td_left_label">确认密码</label>
          <input class="common_layer_td_right_input" id="edit_user_same_passwd" name="confirm_password" type="password"/>
        </div>
    </form>
</div>