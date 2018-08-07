package cn.com.bocosoft.controller;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.com.bocosoft.background.CumulativeWeightLoss;
import cn.com.bocosoft.common.BocosoftUitl;
import cn.com.bocosoft.common.BsetConsts;
import cn.com.bocosoft.common.JSONResult;
import cn.com.bocosoft.common.SDKSendTemplateSMS;
import cn.com.bocosoft.common.TableDataBean;
import cn.com.bocosoft.common.UserBean;
import cn.com.bocosoft.common.UserHisDataBean;
import cn.com.bocosoft.model.DietPhaseInfo;
import cn.com.bocosoft.model.Dietitian;
import cn.com.bocosoft.model.ThirdPartyLogin;
import cn.com.bocosoft.model.UserData;
import cn.com.bocosoft.model.UserData2;
import cn.com.bocosoft.model.UserInfo;
import cn.com.bocosoft.model.UserLoginInfo;
import cn.com.bocosoft.model.UserWeightData;
import cn.com.bocosoft.model.WeeklyRank;
import cn.com.bocosoft.model.WeeklyRecommend;
import cn.com.bocosoft.service.AppRestfulService;
import cn.com.bocosoft.service.UserInfoService;

@Controller
@RequestMapping("app_rest_ful")
public class AppRestfulController {
    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(AppRestfulController.class);
    @Resource
    AppRestfulService appRestfulService;
    @Resource
    UserInfoService userInfoService;
    private JSONResult json;

    public JSONResult getJson() {
        return json;
    }

    public void setJson(JSONResult json) {
        this.json = json;
    }
    
    /**
     * 取得手机的验证码
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/get_phone_validation_code", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_phone_validation_code(HttpServletRequest request) {
        String phoneNo = request.getParameter("phoneNo");
        String validation = BocosoftUitl.makeRandomPassword(BsetConsts.RANDOM_AUTHENTICATION_CODE_LEN);
        SDKSendTemplateSMS restAPI = new SDKSendTemplateSMS();
        restAPI.init();
        HashMap<String, Object> result = restAPI.sendTemplateSMS(phoneNo, BsetConsts.TEMPLATE_TYPE_1, validation, "3");
        if(BsetConsts.SEND_SUCCESS_OK.equals(result.get("statusCode"))){
            return json = new JSONResult(result, validation, true);
        }
        return json = new JSONResult(result, "100", false);
    }
    
    /**
     * 手机注册信息的保存
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/mobile_registration", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult mobile_registration(HttpServletRequest request) throws ParseException {
        String phoneNo = request.getParameter("phoneNo");
        String passwd = request.getParameter("passwd");
        String codeTime = request.getParameter("codeTime");
        if (codeTime == null) {
            return json = new JSONResult("", "101", false);
        }
        String time = codeTime.substring(0,4)
                    +"-"+codeTime.substring(4,6)
                    +"-"+codeTime.substring(6,8)
                    +" "+codeTime.substring(8,10)
                    +":"+codeTime.substring(10,12)
                    +":"+codeTime.substring(12,14);
        Calendar cal = Calendar.getInstance();
        cal.setTime(BocosoftUitl.stringToDate(time, BsetConsts.DATE_FORMAT_1));
        //判断是否过期
        long tmpTime = cal.getTimeInMillis();//发送验证码时间
        long nowTime = new Date().getTime();//现在时间
        if(nowTime - tmpTime > BsetConsts.TIME_MINUTE * 5){
            return json = new JSONResult("", "timeOut", false);
        } else {
            //验证电话号是否已经注册
            boolean reg = appRestfulService.findUserLoginInfoByPhone(phoneNo);
            if(reg == false){
                return json = new JSONResult("", "haveReg", false);
            }else{
                //检查此号码营养师端是否存在
                boolean dietitian = appRestfulService.findDietitianByPhone(phoneNo);
                if(dietitian){
                    UserLoginInfo uli = appRestfulService.mobileRegistrationSave(phoneNo, passwd);
                    return json = new JSONResult(uli, "100", true);
                }else{
                    return json = new JSONResult("", "dietitianHaveReg", false);
                }
               
            }
        }
    }
    
    /**
     * 客户端手机密码修改/重置
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/edit_phone_passwd", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_phone_passwd(HttpServletRequest request) throws ParseException {
        String phoneNo = request.getParameter("phoneNo");
        String passwd = request.getParameter("passwd");
        String codeTime = request.getParameter("codeTime");
        if (codeTime == null) {
            return json = new JSONResult("", "101", false);
        }
        String time = codeTime.substring(0,4)
                    +"-"+codeTime.substring(4,6)
                    +"-"+codeTime.substring(6,8)
                    +" "+codeTime.substring(8,10)
                    +":"+codeTime.substring(10,12)
                    +":"+codeTime.substring(12,14);
        Calendar cal = Calendar.getInstance();
        cal.setTime(BocosoftUitl.stringToDate(time, BsetConsts.DATE_FORMAT_1));
        //判断是否过期
        long tmpTime = cal.getTimeInMillis();//发送验证码时间
        long nowTime = new Date().getTime();//现在时间
        if(nowTime - tmpTime > BsetConsts.TIME_MINUTE * 5){
            return json = new JSONResult("", "timeOut", false);
        } else {
            boolean dietitian = appRestfulService.findDietitianByPhone(phoneNo);
            if(dietitian){
                UserLoginInfo uli = appRestfulService.editPhonePasswdSave(phoneNo, passwd);
                if (uli.getId() != null ) {//重置或者修改密码成功
                    return json = new JSONResult(uli, "true", true);
                } else {//修改的号码不存在
                    return json = new JSONResult(uli, "false", true);
                }
            }else{
                //营养师修改密码
                Dietitian diet = appRestfulService.editDietitianPhonePasswdSave(phoneNo,passwd);
                return json = new JSONResult(diet, "dietitianHaveReg", true);
            }
            
        }
    }
    /**
     * 营养师端手机密码修改/重置
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/edit_phone_passwd_dietitian", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_phone_passwd_dietitian(HttpServletRequest request) throws ParseException {
        String phoneNo = request.getParameter("phoneNo");
        String passwd = request.getParameter("passwd");
        String codeTime = request.getParameter("codeTime");
        if (codeTime == null) {
            return json = new JSONResult("", "101", false);
        }
        String time = codeTime.substring(0,4)
                    +"-"+codeTime.substring(4,6)
                    +"-"+codeTime.substring(6,8)
                    +" "+codeTime.substring(8,10)
                    +":"+codeTime.substring(10,12)
                    +":"+codeTime.substring(12,14);
        Calendar cal = Calendar.getInstance();
        cal.setTime(BocosoftUitl.stringToDate(time, BsetConsts.DATE_FORMAT_1));
        //判断是否过期
        long tmpTime = cal.getTimeInMillis();//发送验证码时间
        long nowTime = new Date().getTime();//现在时间
        if(nowTime - tmpTime > BsetConsts.TIME_MINUTE * 5){
            return json = new JSONResult("", "timeOut", false);
        } else {
            Dietitian dietitian = appRestfulService.editDietitianPhonePasswdSave(phoneNo, passwd);
            if (dietitian.getId() != null ) {//重置或者修改密码成功
                return json = new JSONResult(dietitian, "true", true);
            } else {//修改的号码不存在
                return json = new JSONResult(dietitian, "false", true);
            }
        }
    }
    
    /**
     * 查询用户登录的状态
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/select_login_type", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_login_type (HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));//loginId
        //前台已做判断
        /*if (userLoginId == null) {//重新登录
            return json = new JSONResult("", "101", false);
        }*/
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        if (userInfo.getAgreeFlag() == 1) {//被禁用
            return json = new JSONResult(userInfo, "102", false);
        }
        if (userInfo.getVitality() < 10) {
            userInfo.setVitality(userInfo.getVitality() + 1);
        }
        if (userInfo.getDayCount() > 0) {
            userInfo.setLoginCount(userInfo.getLoginCount() + 1);
            userInfo.setDayCount(BsetConsts.LOGIN_DAY_COUNT);
            userInfoService.updataUserInfo(userInfo);
        } else {//超过30天未登录，需重新登录
            userInfo.setLoginCount(userInfo.getLoginCount() + 1);
            userInfoService.updataUserInfo(userInfo);
            return json = new JSONResult(userInfo, "101", false);
        }
        return json = new JSONResult(userInfo, "100", true);
    }
    
    
    /**
     * 用户登录，手机号
     * @param request message   1：客户登录成功为并且完善资料  2：客户登录成功为未完善资料 3：营养师登录
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/phone_login", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult phone_login(HttpServletRequest request) {
        String phoneNo = request.getParameter("phoneNo");//loginId
        String pwd = request.getParameter("pwd");//loginId
        List<UserLoginInfo> userLoginInfos = appRestfulService.phone_login(phoneNo, pwd);
        if (userLoginInfos != null) {
            LOG.info("User Id "+ userLoginInfos.get(0).getLoginId()+ " is login");
            if(userLoginInfos.get(0).getLoginFlag() == 0){
                return json = new JSONResult("", "6", true);
            }
            List<UserInfo> userInfos = appRestfulService.getUserInfoByLoginId(userLoginInfos.get(0).getId());
            if (userInfos.size() > 0) {//已经完善个人资料
                UserInfo userInfo = userInfos.get(0);
                userInfo.setDayCount(BsetConsts.LOGIN_DAY_COUNT);
                userInfoService.updataUserInfo(userInfo);
/*                List<DietPhaseInfo> dpis = userInfoService.select_user_histroy_weight_list(userInfos.get(0).getId());
                if (dpis.size() > 0) {//有历史数据
                    return json = new JSONResult(userInfo, "4", true);
                }
*/                return json = new JSONResult(userInfo, "1", true);
            } else {//没填写
                return json = new JSONResult(userLoginInfos.get(0), "2", true);
            }
        }
        List<Dietitian> dietitians = appRestfulService.dietitian_phone_login(phoneNo, pwd);
        if (dietitians != null) {//营养师登录
            LOG.info("Dietitian Id "+ dietitians.get(0).getLoginId()+ " is login");
            return json = new JSONResult(dietitians.get(0), "3", true);
        }
        return json = new JSONResult("", "101", false);
    }
    
    /**
     * 第三方微信登录
     * @param request message
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/third_party_login", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult third_party_login (HttpServletRequest request) {
        String openId = request.getParameter("openId");//loginId
        String headUrl = request.getParameter("headUrl");
        String loginFlag = request.getParameter("loginFlag");
        ThirdPartyLogin thirdPartyLogin = appRestfulService.getThirdPartyLogin(openId);
        if (thirdPartyLogin != null) {//登录认证
            List<UserInfo> userInfos = appRestfulService.getUserInfoBythirdPartyLoginId(thirdPartyLogin.getId());
            if (userInfos.size() > 0) {//已经完善个人资料
                UserInfo userInfo = userInfos.get(0);
                userInfo.setDayCount(BsetConsts.LOGIN_DAY_COUNT);
                userInfoService.updataUserInfo(userInfo);
               /* List<DietPhaseInfo> dpis = userInfoService.select_user_histroy_weight_list(userInfos.get(0).getId());
                if (dpis.size() > 0) {//有历史数据
                    return json = new JSONResult(userInfo, "4", true);
                }*/
                LOG.info("User "+ userInfo.getName()+ "is login");
                return json = new JSONResult(userInfo, "1", true);
            } else {//没填写
                return json = new JSONResult(thirdPartyLogin, "5", true);
            }
        } else {//保存信息
            ThirdPartyLogin tpl = new ThirdPartyLogin();
            tpl.setOpenId(openId);
            tpl.setLoginFlag(Integer.valueOf(loginFlag));
            tpl.setHeadImgUrl(headUrl);
            tpl = appRestfulService.saveThirdPartyLogin(tpl);
            LOG.info("User "+ tpl.getName()+ "is registering successfully");
            return json = new JSONResult(tpl, "5", true);
        }
    }
    
    
    /**
     * 用户信息的完善
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/user_info_save", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult user_info_save (HttpServletRequest request) {
        String userLoginId = request.getParameter("userLoginId");//loginId
        String nickname = request.getParameter("nickname");//昵称
        String sex = request.getParameter("sex");//性别
        String age = request.getParameter("age");//出生日期
        String weight = request.getParameter("weight");//体重
        String height = request.getParameter("height");//身高
        String userLoginType = request.getParameter("userLoginType");//注册方式
        UserInfo userInfo = appRestfulService.saveUserInfoData(userLoginType, userLoginId, nickname, sex, age, weight, height);
//                ,blood_pressure, blood_fat, blood_sugar, blood_uric_acid,fld);
        if (userInfo != null) {
            return json = new JSONResult(userInfo, "100", true);
        }
        return json = new JSONResult("", "101", false);
    }
    
    /**
     * 用户信息的完善参加尼基计划
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/user_niji_info_save", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult user_niji_info_save (HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));//用户Id
        String phone = request.getParameter("phone");//手机号
        String blood_pressure = request.getParameter("blood_pressure");//血压
        String blood_fat = request.getParameter("blood_fat");//血脂
        String blood_sugar = request.getParameter("blood_sugar");//血糖
        String blood_uric_acid = request.getParameter("blood_uric_acid");//血尿酸
        String fld = request.getParameter("fld");//脂肪肝
        String contactWay = request.getParameter("contact_way");//联系方式QQ,微信
        String account = request.getParameter("account");//帐号
        String buyFlag = request.getParameter("buy_flag");//是否购买尼基多糖
        UserInfo userInfo = appRestfulService.saveUserNijiInfoData(userInfoId, phone, blood_pressure, blood_fat, blood_sugar, blood_uric_acid,
                fld, contactWay, account, buyFlag);
        LOG.info("User "+ userInfo.getName()+ " jion plan.");
        return json = new JSONResult(userInfo, "101", true);
    }
    /**
     * 客户数据查询
     * @param request
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/find_user_info_data", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult find_user_info_data(HttpServletRequest request) throws ParseException {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        UserData ud = appRestfulService.findByUserData(userInfoId, date);
        if(ud == null){
//            long tmpday = BocosoftUitl.compare2Time(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9), userInfo.getStartDate());
            UserData userData = new UserData();
            if (userInfo.getUserStatus() == BsetConsts.USER_STATUS_1) {
                userData.setUserStatus(BsetConsts.USER_STATUS_1);
                if (userInfo.getStartDate() != null) {
                    int DietDay = BocosoftUitl.getDietDays(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9), userInfo.getStartDate());
                    if (DietDay > 0) {
                        return json = new JSONResult(userData, String.valueOf(DietDay), true);
                    }
                }
                return json = new JSONResult(userData, "- -", true);
            } else if (userInfo.getUserStatus() == BsetConsts.USER_STATUS_2) {
                int DietDay = BocosoftUitl.getDietDays(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9), userInfo.getEndDate());
                if (DietDay > 0 && DietDay < 16) {
                    userData.setUserStatus(BsetConsts.USER_STATUS_2);
                    return json = new JSONResult(userData, String.valueOf(DietDay - 1), true);
                } else if (DietDay > 15) {
                    userData.setUserStatus(BsetConsts.USER_STATUS_3);
                    return json = new JSONResult(userData, String.valueOf(DietDay - 16), true);
                }else {
                    int DietDay3 = BocosoftUitl.getDietDays(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9), userInfo.getStartDate());
                    if (DietDay3 > 0) {
                        userData.setUserStatus(BsetConsts.USER_STATUS_1);
                        return json = new JSONResult(userData, String.valueOf(DietDay3), true);
                    }
                }
            } else if (userInfo.getUserStatus() == BsetConsts.USER_STATUS_3) {
                DietPhaseInfo dietPhaseInfo = userInfoService.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase() - 1);
                int DietDay = BocosoftUitl.getDietDays(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9), dietPhaseInfo.getTransitionEndDate());
                if (DietDay > 0) {
                    userData.setUserStatus(BsetConsts.USER_STATUS_3);
                    return json = new JSONResult(userData, String.valueOf(DietDay), true);
                } else {
                    int DietDay2 = BocosoftUitl.getDietDays(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9), dietPhaseInfo.getTransitionStartDate());
                    if (DietDay2 > 0) {
                        userData.setUserStatus(BsetConsts.USER_STATUS_2);
                        return json = new JSONResult(userData, String.valueOf(DietDay2), true);
                    } else {
                        int DietDay3 = BocosoftUitl.getDietDays(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9), userInfo.getStartDate());
                        if (DietDay3 > 0) {
                            userData.setUserStatus(BsetConsts.USER_STATUS_1);
                            return json = new JSONResult(userData, String.valueOf(DietDay3), true);
                        }
                    }
                }
            }
            return json = new JSONResult("", "- -", true);
        } else {
            if (ud.getUserStatus() == BsetConsts.USER_STATUS_1) {
                if (userInfo.getStartDate() != null) {
                    int DietDay = BocosoftUitl.getDietDays(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9), userInfo.getStartDate());
                    if (DietDay > 0) {
                        return json = new JSONResult(ud, String.valueOf(DietDay), true);
                    }
                }
               return json = new JSONResult(ud, "- -", true);
            } else if (ud.getUserStatus() == BsetConsts.USER_STATUS_2) {
                int DietDay = BocosoftUitl.getDietDays(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9), userInfo.getEndDate());
                if (DietDay > 0) {
                    return json = new JSONResult(ud, String.valueOf(DietDay - 1), true);
                }
            } else if (ud.getUserStatus() == BsetConsts.USER_STATUS_3) {
                DietPhaseInfo dietPhaseInfo = userInfoService.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase() - 1);
                int DietDay = BocosoftUitl.getDietDays(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9), dietPhaseInfo.getTransitionEndDate());
                if (DietDay > 0) {
                    return json = new JSONResult(ud, String.valueOf(DietDay), true);
                }
            }
            return json = new JSONResult(ud, "- -", true);
        }
    }
    /**
     * 客户数据(体重)保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/user_info_data_weight_save", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult user_info_data_weight_save(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String weight = request.getParameter("weight");
        String date = request.getParameter("date");
        //总体累计体重的修改累计体重的修改
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        int userStatus = userInfo.getUserStatus();
        String parentPath = getClass().getResource("/").getFile().toString();
        String weightPath = parentPath + BsetConsts.WEIGHT_LOSS_FILEPATH;
        UserWeightData uwd = userInfoService.findUserWeightDataByDate(userInfoId, date);
        Double addWeight = 0.0;
        LOG.info("The task of update Cumulative Weight Loss started.");
        if (uwd == null) {//添加体重数据
            UserWeightData tmpUwd = userInfoService.getFirstData(userInfoId, date);
            if (tmpUwd == null) {//判断这个时间点之后是否有数据，没有累计，有数据直接跳过
                UserWeightData userWeightData = userInfoService.getLastData(userInfoId, date);
                if (userWeightData != null) {
                    addWeight = (userWeightData.getWeight() * BsetConsts.WEIGHT_NUMBER - Double.valueOf(weight) * BsetConsts.WEIGHT_NUMBER) / BsetConsts.WEIGHT_NUMBER;
                }
            }
        } else {//修改体重
            UserWeightData tmpUwd = userInfoService.getFirstData(userInfoId, date);
            if (tmpUwd == null) {//判断这个时间点之后是否有数据，没有累计，有数据直接跳过
                addWeight = (uwd.getWeight() * BsetConsts.WEIGHT_NUMBER - Double.valueOf(weight) * BsetConsts.WEIGHT_NUMBER) / BsetConsts.WEIGHT_NUMBER;
            }
        }
        String tmpWeight = BocosoftUitl.readTxtFile(weightPath);
        LOG.info("Cumulative Weight Loss："+tmpWeight);
        Double weightLoss = (Double.valueOf(tmpWeight) * BsetConsts.WEIGHT_NUMBER + addWeight * BsetConsts.WEIGHT_NUMBER) / BsetConsts.WEIGHT_NUMBER;
        LOG.info("To calculate Cumulative Weight Loss："+weightLoss);
        BocosoftUitl.setTxtMessage(String.valueOf(weightLoss), weightPath);
        UserData ud = appRestfulService.userInfoDataWeightSave(userInfoId, weight, date, userStatus);
        return json = new JSONResult(ud, "100", true);
    }
    /**
     * 客户数据(早餐)保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/user_info_data_breakfast_save", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult user_info_data_breakfast_save(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String breakfast = request.getParameter("breakfast");
        String date = request.getParameter("date");
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
        Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象
        String upaloadUrl = request.getSession().getServletContext().getRealPath("/")+"upload/"+userInfoId+"/"+date+"/";//得到当前工程路径拼接上文件名
        File dir = new File(upaloadUrl);
        int nub = 1;
        List<UserData2> ud2s = appRestfulService.findByUserData2(userInfoId, date, 1);
        if (ud2s.size() > 0) {
            appRestfulService.deleteUserData2(userInfoId, date, 1);
        }
        //删除以前的上传文件
        if (ud2s.size() > 0) {
            for (int i= 0; i < ud2s.size(); i++) {
                File deleteFile = new File(upaloadUrl, ud2s.get(i).getFileName());
                deleteFile.delete();
            }
        }
        for(MultipartFile file :files.values()){
            if (!file.isEmpty()) {
                dir.delete();
                if(!dir.exists()) {
                    dir.mkdirs();
                }//目录不存在则创建
                String fileName = file.getOriginalFilename();
                int index = fileName.lastIndexOf(".");
                String targetFileName = BocosoftUitl.makeRandomPassword(BsetConsts.RANDOM_AUTHENTICATION_CODE_LEN) + "_" + date + "_breakfast_" + nub + fileName.substring(index);//文件名称
                File tagetFile = new File(upaloadUrl + targetFileName);//创建文件对象
                if(!tagetFile.exists()){//文件名不存在 则新建文件，并将文件复制到新建文件中
                    try {
                        tagetFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        file.transferTo(tagetFile);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String filePath = BsetConsts.SERVICE_URL + +userInfoId+"/"+date+"/"+ targetFileName;
                appRestfulService.saveUserData2(userInfoId, filePath, targetFileName, date, 1, file.getSize());
                nub++;
            }
        }
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        int userStatus = userInfo.getUserStatus();
        UserData ud = appRestfulService.userInfoDataBreakfastSave(userInfoId,breakfast,date,userStatus);
        return json = new JSONResult(ud, "100", true);
    }
    
    /**
     * 客户数据(午餐)保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/user_info_data_lunch_save", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult user_info_data_lunch_save(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String lunch = request.getParameter("lunch");
        String date = request.getParameter("date");
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
        Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象
        String upaloadUrl = request.getSession().getServletContext().getRealPath("/")+"upload/"+userInfoId+"/"+date+"/";//得到当前工程路径拼接上文件名
        File dir = new File(upaloadUrl);
        List<UserData2> ud2s = appRestfulService.findByUserData2(userInfoId, date, 2);
        if (ud2s.size() > 0) {
            appRestfulService.deleteUserData2(userInfoId, date, 2);
        }
        //删除以前的上传文件
        if (ud2s.size() > 0) {
            for (int i= 0; i < ud2s.size(); i++) {
                File deleteFile = new File(upaloadUrl, ud2s.get(i).getFileName());
                deleteFile.delete();
            }
        }
        int nub = 1;
        for(MultipartFile file :files.values()){
            if (!file.isEmpty()) {
                if(!dir.exists()) {
                    dir.mkdirs();
                }//目录不存在则创建
                String fileName = file.getOriginalFilename();
                int index = fileName.lastIndexOf(".");
                String targetFileName = BocosoftUitl.makeRandomPassword(BsetConsts.RANDOM_AUTHENTICATION_CODE_LEN) + "_" + date + "_lunch_" + nub + fileName.substring(index);//文件名称
                File tagetFile = new File(upaloadUrl + targetFileName);//创建文件对象
                if(!tagetFile.exists()){//文件名不存在 则新建文件，并将文件复制到新建文件中
                    try {
                        tagetFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        file.transferTo(tagetFile);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String filePath = BsetConsts.SERVICE_URL + +userInfoId+"/"+date+"/" +targetFileName;
                appRestfulService.saveUserData2(userInfoId, filePath, targetFileName, date, 2, file.getSize());
                nub++;
            }
        }
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        int userStatus = userInfo.getUserStatus();
        UserData ud = appRestfulService.userInfoDataLunchSave(userInfoId,lunch,date,userStatus);
        return json = new JSONResult(ud, "100", true);
    }
    
    /**
     * 客户数据(晚餐)保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/user_info_data_dinner_save", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult user_info_data_dinner_save(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String dinner = request.getParameter("dinner");
        String date = request.getParameter("date");
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
        Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象
        String upaloadUrl = request.getSession().getServletContext().getRealPath("/")+"upload/"+userInfoId+"/"+date+"/";//得到当前工程路径拼接上文件名
        File dir = new File(upaloadUrl);
       
        int nub = 1;
        List<UserData2> ud2s = appRestfulService.findByUserData2(userInfoId, date, 3);
        if (ud2s.size() > 0) {
            appRestfulService.deleteUserData2(userInfoId, date, 3);
        }
        //删除以前的上传文件
        if (ud2s.size() > 0) {
            for (int i= 0; i < ud2s.size(); i++) {
                File deleteFile = new File(upaloadUrl, ud2s.get(i).getFileName());
                deleteFile.delete();
            }
        }
        for(MultipartFile file :files.values()){
            if (!file.isEmpty()) {
                dir.delete();
                if(!dir.exists()) {
                    dir.mkdirs();
                }//目录不存在则创建
                
                String fileName = file.getOriginalFilename();
                int index = fileName.lastIndexOf(".");
                String targetFileName = BocosoftUitl.makeRandomPassword(BsetConsts.RANDOM_AUTHENTICATION_CODE_LEN)+ "_" + date + "_dinner_" + nub + fileName.substring(index);//文件名称
                File tagetFile = new File(upaloadUrl + targetFileName);//创建文件对象
                if(!tagetFile.exists()){//文件名不存在 则新建文件，并将文件复制到新建文件中
                    try {
                        tagetFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        file.transferTo(tagetFile);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String filePath = BsetConsts.SERVICE_URL + +userInfoId+"/"+date+"/"+targetFileName;
                appRestfulService.saveUserData2(userInfoId, filePath, targetFileName, date, 3, file.getSize());
                nub++;
            }
        }
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        int userStatus = userInfo.getUserStatus();
        UserData ud = appRestfulService.userInfoDataDinnerSave(userInfoId,dinner,date,userStatus);
        return json = new JSONResult(ud, "100", true);
    }
    
    /**
     * 客户数据(运动量)保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/user_info_data_exercise_save", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult user_info_data_exercise_save(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String exercise = request.getParameter("exercise");
        String date = request.getParameter("date");
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        int userStatus = userInfo.getUserStatus();
        UserData ud = appRestfulService.userInfoDataExerciseSave(userInfoId, exercise, date,userStatus);
        return json = new JSONResult(ud, "100", true);
    }
    
    /**
     * 客户数据(饮水量)保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/user_info_data_drankWater_save", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult user_info_data_drankWater_save(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String drankWater = request.getParameter("drankWater");
        String date = request.getParameter("date");
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        int userStatus = userInfo.getUserStatus();
        UserData ud = appRestfulService.userInfoDataDrankWaterSave(userInfoId, drankWater, date,userStatus);
        return json = new JSONResult(ud, "100", true);
    }
    
    /**
     * 客户数据(舒适度)保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/user_info_data_comfortLevel_save", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult user_info_data_comfortLevel_save(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String comfortLevel = request.getParameter("comfortLevel");
        String date = request.getParameter("date");
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        int userStatus = userInfo.getUserStatus();
        UserData ud = appRestfulService.userInfoDataComfortLevelSave(userInfoId, comfortLevel, date,userStatus);
        return json = new JSONResult(ud, "100", true);
    }
    
    /**
     * 客户数据(试纸)保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/user_info_data_testPaperValue_save", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult user_info_data_testPaperValue_save(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String testPaperValue = request.getParameter("testPaperValue");
        String date = request.getParameter("date");
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        int userStatus = userInfo.getUserStatus();
        UserData ud = appRestfulService.userInfoDataTestPaperValueSave(userInfoId, testPaperValue, date,userStatus);
        return json = new JSONResult(ud, "100", true);
    }
    
    /**
     * 获取客户数据(表格)
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/select_user_weight_data_table", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_user_weight_data_table(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        try {
            cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
            cal2.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        List SevenDayTableDate = BocosoftUitl.dateToWeek(cal.getTime());
        cal2.add(Calendar.WEEK_OF_MONTH, -1);
        cal2.set(Calendar.DAY_OF_WEEK, 7);
        List<UserWeightData> uwds = userInfoService.findByUserWeightData(userInfoId, weekOfYear, year);
        UserWeightData uwd = userInfoService.findUserWeightDataByDate(userInfoId, BocosoftUitl.dateToString(cal2.getTime(), BsetConsts.DATE_FORMAT_9));//取得上周最后一条数据
        Map<String, TableDataBean> tableDataFirst = new LinkedHashMap<String, TableDataBean>();//创建8天表格数据
        Map<String, TableDataBean> tableData = new LinkedHashMap<String, TableDataBean>();//创建8天表格数据
        List<Map> tmpTableData =  new ArrayList<Map>();
        TableDataBean tdb = new TableDataBean();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        if (uwd != null) {
            tdb.setDate(sdf.format(SevenDayTableDate.get(0)));
            tdb.setDietWeight(Math.abs(uwd.getDeltaWeight()));
            tdb.setWeight(uwd.getWeight());
            tdb.setFlag(0);
            tableDataFirst.put("周六 ", tdb);
        } else {
            tdb.setDate(sdf.format(SevenDayTableDate.get(0)));
            tableDataFirst.put("周六 ", tdb);
        }
        tableData = BocosoftUitl.createSevenDayTableDate(uwds, SevenDayTableDate);
        tmpTableData.add(tableDataFirst);
        tmpTableData.add(tableData);
        return json = new JSONResult(tmpTableData, BocosoftUitl.getDayOfWeek(dayOfWeek), true);
    }
    
    /**
     * 获取客户数据折线图(周)
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/select_user_weight_data_week", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_user_weight_data_week(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.setAttribute("chose_date", date);
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        cal.add(Calendar.WEEK_OF_MONTH, -1);
        cal.set(Calendar.DAY_OF_WEEK, 7);
        List<UserWeightData> uwds = userInfoService.findByUserWeightData(userInfoId, weekOfYear, year);
        UserWeightData uwd = userInfoService.findUserWeightDataByDate(userInfoId, BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9));//取得上周最后一条数据
        List<String> sevenData = new ArrayList<String>();
        List<String> idealBodyWeightSevenData = new ArrayList<String>();
        List<String> allMaxAndMin = new ArrayList<String>();
        List<String> showLastData = new ArrayList<String>();
        if (uwds.size() > 0 || uwd != null) {
            sevenData = BocosoftUitl.createSevenDayDateApp(uwd, uwds);//创建8天图形数据
            
            idealBodyWeightSevenData = BocosoftUitl.createidealBodyWeightDate(userInfo.getIdealBodyWeight(), BsetConsts.DAY_SIZE);//创建8天理想体重数据数据
            if (uwd != null) {
                allMaxAndMin = BocosoftUitl.getListsMinAndMax(uwd.getWeight(), uwds);//获取最大值最小值
            } else {
                allMaxAndMin = BocosoftUitl.getListsMinAndMax(null, uwds);//获取最大值最小值
            }
            showLastData = BocosoftUitl.createShowLastData(1, uwd, uwds);
        } else {//数据为空
            sevenData.add("-");
            allMaxAndMin.add("100");
            allMaxAndMin.add("0");
            showLastData.add("周六 ");
            showLastData.add("'-'");
        }
        
        Map<String, List> map = new LinkedHashMap<String, List>();
        map.put("sevenData", sevenData);
        map.put("idealBodyWeightSevenData", idealBodyWeightSevenData);
        map.put("maxAndMin", allMaxAndMin);
        map.put("showLastData", showLastData);
        return json = new JSONResult(map, "100", true);
    }
    /**
     * 获取客户数据折线图(总)
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/select_user_weight_data_all", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_user_weight_data_all(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        Calendar cal = Calendar.getInstance();
        List<String> xAxisData = new ArrayList<String>();
        List<String> yAxisData = new ArrayList<String>();
        List<String> idealBodyWeightAllData = new ArrayList<String>();
        List<String> allMaxAndMin = new ArrayList<String>();
        List<String> showLastData = new ArrayList<String>();
        List<UserWeightData> userwds = new ArrayList<UserWeightData>();
        int DietDays = 0;
        if (userInfo.getStartDate() != null) {//参加尼基计划  显示总曲线
            if (userInfo.getPhase() > 1) {//参加过尼基计划
                if (userInfo.getUserStatus() == BsetConsts.USER_STATUS_3) {
                    DietPhaseInfo dietPhaseInfo = userInfoService.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase() - 1);
                    userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                            ,BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
                    DietDays = BocosoftUitl.getDietDays(dietPhaseInfo.getEndDate(), dietPhaseInfo.getStartDate());
                } else {//当前期数据
                    if (userInfo.getEndDate() != null) {//取得到尼基计划结束的时间
                        DietPhaseInfo dietPhaseInfo = userInfoService.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase());
                        userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                                ,BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
                        DietDays = BocosoftUitl.getDietDays(dietPhaseInfo.getEndDate(), dietPhaseInfo.getStartDate());
                    } else {//取得到现在的时间
                        userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(userInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                                ,BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9));
                        DietDays = BocosoftUitl.getDietDays(cal.getTime(), userInfo.getStartDate());
                    }
                }
            } else {//只取得尼基计划内体重总体
                if (userInfo.getEndDate() != null) {//取得到尼基计划结束的时间
                    userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(userInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                            ,BocosoftUitl.dateToString(userInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
                    DietDays = BocosoftUitl.getDietDays(userInfo.getEndDate(), userInfo.getStartDate());
                } else {//取得到现在的时间
                    userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(userInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                            ,BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9));
                    DietDays = BocosoftUitl.getDietDays(cal.getTime(), userInfo.getStartDate());
                }
            }
            if (userwds.size() > 0) {
                xAxisData = BocosoftUitl.createAllDayDate(DietDays);
                yAxisData = BocosoftUitl.createAllDayWeightDateApp(userwds, DietDays);
                idealBodyWeightAllData = BocosoftUitl.createidealBodyWeightDate(userInfo.getIdealBodyWeight(), DietDays);
                allMaxAndMin = BocosoftUitl.getListsMinAndMax(userInfo.getIdealBodyWeight(), userwds);//获取最大值最小值
                showLastData = BocosoftUitl.createShowLastData(2, null, userwds);
            } else {
                yAxisData.add(""+userInfo.getIdealBodyWeight()+"");
                allMaxAndMin = BocosoftUitl.getListsMinAndMax(userInfo.getIdealBodyWeight(), userwds);//获取最大值最小值
                xAxisData.add("0");
                idealBodyWeightAllData.add(""+userInfo.getIdealBodyWeight()+"");
                showLastData.add("0");
                showLastData.add(""+userInfo.getWeight()+"");
            }
        }else{
            yAxisData.add("-");
            allMaxAndMin.add("100");
            allMaxAndMin.add("0");
            xAxisData.add("0");
        }
        Map<String, List> map = new LinkedHashMap<String, List>();
        map.put("xAxisData", xAxisData);
        map.put("yAxisData", yAxisData);
        map.put("idealBodyWeightAllData", idealBodyWeightAllData);
        map.put("allMaxAndMin", allMaxAndMin);
        map.put("allshowLastData", showLastData);
        return json = new JSONResult(map, "100", true);
    }
    
    
    /**
     * 个人信息查询
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/find_user_data", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult find_user_data(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        return json = new JSONResult(userInfo, "100", true);
    }
    /**
     * 个人信息是否允许他人查看照片
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/find_userinfo_lookFlag", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult find_userinfo_lookFlag(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        return json = new JSONResult(userInfo, "100", true);
    }
    
    /**
     * 个人信息查询
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/select_user_info", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_user_info(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        String birthday = BocosoftUitl.dateToString(userInfo.getBirthday(),BsetConsts.DATE_FORMAT_9 );
        String startDate = BocosoftUitl.dateToString(userInfo.getStartDate(),BsetConsts.DATE_FORMAT_9 );
        String endDate = BocosoftUitl.dateToString(userInfo.getEndDate(),BsetConsts.DATE_FORMAT_9 );
        UserLoginInfo uli = new UserLoginInfo();
        if(userInfo.getUserLoginInfoId()!=null){
            uli = appRestfulService.findUserLoginInfoById(userInfo.getUserLoginInfoId());
        }
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("userInfo", userInfo);
        map.put("uli", uli);
        map.put("birthday" , birthday);
        map.put("startDate" , startDate);
        map.put("endDate" , endDate);
        if (userInfo.getDietitianId() != null) {
            Dietitian dietitian = appRestfulService.findByDietitian(userInfo.getDietitianId());
            map.put("dietitianName", dietitian.getName());
            return json = new JSONResult(map,"", true);
        }
        return json = new JSONResult(map, "", true);
    }
    
    /**
     * 个人信息昵称的修改保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/edit_user_info_name", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_user_info_name(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String userInfoName = request.getParameter("userInfoName");
        UserInfo userInfo = appRestfulService.editUserInfoName(userInfoId, userInfoName);
        return json = new JSONResult(userInfo, "100", true);
    }
    
//    /**
//     * 个人信息手机号修改保存
//     * @param request
//     * @return
//     */
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    @RequestMapping(value = "/edit_user_info_phone_no", method = RequestMethod.POST)
//    @ResponseBody
//    public JSONResult edit_user_info_phone_no(HttpServletRequest request) {
//        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
//        String userInfoPhoneNo = request.getParameter("userInfoPhoneNo");
//        UserInfo userInfo = appRestfulService.editUserInfoPhoneNo(userInfoId, userInfoPhoneNo);
//        String phoneNo = appRestfulService.getUserLoginInfo(userInfo.getUserLoginInfoId());
//        return json = new JSONResult(userInfo, phoneNo, true);
//    }
    
    
    /**
     * 个人信息出生年月修改保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/edit_user_info_birthday", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_user_info_birthday(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String userInfoBirthday = request.getParameter("userInfoBirthday");
        UserInfo userInfo = appRestfulService.editUserInfoBirthday(userInfoId, userInfoBirthday);
        return json = new JSONResult(userInfo, "100", true);
    }
    
    /**
     * 个人信息性别修改保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/edit_user_info_sex", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_user_info_sex(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String userInfoSex = request.getParameter("userInfoSex");
        UserInfo userInfo = appRestfulService.editUserInfoSex(userInfoId, userInfoSex);
        return json = new JSONResult(userInfo, "100", true);
    }
    
    /**
     * 个人信息身高修改保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/edit_user_info_height", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_user_info_height(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String userInfoHeight = request.getParameter("userInfoHeight");
        UserInfo userInfo = appRestfulService.editUserInfoHeight(userInfoId, userInfoHeight);
        return json = new JSONResult(userInfo, "100", true);
    }
    
    /**
     * 个人信息体重修改保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/edit_user_info_weight", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_user_info_weight(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String userInfoWeight = request.getParameter("userInfoHeight");
        UserInfo userInfo = appRestfulService.editUserInfoWeight(userInfoId, userInfoWeight);
        return json = new JSONResult(userInfo, "100", true);
    }
    
    /**
     * 个人信息血压修改保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/edit_user_info_blood_pressure", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_user_info_blood_pressure(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String userInfoBloodPressure = request.getParameter("userInfoBloodPressure");
        UserInfo userInfo = appRestfulService.editUserInfoBloodPressure(userInfoId, userInfoBloodPressure);
        return json = new JSONResult(userInfo, "100", true);
    }
    
    /**
     * 个人信息血脂修改保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/edit_user_info_blood_fat", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_user_info_blood_fat(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String userInfoBloodFat = request.getParameter("userInfoBloodFat");
        UserInfo userInfo = appRestfulService.editUserInfoBloodFat(userInfoId, userInfoBloodFat);
        return json = new JSONResult(userInfo, "100", true);
    }
    
    /**
     * 个人信息血糖修改保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/edit_user_info_blood_sugar", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_user_info_blood_sugar(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String userInfoBloodSugar = request.getParameter("userInfoBloodSugar");
        UserInfo userInfo = appRestfulService.editUserInfoBloodSugar(userInfoId, userInfoBloodSugar);
        return json = new JSONResult(userInfo, "100", true);
    }
    
    /**
     * 个人信息血尿酸修改保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/edit_user_info_blood_uric_acid", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_user_info_blood_uric_acid(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String userInfoBloodUricAcid = request.getParameter("userInfoBloodUricAcid");
        UserInfo userInfo = appRestfulService.editUserInfoBloodUricAcid(userInfoId, userInfoBloodUricAcid);
        return json = new JSONResult(userInfo, "100", true);
    }
    
    /**
     * 个人信息脂肪肝修改保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/edit_user_info_hepatic_adipose_infiltration", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_user_info_hepatic_adipose_infiltration(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String userInfoHepaticAdiposeInfiltration = request.getParameter("userInfoHepaticAdiposeInfiltration");
        UserInfo userInfo = appRestfulService.editUserInfoHepaticAdiposeInfiltration(userInfoId, userInfoHepaticAdiposeInfiltration);
        return json = new JSONResult(userInfo, "100", true);
    }
    
    /**
     * 排行榜列表查询
     * @param request
     * @return
     * @throws ParseException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/week_weight_rank", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult week_weight_rank(HttpServletRequest request) throws ParseException {
        int currentPage = Integer.parseInt(request.getParameter("page"));
        PageHelper.startPage(currentPage, BsetConsts.PER_PAGE_SIZE);
        int rankFlag = Integer.parseInt(request.getParameter("rankFlag"));
        String date = request.getParameter("date");
        List<WeeklyRank> weeklyRanks = appRestfulService.selectAllUserWeekFoodRank(rankFlag, date);
        List pageSetting = new ArrayList<Object>(); 
        PageInfo<WeeklyRank> pageInfo = new PageInfo<WeeklyRank>(weeklyRanks);
        pageSetting.add(pageInfo.getPages());//一共多少页
        pageSetting.add(pageInfo.getPageNum());//当前页
        Map<String, List> map = new LinkedHashMap<String, List>();
        List<String> list = new ArrayList<String>();
        for (WeeklyRank wr : weeklyRanks ) {
            UserInfo userInfo = userInfoService.findByUserInfo(wr.getUserInfoId());
            int DietDay = BocosoftUitl.getDietDays(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9), userInfo.getStartDate());
            list.add(String.valueOf(DietDay));
        }
        map.put("weeklyRanks", weeklyRanks);
        map.put("pageSetting", pageSetting);
        map.put("dietDayList", list);
        return json = new JSONResult(map,"", true);
    }
    
    /**
     * 减重排行首页界面减重人数
     * @param request
     * @return
     * @throws ParseException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/rank_home_people", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult rank_home_people(HttpServletRequest request) throws ParseException {
        //List<UserInfo> userInfoLists = userInfoService.getUserInfosByUserStatus(BsetConsts.USER_STATUS_1);
        List<UserInfo> ulis = userInfoService.getUserInfosByUserStatus(BsetConsts.USER_STATUS_1);
        Calendar cal = Calendar.getInstance();
        List<UserWeightData> uwds = userInfoService.findUserWeightDataByDate(BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9));
        Double dayWeight = 0.0;
        for (UserWeightData uwd : uwds) {
            dayWeight += uwd.getDeltaWeight() * BsetConsts.WEIGHT_NUMBER;
        }
        String parentPath = getClass().getResource("/").getFile().toString();
        //累计减重人数
        String peopleNumPath = parentPath + BsetConsts.PEOPLE_NUM_FILEPATH;
        String weightLossPath = parentPath + BsetConsts.WEIGHT_LOSS_FILEPATH;
        String allPeopleNum = BocosoftUitl.readTxtFile(peopleNumPath);
        String weightLossNum = BocosoftUitl.readTxtFile(weightLossPath);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("allPeopleNum",allPeopleNum);
        map.put("nowPeopleNum", ulis.size());
        map.put("allWeightLossNum", weightLossNum);
        map.put("dayWeightLossNum", dayWeight / BsetConsts.WEIGHT_NUMBER);
        return json = new JSONResult(map, "100", true);
    }

    
    /**
     * 减重排行首页界面
     * @param request
     * @return
     * @throws ParseException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/week_weight_rank_home", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult week_weight_rank_home(HttpServletRequest request) throws ParseException {
        String tmpUserInfoId = request.getParameter("userInfoId");
        String date = request.getParameter("date");
        WeeklyRank weeklyRank = new WeeklyRank();
        UserInfo userInfo = new UserInfo();
        if (tmpUserInfoId != null && !tmpUserInfoId.equals("")) {
            int userInfoId = Integer.parseInt(tmpUserInfoId);
            weeklyRank = appRestfulService.findWeekRankByUserInfoId(userInfoId, date);
            userInfo = userInfoService.findByUserInfo(userInfoId);
        }
        List<UserInfo> userInfoLists = userInfoService.getUserInfosByUserStatus(BsetConsts.USER_STATUS_1);
        WeeklyRank tmpWeeklyRank = appRestfulService.selectFirstWeekRankUser(BsetConsts.RANK_TYPE_1, date);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("weeklyRank", weeklyRank);
        map.put("userInfo", userInfo);
        map.put("lostUserInfos", userInfoLists.size());
        map.put("firstWeekRankUser", tmpWeeklyRank);
        return json = new JSONResult(map, "100", true);
    }
    
    /**
     * 营养师查询客户页面
     * @param request
     * @return
     * @throws ParseException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/dietitian_select_user_info", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult dietitian_select_user_info(HttpServletRequest request) throws ParseException {
        int dietitianId = Integer.parseInt(request.getParameter("dietitian_id"));
        int userStatus = Integer.parseInt(request.getParameter("userStatus"));
        List<UserInfo> userInfos = userInfoService.getUserInfosById(dietitianId, userStatus);
        List<UserBean> userBeanList = new ArrayList<UserBean>();
        for (UserInfo userInfo : userInfos) {
            UserBean userBean = new UserBean();
            UserData userData = userInfoService.findByUserLastData(userInfo.getId());
            userBean.setUserInfoId(userInfo.getId());
            userBean.setName(userInfo.getName());
            int dietDays = 0;
            Calendar cal = Calendar.getInstance();
            if (userInfo.getStartDate() != null) {//现在时间-营养师填写的开始时间
                if (userStatus == BsetConsts.USER_STATUS_1) {//正在期
                    userBean.setUserStatus(BsetConsts.USER_STATUS_1);
                    dietDays = BocosoftUitl.getDietDays(cal.getTime(), userInfo.getStartDate());
                    if (dietDays < 0) {
                        dietDays = 0;
                    }
                } else if (userStatus == BsetConsts.USER_STATUS_2) {//过渡期
                    userBean.setUserStatus(BsetConsts.USER_STATUS_2);
                    dietDays = BocosoftUitl.getDietDays(cal.getTime(), userInfo.getEndDate());
                    if (dietDays < 0) {
                        dietDays = 1;
                    }
                }
                userBean.setDietDay(dietDays);
            } else {
                if (userStatus == BsetConsts.USER_STATUS_3) {//完成期
                    userBean.setUserStatus(BsetConsts.USER_STATUS_3);
                    DietPhaseInfo dietPhaseInfo = userInfoService.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase() - 1);
                    dietDays = BocosoftUitl.getDietDays(cal.getTime(), dietPhaseInfo.getTransitionEndDate());
                    if (dietDays < 0) {
                        dietDays = 1;
                    }
                    userBean.setWeekly(dietDays / 7 + 1);
                    userBean.setAlarmFlag(dietPhaseInfo.getAlarmFlag());
                }
                userBean.setDietDay(dietDays);
            }
            if (userData != null) {
                userBean.setComfortLevel(userData.getComfortLevel());
                int day = BocosoftUitl.compare2Day(cal.getTime(), userData.getDate());
                if (day > 0) {
                    userBean.setDataFlag(true);
                } else {
                    //查询体重
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(new Date());
                    cal2.add(Calendar.DATE, -1);
                    Date resultDate = cal2.getTime(); // 结果  
                    UserData tday = appRestfulService.findByUserData(userInfo.getId(), BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9));
                    UserData yday = appRestfulService.findByUserData(userInfo.getId(), BocosoftUitl.dateToString(resultDate, BsetConsts.DATE_FORMAT_9));
                    if(tday != null || yday != null){
                        if(tday.getWeight() != null){//如果未填体重
                            userBean.setDataFlag(false);
                        } else if (yday != null) {
                            if (yday.getWeight() != null) {
                                userBean.setDataFlag(false);
                            }
                        } else {
                            userBean.setDataFlag(true);
                        }
                    } else {
                        userBean.setDataFlag(true);
                    }
                }
                UserWeightData tmpuUerWeightData = userInfoService.findFirstUserWeightDataByDate(userInfo.getId(), BocosoftUitl.dateToString(userInfo.getStartDate(), BsetConsts.DATE_FORMAT_9));
                if(tmpuUerWeightData != null) {
                    if (userInfo.getEndDate() != null) {
                        UserWeightData userWeightData = userInfoService.findLastUserWeightDataByDate(userInfo.getId(), BocosoftUitl.dateToString(userInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
                        userBean.setLostTotalWeight((tmpuUerWeightData.getWeight() * BsetConsts.WEIGHT_NUMBER - userWeightData.getWeight() * BsetConsts.WEIGHT_NUMBER) / BsetConsts.WEIGHT_NUMBER);
                    } else {
                        UserWeightData userWeightData = userInfoService.findByUserLastWeightData(userInfo.getId());
                        userBean.setLostTotalWeight((tmpuUerWeightData.getWeight() * BsetConsts.WEIGHT_NUMBER - userWeightData.getWeight() * BsetConsts.WEIGHT_NUMBER) / BsetConsts.WEIGHT_NUMBER);
                    }
                } else {
                    userBean.setDietDay(0);
                    userBean.setLostTotalWeight(0.0);
                }
            } else {
                userBean.setLostTotalWeight(0.0);
                userBean.setDataFlag(true);
            }
            if (userInfo.getTopFlag() == BsetConsts.TOP_FLAG_1) {
                userBean.setTopFlag(true);
            } else {
                userBean.setTopFlag(false);
            }
            userBean.setHeadPhotoUrl(userInfo.getFilePath());
            userBeanList.add(userBean);
        }
        return json = new JSONResult(userBeanList, "100", true);
    }
    
    /**
     * 取得早餐的所有信息
     * @param request
     * @param response
     * @return
     * @throws InterruptedException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/find_user_info_breakfast", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult find_user_info_breakfast(HttpServletRequest request,HttpServletResponse response) throws InterruptedException{
        Thread.sleep(1000);//等待图片上传完成
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        List<String> data = new ArrayList<String>();
        UserData ud = appRestfulService.findByUserData(userInfoId, date);
        if (ud != null) {
            data.add(ud.getBreakfast());
        }
        List<UserData2> ud2s = appRestfulService.findByUserData2(userInfoId, date, 1);
        if (ud2s.size() > 0) {
            for (UserData2 ud2 : ud2s) {
                data.add(ud2.getFilePath());
            }
        }
        return json = new JSONResult(data, "100", true);
      
    }
    
    /**
     * 取得午餐的所有信息
     * @param request
     * @param response
     * @return
     * @throws InterruptedException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/find_user_info_lunch", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult find_user_info_lunch(HttpServletRequest request,HttpServletResponse response) throws InterruptedException{
        Thread.sleep(1000);//等待图片上传完成
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        List<String> data = new ArrayList<String>();
        UserData ud = appRestfulService.findByUserData(userInfoId, date);
        if (ud != null) {
            data.add(ud.getLunch());
        }
        List<UserData2> ud2s = appRestfulService.findByUserData2(userInfoId, date, 2);
        if (ud2s.size() > 0) {
            for (UserData2 ud2 : ud2s) {
                data.add(ud2.getFilePath());
            }
        }
        return json = new JSONResult(data, "100", true);
      
    }
    
    /**
     * 取得晚餐的所有信息
     * @param request
     * @param response
     * @return
     * @throws InterruptedException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/find_user_info_dinner", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult find_user_info_dinner(HttpServletRequest request,HttpServletResponse response) throws InterruptedException{
        Thread.sleep(1000);//等待图片上传完成
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        List<String> data = new ArrayList<String>();
        UserData ud = appRestfulService.findByUserData(userInfoId, date);
        if (ud != null) {
            data.add(ud.getDinner());
        }
        List<UserData2> ud2s = appRestfulService.findByUserData2(userInfoId, date, 3);
        if (ud2s.size() > 0) {
            for (UserData2 ud2 : ud2s) {
                data.add(ud2.getFilePath());
            }
        }
        return json = new JSONResult(data, date, true);
    }
    
    /**
     * 头像修改上传保存
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/user_info_head_photo_save", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult user_info_head_photo_save(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
        Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象
        String upaloadUrl = request.getSession().getServletContext().getRealPath("/")+"upload/headPhoto/";//得到当前工程路径拼接上文件名
        File dir = new File(upaloadUrl);
        for(MultipartFile file :files.values()){
            if (!file.isEmpty()) {
                if(!dir.exists()) {
                    dir.mkdirs();
                }//目录不存在则创建
                //删除以前的上传文件
                if (userInfo.getFileName() != null) {
                    File deleteFile = new File(upaloadUrl, userInfo.getFileName());
                    deleteFile.delete();
                }
                String fileName = file.getOriginalFilename();
                int index = fileName.lastIndexOf(".");
                String targetFileName = userInfo.getId() + "headPhoto"+ fileName.substring(index);//文件名称
                File tagetFile = new File(upaloadUrl + targetFileName);//创建文件对象
                if(!tagetFile.exists()){//文件名不存在 则新建文件，并将文件复制到新建文件中
                    try {
                        tagetFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        file.transferTo(tagetFile);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String filePath = BsetConsts.SERVICE_URL +"headPhoto/" + targetFileName;
                if (userInfo.getFileName() == null) {
                    userInfo.setFileName(targetFileName);
                    userInfo.setFilePath(filePath);
                    userInfoService.updataUserInfo(userInfo);
                }
            }
        }
        return json = new JSONResult("", "100", true);
    }
    
    /**
     * 取得头像的地址和昵称
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/find_user_info_head_photo_by_userInfoId", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult find_user_info_head_photo_by_userInfoId(HttpServletRequest request,HttpServletResponse response){
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        return json = new JSONResult(userInfo,"100", true);
    }
    
    /**
     * 营养师对客户的置顶
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/set_user_top_flag", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult set_user_top_flag(HttpServletRequest request,HttpServletResponse response){
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        int dietitianId = Integer.parseInt(request.getParameter("dietitianId"));
        int topFlag = Integer.parseInt(request.getParameter("topFlag"));
        int flag = userInfoService.set_user_top_flag(dietitianId, userInfoId, topFlag);
        if (flag > 0) {
            return json = new JSONResult(flag, "100", true);
        }
        return json = new JSONResult(flag, "100", false);
    }
    
    /**
     * 营养师对客户的备注修改
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/set_user_note", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult set_user_note(HttpServletRequest request,HttpServletResponse response){
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        int dietitianId = Integer.parseInt(request.getParameter("dietitianId"));
        String note = request.getParameter("note");
        int flag = userInfoService.set_user_note(dietitianId, userInfoId, note);
        if (flag > 0) {
            return json = new JSONResult(flag, "100", true);
        }
        return json = new JSONResult(flag, "100", false);
    }
    
    /**
     * 营养师对客户的减重开始时间的修改
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/set_user_start_date", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult set_user_start_date(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userinfo = userInfoService.findByUserInfo(userInfoId);
        Boolean peopleNumFlag = false;
        if(userinfo.getStartDate() == null){
            peopleNumFlag = true;
        }
        int dietitianId = Integer.parseInt(request.getParameter("dietitianId"));
        String startDate = request.getParameter("startDate");
        int flag = userInfoService.set_user_start_date(dietitianId, userInfoId, startDate);
        if (flag > 0) {
            if(peopleNumFlag){//减重人数变更
                String parentPath = getClass().getResource("/").getFile().toString();
                String peopleNumPath = parentPath + BsetConsts.PEOPLE_NUM_FILEPATH;
                String tmpPeopleNum = BocosoftUitl.readTxtFile(peopleNumPath);
                int peopleNumLoss = 1+Integer.parseInt(tmpPeopleNum);
                BocosoftUitl.setTxtMessage(String.valueOf(peopleNumLoss), peopleNumPath);
            }
            return json = new JSONResult(flag, "100", true);
        }
        return json = new JSONResult(flag, "100", false);
    }
    
    /**
     * 营养师对客户的减重结束时间的修改
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/set_user_end_date", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult set_user_end_date(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        int dietitianId = Integer.parseInt(request.getParameter("dietitianId"));
        String endDate = request.getParameter("endDate");
        int flag = userInfoService.set_user_end_date(dietitianId, userInfoId, BocosoftUitl.stringToDate(endDate, BsetConsts.DATE_FORMAT_9));
        if (flag > 0) {
            return json = new JSONResult(flag, "100", true);
        }
        return json = new JSONResult(flag, "100", false);
    }
    
    /**
     * 营养师对客户的指导的修改
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/set_user_comments", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult set_user_comments(HttpServletRequest request,HttpServletResponse response) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        int dietitianId = Integer.parseInt(request.getParameter("dietitianId"));
        String comments = request.getParameter("comments");
        String date = request.getParameter("date");
        int flag = userInfoService.set_user_comments(dietitianId, userInfoId, date, comments);
        if (flag > 0) {
            return json = new JSONResult(flag, "100", true);
        }
        return json = new JSONResult(flag, "100", false);
    }
    
    /**
     * 营养师对客户的本周饮食推荐的修改
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/set_user_weekly_menu", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult set_user_weekly_menu(HttpServletRequest request,HttpServletResponse response) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        int dietitianId = Integer.parseInt(request.getParameter("dietitianId"));
        String weeklyMenu = request.getParameter("weeklyMenu");
        String date = request.getParameter("date");
        int flag = userInfoService.set_user_weekly_menu(dietitianId, userInfoId, date, weeklyMenu);
        if (flag > 0) {
            return json = new JSONResult(flag, "100", true);
        }
        return json = new JSONResult(flag, "100", false);
    }
    
    /**
     * 营养师对客户的本周饮食推荐的修改
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/set_user_weekly_sport", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult set_user_weekly_sport(HttpServletRequest request,HttpServletResponse response) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        int dietitianId = Integer.parseInt(request.getParameter("dietitianId"));
        String weeklySport = request.getParameter("weeklySport");
        String date = request.getParameter("date");
        int flag = userInfoService.set_user_weekly_sport(dietitianId, userInfoId, date, weeklySport);
        if (flag > 0) {
            return json = new JSONResult(flag, "100", true);
        }
        return json = new JSONResult(flag, "100", false);
    }
    
    /**
     * 客户的是否允许他人查看我的纪录
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/set_user_look_flag", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult set_user_look_flag(HttpServletRequest request,HttpServletResponse response){
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        int lookFlag = Integer.parseInt(request.getParameter("lookFlag"));
        int flag = appRestfulService.set_user_look_flag(userInfoId, lookFlag);
        if (flag > 0) {
            return json = new JSONResult(flag, "100", true);
        }
        return json = new JSONResult(flag, "100", false);
    }
    
    /**
     * 获取是否可以查看客户信息的状态
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/select_user_look_flag", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_user_look_flag(HttpServletRequest request,HttpServletResponse response){
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        if (userInfo.getLookFlag() == BsetConsts.LOOK_FLAG_1) {//允许别人查看数据
            return json = new JSONResult("", "100", true);
        }
        return json = new JSONResult("", "100", false);
    }
    
    /**
     * 客户的历史减重数据列表
     * @param request
     * @param response
     * @return
     * @throws ParseException  
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/select_user_histroy_weight_list", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_user_histroy_weight(HttpServletRequest request,HttpServletResponse response) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        List<UserHisDataBean> userHisDataBeans = new ArrayList<UserHisDataBean>();
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        List<DietPhaseInfo> dpis = userInfoService.findAlldietPhaseInfo(userInfo.getId(), userInfo.getPhase());
        for (DietPhaseInfo dip : dpis) {
            UserHisDataBean udb = new UserHisDataBean();
            udb.setId(dip.getId());
            udb.setStartDate(BocosoftUitl.dateToString(dip.getStartDate(), BsetConsts.DATE_FORMAT_15));
            udb.setEndDate(BocosoftUitl.dateToString(dip.getEndDate(), BsetConsts.DATE_FORMAT_15));
            udb.setPhaseCount(dip.getPhaseCount());
            userHisDataBeans.add(udb);
        }
        return json = new JSONResult(userHisDataBeans, "100", true);
    }
    
    /**
     * 客户的历史减重数据详情
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/select_user_histroy_weight_data", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_user_histroy_weight_data(HttpServletRequest request,HttpServletResponse response) {
        int dietPhaseInfoId = Integer.parseInt(request.getParameter("dietPhaseInfoId"));
        DietPhaseInfo dietPhaseInfo = userInfoService.select_user_histroy_weight_Data(dietPhaseInfoId);
        UserInfo userInfo = userInfoService.findByUserInfo(dietPhaseInfo.getUserInfoId());
        List<UserWeightData> userwds = userInfoService.findUserWeightDatasByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                ,BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
        int DietDays = BocosoftUitl.getDietDays(dietPhaseInfo.getEndDate(), dietPhaseInfo.getStartDate());
        List<String> dpi = new ArrayList<String>();
        dpi.add(BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_15));
        dpi.add(BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_15));
        dpi.add(userInfoService.findFirstUserWeightDataByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)).getWeight().toString());
        dpi.add(userInfoService.findLastUserWeightDataByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9)).getWeight().toString());
        dpi.add(dietPhaseInfo.getDietitianName());
        List<String> xAxisData = BocosoftUitl.createAllDayDate(DietDays);
        List<String> yAxisData = BocosoftUitl.createAllDayWeightDateApp(userwds, DietDays);
        List<String> idealBodyWeightAllData = BocosoftUitl.createidealBodyWeightDate(userInfo.getIdealBodyWeight(), DietDays);
        List<String> allMaxAndMin = BocosoftUitl.getListsMinAndMax(userInfo.getIdealBodyWeight(), userwds);//获取最大值最小值
        List<String> showLastData = BocosoftUitl.createShowLastData(2, null, userwds);
        Map<String, List> map = new LinkedHashMap<String, List>();
        map.put("dietPhaseInfo", dpi);
        map.put("xAxisData", xAxisData);
        map.put("yAxisData", yAxisData);
        map.put("idealBodyWeightAllData", idealBodyWeightAllData);
        map.put("allMaxAndMin", allMaxAndMin);
        map.put("allshowLastData", showLastData);
        return json = new JSONResult(map, "100", true);
    }
    
    /**
     * 客户的本周饮食推荐和运动推荐的获取
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/select_user_weekly_recommend", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_user_weekly_sport(HttpServletRequest request,HttpServletResponse response) throws ParseException {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        WeeklyRecommend wr = new WeeklyRecommend();
        wr.setUserInfoId(userInfoId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        wr.setWeekCount(weekOfYear);
        wr.setYyyy(year);
        wr = userInfoService.findWeeklyRecomendById(wr);
        if (wr == null) {
            return json = new JSONResult(wr, "100", false);
        }
        return json = new JSONResult(wr, "100", true);
    }
    
    /**
     * 检查版本是否更新
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/select_version", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_version(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        String parentPath = getClass().getResource("/").getFile().toString();
        String versionPath = parentPath + BsetConsts.VERSION;
        String versionNum = BocosoftUitl.readTxtFile(versionPath);
        return json = new JSONResult(versionNum, "100", false);
    }
    
    /**
     * 获取新版本安装包路径
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/get_new_version_apk", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_new_version_apk(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        String parentPath = getClass().getResource("/").getFile().toString();
        String versionApkPath = parentPath + BsetConsts.VERSION_APK;
        String apkSrc = BocosoftUitl.readTxtFile(versionApkPath);
        return json = new JSONResult(apkSrc, "100", false);
    }
    
    /**
     * 获取营养师信息
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/get_dietitian_userLogin", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_dietitian_userLogin(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        Dietitian dietitian = appRestfulService.findByDietitian(userInfoId);
        return json = new JSONResult(dietitian, "100", false);
    }
    /**
     * 绑定手机号
     * @throws ParseException 
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/mobile_binding", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult mobile_binding(HttpServletRequest request) throws ParseException, NoSuchAlgorithmException, UnsupportedEncodingException {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String phoneNo = request.getParameter("phoneNo");
        String passwd = request.getParameter("passwd");
        String codeTime = request.getParameter("codeTime");
        if (codeTime == null) {
            return json = new JSONResult("", "101", false);
        }
        String time = codeTime.substring(0,4)
                    +"-"+codeTime.substring(4,6)
                    +"-"+codeTime.substring(6,8)
                    +" "+codeTime.substring(8,10)
                    +":"+codeTime.substring(10,12)
                    +":"+codeTime.substring(12,14);
        Calendar cal = Calendar.getInstance();
        cal.setTime(BocosoftUitl.stringToDate(time, BsetConsts.DATE_FORMAT_1));
        //判断是否过期
        long tmpTime = cal.getTimeInMillis();//发送验证码时间
        long nowTime = new Date().getTime();//现在时间
        if(nowTime - tmpTime > BsetConsts.TIME_MINUTE * 5){
            return json = new JSONResult("", "timeOut", false);
        } else {
            UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
            if(userInfo.getUserLoginInfoId()!=null){//查看是否有手机注册账号
                UserLoginInfo ulis = appRestfulService.findUserLoginInfoById(userInfo.getUserLoginInfoId());
                if(ulis.getLoginId().equals(phoneNo)){//如果相等，修改密码和flag
                    appRestfulService.editPhonePasswdSave(phoneNo, passwd);
                    return json = new JSONResult(ulis, "100", true);
                } else {
                    //验证电话号是否已经注册
                    boolean reg = appRestfulService.findUserLoginInfoByPhone(phoneNo);
                    if(reg == false){
                        return json = new JSONResult("", "haveReg", false);
                    }else{
                        //检查此号码营养师端是否存在
                        boolean dietitian = appRestfulService.findDietitianByPhone(phoneNo);
                        if(dietitian){
                            ulis.setLoginId(phoneNo);
                            ulis.setPasswd(BocosoftUitl.getMd5Str(passwd));
                            ulis.setLoginFlag(BsetConsts.LOGIN_FLAG_1);
                            appRestfulService.updateUserLoginInfo(ulis);
                            return json = new JSONResult(ulis, "100", true);
                        } else {
                            return json = new JSONResult("", "dietitianHaveReg", false);
                        }
                       
                    }
                }
            }else{
                //验证电话号是否已经注册
                boolean reg = appRestfulService.findUserLoginInfoByPhone(phoneNo);
                if(reg == false){
                    return json = new JSONResult("", "haveReg", false);
                } else {
                    //检查此号码营养师端是否存在
                    boolean dietitian = appRestfulService.findDietitianByPhone(phoneNo);
                    if(dietitian){
                        UserLoginInfo userli = appRestfulService.getDataByPhoneNo(phoneNo);
                        if (userli != null) {
                            userli.setLoginFlag(BsetConsts.LOGIN_FLAG_1);
                            appRestfulService.updateUserLoginInfo(userli);
                            userInfo.setUserLoginInfoId(userli.getId());
                            userInfoService.updataUserInfo(userInfo);
                            return json = new JSONResult(userli, "100", true);
                        } else {
                            UserLoginInfo uli = appRestfulService.mobileRegistrationSave(phoneNo, passwd);
                            userInfo.setUserLoginInfoId(uli.getId());
                            userInfoService.updataUserInfo(userInfo);
                            return json = new JSONResult(uli, "100", true);
                        }
                    } else {
                        return json = new JSONResult("", "dietitianHaveReg", false);
                    }
                }
            }
        }
    }
    /**
     * 解除绑定的手机号
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/remove_binding_phone", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult remove_binding_phone(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        UserLoginInfo uli = appRestfulService.findUserLoginInfoById(userInfo.getUserLoginInfoId());
        uli.setLoginFlag(0);
        if (userInfo.getAddFlag() == 0) {//未参加尼基计划，解除绑定删除和手机号的关联
            //逻辑未明确，是否应该删除该条uli
            userInfo.setUserLoginInfoId(null);
            userInfoService.updataUserInfo(userInfo);
        }
        appRestfulService.updateUserLoginInfoPhone(uli);
        return json = new JSONResult("", "100", true);
    }
    /**
     * 查询客户userinfo数据
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/select_userInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_userInfo(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        return json = new JSONResult(userInfo, "100", true);
    }
    /**
     * 查询客户userLogin数据
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/select_userLogin", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_userLogin(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        int userLoginId = Integer.parseInt(request.getParameter("userLoginId"));
        UserLoginInfo uli = appRestfulService.findUserLoginInfoById(userLoginId);
        return json = new JSONResult(uli, "100", true);
    }
    
    /**
     * 验证手机号时隐秘注册账号
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/mobile_registration_secret", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult mobile_registration_secret(HttpServletRequest request) throws ParseException {
        String phoneNo = request.getParameter("phoneNo");
        String passwd =  BocosoftUitl.makeRandomPassword(BsetConsts.RANDOM_AUTHENTICATION_CODE_LEN);//"Nijikeji5197";
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        if(userInfo.getUserLoginInfoId()!=null){//查看是否有手机注册账号
            UserLoginInfo ulis = appRestfulService.findUserLoginInfoById(userInfo.getUserLoginInfoId());
            if(ulis.getLoginId().equals(phoneNo)){//如果相等，修改密码和flag
                appRestfulService.editPhonePasswdSave(phoneNo, passwd);
                return json = new JSONResult(ulis, "100", true);
            }else {
                //验证电话号是否已经注册
                boolean reg = appRestfulService.findUserLoginInfoByPhone(phoneNo);
                if(reg == false){
                    return json = new JSONResult("", "haveReg", false);
                }else{
                    //检查此号码营养师端是否存在
                    boolean dietitian = appRestfulService.findDietitianByPhone(phoneNo);
                    if(dietitian){
                        ulis.setLoginId(phoneNo);
                        try {
                            ulis.setPasswd(BocosoftUitl.getMd5Str(passwd));
                        } catch (NoSuchAlgorithmException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        ulis.setLoginFlag(BsetConsts.LOGIN_FLAG_1);
                        appRestfulService.updateUserLoginInfo(ulis);
                        return json = new JSONResult(ulis, "100", true);
                    } else {
                        return json = new JSONResult("", "dietitianHaveReg", false);
                    }
                   
                }
            }
        }else{
            //验证电话号是否已经注册
            boolean reg = appRestfulService.findUserLoginInfoByPhone(phoneNo);
            if(reg == false){
                return json = new JSONResult("", "haveReg", false);
            } else {
                //检查此号码营养师端是否存在
                boolean dietitian = appRestfulService.findDietitianByPhone(phoneNo);
                if(dietitian){
                    UserLoginInfo userli = appRestfulService.getDataByPhoneNo(phoneNo);
                    if (userli != null) {
                        userli.setLoginFlag(BsetConsts.LOGIN_FLAG_1);
                        appRestfulService.updateUserLoginInfo(userli);
                        userInfo.setUserLoginInfoId(userli.getId());
                        userInfoService.updataUserInfo(userInfo);
                        return json = new JSONResult(userli, "100", true);
                    } else {
                        UserLoginInfo uli = appRestfulService.mobileRegistrationSave(phoneNo, passwd);
                        userInfo.setUserLoginInfoId(uli.getId());
                        userInfoService.updataUserInfo(userInfo);
                        return json = new JSONResult(uli, "100", true);
                    }
                } else {
                    return json = new JSONResult("", "dietitianHaveReg", false);
                }
            }
        }
        /*//验证电话号是否已经注册
        boolean reg = appRestfulService.findUserLoginInfoByPhone(phoneNo);
        if(reg == false){
            return json = new JSONResult("", "haveReg", false);
        } else {
            //检查此号码营养师端是否存在
            boolean dietitian = appRestfulService.findDietitianByPhone(phoneNo);
            if(dietitian){
                UserLoginInfo uli = appRestfulService.mobileRegistrationSave(phoneNo, passwd);
                userInfo.setUserLoginInfoId(uli.getId());
                userInfoService.updataUserInfo(userInfo);
                return json = new JSONResult(uli, "100", true);
            }else{
                return json = new JSONResult("", "dietitianHaveReg", false);
            }
        }*/
    }
    
    /**
     * 查看营养师是否指导
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/find_user_data_comments_read", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult find_user_data_comments_read(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        String tmpUserInfoId = request.getParameter("userInfoId");
        if (tmpUserInfoId != null && !tmpUserInfoId.equals("")){
            int userInfoId = Integer.parseInt(tmpUserInfoId);
                Calendar cal = Calendar.getInstance();
                UserData userData = userInfoService.findUserInfoByDate(userInfoId, BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9));
                if(userData != null){
                    if(userData.getLookCommentsFlag() != null && userData.getLookCommentsFlag() == 1){//营养师指导完成
                        return json = new JSONResult("", "100", true);
                    }
                }
        }
        return json = new JSONResult("", "100", false);
    }
    
    /**
     * 修改营养师指导flag
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/update_user_data_comments_read", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult update_user_data_comments_read(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        Calendar cal = Calendar.getInstance();
        UserData userData = userInfoService.findUserInfoByDate(userInfoId, BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9));
        if(userData != null){
            userData.setLookCommentsFlag(0);
            int flag = appRestfulService.updateUserDataLookCommentsFlag(userData);
            if(flag>0){
                return json = new JSONResult("", "100", true);
            }
        }
        return json = new JSONResult("", "100", false);
    }
    /**
     * 查询客户是否有历史数据
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/select_userInfo_history", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_userInfo_history(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        String uid = request.getParameter("userInfoId");
        if(uid == null || uid == ""){
            return json = new JSONResult("", "100", false);
        }else{
            int userInfoId = Integer.parseInt(uid);
            UserInfo userinfo = userInfoService.findByUserInfo(userInfoId);
            if(userinfo.getPhase()>1){
                return json = new JSONResult("", "100", true);
            }
        }
        return json = new JSONResult("", "100", false);
    }
    
    /**
     * 查询客户是否可以修改密码
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/select_userInfo_password_update", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_userInfo_password_update(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        if(userInfo.getUserLoginInfoId()!= null){
            UserLoginInfo uli = appRestfulService.findUserLoginInfoById(userInfo.getUserLoginInfoId());
            if(uli.getLoginFlag()==1){
                return json = new JSONResult(uli, "100", true);
            }else{
                return json = new JSONResult(uli, "100", false);
            }
        }
        return json = new JSONResult("", "100", false);
    }
    /**
     * 修改用户期盼联系方式account
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/edit_user_info_account", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_user_info_account(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String account = request.getParameter("account");
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        userInfo.setAccount(account);
        int flag = userInfoService.updataUserInfo(userInfo);
        if(flag > 0){
            return json = new JSONResult("", "100", true);
        }
        return json = new JSONResult("", "100", false);
    }
    
    /**
     * 清除提醒的状态
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/clean_user_alarm_flag", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult clean_user_alarm_flag(HttpServletRequest request,HttpServletResponse response) throws ParseException{
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        DietPhaseInfo dietPhaseInfo = userInfoService.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase() - 1);
        dietPhaseInfo.setAlarmFlag(0);
        userInfoService.updateDietPhaseInfo(dietPhaseInfo);
        return json = new JSONResult("", "100", true);
    }
    
    
    /**app_rest_ful
     * 获取淘宝店铺网址
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/get_taobao_url", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_taobao_url(HttpServletRequest request) {
        String parentPath = getClass().getResource("/").getFile().toString();
        String versionApkPath = parentPath + BsetConsts.TAOBAO_URL;
        String taobaoUrl = BocosoftUitl.readTxtFile(versionApkPath);
        return json = new JSONResult(taobaoUrl, "100", true);
    }
}
