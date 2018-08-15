package cn.com.bocosoft.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.com.bocosoft.common.BocosoftUitl;
import cn.com.bocosoft.common.BsetConsts;
import cn.com.bocosoft.common.JSONResult;
import cn.com.bocosoft.common.SpringBeanUtils;
import cn.com.bocosoft.common.TableDataBean;
import cn.com.bocosoft.dao.DietPhaseInfoMapper;
import cn.com.bocosoft.dao.DietitianMapper;
import cn.com.bocosoft.dao.UserInfoMapper;
import cn.com.bocosoft.dao.UserLoginInfoMapper;
import cn.com.bocosoft.model.DietPhaseInfo;
import cn.com.bocosoft.model.Dietitian;
import cn.com.bocosoft.model.UserData;
import cn.com.bocosoft.model.UserData2;
import cn.com.bocosoft.model.UserInfo;
import cn.com.bocosoft.model.UserLoginInfo;
import cn.com.bocosoft.model.UserWeightData;
import cn.com.bocosoft.model.WeeklyRecommend;
import cn.com.bocosoft.service.UserInfoService;

@Controller
@RequestMapping("user_Info")
public class UserInfoController {
    @Resource
    UserInfoService userInfoService;
    
    private JSONResult json;

    public JSONResult getJson() {
        return json;
    }

    public void setJson(JSONResult json) {
        this.json = json;
    }
    
    /**
     * 营养师管理下的客户
     * @param request
     * @return
     */
    @RequestMapping(value = "/user_info_on_dietitian_manange", method = RequestMethod.POST)
    public String user_info_on_dietitian_manange(HttpServletRequest request) {
        int dietitianId = Integer.parseInt(request.getParameter("dietitianId"));
        request.setAttribute("dietitianId", dietitianId);
        return "userInfo/userInfoManage";
    }
    
    /**
     * 取得客户的营养师
     * @param request
     * @return
     */
    @RequestMapping(value = "/get_user_dietitian", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_user_dietitian(HttpServletRequest request) {
        int dietitianId = Integer.parseInt(request.getParameter("dietitianId"));
        DietitianMapper dietitianMapper = (DietitianMapper) SpringBeanUtils.getBean("dietitianMapper");
        Dietitian dietitian = dietitianMapper.selectByPrimaryKey(dietitianId);
        String dietitianName = dietitian.getName();
        return json = new JSONResult(dietitianName, "成功", true);
    }
    
    /**
     * 取得营养师管理下的客户列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/user_info_page_on_dietitian", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult user_inf_page_on_dietitian(HttpServletRequest request) {
        int currentPage = Integer.parseInt(request.getParameter("page"));
        int userStatus = Integer.parseInt(request.getParameter("userStatus"));
        int dietitianId = Integer.parseInt(request.getParameter("dietitianId"));
        PageHelper.startPage(currentPage, BsetConsts.PER_PAGE_SIZE);
        Dietitian dietitian = userInfoService.findByDietitian(dietitianId);
        List<UserInfo> userInfos = userInfoService.getUserInfosById(dietitianId, userStatus);
        PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfos);
//        request.setAttribute("max_page",pageInfo.getPages());
//        request.setAttribute("current_page",pageInfo.getPageNum());
//        request.setAttribute("cause",userInfos);
//        request.setAttribute("dietitian", dietitian);
//        request.setAttribute("userStatus", userStatus);
        return json = new JSONResult(pageInfo, "成功", true);
//        return "userInfo/userInfoOnDietitianList";
    }
    
    /**
     * 获取用户的减重日
     * @param request
     * @return
     */
    @RequestMapping(value = "/user_info_diet_day", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult user_info_diet_day(HttpServletRequest request) {
    	int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfoMapper userInfoMapper = (UserInfoMapper) SpringBeanUtils.getBean("userInfoMapper");
        DietPhaseInfoMapper dietPhaseInfoMapper = (DietPhaseInfoMapper) SpringBeanUtils.getBean("dietPhaseInfoMapper");
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
        Calendar cal = Calendar.getInstance();
        int dietDay = 0;
        if (userInfo.getUserStatus() == BsetConsts.USER_STATUS_1) {
        	if(userInfo.getStartDate() != null)
        		dietDay = BocosoftUitl.compare2Day(cal.getTime(), userInfo.getStartDate());
//            return "减重期第"+dietDay+"天";
        } else if (userInfo.getUserStatus() == BsetConsts.USER_STATUS_2) {
        	if(userInfo.getEndDate() != null)
        		dietDay = BocosoftUitl.compare2Day(cal.getTime(), userInfo.getEndDate());
//            return "过渡期第"+dietDay+"天";
        } else if (userInfo.getUserStatus() == BsetConsts.USER_STATUS_3) {
            DietPhaseInfo dpi = dietPhaseInfoMapper.findDietPhaseInfo(userInfoId, userInfo.getPhase() -1);
            if(dpi.getTransitionEndDate() != null)
            	dietDay = BocosoftUitl.compare2Day(cal.getTime(), dpi.getTransitionEndDate());
//            return "完成期第"+dietDay+"天";
        }

//        setWriter("diet_days");
        return json = new JSONResult(String.valueOf(dietDay));
    }
    
    /**
     * 客户管理
     * @param request
     * @return
     */
    @RequestMapping(value = "/user_info_manage", method = RequestMethod.POST)
    public String user_info_manange(HttpServletRequest request) {
        request.setAttribute("dietitianId", "");
        return "userInfo/userInfoManage";
    }
    
    /**
     * 查询客户管理
     * @param request
     * @return
     */
    @RequestMapping(value = "/select_user_info", method = RequestMethod.POST)
    public String select_user_info(HttpServletRequest request) {
        List<Dietitian> dietitians = userInfoService.findByDietitians();
        Dietitian dietitian = new Dietitian();
        dietitian.setName("无");
        dietitians.add(0, dietitian);
        request.setAttribute("dietitian", dietitians);
        List<UserInfo> count = userInfoService.getAllUserInfosById();
        request.setAttribute("user_count", count.size());
        return "userInfo/userInfoList";
    }
    
    /**
     * 取得客户列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/user_info_page", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult user_info_page(HttpServletRequest request) {
        int currentPage = Integer.parseInt(request.getParameter("page"));
        String userStatus = request.getParameter("userStatus");
        String dietitianId = request.getParameter("dietitianId");
        String message = request.getParameter("message");
        PageHelper.startPage(currentPage, BsetConsts.PER_PAGE_SIZE);
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        if ((userStatus != null && !userStatus.equals("")) && (dietitianId != null && !dietitianId.equals(""))) {
            userInfos = userInfoService.getUserInfosById(Integer.parseInt(dietitianId), Integer.parseInt(userStatus), message);
        } else if ((userStatus != null && !userStatus.equals("")) && (dietitianId == null || dietitianId.equals(""))) {
            userInfos = userInfoService.getUserInfosByUserStatus(Integer.parseInt(userStatus), message);
        } else if ((userStatus == null || userStatus.equals("")) && (dietitianId != null && !dietitianId.equals(""))) {
            userInfos = userInfoService.getUserInfosByUserDietitianId(Integer.parseInt(dietitianId), message);
        } else {
            userInfos = userInfoService.getAllUserInfosById(message);
        }
        PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfos);
//        request.setAttribute("max_page",pageInfo.getPages());
//        request.setAttribute("current_page",pageInfo.getPageNum());
//        request.setAttribute("user_status",userStatus);
//        request.setAttribute("dietitian_Id",dietitianId);
//        request.setAttribute("cause",userInfos);
//        request.setAttribute("causeSize",pageInfo.getTotal());
        List<Dietitian> dietitians = userInfoService.findByDietitians();
        Dietitian dietitian = new Dietitian();
        dietitian.setName("无");
        dietitians.add(0, dietitian);
        request.setAttribute("dietitian", dietitians);
//        return "userInfo/userInfoListTable";
        return json = new JSONResult(pageInfo, "成功", true);
    }

    /**
     * 取得更换营养师列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/change_user_dietitian", method = RequestMethod.POST)
    public String change_user_dietitian(HttpServletRequest request) {
        String dietitianId = request.getParameter("dietitianId");
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        int currentPage = Integer.parseInt(request.getParameter("current_page"));
        List<Dietitian> dietitians = userInfoService.findByDietitians();
        Dietitian dietitian = new Dietitian();
        dietitian.setName("无");
        dietitians.add(0, dietitian);
        request.setAttribute("dietitian", dietitians);
        request.setAttribute("dietitian_id", dietitianId);
        request.setAttribute("user_info_id", userInfoId);
        request.setAttribute("current_page", currentPage);
        return "userInfo/changeUserDietitian";
    }
    
    /**
     * 取得更换营养师保存
     * @param request
     * @return
     */
    @RequestMapping(value = "/change_user_dietitian_save", method = RequestMethod.POST)
    public String change_user_dietitian_save(HttpServletRequest request) {
        String dietitianId = request.getParameter("dietitianId");
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        if (dietitianId == null || dietitianId.equals("")) {//解除营养师
            userInfoService.deleteUserDietitian(userInfoId);
        } else {//更改营养师
            userInfoService.editUserDietitian(userInfoId, Integer.parseInt(dietitianId));
        }
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        PageHelper.startPage(currentPage, BsetConsts.PER_PAGE_SIZE);
        List<UserInfo> userInfos = userInfoService.getAllUserInfosById();
        PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfos);
        request.setAttribute("max_page",pageInfo.getPages());
        request.setAttribute("current_page",pageInfo.getPageNum());
        request.setAttribute("cause",userInfos);
        return "userInfo/userInfoListTable";
    }
    
    /**
     * 修改登录状态
     * @param request
     * @return
     */
    @RequestMapping(value = "/change_user_info_agree", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult change_user_info_agree(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        if (userInfo.getAgreeFlag() == 1) {
            userInfo.setAgreeFlag(0);
        } else {
            userInfo.setAgreeFlag(1);
        }
        userInfoService.updataUserInfo(userInfo);
//        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
//        PageHelper.startPage(currentPage, BsetConsts.PER_PAGE_SIZE);
//        List<UserInfo> userInfos = userInfoService.getAllUserInfosById();
//        PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfos);
//        request.setAttribute("max_page",pageInfo.getPages());
//        request.setAttribute("current_page",pageInfo.getPageNum());
//        request.setAttribute("cause",userInfos);
//        return "userInfo/userInfoListTable";
        return json = new JSONResult(1, "成功", true);
    }
    
    /**
     * 取得客户的历史信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/show_user_history_info", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult show_user_history_info(HttpServletRequest request) {
    	int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
    	//历史数据的查询
        List<DietPhaseInfo> dietPhaseInfos = userInfoService.findAlldietPhaseInfo(userInfo.getId(), userInfo.getPhase());
//        request.setAttribute("dietPhaseInfos", dietPhaseInfos);
    	return json = new JSONResult(dietPhaseInfos);
    }
    
    /**
    * 取得营养师管理下的客户详细信息 
    * @param request
    * @return
    */
    @RequestMapping(value = "/show_user_info", method = RequestMethod.POST)
    public String show_user_info(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        UserData userData = userInfoService.findByUserData(userInfoId);
        WeeklyRecommend wr = userInfoService.findByUserInfoWeeklyRecommend(userInfoId);
        request.setAttribute("user_info", userInfo);
        request.setAttribute("user_data", userData);
        request.setAttribute("weekly_recommend", wr);
        Calendar cal = Calendar.getInstance();
        request.setAttribute("chose_date", BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9));
        List<UserData2> breakfast = userInfoService.getUserData2(userInfoId, BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9), 1);
        List<UserData2> lunch = userInfoService.getUserData2(userInfoId, BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9), 2);
        List<UserData2> dinner = userInfoService.getUserData2(userInfoId, BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9), 3);
        request.setAttribute("breakfastPhotos", breakfast);
        request.setAttribute("lunchPhotos", lunch);
        request.setAttribute("dinnerPhotos", dinner);
        //历史数据的查询
        List<DietPhaseInfo> dietPhaseInfos = userInfoService.findAlldietPhaseInfo(userInfo.getId(), userInfo.getPhase());
        request.setAttribute("dietPhaseInfos", dietPhaseInfos);
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.WEEK_OF_MONTH, -1);
        cal2.set(Calendar.DAY_OF_WEEK, 7);
        List<UserWeightData> uwds = userInfoService.findByUserWeightData(userInfoId, weekOfYear, year);
        UserWeightData uwd = userInfoService.findUserWeightDataByDate(userInfoId, BocosoftUitl.dateToString(cal2.getTime(), BsetConsts.DATE_FORMAT_9));//取得上周最后一条数据
        List<String> sevenData = new ArrayList<String>();
        List<String> idealBodyWeightSevenData = new ArrayList<String>();
        
        if (uwds.size() > 0 || uwd != null) {
            sevenData = BocosoftUitl.createSevenDayDate(uwd, uwds);//创建8天图形数据
            request.setAttribute("week_data", sevenData);
            idealBodyWeightSevenData = BocosoftUitl.createidealBodyWeightDate(userInfo.getIdealBodyWeight(), BsetConsts.DAY_SIZE);//创建8天理想体重数据数据
            request.setAttribute("ideal_body_weight_seven", idealBodyWeightSevenData);
            List<String> maxAndMin = BocosoftUitl.getListsMinAndMax(userInfo.getIdealBodyWeight(), uwds);
            request.setAttribute("week_data_max", maxAndMin.get(0));
            request.setAttribute("week_data_min", maxAndMin.get(1));
        } else {
            sevenData.add("0");
            idealBodyWeightSevenData.add("0");
            request.setAttribute("ideal_body_weight_seven", idealBodyWeightSevenData);
            request.setAttribute("week_data", sevenData);
            request.setAttribute("week_data_max", 100);
            request.setAttribute("week_data_min", 0);
        }
        
        Map<String, TableDataBean> tableData = new LinkedHashMap<String, TableDataBean>();//创建8天表格数据
        List SevenDayTableDate = BocosoftUitl.dateToWeek(cal.getTime());
        TableDataBean tdb = new TableDataBean();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        if (uwd != null) {
            tdb.setDate(sdf.format(SevenDayTableDate.get(0)));
            tdb.setDietWeight(Math.abs(uwd.getDeltaWeight()));
            tdb.setWeight(uwd.getWeight());
            tdb.setFlag(0);
            tableData.put("周六 ", tdb);
        } else {
            tdb.setDate(sdf.format(SevenDayTableDate.get(0)));
            tableData.put("周六 ", tdb);
        }
        tableData = BocosoftUitl.createSevenDayTableDate(uwds, SevenDayTableDate);
        request.setAttribute("week_table_data", tableData);
        List<String> yAxisData = new ArrayList<String>();
        List<String> xAxisData = new ArrayList<String>();
        List<String> idealBodyWeightAllData = new ArrayList<String>();
        List<UserWeightData> userwds = new ArrayList<UserWeightData>();
        int DietDays = 0;
        if (userInfo.getStartDate() != null) {//参加尼基计划  显示总曲线
            if (userInfo.getPhase() > 1) {//参加过尼基计划
                DietPhaseInfo dietPhaseInfo = userInfoService.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase() - 1);
                userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                        ,BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
                DietDays = BocosoftUitl.getDietDays(dietPhaseInfo.getEndDate(), dietPhaseInfo.getStartDate());
            } else {//只取得尼基计划内体重总体
                if (userInfo.getEndDate() != null) {//取得到尼基计划结束的时间
                    userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(userInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                            ,BocosoftUitl.dateToString(userInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
                    DietDays = BocosoftUitl.getDietDays(userInfo.getEndDate(), userInfo.getStartDate());
                } else {//取得到现在的时间
                    userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(userInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                            ,BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9));
                    DietDays = BocosoftUitl.getDietDays(cal.getTime(), userInfo.getStartDate());
                }
            }
            
            if (userwds.size() > 0) {
                yAxisData = BocosoftUitl.createAllDayWeightDate(userwds, DietDays);
                idealBodyWeightAllData = BocosoftUitl.createidealBodyWeightDate(userInfo.getIdealBodyWeight(), DietDays);
                request.setAttribute("ideal_body_weight_all", idealBodyWeightAllData);
                request.setAttribute("all_data_y", yAxisData);
            } else {
                yAxisData.add("0");
                idealBodyWeightAllData.add("0");
                request.setAttribute("all_data_y", yAxisData);
                request.setAttribute("ideal_body_weight_all", idealBodyWeightAllData);
            }
            xAxisData = BocosoftUitl.createAllDayDate(DietDays);
            request.setAttribute("all_data_x", xAxisData);
            List<String> allMaxAndMin = BocosoftUitl.getListsMinAndMax(userInfo.getIdealBodyWeight(), userwds);
            request.setAttribute("all_data_max", allMaxAndMin.get(0));
            request.setAttribute("all_data_min", allMaxAndMin.get(1));
        } else {
            yAxisData.add("0");
            xAxisData.add("0");
            idealBodyWeightAllData.add("0");
            request.setAttribute("all_data_y", yAxisData);
            request.setAttribute("all_data_x", xAxisData);
            request.setAttribute("ideal_body_weight_all", idealBodyWeightAllData);
            request.setAttribute("all_data_max", userInfo.getIdealBodyWeight());
            request.setAttribute("all_data_min", 0);
        }
        
        return "userInfo/showUserInfo";
    }
   
    /**
     * 根据user_login_Id取得客户账号
     * @param date
     */    
    @RequestMapping(value = "/get_login_info_id", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult getUserLoginId(HttpServletRequest request) {
    	int userLoginInfoId = Integer.parseInt(request.getParameter("userLoginInfoId"));
        UserLoginInfoMapper userLoginInfoMapper = (UserLoginInfoMapper) SpringBeanUtils.getBean("userLoginInfoMapper");
        UserLoginInfo uli = userLoginInfoMapper.getUserLoginInfo(userLoginInfoId);
        String loginId = uli.getLoginId();
        json = new JSONResult(loginId, "成功", true);
        return json;
    }
    
   /**
    * 对营养师管理下的客户减肥开始时间的修改 
    * @param request
    * @return
    */
    @RequestMapping(value = "/edit_start_date", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_start_date(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userinfo = userInfoService.findByUserInfo(userInfoId);
        Boolean peopleNumFlag = false;
        if(userinfo.getStartDate() == null){
            peopleNumFlag = true;
        }
        String startDate = request.getParameter("startDate");
        UserInfo userInfo = userInfoService.updateStartDate(userInfoId, startDate);
        //累计减重人数加一
        if(peopleNumFlag){
            String parentPath = getClass().getResource("/").getFile().toString();
            String peopleNumPath = parentPath + BsetConsts.PEOPLE_NUM_FILEPATH;
            String tmpPeopleNum = BocosoftUitl.readTxtFile(peopleNumPath);
            int peopleNumLoss = 1+Integer.parseInt(tmpPeopleNum);
            BocosoftUitl.setTxtMessage(String.valueOf(peopleNumLoss), peopleNumPath);
        }
        String result = "change";
        json = new JSONResult(result, "成功", true);
        return json;
    }
   
   /**
    * 对营养师管理下的客户减肥结束时间的修改 
    * @param request
    * @return
    */
    @RequestMapping(value = "/edit_end_date", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_end_date(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String endDate = request.getParameter("endDate");
        UserInfo userInfo = userInfoService.updateEndDate(userInfoId, endDate);
        String result = "change";
        json = new JSONResult(result, "成功", true);
        return json;
    }
   
   /**
    * 对营养师管理下的客户点评的修改
    * @param request
    * @return
 * @throws ParseException 
    */
    @RequestMapping(value = "/edit_user_comments", method = RequestMethod.POST)
    public String edit_user_comments(HttpServletRequest request) throws ParseException {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        //userInfoService.set_user_comments(dietitianId, userInfoId, date, comments)
        UserData userData = userInfoService.findUserInfoByDate(userInfoId, date);
       //WeeklyRecommend wr = userInfoService.findWeeklyRecommend(userDataId);
        if (userData == null) {
            UserData ud = new UserData();
            ud.setUserInfoId(userInfoId);
            ud.setDate(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
            request.setAttribute("user_data", ud);
        } else {
            request.setAttribute("user_data", userData);
        }
        return "userInfo/editUserComments";
    }
   
   /**
    * 对营养师管理下的客户点评的保存
    * @param request
    * @return
    */
    @RequestMapping(value = "/edit_user_comments_save", method = RequestMethod.POST)
    public String edit_user_comments_save(UserData userData, HttpServletRequest request) {
        UserData tmpUserData = userInfoService.updataUserData(userData);
        UserInfo userInfo = userInfoService.findByUserInfo(tmpUserData.getUserInfoId());
        //WeeklyRecommend wr = userInfoService.findWeeklyRecommend(tmpUserData);
        List<UserData2> breakfast = userInfoService.getUserData2(userInfo.getId(), BocosoftUitl.dateToString(userData.getDate(), BsetConsts.DATE_FORMAT_9), 1);
        List<UserData2> lunch = userInfoService.getUserData2(userInfo.getId(), BocosoftUitl.dateToString(userData.getDate(), BsetConsts.DATE_FORMAT_9), 2);
        List<UserData2> dinner = userInfoService.getUserData2(userInfo.getId(), BocosoftUitl.dateToString(userData.getDate(), BsetConsts.DATE_FORMAT_9), 3);
        request.setAttribute("breakfastPhotos", breakfast);
        request.setAttribute("lunchPhotos", lunch);
        request.setAttribute("dinnerPhotos", dinner);
        request.setAttribute("user_data", tmpUserData);
        request.setAttribute("user_info", userInfo);
        request.setAttribute("chose_date", BocosoftUitl.dateToString(tmpUserData.getDate(), BsetConsts.DATE_FORMAT_9));
        return "userInfo/userData";
    }
   
   /**
    * 对营养师管理下的客户周运动指导的修改
    * @param request
    * @return
    * @throws ParseException 
    */
    @RequestMapping(value = "/edit_weekly_recommend_sport", method = RequestMethod.POST)
    public String edit_weekly_recommend_sport(HttpServletRequest request) throws ParseException {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        WeeklyRecommend wr = new WeeklyRecommend();
        wr.setUserInfoId(userInfoId);
        if (date != null && !date.equals("")) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
            int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
            int year = cal.get(Calendar.YEAR);
            wr.setWeekCount(weekOfYear);
            wr.setYyyy(year);
            request.setAttribute("chose_date", date);
        } else {
            Calendar cal = Calendar.getInstance();
            int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
            int year = cal.get(Calendar.YEAR);
            wr.setWeekCount(weekOfYear);
            wr.setYyyy(year);
            request.setAttribute("chose_date", BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9));
        }

        WeeklyRecommend wlr = userInfoService.findWeeklyRecomendById(wr);
        if (wlr != null) {
            request.setAttribute("weekly_recommend", wlr);
        } else {
            request.setAttribute("weekly_recommend", wr);
        }
        return "userInfo/editWeeklyRecommendSport";
    }
   
   /**
    * 对营养师管理下的客户周运动指导的保存
    * @param request
    * @return
    * @throws ParseException 
    */
    @RequestMapping(value = "/edit_weekly_recommend_save", method = RequestMethod.POST)
    public String edit_weekly_recommend_save(WeeklyRecommend wr, HttpServletRequest request) throws ParseException {
        String date = request.getParameter("chose_date");
        WeeklyRecommend tmpWr = userInfoService.saveWr(wr);
        UserInfo userInfo = userInfoService.findByUserInfo(wr.getUserInfoId());
        request.setAttribute("user_info", userInfo);
        request.setAttribute("weekly_recommend", tmpWr);
        request.setAttribute("chose_date", date);
        return "userInfo/weeklyRecommend";
    }
    
    /**
     * 对营养师管理下的客户备注修改
     * @param request
     * @return
     * @throws ParseException 
     */
    @RequestMapping(value = "/edit_user_note", method = RequestMethod.POST)
    public String edit_user_note(WeeklyRecommend wr, HttpServletRequest request) throws ParseException {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        request.setAttribute("user_info", userInfo);
        return "userInfo/editNote";
    }

    /**
      * 对营养师管理下的客户备注保存
      * @param request
      * @return
      * @throws ParseException 
    */
    @RequestMapping(value = "/edit_user_note_save", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult edit_user_note_save(WeeklyRecommend wr, HttpServletRequest request){
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String note = request.getParameter("note");
        UserInfo userInfo = userInfoService.updateUserNote(userInfoId, note);
        String result = "change";
        json = new JSONResult(result, "成功", true);
        return json;
    }
    
    /**
    * 对营养师管理下的客户周饮食指导的修改
    * @param request
    * @return
    * @throws ParseException 
    */
    @RequestMapping(value = "/edit_weekly_recommend_menu", method = RequestMethod.POST)
    public String edit_weekly_recommend_menu(HttpServletRequest request) throws ParseException {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        WeeklyRecommend wr = new WeeklyRecommend();
        wr.setUserInfoId(userInfoId);
        if (date != null && !date.equals("")) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
            int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
            int year = cal.get(Calendar.YEAR);
            wr.setWeekCount(weekOfYear);
            wr.setYyyy(year);
            request.setAttribute("chose_date", date);
        } else {
            Calendar cal = Calendar.getInstance();
            int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
            int year = cal.get(Calendar.YEAR);
            wr.setWeekCount(weekOfYear);
            wr.setYyyy(year);
            request.setAttribute("chose_date", BocosoftUitl.dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9));
        }
        WeeklyRecommend wlr = userInfoService.findWeeklyRecomendById(wr);
        if (wlr != null) {
            request.setAttribute("weekly_recommend", wlr);
        } else {
            request.setAttribute("weekly_recommend", wr);
        }
       
        return "userInfo/editWeeklyRecommendMenu";
    }
   
   /**
    * 对营养师管理下的客户根据日期查询数据
    * @param request
    * @return
    */
    @RequestMapping(value = "/select_user_data", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_user_data(HttpServletRequest request) {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        UserData tmpUserData = userInfoService.findUserInfoByDate(userInfoId, date);
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
//        System.out.println(tmpUserData);
//        System.out.println(userInfo);
        
//        List<UserData2> breakfast = userInfoService.getUserData2(userInfoId, date, 1);
//        List<UserData2> lunch = userInfoService.getUserData2(userInfoId, date, 2);
//        List<UserData2> dinner = userInfoService.getUserData2(userInfoId, date, 3);
        
//        request.setAttribute("breakfastPhotos", breakfast);
//        request.setAttribute("lunchPhotos", lunch);
//        request.setAttribute("dinnerPhotos", dinner);
//        request.setAttribute("user_info", userInfo);
//        request.setAttribute("user_data", tmpUserData);
//        request.setAttribute("chose_date", date);
//        return "userInfo/userData";
        return json = new JSONResult(tmpUserData);
    }
   
   /**
    * 对营养师管理下的客户根据日期查询每周运动和饮食指导
    * @param request
    * @return
    * @throws ParseException 
    */
    @RequestMapping(value = "/select_weekly_recommend", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult select_weekly_recommend(HttpServletRequest request) throws ParseException {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        WeeklyRecommend wr = new WeeklyRecommend();
        wr.setUserInfoId(userInfoId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        wr.setWeekCount(weekOfYear);
        wr.setYyyy(year);
        WeeklyRecommend newWr = userInfoService.findWeeklyRecomendById(wr);
        if (newWr != null) {
        	wr = newWr;
        }
//        System.out.println(wr.getWeekCount());
//        request.setAttribute("weekly_recommend", wr);
//        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
//        UserData tmpUserData = new UserData();
//        //tmpUserData = userInfoService.findUserInfoByDate(userInfoId, date);
//        tmpUserData.setDate(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
//        request.setAttribute("user_data", tmpUserData);
//        request.setAttribute("user_info", userInfo);
//        request.setAttribute("chose_date", date);
//        return "userInfo/weeklyRecommend";
        return json = new JSONResult(wr);
    }
    
//     /**
//      * 取得最活跃的用户
//      * @param request
//      * @return
//      */
//     @RequestMapping(value = "/get_most_active_user", method = RequestMethod.POST)
//     @ResponseBody
//     public JSONResult get_most_active_user(HttpServletRequest request) {
//         String userStatus = request.getParameter("userStatus");
//         List<UserInfo> userInfos = new ArrayList<UserInfo>();
//         int currentPage = Integer.parseInt(request.getParameter("page"));
//         PageHelper.startPage(currentPage, BsetConsts.PER_PAGE_SIZE);

//         if (userStatus != null && !userStatus.equals("")) {
//             userInfos = userInfoService.getMostActiveUserInfos(Integer.parseInt(userStatus));
//         }
//         else {
//             userInfos = userInfoService.getMostActiveUserInfos();
//         }
//         PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfos);
// //        System.out.println(currentPage);

//         return json = new JSONResult(pageInfo, "成功", true);
//     }
    
    /**
     * 取得按照活跃度排序的用户
     * @param request
     * @return
     */
    @RequestMapping(value = "/get_active_user", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_active_user(HttpServletRequest request) {
        int order = Integer.parseInt(request.getParameter("order"));
        String userStatus = request.getParameter("userStatus");
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        int currentPage = Integer.parseInt(request.getParameter("page"));
        PageHelper.startPage(currentPage, BsetConsts.PER_PAGE_SIZE);
        if (order == 1) {
            if (userStatus != null && !userStatus.equals("")) {
                userInfos = userInfoService.getLeastActiveUserInfos(Integer.parseInt(userStatus));
            }
            else {
                userInfos = userInfoService.getLeastActiveUserInfos();
            }
        }
        else if (order == 2) {
            if (userStatus != null && !userStatus.equals("")) {
                userInfos = userInfoService.getMostActiveUserInfos(Integer.parseInt(userStatus));
            }
            else {
                userInfos = userInfoService.getMostActiveUserInfos();
            }
        }


        PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfos);
        return json = new JSONResult(pageInfo, "成功", true);
    }

    /**
     * 取得营养师管理下的客户七天数据 
     * @param request
     * @return
      * @throws ParseException 
     */
    @RequestMapping(value = "/get_user_weight_sevenday_data", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_user_weight_sevenday_data(HttpServletRequest request) throws ParseException {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        UserData userData = userInfoService.findByUserData(userInfoId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        Calendar cal3 = Calendar.getInstance();
        cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        cal3.add(Calendar.WEEK_OF_MONTH, -1);
        cal3.set(Calendar.DAY_OF_WEEK, 7);
        List<UserWeightData> uwds = userInfoService.findByUserWeightData(userInfoId, weekOfYear, year);
        UserWeightData uwd = userInfoService.findUserWeightDataByDate(userInfoId, BocosoftUitl.dateToString(cal3.getTime(), BsetConsts.DATE_FORMAT_9));//取得上周最后一条数据
        List<String> sevenData = new ArrayList<String>();
        if (uwds.size() > 0 || uwd != null) {
            sevenData = BocosoftUitl.createSevenDayDate(uwd, uwds);//创建8天图形数据
        } else {
            sevenData.add("0");
        }
        json = new JSONResult(sevenData, "成功", true);
        return json;
    }
    
    /**
     * 取得营养师管理下的客户七天附加数据 
     * @param request
     * @return
      * @throws ParseException 
     */
    @RequestMapping(value = "/get_weight_sevenday_maxAndMin_data", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_weight_sevenday_maxAndMin_data(HttpServletRequest request) throws ParseException {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        UserData userData = userInfoService.findByUserData(userInfoId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        Calendar cal3 = Calendar.getInstance();
        cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        cal3.add(Calendar.WEEK_OF_MONTH, -1);
        cal3.set(Calendar.DAY_OF_WEEK, 7);
        List<UserWeightData> uwds = userInfoService.findByUserWeightData(userInfoId, weekOfYear, year);
        UserWeightData uwd = userInfoService.findUserWeightDataByDate(userInfoId, BocosoftUitl.dateToString(cal3.getTime(), BsetConsts.DATE_FORMAT_9));//取得上周最后一条数据

        if (uwds.size() > 0 || uwd != null) {
        	List<String> maxAndMin = BocosoftUitl.getListsMinAndMax(userInfo.getIdealBodyWeight(), uwds);
            json = new JSONResult(maxAndMin, "成功", true);
            return json;
        }
        else
        {
        	return json = new JSONResult("null", "成功", true);
        }
    }
    
    /**
     * 取得营养师管理下的客户体重table数据 
     * @param request
     * @return
      * @throws ParseException 
     */
    @RequestMapping(value = "/get_user_weight_table_data", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_user_weight_table_data(HttpServletRequest request) throws ParseException {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        String date = request.getParameter("date");
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        UserData userData = userInfoService.findByUserData(userInfoId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        Calendar cal3 = Calendar.getInstance();
        cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        cal3.add(Calendar.WEEK_OF_MONTH, -1);
        cal3.set(Calendar.DAY_OF_WEEK, 7);
        List<UserWeightData> uwds = userInfoService.findByUserWeightData(userInfoId, weekOfYear, year);
        UserWeightData uwd = userInfoService.findUserWeightDataByDate(userInfoId, BocosoftUitl.dateToString(cal3.getTime(), BsetConsts.DATE_FORMAT_9));//取得上周最后一条数据
        Map<String, TableDataBean> tableData = new LinkedHashMap<String, TableDataBean>();//创建8天表格数据
        List SevenDayTableDate = BocosoftUitl.dateToWeek(cal.getTime());
        TableDataBean tdb = new TableDataBean();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        if (uwd != null) {
            tdb.setDate(sdf.format(SevenDayTableDate.get(0)));
            tdb.setDietWeight(Math.abs(uwd.getDeltaWeight()));
            tdb.setWeight(uwd.getWeight());
            tdb.setFlag(0);
            tableData.put("周六 ", tdb);
        } else {
            tdb.setDate(sdf.format(SevenDayTableDate.get(0)));
            tableData.put("周六 ", tdb);
        }
        tableData = BocosoftUitl.createSevenDayTableDate(uwds, SevenDayTableDate);
        json = new JSONResult(tableData, "成功", true);
        return json;
    }
    
    /**
     * 取得营养师管理下的客户参与项目时间 
     * @param request
     * @return
      * @throws ParseException 
     */
    @RequestMapping(value = "/get_user_participating_time", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_user_participating_time(HttpServletRequest request)
    {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        UserData userData = userInfoService.findByUserData(userInfoId);
        List<String> xAxisData = new ArrayList<String>();
        List<UserWeightData> userwds = new ArrayList<UserWeightData>();
        Calendar cal2 = Calendar.getInstance();
        int DietDays = 0;
        if (userInfo.getStartDate() != null) {//参加尼基计划  显示总曲线
            if (userInfo.getPhase() > 1) {//参加过尼基计划
                DietPhaseInfo dietPhaseInfo = userInfoService.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase() - 1);
                userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                        ,BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
                DietDays = BocosoftUitl.getDietDays(dietPhaseInfo.getEndDate(), dietPhaseInfo.getStartDate());
            } else {//只取得尼基计划内体重总体
                if (userInfo.getEndDate() != null) {//取得到尼基计划结束的时间
                    userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(userInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                            ,BocosoftUitl.dateToString(userInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
                    DietDays = BocosoftUitl.getDietDays(userInfo.getEndDate(), userInfo.getStartDate());
                } else {//取得到现在的时间
                    userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(userInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                            ,BocosoftUitl.dateToString(cal2.getTime(), BsetConsts.DATE_FORMAT_9));
                    DietDays = BocosoftUitl.getDietDays(cal2.getTime(), userInfo.getStartDate());
                }
            }
            xAxisData = BocosoftUitl.createAllDayDate(DietDays);
        } else {
            xAxisData.add("0");
        }
        json = new JSONResult(xAxisData, "成功", true);
        return json;
    }
    
    /**
     * 取得营养师管理下的客户真实体重走势数据
     * @param request
     * @return
      * @throws ParseException 
     */
    @RequestMapping(value = "/get_user_participation_weight_data", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_user_participation_weight_data(HttpServletRequest request)
    {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        UserData userData = userInfoService.findByUserData(userInfoId);
        List<String> yAxisData = new ArrayList<String>();
        List<UserWeightData> userwds = new ArrayList<UserWeightData>();
        Calendar cal2 = Calendar.getInstance();
        int DietDays = 0;
        if (userInfo.getStartDate() != null) {//参加尼基计划  显示总曲线
            if (userInfo.getPhase() > 1) {//参加过尼基计划
                DietPhaseInfo dietPhaseInfo = userInfoService.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase() - 1);
                userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                        ,BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
                DietDays = BocosoftUitl.getDietDays(dietPhaseInfo.getEndDate(), dietPhaseInfo.getStartDate());
            } else {//只取得尼基计划内体重总体
                if (userInfo.getEndDate() != null) {//取得到尼基计划结束的时间
                    userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(userInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                            ,BocosoftUitl.dateToString(userInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
                    DietDays = BocosoftUitl.getDietDays(userInfo.getEndDate(), userInfo.getStartDate());
                } else {//取得到现在的时间
                    userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(userInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                            ,BocosoftUitl.dateToString(cal2.getTime(), BsetConsts.DATE_FORMAT_9));
                    DietDays = BocosoftUitl.getDietDays(cal2.getTime(), userInfo.getStartDate());
                }
            }
            
            if (userwds.size() > 0) {
                yAxisData = BocosoftUitl.createAllDayWeightDate(userwds, DietDays);
            } else {
                yAxisData.add("0");
            }
        } else {
            yAxisData.add("0");
        }
        json = new JSONResult(yAxisData, "成功", true);
        return json;
    }
    
    /**
     * 取得营养师管理下的客户理想体重走势数据
     * @param request
     * @return
      * @throws ParseException 
     */
    @RequestMapping(value = "/get_user_ideal_weight_data", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_user_ideal_weight_data(HttpServletRequest request)
    {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        UserData userData = userInfoService.findByUserData(userInfoId);
        List<String> idealBodyWeightAllData = new ArrayList<String>();
        List<UserWeightData> userwds = new ArrayList<UserWeightData>();
        Calendar cal2 = Calendar.getInstance();
        int DietDays = 0;
        if (userInfo.getStartDate() != null) {//参加尼基计划  显示总曲线
            if (userInfo.getPhase() > 1) {//参加过尼基计划
                DietPhaseInfo dietPhaseInfo = userInfoService.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase() - 1);
                userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                        ,BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
                DietDays = BocosoftUitl.getDietDays(dietPhaseInfo.getEndDate(), dietPhaseInfo.getStartDate());
            } else {//只取得尼基计划内体重总体
                if (userInfo.getEndDate() != null) {//取得到尼基计划结束的时间
                    userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(userInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                            ,BocosoftUitl.dateToString(userInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
                    DietDays = BocosoftUitl.getDietDays(userInfo.getEndDate(), userInfo.getStartDate());
                } else {//取得到现在的时间
                    userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(userInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                            ,BocosoftUitl.dateToString(cal2.getTime(), BsetConsts.DATE_FORMAT_9));
                    DietDays = BocosoftUitl.getDietDays(cal2.getTime(), userInfo.getStartDate());
                }
            }
            
            if (userwds.size() > 0) {
                idealBodyWeightAllData = BocosoftUitl.createidealBodyWeightDate(userInfo.getIdealBodyWeight(), DietDays);
            } else {
                idealBodyWeightAllData.add("0");
            }
        } else {
            idealBodyWeightAllData.add("0");
        }
        json = new JSONResult(idealBodyWeightAllData, "成功", true);
        return json;
    }
    
    /**
     * 取得全时附加数据
     * @param request
     * @return
      * @throws ParseException 
     */
    @RequestMapping(value = "/get_user_alltime_MinAndMax_data", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_user_alltime_MinAndMax_data(HttpServletRequest request)
    {
        int userInfoId = Integer.parseInt(request.getParameter("userInfoId"));
        UserInfo userInfo = userInfoService.findByUserInfo(userInfoId);
        UserData userData = userInfoService.findByUserData(userInfoId);
        List<UserWeightData> userwds = new ArrayList<UserWeightData>();
        Calendar cal2 = Calendar.getInstance();
        List<String> allMaxAndMin = new ArrayList<String>();
        int DietDays = 0;
        if (userInfo.getStartDate() != null) {//参加尼基计划  显示总曲线
            if (userInfo.getPhase() > 1) {//参加过尼基计划
                DietPhaseInfo dietPhaseInfo = userInfoService.findDietPhaseInfo(userInfo.getId(), userInfo.getPhase() - 1);
                userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                        ,BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
                DietDays = BocosoftUitl.getDietDays(dietPhaseInfo.getEndDate(), dietPhaseInfo.getStartDate());
            } else {//只取得尼基计划内体重总体
                if (userInfo.getEndDate() != null) {//取得到尼基计划结束的时间
                    userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(userInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                            ,BocosoftUitl.dateToString(userInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
                    DietDays = BocosoftUitl.getDietDays(userInfo.getEndDate(), userInfo.getStartDate());
                } else {//取得到现在的时间
                    userwds = userInfoService.findUserWeightDatasByDate(userInfoId, BocosoftUitl.dateToString(userInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                            ,BocosoftUitl.dateToString(cal2.getTime(), BsetConsts.DATE_FORMAT_9));
                    DietDays = BocosoftUitl.getDietDays(cal2.getTime(), userInfo.getStartDate());
                }
            }
            allMaxAndMin = BocosoftUitl.getListsMinAndMax(userInfo.getIdealBodyWeight(), userwds);
            json = new JSONResult(allMaxAndMin, "成功", true);
            return json;
        } else {
            json = new JSONResult("null", "成功", true);
            return json;
        }
    }

    /**
    * 取得营养师管理下的客户历史数据详情图形数据详细信息 
     * @param request
     * @return
      * @throws ParseException 
     */
    @RequestMapping(value = "/show_histroy_user_info", method = RequestMethod.POST)
    public String show_histroy_user_info(HttpServletRequest request) throws ParseException {
         int dietPhaseInfoId = Integer.parseInt(request.getParameter("dietPhaseInfoId"));
         DietPhaseInfo dietPhaseInfo = userInfoService.select_user_histroy_weight_Data(dietPhaseInfoId);
         dietPhaseInfo.setStartWeight(userInfoService.findFirstUserWeightDataByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)).getWeight());
         dietPhaseInfo.setEndWeight(userInfoService.findLastUserWeightDataByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9)).getWeight());
         request.setAttribute("dietPhaseInfo", dietPhaseInfo);
         UserInfo userInfo = userInfoService.findByUserInfo(dietPhaseInfo.getUserInfoId());
         List<String> yAxisData = new ArrayList<String>();
         List<String> xAxisData = new ArrayList<String>();
         List<String> idealBodyWeightAllData = new ArrayList<String>();
         List<UserWeightData> userwds = new ArrayList<UserWeightData>();
         int DietDays = 0;
         userwds = userInfoService.findUserWeightDatasByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                 ,BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
         DietDays = BocosoftUitl.getDietDays(dietPhaseInfo.getEndDate(), dietPhaseInfo.getStartDate());
         if (userwds.size() > 0) {
             yAxisData = BocosoftUitl.createAllDayWeightDate(userwds, DietDays);
             idealBodyWeightAllData = BocosoftUitl.createidealBodyWeightDate(userInfo.getIdealBodyWeight(), DietDays);
             request.setAttribute("ideal_body_weight_all", idealBodyWeightAllData);
             request.setAttribute("all_data_y", yAxisData);
         } else {
             yAxisData.add("0");
             idealBodyWeightAllData.add("0");
             request.setAttribute("all_data_y", yAxisData);
             request.setAttribute("ideal_body_weight_all", idealBodyWeightAllData);
         }
         xAxisData = BocosoftUitl.createAllDayDate(DietDays);
         request.setAttribute("all_data_x", xAxisData);
         List<String> allMaxAndMin = BocosoftUitl.getListsMinAndMax(userInfo.getIdealBodyWeight(), userwds);
         request.setAttribute("all_data_max", allMaxAndMin.get(0));
         request.setAttribute("all_data_min", allMaxAndMin.get(1));
         return "userInfo/history_info";
     }
    
    /**
     * 取得历史记录中的客户参与项目时间 
     * @param request
     * @return
      * @throws ParseException 
     */
    @RequestMapping(value = "/get_history_participating_time", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_history_participating_time(HttpServletRequest request)
    {
        int dietPhaseInfoId = Integer.parseInt(request.getParameter("dietPhaseInfoId"));
        DietPhaseInfo dietPhaseInfo = userInfoService.select_user_histroy_weight_Data(dietPhaseInfoId);
        dietPhaseInfo.setStartWeight(userInfoService.findFirstUserWeightDataByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)).getWeight());
        dietPhaseInfo.setEndWeight(userInfoService.findLastUserWeightDataByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9)).getWeight());
        List<String> xAxisData = new ArrayList<String>();
        int DietDays = 0;
        DietDays = BocosoftUitl.getDietDays(dietPhaseInfo.getEndDate(), dietPhaseInfo.getStartDate());
        xAxisData = BocosoftUitl.createAllDayDate(DietDays);
        json = new JSONResult(xAxisData, "成功", true);
        return json;
    }
    
    /**
     * 取得历史记录中客户体重走势数据
     * @param request
     * @return
      * @throws ParseException 
     */
    @RequestMapping(value = "/get_history_participation_weight_data", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_history_participation_weight_data(HttpServletRequest request)
    {
        int dietPhaseInfoId = Integer.parseInt(request.getParameter("dietPhaseInfoId"));
        DietPhaseInfo dietPhaseInfo = userInfoService.select_user_histroy_weight_Data(dietPhaseInfoId);
        dietPhaseInfo.setStartWeight(userInfoService.findFirstUserWeightDataByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)).getWeight());
        dietPhaseInfo.setEndWeight(userInfoService.findLastUserWeightDataByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9)).getWeight());
        List<String> yAxisData = new ArrayList<String>();
        List<UserWeightData> userwds = new ArrayList<UserWeightData>();
        int DietDays = 0;
        userwds = userInfoService.findUserWeightDatasByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                ,BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
        DietDays = BocosoftUitl.getDietDays(dietPhaseInfo.getEndDate(), dietPhaseInfo.getStartDate());
        if (userwds.size() > 0) {
            yAxisData = BocosoftUitl.createAllDayWeightDate(userwds, DietDays);
        } else {
            yAxisData.add("0");
        }
        json = new JSONResult(yAxisData, "成功", true);
        return json;
    }
  
    /**
     * 取得历史记录中客户理想体重走势数据
     * @param request
     * @return
      * @throws ParseException 
     */
    @RequestMapping(value = "/get_history_ideal_weight_data", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_history_ideal_weight_data(HttpServletRequest request)
    {
        int dietPhaseInfoId = Integer.parseInt(request.getParameter("dietPhaseInfoId"));
        DietPhaseInfo dietPhaseInfo = userInfoService.select_user_histroy_weight_Data(dietPhaseInfoId);
        dietPhaseInfo.setStartWeight(userInfoService.findFirstUserWeightDataByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)).getWeight());
        dietPhaseInfo.setEndWeight(userInfoService.findLastUserWeightDataByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9)).getWeight());
        request.setAttribute("dietPhaseInfo", dietPhaseInfo);
        UserInfo userInfo = userInfoService.findByUserInfo(dietPhaseInfo.getUserInfoId());
        List<String> idealBodyWeightAllData = new ArrayList<String>();
        List<UserWeightData> userwds = new ArrayList<UserWeightData>();
        int DietDays = 0;
        userwds = userInfoService.findUserWeightDatasByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                ,BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
        DietDays = BocosoftUitl.getDietDays(dietPhaseInfo.getEndDate(), dietPhaseInfo.getStartDate());
        if (userwds.size() > 0) {
            idealBodyWeightAllData = BocosoftUitl.createidealBodyWeightDate(userInfo.getIdealBodyWeight(), DietDays);
        } else {
            idealBodyWeightAllData.add("0");
        }
        json = new JSONResult(idealBodyWeightAllData, "成功", true);
        return json;
    }
   
    /**
     * 取得全时附加数据
     * @param request
     * @return
      * @throws ParseException 
     */
    @RequestMapping(value = "/get_history_MinAndMax_data", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_history_MinAndMax_data(HttpServletRequest request)
    {
        int dietPhaseInfoId = Integer.parseInt(request.getParameter("dietPhaseInfoId"));
        DietPhaseInfo dietPhaseInfo = userInfoService.select_user_histroy_weight_Data(dietPhaseInfoId);
        dietPhaseInfo.setStartWeight(userInfoService.findFirstUserWeightDataByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)).getWeight());
        dietPhaseInfo.setEndWeight(userInfoService.findLastUserWeightDataByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9)).getWeight());
        request.setAttribute("dietPhaseInfo", dietPhaseInfo);
        UserInfo userInfo = userInfoService.findByUserInfo(dietPhaseInfo.getUserInfoId());
        List<UserWeightData> userwds = new ArrayList<UserWeightData>();
        userwds = userInfoService.findUserWeightDatasByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)
                ,BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9));
        List<String> allMaxAndMin = BocosoftUitl.getListsMinAndMax(userInfo.getIdealBodyWeight(), userwds);
        json = new JSONResult(allMaxAndMin,"成功", true);
        return json;
    }
    
    /**
     * 取得全时附加数据
     * @param request
     * @return
      * @throws ParseException 
     */
    @RequestMapping(value = "/get_history_phase_table_data", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult get_history_phase_table_data(HttpServletRequest request)
    {
        int dietPhaseInfoId = Integer.parseInt(request.getParameter("dietPhaseInfoId"));
        DietPhaseInfo dietPhaseInfo = userInfoService.select_user_histroy_weight_Data(dietPhaseInfoId);
        dietPhaseInfo.setStartWeight(userInfoService.findFirstUserWeightDataByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getStartDate(), BsetConsts.DATE_FORMAT_9)).getWeight());
        dietPhaseInfo.setEndWeight(userInfoService.findLastUserWeightDataByDate(dietPhaseInfo.getUserInfoId(), BocosoftUitl.dateToString(dietPhaseInfo.getEndDate(), BsetConsts.DATE_FORMAT_9)).getWeight());
        json = new JSONResult(dietPhaseInfo,"成功", true);
        return json;
    }
    
    /**
      * 对营养师管理下的客户根据日期查询每周运动和饮食指导
      * @param request
      * @return
      * @throws ParseException 
      */
    @RequestMapping(value = "/delete_user_photo", method = RequestMethod.POST)
    public String delete_user_photo(HttpServletRequest request) throws ParseException {
          int userData2Id = Integer.parseInt(request.getParameter("userData2Id"));
          UserData2 ud2 = userInfoService.findUserData2(userData2Id);
          String date = BocosoftUitl.dateToString(ud2.getDate(), BsetConsts.DATE_FORMAT_9);
          int userInfoId = ud2.getUserInfoId();
          userInfoService.deleteUserData2(userData2Id);
          UserData tmpUserData = userInfoService.findUserInfoByDate(ud2.getUserInfoId(), date);
          UserInfo userInfo = userInfoService.findByUserInfo(ud2.getUserInfoId());
          List<UserData2> breakfast = userInfoService.getUserData2(userInfoId, date, 1);
          List<UserData2> lunch = userInfoService.getUserData2(userInfoId, date, 2);
          List<UserData2> dinner = userInfoService.getUserData2(userInfoId, date, 3);
          request.setAttribute("breakfastPhotos", breakfast);
          request.setAttribute("lunchPhotos", lunch);
          request.setAttribute("dinnerPhotos", dinner);
          request.setAttribute("user_info", userInfo);
          request.setAttribute("user_data", tmpUserData);
          request.setAttribute("chose_date", date);
          return "userInfo/userData";
      }
}
