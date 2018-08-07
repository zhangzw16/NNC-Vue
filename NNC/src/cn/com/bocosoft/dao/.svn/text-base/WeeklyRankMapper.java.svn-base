package cn.com.bocosoft.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.bocosoft.model.WeeklyRank;

public interface WeeklyRankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WeeklyRank record);

    int insertSelective(WeeklyRank record);

    WeeklyRank selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeeklyRank record);

    int updateByPrimaryKey(WeeklyRank record);

    List<WeeklyRank> findByWeekFoodRank(@Param("rankFlag")int rankFlag, @Param("weekOfYear")int weekOfYear);

    WeeklyRank findWeekRankByUserInfoId(@Param("userInfoId")int userInfoId, @Param("weekOfYear")int weekOfYear);

    int updateWeeklyRank(WeeklyRank weeklyRank);

    WeeklyRank findFirstWeekRankUser(@Param("rankFlag")int rankFlag, @Param("weekOfYear")int weekOfYear);

    int deleteWeekRank(@Param("userInfoId")int userInfoId, @Param("weekOfYear")int weekOfYear);
}