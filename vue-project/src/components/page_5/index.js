
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
        radio: "2"
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

          })
          .catch(err => {
            console.log(err);
          })
    },

    drawSexPiePhase(data, sex) {
      let myChart = this.$echarts.init(document.getElementById('piePhase'))
      myChart.setOption({
        title : {
          text: sex,
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
    }
  }
}