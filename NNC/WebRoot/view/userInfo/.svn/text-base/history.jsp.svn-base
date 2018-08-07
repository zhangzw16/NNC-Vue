<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="bts" uri="/bts-tags" %>
<script type="text/javascript">
function show_histroy_user_info (dietPhaseInfoId) {
    $.post('rest/user_Info/show_histroy_user_info', {
      dietPhaseInfoId : dietPhaseInfoId
    }, function(str) {
      layer.open({
        title : "信息",
        type : 1,
        shift : 4,
        area : ['1000px','600px'],
        offset: '80px',
        skin : 'layui-layer-rim',
        content : str,
        closeBtn : 1,
        shadeClose : false
      });
    });
}
</script>
<style> 
.a_hover:hover{color: blue;text-decoration: none;cursor:pointer;}
.a_background{background-color:#1E90FF;color: #ffffff;}
</style> 
<div>
  <div style="margin-left: 20px;">
        <p id="user_info_name" style="text-align: left;font-size: 14px;">客户名称<span style="color:#5bb3f6;margin-left:25px;">${user_info.name}</span></p>
  </div>
  <table class="common_border_table" width="90%">
    <thead>
      <tr>
        <th>序号</th>
        <th>第几期</th>
        <th>时间段</th>
        <th>详细</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="obj"  items="${dietPhaseInfos}" varStatus="parameter">
          <tr>
            <td>
                ${parameter.count}
            </td>
            <td>
                ${obj.phaseCount}
            </td>
            <td>
                <bts:helper methodName="dateFormat1" className="cn.com.bocosoft.helper.CommonHelper" params="${obj.startDate}">
                    ${date_format}
                </bts:helper>
                ~
                <bts:helper methodName="dateFormat1" className="cn.com.bocosoft.helper.CommonHelper" params="${obj.endDate}">
                    ${date_format}
                </bts:helper>
            </td>
            <td class="a_hover" onclick="show_histroy_user_info(${obj.id})">查看</td>
          </tr>
    </c:forEach>
    </tbody>
  </table>
</div>