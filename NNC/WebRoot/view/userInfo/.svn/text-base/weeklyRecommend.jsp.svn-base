<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="bts" uri="/bts-tags" %>
<script type="text/javascript">
    document.getElementById('weekly_recommend_date').onclick = function(){
      laydate({
        elem: this,
        istime: false,
        format: 'YYYY-MM-DD',
        choose: function(dates){ //选择好日期的回调
            nickNutrition.userInfo.select_weekly_recommend("${user_info.id}", dates);
        }
      });
    };
</script>
<div id="weekly_recommend_div_id">
    <div style="margin-left: 50px;">
    <p id="user_info_name" style="text-align: left;font-size: 14px;">客户名称<span style="color:#5bb3f6;margin-left:25px;">${user_info.name}</span></p>
        <div class="date"><input id="weekly_recommend_date" class="laydate-icon" value="${chose_date}"></div>
    </div>
    <div style="width: 500px; margin:50px auto 30px;border:1px solid #d1d1d1">
        <div class="sumbit_div" style="position:relative">
            <div>
              <span class="d" style="color:#5bb24b">本周运动指导：</span>
              <button class="dietitionEdit"  onclick="nickNutrition.userInfo.edit_weekly_recommend_sport(${user_info.id}, '${chose_date}')"></button>
            </div>
        </div>
        <div class="sumbit_div" style="height: 120px;">
            <div>${weekly_recommend.sport}</div>
        </div>
    </div>
    <div style="width: 500px; margin:0 auto;border:1px solid #d1d1d1">
        <div class="sumbit_div" style="position:relative">
            <div>
              <span class="d" style="color:#5bb24b">本周饮食指导：</span>
              <button class="dietitionEdit"  onclick="nickNutrition.userInfo.edit_weekly_recommend_menu(${user_info.id}, '${chose_date}')"></button>
            </div>
        </div>
        <div class="sumbit_div" style="height: 120px;">
            <div>${weekly_recommend.menu}</div>
        </div>
    </div>
</div>