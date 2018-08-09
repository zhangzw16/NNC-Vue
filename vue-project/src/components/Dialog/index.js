export default {
    name: "Dialog",
    data() {
        return {
          form: {
            name: '',
            number: ''
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