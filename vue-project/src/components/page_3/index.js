import detailDialog from "../detailUserInfo/index.vue"
import axiosM from "../../assets/global/globalFunc.js";

export default {
  name: 'page_3',
  components: {
    detailDialog
  },
  data () {
    return {
      tableData3: null,
      pageData3: null,
      pages: 0,
      detailDialogVisible: false,

      personDetail: null
    }
  },
  created() {
    this.refresh();
  },

  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.axios({
        method: 'post',
        url: '/NNC/rest/user_Info/user_info_page',
        params: {
         page: val,
        }
      })
      .then((res) => {
        this.tableData3 = res.data.data.list;
      })
      .catch(err => {
        // console.log(err);
      });
    },

    //格式转换
    formatDate(row) {
      let date = new Date(row.createTime);
      date = this.dateToStr(date);
      return date;
    },

    formatDietitian(row) {
      // console.log(row.dietitianName);
      return  row.dietitianName ? row.dietitianName : "无";
    },

    formatLoginFlag(row) {
      return row.loginFlag === 1 ? "手机" :
             row.loginFlag === 2 ? "微信" :
             row.loginFlag === 3 ? "QQ"  :
             row.loginFlag === 4 ? "微博" : "";
    },

    formatUserStatus(row) {
      return row.userStatus === 0 ? "准备期" :
             row.userStatus === 1 ? "正在期" :
             row.userStatus === 2 ? "过渡期" :
             row.userStatus === 3 ? "完成期" : "";
    },

    formatAddFlag(row) {
      return row.addFlag === 1 ? "是" : "否";
    },

    filterAgreeFlag(value, row) {
      return row.agreeFlag === value;
    },

    // formatAgreeFlag(row) {
    //   return row.agreeFlag === 1 ? "允许" : "禁止";
    // },

    changeUserAgree(index, row) {
      let agreeFlag = row.agreeFlag;
      let url = '/NNC/rest/user_Info/change_user_info_agree';
      console.log(row);
      this.axios({
        method: 'post',
        url: url,
        params: {
          userInfoId: row.id,
        }
      })
      .then((res) => {
        // console.log(res.data.data);
        this.emitRefresh();
      })
      .catch(err => {
        console.log(err);
      })
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

    // 查看详情（点击按钮）
    detailCheck(index, row){
      this.personDetail = row;
      console.log(this.personDetail);
      this.detailDialogVisible = true;
    },

    // 关闭detail的dialog
    closeDetail(){
      console.log("close");
      this.detailDialogVisible = false;
    },

    refresh() {
      this.axios({
        method: 'post',
        url: '/NNC/rest/user_Info/user_info_page',
        data: {
         page: '1'
        }
      })
      .then((res) => {
        this.pageData3 = res.data,
        this.tableData3 = res.data.data.list;
        this.pages = res.data.data.pages;
  
        for (let i = 0; i < this.tableData3.length; i++) {
          if (this.tableData3[i].dietitianId === null) {
            continue
          }
          this.axios({
            method: 'post',
            url: '/NNC/rest/user_Info/get_user_dietitian',
            params: {
              dietitianId: this.tableData3[i].dietitianId,
            }
          })
          .then((res) => {
            // console.log(res.data.data);
            this.$set(this.tableData3[i], "dietitianName", res.data.data);
          })
          .catch(err => {
            console.log(err);
          })
        }
      })
      .catch(err => {
        // console.log(err);
      })
    },

    emitRefresh() {
      let self = this;
      setTimeout(
        function(){ self.refresh(); }, 
        100);
    },
  }
}