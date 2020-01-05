package net.kitty.lesson.helloapi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
  
  //MM和mm,DD和dd的含义不一样，此处必须严格大小写
  private static String defaultDateFormat="yyyy-MM-dd";

  public static void main(String[] args) throws ParseException {
    //unix的时间转换是以毫秒为单位的，不是以秒为单位，注意1000的换算；
    //当数字过大需要在后面加上l，否则会报out of index的错误，不加l默认为int
    System.out.println(TimeUtil.getDateByUnix(1562751544000l));
    System.out.println(TimeUtil.getUnixDateWithMilliSecond("1988-01-23"));
  }

  public static String getDateByUnix(long unixTime) {
    Date date=new Date(unixTime);
    DateFormat dateformat=new SimpleDateFormat(defaultDateFormat);
    String dateStr=dateformat.format(date);
    
    return dateStr;
  }

  public static long getUnixDateWithMilliSecond(String dateStr) throws ParseException {
     DateFormat dfDateFormat=new SimpleDateFormat(defaultDateFormat);
     Date date=dfDateFormat.parse(dateStr);
     long unixTime=date.getTime();
     return unixTime;
  }
  
  public static long getUnixDateWithSecond(String dateStr) throws ParseException{
    return getUnixDateWithMilliSecond(dateStr)/1000;
  }

}
