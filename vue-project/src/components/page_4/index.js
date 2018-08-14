import detailDialog from "../user_info/index.vue"

export default {
  name: 'page_4',
  components: {
    detailDialog
  },
  data () {
    return {
      radio:"1",
      userStatus:"1",
      tableData4: null,
      dietitianId: null,
      dietitianName: '',
      dietitianPhone: '',

      // detailDialog
      detailDialogVisible: false,
      personDetail: null,
    }
  },
  created() {
    this.dietitianId = this.$router.history.current.query.dietitianId;
    this.requestData(1);
  },
  methods: {
    userFilter(status) {
      // console.log(status);
      this.requestData(status);
    },

    handleRadioChange(value){
      console.log(value);
      this.userStatus = value;
      this.requestData(this.userStatus)
    },

    detailCheck(index, row){
      this.personDetail = row;
      this.detailDialogVisible = true;
      this.$refs.detailDialog.updateSubframes();
    },

    closeDetail(){
      // console.log("close");
      this.detailDialogVisible = false;
    },

    requestData(status) {
      this.axios({
        method: 'post',
        url: '/NNC/rest/user_Info/user_info_page_on_dietitian',
        params: {
          page: '1',
          userStatus: status,
          dietitianId: this.dietitianId,
        }
      })
      .then((res) => {
        this.tableData4 = res.data.data.list;
        console.log(this.tableData4);
  
        for(let i = 0; i < this.tableData4.length; i++) {
          // 查询手机号
          this.axios({
            method: 'post',
            url: '/NNC/rest/user_Info/get_login_info_id',
            data: {
                userLoginInfoId: this.tableData4[i].userLoginInfoId,
            }
          })
          .then((res) => {
  
            console.log("phoneNo", res.data.data);
            // this.pageInfo.phoneNumber = res.data.data;
            this.$set(this.tableData4[i], "phoneNo", res.data.data);
          })
          .catch(err => {
            console.log(err);
          });
        }
      })
      .catch(err => {
        // console.log(err);
      })
    }
  }
}