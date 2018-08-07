package cn.com.bocosoft.background;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.bocosoft.common.BocosoftUitl;
import cn.com.bocosoft.common.BsetConsts;
import cn.com.bocosoft.model.DietPhaseInfo;
import cn.com.bocosoft.model.Dietitian;
import cn.com.bocosoft.model.UserData;
import cn.com.bocosoft.model.UserInfo;
import cn.com.bocosoft.model.UserLoginInfo;
import cn.com.bocosoft.model.UserWeightData;
import cn.com.bocosoft.service.AppRestfulService;
import cn.com.bocosoft.service.DietitianService;
import cn.com.bocosoft.service.UserInfoService;

public class UserInfoStatusUpdate {
    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(UserInfoStatusUpdate.class);
    
    @Resource
    UserInfoService userInfoService;
    
    @Resource
    AppRestfulService appRestfulService;
    
    @Resource
    DietitianService dietitianService;
    
    public void run() throws ParseException {
        LOG.info("The task of update userStatus started.");
        
        List<UserInfo> userInfos = userInfoService.getAllUserInfosById();
        if (userInfos.size() > 0) {
            String parentPath = getClass().getResource("/").getFile().toString();
            
            String filePath = parentPath + BsetConsts.TIME_FILEPATH;
            String date = BocosoftUitl.readTxtFile(filePath);
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                LOG.info("The date format is error.");
            }
            for (UserInfo userInfo : userInfos) {
                if (userInfo.getDayCount() > 0) {//登录期限30天每天减一
                    userInfo.setDayCount(userInfo.getDayCount() - 1);
                    userInfoService.updataUserInfo(userInfo);
                }
                if (userInfo.getEndDate() != null) {
                    int dietDay = BocosoftUitl.compare2Day(cal.getTime(), userInfo.getEndDate());
                    if (dietDay == 0) {//减脂结束时间  用户状态的修改
                        userInfo.setUserStatus(BsetConsts.USER_STATUS_2);
                        userInfoService.updataUserInfo(userInfo);
                    }
                    DietPhaseInfo dietPhaseInfo = userInfoService.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase());
                    if (dietDay == 15) {//完成期开始时间  用户状态的修改
                        if (dietPhaseInfo != null) {
                            //创建第几期详细数据
                            dietPhaseInfo.setTransitionEndDate(BocosoftUitl.getDateforNub(userInfo.getEndDate(), 15));
                            dietPhaseInfo.setTransitionStartDate(BocosoftUitl.getDateforNub(userInfo.getEndDate(), 1));
                            userInfoService.updateDietPhaseInfo(dietPhaseInfo);
                        }
                        //跟新客户的信息，减重开始时间，客户的状态， 减重结束时间
                        userInfo.setUserStatus(BsetConsts.USER_STATUS_3);
                        userInfo.setStartDate(null);
                        userInfo.setEndDate(null);
                        userInfo.setAddFlag(0);
                        
                        userInfo.setPhase(userInfo.getPhase() + 1);
                        userInfoService.updataUserInfo(userInfo);
                    }
                    if (dietPhaseInfo.getTransitionEndDate() != null) {//每周的第一天判断提醒
                        int endDietDay = BocosoftUitl.compare2Day(cal.getTime(), dietPhaseInfo.getTransitionEndDate());
                        if (endDietDay == 1 || endDietDay == 8 || endDietDay == 15 || endDietDay == 23 || endDietDay == 30
                                || endDietDay == 37 || endDietDay == 44 || endDietDay == 51) {//1-8周没周提醒一次
                            dietPhaseInfo.setAlarmFlag(1);
                            userInfoService.updateDietPhaseInfo(dietPhaseInfo);
                        } else if (endDietDay == 58 || endDietDay == 72 || endDietDay == 86 || endDietDay == 100) {
                            if (dietPhaseInfo.getAlarmFlag() == 0) {//8~16周每2周提醒一次
                                dietPhaseInfo.setAlarmFlag(2);
                                userInfoService.updateDietPhaseInfo(dietPhaseInfo);
                            }
                        } else if (endDietDay == 114 || endDietDay == 142) {//17~24周没4周提醒一次
                            if (dietPhaseInfo.getAlarmFlag() == 0) {
                                dietPhaseInfo.setAlarmFlag(3);
                                userInfoService.updateDietPhaseInfo(dietPhaseInfo);
                            }
                        }
                    }
                }
                
            }
        }
        LOG.info("The task of update userStatus ended.");
    }
}
