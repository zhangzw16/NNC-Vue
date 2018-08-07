package cn.com.bocosoft.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.net.BCodec;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.bocosoft.common.BocosoftUitl;
import cn.com.bocosoft.common.BsetConsts;
import cn.com.bocosoft.dao.SystemUserMapper;
import cn.com.bocosoft.model.SystemUser;
import cn.com.bocosoft.service.SystemUserService;

@Service
public class SystemUserServiceImp implements SystemUserService{
    private static final Logger log = Logger.getLogger(SystemUserServiceImp.class);
    @Resource
    private SystemUserMapper systemUserMapper;
    
    @Override
    public List<SystemUser> userLogin(SystemUser user) {
        List<SystemUser> users = systemUserMapper.get_user_info_login(user);
        return users;
    }

    @Override
    public List<SystemUser> getUsers() {
        // TODO Auto-generated method stub
        return systemUserMapper.select_users();
    }

    @Override
    public int system_user_save(SystemUser user) {
        List<SystemUser> users = systemUserMapper.find_by_loginId(user.getLoginId());
        if (users.size() > 0) {
            return 0;
        } else {
            user.setPasswd(BocosoftUitl.makeRandomPassword(6));
            user.setDelFlag(BsetConsts.DEL_FLAG_0);
            user.setCreateId(BocosoftUitl.getSystemUserId());
            return systemUserMapper.save_system_user(user);
        }
        
    }

    @Override
    public SystemUser getUserById(int userId) {
        return systemUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int system_edit_user_save(SystemUser user) {
        try {
            user.setPasswd(BocosoftUitl.getMd5Str(user.getPasswd()));
            user.setUpdateId(BocosoftUitl.getSystemUserId());
        } catch (NoSuchAlgorithmException e) {
            log.debug("The getMd5 error!");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            log.debug("The getMd5 error!");
            e.printStackTrace();
        }
        return systemUserMapper.system_edit_user_save(user);
    }

    @Override
    public int edit_system_user_save(SystemUser user) {
        List<SystemUser> users = systemUserMapper.find_by_loginId(user.getLoginId());
        if (users.size() > 0) {
            if (user.getId().equals(users.get(0).getId())) {
                user.setUpdateId(BocosoftUitl.getSystemUserId());
                return systemUserMapper.update_system_user_save(user);
            }
        } else {
            user.setUpdateId(BocosoftUitl.getSystemUserId());
            return systemUserMapper.update_system_user_save(user);
        }
        return 0;
    }

    @Override
    public String system_user_reset_passwd(int userId) {
        SystemUser user = systemUserMapper.selectByPrimaryKey(userId);
        user.setPasswd(BocosoftUitl.makeRandomPassword(BsetConsts.RANDOM_AUTHENTICATION_CODE_LEN));
        user.setUpdateId(BocosoftUitl.getSystemUserId());
        systemUserMapper.update_system_user_save(user);
        return "true";
    }

    @Override
    public String system_user_delete(int userId) {
        SystemUser user = systemUserMapper.selectByPrimaryKey(userId);
        user.setDelFlag(BsetConsts.DEL_FLAG_1);
        user.setUpdateId(BocosoftUitl.getSystemUserId());
        systemUserMapper.update_system_user_save(user);
        return "true";
    }

}
