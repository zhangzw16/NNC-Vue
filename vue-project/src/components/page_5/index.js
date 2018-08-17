
export default {
  name: 'page_5',
  components: {
  },
  
  data () {
    return {
        pageData: null,
        numOfMen: [],
        numOfWomen: [],
        numOfAll: [],
        radio: "2",
        radio1: "0",

        averWeightLossOfDietitianData: null,
        dietitianNameList: [],
        averWeightLoss: [],

        personOfDietitianData: null,
        personNumberList: [],

        registerData:null,
        startNumberData:null,
        timelist: [],
        registerNumberList:[],
        startNumberList:[],
    }
  },
  created() {
    this.refresh();

    let self = this;
    setTimeout(
        function(){
            self.drawSexPiePhase(self.numOfAll, "全部年龄分布")
        },500);
  },
  methods: {
    refresh() {
      this.getSexData();
    },

    sexChange(value){
        let self = this;
        if(value == 0){
            setTimeout(
                function(){
                    self.drawSexPiePhase(self.numOfMen, "男性年龄分布")
                },500);           
        }
        else if(value == 1){
            setTimeout(
                function(){
                    self.drawSexPiePhase(self.numOfWomen, "女性年龄分布")
                },500);
        }
        else{
            setTimeout(
                function(){
                    self.drawSexPiePhase(self.numOfAll, "全部年龄分布")
                },500);
        }
    },

    phaseChange(value){
        if(value == 0){
            this.drawWeightLossChart();
        }
    },

    getSexData(){
        this.axios({
            method: 'post',
            url: "/NNC/rest/user_Info/get_user_report_forms",
            })
            .then((res) => {
            this.pageData = res.data.data;
            let nameList = ['0-17','18-29','30-39','40-49','50-59','60'];
            
            let menData = null,
                womenData = null;

            menData = res.data.data.numOfMen;
            womenData = res.data.data.numOfWomen;
                
            for(let i of nameList){
                // this.numOfMen[index].value = menData[i],
                // this.numOfMen[index].name = i;
                console.log(i);
                this.numOfMen.push({"value" : menData[i], "name" : i});
                this.numOfWomen.push({"value" : womenData[i], "name" : i});
                this.numOfAll.push({"value" : womenData[i] + menData[i], "name" : i})
            }

            this.averWeightLossOfDietitianData = this.pageData.averWeightLossOfDietitian;
            this.personOfDietitianData = this.pageData.personOfDietitian;
            this.registerData = this.pageData.registerNum;
            this.startNumberData = this.pageData.startNum;
            for(let name in this.averWeightLossOfDietitianData)
            {
                this.dietitianNameList.push(name);
                this.averWeightLoss.push(this.averWeightLossOfDietitianData[name].toFixed(1));
                this.personNumberList.push(this.personOfDietitianData[name]);
            }


            let max = 0;
            for(let name in this.registerData)
            {
                if(parseInt(name) > max);
                max = parseInt(name);
            }
            for(let name in this.startNumberData)
            {
                if(parseInt(name) > max);
                max = parseInt(name);
            }


            for(let i = 0; i <= max; i++)
            {
                if(i === 0)
                {
                    this.timelist.push("2017年8月及之前");
                }
                else
                {
                    let year = 2017;
                    let monthGap = i - 1;
                    let month = 9 + monthGap;
                    year += Math.floor((month-1) / 12);
                    month = (month - 1) % 12 + 1;
                    let timeStr = year.toString()+"年"+month.toString()+"月";
                    this.timelist.push(timeStr);
                }
                if(this.registerData[i.toString()]===undefined)
                {
                    this.registerNumberList.push(0);
                }
                else
                {
                    this.registerNumberList.push(this.registerData[i.toString()]);
                }
                if(this.startNumberData[i.toString()]===undefined)
                {
                    this.startNumberList.push(0);
                }
                else
                {
                    this.startNumberList.push(this.startNumberData[i.toString()]);
                }
            }
            this.drawWeightLossChart();
            // this.drawPersonNumberChart();
            this.drawParticipantNumberChart();

          })
          .catch(err => {
            console.log(err);
          })
    },

    drawSexPiePhase(data, title) {
      let myChart = this.$echarts.init(document.getElementById('piePhase'))
      myChart.setOption({
        title : {
          text: title,
          x:'center'
      },
      tooltip : {
          trigger: 'item',
          formatter: "{a} <br/>{b} : {c} ({d}%)"
      },
      legend: {
          orient : 'vertical',
          x : 'left',
          data:['0-17','18-29','30-39','40-49','50-59','60']
      },
      toolbox: {
          show : true,
          feature : {
              mark : {show: true},
              dataView : {show: true, readOnly: false},
              magicType : {
                  show: true, 
                  type: ['pie', 'funnel'],
                  option: {
                      funnel: {
                          x: '25%',
                          width: '50%',
                          funnelAlign: 'left',
                          max: 1548
                      }
                  }
              },
              restore : {show: true},
              saveAsImage : {show: true}
          }
      },
      calculable : true,
      series : [
          {
              name:'年龄段',
              type:'pie',
              radius : '55%',
              center: ['50%', '60%'],
              data: data         
          }
      ]
      })
    },

    
    //画图线，平均减重
    drawWeightLossChart(){
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.$echarts.init(document.getElementById('averWeightLossChart'))
        // 绘制图表
        myChart.setOption({
            title : {
                // text: '某地区蒸发量和降水量',
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:["平均减重（正值为减重量,负值为增重量）"]
            },
            toolbox: {
                show : true,
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    name : '营养师姓名',
                    data : this.dietitianNameList,
                    
                    axisLabel:{
                        interval:0,//横轴信息全部显示
                        rotate:-30,//-30度角倾斜显示
                    }

                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name : '体重单位:kg',
                }
            ],
            series : [
                {
                    name:'平均减重（正值为减重量,负值为增重量）',
                    type:'bar',
                    data: this.averWeightLoss,
                    // markPoint : {
                    //     data : [
                    //         {type : 'max', name: '最大值'},
                    //         {type : 'min', name: '最小值'}
                    //     ]
                    // },
                    itemStyle: { normal: {label : {show: true}}}
                },
            ]
        })
    },

    //每个营养师客户人数
    drawPersonNumberChart(){
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.$echarts.init(document.getElementById('personNumberChart'))
        // 绘制图表
        myChart.setOption({
            title : {
                // text: '某地区蒸发量和降水量',
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:["客户人数"]
            },
            toolbox: {
                show : true,
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    name : '营养师姓名',
                    data : this.dietitianNameList,
                    
                    axisLabel:{
                        interval:0,//横轴信息全部显示
                        rotate:-30,//-30度角倾斜显示
                    }

                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name : '人数',
                }
            ],
            series : [
                {
                    name:'客户人数',
                    type:'bar',
                    data: this.personNumberList,
                    // markPoint : {
                    //     data : [
                    //         {type : 'max', name: '最大值'},
                    //         {type : 'min', name: '最小值'}
                    //     ]
                    // },
                    itemStyle: {
                        normal: {
                            label : {show: true},
                            color: "#2ec7c9",
                            lineStyle: {
                                color: "#2ec7c9"
                            }
                        }
                    },
                },
            ]
        })
    },

    //注册人数变化
    drawParticipantNumberChart(){
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.$echarts.init(document.getElementById('participantNumberChart'))
        // 绘制图表
        myChart.setOption({
            title : {
                // text: '某地区蒸发量和降水量',
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:["注册人数","开始减重人数"]
            },
            toolbox: {
                show : true,
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    name : '月份',
                    data : this.timelist,
                    
                    axisLabel:{
                        interval:0,//横轴信息全部显示
                        rotate:-30,//-30度角倾斜显示
                    }

                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name : '人数',
                }
            ],
            series : [
                {
                    name:'注册人数',
                    type:'line',
                    data: this.registerNumberList,
                    // markPoint : {
                    //     data : [
                    //         {type : 'max', name: '最大值'},
                    //         {type : 'min', name: '最小值'}
                    //     ]
                    // },
                    itemStyle: {
                        normal: {
                            label : {show: true},
                            color: "#2ec7c9",
                            lineStyle: {
                                color: "#2ec7c9"
                            }
                        }
                    },
                },
                {
                    name:'开始减重人数',
                    type:'line',
                    data: this.startNumberList,
                    // markPoint : {
                    //     data : [
                    //         {type : 'max', name: '最大值'},
                    //         {type : 'min', name: '最小值'}
                    //     ]
                    // },
                    itemStyle: {
                        normal: {
                            label : {show: true},
                        }
                    },
                },
            ]
        })
    },
  }
}