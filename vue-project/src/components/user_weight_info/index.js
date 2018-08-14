import { timingSafeEqual } from "crypto";
export default {
    name: 'user_weight_info',
    data () {
        return{
            personWeightData: {
                name: null,
                id: null,
            },

            pageInfo:{
                name: null,
                // 备注
                date: null,
            },

            totalData: null,//获得的原始表格数据
            tableData: [{},{},{},{},{},{},{}],//表格数据
            firstChartData: null,//第一张折线图的数据
            maxAndMin: null,//第一张折线图上下界

            allDataArrX: null,//第二张折线图的横轴坐标
            allRealWeightData: null,//第二张图线中真实体重数据
            allIdealWeightData: null, //第二张图线中理想体重的数据
            allMaxAndMin: null,//第二张折线图上下界
        }
    },
    props: {
        personWeightData: this.personWeightData
    },
 
    methods: {
        dateToStr(datetime) { 
            var year = datetime.getFullYear();
            var month = datetime.getMonth()+1;//js从0开始取 
            var date = datetime.getDate(); 
            
            if(month<10){
             month = "0" + month;
            }
            if(date<10){
             date = "0" + date;
            }
            
            var time = year+"-"+month+"-"+date; //2009-06-12 17:18:05
           // alert(time);
            return time;
        },

        getPageInfo() {
            this.pageInfo.name = this.personWeightData.name;
            this.getData();
        },
        //获得所有该页面数据
        getData(){
            this.getTableData();
            this.getFirstChartData();
            this.getSecondChartData();
            let self = this;
            setTimeout(
                function(){
                    self.createTable();
                    self.drawChart1();
                    self.drawChart2();
                },1000);
        },

        //获得第一张表格的原始数据
        getTableData(){
            if(this.pageInfo.date === null)
            {
                this.pageInfo.date = new Date();
            }
            let date = this.dateToStr(this.pageInfo.date);
            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/get_user_weight_table_data',
                params: {
                    userInfoId: this.personWeightData.id,
                    date: date,
                }
            })
            .then((res) => {
                this.totalData = res.data.data;
            })
            .catch(err => {
            });
        },

        //第一个表格的数据完成
        createTable(){
            let i = 0;
            for(let obj in this.totalData)
            {
                this.$set(this.tableData[i], "day", obj);
                this.$set(this.tableData[i], "date", this.totalData[obj].date);
                this.$set(this.tableData[i], "weight", this.totalData[obj].weight + "(kg)");

                switch (this.totalData[obj].flag) {
                    case 0:            
                        break;
                    case 1:
                        this.$set(this.tableData[i], "dietWeight", "+" + this.totalData[obj].dietWeight + "(kg)");
                        break;
                    case 2:
                        this.$set(this.tableData[i], "dietWeight", "-" + this.totalData[obj].dietWeight + "(kg)");
                        break;
                    default:
                        this.$set(this.tableData[i], "weight", "");
                        this.$set(this.tableData[i], "dietWeight", "");
                        break;
                }
                i++;
            }
        },

        //获得第一张图线数据
        getFirstChartData(){
            this.getSevenDayData();
            this.getMaxAndMinData();
        },

        //第一张图线，7天体重数据
        getSevenDayData(){
            if(this.pageInfo.date === null)
            {
                this.pageInfo.date = new Date();
            }
            let date = this.dateToStr(this.pageInfo.date);
            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/get_user_weight_sevenday_data',
                params: {
                    userInfoId: this.personWeightData.id,
                    date: date,
                }
            })
            .then((res) => {
                this.firstChartData = res.data.data;
            })
            .catch(err => {
            });
        },

        //第一张图线上下界
        getMaxAndMinData(){
            if(this.pageInfo.date === null)
            {
                this.pageInfo.date = new Date();
            }
            let date = this.dateToStr(this.pageInfo.date);
            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/get_weight_sevenday_maxAndMin_data',
                params: {
                    userInfoId: this.personWeightData.id,
                    date: date,
                }
            })
            .then((res) => {
                this.maxAndMin = res.data.data;
                if(this.maxAndMin === "null")
                {
                    this.maxAndMin = ["100", "0"];
                }
            })
            .catch(err => {
            });
        },

        //第一张图线，7天的体重变化
        drawChart1(){
            // 基于准备好的dom，初始化echarts实例
            let myChart = this.$echarts.init(document.getElementById('firstChart'))
            // 绘制图表
            myChart.setOption({
                title : {
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
                },
                calculable : true,
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
                        min : this.maxAndMin[1],
                        max : this.maxAndMin[0],
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
                        data: this.firstChartData,
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        itemStyle: {
                            normal: {
                                color: "#2ec7c9",
                                lineStyle: {
                                    color: "#2ec7c9"
                                }
                            },
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
            });
        },

        //获得第二张图线所有数据
        getSecondChartData(){
            this.getSecondChartX();
            this.getSecondChartRealWeight();
            this.getSecondChartIdealWeight();
            this.getSecondChartMaxAndMin();
        },

        //获得第二张图的横坐标
        getSecondChartX(){
            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/get_user_participating_time',
                params: {
                    userInfoId: this.personWeightData.id,
                }
            })
            .then((res) => {
                this.allDataArrX = res.data.data;
            })
            .catch(err => {
            });
        },

        //获得第二张图线真实体重数据
        getSecondChartRealWeight(){
            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/get_user_participation_weight_data',
                params: {
                    userInfoId: this.personWeightData.id,
                }
            })
            .then((res) => {
                this.allRealWeightData = res.data.data;
            })
            .catch(err => {
            });
        },

        //获得第二张图线真实体重数据
        getSecondChartIdealWeight(){
            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/get_user_ideal_weight_data',
                params: {
                    userInfoId: this.personWeightData.id,
                }
            })
            .then((res) => {
                this.allIdealWeightData = res.data.data;
            })
            .catch(err => {
            });
        },

        //获得第二张图线上下界
        getSecondChartMaxAndMin(){
            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/get_user_alltime_MinAndMax_data',
                params: {
                    userInfoId: this.personWeightData.id,
                }
            })
            .then((res) => {
                this.allMaxAndMin = res.data.data;
                if(this.allMaxAndMin === "null")
                {
                    this.allMaxAndMin = ["100", "0"];
                }
            })
            .catch(err => {
            });
        },

        //第二张图线，尼基营养干预体重走势
        drawChart2(){
            // 基于准备好的dom，初始化echarts实例
            let myChart = this.$echarts.init(document.getElementById('secondChart'))
            // 绘制图表
            myChart.setOption({
                title : {
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['理想体重','实际体重']
                },
                toolbox: {
                    show : true,
                },
                calculable : false,
                xAxis : [
                    {
                        type : 'category',
                        name : '减重天数(序号)',
                        boundaryGap : false,
                        data : this.allDataArrX
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        name : '体重单位:kg',
                        min : this.allMaxAndMin[1],
                        max : this.allMaxAndMin[0],
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
                        data: this.allRealWeightData,
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
                        itemStyle: {
                            normal: {
                            color: "#2ec7c9",
                            lineStyle: {
                                color: "#2ec7c9"
                            }
                        }
                        },
                        symbol : 'none',
                        data: this.allIdealWeightData
                    }
                ]
            })
        },
    }
}