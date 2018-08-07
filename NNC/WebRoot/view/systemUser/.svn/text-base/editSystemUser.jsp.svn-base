<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
</script>
<div class="common_layer_frame">
    <form id="edit_system_user_from" method="post">
        <input class="common_layer_td_right_input" name="id" value="${system_user.id}" type="hidden"/>
        <input class="common_layer_td_right_input" name="passwd" value="${system_user.passwd}" type="hidden"/>
        <input class="common_layer_td_right_input" name="delFlag" value="${system_user.delFlag}" type="hidden"/>
        <input class="common_layer_td_right_input" name="createId" value="${system_user.createId}" type="hidden"/>
        <input class="common_layer_td_right_input" name="createTime" value="${system_user.createTime}" type="hidden"/>
        <div class="common_layer_tr_div" style="margin-top:35px">
          <label class="common_layer_td_left_label">账户名称</label>
          <input class="common_layer_td_right_input" name="loginId" value="${system_user.loginId}"/>
        </div>
        <div class="common_layer_tr_div">
          <label class="common_layer_td_left_label">用户权限</label>
          <select id="computer_type" class="common_layer_td_right_input" name ="role">
              <option value="2" <c:if test="${system_user.role == 2}">selected="selected"</c:if>>总监</option>
              <option value="3" <c:if test="${system_user.role == 3}">selected="selected"</c:if>>文档管理员</option>
          </select>
        </div>
        <div class="common_layer_tr_div" style="position:relative">
          <label class="common_layer_td_left_label" style="position:absolute;top:0;left:0;text-align:left;padding-left:50px;">备注</label>
          <textarea class="common_layer_td_right_input" name="note" style="position:absolute;right:50px;top:0;line-height:36px">${system_user.note}</textarea>
        </div>
    </form>
</div>