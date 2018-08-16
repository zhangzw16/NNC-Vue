package cn.com.bocosoft.model;

import java.util.Map;

public class UserReportData {
    // private Map<String, Integer> numOfMan;

    // private Map<String, Integer> numOfWoman;

    private Map<String, Double> averWeightLossOfDietitian;
    
    private Map<String, Integer> personOfDietitian;

    public Map<String, Double> getAverWeightLossOfDietitian() {
        return this.averWeightLossOfDietitian;
    }

    public void setAverWeightLossOfDietitian(Map<String, Double> aWLD) {
        this.averWeightLossOfDietitian = aWLD;
    }

    public Map<String, Integer> getPersonOfDietitian() {
        return this.personOfDietitian;
    }

    public void setPersonOfDietitian(Map<String, Integer> PD) {
        this.personOfDietitian = PD;
    }
}