export default {
    data() {
        return {
          activeName: 'second'
        };
    },
    methods: {
        // tab
        handleClick(tab, event) {
            console.log(tab, event);
        },
        // 关闭所有
        handleClose() {
            this.$emit("closeDialog");
        }
    },
    props: {
      dialogVisible:{
        type: Boolean,
        default: false
      },
    }
  }