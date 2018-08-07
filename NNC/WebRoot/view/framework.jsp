<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" href="css/nickNutrition.css"  type="text/css"/>
<link rel="stylesheet" href="css/style.css"  type="text/css" />
<link rel="stylesheet" href="js/tableCJ/firstCJ.css" type="text/css"/>
<link rel="stylesheet" href="js/jquery-ui/jquery-ui.min.css" type="text/css"/>
<link rel="stylesheet" href="js/jqPagination/css/jqpagination.css" type="text/css"/>
<link rel="stylesheet" href="js/layui/css/layui.css" type="text/css"/>
<link rel="stylesheet" href="js/laydate/need/laydate.css" type="text/css"/>
<title>尼基营养</title>

<!-- Javascript -->
<script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="js/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jquery.form.min.js"></script>
<script type="text/javascript" src="js/default-util.js"></script>
<script type="text/javascript" src="js/nickNutrition/framework/framework.js"></script>
<script type="text/javascript" src="js/tableCJ/firstCJ.js"></script>
<script type="text/javascript" src="js/jqPagination/js/jquery.jqpagination.js"></script>
<script type="text/javascript" src="js/layer/layer.js"></script>
<script type="text/javascript" src="js/nickNutrition/systemUser/systemUser.js"></script>
<script type="text/javascript" src="js/validate/jquery.validate.js"></script>
<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/layui/layui.js"></script>
<script type="text/javascript" src="js/echarts/echarts-all.js"></script>
<script type="text/javascript" src="js/laydate/laydate.js"></script>


<style type="text/css">
.bd{
    background-color:#0aa2fd;
    color: #ffffff  !important;
}
</style>
<script type="text/javascript">
$(function() {
    var system_user_flag = "${system_user_flag}";
    if (system_user_flag == 'true') {
        nickNutrition.systemUser.edit_user_passwd("${system_user.getId()}");
    }

    $("ul li a").eq(0).addClass("bd");
    $("ul li a").click(function(){
       //有图片的移除图片样式
       //背景动态切换
        $("ul li a").removeClass("bd");
        $("ul li a").eq($(" li a").index(this)).addClass("bd");
    });
    
    $("ul li a").eq(0).click();
    
    $(".fwallbox").click(function(){
    	$(".fwallhide").toggle();	
    });
});
</script>
</head>
    <body class="default_body" >
        <div class="default_content_div">
            <div class="default_content_head">
                <div class="default_content_company">
                    <img alt="" src="images/ncclogo.png" width="36" height="36" class="ncc_logo">
                    <span class="default_company_name_boco">尼基营养</span>
                    <span class="default_company_name_title">管理平台</span>
                </div>
                <div class="default_content_user">
                   <div class="fwdate">
	                   <span id="user_date_info"></span>
                   </div>
                   <div class="content_user_info  fwallbox">
                        <span>Hi,${system_user.getLoginId()}</span>
                        <div class="fwallhide">
                        		<div class="fwallhead">
                        			<label class="user_quit" onclick="nickNutrition.util.user_quit()">
				                        <span>退出</span>
				                    </label> 
				                    <span class="fwalline"></span>
                        			<label class="updatePass" onclick="$.cachedScript('js/nickNutrition/systemUser/systemUser.js').done(function(){nickNutrition.systemUser.edit_user_passwd(${system_user.getId()});});">
				                        <span>修改密码</span>
				                    </label>	
				                    
				                     
                        		</div>
                        		<div class="fwallcontent">
                        			<img src="images/userimg.png" width="60" height="60" style="margin-right:10px;"/>超级管理员
                        		</div>
                        </div>
                    </div>
                   
                    
                </div>
            </div>
            <div class="default_content_body">
                <div class="default_content_left" id="main_left">
                    <div class="list">
                        <ul class="menu">
                          <c:if test="${system_user.getRole() == 1}">
                            <li>
                              <a href="javascript:" class="bar_div" onclick="$.cachedScript('js/nickNutrition/systemUser/systemUser.js').done(function(){nickNutrition.systemUser.show_system_user_manage();});">账号管理
                              </a>
                            </li>
                          </c:if>
                          <li>
                            <a href="javascript:" class="bar_div" onclick="$.cachedScript('js/nickNutrition/dietitian/dietitian.js').done(function(){nickNutrition.dietitian.show_dietitian_manage();});">
                              <span>营养师管理</span>
                            </a>
                          </li>
                          <li>
                            <a href="javascript:" class="bar_div" onclick="$.cachedScript('js/nickNutrition/userInfo/userInfo.js').done(function(){nickNutrition.userInfo.show_user_info_manage();});">
                              <span>客户管理</span>
                            </a>
                          </li>
                        </ul>
                    </div>
                </div>
                <div class="default_content_center2"></div>
                <div class="default_content_right">
                </div>
            </div>
        </div>
    </body>
</html>