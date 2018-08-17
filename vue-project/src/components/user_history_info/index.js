import { timingSafeEqual } from "crypto";

export default {
    name: 'user_data_info',
    data () {
        return{
            personHistoryData: {
                name: null,
                id: null,
            },

            pageInfo:{
                name: null,
            },

            historyTableData: null,
            historyDialogVisible: false,
            rowData: {
                period: '',
                startWeight: null,
                endWeight: null,
                dietitianName: null,
            },

            historyPhaseData: {
                period: null,
                startWeight: null,
                endWeight: null,
                dietitianName: null,
            },

            allDataArrX: null,//折线图的横轴坐标
            allRealWeightData: null,//图线中真实体重数据
            allIdealWeightData: null, //图线中理想体重的数据
            allMaxAndMin: null,//折线图上下界

        }
    },
    props: {
        personHistoryData: this.personHistoryData
    },
    methods: {
        getPageInfo() {
            this.pageInfo.name = this.personHistoryData.name;
        },

        dateToStr(datetime) { 
            let year = datetime.getFullYear();
            let month = datetime.getMonth()+1;//js从0开始取 
            let date = datetime.getDate(); 
            
            if(month<10){
             month = "0" + month;
            }
            if(date<10){
             date = "0" + date;
            }
            
            let time = year+"-"+month+"-"+date; //2009-06-12 17:18:05
            return time;
        },

        formatEndDate(row) {
            let date = new Date(row.endDate);
            date = this.dateToStr(date);
            return date;
        },

        formatStartDate(row) {
            let date = new Date(row.startDate);
            date = this.dateToStr(date);
            return date;
          },

        requestData() {
            console.log("request data in histoy page");
            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/show_user_history_info',
                data: {
                    userInfoId: this.personHistoryData.id,
                }
            })
            .then((res) => {
                console.log(res.data.data);
                this.historyTableData = res.data.data;

                for( let i = 0; i < this.historyTableData.length; i++) {
                    let sDate = new Date(this.historyTableData[i].startDate);
                    sDate = this.dateToStr(sDate);
                    let eDate = new Date(this.historyTableData[i].endDate);
                    eDate = this.dateToStr(eDate);
                    let periodStr = sDate + " ~ " + eDate;
                    console.log(periodStr);
                    this.$set(this.historyTableData[i], "period", periodStr);
                    this.$set(this.historyTableData[i], "startWeight", this.historyTableData[i].startWeight + "(kg)");
                    this.$set(this.historyTableData[i], "endWeight", this.historyTableData[i].endWeight + "(kg)");
                }
                // console.log(this.historyTableData);
            })
            .catch(err => {
                // console.log(err);
            });
        },

        //获得图线所有数据
        getChartData(){
            this.getChartX();
            this.getChartRealWeight();
            this.getChartIdealWeight();
            this.getChartMaxAndMin();
        },

        //获得图的横坐标
        getChartX(){
            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/get_history_participating_time',
                data: {
                    dietPhaseInfoId: this.rowData.id,
                }
            })
            .then((res) => {
                this.allDataArrX = res.data.data;
            })
            .catch(err => {
            });
        },

        //获得图线真实体重数据
        getChartRealWeight(){
            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/get_history_participation_weight_data',
                data: {
                    dietPhaseInfoId: this.rowData.id,
                }
            })
            .then((res) => {
                this.allRealWeightData = res.data.data;
            })
            .catch(err => {
            });
        },

        //获得图线真实体重数据
        getChartIdealWeight(){
            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/get_history_ideal_weight_data',
                data: {
                    dietPhaseInfoId: this.rowData.id,
                }
            })
            .then((res) => {
                this.allIdealWeightData = res.data.data;
            })
            .catch(err => {
            });
        },

        //获得图线上下界
        getChartMaxAndMin(){
            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/get_history_MinAndMax_data',
                data: {
                    dietPhaseInfoId: this.rowData.id,
                }
            })
            .then((res) => {
                this.allMaxAndMin = res.data.data;
                if(this.allMaxAndMin === null)
                {
                    this.allMaxAndMin = ["100", "0"];
                }
            })
            .catch(err => {
            });
        },

        //画图线，尼基营养干预体重走势
        drawChart(){
            // 基于准备好的dom，初始化echarts实例
            let myChart = this.$echarts.init(document.getElementById('Chart'))
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
                        connectNulls: true,
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
                        connectNulls: true,
                        symbol : 'none',
                        data: this.allIdealWeightData
                    }
                ]
            })
        },

        //阶段性表格
        getPhaseTableData(){
            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/get_history_phase_table_data',
                data: {
                    dietPhaseInfoId: this.rowData.id,
                }
            })
            .then((res) => {
                this.historyPhaseData = res.data.data;
                let sDate = new Date(this.historyPhaseData.startDate);
                sDate = this.dateToStr(sDate);
                let eDate = new Date(this.historyPhaseData.endDate);
                eDate = this.dateToStr(eDate);
                let periodStr = sDate + " ~ " + eDate;
                console.log(periodStr);
                this.historyPhaseData.period = periodStr;
                this.historyPhaseData.startWeight += "(kg)";
                this.historyPhaseData.endWeight += "(kg)";
            })
            .catch(err => {
            });
        },

        showHistoryDetail(index, row) {
            this.historyDialogVisible = true;
            this.rowData = row;
            this.getChartData();
            this.getPhaseTableData();
            let self = this;
            setTimeout(
                function(){
                    self.drawChart();
                },500);
        },
    }
}