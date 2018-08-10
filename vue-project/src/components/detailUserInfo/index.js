import userMainInfo from "../user_main_info/index.vue"

export default {
    components: {
        userMainInfo
    },
    data () {
        return{
            personDetail: {
                account: null,
                addFlag: null,
                age: null,
                agreeFlag: null,
                birthday: null,
                bloodFat: null,
                bloodPressure: null,
                bloodSugar: null,
                bloodUricAcid: null,
                buyFlag: null,
                contactWay: null,
                createId: null,
                createTime: null,
                dataStartDate: null,
                dayCount: null,
                dietitianId: null,
                email: null,
                endDate: null,
                fileName: null,
                filePath: null,
                height: null,
                hepaticAdiposeInfiltration: null,
                id: null,
                idealBodyWeight: null,
                loginCount: null,
                loginFlag: null,
                lookFlag: null,
                name: null,
                note: null,
                phase: null,
                sex: null,
                startDate: null,
                thirdPartyLoginId: null,
                topFlag: null,
                updateFlag: null,
                updateId: null,
                updateTime: null,
                userLoginInfoId: null,
                userStatus: null,
                vitality: null,
                weight: null
            },
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
        // tab
        handleClick(tab, event) {
            let tab_id = event.target.getAttribute('id');
            switch(tab_id)
            {
                case "tab-first":
                    this.$refs.mainInfo.getPageInfo();
                    break;
                default:
                    break;
            }
        },
        // 关闭所有
        handleClose() {
            this.$emit("closeDialog");
        }
    }
  }