<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        document.getElementById('work_time').onclick = function(){
          laydate({elem: this, istime: false, format: 'YYYY-MM-DD'});
        };
    });
</script>
<input id="current_tmp_page" type="hidden" value="${current_page}"/>
<div class="common_layer_frame" style="width: 200px;">
    <input id="dietitian_id" value="${dietitian_id}" type="hidden">
    <div class="common_layer_tr_div" style="width: 200px;">
      <input class="common_layer_td_right_input" style="width: 200px;" id="work_time" value="${work_time}" type="text"/>
    </div>
</div>