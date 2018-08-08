export default {
  name: 'page_2',
  data () {
    return {
			tableData2: null      
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
      // console.log(res.data);
      this.tempData = res.data.data.list;
      // console.log(this.tempData);

      for (let i = 0; i < this.tempData.length; i++) {
        this.axios({
          method: 'post',
          url: '/NNC/rest/user_Info/user_info_page_on_dietitian',
          params: {
            page: '1',
            userStatus: '1',
            dietitianId: this.tempData[i].id
          }
        })
        .then((res) => {
          this.$set(this.tempData[i], "beingReduced", res.data.data.list.length)
        })
        .catch(err => {
          console.log(err);
        })
      }
      this.tableData2 = this.tempData;
      console.log(this.tableData2);
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
    },
    handleRowClick(row) {
      let id = row.id;
      this.$router.push( {path: '/page_4', query: { partnum: 4 , dietitianId: id}} );
    }
  }

}