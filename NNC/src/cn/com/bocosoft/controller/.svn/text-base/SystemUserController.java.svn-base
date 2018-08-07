package cn.com.bocosoft.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
import cn.com.bocosoft.common.JSONResult;
import cn.com.bocosoft.model.SystemUser;
import cn.com.bocosoft.service.SystemUserService;

@Controller
@RequestMapping("systemUser")
public class SystemUserController {
    @Resource
    private SystemUserService systemUserService;
    private JSONResult json;

    public JSONResult getJson() {
        return json;
    }

    public void setJson(JSONResult json) {
        this.json = json;
    }
    
    /**
     * 系统用户的添加
     * @param request
     * @return
     */
    @RequestMapping(value = "/manage", method = RequestMethod.POST)
    public String manage(HttpServletRequest request) {
        return "systemUser/systemUserManage";
    }
    
    /**
     * 管理员列表展示界面
     * @param requests
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/system_user_page", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult system_user_page(HttpServletRequest request) {
        int currentPage = Integer.parseInt(request.getParameter("page"));
        PageHelper.startPage(currentPage, BsetConsts.PER_PAGE_SIZE);
        List<SystemUser> systemUsers = systemUserService.getUsers();
        PageInfo<SystemUser> pageInfo = new PageInfo<SystemUser>(systemUsers);
//        request.setAttribute("max_page",pageInfo.getPages());
//        request.setAttribute("current_page",pageInfo.getPageNum());
//        request.setAttribute("cause",systemUsers);
//        return "systemUser/systemUserList";
        return json = new JSONResult(pageInfo, "成功", true);
    }
    
    /**
     * 系统用户的添加
     * @param request
     * @return
     */
    @RequestMapping(value = "/add_system_user", method = RequestMethod.POST)
    public String add_system_user(HttpServletRequest request) {
        return "systemUser/addSystemUser";
    }
    
    /**
     * 系统用户的保存
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/system_user_save", method = RequestMethod.POST)
    @ResponseBody
    public String system_user_save(SystemUser user, HttpServletRequest request) {
        int saveflag = systemUserService.system_user_save(user);
        return String.valueOf(saveflag);
    }
    
    /**
     * 系统用户的修改
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit_system_user", method = RequestMethod.POST)
    public String edit_system_user(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("param"));
        SystemUser user = systemUserService.getUserById(userId);
        request.setAttribute("system_user", user);
        return "systemUser/editSystemUser";
    }
    
    /**
     * 密码修改
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit_user_passwd", method = RequestMethod.POST)
    public String edit_user_passwd(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("param"));
        SystemUser user = systemUserService.getUserById(userId);
        request.setAttribute("system_user", user);
        if (user.getUpdateId() == null) {
            request.setAttribute("system_user_flag", "true");
        }
        return "systemUser/editUserPasswd";
    }
    
    /**
     * 系统用户修改的保存
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit_system_user_save", method = RequestMethod.POST)
    @ResponseBody
    public String edit_system_user_save(SystemUser user, HttpServletRequest request) {
        int saveflag = systemUserService.edit_system_user_save(user);
        return String.valueOf(saveflag);
    }
    
    
    /**
     * 密码修改更新
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit_user_passwd_save", method = RequestMethod.POST)
    @ResponseBody
    public String edit_user_passwd_save(SystemUser user, HttpServletRequest request) {
        systemUserService.system_edit_user_save(user);
        return "true";
    }
    
    /**
     * 密码验证
     * @param request
     * @return
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    @RequestMapping(value = "/check_user_passwd", method = RequestMethod.POST)
    @ResponseBody
    public String check_user_passwd(SystemUser user, HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String passwd = request.getParameter("passwd");
        String old_passwd = request.getParameter("old_passwd");
        String flag = request.getParameter("flag");
        if (flag.equals("true")) {
            if (!old_passwd.equals(passwd)) {
                return "false";
            }
        } else {
            if (!passwd.equals(BocosoftUitl.getMd5Str(old_passwd))) {
                return "false";
            }
        }
        return "true";
    }
    
    /**
     * 密码重置
     * @param request
     * @return
     */
    @RequestMapping(value = "/system_user_reset_passwd", method = RequestMethod.POST)
    @ResponseBody
    public String system_user_reset_passwd(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("param"));
        String flag = systemUserService.system_user_reset_passwd(userId);
        return flag;
    }
    
    /**
     * 删除管理员
     * @param request
     * @return
     */
    @RequestMapping(value = "/system_user_delete", method = RequestMethod.POST)
    @ResponseBody
    public String system_user_delete(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("param"));
        String flag = systemUserService.system_user_delete(userId);
        return flag;
    }
}
