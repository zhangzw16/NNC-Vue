package cn.com.bocosoft.model;

import java.util.Map;

public class UserReportData {
    // private Map<String, Integer> numOfMan;

    // private Map<String, Integer> numOfWoman;

    private Map<Integer, Integer> averWeightLossOfDietitian;
    
    private Map<Integer, Integer> personOfDietitian;

    public Map<Integer, Integer> getAverWeightLossOfDietitian() {
        return this.averWeightLossOfDietitian;
    }

    public void setAverWeightLossOfDietitian(Map<Integer, Integer> aWLD) {
        this.averWeightLossOfDietitian = aWLD;
    }

    public Map<Integer, Integer> getPersonOfDietitian() {
        return this.personOfDietitian;
    }

    public void setPersonOfDietitian(Map<Integer, Integer> PD) {
        this.personOfDietitian = PD;
    }
}