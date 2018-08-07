package cn.com.bocosoft.background;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.bocosoft.model.UserInfo;
import cn.com.bocosoft.service.UserInfoService;

public class UserVitalityUpdate {
    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(UserVitalityUpdate.class);
    
    @Resource
    UserInfoService userInfoService;
    
    public void run() throws ParseException {
        LOG.info("The task of update UserVitality started.");
        
        List<UserInfo> userInfos = userInfoService.getAllUserInfosById();
        if (userInfos.size() > 0) {
            for (UserInfo userInfo : userInfos) {
                if (userInfo.getVitality() != null && userInfo.getVitality() > 0) {
                    userInfo.setVitality(userInfo.getVitality() - 1);
                    userInfoService.updataUserInfo(userInfo);
                }
            }
        }
        
        LOG.info("The task of update UserVitality ended.");
    }
}
