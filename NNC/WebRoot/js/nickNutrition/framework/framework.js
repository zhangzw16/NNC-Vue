$(function() {
  nickNutrition.framework = {
        toggleMenu : function(index) {
            $(".default_content_left").find(".left_bar_div").hide();
            var tp = $(".default_content_left").find(".left_bar_div:eq(" + index + ")");
            tp.show();
            tp.find(".bar_div:eq(0)").click();
        }
    };
    $(".default_content_head #user_date_info").text(nickNutrition.util.getUserDateInfo());
    
    nickNutrition.framework.toggleMenu(0);
    
    $(".default_content_body " + ".default_content_menu_bar").find("div").click(function() {
      nickNutrition.framework.toggleMenu($(this).index());
    });

});