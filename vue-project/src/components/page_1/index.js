import editDialog from "../Dialog/index.vue"

export default {
  name: 'page_1',
  components: {
    editDialog,
  },
  
  data () {
    return {
      tableData: null,
      dialogVisible: false,
      formVis_addUser: false,
    }
  },
  created() {
    this.refresh();
  },
  methods: {
    addSystemUser() {
      this.dialogVisible = true;
      this.formVis_addUser = true;
    },

    closeAddSystemUserDialog() {
      this.dialogVisible = false;
      this.formVis_addUser = false;
    },

    handleUserEdit(index, row) {
      console.log(row);
    },

    refresh() {
      console.log("refresh");
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
    }
  }
}