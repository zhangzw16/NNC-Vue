export default {
  name: 'page_2',
  components: {
  },
  data () {
    return {
      form: {
        id: null,
        dieName: null,
        number: null,
        date1: null,
        date2: null,
        note: null,
        pageNum: 1,
        pages: 0
      },
      dialogVisible: false,
      tableData2: null,
      isAdd: false,
    }
  },
  created() {
    this.refresh();
  },
  methods: {
    // tableRowClassName({row, rowIndex}) {
    //   if (rowIndex === 1) {
    //     return 'warning-row';
    //   } else if (rowIndex === 3) {
    //     return 'success-row';
    //   }
    //   return '';
    // },

    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`);
      this.axios({
        method: 'post',
        url: '/NNC/rest/dietitian/dietitian_page',
        data: {
         page: val,
        }
      })
      .then((res) => {
        this.tempData = res.data.data.list; 
        this.pageNum = res.data.data.pageNum;

        
        for (let i = 0; i < this.tempData.length; i++) {
          // console.log(this.tempData[i].workStartDate);
          // console.log(new Date(this.tempData[i].workStartDate));

          this.axios({
            method: 'post',
            url: '/NNC/rest/user_Info/user_info_page_on_dietitian',
            data: {
              page: val,
              userStatus: '1',
              dietitianId: this.tempData[i].id
            }
          })
          .then((res) => {
            this.$set(this.tempData[i], "beingReduced", res.data.data.list.length);
            this.$set(this.tempData[i], "workStartDate", this.dateToStr(new Date(this.tempData[i].workStartDate)));
            if(this.tempData[i].workEndDate !== null)
              this.$set(this.tempData[i], "workEndDate", this.dateToStr(new Date(this.tempData[i].workEndDate)));
          })
          .catch(err => {
            console.log(err);
          })
        }
        this.tableData2 = this.tempData;
      })
      .catch(err => {
        // console.log(err);
      });
    },


    handleRowClick(row) {
      let id = row.id;
      console.log("route", row);
      this.$router.push( {path: '/page_4', query: { partnum: 4 , dietitianId: id, name: row.name, phone: row.phoneNo}} );
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

    editDietitian(){
      console.log(this.isAdd);
      let form = this.form;
      let id = form.id;
      let url = null;
      let cId = this.isAdd? 1: 0;
      if (this.isAdd) {
        url = '/NNC/rest/dietitian/dietitian_save';
      }
      else {
        url = '/NNC/rest/dietitian/edit_dietitian_info_save'
      }
      let date1 = null, date2 = null;
      console.log(form.date1.getTime());
      if(form.date1 !== null) {
        date1 = this.dateToStr(form.date1);
      }
      if(form.date2 !== null) {
        date2 = this.dateToStr(form.date2);
      }
      
      let params = {
        id: id,
        createId: cId,
        name: form.dieName,
        loginId: form.number,
        workStartDate: date1,
        workEndDate: date2,
        note: form.note,
      };
      
      this.aixosModel(url, params, '创建营养师');
      this.emitRefresh();
      this.dialogVisible = false;
    },
    
    handleClose() {
      this.form = {
        id: null,
        dieName: null,
        number: null,
        date1: null,
        date2: null,
        note: null,
      };
      console.log("before close");
      this.dialogVisible = false;
    },

    dialogShowAdd() {
      this.isAdd = true;
      this.dialogVisible = true;
    },

    handleEdit(index, row) {
      this.isAdd = false;
      this.form.id = row.id;
      this.form.dieName = row.name;
      this.form.number = row.phoneNo;
      this.form.date1 = new Date(row.workStartDate);
      this.form.date2 = row.workEndDate? new Date(row.workEndDate): null;
      this.form.note = row.note;
      this.dialogVisible = true;
    },

    refresh() {
      this.axios({
        method: 'post',
        url: '/NNC/rest/dietitian/dietitian_page',
        data: {
         page: this.pageNum ? this.pageNum : 1
        }
      })
      .then((res) => {
        console.log(this.pageNum)
        let tempData = res.data.data.list;
        this.pages = res.data.data.pages;
        this.pageNum = res.data.data.pageNum;
  
        for (let i = 0; i < tempData.length; i++) {
          // console.log(tempData[i].workStartDate);
          // console.log(new Date(tempData[i].workStartDate));

          this.axios({
            method: 'post',
            url: '/NNC/rest/user_Info/user_info_page_on_dietitian',
            data: {
              page: this.pageNum,
              userStatus: '1',
              dietitianId: tempData[i].id
            }
          })
          .then((res) => {
            this.$set(tempData[i], "beingReduced", res.data.data.list.length);
            this.$set(tempData[i], "workStartDate", this.dateToStr(new Date(tempData[i].workStartDate)));
            if(tempData[i].workEndDate !== null)
              this.$set(tempData[i], "workEndDate", this.dateToStr(new Date(tempData[i].workEndDate)));
          })
          .catch(err => {
            console.log(err);
          })
        }
        this.tableData2 = tempData;
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
        data: params
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
      let self = this;
      setTimeout(
        function(){ self.refresh(); }, 
        1000);
    },

    dateToStr(datetime) { 
      var year = datetime.getFullYear();
      var month = datetime.getMonth()+1;//js从0开始取 
      var date = datetime.getDate(); 
      var hour = datetime.getHours(); 
      var minutes = datetime.getMinutes(); 
      var second = datetime.getSeconds();
      
      if(month<10){
       month = "0" + month;
      }
      if(date<10){
       date = "0" + date;
      }
      if(hour <10){
       hour = "0" + hour;
      }
      if(minutes <10){
       minutes = "0" + minutes;
      }
      if(second <10){
       second = "0" + second ;
      }
      
      var time = year+"-"+month+"-"+date; //2009-06-12 17:18:05
     // alert(time);
      return time;
     }
  }
}