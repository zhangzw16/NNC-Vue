package cn.com.bocosoft.model;

import java.util.Date;

public class UserInfoHistroy {
    private Integer id;

    private Integer materId;

    private Integer dietitianId;

    private Integer userLoginInfoId;

    private String name;

    private Date birthday;

    private Integer sex;

    private Integer age;

    private Double height;

    private Double weight;

    private Double idealBodyWeight;

    private Integer bloodPressure;

    private Integer bloodFat;

    private Integer bloodSugar;

    private Integer bloodUricAcid;

    private Integer hepaticAdiposeInfiltration;

    private Date dataStartDate;

    private Date startDate;

    private Date endDate;

    private Integer userStatus;

    private Integer topFlag;

    private Integer phase;

    private Integer vitality;

    private Integer createId;

    private Date createTime;

    private Integer updateId;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaterId() {
        return materId;
    }

    public void setMaterId(Integer materId) {
        this.materId = materId;
    }

    public Integer getDietitianId() {
        return dietitianId;
    }

    public void setDietitianId(Integer dietitianId) {
        this.dietitianId = dietitianId;
    }

    public Integer getUserLoginInfoId() {
        return userLoginInfoId;
    }

    public void setUserLoginInfoId(Integer userLoginInfoId) {
        this.userLoginInfoId = userLoginInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getIdealBodyWeight() {
        return idealBodyWeight;
    }

    public void setIdealBodyWeight(Double idealBodyWeight) {
        this.idealBodyWeight = idealBodyWeight;
    }

    public Integer getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(Integer bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Integer getBloodFat() {
        return bloodFat;
    }

    public void setBloodFat(Integer bloodFat) {
        this.bloodFat = bloodFat;
    }

    public Integer getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(Integer bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public Integer getBloodUricAcid() {
        return bloodUricAcid;
    }

    public void setBloodUricAcid(Integer bloodUricAcid) {
        this.bloodUricAcid = bloodUricAcid;
    }

    public Integer getHepaticAdiposeInfiltration() {
        return hepaticAdiposeInfiltration;
    }

    public void setHepaticAdiposeInfiltration(Integer hepaticAdiposeInfiltration) {
        this.hepaticAdiposeInfiltration = hepaticAdiposeInfiltration;
    }

    public Date getDataStartDate() {
        return dataStartDate;
    }

    public void setDataStartDate(Date dataStartDate) {
        this.dataStartDate = dataStartDate;
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

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getTopFlag() {
        return topFlag;
    }

    public void setTopFlag(Integer topFlag) {
        this.topFlag = topFlag;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public Integer getVitality() {
        return vitality;
    }

    public void setVitality(Integer vitality) {
        this.vitality = vitality;
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

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}