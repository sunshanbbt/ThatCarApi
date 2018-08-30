package com.zhengshun.touch.api.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



public class DateUtils {

    public final static String FULL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public final static String FULL_DATE_TIME2 = "yyyyMMddHHmmss";
    public final static String SHORT_DATE_TIME = "yyyy-MM-dd";
    public final static String SIMPLE_DATE_TIME = "yyyyMM";
    public final static String SIMPLE_DATE_TIME2 = "yyyy-MM";
    public final static String TRANS_DATE_TIME = "yyyyMMdd";
    public final static String INTEREST_DISPLAY_TIME = "yyyy.MM.dd";
    public final static String FULL_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static String FULL_DATE_PATTERN2 = "yyyy-MM-dd HH:mm:ss:SSS";
    public final static String REPORT_DATE_PATTERN = "yyyyMMdd";
    public final static String REPORT_DATE_PATTERN2 = "yyyy-MM-dd";

    public static String getSysDate(String pattern) {
        Date curDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(curDate);
    }

    public static String getSysDate(Date curDate, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(curDate);
    }

    public static Date getCurrentDate(String pattern) {
        Date curDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String strDate = sdf.format(curDate);
        return transStringToDate(strDate, pattern);

    }

    public static Date getDateWithPattern(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String strDate = sdf.format(date);
        return transStringToDate(strDate, pattern);
    }

    public static String transDateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static Date transStringToDate(String dateStr, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getDate(String date){
        SimpleDateFormat sdf2 = new SimpleDateFormat(DateUtils.SHORT_DATE_TIME);
        try {
            return sdf2.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date addCurrentDate(int periodDay) {
        String dateStr = getSysDate(SHORT_DATE_TIME);
        Date date = transStringToDate(dateStr, SHORT_DATE_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, periodDay);
        return calendar.getTime();
    }

    public static String addCurrentDate(int periodDay, String pattern) {
        String dateStr = getSysDate(SHORT_DATE_TIME);
        Date date = transStringToDate(dateStr, SHORT_DATE_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, periodDay);
        return transDateToString(calendar.getTime(), pattern);
    }

    public static Date addDay(Date date, int periodDay) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_TIME);
        String strDate = sdf.format(date);
        Date shortDate = transStringToDate(strDate, SHORT_DATE_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(shortDate);
        calendar.add(Calendar.DAY_OF_MONTH, periodDay);
        return calendar.getTime();
    }

    public static Date addDateDay(Date date, int periodDay, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String strDate = sdf.format(date);
        Date shortDate = transStringToDate(strDate, pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(shortDate);
        calendar.add(Calendar.DAY_OF_MONTH, periodDay);
        return calendar.getTime();
    }

    public static String addDay(Date date, int periodDay, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_TIME);
        String strDate = sdf.format(date);
        Date shortDate = transStringToDate(strDate, SHORT_DATE_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(shortDate);
        calendar.add(Calendar.DAY_OF_MONTH, periodDay);
        return transDateToString(calendar.getTime(), pattern);
    }

    public static String addDay(String date, int periodDay, String pattern) {
        Date shortDate = transStringToDate(date, SHORT_DATE_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(shortDate);
        calendar.add(Calendar.DAY_OF_MONTH, periodDay);
        return transDateToString(calendar.getTime(), pattern);
    }

    public static long dayDiff(Date date1, Date date2) {
        long diff = 0;
        if (date1.compareTo(date2) >= 0) {
            diff = date1.getTime() - date2.getTime();
        } else {
            diff = date2.getTime() - date1.getTime();
        }
        return diff / (1000 * 24 * 60 * 60);
    }

    public static boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (Calendar.SATURDAY == dayOfWeek || Calendar.SUNDAY == dayOfWeek) {
            return true;
        } else {
            return false;
        }
    }

    public static Date getLastDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
        return date;

    }

    public static int getDiffDays(Date endDate, Date startDate) {
        long endTime = endDate.getTime();
        long startTime = startDate.getTime();
        long days = (endTime - startTime) / (1000 * 60 * 60 * 24);
        return (int) days;
    }

    public static int getDiffDays(String endDate, String startDate) {
        Date start = transStringToDate(startDate, SHORT_DATE_TIME);
        Date end = transStringToDate(endDate, SHORT_DATE_TIME);
        long endTime = end.getTime();
        long startTime = start.getTime();
        long days = (endTime - startTime) / (1000 * 60 * 60 * 24);
        return (int) days;
    }

    public static Date getDateByDays(Integer days, Date nowDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_TIME);
        String date = sdf.format(nowDate) + days;
        Date curDate = transStringToDate(date, TRANS_DATE_TIME);
        return curDate;
    }

    public static Boolean isBetweenDates(Date startDate, Date endDate,
                                         Date date) {
        if (date.after(startDate) && date.before(endDate)
                || date.equals(startDate)) {
            return true;
        }
        return false;
    }

    public static Date getLastDateByDays(Integer days, Date nowDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.MONTH, -1);
        return getDateByDays(days, calendar.getTime());
    }

    public static Date addMinute(Date date, int miniute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, miniute);
        return calendar.getTime();
    }

    public static String dateFormat(String datetime, Integer periodMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_TIME2);
        Date date = null;
        try {
            date = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MONTH, periodMonth);
        date = cl.getTime();
        return sdf.format(date);
    }

    public static List<String> getBetweenDays(String beginDate,
                                              String endDate, String pattern) {
        Integer listSize = getDiffDays(endDate, beginDate);
        if (listSize < 0) {
            listSize = listSize * -1;
        }
        List<String> days = new ArrayList<String>(listSize + 1);
        for (int index = 0; index <= listSize; index++) {
            String addDay = addDay(beginDate, index, SHORT_DATE_TIME);
            days.add(addDay);
        }
        return days;
    }

    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATE_PATTERN);
        Calendar calendar = Calendar.getInstance(TimeZone
                .getTimeZone("GMT+8"));
        String now = sdf.format(calendar.getTime());
        return now;
    }

    public static String getCurrentTime(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance(TimeZone
                .getTimeZone("GMT+8"));
        String currentTime = sdf.format(calendar.getTime());
        return currentTime;
    }

    public static Date StringToDate(String dateTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATE_PATTERN);
            return sdf.parse(dateTime);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String DateToString(Date dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATE_PATTERN);
        return sdf.format(dateTime);
    }

    public static Date addDate(Date date, int offset) {
        Calendar calendar = Calendar.getInstance(TimeZone
                .getTimeZone("GMT+8"));
        calendar.setTime(date);
        calendar.add(Calendar.DATE, offset);// 把日期往后增加.整数往后推,负数往前移动
        date = calendar.getTime(); //
        return date;
    }

    // 时间格式校验
    public static boolean checkFormat(String date, String type) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        try {
            simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String transforDateFormat(String date, String inputPattern,
                                            String outputPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(inputPattern);
        try {
            Date _temp = sdf.parse(date);
            sdf = new SimpleDateFormat(outputPattern);
            return sdf.format(_temp);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String timestampToStr(Long timestamp, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(timestamp);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /**
     * 获取当年的第一天
     *
     * @return
     */
    public static String getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return DateUtils.transDateToString(getYearFirst(currentYear), DateUtils.SHORT_DATE_TIME);
    }

    /**
     * 获取当年的最后一天
     *
     * @return
     */
    public static String getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return DateUtils.transDateToString(getYearLast(currentYear), DateUtils.SHORT_DATE_TIME);
    }

    /**
     * 获取时间段内的月份
     *
     * @param startDay
     * @param lastDay
     * @return
     * @throws ParseException
     */
    public static List<String> getYearMounthList(String startDay, String lastDay) throws ParseException {
        List<String> monthList = new ArrayList<>();
        Date d1 = new SimpleDateFormat(SIMPLE_DATE_TIME2).parse(startDay);//定义起始日期
        Date d2 = new SimpleDateFormat(SIMPLE_DATE_TIME2).parse(lastDay);//定义结束日期
        Calendar dd = Calendar.getInstance();//定义日期实例
        dd.setTime(d1);//设置日期起始时间
        while (dd.getTime().before(d2) || dd.getTime().equals(d2)) {//判断是否到结束日期
            SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_TIME2);
            monthList.add(sdf.format(dd.getTime()));
            dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
        }
        return monthList;
    }

    public static List<String> getThisYearMounthList() throws ParseException {
        List<String> monthList = new ArrayList<>();
        Date d1 = new SimpleDateFormat(SIMPLE_DATE_TIME2).parse(getCurrYearFirst());//定义起始日期
        Date d2 = new SimpleDateFormat(SIMPLE_DATE_TIME2).parse(getCurrYearLast());//定义结束日期
        Calendar dd = Calendar.getInstance();//定义日期实例
        dd.setTime(d1);//设置日期起始时间
        while (dd.getTime().before(d2) || dd.getTime().equals(d2)) {//判断是否到结束日期
            SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_TIME2);
            monthList.add(sdf.format(dd.getTime()));
            dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
        }
        return monthList;
    }

    //获取本周的日期(8天)
    public static List<String> getWeekFrist(String date) throws ParseException {
        List<String> dateList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat(SHORT_DATE_TIME).parse(date));
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        //所在周开始日期
        dateList.add(new SimpleDateFormat(SHORT_DATE_TIME).format(cal.getTime()));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        //所在周结束日期
        dateList.add(new SimpleDateFormat(SHORT_DATE_TIME).format(cal.getTime()));
        return dateList;
    }

    //获取前月的第一天
    public static String getMonthFirstDate() {
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return new SimpleDateFormat(DateUtils.SHORT_DATE_TIME).format(cal_1.getTime());
    }

    //获取前月的最后一天
    public static String getMonthLastDate() {
        Calendar calendar = Calendar.getInstance();
        // 设置时间,当前时间不用设置
        // calendar.setTime(new Date());
        // 设置日期为本月最大日期
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        // 打印
        DateFormat format = new SimpleDateFormat(SHORT_DATE_TIME);
        return format.format(calendar.getTime());
    }

    //获取下个月的第一天
    public static String getPerFirstDayOfMonth() {
        SimpleDateFormat dft = new SimpleDateFormat(SHORT_DATE_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }

    //两个时间的天数差
    public static int daysBetween(Date smdate,Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    public static String getMonthFrist() {
        return null;
    }

    //	public static Date formatBeginDate( Date beginDate ) {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat( SHORT_DATE_TIME );
//		String dateStr = simpleDateFormat.format( beginDate ).concat( SystemConstant.BEGIN_DATE );
//		simpleDateFormat = new SimpleDateFormat( FULL_DATE_PATTERN );
//		try {
//			return simpleDateFormat.parse( dateStr );
//		} catch ( ParseException e ) {
//			return null;
//		}
//	}
//
//	public static Date formatEndDate( Date beginDate ) {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat( SHORT_DATE_TIME );
//		String dateStr = simpleDateFormat.format( beginDate ).concat( SystemConstant.END_DATE );
//		simpleDateFormat = new SimpleDateFormat( FULL_DATE_PATTERN );
//		try {
//			return simpleDateFormat.parse( dateStr );
//		} catch ( ParseException e ) {
//			return null;
//		}
//	}
//
    public static void main(String[] args) throws ParseException {
        System.out.println(addMinute(new Date(), 7*60 ));
    }




}
