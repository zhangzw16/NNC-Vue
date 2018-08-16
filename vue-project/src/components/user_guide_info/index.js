import { timingSafeEqual } from "crypto";

export default {
    name: 'user_data_info',
    data () {
        return{
            personGuideData: {
                name: null,
                id: null,
            },

            pageInfo:{
                name: null,
                guideData: {
                    // id: null,
                    // userInfoId: null,
                    // weekCount: null,
                    // yyyy: null,
                    // menu: null,
                    // sport: null,
                    // createId: null,
                },
            },
            
            //日期选择器
            pickerOptions: {
                disabledDate(time) {
                  return time.getTime() > Date.now();
                },
                shortcuts: [{
                  text: '今天',
                  onClick(picker) {
                    picker.$emit('pick', new Date());
                  }
                }, {
                  text: '昨天',
                  onClick(picker) {
                    const date = new Date();
                    date.setTime(date.getTime() - 3600 * 1000 * 24);
                    picker.$emit('pick', date);
                  }
                }, {
                  text: '一周前',
                  onClick(picker) {
                    const date = new Date();
                    date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                    picker.$emit('pick', date);
                  }
                }]
            },
            value: '',

            sport: '',
            menu: '',
            chose_date: '',
        }
    },
    created() {
        // console.log('user_data_info id', this.personFoodData.id);
    },
    props: {
        personGuideData: this.personGuideData
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
            this.pageInfo.name = this.personGuideData.name;
        },

        editRecommend(type) {
            this.$prompt('备注', '备注', {
                confirmButtonText: '保存',
                cancelButtonText: '取消',
                value: type===1 ? this.sport : this.menu,
            }).then(({ value }) => {
                console.log("value", value);
                console.log("type", type);
                this.axios({
                    method: 'post',
                    url: '/NNC/rest/user_Info/edit_weekly_recommend_save',
                    data: {
                        id: this.pageInfo.guideData.id,
                        userInfoId: this.personGuideData.id,
                        weekCount: this.pageInfo.guideData.weekCount,
                        yyyy: this.pageInfo.guideData.yyyy,
                        menu: type === 1 ? this.pageInfo.guideData.menu : value,
                        sport: type === 1 ? value : this.pageInfo.guideData.sport,
                        createId: this.pageInfo.guideData.createId,
                        chose_date: this.chose_date,
                    }
                })
                .then((res) => {
                    console.log(res);
                })
                .catch(err => {
                    console.log(err);
                });

                this.$message({
                    type: 'success',
                    message: "修改成功"
                });
                // this.$emit("noteChanged");
                if (type == 1) {
                    this.sport = value;
                }
                else {
                    this.menu = value;
                }

            }).catch(() => {
                this.$message({
                  type: 'info',
                  message: '取消修改'
                });       
              });
        },

        datePicked(value) {
            this.requestData(value);
        },

        requestData(date) {
            if (date === null) {
                date = new Date();
            }
            date = this.dateToStr(date);
            this.chose_date = date;
            // console.log(date);
            // console.log(this.personGuideData.id);

            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/select_weekly_recommend',
                params: {
                    userInfoId: this.personGuideData.id,
                    date: date,
                }
            })
            .then((res) => {
                console.log(res.data);
                this.pageInfo.guideData = res.data.data;
                this.sport = res.data.data.sport;
                this.menu = res.data.data.menu;
                console.log(this.sport, this.menu);
            })
            .catch(err => {
                // console.log(err);
            });
        }
    }
}