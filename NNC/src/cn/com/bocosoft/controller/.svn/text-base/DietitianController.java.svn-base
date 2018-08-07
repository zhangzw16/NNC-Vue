package cn.com.bocosoft.controller;

import java.util.List;

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
import cn.com.bocosoft.model.Dietitian;
import cn.com.bocosoft.service.DietitianService;

@Controller
@RequestMapping("dietitian")
public class DietitianController {
    @Resource
    DietitianService dietitianService;
    /**
     * 营养师管理
     * @param request
     * @return
     */
    @RequestMapping(value = "/dietitian_manage", method = RequestMethod.POST)
    public String dietitian_manage(HttpServletRequest request) {
        return "dietitian/dietitianManage";
    }
    
    /**
     * 营养师列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/dietitian_page", method = RequestMethod.POST)
    public String dietitian_page(HttpServletRequest request) {
        int currentPage = Integer.parseInt(request.getParameter("page"));
        PageHelper.startPage(currentPage, BsetConsts.PER_PAGE_SIZE);
        List<Dietitian> dietitians = dietitianService.getDietitians();
        PageInfo<Dietitian> pageInfo = new PageInfo<Dietitian>(dietitians);
        request.setAttribute("max_page",pageInfo.getPages());
        request.setAttribute("current_page",pageInfo.getPageNum());
        request.setAttribute("cause",dietitians);
        return "dietitian/dietitianList";
    }
    
    /**
     * 营养师添加
     * @param request
     * @return
     */
    @RequestMapping(value = "/add_dietitian", method = RequestMethod.POST)
    public String add_dietitian(HttpServletRequest request) {
        return "dietitian/addDietitian";
    }
    
    @RequestMapping(value = "/dietitian_save", method = RequestMethod.POST)
    @ResponseBody
    public String dietitian_save(Dietitian dietitian, HttpServletRequest request) {
        int saveflag = dietitianService.dietitian_save(dietitian);
        return String.valueOf(saveflag);
    }
    
    /**
     * 营养师信息的修改
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit_dietitian_info", method = RequestMethod.POST)
    public String edit_dietitian_info(HttpServletRequest request) {
        int dietitianId = Integer.parseInt(request.getParameter("param"));
        Dietitian dietitian = dietitianService.getDietitian(dietitianId);
        request.setAttribute("dietitian", dietitian);
        return "dietitian/editDietitian";
    }
    
    /**
     * 营养师信息的修改保存
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit_dietitian_info_save", method = RequestMethod.POST)
    @ResponseBody
    public String edit_dietitian_info_save(Dietitian dietitian, HttpServletRequest request) {
        int saveflag = dietitianService.edit_dietitian_save(dietitian);
        return String.valueOf(saveflag);
    }
    
    /**
     * 营养师上岗时间的修改
     * @param request
     * @return
     */
    @RequestMapping(value = "/change_dietitian_work_start_time", method = RequestMethod.POST)
    public String change_dietitian_work_start_time(HttpServletRequest request) {
        int dietitianId = Integer.parseInt(request.getParameter("param"));
        String current_page = request.getParameter("current_page");
        Dietitian dietitian = dietitianService.getDietitian(dietitianId);
        request.setAttribute("dietitian_id",dietitian.getId());
        request.setAttribute("current_page",current_page);
        request.setAttribute("work_time",BocosoftUitl.dateToString(dietitian.getWorkStartDate(), BsetConsts.DATE_FORMAT_9));
        return "dietitian/chang_dietitian_work_time";
    }
    
    /**
     * 营养师上岗时间的保存
     * @param request
     * @return
     */
    @RequestMapping(value = "/dietitian_work_start_time_save", method = RequestMethod.POST)
    @ResponseBody
    public String dietitian_work_start_time_save(HttpServletRequest request) {
        int dietitianId = Integer.parseInt(request.getParameter("dietitian_id"));
        String workStartTime = request.getParameter("work_start_time");
        int updateflag = dietitianService.updateStartWorkTime(dietitianId, workStartTime);
        return String.valueOf(updateflag);
    }
    
    /**
     * 营养师下岗时间的修改
     * @param request
     * @return
     */
    @RequestMapping(value = "/change_dietitian_work_end_time", method = RequestMethod.POST)
    public String change_dietitian_work_end_time(HttpServletRequest request) {
        int dietitianId = Integer.parseInt(request.getParameter("param"));
        String current_page = request.getParameter("current_page");
        Dietitian dietitian = dietitianService.getDietitian(dietitianId);
        request.setAttribute("dietitian_id",dietitian.getId());
        request.setAttribute("current_page",current_page);
        request.setAttribute("work_time",BocosoftUitl.dateToString(dietitian.getWorkEndDate(), BsetConsts.DATE_FORMAT_9));
        return "dietitian/chang_dietitian_work_time";
    }
    
    /**
     * 营养师下岗时间的保存
     * @param request
     * @return
     */
    @RequestMapping(value = "/dietitian_work_end_time_save", method = RequestMethod.POST)
    @ResponseBody
    public String dietitian_work_end_time_save(HttpServletRequest request) {
        int dietitianId = Integer.parseInt(request.getParameter("dietitian_id"));
        String workEndTime = request.getParameter("work_end_time");
        int updateflag = dietitianService.updateEndWorkTime(dietitianId, workEndTime);
        return String.valueOf(updateflag);
    }
    
    /**
     * 营养师重置密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/reset_dietitian_passwd", method = RequestMethod.POST)
    @ResponseBody
    public String reset_dietitian_passwd(HttpServletRequest request) {
        int dietitianId = Integer.parseInt(request.getParameter("dietitian_id"));
        int updateflag = dietitianService.resetDietitianPasswd(dietitianId);
        return String.valueOf(updateflag);
    }
}
