<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
    $(function(){
        var dietitianId = "${dietitianId}";
        if (dietitianId != "") {
            nickNutrition.userInfo.show_user_info_page_on_dietitian(dietitianId, 1, 1);
        } else {
            nickNutrition.util.bset_ajax({
              url : "rest/user_Info/select_user_info",
              target : ".default_content_right"
            });
        }
    });
</script>