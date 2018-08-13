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
                // 备注
                note: null,
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

        editNote() {
            let self = this;
            this.$prompt('备注', '备注', {
                confirmButtonText: '保存',
                cancelButtonText: '取消',
                value: self.pageInfo.note
            }).then(({ value }) => {
                self.axios({
                    method: 'post',
                    url: '/NNC/rest/user_Info/edit_user_note_save',
                    data: {
                        userInfoId : self.personDetail.id,
                        note : value
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
                self.$emit("noteChanged");
                self.pageInfo.note = value;
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
        }
    }
}