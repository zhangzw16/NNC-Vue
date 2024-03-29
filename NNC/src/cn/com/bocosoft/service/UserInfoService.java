package cn.com.bocosoft.service;

import java.util.Date;
import java.util.List;
import java.util.Calendar;

import org.apache.ibatis.annotations.Param;

import cn.com.bocosoft.model.DietPhaseInfo;
import cn.com.bocosoft.model.Dietitian;
import cn.com.bocosoft.model.UserData;
import cn.com.bocosoft.model.UserData2;
import cn.com.bocosoft.model.UserInfo;
import cn.com.bocosoft.model.UserWeightData;
import cn.com.bocosoft.model.WeeklyRecommend;
import cn.com.bocosoft.model.UserReportData;

public interface UserInfoService {

    Dietitian findByDietitian(int dietitianId);

    List<UserInfo> getUserInfosById(int dietitianId, int userStatus);

    UserInfo findByUserInfo(int userInfoId);

    WeeklyRecommend findByUserInfoWeeklyRecommend(int userInfoId);

    UserInfo updateStartDate(int userInfoId, String startDate);

    UserInfo updateEndDate(int userInfoId, String endDate);

    UserData findByUserData(int userInfoId);

    UserData findUserData(int userDataId);

    WeeklyRecommend findWeeklyRecommend(int userDataId);

    UserData updataUserData(UserData userData);

    WeeklyRecommend findWeeklyRecomendById(WeeklyRecommend wr);

    void saveWr(WeeklyRecommend wr);

    UserData findUserInfoByDate(int userInfoId, String date);

    List<UserWeightData> findByUserWeightData(int userInfoId, int weekOfYear, int year);

    List<UserWeightData> findUserWeightDatasByDate(int userInfoId, String startDate, String endDate);
    
    UserWeightData findUserWeightDataByDate(int userInfoId, String date);

    UserWeightData findLastUserWeightDataByWeek(int userInfoId, int weekOfYear, int year);

    List<UserInfo> getAllUserInfosById();

    // List<UserInfo> getMostActiveUserInfos(int userStatus);

    // List<UserInfo> getMostActiveUserInfos();

    // List<UserInfo> getLeastActiveUserInfos(int userStatus);

    // List<UserInfo> getLeastActiveUserInfos();

    List<Dietitian> findByDietitians();

    int deleteUserDietitian(int userInfoId);

    int editUserDietitian(int userInfoId, int dietitianId);

    List<UserInfo> getUserInfosByUserStatus(int userStatus);
    
    List<UserInfo> getUserInfosByUserDietitianId(int dietitianId);

    int getAllUserInfoLoginCount();

    int updataUserInfo(UserInfo userInfo);

    int saveDietPhaseInfo(DietPhaseInfo dpi);

    DietPhaseInfo findDietPhaseInfo(int userInfoId, int phase);

    List<UserWeightData> findUserWeightDataByDate(String date);

    UserData findByUserLastData(Integer userInfoId);

    UserWeightData findByUserLastWeightData(Integer userInfoId);

    int set_user_top_flag(int dietitianId, int userInfoId, int topFlag);

    int set_user_note(int dietitianId, int userInfoId, String note);

    int set_user_start_date(int dietitianId, int userInfoId, String stringToDate);

    int set_user_end_date(int dietitianId, int userInfoId, Date stringToDate);

    int set_user_comments(int dietitianId, int userInfoId, String date, String comments);

    int set_user_weekly_menu(int dietitianId, int userInfoId, String date, String weeklyMenu);

    int set_user_weekly_sport(int dietitianId, int userInfoId, String date, String weeklySport);

    List<DietPhaseInfo> select_user_histroy_weight_list(int userInfoId);

    List<UserData> findUserDatasByUserInfoId(Integer userInfoId);

    List<UserWeightData> findUserWeightDatasByUserInfoId(Integer userInfoId);

    DietPhaseInfo select_user_histroy_weight_Data(int dietPhaseInfoId);

    List<UserInfo> getUserByUserLoginInfoId(int userLoginInfoId);

    int updateDietPhaseInfo(DietPhaseInfo dietPhaseInfo);

    List<UserData2> getUserData2(int userInfoId, String date, int flag);

    UserInfo updateUserNote(int userInfoId, String note);

    List<DietPhaseInfo> findAlldietPhaseInfo(Integer id, int phase);

    UserData2 findUserData2(int userData2Id);

    int deleteUserData2(int userData2Id);

    UserWeightData findFirstUserWeightDataByDate(int userInfoId, String date);
    
    UserWeightData findLastUserWeightDataByDate(int userInfoId, String date);

    UserWeightData getFirstData(int userInfoId, String date);

    UserWeightData getLastData(int userInfoId, String date);

    List<UserInfo> getUserInfosById(int dietitianId, int userStatus, String message);

    List<UserInfo> getUserInfosByIdOrdered(int dietitianId, int userStatus, String message, int order);

    List<UserInfo> getUserInfosByUserStatus(int userStatus, String message);

    List<UserInfo> getUserInfosByUserStatusOrdered(int userStatus, String message, int order);

    List<UserInfo> getUserInfosByUserDietitianId(int dietitianId, String message);

    List<UserInfo> getUserInfosByUserDietitianIdOrdered(int dietitianId, String message, int order);

    List<UserInfo> getAllUserInfosById(String message);

    List<UserInfo> getAllUserInfosByIdOrdered(String message, int order);

    UserReportData getReportData(Calendar cal1, Calendar cal2);
}
