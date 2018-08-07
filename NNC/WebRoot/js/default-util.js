/**
 * @fileoverview nnc WebAPI.
 * 
 * @name nnc
 * @access public
 * @chenwl
 * @create 2017-06-26
 * @version 1.0.0
 */
var loadingLayer = null;
var scriptsArray = new Array();
$.cachedScript = function(url, options) {
    // 循环script标记数组
    for ( var s in scriptsArray) {
        // console.log(scriptsArray[s]);
        // 如果某个数组已经下载到了本地
        if (scriptsArray[s] == url) {
            return { // 则返回一个对象字面量，其中的done之所以叫做done是为了与下面$.ajax中的done相对应
                done : function(method) {
                    if (typeof method == 'function') { // 如果传入参数为一个方法
                        method();
                    }
                }
            };
        }
    }
    // 这里是jquery官方提供类似getScript实现的方法，也就是说getScript其实也就是对ajax方法的一个拓展
    options = $.extend(options || {}, {
        dataType : "script",
        url : url,
        cache : true
    // 其实现在这缓存加与不加没多大区别
    });
    scriptsArray.push(url); // 将url地址放入script标记数组中
    return $.ajax(options);
};
var nickNutrition = {
    userManage : {},
    deviceManage : {},
    util : {
        user_quit : function() {
            layer.confirm('您确定要退出吗？', {//选择提示框//弹框内容
                title:false,//是否需要标头
                area : ['300px','140px'],
                btn: ['确认', '取消']
             },//可以无限个按钮//设置按钮
             function(index){//执行点击第一个按钮后的任务
                 window.location.href = "rest/index";
                 $.post('rest/user/logout', {
                 });
             });
        },
        authentication : function(type) {
            if (1 != type) {
              nickNutrition.util.pup_tip("您没有此项操作的权！");
                return false;
            }
            return true;
        },
        getUserDateInfo : function() {
            var now = new Date();
            var year = now.getFullYear(); // 年
            var month = now.getMonth() + 1; // 月
            var day = now.getDate(); // 日
            var hh = now.getHours(); // 时
            var mm = now.getMinutes(); // 分
            var clock = year + "-";
            if (month < 10) {
                clock += "0";
            }
            clock += month + "-";

            if (day < 10) {
                clock += "0";
            }
            clock += day + " ";
            /**
             * if(hh < 10){ clock += "0"; } clock += hh + ":"; if (mm < 10){
             * clock += '0'; } clock += mm;
             */
            return (clock);
        },
        // 打开ajax 用loading效果
        // 0 3个球 1传统圆圈 mini 2 传统圆圈
        loading_dialog_open : function(e) {
            loadingLayer = layer.load(e, {
                shade : [ 0.6, 'rgba(255,255,255,1.00)' ]
            // 0.6透明度的白色背景
            });
        },
        // 关闭ajax 用loading效果
        loading_dialog_close : function() {
            layer.close(loadingLayer);
        },
        pup_tip : function(contents, type) {
            layer.msg(contents, {
                icon : type,
                time : 2000
            });
        },
        util_post : function(opt, options) {
            $.post(opt.url, opt.data, function(str) {
                options.content = str;
                layer.open(options);
            });
        },
        bset_ajax : function(opt) {
            var options = jQuery.extend({
                type : "POST",
                url : "",
                beforeSend : nickNutrition.util.loading_dialog_open(0),
                success : function(content) {
                    $(opt["target"]).html(content);

                    if (opt["afterSuccess"] != undefined) {
                        opt.afterSuccess();
                    }
                    nickNutrition.util.loading_dialog_close();
                }
            }, opt);
            $.ajax(options);
        },

        bset_ajax_form : function(opt) {
            $("#" + opt["form"]).ajaxForm({
                beforeSubmit : nickNutrition.util.loading_dialog_open(0),
                success : function() {
                  nickNutrition.util.loading_dialog_close();
                },
                target : "#" + opt["target"]
            });
        },

        // 判断是否是number类型
        isNumber : function(value) {
            if (/^-?[0-9]+(.[0-9]+)?$/.test(value) == false) {
                return false;
            }
            return true;
        }
    }
};