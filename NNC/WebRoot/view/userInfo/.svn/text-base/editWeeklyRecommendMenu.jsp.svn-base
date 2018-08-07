<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="bts" uri="/bts-tags" %>
<input id="chose_date" value="${chose_date}" type="hidden">
<div class="common_layer_frame">
    <form id="edit_weekly_recommend_menu_from" method="post">
        <input class="common_layer_td_right_input" name="id" value="${weekly_recommend.id}" type="hidden"/>
        <input class="common_layer_td_right_input" name="userInfoId" value="${weekly_recommend.userInfoId}" type="hidden"/>
        <input class="common_layer_td_right_input" name="weekCount" value="${weekly_recommend.weekCount}" type="hidden"/>
        <input class="common_layer_td_right_input" name="yyyy" value="${weekly_recommend.yyyy}" type="hidden"/>
        <input class="common_layer_td_right_input" name="sport" value="${weekly_recommend.sport}" type="hidden"/>
        <input class="common_layer_td_right_input" name="createId" value="${weekly_recommend.createId}" type="hidden"/>
        
        <div class="common_layer_tr_div" style="height: 70px;position:relative"">
          <label class="common_layer_td_left_label" style="position:absolute;left:20;top:10px">每周饮食指导：</label>
          <textarea class="common_layer_td_right_input" name="menu" rows="3" style="width:400px;padding:5px 10px;height: 80px;position:absolute;left:120px;top:20px">${weekly_recommend.menu}</textarea>
        </div>
    </form>
</div>