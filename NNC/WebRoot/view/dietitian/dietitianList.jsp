<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="bts" uri="/bts-tags"%>
<script type="text/javascript"
	src="js/nickNutrition/userInfo/userInfo.js"></script>
<script type="text/javascript">
    function change_dietitian_work_start_time(dietitianId) {
        var current_page = $("#current_page").val();
        $.post('rest/dietitian/change_dietitian_work_start_time', {
            param : dietitianId,
            current_page : current_page
        }, function(str) {
          layer.open({
            title : "上岗时间",
            type : 1,
            shift : 4,
            area : ['250px','170px'],
            offset: '80px',
            skin : 'layui-layer-rim',
            content : str,
            closeBtn : 1,
            shadeClose : false,
            btn : [ '保存' , '取消' ],
            yes : function(index, layero){
            var dietitian_id = $("#dietitian_id").val();
            var work_start_time = $("#work_time").val();
            var data = {
                dietitian_id : dietitian_id,
                work_start_time : work_start_time
            };
              $.ajax({
                  url : "rest/dietitian/dietitian_work_start_time_save",
                  type : "POST",
                  data : data,
                  success : function(data) {
                  var page = $("#current_tmp_page").val();
                    if (data == "1") {
                      layer.close(index);
                      nickNutrition.util.pup_tip('信息保存成功',1);
                      nickNutrition.dietitian.show_dietitian_page(page);
                    }
                  }
              });
            }
          });
        });
    }
    
    function change_dietitian_work_end_time(dietitianId) {
        var current_page = $("#current_page").val();
        $.post('rest/dietitian/change_dizetitian_work_end_time', {
            current_page : current_page,
            param : dietitianId
        }, function(str) {
          layer.open({
            title : "下岗时间",
            type : 1,
            shift : 4,
            area : ['250px','170px'],
            offset: '80px',
            skin : 'layui-layer-rim',
            content : str,
            closeBtn : 1,
            shadeClose : false,
            btn : [ '保存' , '取消' ],
            yes : function(index, layero){
            var dietitian_id = $("#dietitian_id").val();
            var work_end_time = $("#work_time").val();
            var data = {
                dietitian_id : dietitian_id,
                work_end_time : work_end_time
            };
              $.ajax({
                  url : "rest/dietitian/dietitian_work_end_time_save",
                  type : "POST",
                  data : data,
                  success : function(data) {
                  var page = $("#current_tmp_page").val();
                    if (data == "1") {
                      layer.close(index);
                      nickNutrition.util.pup_tip('信息保存成功',1);
                      nickNutrition.dietitian.show_dietitian_page(page);
                    } else if (data == "2") {
                        nickNutrition.util.pup_tip('请先移除营养师下客户',2);
                        layer.close(index);
                    }
                  }
              });
            }
          });
        });
    }
    
    
    
    function reset_dietitian_passwd(dietitian_id) {
        layer.open({
          title : "确认信息",
          content : "确认重置密码？",
          btn : [ '确认' , '取消' ],
          yes : function(index, layero){
          var data = {
              dietitian_id : dietitian_id
          };
            $.ajax({
                url : "rest/dietitian/reset_dietitian_passwd",
                type : "POST",
                data : data,
                success : function(data) {
                  if (data == "1") {
                    layer.close(index);
                    nickNutrition.util.pup_tip('重置密码成功',1);
                    nickNutrition.dietitian.show_dietitian_manage();
                  }
                }
            });
          }
        });
    }
    //修改营养师
    function update_dietitian(dietitianId) {
	    $.post("rest/dietitian/edit_dietitian_info", {
            param : dietitianId
	    }, function(str) {
	      layer.open({
	        title : "修改用户信息",
	        type : 1,
	        shift : 4,
	        area : ['420px','350px'],
	        offset: '80px',
	        skin : 'layui-layer-rim',
	        content : str,
	        closeBtn : 1,
	        shadeClose : false,
	        btn : [ '保存' , '取消' ],
	        yes : function(index, layero){
	          var falg = nickNutrition.dietitian.check_dietitian_add_from();
	          if (falg) {
	            $.ajax({
	                url : "rest/dietitian/edit_dietitian_info_save",
	                type : "POST",
	                data : $("#update_dietitian_from").serialize(),
	                success : function(data) {
	                //console.log($("#update_dietitian_from").serialize());
	                  if (data == "1") {
	                    layer.close(index);
	                    nickNutrition.util.pup_tip("用户信息保存成功",1);
	                    nickNutrition.dietitian.show_dietitian_manage();
	                  } else {
	                    nickNutrition.util.pup_tip("营养师姓名重复或者登录帐号重复",2);
	                  }
	                },
	                error:function(){
	                	console.log("失败了");
	                }
	            });
	          }
	        }
	      });
	    });
  }
</script>
<style>
.a_hover:hover {
	color: blue;
	text-decoration: none;
	cursor: pointer;
}
</style>
<input id="current_page" type="hidden" value="${current_page}" />
<input id="max_page" type="hidden" value="${max_page}" />
<div id="dietitian_table" class="dietitian_table">
	<div class="button_table">
		<button class="common_button"
			onclick="nickNutrition.dietitian.add_dietitian();">
			创建营养师
		</button>
	</div>
	<div style="width: 100%; text-align: center">
		营养师列表
	</div>
	<table class="common_border_table" width="90%">
		<thead>
			<tr>
				<th>
					序号
				</th>
				<th>
					营养师名称
				</th>
				<th>
					APP登陆账号
				</th>
				<th>
					初始密码
				</th>
				<th>
					正在减脂人数
				</th>
				<th>
					上岗时间
				</th>
				<th>
					下岗时间
				</th>
				<th>
					重置密码
				</th>
				<th>
					修改
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="obj" items="${cause}" varStatus="parameter">
				<tr class="a_hover"
					ondblclick="nickNutrition.userInfo.user_info_on_dietitian_manange(${obj.id})">
					<td>
						${parameter.count}
					</td>
					<td>
						${obj.name}
					</td>
					<td>
						${obj.loginId}
					</td>
					<td>
						${obj.passwd}
					</td>
					<td>
						<bts:helper methodName="getUsers"
							className="cn.com.bocosoft.helper.CommonHelper"
							params="${obj.id}">
                        ${user_count}
                    </bts:helper>
					</td>
					<td class="a_hover">
						<c:if test="${obj.workStartDate != null}">
							<bts:helper methodName="dateFormat1"
								className="cn.com.bocosoft.helper.CommonHelper"
								params="${obj.workStartDate}">
                            ${date_format}
                        </bts:helper>
						</c:if>
						<span id="start_date" class="icon_date"></span>
					</td>
					<td class="a_hover">
						<c:if test="${obj.workEndDate != null}">
							<bts:helper methodName="dateFormat1"
								className="cn.com.bocosoft.helper.CommonHelper"
								params="${obj.workEndDate}">
                            ${date_format}
                        </bts:helper>
						</c:if>
						<span id="start_date" class="icon_date"></span>
					</td>
					<td class="a_hover" onclick="reset_dietitian_passwd(${obj.id})"
						style="color: red">
						重置密码
					</td>
					<td onclick="update_dietitian(${obj.id});">
						修改
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="user_manage_page_div">
		<div id="user_manage_page_gigantic" class="gigantic pagination">
			<a href="#" class="first" data-action="first">&laquo;</a>
			<a href="#" class="previous" data-action="previous">&lsaquo;</a>
			<input type="text" readonly />
			<a href="#" class="next" data-action="next">&rsaquo;</a>
			<a href="#" class="last" data-action="last">&raquo;</a>
		</div>
	</div>
</div>
<script>
    var current_page = $("#current_page").val();
    var max_page = $("#max_page").val();
    $("#user_manage_page_gigantic").jqPagination({
        current_page : current_page,
        max_page : max_page,
        paged : function(page) {
            nickNutrition.dietitian.show_dietitian_page(page);
        }
    });
</script>