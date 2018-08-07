<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="bts" uri="/bts-tags" %>
<div class="common_layer_frame" style="width: 200px; margin-left: 30px">
    <input id="tmp_current_page" type="hidden" value="${current_page}"/>
    <input id="user_info_id" type="hidden" value="${user_info_id}"/>
    <span>营养师：</span>
    <select class="common_select" id="change_user_dietitian" style="width:100px;margin:20px">
         <c:forEach items="${dietitian}" var='obj'>
            <option value='${obj.id}' <c:if test="${dietitian_id == obj.id}">selected="selected"</c:if>>
              ${obj.name}
            </option>
          </c:forEach>
    </select>
</div>