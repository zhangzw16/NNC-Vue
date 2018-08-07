package cn.com.bocosoft.background;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.bocosoft.common.BocosoftUitl;
import cn.com.bocosoft.common.BsetConsts;

public class SetSystemTime {
    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(SetSystemTime.class);
    
    public void run() throws ParseException {
        LOG.info("The task of set system time started.");
        String parentPath = getClass().getResource("/").getFile().toString();
        
        String filePath = parentPath + BsetConsts.TIME_FILEPATH;
        String date = BocosoftUitl.readTxtFile(filePath);
        Date tmpDate = BocosoftUitl.getDateforNub(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9), 1);
        
        BocosoftUitl.setTxtMessage(BocosoftUitl.dateToString(tmpDate, BsetConsts.DATE_FORMAT_9), filePath);
        LOG.info("The task of set System time ended.");
    }
}
