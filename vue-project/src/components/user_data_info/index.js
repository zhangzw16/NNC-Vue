import { timingSafeEqual } from "crypto";

export default {
    name: 'user_data_info',
    data () {
        return{
            personFoodData: {
                name: null,
                id: null,
            },

            pageInfo:{
                name: null,
                exercise: null,
                drankWater: null,
                comfortLevel: null,
                testPaperValue: null,
                weightL: null,
                comments: null,
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

            breakfast: '',
            lunch: '',
            dinner: '',
        }
    },
    created() {
        // console.log('user_data_info id', this.personFoodData.id);
    },
    props: {
        personFoodData: this.personFoodData
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
            this.pageInfo.name = this.personFoodData.name;
        },

        editComment() {
            this.$prompt('备注', '备注', {
                confirmButtonText: '保存',
                cancelButtonText: '取消',
                value: this.pageInfo.comments
            }).then(({ value }) => {
                this.axios({
                    method: 'post',
                    url: '/NNC/rest/user_Info/edit_user_comments_save',
                    data: {
                        id: this.pageInfo.id,
                        userInfoId: this.personFoodData.id,
                        comments : value
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
                this.pageInfo.comments = value;
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
            // console.log(date);

            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/select_user_data',
                data: {
                    userInfoId: this.personFoodData.id,
                    date: date,
                }
            })
            .then((res) => {
                console.log(res.data.data);
                let data = res.data.data;
                this.breakfast = data.breakfast;
                this.lunch = data.lunch;
                this.dinner = data.dinner;
                this.pageInfo.exercise = data.exercise;
                this.pageInfo.drankWater = (data.drankWater === null) ? "" :
                    data.drankWater + "(ml)";
                this.pageInfo.comfortLevel = data.comfortLevel;
                this.pageInfo.testPaperValue = data.testPaperValue;
                this.pageInfo.weight = data.weight === null ? "" :
                    data.weight + "(kg)";
                this.pageInfo.comments = data.comments;
                this.pageInfo.id = data.id;
            })
            .catch(err => {
                // console.log(err);
            });
        }
    }
}