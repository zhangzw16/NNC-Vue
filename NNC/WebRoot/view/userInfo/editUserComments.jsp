<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="bts" uri="/bts-tags" %>
<div class="common_layer_frame">
    <form id="edit_user_comments_from" method="post">
        <input class="common_layer_td_right_input" name="id" value="${user_data.id}" type="hidden"/>
        <input class="common_layer_td_right_input" name="userInfoId" value="${user_data.userInfoId}" type="hidden"/>
        <input class="common_layer_td_right_input" name="breakfast" value="${user_data.breakfast}" type="hidden"/>
        <input class="common_layer_td_right_input" name="lunch" value="${user_data.lunch}" type="hidden"/>
        <input class="common_layer_td_right_input" name="dinner" value="${user_data.dinner}" type="hidden"/>
        <input class="common_layer_td_right_input" name="exercise" value="${user_data.exercise}" type="hidden"/>
        <input class="common_layer_td_right_input" name="drankWater" value="${user_data.drankWater}" type="hidden"/>
        <input class="common_layer_td_right_input" name="comfortLevel" value="${user_data.comfortLevel}" type="hidden"/>
        <input class="common_layer_td_right_input" name="testPaperValue" value="${user_data.testPaperValue}" type="hidden"/>
        <input class="common_layer_td_right_input" name="weight" value="${user_data.weight}" type="hidden"/>
        <bts:helper methodName="dateFormat1" className="cn.com.bocosoft.helper.CommonHelper" params="${user_data.date}">
           <input class="common_layer_td_right_input" name="date" value="${date_format}" type="hidden"/>
        </bts:helper>
        <input class="common_layer_td_right_input" name="createId" value="${user_data.createId}" type="hidden"/>
        <c:if test="${user_data.createTime != null}">
          <bts:helper methodName="dateFormat1" className="cn.com.bocosoft.helper.CommonHelper" params="${user_data.createTime}">
             <input class="common_layer_td_right_input" name="createTime" value="${date_format}" type="hidden"/>
          </bts:helper>
        </c:if>
        <div class="common_layer_tr_div" style="height: 70px;position:relative">
          <label class="common_layer_td_left_label" style="position:absolute;left:20;top:10px">营养师点评：</label>
          <textarea class="common_layer_td_right_input" name="comments" rows="3" style="width:400px;padding:5px 10px;height: 80px;position:absolute;left:120px;top:20px">${user_data.comments}</textarea>
        </div>
    </form>
</div>