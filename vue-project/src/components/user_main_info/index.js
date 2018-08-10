export default{
    name: 'user_main_info',
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
            form1: {
                name: null,
                phoneNumber: null,
                birthDate: null,
                sex: null,
                height: null,
                weight: null,
                startDate: null,
                endDate: null,
            }
        }
    },
    props: {
        personDetail: this.personDetail
    },
    methods: {
    }
}