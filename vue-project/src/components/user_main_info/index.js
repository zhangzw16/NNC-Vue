import { timingSafeEqual } from "crypto";

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

            pageInfo:{
                name: null,
                phoneNumber: null,
                birthDate: null,
                gender: null,
                height: null,
                weight: null,
                startDate: null,
                endDate: null,

                bloodFat: null,
                bloodPressure: null,
                bloodSugar: null,
                bloodUricAcid: null,
                hepaticAdiposeInfiltration: null,

                // 备注
                note: null,
            }
        }
    },
    props: {
        personDetail: this.personDetail
    },
    methods: {
        dateToStr(datetime) { 
            var year = datetime.getFullYear();
            var month = datetime.getMonth()+1;//js从0开始取 
            var date = datetime.getDate(); 
            var hour = datetime.getHours(); 
            var minutes = datetime.getMinutes(); 
            var second = datetime.getSeconds();
            
            if(month<10){
             month = "0" + month;
            }
            if(date<10){
             date = "0" + date;
            }
            if(hour <10){
             hour = "0" + hour;
            }
            if(minutes <10){
             minutes = "0" + minutes;
            }
            if(second <10){
             second = "0" + second ;
            }
            
            var time = year+"-"+month+"-"+date; //2009-06-12 17:18:05
           // alert(time);
            return time;
        },
        getPageInfo(){
            this.pageInfo.name = this.personDetail.name;
            this.pageInfo.phoneNumber = "无";
            if(this.personDetail.birthday !== null)
            {
                let date = new Date(this.personDetail.birthday);
                this.pageInfo.birthDate = this.dateToStr(date);
            }
            else
            {
                this.pageInfo.birthDate = "";
            }
            
            if(this.personDetail.sex === 1)
            {
                this.pageInfo.gender = "男";
            }
            else
            {
                this.pageInfo.gender = "女";
            }
            this.pageInfo.height = this.personDetail.height + "(cm)";
            this.pageInfo.weight = this.personDetail.weight + "(kg)";
            if(this.personDetail.startDate !== null)
            {
                let date = new Date(this.personDetail.startDate)
                this.pageInfo.startDate = this.dateToStr(date);
            }
            else
            {
                this.pageInfo.startDate = "";
            }
            if(this.personDetail.endDate !== null)
            {
                let date = new Date(this.personDetail.endDate);
                this.pageInfo.endDate = this.dateToStr(date);
            }
            else
            {
                this.pageInfo.endDate = "";
            }

            if(this.personDetail.addFlag === 1)
            {
                switch(this.personDetail.bloodPressure)
                {
                    case 1:
                        this.pageInfo.bloodPressure = "偏低";
                        break;

                    case 2:
                        this.pageInfo.bloodPressure = "正常";
                        break;
                    
                    case 3:
                        this.pageInfo.bloodPressure = "偏高";
                        break;
                }
                switch(this.personDetail.bloodFat)
                {
                    case 1:
                    this.pageInfo.bloodFat = "偏低";
                    break;

                    case 2:
                        this.pageInfo.bloodFat = "正常";
                        break;
                    
                    case 3:
                        this.pageInfo.bloodFat = "偏高";
                        break;
                }
                switch(this.personDetail.bloodSugar)
                {
                    case 1:
                    this.pageInfo.bloodSugar = "偏低";
                    break;

                    case 2:
                        this.pageInfo.bloodSugar = "正常";
                        break;
                    
                    case 3:
                        this.pageInfo.bloodSugar = "偏高";
                        break;
                }
                switch(this.personDetail.bloodUricAcid)
                {
                    case 1:
                    this.pageInfo.bloodUricAcid = "偏低";
                    break;

                    case 2:
                        this.pageInfo.bloodUricAcid = "正常";
                        break;
                    
                    case 3:
                        this.pageInfo.bloodUricAcid = "偏高";
                        break;
                }
                switch(this.personDetail.hepaticAdiposeInfiltration)
                {
                    case 1:
                    this.pageInfo.hepaticAdiposeInfiltration = "正常";
                    break;

                    case 2:
                        this.pageInfo.hepaticAdiposeInfiltration = "轻度";
                        break;
                    
                    case 3:
                        this.pageInfo.hepaticAdiposeInfiltration = "中度";
                        break;
                    
                    case 4:
                        this.pageInfo.hepaticAdiposeInfiltration = "重度";
                        break;
                }
            }
            
            if(this.personDetail.note !== null)
            {
                this.pageInfo.note = this.personDetail.note;
            }
            else
            {
                this.pageInfo.note = "";
            }
            console.log(this.pageInfo);
        },
        editNote(){
            let self = this;
            this.$prompt('备注', '备注', {
                confirmButtonText: '保存',
                cancelButtonText: '取消',
                value: self.pageInfo.note
            }).then(({ value }) => {
                
                this.$message({
                  type: 'success',
                  message: "修改成功"
                });
            }).catch(() => {
                this.$message({
                  type: 'info',
                  message: '取消输入'
                });       
              });
        }
    }
}