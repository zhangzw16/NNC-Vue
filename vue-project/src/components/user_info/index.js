import userMainInfo from "../user_main_info/index.vue"
import userDataInfo from "../user_data_info/index.vue"

export default {
    components: {
        userMainInfo,
        userDataInfo
    },
    data () {
        return{
            personDetail: {},
            personFoodData: {},
            activeName: "first",
        }
    },
    props: {
        personDetail: this.personDetail,
        dialogVisible:{
            type: Boolean,
            default: false
        },
    },
    methods: {
        //更新数据
        detailDataChanged(){
            this.$emit("detailDataChanged");       
        },
        // tab
        handleClick(tab, event) {
            // let tab_id = event.target.getAttribute('id');
            // // console.log(tab, event);
            let tabName = tab.name;
            switch(tabName)
            {
                case "first":
                    break;
                case "second":
                    this.$refs.dataInfo.requestData(null);
                default:
                    break;
            }
        },

        // 关闭所有
        handleClose() {
            this.$emit("closeDialog");
        },

        updateMainInfo(){
            this.personFoodData.name = this.personDetail.name;
            this.personFoodData.id = this.personDetail.id;
            this.$refs.mainInfo.getPageInfo();
            this.$refs.dataInfo.getPageInfo();
        },

        //更新子窗口
        updateSubframes() {
            this.activeName = "first";
            let self = this;
            setTimeout(
                function(){
                    self.updateMainInfo();
                },0);
        }
    }
  }