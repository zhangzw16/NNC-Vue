import editDialog from "../Dialog/index.vue"

export default {
  name: 'page_1',
  components: {
    editDialog,
  },
  
  data () {
    return {
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
        this.tableData = res.data.data.list;
        console.log(this.tableData);
      })
      .catch(err => {
        // console.log(err);
      });
    }
  }
}