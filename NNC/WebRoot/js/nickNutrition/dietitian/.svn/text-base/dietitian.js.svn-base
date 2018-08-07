$(function() {nickNutrition.dietitian = {
  show_dietitian_manage : function() {
    nickNutrition.util.bset_ajax({
      url:"rest/dietitian/dietitian_manage",
      target:".default_content_right"
    });
  },
  
  show_dietitian_page : function(page) {
    var data = {
        page : page
    };
    nickNutrition.util.bset_ajax({
      url : 'rest/dietitian/dietitian_page',
      data : data,
      target : ".default_content_right"
    });
  },
  
  add_dietitian : function() {
    $.post('rest/dietitian/add_dietitian', {
    }, function(str) {
      layer.open({
        title : "用户信息",
        type : 1,
        shift : 4,
        area : ['420px','420px'],
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
                url : "rest/dietitian/dietitian_save",
                type : "POST",
                data : $("#add_dietitian_from").serialize(),
                success : function(data) {
                  if (data == "1") {
                    layer.close(index);
                    nickNutrition.util.pup_tip('用户信息保存成功',1);
                    nickNutrition.dietitian.show_dietitian_manage();
                  } else {
                    nickNutrition.util.pup_tip('营养师姓名重复或者登录帐号重复',2);
                  }
                }
            });
          }
        }
      });
    });
  },
  
  check_dietitian_add_from : function(){
    var loginId = $(".common_layer_frame input[name='loginId']").val();
    if($.trim(loginId) == ''){
      nickNutrition.util.pup_tip("请输入登录帐号 ",2);
      return false;
    }
    if(!(/^1[34578]\d{9}$/.test(loginId))){
        nickNutrition.util.pup_tip("不是正确的11位手机号", 2);
        return false;
    }
    var name = $(".common_layer_frame input[name='name']").val();
    if($.trim(name) == ''){
      nickNutrition.util.pup_tip("请输入营养师名称 ",2);
      return false;
    }
    return true;
  }
};});