import Dialog from "../Dialog/newSystemUser.vue"

export default {
  name: 'page_1',
  components: {
    Dialog,
  },
  
  data () {
    return {
      tableData: null,
      addSystemUserDialogVisible: false
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
    addSystemUser(){
      this.addSystemUserDialogVisible = true;
    },
    closeAddSystemUserDialog(){
      this.addSystemUserDialogVisible = false;
    }
  }
}