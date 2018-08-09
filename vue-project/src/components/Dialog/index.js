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
      dialogFormVisible:{
        type: Boolean,
        default: false
      }
    },
    methods: {
      handleClose() {
        this.$emit("closeDialog");
      }
    }
  }