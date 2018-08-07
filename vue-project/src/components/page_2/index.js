export default {
  name: 'page_2',
  data () {
    return {
			tableData2: [{
        date: '2018-05-02',
        name: '刘小帅',
        address: '上海市普陀区金沙江路 1518 弄',
      }, {
        date: '2018-05-04',
        name: '刘小帅',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2018-05-01',
        name: '刘小帅',
        address: '上海市普陀区金沙江路 1518 弄',
      }, {
        date: '2018-05-03',
        name: '刘小帅',
        address: '上海市普陀区金沙江路 1518 弄'
      }]      
    }
  },
  created() {
    this.axios({
      method: 'post',
      url: '/NNC/rest/dietitian/dietitian_page',
      data: {
       page: '1'
      }
    })
    .then((res) => {
      console.log(res.data);
      this.tableData = res.data.data.list;
      console.log(this.tableData);
    })
    .catch(err => {
      // console.log(err);
    })
  },
  methods: {
    tableRowClassName({row, rowIndex}) {
      if (rowIndex === 1) {
        return 'warning-row';
      } else if (rowIndex === 3) {
        return 'success-row';
      }
      return '';
    }
  }
}