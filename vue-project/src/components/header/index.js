export default {
  name: 'header',
  data () {
    return {
      date: null
    }
  },
  created() {
    var temp = new Date(),
        seperator = "-",
        year = temp.getFullYear(),
        month = temp.getMonth() + 1,
        strDate = temp.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    this.date = year + seperator + month + seperator + strDate;
    console.log("日期:"+this.date)
  },
}