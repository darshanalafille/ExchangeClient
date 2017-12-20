package com.stockexchange.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 *
 * Provide Many functionas due to date and time, try to keep the code law level as possible without using any third party libraries
 * or even java native objects related to timing.
 *
 * Created by darshanas on 9/13/2017.
 */
public class TimeUtils {


    private static int mktCloseH;
    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String timeDtFormat = "yyyyMMdd-HH:mm:ss";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static String dateString;

    static {
        dateString = new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }


    public static long getDatesBetween(String from, String to){
        LocalDate ldtStart = LocalDate.parse(from,dateFormatter);
        LocalDate ldtEnd = LocalDate.parse(to,dateFormatter);
        return ChronoUnit.DAYS.between(ldtStart, ldtEnd);
    }



    private static String getStringVal(int intVal){
        String x = Integer.toString(intVal);
        if(intVal < 10){
            return "0" + x;
        }
        return x;
    }


    public static String getTimeString(long milliVal){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date(milliVal));
    }



    public static String getDateString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    public static String getDateString(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }



    public static String getNextDate(String date,int diff)  {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try{
            Date dt = df.parse(date);
            LocalDateTime ldt = dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            ldt = ldt.plusDays(diff);
            Date nextDay = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
            return df.format(nextDay);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Date subtractDate(Date todat, int dayRange){
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(todat);
        cal.add(Calendar.DATE,(dayRange * -1));
        return cal.getTime();
    }

    public static String getCurrentGMTTimeStr(){
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        try {
            Date dt = dateFormatLocal.parse(dateFormatGmt.format(new Date()));
            return TimeUtils.getDateString(dt,"yyyyMMdd HHmmss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentGMTTimeStr(String format){
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        try {
            Date dt = dateFormatLocal.parse( dateFormatGmt.format(new Date()) );
            return TimeUtils.getDateString(dt,format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Long getCurrentGMTMills(){
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        try {
            Date dt = dateFormatLocal.parse(dateFormatGmt.format(new Date()));
            return dt.getTime();
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Long getMilliVal(String time) {

        String fm = dateString + "-" + time;
        SimpleDateFormat sdf = new SimpleDateFormat(timeDtFormat);
        try {
            Date date = sdf.parse(fm);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static Long getGMTMilliVal(String dateTime,String format){
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat(format);
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat dateFormatLcl = new SimpleDateFormat(format);
        try{
            Date date = dateFormatGmt.parse(dateTime);
            return date.getTime();
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }



}
