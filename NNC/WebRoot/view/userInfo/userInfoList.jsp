<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="bts" uri="/bts-tags" %>
<script type="text/javascript">
    $(function(){
        nickNutrition.userInfo.show_user_info_page(1,"", "");
    });

    function select_user_info() {
        var userStatus = $("input[name='userStatus']:checked").val();
        var dietitianId = $("#user_dietitian_id").val();
        var message = $("#message").val();
        nickNutrition.userInfo.show_user_info_page(1, userStatus, dietitianId, message);
    }
    $("input[name='userStatus']").click(function(){
        $(this).parent("label").addClass("ck");
        $(this).parent("label").siblings("label").removeClass("ck");
    });
   
</script>
<style>
.t>label{
    padding:5px;
    border-radius:6px;
    cursor: pointer;
}
.t>label:hover{
    background:#21c9ff;
    color:#fff;
}
.ck{background:#21c9ff;color:#fff}
.allStatus{
    float:left;
    margin-left:22px;
    font-size:14px;
}
.allStatus p{
    float:left;    
}
.status,.dietitian{
    display:none;
}
.allStatus p i{
    font-size:16px;
    cursor:pointer;
    margin-left:5px;
}

</style>
  <div id="select_table_id" class="bocosoft_body_content_div">
      <div class="common_table" style="background:#fff">
          <table class="common_border_table_info" width="100%">
            <tbody>
                <tr>
                    <td class="b" style="border-bottom:1px dotted #d1d1d1">减重状态：</td>
                    <td class="t" id="user_status_id"  style="border-bottom:1px dotted #d1d1d1">
                      <label><input name="userStatus" type="radio" value="" checked="checked" class="ck"/>全部</label>
                      <label><input name="userStatus" type="radio" value="0"/>准备期</label>
                      <label><input name="userStatus" type="radio" value="1"/>正在期</label>
                      <label><input name="userStatus" type="radio" value="2"/>过渡期</label>
                      <label><input name="userStatus" type="radio" value="3"/>完成期</label>
                    </td>
                    
                </tr>
                <tr >
                    <td class="b">营养师：</td>
                    <td class="t">
                        <select id="user_dietitian_id" style="border:none"> 
                             <c:forEach items="${dietitian}" var='obj'>
                                <option value='${obj.id}'>
                                  ${obj.name}
                                </option>
                              </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr style="border-top:1px dotted #d1d1d1">
                    <td>客户姓名或</br>联系方式：</td>
                    <td>
                        <input type="text" id="message" style="width:150px;height:32px;float:left;"/>
                    </td>
                </tr>
            </tbody>
          </table>
      </div>
      <div class="bocosoft_body_text_div" style="text-align: right;">
        <button class="common_button" style="width:50px;margin-right:50px;" onclick="select_user_info();">查询</button>
        <div class="line-bottom"></div>
      </div>
  </div>
  <div class="common_table" style="margin-top:0px;padding-top:10px">
  <!--<div class="common_table">
      <label>注册人数：</label>
      <span>${user_count}</span>
  </div>
  --><div id="user_info_list_table" class="common_table" style="margin:0px;padding:10px 0px;width:100%;">
    <jsp:include page="userInfoListTable.jsp"></jsp:include>
  </div>
