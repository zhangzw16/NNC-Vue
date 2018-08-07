<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
	.common_layer_tr_div{
		margin-bottom:18px !important;
	}
	.common_layer_td_right_input{
		width:200px !important;
	}
	.common_layer_td_left_label{
		width:147px !important;
		text-align:left !important;
		padding-left:44px !important;
	}
</style>
<script type="text/javascript">
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        
        document.getElementById('work_start_date').onclick = function(){
          laydate({elem: this, istime: false, format: 'YYYY-MM-DD'});
        };
        
        document.getElementById('work_end_date').onclick = function(){
          laydate({elem: this, istime: false, format: 'YYYY-MM-DD'});
        };
    });
    function todate(inputstr, showsplit, showweek) {
        //Wed Mar 22 13:38:37 CST 2017
        inputstr = inputstr + ""; //末尾加一个空格
        var date = "";
        var month = new Array();
        var week = new Array();
        
        month["Jan"] = 1; month["Feb"] = 2; month["Mar"] = 3; month["Apr"] = 4; month["May"] = 5; month["Jan"] = 6; 
        month["Jul"] = 7; month["Aug"] = 8; month["Sep"] = 9; month["Oct"] = 10; month["Nov"] = 11; month["Dec"] = 12;
        week["Mon"] = "一"; week["Tue"] = "二"; week["Wed"] = "三"; week["Thu"] = "四"; week["Fri"] = "五"; week["Sat"] = "六"; week["Sun"] = "日";
        
        str = inputstr.split(" ");
        
        date = str[5];
        date += showsplit + month[str[1]] + showsplit + str[2];
        if(showweek){
        date += "    " ;
        }
                
        return date;
    }


    var sdate = $("#work_start_date").val();
    var edate = $("#work_end_date").val();
    if(sdate != ""){
    	var c=todate(sdate, "-", true);
     	$("#work_start_date").val(c);
    }else{
    	$("#work_start_date").val("");
    }
    if (edate != ""){
     	var d=todate(edate, "-", true);
     	$("#work_end_date").val(d);
    }else{
    	$("#work_end_date").val("");
    }
   
    
   // alert(b); //Thu Mar 23 2017 03:38:37 GMT+0800 (中国标准时间)
   // console.log(c); //2017-3-22 星期三
   // alert(d); //2017322
  
   
  //  console.log(${dietitian});
</script>

<div class="common_layer_frame">
    <form id="update_dietitian_from" method="post">
        <input class="common_layer_td_right_input" name="id" type="hidden" value="${dietitian.id}"/>
        <input class="common_layer_td_right_input" name="createId" type="hidden" value="${dietitian.createId}" />
        <div class="common_layer_tr_div" style="margin-top:20px;">
          <label class="common_layer_td_left_label">营养师姓名</label>
          <input class="common_layer_td_right_input" name="name" type="text" value="${dietitian.name}"/>
        </div>
        <div class="common_layer_tr_div">
          <label class="common_layer_td_left_label">APP登录帐号</label>
          <input class="common_layer_td_right_input" name="loginId" type="text" placeholder="手机号码" value="${dietitian.loginId}"/>
        </div>
        <div class="common_layer_tr_div">
          <label class="common_layer_td_left_label">上岗时间</label>
          <input class="common_layer_td_right_input" id="work_start_date" name="workStartDate" type="text" value="${dietitian.workStartDate}"/>
        </div>
        <div class="common_layer_tr_div">
          <label class="common_layer_td_left_label">下岗时间</label>
          <input class="common_layer_td_right_input" id="work_end_date" name="workEndDate" type="text" value="${dietitian.workEndDate}"/>
        </div>
    </form>
</div>