import editDialog from "../Dialog/index.vue"

export default {
  name: 'page_1',
  components: {
    editDialog,
  },
  
  data () {
    return {
      pages: 0,
      tableData: null,
      dialogVisible: false,
      formVis_addUser: false,
      formVis_editUser: false,
      form3: {
        systemUserName: null,
        systemUserAuthority: null,
        systemUserRemark: null,
      },
    }
  },
  created() {
    this.refresh();
  },
  methods: {
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`);
      this.axios({
        method: 'post',
        url: '/NNC/rest/systemUser/system_user_page',
        data: {
         page: val,
        }
      })
      .then((res) => {
        console.log(val)
        this.tableData = res.data.data.list;
        for (let i = 0; i < this.tableData.length; i++) {
          let roleName = (this.tableData[i].role===2)? "总监":(this.tableData[i].role===3)?"文档管理员":"";
          this.$set(this.tableData[i], "roleName", roleName);
        }
      })
      .catch(err => {
        // console.log(err);
      });
    },


    handleRowClick(row) {
      let id = row.id;
      this.$router.push( {path: '/page_4', query: { partnum: 4 , dietitianId: id}} );
    },

    addSystemUser() {
      this.dialogVisible = true;
      this.formVis_addUser = true;
    },

    closeUserDialog() {
      this.dialogVisible = false;
      this.formVis_addUser = false;
      this.formVis_editUser = false;
    },

    editSystemUser(index, row) {
      this.form3.systemUserName = row.loginId; // loginId
      this.form3.systemUserAuthority = (row.role===2)? "总监" :
        (row.role===3)?"文档管理员":""; // role
      this.form3.systemUserRemark = row.note; // note

      this.form3.id = row.id;
      this.form3.passwd = row.passwd;
      this.form3.delFlag = row.delFlag;
      this.form3.createId = row.createId;
      this.form3.createTime = row.createTime;

      this.dialogVisible = true;
      this.formVis_editUser = true;
    },

    refresh() {
      console.log("refresh");
      this.axios({
        method: 'post',
        url: '/NNC/rest/systemUser/system_user_page',
        data: {
         page: '1'
        }
      })
      .then((res) => {
        this.pages = res.data.data.pages;
        this.tableData = res.data.data.list;
        for (let i = 0; i < this.tableData.length; i++) {
          let roleName = (this.tableData[i].role===2)? "总监":(this.tableData[i].role===3)?"文档管理员":"";
          this.$set(this.tableData[i], "roleName", roleName);
        }
        console.log(this.tableData);
      })
      .catch(err => {
        // console.log(err);
      });
    }
  }
}