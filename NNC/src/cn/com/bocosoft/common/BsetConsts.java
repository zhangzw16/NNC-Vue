package cn.com.bocosoft.common;

public class BsetConsts {
    //日期格式常量(固定，不能变)yyyy-MM-dd HH:mm:ss
    public final static String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
    //用于前台画面操作
//    public final static String DATE_FORMAT_1_Show = TextHelper.getInstance().getText("DATE_FORMAT_1_Show");
    //用于flot显示(固定，不能变)，javascript的Date只支持yyyy/MM/dd HH:mm:ss格式
    public final static String DATE_FORMAT_2 = "yyyy/MM/dd HH:mm:ss";
    public final static String DATE_FORMAT_3 = "MM/dd/yyyy HH:mm:ss";
    public final static String DATE_FORMAT_4 = "dd/MM/yyyy HH:mm:ss";
    //后台处理数据格式(固定，不能变)
    public final static String DATE_FORMAT_5 = "yy/MM/dd HH:mm:ss";
    //自定义无分切格式
    public final static String DATE_FORMAT_6 = "yyyyMMdd HH:mm:ss";
    public final static String DATE_FORMAT_7 = "yyyy/MM/dd HH:mm";
    public final static String DATE_FORMAT_8 = "yyyyMMddHHmmss";
    public final static String DATE_FORMAT_9 = "yyyy-MM-dd";
    public final static String DATE_FORMAT_10 = "yyyy-MM";
    public final static String DATE_FORMAT_11 = "yyyyMMdd";
    public final static String DATE_FORMAT_12 = "yyyyMM";
    public final static String DATE_FORMAT_13 = "yy.MM.dd";
    public final static String DATE_FORMAT_14 = "yyyy年MM月dd日";
    public final static String DATE_FORMAT_15 = "yyyy.MM.dd";
    //用户定义日期格式
    public final static int DATE_FORMAT_TYPE_1 = 1;//"yyyy-MM-dd HH:mm:ss";
    public final static int DATE_FORMAT_TYPE_2 = 2;//"yyyy/MM/dd HH:mm:ss";
    public final static int DATE_FORMAT_TYPE_3 = 3;//"MM/dd/yyyy HH:mm:ss";
    public final static int DATE_FORMAT_TYPE_4 = 4;//"dd/MM/yyyy HH:mm:ss";
    public final static int DATE_FORMAT_TYPE_5 = 5;//"yy/MM/dd HH:mm:ss";
    
    public static final long TIME_MINUTE = 60 * 1000;
    public static final long TIME_HOUR = 60 * TIME_MINUTE;
    public static final long TIME_DAY = 24 * TIME_HOUR;
    
    //是否删除
    public final static int DEL_FLAG_0 = 0;//不删除
    public final static int DEL_FLAG_1 = 1;//删除
    
    //每页显示10条数据
    public final static int PER_PAGE_SIZE = 10;
    
    //短信平台短信模版1
    public final static String TEMPLATE_TYPE_1 = "204837";
    
    //随机验证码位数长度
    public final static int RANDOM_AUTHENTICATION_CODE_LEN = 6;
    
    //短信平台发送成功的状态
    public final static String SEND_SUCCESS_OK = "000000";
    
    //注册方式
    public final static int REGISTER_TYPE_0 = 0;//第三方
    public final static int REGISTER_TYPE_1 = 1;//手机
    public final static int REGISTER_TYPE_2 = 2;//微信
    public final static int REGISTER_TYPE_3 = 3;//QQ
    public final static int REGISTER_TYPE_4 = 4;//微博
    
    //客户减脂处在什么时期
    public final static int USER_STATUS_0 = 0;//准备期
    public final static int USER_STATUS_1 = 1;//正在期
    public final static int USER_STATUS_2 = 2;//过渡期
    public final static int USER_STATUS_3 = 3;//完成期
    
    //数据展示周的天数
    public final static int DAY_SIZE = 8;//8天
    
    //性别
    public final static int SEX_1 = 1;//男
    public final static int SEX_2 = 2;//女
    
    //是否置顶
    public final static int TOP_FLAG_0 = 0;//否
    public final static int TOP_FLAG_1 = 1;//是
    
    //是否允许查看
    public final static int LOOK_FLAG_0 = 0;//否
    public final static int LOOK_FLAG_1 = 1;//是
    
    //是否绑定
    public final static int LOGIN_FLAG_0 = 0;//否
    public final static int LOGIN_FLAG_1 = 1;//是
    
    //体重上升下降的标志
    public final static int WEIGHT_UP_DOWN_0 = 0;//不变
    public final static int WEIGHT_UP_DOWN_1 = 1;//增加
    public final static int WEIGHT_UP_DOWN_2 = 2;//减少
    
    //排行榜类型
    public final static int RANK_TYPE_1 = 1;//体重
    public final static int RANK_TYPE_2 = 2;//食物
    public final static int RANK_TYPE_3 = 3;//运动
    
    //登录系统的有效期
    public final static int LOGIN_DAY_COUNT = 30;//30天
    
    //活跃度
    public final static int VITALITY_COUNT = 10;//活跃度10
    
    //为避免小数点很多为进行乘除运算的基数
    public final static double WEIGHT_NUMBER = 10.0;//一位小数
    
    //理想体重BMI系数
    public final static double WEIGHT_BMI = 22.0;//一位小数
    
    //线程获取时间的路径
    public final static String TIME_FILEPATH = "time.txt";//取得时间的路径
    //线程获取体重的路径
    public final static String WEIGHT_LOSS_FILEPATH = "cumulativeWeightLoss.txt";
    //获取减重人数的路径
    public final static String PEOPLE_NUM_FILEPATH = "cumulativePeopleNum.txt";
    //获取版本信息的文件路径
    public final static String VERSION = "version.txt";
    //获取新版本安装包的文件路径
    public final static String VERSION_APK = "version_apk.txt";
  //获取新版本安装包的文件路径
    public final static String TAOBAO_URL = "taobao_url.txt";
    //项目路径
    public final static String SERVICE_URL = "http://118.89.241.96:8080/NNC/upload/";
//    public final static String SERVICE_URL = "http://192.168.0.47:8080/NNC/upload/";
//    public final static String SERVICE_URL = "http://114.242.25.126:7080/NNC/upload/";
}
