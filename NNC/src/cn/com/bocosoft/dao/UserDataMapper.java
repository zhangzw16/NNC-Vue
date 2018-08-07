package cn.com.bocosoft.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.bocosoft.model.UserData;

public interface UserDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserData record);

    int insertSelective(UserData record);

    UserData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserData record);

    int updateByPrimaryKey(UserData record);

    int userInfoDataSave(UserData userData);

    UserData findByUserData(@Param("userInfoId")int userInfoId, @Param("date")String date);

    int updataUserData(UserData userData);

    UserData findByUserLastData(@Param("userInfoId")Integer userInfoId);

    List<UserData> findUserDatasByUserInfoId(@Param("userInfoId")Integer userInfoId);

    List<UserData> findByUserDataGreaterThanStartDate(@Param("userInfoId")int userInfoId, @Param("date")String date);
}