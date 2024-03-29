
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
        value1: "",

        startDate: "",
        endDate: "",

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

        pickerOptions1: {
            shortcuts: [{
              text: '最近一个月',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                picker.$emit('pick', [start, end]);
              }
            },{
              text: '最近三个月',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                picker.$emit('pick', [start, end]);
              }
            }, {
                text: '最近一年',
                onClick(picker) {
                  const end = new Date();
                  const start = new Date();
                  start.setTime(start.getTime() - 3600 * 1000 * 24 * 365);
                  picker.$emit('pick', [start, end]);
                }
            }, {
                text: '全部',
                onClick(picker) {
                  const end = new Date();
                  picker.$emit('pick', ["", end]);
                }
            }, ]
          },
        //柱状或折线图表option
        barOrLineChartOption: {
            title : {
                text: null
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:[]
            },
            toolbox: {
                show : true,
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    name : null,
                    data : null,                    
                    axisLabel:{
                        interval:0,//横轴信息全部显示
                        rotate:-30,//-30度角倾斜显示
                    }

                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name : null,
                }
            ],
            series : []
        }
    }
  },
  created() {
    this.refresh();     
    let self = this;
    setTimeout(
        function(){
            self.drawSexPiePhase(self.numOfAll, "全部年龄分布")           
            self.drawParticipantNumberChart();
        },500);
  },
  methods: {
    //图表标题
    setBarChartTitle(title)
    {
        this.barOrLineChartOption.title.text = title;
    },
    //横轴数据（list)
    setxAxisData(data)
    {
        this.barOrLineChartOption.xAxis[0].data = data;
    },
    //横轴表示的数据名称
    setxAxisName(name)
    {
        this.barOrLineChartOption.xAxis[0].name = name;
    },
    //纵轴表示的数据名称
    setyAxisName(name)
    {
        this.barOrLineChartOption.yAxis[0].name = name;
    },
    //增加图线, name为图线名，data为图线数据（list),type为图线类型(bar或者line),color为图线颜色
    addNewSeries(seriesName, seriesData, serieStype, chartColor)
    {
        this.barOrLineChartOption.legend.data.push(seriesName);
        let newSeries = {
            name: seriesName,
            type: serieStype,
            data: seriesData,
            itemStyle: {
                normal: {
                    label : {show: true},
                    color: chartColor,
                    lineStyle: {
                        color: chartColor
                    }
                }
            },
        }
        this.barOrLineChartOption.series.push(newSeries);
    },
  
    refresh() {
      this.getFormData();
      let self = this;
      setTimeout(
          function(){
              switch(self.radio1)
              {
                case "0":
                    self.setxAxisName('营养师姓名');
                    self.setxAxisData(self.dietitianNameList)
                    self.setyAxisName('体重单位:kg')
                    self.barOrLineChartOption.series = [];
                    self.addNewSeries('平均减重（正值为减重量,负值为增重量）',self.averWeightLoss,'bar',"#2ec7c9");
                    self.drawChart('dietitianInfoChart', self.barOrLineChartOption);
                    break;
                case "1":
                    self.setxAxisName('营养师姓名');
                    self.setxAxisData(self.dietitianNameList)
                    self.setyAxisName('人数')
                    self.barOrLineChartOption.series = [];
                    self.addNewSeries('客户人数',self.personNumberList,'bar',"#71d");
                    self.drawChart('dietitianInfoChart', self.barOrLineChartOption);
                    break;
                default:
                    break;
              }

          },500);

    },

    dateToStr(datetime) { 
        var year = datetime.getFullYear();
        var month = datetime.getMonth()+1;//js从0开始取 
        var date = datetime.getDate(); 
        var hour = datetime.getHours(); 
        var minutes = datetime.getMinutes(); 
        var second = datetime.getSeconds();
        
        if(month<10){
         month = "0" + month;
        }
        if(date<10){
         date = "0" + date;
        }
        if(hour <10){
         hour = "0" + hour;
        }
        if(minutes <10){
         minutes = "0" + minutes;
        }
        if(second <10){
         second = "0" + second ;
        }
        
        var time = year+"-"+month+"-"+date; //2009-06-12 17:18:05
       // alert(time);
        return time;
      },

    dateChange(value){
        // console.log(value)
        if(value[0] != "")
        {
            this.startDate = this.dateToStr(value[0]);
        }
        else{
            this.startDate = "";
        }
        this.endDate = this.dateToStr(value[1]);
        // console.log(this.startDate+"   "+this.endDate);
        this.refresh();
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
            this.setxAxisName('营养师姓名');
            this.setxAxisData(this.dietitianNameList)
            this.setyAxisName('体重单位:kg')
            this.barOrLineChartOption.series = [];
            this.addNewSeries('平均减重（正值为减重量,负值为增重量）',this.averWeightLoss,'bar',"#2ec7c9");
            this.drawChart('dietitianInfoChart', this.barOrLineChartOption);
        }
        else if(value == 1)
        {
            this.setxAxisName('营养师姓名');
            this.setxAxisData(this.dietitianNameList)
            this.setyAxisName('人数')
            this.barOrLineChartOption.series = [];
            this.addNewSeries('客户人数',this.personNumberList,'bar',"#71d");
            this.drawChart('dietitianInfoChart', this.barOrLineChartOption);
        }
    },
    //获得营养师相关数据
    getDietitianInfoData(){       
        this.averWeightLossOfDietitianData = this.pageData.averWeightLossOfDietitian;
        this.personOfDietitianData = this.pageData.personOfDietitian;
        this.dietitianNameList = [];
        this.averWeightLoss = [];
        this.personNumberList = [];
        for(let name in this.averWeightLossOfDietitianData)
        {
            this.dietitianNameList.push(name);
            this.averWeightLoss.push(this.averWeightLossOfDietitianData[name].toFixed(1));
            this.personNumberList.push(this.personOfDietitianData[name]);
        }
    },
    //获得注册人数随时间变化数据
    getRegisterNumberData(){       
        this.registerData = this.pageData.registerNum;
        this.startNumberData = this.pageData.startNum;
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
    },
    
    getFormData(){
        this.axios({
            method: 'post',
            url: "/NNC/rest/user_Info/get_user_report_forms",
            data: {
                startDate: this.startDate,
                endDate: this.endDate,
            }
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

            this.getDietitianInfoData();
            this.getRegisterNumberData();

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

    drawChart(elementId, option)
    {
        let myChart = this.$echarts.init(document.getElementById(elementId));
        myChart.setOption(option);
    },

    //注册人数变化
    drawParticipantNumberChart(){
        this.setxAxisName('月份');
        this.setxAxisData(this.timelist)
        this.setyAxisName('人数')
        this.barOrLineChartOption.series = [];
        this.addNewSeries("注册人数",this.registerNumberList,'line',"#71d");
        this.addNewSeries('开始减重人数',this.startNumberList,'line',"#2ec7c9");
        this.drawChart('participantNumberChart', this.barOrLineChartOption);
    },
  }
}