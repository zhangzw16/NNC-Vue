package cn.com.bocosoft.background;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.com.bocosoft.common.BocosoftUitl;
import cn.com.bocosoft.common.BsetConsts;
import cn.com.bocosoft.model.UserInfo;
import cn.com.bocosoft.model.UserLoginInfo;
import cn.com.bocosoft.model.UserWeightData;
import cn.com.bocosoft.model.WeeklyRank;
import cn.com.bocosoft.service.AppRestfulService;
import cn.com.bocosoft.service.UserInfoService;

public class WeeklyRankUpdate {
    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(WeeklyRankUpdate.class);
    
    @Resource
    UserInfoService userInfoService;
    
    @Resource
    AppRestfulService appRestfulService;
    
    public void run() {
        LOG.debug("The task of update WeeklyRank started.");

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
            int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
            int year = cal.get(Calendar.YEAR);
            for (UserInfo userInfo : userInfos) {
                if (userInfo.getUserStatus() != BsetConsts.USER_STATUS_1) {
                    appRestfulService.deleteWeekRank(userInfo.getId(), date);
                }
                if (userInfo.getUserStatus() == BsetConsts.USER_STATUS_1) {
                    if (userInfo.getStartDate() != null) {
                        List<UserWeightData> uwds = userInfoService.findByUserWeightData(userInfo.getId(), weekOfYear, year);
                        WeeklyRank wr = appRestfulService.findWeekRankByUserInfoId(userInfo.getId(), date);
                        Double weight = 0.0;
                        if (uwds.size() > 0) {
                            for (UserWeightData uwd : uwds) {
                                weight += uwd.getDeltaWeight() * BsetConsts.WEIGHT_NUMBER;
                            }
                        }
                        if (wr != null) {
                            wr.setName(userInfo.getName());
                            wr.setWeight(weight / BsetConsts.WEIGHT_NUMBER);
                            wr.setPhotoUrl(userInfo.getFilePath());
                            appRestfulService.upDateWeekRank(wr);
                        } else {
                            WeeklyRank weeklyRank = new WeeklyRank();
                            weeklyRank.setName(userInfo.getName());
                            weeklyRank.setWeight(weight / BsetConsts.WEIGHT_NUMBER);
                            weeklyRank.setRankFlag(BsetConsts.RANK_TYPE_1);
                            weeklyRank.setUserInfoId(userInfo.getId());
                            weeklyRank.setWeekCount(weekOfYear);
                            weeklyRank.setPhotoUrl(userInfo.getFilePath());
                            appRestfulService.upDateWeekRank(weeklyRank);
                        }
                    }
                    userInfo.setUpdateFlag(0);
                    userInfoService.updataUserInfo(userInfo);
                }
            }
            
            List<WeeklyRank> wrs = appRestfulService.selectAllUserWeekFoodRank(1, date);//对排行榜数据进行排名
            if (wrs.size() > 0) {
                for (int i = 0; i < wrs.size(); i++) {
                    wrs.get(i).setRankNub(i + 1);
                    appRestfulService.upDateWeekRank(wrs.get(i));
                }
            }
        }
        LOG.debug("The task of update WeeklyRank ended.");
    }
}
