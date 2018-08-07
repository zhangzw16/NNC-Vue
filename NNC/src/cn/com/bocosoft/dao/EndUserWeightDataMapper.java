package cn.com.bocosoft.dao;

import cn.com.bocosoft.model.EndUserWeightData;

public interface EndUserWeightDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EndUserWeightData record);

    int insertSelective(EndUserWeightData record);

    EndUserWeightData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EndUserWeightData record);

    int updateByPrimaryKey(EndUserWeightData record);
}