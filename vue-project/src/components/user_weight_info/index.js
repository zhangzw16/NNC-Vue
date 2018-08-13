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

            totalData: null,
            tableData: [{},{},{},{},{},{},{}]
        }
    },
    props: {
        personWeightData: this.personWeightData
    },
    
    mounted(){
        this.drawChart1();
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

        getData(){
            this.getTableData();
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

                console.log(this.tableData);
            })
            .catch(err => {
                // console.log(err);
            });
        },

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

        requestData(date) {
            if (date === null) {
                date = new Date();
            }
            date = this.dateToStr(date);
            // console.log(date);

            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/select_user_data',
                params: {
                    userInfoId: this.personFoodData.id,
                    date: date,
                }
            })
            .then((res) => {
                console.log(res.data);
            })
            .catch(err => {
                // console.log(err);
            });
        },

        drawChart1(){
            // 基于准备好的dom，初始化echarts实例
            var myChart = this.$echarts.init(document.getElementById('myChart'))
            console.log("draw");
            // 绘制图表
            myChart.setOption({
                title : {
                    text: '未来一周气温变化',
                    subtext: '纯属虚构'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['最高气温','最低气温']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : ['周一','周二','周三','周四','周五','周六','周日']
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel : {
                            formatter: '{value} °C'
                        }
                    }
                ],
                series : [
                    {
                        name:'最高气温',
                        type:'line',
                        data:[11, 11, 15, 13, 12, 13, 10],
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }
                    },
                    {
                        name:'最低气温',
                        type:'line',
                        data:[1, -2, 2, 5, 3, 2, 0],
                        markPoint : {
                            data : [
                                {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name : '平均值'}
                            ]
                        }
                    }
                ]
            });
        }

    }
}