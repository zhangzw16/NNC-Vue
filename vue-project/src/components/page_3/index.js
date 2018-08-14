import detailDialog from "../user_info/index.vue"
import axiosM from "../../assets/global/globalFunc.js";

export default {
  name: 'page_3',
  components: {
    detailDialog
  },
  data () {
    return {
      tableData3: null,
      pages: 0,
      pageNum: 1,
      userStatus: "",
      dietitianId: null,
      message: null,
      detailDialogVisible: false,
      dietitianDialogVisible: false,

      personDetail: null,

      radio1: "",
      total:null,
      value: "无",
      valueDialog: "无",

      optionsDietitian: [],
      rowInfo4Dietitian: null,
      dietitianId4Dietitian: null,
    }
  },
  created() {
    this.refresh();
  },

  methods: {
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
    },
    
    handleCurrentChange(val) {
      // val
      this.requestData(val);
    },

    Search(){
      this.requestData(1);
    },

    handleRadioChange(value){
      // console.log(value)
      this.userStatus = value;      
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

    fomatContactWay(row) {
      let head = row.contactWay === 0 ? "手机号：" :
                 row.contactWay === 1 ? `微信：${row.account}` :
                 row.contactWay === 2 ? `QQ：${row.account}`  : "";
      let number = '';
      if (row.contactWay == 0) {
        number = row.telNo ? row.telNo: "无";
      }
      return head + number;
    },

    filterAgreeFlag(value, row) {
      return row.agreeFlag === value;
    },

    // formatAgreeFlag(row) {
    //   return row.agreeFlag === 1 ? "允许" : "禁止";
    // },

    SearchSelectChange(event){
      this.dietitianId = event;
    },

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

    handleClose() {
      // console.log("before close");
      this.dietitianDialogVisible = false;
    },

    changeDietitian(index, row) {
      this.rowInfo4Dietitian = row;
      this.dietitianDialogVisible = true;
    },

    dietitianSelect(event) {
      this.dietitianId4Dietitian = event;
    },

    saveDietitian() {
      let url = '/NNC/rest/user_Info/change_user_dietitian_save';

      this.$confirm(`此操作将修改用户的营养师, 是否继续?`, '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(() => {
        this.axios({
          method: 'post',
          url: url,
          params: {
            userInfoId: this.rowInfo4Dietitian.id,
            dietitianId: this.dietitianId4Dietitian,
            currentPage: 1,
          }
        })
        .then((res) => {
          // console.log(res.data.data);
          if(res.status === 200) {
            this.$message({
            type: 'success',
            message: `修改营养师成功!`
            });
            this.emitRefresh();
        }
        else {
            self.$message({
            type: 'danger',
            message: `修改营养师失败!`
            });
        }
        })
        .catch(err => {
          console.log(err);
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消修改营养师'
        });          
      });

      this.handleClose();
    },

    // 查看详情（点击按钮）
    change(event, row){
      // console.log(event)
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
      this.requestData(this.pageNum ? this.pageNum : 1 )

      this.axios({
        method: 'post',
        url: '/NNC/rest/dietitian/dietitian_page',
        data: {
         page: '1'
        }
      })
      .then((res) => {
        let dietitianData = res.data.data.list;
        this.optionsDietitian.push({
          value: '',
          label: "无",
        })
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

    requestData(page) {
      this.axios({
        method: 'post',
        url: '/NNC/rest/user_Info/user_info_page',
        data: {
         page: page,
         userStatus: this.userStatus,
         dietitianId: this.dietitianId,
         message: this.message
        }
      })
      .then((res) => {
        this.total = res.data.data.total;
        this.tableData3 = res.data.data.list;
        this.pages = res.data.data.pages;
        this.pageNum = res.data.data.pageNum;
        console.log(this.tableData3);
  
        for (let i = 0; i < this.tableData3.length; i++) {
          if(this.tableData3[i].userLoginInfoId === null)
          {
          }
          else{
            // 查询手机号
            this.axios({
              method: 'post',
              url: '/NNC/rest/user_Info/get_login_info_id',
              params: {
                userLoginInfoId: this.tableData3[i].userLoginInfoId,
              }
            })
            .then((res) => {
              console.log(res)
              // console.log(res.data.data);
              this.$set(this.tableData3[i], "telNo", res.data.data);
            })
            .catch(err => {
              console.log(err);
            })
          }


          // 查询营养师
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
    }
  }
}