package cn.com.bocosoft.helper;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.bocosoft.common.BocosoftUitl;
import cn.com.bocosoft.common.BsetConsts;
import cn.com.bocosoft.common.SpringBeanUtils;
import cn.com.bocosoft.dao.DietPhaseInfoMapper;
import cn.com.bocosoft.dao.DietitianMapper;
import cn.com.bocosoft.dao.SystemUserMapper;
import cn.com.bocosoft.dao.UserInfoMapper;
import cn.com.bocosoft.dao.UserLoginInfoMapper;
import cn.com.bocosoft.dao.UserWeightDataMapper;
import cn.com.bocosoft.model.DietPhaseInfo;
import cn.com.bocosoft.model.Dietitian;
import cn.com.bocosoft.model.SystemUser;
import cn.com.bocosoft.model.UserInfo;
import cn.com.bocosoft.model.UserLoginInfo;
import cn.com.bocosoft.model.UserWeightData;
import cn.com.bocosoft.tag.helper.HelperTag;

public class CommonHelper extends HelperTag {
    
    private final static Logger log = LoggerFactory.getLogger(CommonHelper.class);
    
    public Integer getUsers(Integer dietitianId) {
        UserInfoMapper userInfoMapper = (UserInfoMapper) SpringBeanUtils.getBean("userInfoMapper");
        int userCount = userInfoMapper.get_users(dietitianId);
        setWriter("user_count");
        return userCount;
    }
    
    /**
     * 日期转换为用户自定义的日期格式字符串（例如：yyyy-MM-dd）
     * @param date
     */
    public String dateFormat1(Date date) throws IOException{
        if(date != null && !date.equals("")){
            String date2 =  BocosoftUitl.dateToString(date, BsetConsts.DATE_FORMAT_9);
            setWriter("date_format");
            return date2;
        }
        return " - ";
    }
    
    /**
     * 根据user_login_Id取得客户手机号
     * @param date
     */
    public String getUserPhoneNo(Integer userLoginInfoId) {
        UserLoginInfoMapper userLoginInfoMapper = (UserLoginInfoMapper) SpringBeanUtils.getBean("userLoginInfoMapper");
        UserLoginInfo uli = userLoginInfoMapper.getUserLoginInfo(userLoginInfoId);
        setWriter("phone_no");
        return uli.getLoginId();
    }
    
    /**
     * 根据userInfoId取得客户减重第几天
     * @param date
     */
    public String getUserDietDays(Integer userInfoId) {
        UserInfoMapper userInfoMapper = (UserInfoMapper) SpringBeanUtils.getBean("userInfoMapper");
        DietPhaseInfoMapper dietPhaseInfoMapper = (DietPhaseInfoMapper) SpringBeanUtils.getBean("dietPhaseInfoMapper");
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        Calendar cal = Calendar.getInstance();
        int dietDay = 0;
        if (userInfo.getUserStatus() == BsetConsts.USER_STATUS_1) {
            dietDay = BocosoftUitl.compare2Day(cal.getTime(), userInfo.getStartDate());
//            return "减重期第"+dietDay+"天";
        } else if (userInfo.getUserStatus() == BsetConsts.USER_STATUS_2) {
            dietDay = BocosoftUitl.compare2Day(cal.getTime(), userInfo.getEndDate());
//            return "过渡期第"+dietDay+"天";
        } else if (userInfo.getUserStatus() == BsetConsts.USER_STATUS_3) {
            DietPhaseInfo dpi = dietPhaseInfoMapper.findDietPhaseInfo(userInfoId, userInfo.getPhase() -1);
            dietDay = BocosoftUitl.compare2Day(cal.getTime(), dpi.getTransitionEndDate());
//            return "完成期第"+dietDay+"天";
        }
        setWriter("diet_days");
        return String.valueOf(dietDay);
    }
    
    /**
     * 根据user_login_Id取得客户账号
     * @param date
     */
    public String getUserLoginId(Integer userLoginInfoId) {
        UserLoginInfoMapper userLoginInfoMapper = (UserLoginInfoMapper) SpringBeanUtils.getBean("userLoginInfoMapper");
        UserLoginInfo uli = userLoginInfoMapper.getUserLoginInfo(userLoginInfoId);
        setWriter("login_id");
        return uli.getLoginId();
    }
    
    /**
     * 根据userInfoId取得客户的营养师
     * @param date
     */
    public String getUserDietitian(Integer dietitianId) {
        DietitianMapper dietitianMapper = (DietitianMapper) SpringBeanUtils.getBean("dietitianMapper");
        Dietitian dietitian = dietitianMapper.selectByPrimaryKey(dietitianId);
        setWriter("dietitian_name");
        return dietitian.getName();
    }
    
//    /**
//     * 根据user_login_Id取得客户注册方式
//     * @param date
//     */
//    public String getUserLoginFlag(Integer userLoginInfoId) {
//        UserLoginInfoMapper userLoginInfoMapper = (UserLoginInfoMapper) SpringBeanUtils.getBean("userLoginInfoMapper");
//        UserLoginInfo uli = userLoginInfoMapper.getUserLoginInfo(userLoginInfoId);
//        String loginFlag = "手机注册";
//        switch (uli.getLoginFlag()) {
//        case BsetConsts.REGISTER_TYPE_1:
//            loginFlag = "手机注册";
//            break;
//        case BsetConsts.REGISTER_TYPE_2:
//            loginFlag = "微信注册";
//            break;
//        case BsetConsts.REGISTER_TYPE_3:
//            loginFlag = "QQ注册";
//            break;
//        case BsetConsts.REGISTER_TYPE_4:
//            loginFlag = "微博注册";
//            break;
//        default:
//            loginFlag = "手机注册";
//            break;
//        }
//        setWriter("login_flag");
//        return loginFlag;
//    }
    
    /**
     * 根据user_login_Id取得客户注册时间
     * @param date
     */
    public String getUserCreateTime(Integer userLoginInfoId) {
        UserLoginInfoMapper userLoginInfoMapper = (UserLoginInfoMapper) SpringBeanUtils.getBean("userLoginInfoMapper");
        UserLoginInfo uli = userLoginInfoMapper.getUserLoginInfo(userLoginInfoId);
        setWriter("create_time");
        return BocosoftUitl.dateToString(uli.getCreateTime(), BsetConsts.DATE_FORMAT_9);
    }
}