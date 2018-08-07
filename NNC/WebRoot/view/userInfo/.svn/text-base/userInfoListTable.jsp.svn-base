<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="bts" uri="/bts-tags" %>
<script type="text/javascript">
    function show_user_info(userInfoId) {
        nickNutrition.userInfo.show_user_info(userInfoId);
    }
    
    function change_user_dietitian(userInfoId) {
        var dietitianId = "${obj.dietitianId}";
        nickNutrition.userInfo.change_user_dietitian(userInfoId, dietitianId);
    }
    
    function change_user_info_agree(userInfoId) {
        var current_page = $("#current_page").val();
        var data = {
            userInfoId : userInfoId,
            currentPage :current_page
        };
        nickNutrition.util.bset_ajax({
            url : "rest/user_Info/change_user_info_agree",
            type : "POST",
            data : data,
            target : "#user_info_list_table"
        });
    }
</script>
<style> 
.a_hover:hover{color: blue;text-decoration: none;cursor:pointer;}
.a_background{background-color:#1E90FF;color: #ffffff;}
</style> 
<input id="current_page" type="hidden" value="${current_page}"/>
<input id="max_page" type="hidden" value="${max_page}" />
<input id="user_status" type="hidden" value="${user_status}" />
<input id="dietitian_Id" type="hidden" value="${dietitian_Id}" />
<div>
	<label style="padding-left:22px;font-size:14px;">客户人数：</label>
    <span style="font-size:14px;">${causeSize}</span>
  <table class="common_border_table" width="100%">
    <thead>
      <tr>
        <th>序号</th>
        <th>客户姓名</th>
        <th>注册时间</th>
        <th>指定营养师</th>
        <th>注册方式</th>
        <th>联系方式</th>
        <th>减重状态</th>
        <th>参加尼基计划</th>
        <th>购买尼基营养缓释多糖</th>
        <th>活跃度</th>
        <th>登陆系统的状态</th>
        <th>详细</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="obj"  items="${cause}" varStatus="parameter">
              <tr>
                <td>
                    ${parameter.count}
                </td>
                <td>
                    ${obj.name}
                </td>
                <td>
                    <bts:helper methodName="dateFormat1" className="cn.com.bocosoft.helper.CommonHelper" params="${obj.createTime}">
                        ${date_format}
                    </bts:helper>
                </td>
                <td class="a_hover" onclick="change_user_dietitian(${obj.id})">
                    <c:if test="${obj.dietitianId != null}">
                        <bts:helper methodName="getUserDietitian" className="cn.com.bocosoft.helper.CommonHelper" params="${obj.dietitianId}">
                            ${dietitian_name}
                        </bts:helper>
                    </c:if>
                    <c:if test="${obj.dietitianId == null}">无</c:if>
                </td>
                <td>
                    <c:if test="${obj.loginFlag == 1}">手机</c:if>
                    <c:if test="${obj.loginFlag == 2}">微信</c:if>
                    <c:if test="${obj.loginFlag == 3}">QQ</c:if>
                    <c:if test="${obj.loginFlag == 4}">微博</c:if>
                </td>
                <td>
                    <c:if test="${obj.contactWay == 0}">
                        <c:if test="${obj.userLoginInfoId != null}">
                          <bts:helper methodName="getUserLoginId" className="cn.com.bocosoft.helper.CommonHelper" params="${obj.userLoginInfoId}">
                                                                                手机号：${login_id}
                          </bts:helper>
                        </c:if>
                    </c:if>
                    <c:if test="${obj.contactWay == 1}">微信：${obj.account}</c:if>
                    <c:if test="${obj.contactWay == 2}">QQ：${obj.account}</c:if>
                </td>
                <td>
                    <c:if test="${obj.userStatus == 0}">准备期</c:if>
                    <c:if test="${obj.userStatus == 1}">正在期</c:if>
                    <c:if test="${obj.userStatus == 2}">过渡期</c:if>
                    <c:if test="${obj.userStatus == 3}">完成期</c:if>
                </td>
                <td>
                    <c:if test="${obj.addFlag == 0}">否</c:if>
                    <c:if test="${obj.addFlag == 1}">是</c:if> 
                </td>
                <td>
                    <c:if test="${obj.buyFlag == 0}">否</c:if>
                    <c:if test="${obj.buyFlag == 1}">是</c:if> 
                </td>
                <td>${obj.vitality}</td>
                <td class="a_hover" onclick="change_user_info_agree(${obj.id})">
                    <c:if test="${obj.agreeFlag == 1}"><span style="color: red;">禁止登录</span></c:if>
                    <c:if test="${obj.agreeFlag == 0}">可以登录</c:if> 
                </td>
                <td class="a_hover" onclick="show_user_info(${obj.id})">查看</td>
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
    var dietitianId = $("#dietitian_Id").val();
    var userStatus = $("#user_status").val();
    var message = $("#message").val();
    $('#user_manage_page_gigantic').jqPagination({
        current_page : current_page,
        max_page    : max_page,
        paged       : function(page) {
            nickNutrition.userInfo.show_user_info_page(page, userStatus, dietitianId, message);
        }
    });
</script>