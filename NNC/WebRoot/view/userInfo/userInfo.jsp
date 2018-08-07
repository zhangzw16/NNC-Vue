<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="bts" uri="/bts-tags" %>
<script type="text/javascript">
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        document.getElementById('start_date').onclick = function(){
          laydate({
            elem: this,
            istime: false,
            format: 'YYYY-MM-DD',
            choose: function(dates){ //选择好日期的回调
                var tmpId = $("#dietitian_id").val();
                if (tmpId != null && $.trim(tmpId) != "") {
                    nickNutrition.userInfo.edit_start_date("${user_info.id}", dates);
                } else {
                    nickNutrition.util.pup_tip('未分配营养师',2);
                }
                
            }
          });
          
        };
        
        document.getElementById('end_date').onclick = function(){
          laydate({
            elem: this,
            istime: false,
            format: 'YYYY-MM-DD',
            choose: function(dates){ //选择好日期的回调
            var tmpId = $("#dietitian_id").val();
                if (tmpId != null && $.trim(tmpId) != "") {
                    nickNutrition.userInfo.edit_end_date("${user_info.id}", dates);
                } else {+
                    nickNutrition.util.pup_tip('未分配营养师',2);
                }
            }
          });
        };
    });
</script>
<div id="user_info_id" style="position:relative">
    <div style="margin-left: 30px;">
        <p id="user_info_name" style="text-align: left;font-size: 14px;">客户名称：<span style="color:#5bb3f6"> ${user_info.name}</span></p>
    </div>
    <input id="dietitian_id" value="${user_info.dietitianId} " type="hidden">
    <table class="window_common_table basemsg">
      <tbody>
        <tr>
            <td class="b">手机号</td>
            <td class="t">
                <c:if test="${user_info.userLoginInfoId == null}">无</c:if>
                <c:if test="${user_info.userLoginInfoId != null}">
                  <bts:helper methodName="getUserLoginId" className="cn.com.bocosoft.helper.CommonHelper" params="${user_info.userLoginInfoId}">
                      ${login_id}
                  </bts:helper>
                </c:if>
            </td>
        </tr>
        <tr>
            <td class="b">出生年月</td>
            <td class="t">
                <bts:helper methodName="dateFormat1" className="cn.com.bocosoft.helper.CommonHelper" params="${user_info.birthday}">
                    ${date_format}
                </bts:helper>
            </td>
        </tr>
        <tr>
            <td class="b">性别</td>
            <td class="t">
                <c:if test="${user_info.sex == 1}">男</c:if>
                <c:if test="${user_info.sex == 2}">女</c:if>
            </td>
        </tr>
        <tr>
            <td class="b">身高</td>
            <td class="t">${user_info.height}(cm)</td>
        </tr>
        <tr>
            <td class="b">体重</td>
            <td class="t">${user_info.weight}(kg)</td>
        </tr>
      </tbody>
    </table>
    <c:if test="${user_info.addFlag == 1}">
    <table class="window_common_table bodymsg">
      <tbody>
        <tr>
            <td class="b">血压</td>
            <td class="t">
                <c:if test="${user_info.bloodPressure == 1}">偏低</c:if>
                <c:if test="${user_info.bloodPressure == 2}">正常</c:if>
                <c:if test="${user_info.bloodPressure == 3}">偏高</c:if>
            </td>
        </tr>
        <tr>
            <td class="b">血脂</td>
            <td class="t">
                <c:if test="${user_info.bloodFat == 1}">偏低</c:if>
                <c:if test="${user_info.bloodFat == 2}">正常</c:if>
                <c:if test="${user_info.bloodFat == 3}">偏高</c:if>
            </td>
        </tr>
        <tr>
            <td class="b">血糖</td>
            <td class="t">
                <c:if test="${user_info.bloodSugar == 1}">偏低</c:if>
                <c:if test="${user_info.bloodSugar == 2}">正常</c:if>
                <c:if test="${user_info.bloodSugar == 3}">偏高</c:if>
            </td>
        </tr>
        <tr>
            <td class="b">尿酸</td>
            <td class="t">
                <c:if test="${user_info.bloodUricAcid == 1}">偏低</c:if>
                <c:if test="${user_info.bloodUricAcid == 2}">正常</c:if>
                <c:if test="${user_info.bloodUricAcid == 3}">偏高</c:if>
            </td>
        </tr>
        <tr>
            <td class="b">脂肪肝</td>
            <td class="t">
                <c:if test="${user_info.hepaticAdiposeInfiltration == 1}">正常</c:if>
                <c:if test="${user_info.hepaticAdiposeInfiltration == 2}">轻度</c:if>
                <c:if test="${user_info.hepaticAdiposeInfiltration == 3}">中度</c:if>
                <c:if test="${user_info.hepaticAdiposeInfiltration == 4}">重度</c:if>
            </td>
        </tr>
      </tbody>
    </table>
    </c:if>
    <table class="window_common_table datamsg">
      <tbody>
        <tr>
            <td class="b">开始减重日期：</td>
            <td class="t">
                <c:if test="${user_info.startDate != null}">
                  <bts:helper methodName="dateFormat1" className="cn.com.bocosoft.helper.CommonHelper" params="${user_info.startDate}">
                      ${date_format}
                  </bts:helper>
                </c:if>
                <span id="start_date" class="icon_date" style="text-align: right;width:18px;height:15px;margin-left:15px;"></span>
            </td>
        </tr>
        <tr>
            <td class="b">结束减重日期：</td>
            <td class="t">
                <c:if test="${user_info.endDate != null}">
                  <bts:helper methodName="dateFormat1" className="cn.com.bocosoft.helper.CommonHelper" params="${user_info.endDate}">
                      ${date_format}
                  </bts:helper>
                </c:if>
                <span id="end_date" class="icon_date" style="text-align: right;width:18px;height:15px;margin-left:15px;"></span>
            </td>
        </tr>
      </tbody>
    </table>
	<div class="message">
        <div class="sumbit_div" style="position:relative">
            <div>
              <span class="d" style="color:#5bb24b">备注：</span>
              <button class="dietitionEdit"  onclick="nickNutrition.userInfo.edit_note(${user_info.id})"></button>
            </div>
        </div>
        <div class="sumbit_div" style="height: 160px;border-bottom:none">
            <div>${user_info.note}</div>
        </div>
    </div>

</div>