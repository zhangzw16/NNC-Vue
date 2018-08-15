package cn.com.bocosoft.service.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.bocosoft.common.BocosoftUitl;
import cn.com.bocosoft.common.BsetConsts;
import cn.com.bocosoft.dao.DietPhaseInfoMapper;
import cn.com.bocosoft.dao.DietitianMapper;
import cn.com.bocosoft.dao.DietitianUserInfoMapper;
import cn.com.bocosoft.dao.UserData2Mapper;
import cn.com.bocosoft.dao.UserDataMapper;
import cn.com.bocosoft.dao.UserInfoMapper;
import cn.com.bocosoft.dao.UserLoginInfoMapper;
import cn.com.bocosoft.dao.UserWeightDataMapper;
import cn.com.bocosoft.dao.WeeklyRecommendMapper;
import cn.com.bocosoft.model.DietPhaseInfo;
import cn.com.bocosoft.model.Dietitian;
import cn.com.bocosoft.model.DietitianUserInfo;
import cn.com.bocosoft.model.UserData;
import cn.com.bocosoft.model.UserData2;
import cn.com.bocosoft.model.UserInfo;
import cn.com.bocosoft.model.UserWeightData;
import cn.com.bocosoft.model.WeeklyRecommend;
import cn.com.bocosoft.service.UserInfoService;

@Service
public class UserInfoServiceImp implements UserInfoService{

    @Resource
    private DietitianMapper dietitianMapper;
    
    @Resource
    private UserInfoMapper userInfoMapper;
    
    @Resource
    private WeeklyRecommendMapper weeklyRecommendMapper;
    
    @Resource
    private UserDataMapper userDataMapper;
    
    @Resource
    private UserData2Mapper userData2Mapper;
    
    @Resource
    private UserWeightDataMapper userWeightDataMapper;
    
    @Resource
    private DietitianUserInfoMapper dietitianUserInfoMapper;
    
    @Resource
    private UserLoginInfoMapper userLoginInfoMapper;
    
    @Resource
    private DietPhaseInfoMapper dietPhaseInfoMapper;
    
    @Override
    public Dietitian findByDietitian(int dietitianId) {
        Dietitian dietitian = dietitianMapper.selectByPrimaryKey(dietitianId);
        return dietitian;
    }

    @Override
    public List<UserInfo> getUserInfosById(int dietitianId, int userStatus) {
        return userInfoMapper.get_users_by_dietitianId(dietitianId, userStatus);
    }

    /**
     * 查找用户的信息
     */
    @Override
    public UserInfo findByUserInfo(int userInfoId) {
        return userInfoMapper.selectByPrimaryKey(userInfoId);
    }

    /**
     * 客户的本周运动和饮食指导
     */
    @Override
    public WeeklyRecommend findByUserInfoWeeklyRecommend(int userInfoId) {
        Calendar cal = Calendar.getInstance();
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        return weeklyRecommendMapper.findByUserInfoWeeklyRecommend(userInfoId, weekOfYear, year);
    }

    @Override
    public UserInfo updateStartDate(int userInfoId, String startDate) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        try {
            userInfo.setStartDate(BocosoftUitl.stringToDate(startDate, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        userInfo.setUserStatus(BsetConsts.USER_STATUS_1);
        userInfo.setAddFlag(1);
        userInfo.setUpdateFlag(1);
        userInfo.setUpdateId(BocosoftUitl.getSystemUserId());
        userInfoMapper.update_user_info(userInfo);
        try {
            List<UserWeightData> uwds = userWeightDataMapper.findUserWeightDatasByDate(userInfoId, startDate, BocosoftUitl.getDateStr(BsetConsts.DATE_FORMAT_9));
            if (uwds.size() > 0) {
                for (int i = 0; i < uwds.size(); i++) {
                    uwds.get(i).setDietDays(i + 1);
                    userWeightDataMapper.updateUserWeightData(uwds.get(i));
                }
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DietPhaseInfo dietPhaseInfo = dietPhaseInfoMapper.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase());
        if (dietPhaseInfo == null) {
            Dietitian diet = dietitianMapper.selectByPrimaryKey(userInfo.getDietitianId());
            DietPhaseInfo dpi = new DietPhaseInfo();//创建第几期详细数据
            dpi.setStartWeight(userInfo.getWeight());
            dpi.setDietitianName(diet.getName());
            dpi.setStartDate(userInfo.getStartDate());
            dpi.setPhaseCount(userInfo.getPhase());
            dpi.setUserInfoId(userInfoId);
            dietPhaseInfoMapper.insertSelective(dpi);
        } else {
            dietPhaseInfo.setStartWeight(userInfo.getWeight());
            dietPhaseInfo.setStartDate(userInfo.getStartDate());
            dietPhaseInfoMapper.updateByPrimaryKeySelective(dietPhaseInfo);
        }
        //遍历userData表，修改开始时间后数据的userstatus字段
        List<UserData>  udList = userDataMapper.findByUserDataGreaterThanStartDate(userInfoId,startDate);
        if(udList.size()>0){
            for (UserData ud : udList){
                ud.setUserStatus(BsetConsts.USER_STATUS_1);
                userDataMapper.updataUserData(ud);
            }
        }
        return userInfo;
    }

    @Override
    public UserInfo updateEndDate(int userInfoId, String endDate) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        try {
            userInfo.setEndDate(BocosoftUitl.stringToDate(endDate, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        long tmpDay = 0;
        int dieDay = 0;
        try {
            tmpDay = BocosoftUitl.compare2Time(cal.getTime(), BocosoftUitl.stringToDate(endDate, BsetConsts.DATE_FORMAT_9));
            dieDay = BocosoftUitl.compare2Day(cal.getTime(), BocosoftUitl.stringToDate(endDate, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        if (tmpDay > 0) {//填写时间小于等于当前时间改变客户状态
          userInfo.setUserStatus(BsetConsts.USER_STATUS_2);
        }
        userInfo.setUpdateId(BocosoftUitl.getSystemUserId());
        userInfoMapper.update_user_info(userInfo);
        //第几期详细数据的更新
        DietPhaseInfo dietPhaseInfo = dietPhaseInfoMapper.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase());
        UserWeightData uwd = userWeightDataMapper.findLastUserWeightDataByDate(userInfo.getId(), endDate);
        if (uwd != null) {
            dietPhaseInfo.setEndWeight(uwd.getWeight());
        }
        dietPhaseInfo.setEndDate(userInfo.getEndDate());
        if (dieDay > 15) {//结束时间小于当前时间15天建立第几期详细数据的过渡期时间， 修改用户的状态
            if (dietPhaseInfo != null) {
                //创建第几期详细数据
                try {
                    dietPhaseInfo.setTransitionEndDate(BocosoftUitl.getDateforNub(BocosoftUitl.stringToDate(endDate, BsetConsts.DATE_FORMAT_9), 15));
                    dietPhaseInfo.setTransitionStartDate(BocosoftUitl.getDateforNub(BocosoftUitl.stringToDate(endDate, BsetConsts.DATE_FORMAT_9), 1));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                dietPhaseInfoMapper.updateByPrimaryKeySelective(dietPhaseInfo);
            }
            //跟新客户的信息，减重开始时间，客户的状态， 减重结束时间
            userInfo.setUserStatus(BsetConsts.USER_STATUS_3);
            userInfo.setStartDate(null);
            userInfo.setEndDate(null);
            userInfo.setPhase(userInfo.getPhase() + 1);
        }
        dietPhaseInfoMapper.updateByPrimaryKeySelective(dietPhaseInfo);
        return userInfo;
    }

    @Override
    public UserData findByUserData(int userInfoId) {
        Calendar cal = Calendar.getInstance();
        String tmpDate = BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9);
        return userDataMapper.findByUserData(userInfoId, tmpDate);
    }

    @Override
    public UserData findUserData(int userDataId) {
        return userDataMapper.selectByPrimaryKey(userDataId);
    }

    @Override
    public WeeklyRecommend findWeeklyRecommend(int userDataId) {
        return weeklyRecommendMapper.findWeeklyRecommend(userDataId);
    }

    @Override
    public UserData updataUserData(UserData userData) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userData.getUserInfoId());
        userData.setLookCommentsFlag(1);
        userData.setUpdateId(BocosoftUitl.getSystemUserId());
        if (userData.getId() == null) {
            userData.setUserStatus(userInfo.getUserStatus());
            userDataMapper.userInfoDataSave(userData);
            return userData;
        }
        if (userData.getUserStatus() == null) {
            userData.setUserStatus(userInfo.getUserStatus());
        }
        int flag = userDataMapper.updataUserData(userData);
        if (flag == 1) {
            return userData;
        }
        return userData;
    }

    @Override
    public WeeklyRecommend findWeeklyRecomendById(WeeklyRecommend wr) {
        return weeklyRecommendMapper.findByUserInfoWeeklyRecommend(wr.getUserInfoId(), wr.getWeekCount(), wr.getYyyy());
    }

    @Override
    public WeeklyRecommend saveWr(WeeklyRecommend wr) {
        if (wr.getId() != null) {
            wr.setUpdateId(BocosoftUitl.getSystemUserId());
            weeklyRecommendMapper.upDateWeeklyRecommend(wr);
            return weeklyRecommendMapper.findByUserInfoWeeklyRecommend(wr.getUserInfoId(), wr.getWeekCount(), wr.getYyyy());
        } else {
            weeklyRecommendMapper.save_weekly_recommend(wr);
            return weeklyRecommendMapper.findByUserInfoWeeklyRecommend(wr.getUserInfoId(), wr.getWeekCount(), wr.getYyyy());
        }
        
    }

    @Override
    public UserData findUserInfoByDate(int userInfoId, String date) {
        return userDataMapper.findByUserData(userInfoId, date);
    }

    @Override
    public List<UserWeightData> findByUserWeightData(int userInfoId, int weekOfYear, int year) {
        List<UserWeightData> uwds = userWeightDataMapper.findUserWeightDataByWeekCount(userInfoId, weekOfYear, year);
        return uwds;
    }

    @Override
    public List<UserWeightData> findUserWeightDatasByDate(int userInfoId, String startDate, String endDate) {
        return userWeightDataMapper.findUserWeightDatasByDate(userInfoId, startDate, endDate);
    }

    @Override
    public UserWeightData findLastUserWeightDataByWeek(int userInfoId, int weekOfYear, int year) {
        return userWeightDataMapper.findLastUserWeightDataByWeekCount(userInfoId, weekOfYear, year);
    }

    @Override
    public UserWeightData findUserWeightDataByDate(int userInfoId, String date) {
        return userWeightDataMapper.findUserWeightDataByDate(userInfoId, date);
    }

    @Override
    public List<UserInfo> getAllUserInfosById() {
        return userInfoMapper.getAllUserList();
    }

    @Override
    public List<UserInfo> getMostActiveUserInfos(int userStatus) {
        return userInfoMapper.getMostActiveUserListByUserStatus(userStatus);
    }
    
    @Override
    public List<UserInfo> getMostActiveUserInfos() {
        return userInfoMapper.getMostActiveUserList();
    }

    @Override
    public List<UserInfo> getLeastActiveUserInfos(int userStatus) {
        return userInfoMapper.getLeastActiveUserListByUserStatus(userStatus);
    }

    @Override
    public List<UserInfo> getLeastActiveUserInfos() {
        return userInfoMapper.getLeastActiveUserList();
    }


    @Override
    public List<Dietitian> findByDietitians() {
        return dietitianMapper.getDietitians();
    }

    @Override
    public int deleteUserDietitian(int userInfoId) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);//解除营养师
        if (userInfo.getDietitianId() == null) {
            return 1;
        }
        int userInfoDietitianId = userInfo.getDietitianId();
        userInfo.setDietitianId(null);
        userInfo.setUpdateId(BocosoftUitl.getSystemUserId());
        int flag = userInfoMapper.update_user_info(userInfo);
        if (flag > 0) {
            List<DietitianUserInfo> dietitianUserInfos = dietitianUserInfoMapper.findBydietitianUserInfo(userInfoId, userInfoDietitianId);
            try {
                dietitianUserInfos.get(0).setDateTo(BocosoftUitl.stringToDate(BocosoftUitl.getDateStr(BsetConsts.DATE_FORMAT_9), BsetConsts.DATE_FORMAT_9));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return dietitianUserInfoMapper.updateByPrimaryKey(dietitianUserInfos.get(0));
        }
        return flag;
    }

    @Override
    public int editUserDietitian(int userInfoId, int dietitianId) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);//添加、修改营养师
        if (userInfo.getDietitianId() == null) {
            userInfo.setDietitianId(dietitianId);
            userInfo.setUserStatus(BsetConsts.USER_STATUS_1);
            userInfo.setUpdateId(BocosoftUitl.getSystemUserId());
            int flag = userInfoMapper.update_user_info(userInfo);
            if (flag > 0) {
                DietitianUserInfo dietitianUserInfo = new DietitianUserInfo();
                dietitianUserInfo.setDietitianId(dietitianId);
                dietitianUserInfo.setUserInfoId(userInfoId);
                dietitianUserInfo.setCreateId(BocosoftUitl.getSystemUserId());
                try {
                    dietitianUserInfo.setDateFrom(BocosoftUitl.stringToDate(BocosoftUitl.getDateStr(BsetConsts.DATE_FORMAT_9), BsetConsts.DATE_FORMAT_9));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return dietitianUserInfoMapper.insertSelective(dietitianUserInfo);
            }
        }
        int userInfoDietitianId = userInfo.getDietitianId();
        if (!userInfo.getDietitianId().equals(dietitianId)) {//营养师没有修改
            userInfo.setDietitianId(dietitianId);
            userInfo.setUpdateId(BocosoftUitl.getSystemUserId());
            int flag = userInfoMapper.update_user_info(userInfo);
            if (flag > 0) {
                List<DietitianUserInfo> dietitianUserInfos = dietitianUserInfoMapper.findBydietitianUserInfo(userInfoId, userInfoDietitianId);
                if (dietitianUserInfos.size() > 0) {//修改营养师和客户关系
                    try {
                        dietitianUserInfos.get(0).setDateTo(BocosoftUitl.stringToDate(BocosoftUitl.getDateStr(BsetConsts.DATE_FORMAT_9), BsetConsts.DATE_FORMAT_9));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    dietitianUserInfoMapper.updateByPrimaryKeySelective(dietitianUserInfos.get(0));
                    DietitianUserInfo dietitianUserInfo = new DietitianUserInfo();
                    dietitianUserInfo.setDietitianId(dietitianId);
                    dietitianUserInfo.setUserInfoId(userInfoId);
                    dietitianUserInfo.setCreateId(BocosoftUitl.getSystemUserId());
                    try {
                        dietitianUserInfo.setDateFrom(BocosoftUitl.stringToDate(BocosoftUitl.getDateStr(BsetConsts.DATE_FORMAT_9), BsetConsts.DATE_FORMAT_9));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    dietitianUserInfoMapper.insertSelective(dietitianUserInfo);
                } else {//新建营养师和客户关系
                    DietitianUserInfo dietitianUserInfo = new DietitianUserInfo();
                    dietitianUserInfo.setDietitianId(dietitianId);
                    dietitianUserInfo.setUserInfoId(userInfoId);
                    dietitianUserInfo.setCreateId(BocosoftUitl.getSystemUserId());
                    try {
                        dietitianUserInfo.setDateFrom(BocosoftUitl.stringToDate(BocosoftUitl.getDateStr(BsetConsts.DATE_FORMAT_9), BsetConsts.DATE_FORMAT_9));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return dietitianUserInfoMapper.insertSelective(dietitianUserInfo);
                }
            }
            return flag;
        }
        return 1;
    }

    @Override
    public List<UserInfo> getUserInfosByUserStatus(int userStatus) {
        return userInfoMapper.getUserInfoListByUserStatus(userStatus);
    }

    @Override
    public List<UserInfo> getUserInfosByUserDietitianId(int dietitianId) {
        return userInfoMapper.getUserInfosByUserDietitianId(dietitianId);
    }

    @Override
    public int getAllUserInfoLoginCount() {
        return userLoginInfoMapper.getAllUserInfoLoginCount();
    }

    @Override
    public int updataUserInfo(UserInfo userInfo) {
        // TODO Auto-generated method stub
        return userInfoMapper.update_user_info(userInfo);
    }

    @Override
    public int saveDietPhaseInfo(DietPhaseInfo dpi) {
        return dietPhaseInfoMapper.insertSelective(dpi);
    }

    @Override
    public DietPhaseInfo findDietPhaseInfo(int userInfoId, int phase) {
        // TODO Auto-generated method stub
        return dietPhaseInfoMapper.findDietPhaseInfo(userInfoId, phase);
    }

    @Override
    public List<UserWeightData> findUserWeightDataByDate(String date) {
        // TODO Auto-generated method stub
        return userWeightDataMapper.findUserWeightDataListByDate(date);
    }

    @Override
    public UserData findByUserLastData(Integer userInfoId) {
        return userDataMapper.findByUserLastData(userInfoId);
    }

    @Override
    public UserWeightData findByUserLastWeightData(Integer userInfoId) {
        return userWeightDataMapper.findByUserLastWeightData(userInfoId);
    }

    @Override
    public int set_user_top_flag(int dietitianId, int userInfoId, int topFlag) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setTopFlag(topFlag);
        userInfo.setUpdateId(dietitianId);
        return userInfoMapper.update_user_info(userInfo);
    }

    @Override
    public int set_user_note(int dietitianId, int userInfoId, String note) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setNote(note);
        userInfo.setUpdateId(dietitianId);
        return userInfoMapper.update_user_info(userInfo);
    }

    @Override
    public int set_user_start_date(int dietitianId, int userInfoId, String startDate) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        try {
            userInfo.setStartDate(BocosoftUitl.stringToDate(startDate, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        userInfo.setUserStatus(BsetConsts.USER_STATUS_1);
        userInfo.setUpdateFlag(1);
        userInfo.setUpdateId(dietitianId);
        userInfo.setAddFlag(1);
        //针对先有数据，在分配营养师的修改开始减脂时间之后的数据
        try {
            List<UserWeightData> uwds = userWeightDataMapper.findUserWeightDatasByDate(userInfoId, startDate, BocosoftUitl.getDateStr(BsetConsts.DATE_FORMAT_9));
            if (uwds.size() > 0) {
                for (int i = 0; i < uwds.size(); i++) {
                    uwds.get(i).setDietDays(i + 1);
                    userWeightDataMapper.updateUserWeightData(uwds.get(i));
                }
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //遍历userData表，修改开始时间后数据的userstatus字段
        List<UserData>  udList = userDataMapper.findByUserDataGreaterThanStartDate(userInfoId,startDate);
        if(udList.size()>0){
            for (UserData ud : udList){
                ud.setUserStatus(BsetConsts.USER_STATUS_1);
                userDataMapper.updataUserData(ud);
            }
        }
        DietPhaseInfo dietPhaseInfo = dietPhaseInfoMapper.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase());
        if (dietPhaseInfo == null) {
            Dietitian diet = dietitianMapper.selectByPrimaryKey(userInfo.getDietitianId());
            DietPhaseInfo dpi = new DietPhaseInfo();//创建第几期详细数据
            dpi.setStartWeight(userInfo.getWeight());
            dpi.setDietitianName(diet.getName());
            dpi.setStartDate(userInfo.getStartDate());
            dpi.setPhaseCount(userInfo.getPhase());
            dpi.setUserInfoId(userInfoId);
            dietPhaseInfoMapper.insertSelective(dpi);
        } else {
            dietPhaseInfo.setStartWeight(userInfo.getWeight());
            dietPhaseInfo.setStartDate(userInfo.getStartDate());
            dietPhaseInfoMapper.updateByPrimaryKeySelective(dietPhaseInfo);
        }
        return userInfoMapper.update_user_info(userInfo);
    }

    @Override
    public int set_user_end_date(int dietitianId, int userInfoId, Date endDate) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setEndDate(endDate);
        Calendar cal = Calendar.getInstance();
        long tmpDay = BocosoftUitl.compare2Time(cal.getTime(), endDate);
        int dieDay = BocosoftUitl.compare2Day(cal.getTime(), endDate);
        if (tmpDay > 0) {//填写时间小于等于当前时间改变客户状态
          userInfo.setUserStatus(BsetConsts.USER_STATUS_2);
        }
        userInfo.setUpdateId(dietitianId);
        //第几期详细数据的更新
        DietPhaseInfo dietPhaseInfo = dietPhaseInfoMapper.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase());
        UserWeightData uwd = userWeightDataMapper.findLastUserWeightDataByDate(userInfo.getId(), BocosoftUitl.dateToString(endDate, BsetConsts.DATE_FORMAT_9));
        if (uwd != null) {
            dietPhaseInfo.setEndWeight(uwd.getWeight());
        }
        dietPhaseInfo.setEndDate(endDate);
        dietPhaseInfoMapper.updateByPrimaryKeySelective(dietPhaseInfo);
        if (dieDay > 15) {//结束时间小于当前时间15天建立第几期详细数据的过渡期时间， 修改用户的状态
            if (dietPhaseInfo != null) {
                //创建第几期详细数据
                try {
                    dietPhaseInfo.setTransitionEndDate(BocosoftUitl.getDateforNub(endDate, 15));
                    dietPhaseInfo.setTransitionStartDate(BocosoftUitl.getDateforNub(endDate, 1));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                dietPhaseInfoMapper.updateByPrimaryKeySelective(dietPhaseInfo);
            }
            //跟新客户的信息，减重开始时间，客户的状态， 减重结束时间
            userInfo.setUserStatus(BsetConsts.USER_STATUS_3);
            userInfo.setStartDate(null);
            userInfo.setEndDate(null);
            userInfo.setPhase(userInfo.getPhase() + 1);
        }
        return userInfoMapper.update_user_info(userInfo);
    }

    @Override
    public int set_user_comments(int dietitianId, int userInfoId, String date, String comments) {
        UserData userData = userDataMapper.findByUserData(userInfoId, date);
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        if (userData == null) {
            UserData ud = new UserData();
            ud.setUserInfoId(userInfoId);
            ud.setUserStatus(userInfo.getUserStatus());
            try {
                ud.setDate(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ud.setComments(comments);
            ud.setLookCommentsFlag(1);
            ud.setCreateId(dietitianId);
            ud.setCreateTime(new Date());
            return userDataMapper.insertSelective(ud);
        }
        if (userData.getUserStatus() == null) {
            userData.setUserStatus(userInfo.getUserStatus());
        }
        userData.setLookCommentsFlag(1);
        userData.setComments(comments);
        userData.setUpdateId(dietitianId);
        return userDataMapper.updataUserData(userData);
    }

    @Override
    public int set_user_weekly_menu(int dietitianId, int userInfoId, String date, String weeklyMenu) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        WeeklyRecommend wr = weeklyRecommendMapper.findByUserInfoWeeklyRecommend(userInfoId, weekOfYear, year);
        if (wr == null) {
            WeeklyRecommend weeklyRecommend = new WeeklyRecommend();
            weeklyRecommend.setMenu(weeklyMenu);
            weeklyRecommend.setUserInfoId(userInfoId);
            weeklyRecommend.setWeekCount(weekOfYear);
            weeklyRecommend.setYyyy(year);
            return weeklyRecommendMapper.save_weekly_recommend(weeklyRecommend);
        } else {
            wr.setMenu(weeklyMenu);
            wr.setUpdateId(dietitianId);
            return weeklyRecommendMapper.upDateWeeklyRecommend(wr);
        }
    }

    @Override
    public int set_user_weekly_sport(int dietitianId, int userInfoId, String date, String weeklySport) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        WeeklyRecommend wr = weeklyRecommendMapper.findByUserInfoWeeklyRecommend(userInfoId, weekOfYear, year);
        if (wr == null) {
            WeeklyRecommend weeklyRecommend = new WeeklyRecommend();
            weeklyRecommend.setSport(weeklySport);
            weeklyRecommend.setUserInfoId(userInfoId);
            weeklyRecommend.setWeekCount(weekOfYear);
            weeklyRecommend.setYyyy(year);
            return weeklyRecommendMapper.save_weekly_recommend(weeklyRecommend);
        } else {
            wr.setSport(weeklySport);
            wr.setUpdateId(dietitianId);
            return weeklyRecommendMapper.upDateWeeklyRecommend(wr);
        }
    }

    @Override
    public List<DietPhaseInfo> select_user_histroy_weight_list(int userInfoId) {
        List<DietPhaseInfo> dpis = dietPhaseInfoMapper.findDietPhaseInfoList(userInfoId);
        return dpis;
    }

    @Override
    public List<UserData> findUserDatasByUserInfoId(Integer userInfoId) {
        return userDataMapper.findUserDatasByUserInfoId(userInfoId);
    }

    @Override
    public List<UserWeightData> findUserWeightDatasByUserInfoId(Integer userInfoId) {
        return userWeightDataMapper.findUserWeightDatasByUserInfoId(userInfoId);
    }

    @Override
    public DietPhaseInfo select_user_histroy_weight_Data(int dietPhaseInfoId) {
        return dietPhaseInfoMapper.selectByPrimaryKey(dietPhaseInfoId);
    }

    @Override
    public List<UserInfo> getUserByUserLoginInfoId(int userLoginInfoId) {
        return userInfoMapper.getUserInfoByUserLoginId(userLoginInfoId);
    }

    @Override
    public int updateDietPhaseInfo(DietPhaseInfo dietPhaseInfo) {
        return dietPhaseInfoMapper.updateByPrimaryKeySelective(dietPhaseInfo);
    }

    @Override
    public List<UserData2> getUserData2(int userInfoId, String date, int flag) {
        return userData2Mapper.findByUserData2(userInfoId, date, flag);
    }

    @Override
    public UserInfo updateUserNote(int userInfoId, String note) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfo.setNote(note);
        userInfoMapper.update_user_info(userInfo);
        return userInfoMapper.selectByPrimaryKey(userInfoId);
    }

    @Override
    public List<DietPhaseInfo> findAlldietPhaseInfo(Integer userInfoId, int phase) {
        return dietPhaseInfoMapper.findAlldietPhaseInfo(userInfoId, phase);
    }

    @Override
    public UserData2 findUserData2(int userData2Id) {
        return userData2Mapper.selectByPrimaryKey(userData2Id);
    }

    @Override
    public int deleteUserData2(int userData2Id) {
        return userData2Mapper.deleteByPrimaryKey(userData2Id);
    }

    @Override
    public UserWeightData findFirstUserWeightDataByDate(int userInfoId, String date) {
        return userWeightDataMapper.findFirstUserWeightDataByDate(userInfoId, date);
    }

    @Override
    public UserWeightData findLastUserWeightDataByDate(int userInfoId, String date) {
        return userWeightDataMapper.findLastUserWeightDataByDate(userInfoId, date);
    }

    @Override
    public UserWeightData getFirstData(int userInfoId, String date) {
        return userWeightDataMapper.getFirstData(userInfoId, date);
    }

    @Override
    public UserWeightData getLastData(int userInfoId, String date) {
        return userWeightDataMapper.getLastData(userInfoId, date);
    }

    @Override
    public List<UserInfo> getUserInfosById(int dietitianId, int userStatus,
            String message) {
        if (message != null && !message.equals("")) {
            return userInfoMapper.get_users_by_dietitianId_and_message(dietitianId, userStatus, message);
        }
        return userInfoMapper.get_users_by_dietitianId(dietitianId, userStatus);
    }

    @Override
    public List<UserInfo> getUserInfosByUserStatus(int userStatus,
            String message) {
        if (message != null && !message.equals("")) {
            return userInfoMapper.getUserInfoListByUserStatusAndMessage(userStatus, message);
        }
        return userInfoMapper.getUserInfoListByUserStatus(userStatus);
    }

    @Override
    public List<UserInfo> getUserInfosByUserDietitianId(int dietitianId,
            String message) {
        if (message != null && !message.equals("")) {
            return userInfoMapper.getUserInfosByUserDietitianIdAndMessage(dietitianId, message);
        }
        return userInfoMapper.getUserInfosByUserDietitianId(dietitianId);
    }

    @Override
    public List<UserInfo> getAllUserInfosById(String message) {
        if (message != null && !message.equals("")) {
            return userInfoMapper.getAllUserByMessage(message);
        }
        return userInfoMapper.getAllUserList();
    }
}
