export default {
  name: 'page_2',
  components: {
  },
  data () {
    return {
      form: {
        dieName: null,
        number: null,
        date1: null,
        date2: null,
        note: null,
      },
      dialogVisible: false,
      tableData2: null,
      addDietitianDialogVisible: false,
      formVis_dietitian: false,
    }
  },
  created() {
    this.refresh();
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
        let url = '/NNC/rest/dietitian/reset_dietitian_passwd';
        let params = {
          dietitian_id: deId
        };
        this.aixosModel(url, params, '重置密码');
        this.emitRefresh();
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消重置密码'
        });          
      });
    },

    addDietitian(){
      this.addDietitianDialogVisible = true;
      this.formVis_dietitian = true;
    },

    closeAddDietitianDialog(){
      this.addDietitianDialogVisible = false;
      this.formVis_dietitian = false;
    },

    refresh() {
      this.axios({
        method: 'post',
        url: '/NNC/rest/dietitian/dietitian_page',
        data: {
         page: '1'
        }
      })
      .then((res) => {
        this.tempData = res.data.data.list;
  
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

    aixosModel(url, params, info) {
      console.log("axiosModel params",params);
      this.axios({
        method: 'post',
        url: url,
        params: params
      })
      .then((res) => {
        if(res.data === 1 || res.data === true) {
          this.$message({
            type: 'success',
            message: `${info}成功!`
          });
        }
        else {
          this.$message({
            type: 'danger',
            message: `${info}失败!`
          });
        }
      })
      .catch(err => {
        console.log(err);
      })
    },

    emitRefresh() {
      setTimeout(
        function(){ this.refresh(); }, 
        1000);
    },
  }
}