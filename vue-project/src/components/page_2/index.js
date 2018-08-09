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
    },

    resetPsd(index, row) {
      let deId = row.id;
      let name = row.name;

      this.$confirm(`此操作将重置${name}的密码, 是否继续?`, '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(() => {
        this.axios({
          method: 'post',
          url: '/NNC/rest/dietitian/reset_dietitian_passwd',
          params: {
            dietitian_id: deId
          }
        }).then((res) => {
          if(res.data === 1) {
            console.log(true);
            this.$message({
              type: 'success',
              message: '重置密码成功!'
            });
          }
          else {
            this.$message({
              type: 'danger',
              message: '重置密码失败!'
            });
          }
        }).catch(err => {
          console.log(err);
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消重置密码'
        });          
      });
    }
  }
}