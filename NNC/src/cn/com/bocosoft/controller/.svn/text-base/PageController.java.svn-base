package cn.com.bocosoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 视图控制器,返回jsp视图给前端
 * 
 * @author common
 * @
 **/
@Controller
@RequestMapping("/page")
public class PageController {
    /**
     * 登录页
     */
    @RequestMapping("/login")
    public String login() {
        return "index";
    }

    /**
     * dashboard页
     */
    @RequestMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    /**
     * 404页
     */
    @RequestMapping("/404")
    public String error404() {
        return "index";
    }

    /**
     * 401页
     */
    @RequestMapping("/401")
    public String error401() {
        return "index";
    }

    /**
     * 500页
     */
    @RequestMapping("/500")
    public String error500() {
        return "index";
    }

}