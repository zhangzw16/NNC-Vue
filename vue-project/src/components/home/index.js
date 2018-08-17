// 侧边栏
import asideBar from '../aside/index.vue'
import headers from '../header/index.vue'
export default {
  name: 'home',
  data() {
    return {
      loginForm: {
        loginId: null,
        passWord: null,
      },
      loginDialogVisible: true,
      mainWindowVisible: false,
    };
  },
  components:{
    asideBar, // 侧边栏
    headers,
  },
  created(){
    this.loginDialogVisible = true;
    this.mainWindowVisible = false;
  },
  methods: {
    closeLogin(){
      this.loginDialogVisible = false;
      this.mainWindowVisible = true;
    },
    login(){

    }
  }
}