package cn.com.bocosoft.dao;

import java.util.List;

import cn.com.bocosoft.model.SystemUser;

public interface SystemUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemUser record);

    int insertSelective(SystemUser record);

    SystemUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemUser record);

    int updateByPrimaryKey(SystemUser record);
    
    List<SystemUser> get_user_info_login(SystemUser user);

    List<SystemUser> select_users();

    int save_system_user(SystemUser user);

    int system_edit_user_save(SystemUser user);

    List<SystemUser> find_by_loginId(String loginId);

    int update_system_user_save(SystemUser user);
}