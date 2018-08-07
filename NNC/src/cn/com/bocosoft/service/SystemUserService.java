/**
 * 
 */
package cn.com.bocosoft.service;

import java.util.List;

import com.github.pagehelper.PageHelper;

import cn.com.bocosoft.model.SystemUser;

/**
 * @author chenwl
 *
 */
public interface SystemUserService {

    List<SystemUser> userLogin(SystemUser user);

    List<SystemUser> getUsers();

    int system_user_save(SystemUser user);

    int system_edit_user_save(SystemUser user);

    SystemUser getUserById(int userId);

    int edit_system_user_save(SystemUser user);

    String system_user_reset_passwd(int userId);

    String system_user_delete(int userId);

}
