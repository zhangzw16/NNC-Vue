package cn.com.bocosoft.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.bocosoft.model.DietitianUserInfo;
import cn.com.bocosoft.model.DietitianUserInfoKey;

public interface DietitianUserInfoMapper {
    int deleteByPrimaryKey(DietitianUserInfoKey key);

    int insert(DietitianUserInfo record);

    int insertSelective(DietitianUserInfo record);

    DietitianUserInfo selectByPrimaryKey(DietitianUserInfoKey key);

    int updateByPrimaryKeySelective(DietitianUserInfo record);

    int updateByPrimaryKey(DietitianUserInfo record);

    List<DietitianUserInfo> findBydietitianUserInfo(@Param("userInfoId")int userInfoId, @Param("dietitianId")Integer dietitianId);

}