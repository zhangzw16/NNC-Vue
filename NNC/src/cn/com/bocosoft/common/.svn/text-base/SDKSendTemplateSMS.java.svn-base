package cn.com.bocosoft.common;

import java.util.HashMap;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

public class SDKSendTemplateSMS {
    HashMap<String, Object> result = null;
    CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
    public final void init () {
        //******************************注释*********************************************
        //*初始化服务器地址和端口                                                       *
        //*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
        //*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
        //*******************************************************************************
        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount("8a216da85e0e48b2015e324251f70db4", "ae4987ea85fe449e976f36b652ece0f5");
        restAPI.setAppId("8a216da85e0e48b2015e3242544a0dbb");
    }
    
    public final void setAccount() {
        //******************************注释*********************************************
        //*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
        //*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
        //*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
        //*******************************************************************************
        restAPI.setAccount("8a216da85d158d1b015d2f61c3ec0900", "26c7b8ab8d1a44f4bd98ad8067cc4e2e");
    }
    
    public final void setAppId() {
        //******************************注释*********************************************
        //*初始化应用ID                                                                 *
        //*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
        //*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
        //*******************************************************************************
        restAPI.setAppId("8a216da85d158d1b015d2f61c42d0904");
    }
    
    /**
     * 
     * @param phoneNo  接收验证码的手机
     * @param templateType  消息模版类型
     * @param effectivTime 有效时间
     * @return
     */
    public final HashMap<String, Object> sendTemplateSMS(String phoneNo, String templateType, String validation, String effectivTime) {
        //******************************注释****************************************************************
        //*调用发送模板短信的接口发送短信                                                                  *
        //*参数顺序说明：                                                                                  *
        //*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
        //*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
        //*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
        //*第三个参数是要替换的内容数组。                                                                                                                             *
        //**************************************************************************************************
        
        //**************************************举例说明***********************************************************************
        //*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
        //*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});                                                                         *
        //*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
        //*********************************************************************************************************************
        result = restAPI.sendTemplateSMS(phoneNo, templateType ,new String[]{validation, effectivTime});
        return result;
    }
}