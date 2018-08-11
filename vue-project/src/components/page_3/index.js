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

      personDetail: null,

      optionsDietitian: [],
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

    handleCommand(row, command) {
      console.log(row);
      this.$message('click on item ' + command);
    },

    // 查看详情（点击按钮）
    change(event, row){
      let newDietitianId = event;
      let userId = row.id;

      this.$confirm(`此操作将更换用户${row.name}的营养师, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 发送更换营养师请求
        
        this.axios({
          method: 'post',
          url: '/NNC/rest/user_Info/change_user_dietitian_save',
          params: {
            dietitianId: newDietitianId,
            userInfoId: userId,
            currentPage: '',
          }
        })
        .then((res) => {

          this.$message({
            type: 'success',
            message: '更换营养师成功!'
          });
        })
        .catch(err => {
          console.log(err);
          this.$message({
            type: 'danger',
            message: '更换营养师失败!'
          });
        })
        // this.emitRefresh();
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消更换'
        });          
      });
    },

    //打开detail的dialog
    detailCheck(index, row){
      this.personDetail = row;
      this.detailDialogVisible = true;
      this.$refs.detailDialog.updateSubframes();
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
        console.log("user refresh");
        this.pageData3 = res.data,
        this.tableData3 = res.data.data.list;
        this.pages = res.data.data.pages;
        console.log(this.tableData3);
  
        for (let i = 0; i < this.tableData3.length; i++) {
          if (this.tableData3[i].dietitianId === null) {
            this.$set(this.tableData3[i], "dietitianName", "无");
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
      });

      this.axios({
        method: 'post',
        url: '/NNC/rest/dietitian/dietitian_page',
        data: {
         page: '1'
        }
      })
      .then((res) => {
        let dietitianData = res.data.data.list;
  
        for (let i = 0; i < dietitianData.length; i++) {
          let dietitian = {
            value: dietitianData[i].id,
            label: dietitianData[i].name,
          }
          this.optionsDietitian.push(dietitian);
        }
      })
      .catch(err => {
        // console.log(err);
      });

      // console.log(this.optionsDietitian)
    },

    emitRefresh() {
      let self = this;
      setTimeout(
        function(){ self.refresh(); }, 
        1000);
    },
  }
}