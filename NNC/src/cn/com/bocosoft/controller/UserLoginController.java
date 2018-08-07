package cn.com.bocosoft.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import cn.com.bocosoft.common.BocosoftUitl;
import cn.com.bocosoft.model.SystemUser;
import cn.com.bocosoft.service.SystemUserService;

@Controller
@RequestMapping("user")
//@SessionAttributes("system_user")
public class UserLoginController {
    
    @Resource
    private SystemUserService systemUserService;
    /**
     * 首页
     * 
     * @param request
     * @return
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */

    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public String login(SystemUser user, HttpServletRequest requests) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if ("".equals(user.getLoginId()) || "".equals(user.getPasswd())) {
            requests.setAttribute("login_message_tip", "请录入完整信息！");
            return "index";
        }
        List<SystemUser> users = systemUserService.userLogin(user);
        if (users.size() == 1) {//正常登录
            if (users.get(0).getPasswd().equals(user.getPasswd())) {//首次登陆
                HttpSession session = requests.getSession();
                session.setAttribute("system_user_flag", "true");
                session.setAttribute("system_user", users.get(0));
//                UsernamePasswordToken token = new UsernamePasswordToken(users.get(0).getLoginId(), users.get(0).getPasswd());
//                token.setRememberMe(true);
//                Subject subject = SecurityUtils.getSubject();
//                subject.login(token);
                return "framework";
            } else if (users.get(0).getPasswd().equals(BocosoftUitl.getMd5Str(user.getPasswd()))) {//加密密码
                HttpSession session = requests.getSession();
                session.setAttribute("system_user_flag", "false");
                session.setAttribute("system_user", users.get(0));
//                UsernamePasswordToken token = new UsernamePasswordToken(users.get(0).getLoginId(), users.get(0).getPasswd());
//                token.setRememberMe(true);
//                Subject subject = SecurityUtils.getSubject();
//                subject.login(token);
                return "framework";
            }
            requests.setAttribute("login_message_tip", "密码错误，请确认登录信息！");
            return "index";
        } else {
            requests.setAttribute("login_message_tip", "登陆失败，请确认登录信息！");
            return "index";
        }
    }
    
    @RequestMapping(value = "/logout")
    public String logOut(SystemUser user, HttpServletRequest requests) {
        SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
        return "index";
    }
}
