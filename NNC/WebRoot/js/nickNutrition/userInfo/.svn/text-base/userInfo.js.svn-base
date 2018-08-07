$(function() {nickNutrition.userInfo = {
    user_info_on_dietitian_manange : function(dietitianId) {
      var data = {
          dietitianId : dietitianId
      };
      nickNutrition.util.bset_ajax({
        url: "rest/user_Info/user_info_on_dietitian_manange",
        data : data,
        target:".default_content_right"
      });
    },
    
    show_user_info_page_on_dietitian : function(dietitianId, userStatus, page) {
      var data = {
          dietitianId : dietitianId,
          userStatus : userStatus,
          page : page
      };
      nickNutrition.util.bset_ajax({
        url : "rest/user_Info/user_info_page_on_dietitian",
        data : data,
        target : ".default_content_right"
      });
    },
    
    show_user_info_manage : function() {
        nickNutrition.util.bset_ajax({
            url:"rest/user_Info/user_info_manage",
            target:".default_content_right"
        });
    },
    
    show_user_info_page :function(page, userStatus, dietitianId, message) {
        var data = {
            page : page,
            userStatus : userStatus,
            dietitianId : dietitianId,
            message : message
        };
        nickNutrition.util.bset_ajax({
          url : "rest/user_Info/user_info_page",
          data : data,
          target : "#user_info_list_table"
        });
    },
    
    show_user_info : function(userInfoId) {
      $.post('rest/user_Info/show_user_info', {
        userInfoId : userInfoId
      }, function(str) {
        layer.open({
          title : "用户信息",
          type : 1,
          shift : 4,
         /* area : ['70%','90%'],*/
          area : ['1000px','850px'],
          offset: '10px',
          skin : 'layui-layer-rim userInfoBox',
          content : str,
          closeBtn : 1,
          shadeClose : false
        /*  btn : [ '关闭' ]*/
        });
      });
    },
    
    edit_start_date : function(userInfoId, startDate) {
      var data = {
          userInfoId : userInfoId,
          startDate : startDate
      };
      nickNutrition.util.bset_ajax({
        url : "rest/user_Info/edit_start_date",
        data : data,
        target : "#user_info_id"
      });
    },
    
    edit_end_date : function(userInfoId, endDate) {
      var data = {
          userInfoId : userInfoId,
          endDate : endDate
      };
      nickNutrition.util.bset_ajax({
        url : "rest/user_Info/edit_end_date",
        data : data,
        target : "#user_info_id"
      });
    },
    
    edit_user_comments : function(userInfoId, date) {
      $.post('rest/user_Info/edit_user_comments', {
          userInfoId : userInfoId,
          date : date
      }, function(str) {
        layer.open({
          title : "营养师点评编辑",
          type : 1,
          shift : 4,
          area : ['630px','200px'],
          offset: '80px',
          skin : 'layui-layer-rim',
          content : str,
          closeBtn : 1,
          shadeClose : false,
          btn : [ '保存' , '取消' ],
          yes : function(index, layero){
            nickNutrition.util.bset_ajax({
                url : "rest/user_Info/edit_user_comments_save",
                type : "POST",
                data : $("#edit_user_comments_from").serialize(),
                target : "#user_data_id_div"
            });
            layer.close(index);
          }
        });
      });
    },
    
    edit_weekly_recommend_sport : function(userInfoId, date) {
      $.post('rest/user_Info/edit_weekly_recommend_sport', {
        userInfoId : userInfoId,
        date : date
      }, function(str) {
        layer.open({
          title : "每周运动指导编辑",
          type : 1,
          shift : 4,
          area : ['630px','200px'],
          offset: '80px',
          skin : 'layui-layer-rim',
          content : str,
          closeBtn : 1,
          shadeClose : false,
          btn : [ '保存' , '取消' ],
          yes : function(index, layero){
            var chose_date = $("#chose_date").val();
            nickNutrition.util.bset_ajax({
                url : "rest/user_Info/edit_weekly_recommend_save",
                type : "POST",
                data : $("#edit_weekly_recommend_sport_from").serialize() + "&chose_date="+chose_date,
                target : "#weekly_recommend_div_id"
            });
            layer.close(index);
          }
        });
      });
    },
    
    edit_weekly_recommend_menu : function(userInfoId, date) {
      $.post('rest/user_Info/edit_weekly_recommend_menu', {
        userInfoId : userInfoId,
        date : date
      }, function(str) {
        layer.open({
          title : "每周运动指导编辑",
          type : 1,
          shift : 4,
          area : ['630px','200px'],
          offset: '80px',
          skin : 'layui-layer-rim',
          content : str,
          closeBtn : 1,
          shadeClose : false,
          btn : [ '保存' , '取消' ],
          yes : function(index, layero){
            var chose_date = $("#chose_date").val();
            nickNutrition.util.bset_ajax({
                url : "rest/user_Info/edit_weekly_recommend_save",
                type : "POST",
                data : $("#edit_weekly_recommend_menu_from").serialize() + "&chose_date="+chose_date,
                target : "#weekly_recommend_div_id"
            });
            layer.close(index);
          }
        });
      });
    },
    edit_note : function(userInfoId) {
        $.post('rest/user_Info/edit_user_note', {
          userInfoId : userInfoId
        }, function(str) {
          layer.open({
            title : "备注",
            type : 1,
            shift : 4,
            area : ['630px','200px'],
            offset: '80px',
            skin : 'layui-layer-rim',
            content : str,
            closeBtn : 1,
            shadeClose : false,
            btn : [ '保存' , '取消' ],
            yes : function(index, layero){
              var note = $("#user_info_note").val();
              var userInfoid =  $("#user_info_note_id").val();
              var data = {
                      userInfoId : userInfoId,
                      note : note
                  };
              nickNutrition.util.bset_ajax({
                  url : "rest/user_Info/edit_user_note_save",
                  type : "POST",
                  data : data,
                  target : "#user_info_id"
              });
              layer.close(index);
            }
          });
        });
      },
    select_user_data : function(userInfoId, date) {
      var data = {
          userInfoId : userInfoId,
          date : date
      };
      nickNutrition.util.bset_ajax({
        url : "rest/user_Info/select_user_data",
        data : data,
        target : "#user_data_id_div"
      });
    },
    
    select_weekly_recommend : function(userInfoId, date) {
      var data = {
          userInfoId : userInfoId,
          date : date
      };
      nickNutrition.util.bset_ajax({
        url : "rest/user_Info/select_weekly_recommend",
        data : data,
        target : "#weekly_recommend_div_id"
      });
    },
    
    select_user_weight_data : function(userInfoId, date) {
        var data = {
            userInfoId : userInfoId,
            date : date
        };
        nickNutrition.util.bset_ajax({
          url : "rest/user_Info/select_user_weight_data",
          data : data,
          target : "#user_weight_data_div_id"
        });
    },
    
    change_user_dietitian : function(userInfoId, dietitianId) {
        var current_page = $("#current_page").val();
        $.post('rest/user_Info/change_user_dietitian', {
            userInfoId : userInfoId,
            dietitianId : dietitianId,
            current_page : current_page
        }, function(str) {
          layer.open({
            title : "添加/修改营养师",
            type : 1,
            shift : 4,
            area : ['250px','150px'],
            offset: '80px',
            skin : 'layui-layer-rim',
            content : str,
            closeBtn : 1,
            shadeClose : false,
            btn : [ '保存' , '取消' ],
            yes : function(index, layero){
              var dietitianId = $("#change_user_dietitian").val();
              var userInfoId = $("#user_info_id").val();
              var currentPage = $("#tmp_current_page").val();
              var data = {
                  dietitianId : dietitianId,
                  userInfoId : userInfoId,
                  currentPage :currentPage
              };
              nickNutrition.util.bset_ajax({
                  url : "rest/user_Info/change_user_dietitian_save",
                  type : "POST",
                  data : data,
                  target : "#user_info_list_table"
              });
              layer.close(index);
            }
          });
        });
    },
    
    select_user_info : function(userStatus, dietitianId) {
        var data = {
            userStatus : userStatus,
            dietitianId : dietitianId
        };
        nickNutrition.util.bset_ajax({
          url : "rest/user_Info/select_user_info",
          data : data,
          target : "#user_info_list_table"
        });
      }
};});