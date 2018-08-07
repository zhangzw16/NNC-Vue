<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="bts" uri="/bts-tags"%>
<script type="text/javascript">
    document.getElementById('user_data_date').onclick = function(){
        laydate({
          elem: this,
          istime: false,
          format: 'YYYY-MM-DD',
          choose: function(dates){ //选择好日期的回调
              nickNutrition.userInfo.select_user_data("${user_info.id}", dates);
          }
        });
    };
    
    function prevFun(){
        var date = $("#user_data_date").val();
        var date2 = new Date(new Date(date).getTime()-86400000);
        var Y = date2.getFullYear()+"-";
        var M = (date2.getMonth()+1 < 10 ? "0"+(date2.getMonth()+1):date2.getMonth()+1)+"-";
        var D = date2.getDate();
        var newDate = Y+M+D;
        //判断年份，
        nickNutrition.userInfo.select_user_data("${user_info.id}", newDate);
    }
    function nextFun(){
        var date = $("#user_data_date").val();
        var date2 = new Date(new Date(date).getTime()+86400000);
        var Y = date2.getFullYear()+"-";
        var M = (date2.getMonth()+1 < 10 ? "0"+(date2.getMonth()+1):date2.getMonth()+1)+"-";
        var D = date2.getDate();
        var newDate = Y+M+D;
        nickNutrition.userInfo.select_user_data("${user_info.id}", newDate);
    
    }
    /*$(".delImg").click(function(){
        
        alert($(".delImg").value);
        $(this).parent(".imgbox").remove();
    });*/
    
    function deleteImg(userData2Id){
        var data = {
            userData2Id : userData2Id
        };
        nickNutrition.util.bset_ajax({
            url : "rest/user_Info/delete_user_photo",
            data : data,
            target : "#user_data_id_div"
        });
    }

    function showImg(obj){
        var url = $(obj).attr("src");
        $("body").append("<div class='imgboxbg'><div class='showImgBig'><img src='"+url+"'/></div><i class='closeBg' onclick='closeBg()'></i></div>");
        
    }
    function closeBg(){
        $(".imgboxbg").remove();
    }
</script>
<style>
.imgboxbg{
    width:100%;
    height:100%;
    position:fixed;
    top:0;
    left:0;
    z-index:1111111111111;
    background:rgba(51,51,51,.5);
}
.showImgBig{
    width:800px;
    height:500px;
    position:absolute;
    top:50%;
    margin-top:-250px;
    left:50%;
    margin-left:-400px;
    text-align:center
}
.showImgBig>img{height:100%;width:auto;max-width:1000px;}
.closeBg{
    position: absolute;
    top: 30px;
    right: 30px;
    width: 30px;
    height: 30px;
    background: url(images/close1.png) center no-repeat #fff;
    background-size: 100%;
    border-radius: 50%;
    border: 1px solid #d1d1d1;
    cursor: pointer
}

.imgbox {
    position: relative;
    width: 74px;
    height: 64px;
    border: 1px solid #d1d1d1;
    float: left;
    margin-right: 20px;
}

.t2>.imgbox:first-child {
    margin-left: 16px;
}

.imgbox>img {
    width: 100%;
    height: 100%;
}

.delImg {
    position: absolute;
    top: -10px;
    right: -10px;
    width: 20px;
    height: 20px;
    background: url(images/close2.png) center no-repeat #fff;
    background-size: 100%;
    border-radius: 50%;
    border: 1px solid #f00;
    cursor: pointer
}
.datebox{
    
}
.datebox>.date{
    width:150px;
    float:left;
    overflow:hidden;
    margin-left:24px;
}
.datePrev,.dateNext{
    width:22px;
    height:22px;
    text-align:center;
    line-height:22px;
    font-weight:700;
    float:left;
    margin:18px 0px;
    cursor:pointer;
    border-radius:50%;
    
}
.datePrev{
    background:url(./images/prev.png) center no-repeat 
}
.dateNext{
    background:url(./images/next.png) center no-repeat 
}
.datePrev:hover{
    background:url(./images/prev.png) center no-repeat #f6f6f6
}
.dateNext:hover{
    background:url(./images/next.png) center no-repeat #f6f6f6
  }
</style>
<div id="user_data_id_div">
    <div style="margin-left: 20px;">
        <p id="user_info_name" style="text-align: left; font-size: 14px;">
            客户名称
            <span style="color: #5bb3f6; margin-left: 25px;">${user_info.name}</span>
        </p>
        <div class="datebox">
            <div class="datePrev" onclick="prevFun()"></div>
            <div class="date">
                <input id="user_data_date" class="laydate-icon" value="${chose_date}">
            </div>
            <div class="dateNext" onclick="nextFun()"></div>
        </div>
    </div>
    <table class="dataBox">
        <thead>
            <tr class="data_tr">
                <th class="b2">
                    早餐
                </th>
                <th class="b2">
                    午餐
                </th>
                <th class="b2">
                    晚餐
                </th>
            </tr>
        </thead>
        <tbody>
            <tr class="msg_tr">
                <td class="t2">
                    ${user_data.breakfast}
                </td>
                <td class="t2">
                    ${user_data.lunch}
                </td>
                <td class="t2">
                    ${user_data.dinner}
                </td>
            </tr>
            <tr class="photo_tr">
                <td class="t2">
                    <c:forEach items="${breakfastPhotos}" var='obj'>
                        <p class="imgbox">
                            <img src="${obj.filePath}" onclick="showImg(this)"/>
                            <i class="delImg" onclick="deleteImg(${obj.id})"></i>
                        </p>
                    </c:forEach>
                </td>
                <td class="t2">
                    <c:forEach items="${lunchPhotos}" var='obj'>
                        <p class="imgbox">
                            <img src="${obj.filePath}" onclick="showImg(this)"/>
                            <i class="delImg" onclick="deleteImg(${obj.id})"></i>
                        </p>
                    </c:forEach>
                </td>
                <td class="t2">
                    <c:forEach items="${dinnerPhotos}" var='obj'>
                        <p class="imgbox">
                            <img src="${obj.filePath}" onclick="showImg(this)"/>
                            <i class="delImg" onclick="deleteImg(${obj.id})"></i>
                        </p>
                    </c:forEach>
                </td>
            </tr>
        </tbody>
    </table>
    <div class="dataBoxTwo">
        <div class="sumbit_div">
            <div class="d">
                <span class="fl">今天的运动</span>
                <span class="c">${user_data.exercise}</span>
            </div>

            <!--
              <div class="d">今天的运动</div>
              <div class="c">${user_data.exercise}</div>
            -->
        </div>
        <div class="sumbit_div">
            <div class="d">
                <span class="fl">饮水量</span>
                <span class="c">${user_data.drankWater}(ml)</span>
            </div>
        </div>
        <div class="sumbit_div">
            <div class="d">
                <span class="fl">舒适度</span>
                <span class="c">${user_data.comfortLevel}</span>
            </div>
        </div>
        <div class="sumbit_div">
            <div class="d">
                <span class="fl">试纸等级</span>
                <span class="c">${user_data.testPaperValue}</span>
            </div>
        </div>
        <div class="sumbit_div" style="border: none">
            <div class="d">
                <span class="fl">体重</span>
                <span class="c">${user_data.weight}(kg)</span>
            </div>
        </div>
    </div>
    <div class="dietitianBox">
        <div class="sumbit_div" style="position: relative">
            <span class="d">营养师点评：</span>
            <button class="dietitionEdit"
                onclick="nickNutrition.userInfo.edit_user_comments(${user_info.id}, $('#user_data_date').val())"></button>
        </div>
        <div class="sumbit_div" style="height: 159px; border: none">
            <div>
                ${user_data.comments}
            </div>
        </div>
    </div>
</div>