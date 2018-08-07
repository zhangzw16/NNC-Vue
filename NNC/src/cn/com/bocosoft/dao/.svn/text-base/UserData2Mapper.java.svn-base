package cn.com.bocosoft.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.bocosoft.model.UserData2;

public interface UserData2Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserData2 record);

    int insertSelective(UserData2 record);

    UserData2 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserData2 record);

    int updateByPrimaryKey(UserData2 record);

    int deleteUserData2(@Param("userInfoId")int userInfoId, @Param("date")String date, @Param("flag")int flag);

    List<UserData2> findByUserData2(@Param("userInfoId")int userInfoId, @Param("date")String date, @Param("flag")int flag);

    List<UserData2> getUserData2(@Param("userInfoId")int userInfoId, @Param("date")String date);
}