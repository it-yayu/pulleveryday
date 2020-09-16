package com.test.testmysql.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
//处理日期
	public List<String> findDates(Date dBegin, Date dEnd){
		  List<String> lDate = new ArrayList<String>();
		  SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		  lDate.add(sd.format(dBegin));
		  Calendar calBegin = Calendar.getInstance();
		  // 使用给定的 Date 设置此 Calendar 的时间
		  calBegin.setTime(dBegin);
		  Calendar calEnd = Calendar.getInstance();
		  // 使用给定的 Date 设置此 Calendar 的时间
		  calEnd.setTime(dEnd);
		  // 测试此日期是否在指定日期之后
		  while (dEnd.after(calBegin.getTime()))
		  {
			   // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			   calBegin.add(Calendar.DAY_OF_MONTH, 1);
			   lDate.add(sd.format(calBegin.getTime()));
		  }
		  return lDate;
	 }
	//处理编码
	public String convertCharset(String s) {
		if (s != null) {
			try {
				int length = s.length();
				byte[] buffer = new byte[length];
				// 0x81 to Unicode 0x0081, 0x8d to 0x008d, 0x8f to 0x008f, 0x90
				// to 0x0090, and 0x9d to 0x009d.
				for (int i = 0; i < length; ++i) {
					char c = s.charAt(i);
					if (c == 0x0081) {
						buffer[i] = (byte) 0x81;
					} else if (c == 0x008d) {
						buffer[i] = (byte) 0x8d;
					} else if (c == 0x008f) {
						buffer[i] = (byte) 0x8f;
					} else if (c == 0x0090) {
						buffer[i] = (byte) 0x90;
					} else if (c == 0x009d) {
						buffer[i] = (byte) 0x9d;
					} else {
						buffer[i] = Character.toString(c).getBytes("CP1252")[0];
					}
				}
				String result = new String(buffer, "UTF-8");
				return s;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
}
	
	

    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
        return Integer.parseInt(String.valueOf(between_days));     
    } 

	
	
}
