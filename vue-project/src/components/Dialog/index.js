export default {
    name: "Dialog",
    data() {
      return {
        form1: {
          dieName: null,
          number: null,
          date1: null,
          date2: null,
          note: null,
        },
        form2: {
          systemUserName: null,
          systemUserAuthority: null,
          systemUserRemark: null,
        },
        form3: {
          systemUserName: null,
          systemUserAuthority: null,
          systemUserRemark: null,
        },
        formLabelWidth: '120px'
      };
    },
    props: {
      dialogVisible:{
        type: Boolean,
        default: false
      },
      formVis_dietitian:{
        type: Boolean,
        default: false
      },
      formVis_addUser:{
        type: Boolean,
        default: false
      },
      formVis_editUser:{
        type: Boolean,
        default: false
      },
    },
    methods: {
      handleClose() {
        this.$emit("closeDialog");
      },

      saveUser() {
        let roleString = this.form2.systemUserAuthority
        let role = (roleString === "总监")? 2:
                   (roleString === "文档管理员")? 3: -1;
        
        this.axios({
          method: 'post',
          url: '/NNC/rest/systemUser/system_user_save',
          params: {
            loginId: this.form2.systemUserName,
            role: role,
            note: this.form2.systemUserRemark,
          }
        })
        .then((res) => {
          if(res.data === 1) {
            this.$message({
              type: 'success',
              message: '创建管理员成功!'
            });
          }
          else {
            this.$message({
              type: 'danger',
              message: '创建管理员失败!'
            });
          }
        })
        .catch(err => {
          console.log(err);
        })
        this.$emit("addUserSuccess")
        this.handleClose();
      }
    }
  }