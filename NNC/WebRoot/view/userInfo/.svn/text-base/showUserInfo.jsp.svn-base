<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
    //注意：选项卡 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function(){
        var element = layui.element();
    });
</script>
<style>
.layui-tab-title li{
	border-bottom:4px solid #fff;
	height:32px;
	line-height:32px;
	padding:0;
	margin-right:40px;
	color:#a1a1a1;
}
.layui-tab-title li:hover{
	border-bottom:4px solid #54b1f6;
	color:#333
}
.layui-tab-title .layui-this{
	border-bottom:4px solid #54b1f6;
	background:#fff;
	color:#333
}

.layui-tab-title .layui-this:after {
   	border:none;
}

</style>
<div class="layui-tab">
  <ul class="layui-tab-title">
    <li class="layui-this" style="margin-left:20px">基本信息</li>
    <li>日常数据</li>
    <li>体重走势</li>
    <li>每周食物和运动指导</li>
    <li>历史数据</li>
  </ul>
  <div class="layui-tab-content">
    <div class="layui-tab-item layui-show">
        <jsp:include page="userInfo.jsp"></jsp:include>
    </div>
    <div class="layui-tab-item">
        <jsp:include page="userData.jsp"></jsp:include>
    </div>
    <div class="layui-tab-item">
        <jsp:include page="userWeightData.jsp"></jsp:include>
    </div>
    <div class="layui-tab-item">
        <jsp:include page="weeklyRecommend.jsp"></jsp:include>
    </div>
    <div class="layui-tab-item">
        <jsp:include page="history.jsp"></jsp:include>
    </div>
  </div>
</div>