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
            this.getSevenDayData();
        },

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
                this.getMaxAndMinData();
            })
            .catch(err => {
                // console.log(err);
            });
        },
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
                this.drawChart1();
            })
            .catch(err => {
                // console.log(err);
            });
        },

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
                this.createTable();
                console.log("table");
                console.log(this.tableData);
            })
            .catch(err => {
                // console.log(err);
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
        //第一张图线，7天的体重变化
        drawChart1(){
            // 基于准备好的dom，初始化echarts实例
            var myChart = this.$echarts.init(document.getElementById('firstChart'))
            console.log("draw");
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
                        min : this.maxAndMin[0],
                        max : this.maxAndMin[1],
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

        //第二张图线，尼基营养干预体重走势
        drawChart2(){

        },
    }
}