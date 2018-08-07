package cn.com.bocosoft.service.impl;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.bocosoft.common.BocosoftUitl;
import cn.com.bocosoft.common.BsetConsts;
import cn.com.bocosoft.dao.DietitianMapper;
import cn.com.bocosoft.dao.UserInfoMapper;
import cn.com.bocosoft.model.Dietitian;
import cn.com.bocosoft.service.DietitianService;

@Service
public class DietitianServiceImp implements DietitianService{

    @Resource
    private DietitianMapper dietitianMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Override
    public List<Dietitian> getDietitians() {
        return dietitianMapper.getDietitians();
    }
    @Override
    public int dietitian_save(Dietitian dietitian) {
        List<Dietitian> dietitians = dietitianMapper.find_by_dietitians(dietitian);
        if (dietitians.size() > 0) {
            return 0;
        } else {
            dietitian.setPhoneNo(dietitian.getLoginId());
            dietitian.setPasswd(BocosoftUitl.makeRandomPassword(BsetConsts.RANDOM_AUTHENTICATION_CODE_LEN));
            dietitian.setCreateId(BocosoftUitl.getSystemUserId());
            int flag = dietitianMapper.dietitian_save(dietitian);
            return flag;
        }
    }
    @Override
    public Dietitian getDietitian(int dietitianId) {
        return dietitianMapper.selectByPrimaryKey(dietitianId);
    }
    @Override
    public int updateStartWorkTime(int dietitianId, String workStartTime) {
        Dietitian dietitian = dietitianMapper.selectByPrimaryKey(dietitianId);
        try {
            dietitian.setWorkStartDate(BocosoftUitl.stringToDate(workStartTime, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dietitian.setUpdateId(BocosoftUitl.getSystemUserId());
        return dietitianMapper.updateByPrimaryKeySelective(dietitian);
    }
    @Override
    public int updateEndWorkTime(int dietitianId, String workEndTime) {
        int flag = userInfoMapper.get_all_users(dietitianId);
            if (flag <= 0) {
            Dietitian dietitian = dietitianMapper.selectByPrimaryKey(dietitianId);
            try {
                dietitian.setWorkEndDate(BocosoftUitl.stringToDate(workEndTime, BsetConsts.DATE_FORMAT_9));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            dietitian.setUpdateId(BocosoftUitl.getSystemUserId());
            return dietitianMapper.updateDietitian(dietitian);
        } else {
            return 2;
        }
    }
    
    @Override
    public int resetDietitianPasswd(int dietitianId) {
        Dietitian dietitian = dietitianMapper.selectByPrimaryKey(dietitianId);
        dietitian.setPasswd(BocosoftUitl.makeRandomPassword(BsetConsts.RANDOM_AUTHENTICATION_CODE_LEN));
        dietitian.setUpdateId(BocosoftUitl.getSystemUserId());
        return dietitianMapper.updateDietitian(dietitian);
    }
    @Override
    public int edit_dietitian_save(Dietitian dietitian) {
        List<Dietitian> tmpDietitians = dietitianMapper.findUserLoginInfoByPhone(dietitian.getLoginId());
        if (tmpDietitians.size() > 0) {
        	if (tmpDietitians.get(0).getId().equals(dietitian.getId())) {
        		return dietitianMapper.updateDietitian(dietitian);
            } else {
            	return 0;
            }
        }
        return dietitianMapper.updateDietitian(dietitian);
    }

}
