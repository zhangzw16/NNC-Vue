import detailDialog from "../detailUserInfo/index.vue"
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
    this.axios({
      method: 'post',
      url: '/NNC/rest/user_Info/user_info_page',
      data: {
       page: '1'
      }
    })
    .then((res) => {
      console.log(res.data);
      this.pageData3 = res.data,
      this.tableData3 = res.data.data.list;
      this.pages = res.data.data.pages;
      // console.log(this.pages)
      // console.log(this.pageData3),
      // console.log(this.tableData3)
    })
    .catch(err => {
      // console.log(err);
      
    })
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
    }
  }
}