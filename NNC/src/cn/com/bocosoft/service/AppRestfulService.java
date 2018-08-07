package cn.com.bocosoft.service;

import java.util.List;

import cn.com.bocosoft.model.Dietitian;
import cn.com.bocosoft.model.ThirdPartyLogin;
import cn.com.bocosoft.model.UserData;
import cn.com.bocosoft.model.UserData2;
import cn.com.bocosoft.model.UserInfo;
import cn.com.bocosoft.model.UserLoginInfo;
import cn.com.bocosoft.model.WeeklyRank;

public interface AppRestfulService {

    UserLoginInfo mobileRegistrationSave(String phoneNo, String passwd);

    boolean findUserLoginInfoByPhone(String phoneNo);

    UserData userInfoDataWeightSave(int userInfoId, String weight, String date, int userStatus);

    UserData userInfoDataBreakfastSave(int userInfoId, String breakfast, String date, int userStatus);

    UserData userInfoDataLunchSave(int userInfoId, String lunch, String date, int userStatus);
    
    UserData userInfoDataDinnerSave(int userInfoId, String dinner, String date, int userStatus);
    
    UserData userInfoDataExerciseSave(int userInfoId, String exercise, String date, int userStatus);
    
    UserData userInfoDataDrankWaterSave(int userInfoId, String drankWater, String date, int userStatus);
    
    UserData userInfoDataComfortLevelSave(int userInfoId, String comfortLevel, String date, int userStatus);
    
    UserData userInfoDataTestPaperValueSave(int userInfoId, String testPaperValue, String date, int userStatus);

    UserData findByUserData(int userInfoId, String date);

    UserInfo saveUserInfoData(String userLoginType, String userLoginId, String nickname, String sex,
            String age, String weight, String height);

    List<UserLoginInfo> phone_login(String phoneNo, String pwd);

    List<UserInfo> getUserInfoByLoginId(Integer id);

    UserLoginInfo editPhonePasswdSave(String phoneNo, String passwd);

    UserInfo editUserInfoName(int userInfoId, String userInfoName);

    UserInfo editUserInfoPhoneNo(int userInfoId, String userInfoPhoneNo);

    UserInfo editUserInfoBirthday(int userInfoId, String userInfoBirthday);

    UserInfo editUserInfoSex(int userInfoId, String userInfoSex);

    UserInfo editUserInfoHeight(int userInfoId, String userInfoHeight);

    UserInfo editUserInfoWeight(int userInfoId, String userInfoWeight);

    UserInfo editUserInfoBloodPressure(int userInfoId, String userInfoBloodPressure);

    UserInfo editUserInfoBloodFat(int userInfoId, String userInfoBloodFat);

    UserInfo editUserInfoBloodSugar(int userInfoId, String userInfoBloodSugar);

    UserInfo editUserInfoBloodUricAcid(int userInfoId, String userInfoBloodUricAcid);

    UserInfo editUserInfoHepaticAdiposeInfiltration(int userInfoId, String userInfoHepaticAdiposeInfiltration);

    Dietitian findByDietitian(Integer dietitianId);

    String getUserLoginInfo(Integer userLoginInfoId);

    UserLoginInfo findUserLoginInfoById(Integer userLoginInfoId);

    List<WeeklyRank> selectAllUserWeekFoodRank(int rankFlag, String date);

    WeeklyRank findWeekRankByUserInfoId(int userInfoId, String date);
    
    int upDateWeekRank(WeeklyRank weeklyRank);

    List<Dietitian> dietitian_phone_login(String phoneNo, String pwd);

    int saveUserData2(int userInfoId, String filePath, String targetFileName,
            String date,  int flag, long size);

    int deleteUserData2(int userInfoId, String date, int flag);

    List<UserData2> findByUserData2(int userInfoId, String date, int flag);

    List<UserInfo> findUserInfoAll();

    UserLoginInfo getUserLoginInfoByUserInfoId(int userLoginInfoId);

    UserLoginInfo saveUserLoginInfo(UserLoginInfo uli);

    int set_user_look_flag(int userInfoId, int lookFlag);

    UserLoginInfo updateUserLoginInfoPhone(UserLoginInfo userLoginInfo);

    WeeklyRank selectFirstWeekRankUser(int rankType1, String date);

    int updateUserLoginInfo(UserLoginInfo uli);

    ThirdPartyLogin saveThirdPartyLogin(ThirdPartyLogin tpl);

    ThirdPartyLogin getThirdPartyLogin(String openId);

    List<UserInfo> getUserInfoBythirdPartyLoginId(Integer thirdPartyLoginId);

    boolean findDietitianByPhone(String phoneNo);
    
    Dietitian editDietitianPhonePasswdSave(String phoneNo, String passwd);

    UserInfo saveUserNijiInfoData(int userInfoId, String phone, String bloodPressure,
            String bloodFat, String bloodSugar, String bloodUricAcid,
            String fld, String contactWay, String account, String buyFlag);

    int updateUserDataLookCommentsFlag(UserData userData);

    UserLoginInfo getDataByPhoneNo(String phoneNo);

    int deleteWeekRank(Integer id, String date);
}
