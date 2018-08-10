import userMainInfo from "../user_main_info/index.vue"

export default {
    components: {
        userMainInfo
    },
    data() {
        return {
          activeName: 'first'
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