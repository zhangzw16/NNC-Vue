<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style>
.yxq .layui-layer-btn{
	position:relative;
}
.yxq .layui-layer-btn1,.yxq .layui-layer-btn2{
	position:absolute;
	top:-52px;
	width:100px !important;
	height:30px !important;
	line-height:30px;
	text-align:center;
	border-radius:6px;
	background:#fe9524;
	box-shadow:1px 1px 4px rgba(0,0,0,.5);
	color:#fff !important;
}
.yxq .layui-layer-btn1{
	right:150px;
}
.yxq .layui-layer-btn2{
	right:30px;
}
</style>
<script type="text/javascript">
  function edit_system_user(userId) {
    $.post('rest/systemUser/edit_system_user', {
      param : userId
    }, function(str) {
      layer.open({
        title : "信息修改",
        type : 1,
        shift : 4,
        area : ['420px','360px'],
        offset: '80px',
        skin : 'layui-btn-normal yxq',
        content : str,
        closeBtn : 1,
        shadeClose : false,
        btn : [ '保存' , '重置密码' , '删除管理员' , '取消' ],
        yes : function(index, layero){
          var falg = nickNutrition.systemUser.check_system_user_add_from();
          if (falg) {
            $.ajax({
                url : "rest/systemUser/edit_system_user_save",
                type : "POST",
                data : $("#edit_system_user_from").serialize(),
                success : function(data) {
                  if (data == "1") {
                    layer.close(index);
                    nickNutrition.util.pup_tip('用户信息修改成功',1);
                    nickNutrition.systemUser.show_system_user_manage();
                  } else {
                    nickNutrition.util.pup_tip('登录帐号已经注册',2);
                  }
                }
            });
          }
        },btn2 : function(index, layero) {
            var current_page = $("#current_page").val();
            var data = {
                param : userId
            };
            $.ajax({
                url : "rest/systemUser/system_user_reset_passwd",
                type : "POST",
                data : data,
                success : function(data) {
                  if (data == "true") {
                    layer.close(index);
                    nickNutrition.util.pup_tip('重置密码成功',1);
                    nickNutrition.systemUser.show_system_user_page(current_page);
                  }
                }
            });
        },btn3 : function(index, layero) {
            var current_page = $("#current_page").val();
            var data = {
                param : userId
            };
            $.ajax({
                url : "rest/systemUser/system_user_delete",
                type : "POST",
                data : data,
                success : function(data) {
                  if (data == "true") {
                    layer.close(index);
                    nickNutrition.util.pup_tip('删除管理员成功',1);
                    nickNutrition.systemUser.show_system_user_page(current_page);
                  }
                }
            });
        }
      });
    });
  }
</script>
<style> 
.a_hover:hover{color: blue;text-decoration: none;cursor:pointer;}
</style> 
<input id="current_page" type="hidden" value="${current_page}"/>
<input id="max_page" type="hidden" value="${max_page}" />
<div id="system_user_table" class="system_user_table">
<div class="button_table">
    <button class="common_button" onclick="nickNutrition.systemUser.add_system_user(id);">创建管理员</button>
</div>

<div style="width:100%;text-align:center;height:30px;line-height:30px;">管理员列表</div>
  <table class="common_border_table" width="90%">
    <thead>
      <tr>
        <th>序号</th>
        <th>权限</th>
        <th>账户名称</th>
        <th>初始密码</th>
        <th>备注</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="obj"  items="${cause}" varStatus="parameter">
              <tr class="a_hover" ondblclick="edit_system_user(${obj.id})">
                <td>
                    ${parameter.count}
                </td>
                <td>
                  <c:if test="${obj.role == 1}">系统管理员</c:if>
                  <c:if test="${obj.role == 2}">总监</c:if>
                  <c:if test="${obj.role == 3}">文案</c:if>
                </td>
                <td>
                    ${obj.loginId}
                </td>
                <td>
                    ${obj.passwd}
                </td>
                <td>
                    ${obj.note}
                </td>
              </tr>
    </c:forEach>
    </tbody>
  </table>
  
  <div class="user_manage_page_div">
    <div id="user_manage_page_gigantic" class="gigantic pagination">
        <a href="#" class="first" data-action="first">&laquo;</a>
        <a href="#" class="previous" data-action="previous">&lsaquo;</a>
        <input type="text" readonly />
        <a href="#" class="next" data-action="next">&rsaquo;</a>
        <a href="#" class="last" data-action="last">&raquo;</a>
    </div>
  </div>
</div>
<script>
    var current_page = $("#current_page").val();
    var max_page = $("#max_page").val();
    $('#user_manage_page_gigantic').jqPagination({
        current_page : current_page,
        max_page    : max_page,
        paged       : function(page) {
            nickNutrition.systemUser.show_system_user_page(page);
        }
    });
</script>