package cn.com.bocosoft.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.bocosoft.common.BocosoftUitl;
import cn.com.bocosoft.common.BsetConsts;
import cn.com.bocosoft.dao.DietitianMapper;
import cn.com.bocosoft.dao.ThirdPartyLoginMapper;
import cn.com.bocosoft.dao.UserData2Mapper;
import cn.com.bocosoft.dao.UserDataMapper;
import cn.com.bocosoft.dao.UserInfoMapper;
import cn.com.bocosoft.dao.UserLoginInfoMapper;
import cn.com.bocosoft.dao.UserWeightDataMapper;
import cn.com.bocosoft.dao.WeeklyRankMapper;
import cn.com.bocosoft.model.Dietitian;
import cn.com.bocosoft.model.ThirdPartyLogin;
import cn.com.bocosoft.model.UserData;
import cn.com.bocosoft.model.UserData2;
import cn.com.bocosoft.model.UserInfo;
import cn.com.bocosoft.model.UserLoginInfo;
import cn.com.bocosoft.model.UserWeightData;
import cn.com.bocosoft.model.WeeklyRank;
import cn.com.bocosoft.service.AppRestfulService;
@Service
public class AppRestfulServiceImp implements AppRestfulService{

    @Resource
    UserLoginInfoMapper userLoginInfoMapper;
    
    @Resource
    ThirdPartyLoginMapper thirdPartyLoginMapper;
    
    @Resource
    UserInfoMapper userInfoMapper;
    
    @Resource
    UserDataMapper userDataMapper;
    
    @Resource
    UserWeightDataMapper userWeightDataMapper;
    
    @Resource
    DietitianMapper dietitianMapper;
    
    @Resource
    WeeklyRankMapper weeklyRankMapper;
    
    @Resource
    UserData2Mapper userData2Mapper;
    
    @Override
    public UserLoginInfo mobileRegistrationSave(String phoneNo, String passwd) {
        UserLoginInfo uli = new UserLoginInfo();
        uli.setLoginId(phoneNo);
        try {
            uli.setPasswd(BocosoftUitl.getMd5Str(passwd));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        uli.setLoginFlag(1);
        int flag = userLoginInfoMapper.userLonginInfoSave(uli);
        if (flag > 0) {
            uli = userLoginInfoMapper.findByLoginUser(phoneNo);
        }
        return uli;
    }

    /**
     * 验证注册手机号是否已注册
     */
    @Override
    public boolean findUserLoginInfoByPhone(String phoneNo) {
        UserLoginInfo uli = userLoginInfoMapper.findByLoginUser(phoneNo);
        if(uli != null){
            //查找和这个登录关联的客户
            List<UserInfo> uis = userInfoMapper.getUserInfoByUserLoginId(uli.getId());
            if (uis.size() > 0) {
                return false;
            } else {
                if (uli.getLoginFlag() == BsetConsts.LOGIN_FLAG_1) {//并且绑定
                    return false;
                }
            }
            return true;
        }
        return true;
    }
    /**
     * 验证注册手机号是否是营养师账号
     */
    @Override
    public boolean findDietitianByPhone(String phoneNo) {
        List<Dietitian> dietitian = dietitianMapper.findUserLoginInfoByPhone(phoneNo);
        if(dietitian.size()>0){
            return false;
        }
        return true;
    }
    /**
     * 保存个人记录的数据
     */
    @Override
    public UserData userInfoDataWeightSave(int userInfoId, String weight,String date,int userStatus) {
        UserData ud = userDataMapper.findByUserData(userInfoId, date);
        UserData userData = new UserData();
        Date day = null;
        try {
            day = BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int flag = 0;
        if(ud == null){//没有数据，新添
            userData.setWeight(Double.valueOf(weight));
            userData.setUserInfoId(userInfoId);
            userData.setCreateId(userInfoId);
            userData.setCreateTime(new Date());
            userData.setDate(day);
            userData.setUserStatus(userStatus);
            flag = userDataMapper.insertSelective(userData);
        } else {//有数据，修改
            userData = ud;
            userData.setWeight(Double.valueOf(weight));
            userData.setUserStatus(userStatus);
            userData.setUpdateId(userInfoId);
            userData.setUpdateTime(new Date());
            flag = userDataMapper.updateByPrimaryKeySelective(userData);
        }
        if (flag > 0) {
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);//更新数据开始时间
            userInfo.setUpdateFlag(1);
            try {
                if (userInfo.getDataStartDate() == null || (BocosoftUitl.compare2Day(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9), userInfo.getDataStartDate())) < 0) {
                    userInfo.setDataStartDate(day);
                    
                }
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            userInfoMapper.update_user_info(userInfo);
            UserWeightData uwd = userWeightDataMapper.getLastData(userInfoId, date);//查找当前时间之前的最后一条数据
            UserWeightData userWd = userWeightDataMapper.findUserWeightDataByDate(userInfoId, date);//查询当前时间的数据
            int dietDays = 0;
            if (userInfo.getStartDate() != null) {//现在时间-营养师填写的开始时间
                dietDays = BocosoftUitl.getDietDays(day, userInfo.getStartDate());
                if (dietDays < 0) {
                    dietDays = 1;
                }
            }
            if (uwd == null) {//最后一条数据为空
                if (userWd != null) {//修改数据
                    userWd.setWeight(Double.valueOf(weight));
                    userWd.setDietDays(dietDays);
                    userWeightDataMapper.updateUserWeightData(userWd);
                } else {
                    UserWeightData tmpUwd = new UserWeightData();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(day);
                    int year = cal.get(Calendar.YEAR);
                    tmpUwd.setYyyy(year);
                    int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
                    tmpUwd.setWeight(Double.valueOf(weight));
                    tmpUwd.setDeltaWeight(0.0);
                    tmpUwd.setUpDown(0);
                    int weekDay = BocosoftUitl.getWeekDay(day);
                    tmpUwd.setDietDays(dietDays);
                    tmpUwd.setWeekDay(weekDay);
                    tmpUwd.setLostTotalWeight(0.0);
                    tmpUwd.setWeekCount(weekOfYear);
                    tmpUwd.setDate(day);
                    tmpUwd.setUserInfoId(userInfoId);
                    userWeightDataMapper.saveUserWeightData(tmpUwd);
                }
            } else {
                if (userWd != null) {//这个时间的数据已经存储
                    userWd.setWeight(Double.valueOf(weight));
                    Double deltaWeight = (uwd.getWeight() * BsetConsts.WEIGHT_NUMBER - Double.valueOf(weight) * BsetConsts.WEIGHT_NUMBER) / BsetConsts.WEIGHT_NUMBER;
                    userWd.setDeltaWeight(deltaWeight);
                    if (deltaWeight > 0) {//昨天体重大于今天体重 减脂
                        userWd.setUpDown(2);
                    } else if (deltaWeight < 0) {//昨天体重小于今天体重 增长
                        userWd.setUpDown(1);
                    } else {//相等没变化
                        userWd.setUpDown(0);
                    }
                    userWd.setDietDays(dietDays);
                    userWd.setLostTotalWeight((uwd.getLostTotalWeight() * BsetConsts.WEIGHT_NUMBER + deltaWeight * BsetConsts.WEIGHT_NUMBER) / BsetConsts.WEIGHT_NUMBER);
                    userWeightDataMapper.updateUserWeightData(userWd);
                } else {//根据昨天的数据，计算相关的今天数据信息
                    UserWeightData tmpUwd = new UserWeightData();
                    tmpUwd.setUserInfoId(userInfoId);
                    tmpUwd.setWeight(Double.valueOf(weight));
                    Double deltaWeight = (uwd.getWeight() * BsetConsts.WEIGHT_NUMBER - Double.valueOf(weight) * BsetConsts.WEIGHT_NUMBER) / BsetConsts.WEIGHT_NUMBER;
                    tmpUwd.setDeltaWeight(deltaWeight);
                    if (deltaWeight > 0) {//昨天体重大于今天体重 减脂
                        tmpUwd.setUpDown(2);
                    } else if (deltaWeight < 0) {//昨天体重小于今天体重 增长
                        tmpUwd.setUpDown(1);
                    } else {//相等没变化
                        tmpUwd.setUpDown(0);
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(day);
                    int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
                    int year = cal.get(Calendar.YEAR);
                    tmpUwd.setYyyy(year);
                    tmpUwd.setDietDays(dietDays);
                    tmpUwd.setWeekDay(cal.get(Calendar.DAY_OF_WEEK));
                    tmpUwd.setLostTotalWeight((uwd.getLostTotalWeight() * BsetConsts.WEIGHT_NUMBER + deltaWeight * BsetConsts.WEIGHT_NUMBER) / BsetConsts.WEIGHT_NUMBER);
                    tmpUwd.setWeekCount(weekOfYear);
                    tmpUwd.setDate(day);
                    userWeightDataMapper.saveUserWeightData(tmpUwd);
                }
                
            }
            upDataNowDayAfterWeightData(userInfoId, date);
        }
        return userData;
    }
    
    /**
     * 更新当前时间之后的所有数据
     * @param userInfoId
     * @param date
     */
    public void upDataNowDayAfterWeightData (int userInfoId, String date) {
        List<UserWeightData> uwds = new ArrayList<UserWeightData>();
        try {
            uwds = userWeightDataMapper.findUserWeightDatasByDate(userInfoId, date, BocosoftUitl.getDateStr(BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//查看当前时间之后是否有数据
        if (uwds.size() > 1) { //特殊情况没按正常时间填写数据
            for (int i = 1; i < uwds.size(); i++) {
                Double DeltaWeight = (uwds.get(i-1).getWeight()*BsetConsts.WEIGHT_NUMBER - uwds.get(i).getWeight() * BsetConsts.WEIGHT_NUMBER) / BsetConsts.WEIGHT_NUMBER;
                uwds.get(i).setDeltaWeight(DeltaWeight);
                if (DeltaWeight > 0) {//昨天体重大于今天体重 减脂
                    uwds.get(i).setUpDown(2);
                } else if (DeltaWeight < 0) {//昨天体重小于今天体重 增长
                    uwds.get(i).setUpDown(1);
                } else {//相等没变化
                    uwds.get(i).setUpDown(0);
                }
                //uwds.get(i).setDietDays(uwds.get(i-1).getDietDays() + 1);
                uwds.get(i).setLostTotalWeight((uwds.get(i-1).getLostTotalWeight() * BsetConsts.WEIGHT_NUMBER + DeltaWeight * BsetConsts.WEIGHT_NUMBER) / BsetConsts.WEIGHT_NUMBER);
                userWeightDataMapper.updateUserWeightData(uwds.get(i));
            }
        }
    }
    
    
    @Override
    public UserData userInfoDataBreakfastSave(int userInfoId, String breakfast,String date,int userStatus) {
        UserData ud = userDataMapper.findByUserData(userInfoId,date);
        UserData userData = new UserData();
        if(ud == null){//没有数据，新添
            userData.setBreakfast(breakfast);
            userData.setUserInfoId(userInfoId);
            userData.setCreateId(userInfoId);
            userData.setCreateTime(new Date());
            userData.setUserStatus(userStatus);
            Date day = null;
            try {
                day = BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            userData.setDate(day);
            int flag = userDataMapper.insertSelective(userData);
            if (flag > 0) {
                UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);//更新数据开始时间
                if (userInfo.getDataStartDate() == null) {
                    userInfo.setDataStartDate(day);
                    userInfoMapper.update_user_info(userInfo);
                }
                return userData;
            }
        }else{//有数据，修改
            userData = ud;
            userData.setBreakfast(breakfast);
            userData.setUpdateId(userInfoId);
            userData.setUpdateTime(new Date());
            userData.setUserStatus(userStatus);
            int flag = userDataMapper.updateByPrimaryKeySelective(userData);
            if (flag > 0) {
                return userData;
            }
        }
        return userData;
    }
    @Override
    public UserData userInfoDataLunchSave(int userInfoId, String lunch,String date,int userStatus) {
        UserData ud = userDataMapper.findByUserData(userInfoId,date);
        UserData userData = new UserData();
        if(ud == null){//没有数据，新添
            userData.setLunch(lunch);
            userData.setUserInfoId(userInfoId);
            userData.setCreateId(userInfoId);
            userData.setCreateTime(new Date());
            userData.setUserStatus(userStatus);
            Date day = null;
            try {
                day = BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            userData.setDate(day);
            int flag = userDataMapper.insertSelective(userData);
            if (flag > 0) {
                UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);//更新数据开始时间
                if (userInfo.getDataStartDate() == null) {
                    userInfo.setDataStartDate(day);
                    userInfoMapper.update_user_info(userInfo);
                }
                return userData;
            }
        }else{//有数据，修改
            userData=ud;
            userData.setLunch(lunch);
            userData.setUpdateId(userInfoId);
            userData.setUpdateTime(new Date());
            userData.setUserStatus(userStatus);
            int flag = userDataMapper.updateByPrimaryKeySelective(userData);
            if (flag > 0) {
                return userData;
            }
        }
        return userData;
    }
    @Override
    public UserData userInfoDataDinnerSave(int userInfoId, String dinner,String date,int userStatus) {
        UserData ud = userDataMapper.findByUserData(userInfoId,date);
        UserData userData = new UserData();
        Date day = null;
        int flag = 0;
        if(ud == null){//没有数据，新添
            userData.setDinner(dinner);
            userData.setUserInfoId(userInfoId);
            userData.setCreateId(userInfoId);
            userData.setCreateTime(new Date());
            userData.setUserStatus(userStatus);
            try {
                day = BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            userData.setDate(day);
            flag = userDataMapper.insertSelective(userData);
            if (flag > 0) {
                UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);//更新数据开始时间
                if (userInfo.getDataStartDate() == null) {
                    userInfo.setDataStartDate(day);
                    userInfoMapper.update_user_info(userInfo);
                }
                return userData;
            }
        } else {//有数据，修改
            userData = ud;
            userData.setDinner(dinner);
            userData.setUpdateId(userInfoId);
            userData.setUpdateTime(new Date());
            userData.setUserStatus(userStatus);
            flag = userDataMapper.updateByPrimaryKeySelective(userData);
            if (flag > 0) {
                return userData;
            }
        }
        
        return userData;
    }
    @Override
    public UserData userInfoDataExerciseSave(int userInfoId, String exercise, String date,int userStatus) {
        UserData ud = userDataMapper.findByUserData(userInfoId,date);
        UserData userData = new UserData();
        if(ud == null){//没有数据，新添
            userData.setExercise(exercise);
            userData.setUserInfoId(userInfoId);
            userData.setCreateId(userInfoId);
            userData.setCreateTime(new Date());
            userData.setUserStatus(userStatus);
            Date day = null;
            try {
                day = BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            userData.setDate(day);
            int flag = userDataMapper.insertSelective(userData);
            if (flag > 0) {
                UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);//更新数据开始时间
                if (userInfo.getDataStartDate() == null) {
                    userInfo.setDataStartDate(day);
                    userInfoMapper.update_user_info(userInfo);
                }
                return userData;
            }
        }else{//有数据，修改
            userData=ud;
            userData.setExercise(exercise);
            userData.setUpdateId(userInfoId);
            userData.setUpdateTime(new Date());
            userData.setUserStatus(userStatus);
            int flag = userDataMapper.updateByPrimaryKeySelective(userData);
            if (flag > 0) {
                return userData;
            }
        }
        return userData;
    }
    @Override
    public UserData userInfoDataDrankWaterSave(int userInfoId,String drankWater, String date,int userStatus) {
        UserData ud=userDataMapper.findByUserData(userInfoId,date);
        UserData userData = new UserData();
        if(ud == null){//没有数据，新添
            userData.setDrankWater(Integer.valueOf(drankWater));
            userData.setUserInfoId(userInfoId);
            userData.setCreateId(userInfoId);
            userData.setCreateTime(new Date());
            userData.setUserStatus(userStatus);
            Date day = null;
            try {
                day = BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            userData.setDate(day);
            int flag = userDataMapper.insertSelective(userData);
            if (flag > 0) {
                UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);//更新数据开始时间
                if (userInfo.getDataStartDate() == null) {
                    userInfo.setDataStartDate(day);
                    userInfoMapper.update_user_info(userInfo);
                }
                return userData;
            }
        }else{//有数据，修改
            userData=ud;
            userData.setDrankWater(Integer.valueOf(drankWater));
            userData.setUpdateId(userInfoId);
            userData.setUpdateTime(new Date());
            userData.setUserStatus(userStatus);
            int flag = userDataMapper.updateByPrimaryKeySelective(userData);
            if (flag > 0) {
                return userData;
            }
        }
        return userData;
    }
    @Override
    public UserData userInfoDataComfortLevelSave(int userInfoId,String comfortLevel, String date,int userStatus) {
        UserData ud = userDataMapper.findByUserData(userInfoId,date);
        UserData userData=new UserData();
        if(ud == null){//没有数据，新添
            userData.setComfortLevel(Integer.valueOf(comfortLevel));
            userData.setUserInfoId(userInfoId);
            userData.setCreateId(userInfoId);
            userData.setCreateTime(new Date());
            userData.setUserStatus(userStatus);
            Date day = null;
            try {
                day = BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            userData.setDate(day);
            int flag = userDataMapper.insertSelective(userData);
            if (flag > 0) {
                UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);//更新数据开始时间
                if (userInfo.getDataStartDate() == null) {
                    userInfo.setDataStartDate(day);
                    userInfoMapper.update_user_info(userInfo);
                }
                return userData;
            }
        }else{//有数据，修改
            userData=ud;
            userData.setComfortLevel(Integer.valueOf(comfortLevel));
            userData.setUpdateId(userInfoId);
            userData.setUpdateTime(new Date());
            userData.setUserStatus(userStatus);
            int flag = userDataMapper.updateByPrimaryKeySelective(userData);
            if (flag > 0) {
                return userData;
            }
        }
        return userData;
    }
    @Override
    public UserData userInfoDataTestPaperValueSave(int userInfoId,String testPaperValue, String date,int userStatus) {
        UserData ud = userDataMapper.findByUserData(userInfoId,date);
        UserData userData = new UserData();
        if(ud==null){//没有数据，新添
            userData.setTestPaperValue(Integer.valueOf(testPaperValue));
            userData.setUserInfoId(userInfoId);
            userData.setCreateId(userInfoId);
            userData.setUserStatus(userStatus);
            userData.setCreateTime(new Date());
            Date day = null;
            try {
                day = BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            userData.setDate(day);
            int flag = userDataMapper.insertSelective(userData);
            if (flag > 0) {
                UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);//更新数据开始时间
                if (userInfo.getDataStartDate() == null) {
                    userInfo.setDataStartDate(day);
                    userInfoMapper.update_user_info(userInfo);
                }
                return userData;
            }
        }else{//有数据，修改
            userData = ud;
            userData.setTestPaperValue(Integer.valueOf(testPaperValue));
            userData.setUpdateId(userInfoId);
            userData.setUpdateTime(new Date());
            userData.setUserStatus(userStatus);
            int flag = userDataMapper.updateByPrimaryKeySelective(userData);
            if (flag > 0) {
                return userData;
            }
        }
        return userData;
    }
    @Override
    public UserData findByUserData(int userInfoId, String date) {
        UserData ud = userDataMapper.findByUserData(userInfoId,date);
        return ud;
    }
    @Override
    public UserInfo saveUserInfoData(String userLoginType, String userLoginId, String nickname,
            String sex, String age, String weight, String height) {
        UserInfo userInfo = new UserInfo();
//        List<UserInfo> users = userInfoMapper.getUserInfoBythirdPartyLoginId(Integer.parseInt(userLoginId));
//        if (users.size() <= 0) {
            int typeFlag = Integer.parseInt(userLoginType);
            if (typeFlag == BsetConsts.REGISTER_TYPE_0) {
                ThirdPartyLogin tpl = thirdPartyLoginMapper.selectByPrimaryKey(Integer.parseInt(userLoginId));
                userInfo.setFilePath(tpl.getHeadImgUrl());
                userInfo.setLoginFlag(tpl.getLoginFlag());
                userInfo.setThirdPartyLoginId(Integer.parseInt(userLoginId));
            } else {
                userInfo.setLoginFlag(BsetConsts.REGISTER_TYPE_1);
                userInfo.setUserLoginInfoId(Integer.parseInt(userLoginId));
            }
            userInfo.setSex(Integer.parseInt(sex));
            userInfo.setName(nickname);
            try {
                userInfo.setBirthday(BocosoftUitl.stringToDate(age, BsetConsts.DATE_FORMAT_9));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            int tmpAge = BocosoftUitl.getAge(age);
            userInfo.setAge(tmpAge);
            userInfo.setContactWay(0);
            userInfo.setWeight(Double.valueOf(weight));
            userInfo.setHeight(Double.valueOf(height));
            Double idealWeight = BocosoftUitl.getIdealWeightBMI(Double.valueOf(height), Integer.parseInt(sex));
            userInfo.setIdealBodyWeight(idealWeight);
            userInfo.setUserStatus(BsetConsts.USER_STATUS_0);
            userInfo.setTopFlag(BsetConsts.TOP_FLAG_0);
            userInfo.setLookFlag(BsetConsts.LOOK_FLAG_1);
            userInfo.setPhase(1);
            userInfo.setAddFlag(0);
            userInfo.setDayCount(BsetConsts.LOGIN_DAY_COUNT);
            userInfo.setLoginCount(1);
            userInfo.setAgreeFlag(0);
            userInfo.setUpdateFlag(0);
            userInfo.setVitality(BsetConsts.VITALITY_COUNT);
            int flag = userInfoMapper.saveUserInfo(userInfo);
            if (flag > 0) {
                if (typeFlag == BsetConsts.REGISTER_TYPE_0) {
                    return userInfoMapper.getUserInfoBythirdPartyLoginId(Integer.parseInt(userLoginId)).get(0);
                } else {
                    return userInfoMapper.getUserInfoByUserLoginId(Integer.parseInt(userLoginId)).get(0);
                }
            }
//        }
        return null;
    }
    @Override
    public List<UserLoginInfo> phone_login(String phoneNo, String pwd) {
        List<UserLoginInfo> users = userLoginInfoMapper.findUserLoginInfoByPhone(phoneNo);
        if (users.size() > 0) {
            try {
                if (BocosoftUitl.getMd5Str(pwd).equals(users.get(0).getPasswd())) {
                    return users;
                }
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
    
    
    @Override
    public List<UserInfo> getUserInfoByLoginId(Integer id) {
        return userInfoMapper.getUserInfoByUserLoginId(id);
    }
    
    
    @Override
    public UserLoginInfo editPhonePasswdSave(String phoneNo, String passwd) {
        // TODO Auto-generated method stub
        List<UserLoginInfo> users = userLoginInfoMapper.findUserLoginInfoByPhone(phoneNo);
        UserLoginInfo uli = new UserLoginInfo();
        if (users.size() > 0) {
            uli = users.get(0);
            try {
                uli.setPasswd(BocosoftUitl.getMd5Str(passwd));
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            uli.setLoginFlag(1);
            int flag = userLoginInfoMapper.updateByPrimaryKeySelective(uli);
            if (flag > 0) {
                return uli;
            }
        } 
        return uli;
    }
    
    
    @Override
    public UserInfo editUserInfoName(int userInfoId, String userInfoName) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setName(userInfoName);
        userInfoMapper.update_user_info(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo editUserInfoPhoneNo(int userInfoId, String userInfoPhoneNo) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        UserLoginInfo uli = userLoginInfoMapper.getUserLoginInfo(userInfo.getUserLoginInfoId());
        uli.setLoginId(userInfoPhoneNo);
        userLoginInfoMapper.updateByPrimaryKeySelective(uli);
        return userInfo;
    }

    @Override
    public UserInfo editUserInfoBirthday(int userInfoId, String userInfoBirthday) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        try {
            userInfo.setBirthday(BocosoftUitl.stringToDate(userInfoBirthday, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int tmpAge = BocosoftUitl.getAge(userInfoBirthday);
        userInfo.setAge(tmpAge);
        userInfoMapper.update_user_info(userInfo);
        return userInfoMapper.selectByPrimaryKey(userInfoId);
    }

    @Override
    public UserInfo editUserInfoSex(int userInfoId, String userInfoSex) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setSex(Integer.parseInt(userInfoSex));
        Double idealWeight = BocosoftUitl.getIdealWeightBMI(userInfo.getHeight(), Integer.parseInt(userInfoSex));
        userInfo.setIdealBodyWeight(idealWeight);
        userInfoMapper.update_user_info(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo editUserInfoHeight(int userInfoId, String userInfoHeight) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setHeight(Double.valueOf(userInfoHeight));
        Double idealWeight = BocosoftUitl.getIdealWeightBMI(Double.valueOf(userInfoHeight), userInfo.getSex());
        userInfo.setIdealBodyWeight(idealWeight);
        userInfoMapper.update_user_info(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo editUserInfoWeight(int userInfoId, String userInfoWeight) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setWeight(Double.valueOf(userInfoWeight));
        userInfoMapper.update_user_info(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo editUserInfoBloodPressure(int userInfoId, String userInfoBloodPressure) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setBloodPressure(Integer.parseInt(userInfoBloodPressure));
        userInfoMapper.update_user_info(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo editUserInfoBloodFat(int userInfoId, String userInfoBloodFat) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setBloodFat(Integer.parseInt(userInfoBloodFat));
        userInfoMapper.update_user_info(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo editUserInfoBloodSugar(int userInfoId, String userInfoBloodSugar) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setBloodSugar(Integer.parseInt(userInfoBloodSugar));
        userInfoMapper.update_user_info(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo editUserInfoBloodUricAcid(int userInfoId, String userInfoBloodUricAcid) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setBloodUricAcid(Integer.parseInt(userInfoBloodUricAcid));
        userInfoMapper.update_user_info(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo editUserInfoHepaticAdiposeInfiltration(int userInfoId, String userInfoHepaticAdiposeInfiltration) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setHepaticAdiposeInfiltration(Integer.parseInt(userInfoHepaticAdiposeInfiltration));
        userInfoMapper.update_user_info(userInfo);
        return userInfo;
    }

    @Override
    public Dietitian findByDietitian(Integer dietitianId) {
        Dietitian dietitian = dietitianMapper.selectByPrimaryKey(dietitianId);
        return dietitian;
    }

    @Override
    public String getUserLoginInfo(Integer userLoginInfoId) {
        UserLoginInfo userLoginInfo = userLoginInfoMapper.getUserLoginInfo(userLoginInfoId);
        return userLoginInfo.getLoginId();
    }

    @Override
    public UserLoginInfo findUserLoginInfoById(Integer userLoginInfoId) {
        UserLoginInfo uli = userLoginInfoMapper.selectByPrimaryKey(userLoginInfoId);
        return uli;
    }

    @Override
    public List<WeeklyRank> selectAllUserWeekFoodRank(int rankFlag, String date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        List<WeeklyRank> weeklyRanks = weeklyRankMapper.findByWeekFoodRank(rankFlag, weekOfYear);
        return weeklyRanks;
    }

    @Override
    public WeeklyRank findWeekRankByUserInfoId(int userInfoId, String date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        WeeklyRank weekRank = weeklyRankMapper.findWeekRankByUserInfoId(userInfoId, weekOfYear);
        return weekRank;
    }

    @Override
    public int upDateWeekRank(WeeklyRank weeklyRank) {
        int flag = 0;
        if (weeklyRank.getId() == null) {
            flag = weeklyRankMapper.insertSelective(weeklyRank);
        } else {
            flag = weeklyRankMapper.updateWeeklyRank(weeklyRank);
        }
        return flag;
    }

    @Override
    public List<Dietitian> dietitian_phone_login(String phoneNo, String pwd) {
        List<Dietitian> users = dietitianMapper.findUserLoginInfoByPhone(phoneNo);
        if (users.size() > 0) {
            if (users.get(0).getPasswd().length() == 6) {//初始密码未修改
                if (pwd.equals(users.get(0).getPasswd())) {
                    return users;
                }
            } else {
                try {
                    if (BocosoftUitl.getMd5Str(pwd).equals(users.get(0).getPasswd())) {
                        return users;
                    }
                } catch (NoSuchAlgorithmException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
        }
        return null;
    }

    @Override
    public int saveUserData2(int userInfoId, String filePath, String targetFileName,
        String date, int flag, long size) {
        UserData2 ud = new UserData2();
        try {
            ud.setDate(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
            ud.setUploadTime(BocosoftUitl.stringToDate(BocosoftUitl.getDateStr(BsetConsts.DATE_FORMAT_1), BsetConsts.DATE_FORMAT_1));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ud.setFilePath(filePath);
        ud.setFileName(targetFileName);
        ud.setFlag(flag);
        ud.setFileSize((int)size);
        ud.setUserInfoId(userInfoId);
        int tmpFlag = userData2Mapper.insertSelective(ud);
        return tmpFlag;
    }

    @Override
    public int deleteUserData2(int userInfoId, String date, int flag) {
        return userData2Mapper.deleteUserData2(userInfoId, date, flag);
    }

    @Override
    public List<UserData2> findByUserData2(int userInfoId, String date, int flag) {
        // TODO Auto-generated method stub
        return userData2Mapper.findByUserData2(userInfoId, date, flag);
    }

    @Override
    public List<UserInfo> findUserInfoAll() {
        List<UserInfo> userInfoList = userInfoMapper.getAllUser();
        return userInfoList;
    }

    @Override
    public UserLoginInfo getUserLoginInfoByUserInfoId(int userLoginInfoId) {
        // TODO Auto-generated method stub
        return userLoginInfoMapper.getUserLoginInfo(userLoginInfoId);
    }

    @Override
    public UserLoginInfo saveUserLoginInfo(UserLoginInfo uli) {
        userLoginInfoMapper.userLonginInfoSave(uli);
        return userLoginInfoMapper.findByLoginUser(uli.getLoginId());
    }

    @Override
    public int set_user_look_flag(int userInfoId, int lookFlag) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setLookFlag(lookFlag);
        return userInfoMapper.update_user_info(userInfo);
    }

    @Override
    public UserLoginInfo updateUserLoginInfoPhone(UserLoginInfo userLoginInfo) {
        userLoginInfoMapper.updateByPrimaryKey(userLoginInfo);
        return userLoginInfoMapper.findByLoginUser(userLoginInfo.getLoginId());
    }

    @Override
    public WeeklyRank selectFirstWeekRankUser(int rankFlag, String date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        WeeklyRank weekRank = weeklyRankMapper.findFirstWeekRankUser(rankFlag, weekOfYear);
        return weekRank;
    }

    @Override
    public int updateUserLoginInfo(UserLoginInfo uli) {
        return userLoginInfoMapper.updateByPrimaryKey(uli);
    }

    @Override
    public ThirdPartyLogin saveThirdPartyLogin(ThirdPartyLogin tpl) {
        thirdPartyLoginMapper.insertSelective(tpl);
        return thirdPartyLoginMapper.getThirdPartyLogin(tpl.getOpenId());
    }

    @Override
    public ThirdPartyLogin getThirdPartyLogin(String openId) {
        return thirdPartyLoginMapper.getThirdPartyLogin(openId);
    }

    @Override
    public List<UserInfo> getUserInfoBythirdPartyLoginId(Integer thirdPartyLoginId) {
        return userInfoMapper.getUserInfoBythirdPartyLoginId(thirdPartyLoginId);
    }

    @Override
    public Dietitian editDietitianPhonePasswdSave(String phoneNo, String passwd) {
        List<Dietitian> dietitians = dietitianMapper.findUserLoginInfoByPhone(phoneNo);
        Dietitian dietitian = new Dietitian();
        if (dietitians.size() > 0) {
            dietitian = dietitians.get(0);
            try {
                dietitian.setPasswd(BocosoftUitl.getMd5Str(passwd));
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            int flag = dietitianMapper.updateByPrimaryKeySelective(dietitian);
            if (flag > 0) {
                return dietitian;
            }
        } 
        return dietitian;
    }
    
    @Override
    public UserInfo saveUserNijiInfoData(int userInfoId, String phone, String bloodPressure,
            String bloodFat, String bloodSugar, String bloodUricAcid,
            String fld, String contactWay, String account, String buyFlag) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setAccount(account);
        userInfo.setBloodFat(Integer.parseInt(bloodFat));
        userInfo.setBloodPressure(Integer.parseInt(bloodPressure));
        userInfo.setBloodSugar(Integer.parseInt(bloodSugar));
        userInfo.setBloodUricAcid(Integer.parseInt(bloodUricAcid));
        userInfo.setHepaticAdiposeInfiltration(Integer.parseInt(fld));
        userInfo.setBuyFlag(Integer.parseInt(buyFlag));
        userInfo.setContactWay(Integer.parseInt(contactWay));
        userInfo.setAddFlag(1);
        userInfoMapper.update_user_info(userInfo);
        return userInfoMapper.selectByPrimaryKey(userInfoId);
    }

    @Override
    public int updateUserDataLookCommentsFlag(UserData userData) {
        int flag =userDataMapper.updataUserData(userData);
        return flag;
    }

    @Override
    public UserLoginInfo getDataByPhoneNo(String phoneNo) {
        return userLoginInfoMapper.findByLoginUser(phoneNo);
    }

    @Override
    public int deleteWeekRank(Integer userInfoId, String date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        return weeklyRankMapper.deleteWeekRank(userInfoId, weekOfYear);
    }
}
