<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="bts" uri="/bts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
</head>
<script type="text/javascript">
    document.getElementById('user_weight_data_date').onclick = function(){
        laydate({
          elem: this,
          istime: false,
          format: 'YYYY-MM-DD',
          choose: function(dates){ //选择好日期的回调
              nickNutrition.userInfo.select_user_weight_data("${user_info.id}", dates);
          }
        });
    };
</script>
<body>
<div id="user_weight_data_div_id">
    <div style="margin-left: 20px;">
        <p id="user_info_name" style="text-align: left;font-size: 14px;">客户名称<span style="color:#5bb3f6;margin-left:25px;">${user_info.name}</span></p>
    </div>
    <div class="weightDataBox">
        <div class="weightDataBox_head">
            <span>每周体重变化数据</span>
            <label for="user_weight_data_date"></label><input id="user_weight_data_date" class="laydate-icon" value="${chose_date}">
        </div>
        <div class="weightDataBox_main">
            <div class="wdb_main_left">
                <table class="common_border_table">
                    <tbody>
                        <tr>
                            <td>星期</td>
                            <td>日期</td>
                            <td>当日体重</td>
                            <td>体重变化</td>
                        </tr>
                    </tbody>
                    <c:forEach var="obj" items="${week_table_data}" varStatus="parameter">
                        <tr>
                            <td>${obj.key}</td>
                            <td>${obj.value.date}</td>
                            <c:if test="${obj.value.flag == 0}">
                                <td>${obj.value.weight}(kg)</td>
                                <td>${obj.value.dietWeight}(kg)</td>
                            </c:if>
                            <c:if test="${obj.value.flag == 1}">
                                <td style="color: red;">${obj.value.weight}(kg)</td>
                                <td style="color: red;">+${obj.value.dietWeight}(kg)</td>
                            </c:if>
                            <c:if test="${obj.value.flag == 2}">
                                <td style="color: green;">${obj.value.weight}(kg)</td>
                                <td style="color: green;">-${obj.value.dietWeight}(kg)</td>
                            </c:if>
                            <c:if test="${obj.value.flag == null}">
                                <td></td>
                                <td></td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </div>
             <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
             <div class="wdb_main_right">
                <div id="week_data" style="width: 450px; height:300px; margin-top:40px; "></div>
                 <script type="text/javascript">
                // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('week_data'));
                    var dataArr = ${week_data};
                    var week_data_max = "${week_data_max}";
                    var week_data_min = "${week_data_min}";
                    var option = {
                        title : {
                            /*text: '每周体重变化曲线图'*/
                        },
                        tooltip : {
                            trigger: 'item',
                            axisPointer:{
                                type: 'cross'
                            }
                        },
                        legend: {
                            data:['实际体重']
                            
                        },
                        toolbox: {
                            show : true,
                            feature : {
                                //dataView : {show: true, readOnly: false},
                                //saveAsImage : {show: true}
                            }
                        },
                        calculable : false,
                        xAxis : [
                            {
                                type : 'category',
                                boundaryGap : false,
                                data : ['周六','周日','周一','周二','周三','周四','周五','周六']
                            }
                        ],
                        yAxis : [
                            {
                                type : 'value',
                                name : '体重单位:kg',
                                //scale:true,
                                min : week_data_min,
                                max : week_data_max,
                                axisTick : {
                                    show : true,
                                    interval : 0
                                },
                                axisLabel : {
                                    show : true,
                                    formatter: '{value}',
                                    interval : '{1}'
                                }
                            }
                        ],
                        series : [
                            {
                                name:'实际体重',
                                type:'line',
                                data: dataArr,
                                //symbol : 'none',
                                markPoint : {
                                    data : [
                                        {type : 'max', name: '最大值'},
                                        {type : 'min', name: '最小值'}
                                    ]
                                },
                                itemStyle: {
                                    emphasis : {
                                        label : {
                                            show: true,
                                            position:'top',
                                            textStyle :{
                                                fontStyle :' oblique',
                                                fontWeight :'bold'
                                            }
                                         }
                                    }
                                }
                            }
                        ]
                    };
                    myChart.setOption(option);
                </script>
             
             </div>
        </div>
    </div>
    <div class="weightDataBoxTwo">
        <div class="weightDataBox_head">尼基营养干预体重走势</div>
        <div id="all_data" style="width: 800px; height:260px; margin-left: 10px;margin-top:10px;"></div>
        <script type="text/javascript">
            // 基于准备好的dom，初始化echarts实例
            var allData = echarts.init(document.getElementById('all_data'));
            var allDataArrX = ${all_data_x};
            var allDataArrY = ${all_data_y};
            var allWeightDataArr = ${ideal_body_weight_all};
            var all_data_max = "${all_data_max}";
            var all_data_min = "${all_data_min}";
            var option2 = {
                title : {
                   /* text: '尼基营养干预体重走势'*/
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['理想体重','实际体重']
                },
                toolbox: {
                    show : true,
                    feature : {
                        //dataView : {show: true, readOnly: false},
                        //saveAsImage : {show: true}
                    }
                },
                calculable : false,
                xAxis : [
                    {
                        type : 'category',
                        name : '减重天数(序号)',
                        boundaryGap : false,
                        data : allDataArrX
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        name : '体重单位:kg',
                        //scale:true,
                        min : all_data_min,
                        max : all_data_max,
                        axisTick : {
                            show : true
                        },
                        axisLabel : {
                            formatter: '{value}'
                        }
                    }
                ],
                series : [
                    {
                        name:'实际体重',
                        type:'line',
                        //symbol : 'none',
                        data: allDataArrY,
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        }
                    },
                    {
                        name:'理想体重',
                        type:'line',
                        symbol : 'none',
                        data: allWeightDataArr
                    }
                ]
            };
            allData.setOption(option2);
        </script>
    </div>
    </div>
</body>
</html>