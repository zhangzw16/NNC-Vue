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
      closeDialog(){
        console.log("dia close");
        this.$emit("closeDialog");
      },
      handleClose() {
        this.$emit("closeDialog");
      }
    }
  }