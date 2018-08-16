package cn.com.bocosoft.model;

import java.util.Map;
import java.util.List;

public class UserReportData {
    private Map<String, Integer> numOfMen;

    private Map<String, Integer> numOfWomen;

    private Map<String, Double> averWeightLossOfDietitian;
    
    private Map<String, Integer> personOfDietitian;

    List<Integer> registerNum;

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

    public Map<String, Integer> getNumOfMen() {
        return this.numOfMen;
    }

    public void setNumOfMen(Map<String, Integer> nom) {
        this.numOfMen = nom;
    } 

    public Map<String, Integer> getNumOfWomen() {
        return this.numOfWomen;
    }

    public void setNumOfWomen(Map<String, Integer> now) {
        this.numOfWomen = now;
    }

    public List<Integer> getRegisterNum() {
        return this.registerNum;
    }

    public void setRegisterNum(List<Integer> rn) {
        this.registerNum = rn;
    }
}