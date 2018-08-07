package cn.com.bocosoft.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.bocosoft.model.DietPhaseInfo;

public interface DietPhaseInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietPhaseInfo record);

    int insertSelective(DietPhaseInfo record);

    DietPhaseInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietPhaseInfo record);

    int updateByPrimaryKey(DietPhaseInfo record);

    DietPhaseInfo findDietPhaseInfo(@Param("userInfoId")int userInfoId, @Param("phase")int phase);

    List<DietPhaseInfo> findDietPhaseInfoList(@Param("userInfoId")int userInfoId);

    List<DietPhaseInfo> findAlldietPhaseInfo(@Param("userInfoId")int userInfoId, @Param("phase")int phase);
}