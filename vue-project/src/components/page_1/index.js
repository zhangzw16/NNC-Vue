export default {
  name: 'page_1',
  data () {
    return {
      tableData: null
    }
  },
  created() {
    this.axios({
      method: 'post',
      url: '/NNC/rest/systemUser/system_user_page',
      data: {
       page: '1'
      }
    })
    .then((res) => {
      this.tableData = res.data.data.list;
      console.log(this.tableData);
    })
    .catch(err => {
      // console.log(err);
    })
  }
}