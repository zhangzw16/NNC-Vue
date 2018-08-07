<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="bts" uri="/bts-tags" %>
<script type="text/javascript">
    var userStatus = "${userStatus}";
    if (userStatus == 1) {
        $(".common_button").eq(0).addClass("a_background");
        $(".common_button").eq(1).removeClass("a_background");
        $(".common_button").eq(2).removeClass("a_background");
    } else if (userStatus == 2) {
        $(".common_button").eq(1).addClass("a_background");
        $(".common_button").eq(0).removeClass("a_background");
        $(".common_button").eq(2).removeClass("a_background");
    } else if (userStatus == 3) {
        $(".common_button").eq(2).addClass("a_background");
        $(".common_button").eq(0).removeClass("a_background");
        $(".common_button").eq(1).removeClass("a_background");
    }

    function user_in_unweight(dietitianId, userStatus, page) {
        nickNutrition.userInfo.show_user_info_page_on_dietitian(dietitianId, userStatus, page);

    }
    
    function user_out_unweight(dietitianId, userStatus, page) {
        nickNutrition.userInfo.show_user_info_page_on_dietitian(dietitianId, userStatus, page);

    }
    
    function user_end_unweight(dietitianId, userStatus, page) {
        nickNutrition.userInfo.show_user_info_page_on_dietitian(dietitianId, userStatus, page);
    }
    
    function show_user_info(userInfoId) {
        nickNutrition.userInfo.show_user_info(userInfoId);
    }
</script>
<style> 
</style> 
<input id="current_page" type="hidden" value="${current_page}"/>
<input id="max_page" type="hidden" value="${max_page}" />
<div id="user_info_table" class="dietitian_table uifodlist" >
  <div class="common_table">
      <label>营养师：</label>
      <span>${dietitian.name}</span>
      <label style="margin-left: 50px;">手机号：</label>
      <span>${dietitian.phoneNo}</span>
  </div>
  <div class="button_table">
      <button class="common_button" onclick="user_in_unweight(${dietitian.id}, 1, ${current_page});">正在减脂客户</button>
      <button class="common_button" onclick="user_out_unweight(${dietitian.id}, 2, ${current_page});">过渡期客户</button>
      <button class="common_button" onclick="user_end_unweight(${dietitian.id}, 3, ${current_page});">已完成客户</button>
  </div>
  <div class="common_table" style="margin-bottom:5px">
      <label>当前减脂人数：</label>
      <span>${cause.size()}</span>
  </div>
  <table class="common_border_table" width="90%">
    <thead>
      <tr>
        <th>序号</th>
        <th>客户姓名</th>
        <th>手机号</th>
        <th>减重日</th>
        <th>详细数据</th>
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
                    <c:if test="${obj.userLoginInfoId == null}">无</c:if>
                    <c:if test="${obj.userLoginInfoId != null}">
                      <bts:helper methodName="getUserLoginId" className="cn.com.bocosoft.helper.CommonHelper" params="${obj.userLoginInfoId}">
                          ${login_id}
                      </bts:helper>
                    </c:if>
                </td>
                <td>
                    <bts:helper methodName="getUserDietDays" className="cn.com.bocosoft.helper.CommonHelper" params="${obj.id}">
                        ${diet_days}
                    </bts:helper>
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
    $('#user_manage_page_gigantic').jqPagination({
        current_page : current_page,
        max_page    : max_page,
        paged       : function(page) {
            nickNutrition.dietitian.show_dietitian_page(page);
        }
    });
</script>