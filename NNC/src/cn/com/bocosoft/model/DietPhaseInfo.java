package cn.com.bocosoft.model;

import java.util.Date;

public class DietPhaseInfo {
    private Integer id;

    private Integer userInfoId;

    private Integer phaseCount;

    private Double startWeight;

    private Double endWeight;

    private Integer alarmFlag;
    
    private String dietitianName;

    private Date startDate;

    private Date endDate;

    private Date transitionStartDate;

    private Date transitionEndDate;

    private Integer createId;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Integer userInfoId) {
        this.userInfoId = userInfoId;
    }

    public Integer getPhaseCount() {
        return phaseCount;
    }

    public void setPhaseCount(Integer phaseCount) {
        this.phaseCount = phaseCount;
    }

    public Double getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(Double startWeight) {
        this.startWeight = startWeight;
    }

    public Double getEndWeight() {
        return endWeight;
    }

    public void setEndWeight(Double endWeight) {
        this.endWeight = endWeight;
    }

    public String getDietitianName() {
        return dietitianName;
    }

    public void setDietitianName(String dietitianName) {
        this.dietitianName = dietitianName == null ? null : dietitianName.trim();
    }

    public Integer getAlarmFlag() {
        return alarmFlag;
    }

    public void setAlarmFlag(Integer alarmFlag) {
        this.alarmFlag = alarmFlag;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getTransitionStartDate() {
        return transitionStartDate;
    }

    public void setTransitionStartDate(Date transitionStartDate) {
        this.transitionStartDate = transitionStartDate;
    }

    public Date getTransitionEndDate() {
        return transitionEndDate;
    }

    public void setTransitionEndDate(Date transitionEndDate) {
        this.transitionEndDate = transitionEndDate;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}