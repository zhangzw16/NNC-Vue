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
      this.tableData2 = res.data.data.list;
      console.log(this.tableData2);

      for (let i = 0; i < this.tableData2.length; i++) {
        this.axios({
          method: 'post',
          url: '/NNC/rest/user_Info/user_info_page_on_dietitian',
          params: {
            page: '1',
            userStatus: '1',
            dietitianId: this.tableData2[i].id
          }
        })
        .then((res) => {
          // console.log(res.data.data.list);
          this.tableData2[i].beingReduced = res.data.data.list.length;
        })
        .catch(err => {
          // console.log(err);
        })
      }

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