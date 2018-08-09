export default {
  name: 'page_4',
  data () {
    return {
      tableData4: null
    }
  },
  created() {
    let id = this.$router.history.current.query.dietitianId;
    console.log(id);
    this.axios({
      method: 'post',
      url: '/NNC/rest/user_Info/user_info_page_on_dietitian',
      params: {
        page: '1',
        userStatus: '1',
        dietitianId: id
      }
    })
    .then((res) => {
      this.tableData4 = res.data.data.list;
      console.log(this.tableData4);

      for(let i = 0; i < this.tableData4.length; i++) {
        // console.log(this.tableData4[i].id);
      }
    })
    .catch(err => {
      // console.log(err);
    })
  },
  methods: {
    userFilter(status) {
      console.log(status);
      this.axios({
        method: 'post',
        url: '/NNC/rest/user_Info/user_info_page_on_dietitian',
        params: {
          page: '1',
          userStatus: status,
          dietitianId: this.$router.history.current.query.dietitianId
        }
      })
      .then((res) => {
        this.tableData4 = res.data.data.list;
        console.log(this.tableData4);
  
        for(let i = 0; i < this.tableData4.length; i++) {
          // console.log(this.tableData4[i].id);
        }
      })
      .catch(err => {
        // console.log(err);
      })
    }
  }
}