package cn.com.bocosoft.common;

public class UserBean {
    public Integer userInfoId;//userInfoId
    public Integer userStatus;//正在期,过渡期,完成期
    public String name;//昵称
    public Integer weekly;//第多少周
    public Double lostTotalWeight;//一共减重多少
    public Integer dietDay;//减重多少天
    public Integer comfortLevel;//舒适度
    public boolean topFlag;//置顶
    public boolean dataFlag;//是否填写数据的标志
    public String headPhotoUrl;//头像的地址
    public Integer alarmFlag;//完成期提醒标志
    public Integer getUserInfoId() {
        return userInfoId;
    }
    public void setUserInfoId(Integer userInfoId) {
        this.userInfoId = userInfoId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public Double getLostTotalWeight() {
        return lostTotalWeight;
    }
    public void setLostTotalWeight(Double lostTotalWeight) {
        this.lostTotalWeight = lostTotalWeight;
    }
    public Integer getDietDay() {
        return dietDay;
    }
    public void setDietDay(Integer dietDay) {
        this.dietDay = dietDay;
    }

    
    public Integer getComfortLevel() {
        return comfortLevel;
    }
    public void setComfortLevel(Integer comfortLevel) {
        this.comfortLevel = comfortLevel;
    }
    public boolean isTopFlag() {
        return topFlag;
    }
    public void setTopFlag(boolean topFlag) {
        this.topFlag = topFlag;
    }
    public boolean isDataFlag() {
        return dataFlag;
    }
    public void setDataFlag(boolean dataFlag) {
        this.dataFlag = dataFlag;
    }
    public String getHeadPhotoUrl() {
        return headPhotoUrl;
    }
    public void setHeadPhotoUrl(String headPhotoUrl) {
        this.headPhotoUrl = headPhotoUrl;
    }
    public Integer getUserStatus() {
        return userStatus;
    }
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
    public Integer getWeekly() {
        return weekly;
    }
    public void setWeekly(Integer weekly) {
        this.weekly = weekly;
    }
    public Integer getAlarmFlag() {
        return alarmFlag;
    }
    public void setAlarmFlag(Integer alarmFlag) {
        this.alarmFlag = alarmFlag;
    }
    
}
