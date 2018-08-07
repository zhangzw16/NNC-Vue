package cn.com.bocosoft.dao;

import cn.com.bocosoft.model.ThirdPartyLogin;

public interface ThirdPartyLoginMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ThirdPartyLogin record);

    int insertSelective(ThirdPartyLogin record);

    ThirdPartyLogin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThirdPartyLogin record);

    int updateByPrimaryKey(ThirdPartyLogin record);

    ThirdPartyLogin getThirdPartyLogin(String openId);
}