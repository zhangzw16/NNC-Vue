package cn.com.bocosoft.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import cn.com.bocosoft.model.SystemUser;
import cn.com.bocosoft.model.UserWeightData;

public class BocosoftUitl {
    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(BocosoftUitl.class);
    /**
     * md5加密 
     * @param str
     * @return String
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public final static String getMd5Str(String str) 
        throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.reset();
        md5.update(str.getBytes("UTF-8"));
        byte[] byteArray = md5.digest();
        StringBuffer md5Buffer = new StringBuffer();
        for(int i=0;i<byteArray.length;i++){
            if(Integer.toHexString(0xFF & byteArray[i]).length()==1){
                md5Buffer.append("0");
            }
            md5Buffer.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5Buffer.toString();
    }
    
    /**
     * 随机密码生成
     * @param len 生成密码的长度
     * @return String生成的密码
     */
    public final static String makeRandomPassword(int len){
        char charr[] = "1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int x = 0; x < len; ++x) {
            sb.append(charr[r.nextInt(charr.length)]);
        }
        return sb.toString();
    }
    
    /**
     * 获取系统日期
     * date装字符串
     * @return
     * @throws ParseException 
     */
    public final static String getDateStr(String format) throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        return df.format(new Date());
    }
    /**
     * Date格式化为字符串
     * 
     * @param time
     *            被转时间
     * @param format
     *            转换格式
     * @return String 转换后字符串
     */
    public final static String dateToString(Date time, String format) {
        if (time == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(time);
    }
   
    
    /**
     * 字符串格式化为Date
     * 
     * @param time
     *            被转字符串
     * @param format
     *            转换格式
     * @return String 转换后Date
     * @throws ParseException
     */
    public final static Date stringToDate(String time, String format)
            throws ParseException {
        if (time == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.parse(time);
    }

    /**
     * 取得session中的值
     * @param key
     * @return Object
     */
    public final static Object getSession(String key){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return (session != null) ? session.getAttribute(key) : null;
    }
    
    /**
     * 取得登录系统用户的id
     * @return
     */
    public final static Integer getSystemUserId () {
        int id = 0;
        if(getSession("system_user") != null){
            SystemUser user = (SystemUser)getSession("system_user");
            id = user.getId();
        }
        return id;
    }

    /**
     * 组建7天的图形数据
     * @param uwd 
     * @param uwds
     * @return
     */
    public static List<String> createSevenDayDate(UserWeightData uwd, List<UserWeightData> uwds) {
        List<String> datas = new ArrayList<String>();//组建图形数据
        if (uwd != null) {
            datas.add(""+uwd.getWeight()+"");
        } else {
            datas.add("'-'");
        }
        for (int i=1; i < 8; i++) {//组建7天数据，不管数据是否存在都要组成7天数据，没有数据为 '-'
            boolean flag = true;
            for (UserWeightData uswd : uwds) {
                if (uswd.getWeekDay() == i) {
                    datas.add(""+uswd.getWeight()+"");
                    flag = false;
                    break;
                }
            }
            if (flag) {//如果添加了数据，就不在添加空数据
                datas.add("'-'");
            }
        }
        return datas;
    }
    
    /**
     * 组建手机端7天的图形数据
     * @param uwd 
     * @param uwds
     * @return
     */
    public static List<String> createSevenDayDateApp(UserWeightData uwd, List<UserWeightData> uwds) {
        List<String> datas = new ArrayList<String>();//组建图形数据
        if (uwd != null) {
            datas.add(""+uwd.getWeight()+"");
        } else {
            datas.add("-");
        }
        for (int i=1; i < 8; i++) {//组建7天数据，不管数据是否存在都要组成7天数据，没有数据为 '-'
            boolean flag = true;
            for (UserWeightData uswd : uwds) {
                if (uswd.getWeekDay() == i) {
                    datas.add(""+uswd.getWeight()+"");
                    flag = false;
                    break;
                }
            }
            if (flag) {//如果添加了数据，就不在添加空数据
//                if (i < uwds.size()) {
//                    Double lastData = uwds.get(i-2).getWeight();
//                    Double nextData = uwds.get(i-1).getWeight();
//                    datas.add(""+(lastData + nextData) / 2.0+"");
//                } else {
                    datas.add("-");
//                }
            }
        }
        
      //处理数据，计算平均数
        List<String> strList = dealWithDate(datas);
        return strList;
    }
    
    public static Map<String, TableDataBean> createSevenDayTableDate(List<UserWeightData> uwds, List sevenDayTableDate) {
        Map<String, TableDataBean> tableData = new LinkedHashMap<String, TableDataBean>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        for (int i=1; i < BsetConsts.DAY_SIZE; i++) {//组建7天数据，不管数据是否存在都要组成7天数据，没有数据为 '-'
            boolean flag = true;
            for (UserWeightData uswd : uwds) {
                TableDataBean tdbean = new TableDataBean();
                if (uswd.getWeekDay() == i) {
                    tdbean.setDate(sdf.format(sevenDayTableDate.get(i)));
                    tdbean.setDietWeight(Math.abs(uswd.getDeltaWeight()));
                    tdbean.setWeight(uswd.getWeight());
                    tdbean.setFlag(uswd.getUpDown());
                    tableData.put(getDayOfWeek(i), tdbean);
                    flag = false;
                    break;
                }
            }
            if (flag) {//如果添加了数据，就不在添加空数据
                TableDataBean tdbean = new TableDataBean();
                tdbean.setDate(sdf.format(sevenDayTableDate.get(i)));
                tableData.put(getDayOfWeek(i), tdbean);
            }
        }
        return tableData;
    }
    
    /**
     * 取得开始减重第几天
     * @param date
     * @param startDate
     * @return
     */
    public static int getDietDays(Date date, Date startDate) {
        Calendar calsd = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        calsd.setTime(startDate);
        cal.setTime(date);
        long nowTime = (cal.getTimeInMillis() - (calsd.getTimeInMillis())) / BsetConsts.TIME_DAY;
        return (int) (nowTime + 1);
    }

    /**
     * 取得2时间的差值
     * @param date
     * @param startDate
     * @return 
     */
    public static int compare2Day(Date date, Date startDate) {
        Calendar calsd = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        calsd.setTime(startDate);
        cal.setTime(date);
        long nowTime = (cal.getTimeInMillis() - (calsd.getTimeInMillis())) / BsetConsts.TIME_DAY;
        return (int) (nowTime);
    }
    
    /**
     * 比较两个时间的大小
     * @param date
     * @param startDate
     * @return 
     */
    public static long compare2Time(Date date, Date startDate) {
        Calendar calsd = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        calsd.setTime(startDate);
        cal.setTime(date);
        long nowTime = (cal.getTimeInMillis() - (calsd.getTimeInMillis()));
        return nowTime;
    }
    
    
    /**
     * 取得时间在一周中的第几天
     * @param tmpDate  星期日为一周的第一天
     * @return
     */
    public static int getWeekDay(Date tmpDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(tmpDate);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        return week;
    }

    /**
     * 组建一共减脂多少天
     * @param dietDays
     * @return
     */
    public static List<String> createAllDayDate(int dietDays) {
        List<String> datas = new ArrayList<String>();//组建图形数据
        for (int i = 1; i <= dietDays; i++) {
            datas.add(""+i+"");
        }
        return datas;
    }

    /**
     * 组建一共减重的体重数据
     * @param userwds
     * @param dietDays 
     * @return
     */
    public static List<String> createAllDayWeightDate(List<UserWeightData> userwds, int dietDays) {
        List<String> datas = new ArrayList<String>();//组建图形数据
        for (int i=1; i <= dietDays; i++) {//组建7天数据，不管数据是否存在都要组成7天数据，没有数据为 '-'
            boolean flag = true;
            for (UserWeightData uswd : userwds) {
                if (uswd.getDietDays() == i) {
                    datas.add(""+uswd.getWeight()+"");
                    flag = false;
                    break;
                }
            }
            if (flag) {//如果添加了数据，就不在添加空数据
                datas.add("'-'");
            }
        }
        return datas;
    }
    
    /**
     * 组建手机端一共减重的体重数据
     * @param userwds
     * @param dietDays 
     * @return
     */
    public static List<String> createAllDayWeightDateApp(List<UserWeightData> userwds, int dietDays) {
        List<String> datas = new ArrayList<String>();//组建图形数据
        for (int i=1; i <= dietDays; i++) {//组建7天数据，不管数据是否存在都要组成7天数据，没有数据为 '-'
            boolean flag = true;
            for (UserWeightData uswd : userwds) {
                if (uswd.getDietDays() == i) {
                    datas.add(""+uswd.getWeight()+"");
                    flag = false;
                    break;
                }
            }
            if (flag) {//如果添加了数据，就不在添加空数据
                datas.add("-");
            }
        }
        return datas;
    }

    /**
     * 组建daySize天数的理想体重数据表
     * @param idealBodyWeight
     * @param daySize
     * @return
     */
    public static List<String> createidealBodyWeightDate(Double idealBodyWeight, int daySize) {
        List<String> datas = new ArrayList<String>();//组建图形数据
        for (int i = 1; i <= daySize; i++) {
            datas.add(""+idealBodyWeight+"");
        }
        return datas;
    }
    

    /**
     * 计算理想体重
     * @param height
     * @param sex
     * @return
     */
    public static Double getIdealWeight(Double height, int sex) {
        if (height < 150) {
            return height - 100;
        } else if (height > 175) {
            return height - 110;
        } else {
            if (sex == BsetConsts.SEX_1) {
                return height - 105;
            } else {
                return (height - 100) * 0.85;
            }
        }
    }
    
    
    /**
     * 计算理想体重BMI 23.9
     * @param height
     * @param sex
     * @return
     */
    public static Double getIdealWeightBMI(Double height, int sex) {
        Double weight = BsetConsts.WEIGHT_BMI * (height * height * 0.01 * 0.01);
        return Double.valueOf(String.format("%.1f", weight));
    }
    
    
    /** 
     * 根据日期获得所在周的日期  
     * @param mdate 
     * @return 
     */  
    @SuppressWarnings("deprecation")  
    public static List<Date> dateToWeek(Date mdate) {
        int b = mdate.getDay();
        Date fdate;  
        List<Date> list = new ArrayList<Date>();
        Long fTime = mdate.getTime() - b * 24 * 3600000;
        for (int a = 0; a < 8; a++) {  
            fdate = new Date();  
            fdate.setTime(fTime + ((a-1) * 24 * 3600000));
            list.add(a, fdate);
        }
        return list;
    }
    
    /**
     * 取得星期几
     * @param nub
     * @return
     */
    public static String getDayOfWeek(int nub) {
        String str = "";
        switch (nub) {
        case 1:
            str = "周日";
            break;
        case 2:
            str = "周一";
            break;
        case 3:
            str = "周二";
            break;
        case 4:
            str = "周三";
            break;
        case 5:
            str = "周四";
            break;
        case 6:
            str = "周五";
            break;
        case 7:
            str = "周六";
            break;
        default:
            break;
        }
        return str;
    }

    /**
     * 取得一组数的最大最小值
     * @param weight
     * @param uwds
     * @return
     */
    public static List<String> getListsMinAndMax(Double weight, List<UserWeightData> uwds) {
        List<String> values =  new ArrayList<String>();
        Double max = weight;
        Double min = weight;
        if (uwds.size() > 0 && weight == null) {
            max = 0.0;
            min = 100.0;
        }
        for(int i = 0; i < uwds.size(); i++){
            if(uwds.get(i).getWeight() > max){
                max = uwds.get(i).getWeight();
            }
            
            if(uwds.get(i).getWeight() < min){
                min = uwds.get(i).getWeight();
            }
        }
        min = Math.ceil(min) - 2;
        max = Math.ceil(max) + 1;
        values.add(String.valueOf((max)));
        if (min > 0) {
            values.add(String.valueOf((min)));
        } else {
            values.add(String.valueOf(0));
        }
        return values;
    }

    public static int getAge(String date) {
        Calendar cal = Calendar.getInstance();//根据出生年月计算年龄
        try {
            cal.setTime(BocosoftUitl.stringToDate(date, BsetConsts.DATE_FORMAT_9));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar cal2 = Calendar.getInstance();
        int tmpAge = cal2.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
        return tmpAge;
    }

    /**
     * 显示最后一天数据
     * @param uwds
     * @param type 1 周曲线   2  总曲线
     * @param uwd 
     * @return
     */
    public static List<String> createShowLastData(int type, UserWeightData uwd, List<UserWeightData> uwds) {
        List<String> showLastData = new ArrayList<String>();
        if (uwds.size() > 0) {
            if (type == 1) {
                showLastData.add(getDayOfWeek(uwds.get(uwds.size() - 1).getWeekDay()));
            } else {
                showLastData.add(String.valueOf(uwds.get(uwds.size() - 1).getDietDays()));
            }
            showLastData.add(String.valueOf(uwds.get(uwds.size() - 1).getWeight()));
        } else if (uwd != null) {
            showLastData.add("周六 ");
            showLastData.add(String.valueOf(uwd.getWeight()));
        }
        return showLastData;
    }
    
    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
    public static String readTxtFile(String filePath){
        StringBuilder result = new StringBuilder();
        
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    result.append(lineTxt);
                }
                read.close();
            }else{
                LOG.info("The txt is not find");
            }
        } catch (Exception e) {
            LOG.info("Red the txt message error");
            e.printStackTrace();
        }
        return result.toString();
     
    }

    /**
     * 取得时间后days的时间
     * @param date
     * @param days
     * @return
     * @throws ParseException 
     */
    public static Date getDateforNub(Date date, int days) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(stringToDate(dateToString(date, BsetConsts.DATE_FORMAT_9), BsetConsts.DATE_FORMAT_9));
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + days);
        Date tmpDate = stringToDate(dateToString(cal.getTime(), BsetConsts.DATE_FORMAT_9), BsetConsts.DATE_FORMAT_9);
        return tmpDate;
    }
    /**
     * 向TXT第一行里面添加内容
     * @param message
     * @param filePath
     */
    public static void setTxtMessage(String message, String filePath) {
        try {  
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();
            // 将内容插入  
            buf = buf.append(message);
            br.close();  
            FileOutputStream fos = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public static List<String> dealWithDate(List<String> datas) {
        ArrayList<Double> list = new ArrayList<Double>();
        for(String str : datas){
            if(str.equals("-")){
                list.add(0.0);
            }else{
                list.add(Double.parseDouble(str));
            }
        }
        ArrayList<Double> list2 = new ArrayList<Double>();
        for(Double i : list) {
            list2.add(i);
        }
        Double a = 0.0;
        Double b = 0.0;
        int flag1 = 0;
        int flag2 = 0;
        //取出第一位和最后一位不为0的索引
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i) != 0) {
                flag1 = i;
                break;
            }
        }
        for(int i = flag1 + 1; i < list.size(); i++) {
            if(list.get(i) != 0) {
                flag2 = i;
            }
        }
        for(int i = flag1; i < flag2; i++) {
            if(list.get(i) == 0 ) {
                //找之前的非空
                for(int i1 = i; i1 >= 0; i1--) {
                    if(list.get(i1) != 0) {
                        a = list.get(i1);
                        break;
                    }
                }
                //找之后的非空
                for(int i2 = i; i2 < list.size(); i2++) {
                    if(list.get(i2) != 0) {
                        b = list.get(i2);
                        break;
                    } 
                }
                list2.set(i, (a + b) / 2);
                if( (i != list.size() - 1) && list.get(1+i) == 0) {
                    list2.set(1+i, list2.get(i));
                }
                
            }
        }
        List<String> strList = new ArrayList<String>();
        for(Double dou : list2){
            if(dou==0.0){
                strList.add("-"); 
            }else{
                DecimalFormat df = new DecimalFormat("#0.0");
                strList.add(df.format(dou));
            }
        }
        return strList;
    }
}
