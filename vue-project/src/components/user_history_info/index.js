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

        formatDate(row) {
            let date = new Date(row.createTime);
            date = this.dateToStr(date);
            return date;
          },

        requestData() {
            console.log("request data in histoy page");
            this.axios({
                method: 'post',
                url: '/NNC/rest/user_Info/show_user_history_info',
                params: {
                    userInfoId: this.personHistoryData.id,
                }
            })
            .then((res) => {
                console.log(res.data.data);
                this.historyTableData = res.data.data;

                for( let i = 0; i < this.historyTableData.length; i++) {
                    let sDate = new Date(this.historyTableData[i].transitionStartDate);
                    sDate = this.dateToStr(sDate);
                    let eDate = new Date(this.historyTableData[i].transitionEndDate);
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

        showHistoryDetail(index, row) {
            this.historyDialogVisible = true;
            this.rowData = row;
        }
    }
}