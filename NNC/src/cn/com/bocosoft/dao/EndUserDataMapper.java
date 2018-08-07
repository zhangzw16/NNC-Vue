package cn.com.bocosoft.dao;

import cn.com.bocosoft.model.EndUserData;

public interface EndUserDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EndUserData record);

    int insertSelective(EndUserData record);

    EndUserData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EndUserData record);

    int updateByPrimaryKey(EndUserData record);
}