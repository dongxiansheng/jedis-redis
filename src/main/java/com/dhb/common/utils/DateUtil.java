/*
 * @(#)DateUtil.java
 *
 * Copyright (c) 2004 HiTRUST Incorporated. All rights reserved.
 *
 * Modify History:
 *  v1.00, 2004/08/10, Chris Liaw
 *   1) re-write from e-factoring
 *  v1.01, 2004/08/17, Steven Lin  
 *   1) add method getToday()
 *	v1.02, 2004/09/02, Joanne Lin
 *	 1) add getCountDays() method
 *
 *	v1.03, 2004/09/08, Chris Liaw
 *	 1) add getCountDaysToDouble() method
 *
 *	v1.04, 2004/10/20, Steven Lin
 *	 1) add methods getDateOfROC(),getDayOfWeek(),isMaxMonthDay(),getMaxMonthDay()
 */

package com.dhb.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

    public static final String TYPE_DATE = "D"; // 只取日期
    public static final String TYPE_TIME = "T"; // 只取时间
    public static final String TYPE_DATETIME = "DT"; // 取得日期及时间
    public static final String STYLE_XML = "X"; // XML格式 CCYY-MM-DDThh:mm:ss+hh:ss
    public static final String STYLE_AD = "AD"; // 西元年 CCYYMMDDhhmmss
    public static final String STYLE_ROC = "R"; // 民国年 YYYMMDDhhmmss
    public static final String STYLE_FORMAT = "F"; // 格式化 CCYY-MM-DD hh:mm:ss
    public static final String STYLE_FORMAT_FOR_USER = "FU"; // 格式化 CCYY/MM/DD hh:mm:ss

    public DateUtil() {
    }



    public synchronized static boolean compareDates(String date1, String date2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        
        if (sdf.parse(date1).after(sdf.parse(date2))) {
            return true;
        } else {
            return false;	
        }
    }
    
    /**
     * 取得当天日期字符串
     * @return
     */
    static public String getToday() {
        Calendar calendar = Calendar.getInstance();
        int tYear = calendar.get(Calendar.YEAR);
        int tMonth = calendar.get(Calendar.MONTH) + 1;
        int tDate = calendar.get(Calendar.DATE);
        return String.valueOf(tYear * 10000 + tMonth * 100 + tDate);
    }
    
    /**
	 * <p>
	 *     获取当前系统日期
	 * </P>
	 * @return String
	 */
	public static String getSystemDate(String dateFormat){
		Date sysDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		return simpleDateFormat.format(sysDate);
	}
    
	
	/**
	 * @author wushaolin
	 * @time 2012-11-26
	 * @reason 计算得到 指定时间-时间间隔
	 * @param dateStr-格式为yyyyMMdd timeInterval-时间间隔，单位为天
	 * @return String 格式为yyyyMMdd
	 */
	public static String getBeforeDate(String dateStr,int timeInterval) throws ParseException{
		SimpleDateFormat sdf = null;
		String beforeDateStr=null;
		sdf = new SimpleDateFormat("yyyyMMdd");
		Date date=sdf.parse(dateStr);
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		int day=cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, day-timeInterval);
		Date date1=cal.getTime();
		beforeDateStr=sdf.format(date1);
		return beforeDateStr;
}
	
	
	
	
	/**
     * <p>
     *    字符串转换成日期格式
     * </P>
     * @return Date
     */
	public static Date StringTODate(String str) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = null;
        try {
                date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return date;
}
	
	/**
	 * 
	 * @author:qidp
	 * @time:2012-8-2
	 * @reason:计算两个日期差几天，也可比较两个日期谁在前，谁在后
	 * @param:只支持yyyyMMdd格式
	 * @throws Exception 
	 * @return：int 如果firstDate在secondDate之前，返回一个负整数；反之返回正整数
	 */
	public static int getDiffBetweenTwoDate(String firstDate,String secondDate) throws Exception {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");//计算两天之差
		Date date1=null;
		Date date2=null;
		int cha=0;
		try {
			date1 = myFormatter.parse(firstDate);//起始日期
			date2 = myFormatter.parse(secondDate);//终止日期
			long  seconds=date1.getTime()-date2.getTime();//起始日期-终止日期=毫秒 
		    cha=(int)(seconds/(24*60*60*1000));//再除以每天多少毫秒(24*60*60*1000) ＝差几天
		} catch (Exception e) {
			throw e;
		} 
		return cha;
	}
	
	/**
	 * 转换日期时间戳
	 * @param timestamp
	 * @return
	 */
	public static java.sql.Date getSqlDateWithTime(java.sql.Timestamp timestamp) {
		java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
//		System.out.println(sqlDate.getTime() == timestamp.getTime());
		return sqlDate;
	}
}
