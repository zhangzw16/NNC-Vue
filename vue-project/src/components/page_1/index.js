import MessageBox from "../MessageBox/newSystemUser.vue"

export default {
  name: 'page_1',
  components: {
    MessageBox,
  },
  
  data () {
    return {
      tableData: null,
      childDialogVisible: false
    }
  },
  created() {
    this.axios({
      method: 'post',
      url: '/NNC/rest/systemUser/system_user_page',
      data: {
       page: '1'
      }
    })
    .then((res) => {
      this.tableData = res.data.data.list;
      console.log(this.tableData);
    })
    .catch(err => {
      // console.log(err);
    });
  },
  methods: {
    AddNewSystemUser(){
      this.childDialogVisible = true;
    },
    closeChildDialog(){
      this.childDialogVisible = false;
    }
  }
}