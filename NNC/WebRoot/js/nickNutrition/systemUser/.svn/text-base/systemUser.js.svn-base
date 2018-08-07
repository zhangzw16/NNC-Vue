$(function() {nickNutrition.systemUser = {
  show_system_user_manage : function() {
    nickNutrition.util.bset_ajax({
      url:"rest/systemUser/manage",
      target:".default_content_right"
    });
  },
  
  show_system_user_page : function(page) {
    var data = {
        page : page
    };
    nickNutrition.util.bset_ajax({
      url : 'rest/systemUser/system_user_page',
      data : data,
      target : ".default_content_right"
    });
  },

  add_system_user : function() {
    $.post('rest/systemUser/add_system_user', {
    }, function(str) {
      layer.open({
        title : "创建管理员",
        type : 1,
        shift : 4,
        area : ['420px','360px'],
        offset: '80px',
        skin : 'layui-layer-rim',
        content : str,
        closeBtn : 1,
        shadeClose : false,
        btn : [ '保存' , '取消' ],
        yes : function(index, layero){
          var falg = nickNutrition.systemUser.check_system_user_add_from();
          if (falg) {
            $.ajax({
                url : "rest/systemUser/system_user_save",
                type : "POST",
                data : $("#add_system_user_from").serialize(),
                success : function(data) {
                  if (data == "1") {
                    layer.close(index);
                    nickNutrition.util.pup_tip('用户信息保存成功',1);
                    nickNutrition.systemUser.show_system_user_manage();
                  } else {
                    nickNutrition.util.pup_tip('登录帐号已经注册',2);
                  }
                }
            });
          }
        }
      });
    });
  },
  
  edit_user_passwd : function(userId) {
    $.post('rest/systemUser/edit_user_passwd', {
      param : userId
    }, function(str) {
      layer.open({
        title : "密码修改",
        type : 1,
        shift : 4,
        area : ['420px','360px'],
        offset: '80px',
        skin : 'layui-layer-rim',
        content : str,
        closeBtn : 1,
        shadeClose : false,
        btn : [ '保存' , '取消' ],
        yes : function(index, layero){
          var new_password = $("#edit_user_new_passwd").val();
          var old_passwd = $("#edit_user_first_passwd").val();
          var same_passwd = $("#edit_user_same_passwd").val();
          var passwd = $("#system_user_passwd").val();
          var reg = new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/);
          if (!reg.test(new_password) || (new_password.length) != 8){
              nickNutrition.util.pup_tip('密码格式不正确',2);
          } else if (same_passwd != new_password){
              nickNutrition.util.pup_tip('两次输入的密码不一致',2);
          } else {
            nickNutrition.util.bset_ajax({
                url : "rest/systemUser/edit_user_passwd_save",
                type : "POST",
                data : $("#edit_user_passwd_from").serialize()
            });
            layer.close(index);
            nickNutrition.util.pup_tip('密码成功',1);
          }
        }
      });
    });
  },
  
  check_user_passwd : function() {
    var old_passwd = $("#edit_user_first_passwd").val();
    var passwd = $("#system_user_passwd").val();
    var flag = $("#system_user_flag").val();
    var data = {
        flag : flag,
        passwd : passwd,
        old_passwd : old_passwd
    };
    $.ajax({
      url : "rest/systemUser/check_user_passwd",
      type : "POST",
      data : data,
      success : function(data) {
        if (data == "false") {
          nickNutrition.util.pup_tip('原始密码不正确',2);
        }
      }
    });
  },
  
  check_system_user_add_from : function(){
    var loginId = $(".common_layer_frame input[name='loginId']").val();
    if($.trim(loginId) == ''){
      nickNutrition.util.pup_tip("请输入账户名称",2);
      $(".common_layer_frame input[name='loginId']").focus();
      return false;
    }
    return true;
  }
};});