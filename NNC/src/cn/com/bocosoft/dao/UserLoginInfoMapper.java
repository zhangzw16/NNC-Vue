package cn.com.bocosoft.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.bocosoft.model.UserLoginInfo;

public interface UserLoginInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLoginInfo record);

    int insertSelective(UserLoginInfo record);

    UserLoginInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLoginInfo record);

    int updateByPrimaryKey(UserLoginInfo record);

    int userLonginInfoSave(UserLoginInfo uli);

    UserLoginInfo findByLoginUser(String loginId);

    List<UserLoginInfo> findUserLoginInfoByPhone(String phoneNo);

    UserLoginInfo getUserLoginInfo(Integer userLoginInfoId);

    int getAllUserInfoLoginCount();

    List<UserLoginInfo> getLoginUserByLoginIdAndPwd(@Param("phoneNo")String phoneNo, @Param("passwd")String passwd);

}