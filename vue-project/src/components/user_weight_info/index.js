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
            tableData: null
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
        },

        getData(){
            this.getTableData();
        },

        getTableData(){
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
                console.log(this.totalData);

            })
            .catch(err => {
                // console.log(err);
            });
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