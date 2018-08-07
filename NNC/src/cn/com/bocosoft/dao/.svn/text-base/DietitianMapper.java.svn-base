package cn.com.bocosoft.dao;

import java.util.List;

import cn.com.bocosoft.model.Dietitian;

public interface DietitianMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dietitian record);

    int insertSelective(Dietitian record);

    Dietitian selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dietitian record);

    int updateByPrimaryKey(Dietitian record);
    
    List<Dietitian> getDietitians();

    int dietitian_save(Dietitian dietitian);

    List<Dietitian> find_by_dietitians(Dietitian dietitian);

    int updateDietitian(Dietitian dietitian);

    List<Dietitian> findUserLoginInfoByPhone(String phoneNo);
}