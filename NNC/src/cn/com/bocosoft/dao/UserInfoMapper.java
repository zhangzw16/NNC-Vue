package cn.com.bocosoft.dao;

import java.util.Calendar;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.bocosoft.model.UserInfo;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    int get_users(Integer dietitianId);

    List<UserInfo> get_users_by_dietitianId(@Param("dietitianId") int dietitianId, @Param("userStatus")int userStatus);

    int get_all_users(Integer dietitianId);

    int update_user_info(UserInfo userInfo);

    List<UserInfo> getAllUser();

    List<UserInfo> getUserInfosByUserStatus(int userStatus);

    List<UserInfo> getUserInfosByUserDietitianId(int dietitianId);

    int saveUserInfo(UserInfo userInfo);

    List<UserInfo> getUserInfoByUserLoginId(int userLoginId);

    List<UserInfo> getUserInfoBythirdPartyLoginId(Integer thirdPartyLoginId);

    Calendar getUserInfoByThirdPartyId(int parseInt);

    List<UserInfo> getUserInfoListByUserStatus(int userStatus);

    List<UserInfo> getAllUserList();

    List<UserInfo> get_users_by_dietitianId_and_message(@Param("dietitianId") int dietitianId, @Param("userStatus")int userStatus, 
            @Param("message")String message);

    List<UserInfo> getUserInfoListByUserStatusAndMessage(@Param("userStatus")int userStatus, 
            @Param("message")String message);

    List<UserInfo> getUserInfosByUserDietitianIdAndMessage(@Param("dietitianId")int dietitianId,
            @Param("message")String message);

    List<UserInfo> getAllUserByMessage(@Param("message")String message);

    List<UserInfo> getMostActiveUserListByUserStatus(int userStatus);

    List<UserInfo> getMostActiveUserList();

    List<UserInfo> getLeastActiveUserListByUserStatus(int userStatus);

    List<UserInfo> getLeastActiveUserList();
}