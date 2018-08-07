//判断登录状态
function loginStatus(){
	plus.networkinfo.CONNECTION_UNKNOW = "未知";
    plus.networkinfo.CONNECTION_NONE = "无网络链接";
   	plus.networkinfo.CONNECTION_ETHERNET = "有线网络";
	plus.networkinfo.CONNECTION_WIFI = "无线网络";
	plus.networkinfo.CONNECTION_CELL2G = "移动2G网络";
	plus.networkinfo.CONNECTION_CELL3G = "移动3G网络";
	plus.networkinfo.CONNECTION_CELL4G = "移动4G网络";
    if(plus.networkinfo.getCurrentType()==1){
        mui.toast("无网络连接,请检查网络!");
        return false;
    }
	var uid=localStorage.getItem('NNC_userInfoId');
	if(uid == null){
		alertLogin();
		return false;
	}
	return true;
}
//登录弹框
function alertLogin(){
	mui.confirm('您还没有登录，现在需要去登录吗','提示',new Array('取消','确认'),function(e){
		if(e.index == 1){
			Login();
		}
	})
}
//跳转登录页面
function Login(){
	mui.openWindow({
		url : '../../page/login/login.html',
		id  : 'login.html'
	});
}
//判断数据提交时间是否大于今天
function checkDate(submitDate){
	var d = new Date();
	today = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
    var todayDate=new Date(today.replace("-", "/").replace("-", "/"));  
    var subDate=new Date(submitDate.replace("-", "/").replace("-", "/"));  
    if(subDate <= todayDate){
        return true;  
    }else{
    	mui.alert("所填数据的日期大于当前日期，无法提交数据！")
    	return false;
    }
}
