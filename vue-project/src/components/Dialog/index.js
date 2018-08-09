export default {
    name: "Dialog",
    data() {
        return {
          form: {
            systemUserName: '',
            systemUserAuthority: '',
            systemUserRemark: ''
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
      }
    }
  }