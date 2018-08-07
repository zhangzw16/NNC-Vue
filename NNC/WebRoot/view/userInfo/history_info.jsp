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
</script>
<body>
<div>
    <div class="weightDataBoxTwo">
        <div class="weightDataBox_head">尼基营养干预体重走势</div>
        <div id="history_all_data" style="width: 800px; height:260px; margin-left: 10px;margin-top:10px;"></div>
        <script type="text/javascript">
            // 基于准备好的dom，初始化echarts实例
            var allData = echarts.init(document.getElementById('history_all_data'));
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
    <div class="dataBoxTwo" style="margin-left: 80px;">
        <div class="sumbit_div">
            <div class="d">
              <span class="fl">减重期间</span>
              <span class="c">
                <bts:helper methodName="dateFormat1" className="cn.com.bocosoft.helper.CommonHelper" params="${dietPhaseInfo.startDate}">
                    ${date_format}
                </bts:helper>
                ~
                <bts:helper methodName="dateFormat1" className="cn.com.bocosoft.helper.CommonHelper" params="${dietPhaseInfo.endDate}">
                    ${date_format}
                </bts:helper>
              </span>
            </div>
        </div>
        <div class="sumbit_div">
            <div class="d">
              <span class="fl">初始体重</span>
              <span class="c">${dietPhaseInfo.startWeight}(kg)</span>
            </div>
        </div>
        <div class="sumbit_div">
            <div class="d">
              <span class="fl">减脂后体重</span>
              <span class="c">${dietPhaseInfo.endWeight}</span>
            </div>
        </div>
        <div class="sumbit_div">
            <div class="d">
              <span class="fl">营养师</span>
              <span class="c">${dietPhaseInfo.dietitianName}</span>
            </div>
        </div>
    </div>
</div>
</body>
</html>