export default {
    name: "Dialog",
    data() {
      return {
        // form1: {
        //   dieName: null,
        //   number: null,
        //   date1: null,
        //   date2: null,
        //   note: null,
        // },
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
      form3: this.form3,
      dialogVisible:{
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
        // console.log(this.form3);
        this.form2 = {
          systemUserName: null,
          systemUserAuthority: null,
          systemUserRemark: null,
        };
        this.form3 = {
          systemUserName: null,
          systemUserAuthority: null,
          systemUserRemark: null,
        };
        this.$emit("closeDialog");
      },

      saveUser() {
        let form = this.form2;
        let url = '/NNC/rest/systemUser/system_user_save';
        let role = this.roleStr2Role(form.systemUserAuthority);
        let params = {
          loginId: form.systemUserName,
          role: role,
          note: form.systemUserRemark,
        };
        this.aixosModel(url, params, '创建管理员');
        this.emitRefresh();
        this.handleClose();
      },

      editUser() {
        let form = this.form3;
        let url = '/NNC/rest/systemUser/edit_system_user_save';
        let date = new Date(form.createTime);
        let role = this.roleStr2Role(form.systemUserAuthority);
        let params = {
          id: form.id,
          passwd: form.passwd,
          delFlag: form.delFlag,
          createId: form.createId,
          createTime: date,
          loginId: form.systemUserName,
          role: role,
          note: form.systemUserRemark,
        };
        this.aixosModel(url, params, '修改管理员');
        this.emitRefresh();
        this.handleClose();
      },

      resetUserPasswd() {
        let params = {
          param: this.form3.id,
        };
        let url = '/NNC/rest/systemUser/system_user_reset_passwd';
        this.$confirm(`此操作将重置${name}的密码, 是否继续?`, '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'danger'
        }).then(() => {
          this.aixosModel(url, params, '重置密码');
          this.emitRefresh();
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消重置密码'
          });
        });
        this.handleClose();
      },

      deleteSysUser() {
        let params = {
          param: this.form3.id,
        };
        let url = '/NNC/rest/systemUser/system_user_delete';
        let name = this.form3.systemUserName;
        this.$confirm(`此操作将删除用户：${name}, 是否继续?`, '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'danger'
        }).then(() => {
          this.aixosModel(url, params, '删除管理员');
          this.emitRefresh();
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除管理员'
          });
        });
        this.handleClose();
      },

      emitRefresh() {
        let self = this;
        setTimeout(
          function(){ self.$emit("addUserSuccess") ;}, 
          1000);
      },

      roleStr2Role(roleString) {
        let role = (roleString === "总监")? 2:
                   (roleString === "文档管理员")? 3: -1; 
        return role;
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
      }
    }
  }
  