export default {
  name: 'page_3',
  data () {
    return {
      tableData3: null
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
      // console.log(res.data);
      this.tableData3 = res.data.data.list;
      console.log(this.tableData3);
    })
    .catch(err => {
      // console.log(err);
    })
  }
}