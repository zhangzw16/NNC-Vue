package cn.com.bocosoft.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.bocosoft.model.UserData;
import cn.com.bocosoft.model.UserWeightData;

public interface UserWeightDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserWeightData record);

    int insertSelective(UserWeightData record);

    UserWeightData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserWeightData record);

    int updateByPrimaryKey(UserWeightData record);

    List<UserWeightData> getUserDietDays(Integer userInfoId);

    UserWeightData getLastData(@Param("userInfoId")int userInfoId, @Param("date")String date);

    List<UserWeightData> findUserWeightDatasByDate(@Param("userInfoId")int userInfoId, @Param("startDate")String startDate, @Param("endDate")String endDate);

    int saveUserWeightData(UserWeightData uwd);

    int updateUserWeightData(UserWeightData userWeightData);

    List<UserWeightData> findUserWeightDataByWeekCount(@Param("userInfoId")int userInfoId, @Param("weekOfYear")int weekOfYear, @Param("year")int year);

    UserWeightData findLastUserWeightDataByWeekCount(@Param("userInfoId")int userInfoId, @Param("weekOfYear")int weekOfYear, @Param("year")int year);

    UserWeightData findUserWeightDataByDate(@Param("userInfoId")int userInfoId, @Param("date")String date);

    List<UserWeightData> getAllDatas(int userInfoId);

    List<UserWeightData> findUserWeightDataListByDate(@Param("date")String date);

    UserWeightData findByUserLastWeightData(@Param("userInfoId")Integer userInfoId);

    List<UserWeightData> findUserWeightDatasByUserInfoId(@Param("userInfoId")Integer userInfoId);

    UserWeightData findLastUserWeightDataByDate(@Param("userInfoId")int userInfoId, @Param("date")String date);

    UserWeightData findFirstUserWeightDataByDate(@Param("userInfoId")int userInfoId, @Param("date")String date);

    UserWeightData getFirstData(@Param("userInfoId")int userInfoId, @Param("date")String date);

}