package cn.com.bocosoft.dao;

import org.apache.ibatis.annotations.Param;

import cn.com.bocosoft.model.WeeklyRecommend;

public interface WeeklyRecommendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WeeklyRecommend record);

    int insertSelective(WeeklyRecommend record);

    WeeklyRecommend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeeklyRecommend record);

    int updateByPrimaryKey(WeeklyRecommend record);

    WeeklyRecommend findByUserInfoWeeklyRecommend(@Param("userInfoId")int userInfoId, @Param("weekOfYear")int weekOfYear, @Param("year")int year);

    WeeklyRecommend findWeeklyRecommend(int userDataId);

    int upDateWeeklyRecommend(WeeklyRecommend wr);

    int save_weekly_recommend(WeeklyRecommend wr);
}