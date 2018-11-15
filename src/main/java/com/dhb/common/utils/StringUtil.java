package com.dhb.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class StringUtil {
	public static boolean isObjectEmpty(Object obj) {
		return null == obj
				|| ((obj instanceof String) && isStringEmpty((String) obj));
	}

	public static boolean isStringEmpty(String str) {
		return null == str || str.trim().length() == 0;
	}

	public static String contactString(Object value1, Object value2) {
		StringBuffer buffer = new StringBuffer();

		if (!isObjectEmpty(value1))
			buffer.append(value1);
		if (!isObjectEmpty(value2))
			buffer.append(value2);

		return buffer.toString();
	}

	public static String parseObjectToString(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof String) {
			return (String) object;
		} else {
			return object.toString();
		}
	}
	


	public static String buildString(Object... args) {
		if (args == null)
			return null;

		StringBuffer buffer = new StringBuffer();

		for (Object object : args) {
			if (object != null) {
				buffer.append(object);
			}
		}

		return buffer.toString();
	}

	public static BigDecimal parseBigDecimal(Object value) {

		if (value instanceof BigDecimal) {
			try {
				return (BigDecimal) value;
			} catch (Exception e) {
				return null;
			}
		} else {
			try {
				return new BigDecimal(value.toString());
			} catch (Exception e) {
				return null;
			}
		}

	}

	public static Integer parseInteger(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Integer) {
			try {
				return (Integer) value;
			} catch (Exception e) {
				return null;
			}
		} else {
			try {
				return new Integer(value.toString());
			} catch (Exception e) {
				return null;
			}
		}

	}

	public static Long parseLong(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Long) {
			try {
				return (Long) value;
			} catch (Exception e) {
				return null;
			}
		} else {
			try {
				return new Long(value.toString());
			} catch (Exception e) {
				return null;
			}
		}

	}

	public static String replace(String str, String replaced, String replaceWith) {
		if (str == null) {
			return null;
		}
		if (replaced == null || replaceWith == null) {
			return str;
		}
		StringBuffer buf = new StringBuffer();

		int pos = str.indexOf(replaced);
		if (pos > -1) {
			String left = str.substring(0, pos);
			String right = str.substring(pos + replaced.length());
			buf.append(left);
			buf.append(replaceWith);
			buf.append(replace(right, replaced, replaceWith));
		} else {
			buf.append(str);
		}

		return buf.toString();
	}

	public final static boolean toBoolean(String str) {
		return toBoolean(str, false);
	}

	public final static boolean toBoolean(String str, boolean defaultValue) {
		if (isStringEmpty(str)) {
			return defaultValue;
		} else
			return new Boolean(str.trim()).booleanValue();

	}

	public static final int toInt(String str) {
		return toInt(str, 0);
	}

	public static final int toInt(String str, int defaultValue) {
		if (isStringEmpty(str)) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(str.trim());
		} catch (Throwable t) {
			return defaultValue;
		}
	}

	/**
	 * 将字符串填充到len长,不足len长前面加0,超过截断
	 * 
	 * @param str
	 * @param len
	 *            是指字节长度
	 * @return
	 */
	public static String formatLeftStr(Object object, int len) {
		String str = (object == null) ? "" : object.toString().trim();
		while (str.getBytes().length > len) {
			str = str.substring(0, str.length() - 1);
		}

		int strLen = str.getBytes().length;

		str = repeat("0", len - strLen) + str;
		return str;
	}

	/**
	 * 将字符串填充到len长,不足len长前面加指定,超过则从右侧开始 截断
	 * 
	 * @param str
	 * @param len
	 *            是指字节长度
	 * @param padChar
	 *            填充字符 为空时不填充
	 * @return
	 */
	public static String formatRightStr(Object object, int len, String padChar) {
		String str = (object == null) ? "" : object.toString().trim();
		String tmpstr = "13";
		// while (tmpstr.getBytes().length > len) {
		if (str.getBytes().length > len)
			str = str.substring(str.length() - len, str.length());

		// }
		int strLen = str.getBytes().length;
		if (padChar == null) {
			str = str;
		} else {
			str = repeat(padChar, len - strLen) + str;
		}
		return str;
	}

	public static String repeat(String str, int num) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < num; i++) {
			buf.append(str);
		}
		return buf.toString();
	}

	public static String BigDel2Str(BigDecimal bigd) {
		return bigd.toPlainString().indexOf('.') != -1 ? bigd.toPlainString()
				.substring(0, bigd.toPlainString().indexOf('.')) : bigd
				.toPlainString().substring(0, bigd.toPlainString().length());
	}
	
	public static String byteToString(byte[] in) throws Exception{  
		InputStream is=null;
		try {
			is = byteToInputStream(in);  
        return InputStreamTOString(is);  
		} finally {
			try {
				if(is!=null){
					is.close();
				}
			} catch (IOException ex) {
				throw new Exception("transaction error",ex);
			}
		}
    } 
	
	public static InputStream byteToInputStream(byte[] in) throws Exception{  
        ByteArrayInputStream is = new ByteArrayInputStream(in);  
        return is;  
    } 
	
	public static String InputStreamTOString(InputStream in) throws Exception{  

        ByteArrayOutputStream outStream = null;  
		try {
			outStream = new ByteArrayOutputStream();  
	        byte[] data = new byte[4096];  
	        int count = -1;  
	        while((count = in.read(data,0,4096)) != -1)  
	            outStream.write(data, 0, count);  
	        return new String(outStream.toByteArray(),"ISO-8859-1");  
		} finally {
			try {
				if(outStream!=null){
					outStream.close();
				}
			} catch (IOException ex) {
				throw new Exception("transaction error",ex);
			}
		}
    }
	
	public static String toStringAndTrim(Object object) {
		if (object == null) {
			return "";
		} else {
			return object.toString().trim();
		}
	}
	public static String todefaultzero(String object) {
		if (object == null||"".equals(object)) {
			return "0";
		} else {
			return object;
		}
	}
	
	/**
	 * 把二进制的串转化为十六进制的字符串
	 * Creation date: (00-6-9 17:06:35)
	 * @return java.lang.String
	 * @param inbuf byte[]
	 */
	public static String byteToHex(byte[] inbuf) {

		int i;
		String byteStr;
		StringBuffer strBuf = new StringBuffer();

		for (i = 0; i < inbuf.length; i++) {

			byteStr = Integer.toHexString(inbuf[i] & 0x00ff);
			if (byteStr.length() != 2) {
				strBuf.append('0').append(byteStr);
			} else {
				strBuf.append(byteStr);
			}

		}

		return new String(strBuf);
	}

	
	private static int hisDataTime = 0;//历史数据转移日期
	private static int unionpayHisDataTime = 0;//历史数据转移日期
	
	/**
	 *	根据分隔符将字符串分解, "\"(ascii 92)为转义符
	 *	"\"(ascii 92)为转义符.如“abc,cdef,a\,b”分隔后为三项[abc][cdef][a,b]
	 *	@param	strSource	字符串
	 *	@param	delimiter	分隔符
	 *	@return	分割的字符串数组；失败，返回null
	 */
	static public String[] strSplit(String strSource,String delimiter)
	{
		String str=null;
		int intPos=0;
		Vector vector = new Vector();
		String[]  strRet = new String[1];

		//校验输入参数
		if (strSource == null) return (new String[0]);
		if (delimiter == null) return (new String[0]);
		if ((strSource.trim()).equals("")) return (new String[0]);
		//
		intPos = strSource.indexOf(delimiter);
		String strTemp = "";
		while (intPos != -1)
		{
			//判断是否为转义符
			if(intPos!=0)
			{
				if (strSource.substring(intPos-1,intPos).equals("\\"))
				{
					strTemp = strTemp + strSource.substring(0,intPos-1)+delimiter;
					strSource =strSource.substring(intPos+delimiter.length());
					intPos = strSource.indexOf(delimiter);
					continue;
				}
			}
			//非转义符
			str = strTemp.equals("")?strSource.substring(0,intPos).trim():strTemp+strSource.substring(0,intPos).trim();
			strSource = strSource.substring(intPos+delimiter.length());
			vector.addElement(str);
			strTemp= "";
			intPos = strSource.indexOf(delimiter);
		}
		vector.addElement(strSource.trim());

		strRet = new String[vector.size()];
		for (int i = 0; i < vector.size(); i++) {
				strRet[i] = (String)vector.elementAt(i);
			}
		return strRet;
	}	
	/**   
     * 字符串按字节截取   
     * @param str 原字符   
     * @param len 截取长度   
     * @return String   
     * @author kinglong   
     * @since 2006.07.20   
     */      
    public static String splitString(String str, int len) {      
           return splitString(str, len, "");      
     }      
    
    /**   
      * 字符串按字节截取   
      * @param str 原字符   
      * @param len 截取长度   
      * @param elide 省略符   
      * @return String   
      * @author kinglong   
      * @since 2006.07.20   
      */      
     public static String splitString(String str,int len,String elide) {      
            if (str == null) {      
                   return "";      
             }      
            byte[] strByte = str.getBytes();      
            int strLen = strByte.length;      
            //int elideLen = (elide.trim().length() == 0) ? 0 : elide.getBytes().length;      
            if (len >= strLen || len < 1) {      
                   return str;      
             }      
           /* if (len - elideLen > 0) {     
                    len = len - elideLen;     
             }  */    
            int count = 0;      
            for (int i = 0; i < len; i++) {      
                   int value = (int) strByte[i];      
                   if (value < 0) {      
                           count++;      
                    }      
             }      
            if (count % 2 != 0) {      
                    len = (len == 1) ? len + 1 : len - 1;      
             }      
            return new String(strByte, 0, len) + elide.trim();      
      } 

	
	/**
	 * added by qidp
	 * 根据上送来的yyyymmddhhmmss格式日期时间，转换为yyyy年mm月dd日 hh:mm:ss
	 * @param dt
	 * @return
	 * @throws ParseException 
	 */
	public static String getCHNdt(String dt) throws ParseException{
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date df=sdf1.parse(dt);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		String date = sdf2.format(df);//得到带时分秒的日期
		return date;
	}
	
	/**
	 * added by qidp
	 * 根据上送来的yyyymmddhhmmss格式日期时间，转换为yyyy年mm月dd日 hh:mm:ss
	 * @param dt
	 * @return
	 * @throws ParseException 
	 */
	public static String getCHNdt2(String dt) throws ParseException{
		SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyyHH:mm:ss");
		Date df=sdf1.parse(dt);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf2.format(df);//得到带时分秒的日期
		return date;
	}
	
	/**
	 * added by qidp
	 * 根据上送来的yyyymmddhhmmss格式日期时间
	 * @param dt-需要被转换的值，format-需要转换成的格式，例如：ddMMyyyy、HH:mm:ss
	 * @return
	 * @throws ParseException 
	 */
	public static String getUSdt(String dt,String format) throws ParseException{
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date df=sdf1.parse(dt);
		SimpleDateFormat sdf2 = new SimpleDateFormat(format);
		String date = sdf2.format(df);//得到带时分秒的日期
		return date;
	}
	
	/**
	 * added by qidp
	 * 根据上送来的format1格式日期时间转换成format2的格式
	 * @param dt-需要被转换的值，format2-需要转换成的格式，例如：ddMMyyyy、HH:mm:ss
	 * @return
	 * @throws ParseException 
	 */
	public static String getUSdt2(String dt,String format1,String format2) throws ParseException{
		SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
		Date df=sdf1.parse(dt);
		SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
		String date = sdf2.format(df);//得到带时分秒的日期
		return date;
	}
	
	/**
	 * 
	 * @author:qidp
	 * @reason:得到系统当前时间
	 * @param:默认时间格式yyyyMMddHHmmss
	 * @return:
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf1.format(new Date());//得到带时分秒的日期
		return date;
	}
	/**
	 * 
	 * @author:qidp
	 * @reason:得到系统当前时间
	 * @param:默认时间格式yyyyMMdd
	 * @return:
	 */
	public static String getCurrentTime1() {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		String date = sdf1.format(new Date());//得到日期
		return date;
	}
	/**
	 * 
	 * @author:qidp
	 * @reason:得到系统当前时间
	 * @param:输入需要的时间日期格式
	 * 常用格式 :
	 * 		yyyy-MM-dd HH:mm:ss
	 * 		yyyyMMdd
	 * 		HHmmss
	 * @return:
	 */
	public static String getCurrentTime(String format) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(format);
		String date = sdf1.format(new Date());//得到带时分秒的日期
		return date;
	}
	
	
	/**
	 * 
	 * @author:lijq
	 * @reason:得到系统当前时间
	 * @param:默认时间格式yyyyMMddHHmmsssss
	 * @return:
	 */
	public static String getCurrentTimeMillisecond() {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String date = sdf1.format(new Date());//得到日期
		return date;
	}
	
	/**
	* 获取e商宝行内流水号For Beai added by kangqiong
	 * @return 交易流水号（7位） + 渠道号 + 外围系统
	 */
//	public static String getESERNO(String eFlowNo){
//		String qudaono="12";
//		String xitongno="016";
//		String dt=eFlowNo.substring(13, 20);
//		return dt+qudaono+xitongno;
//	}
	/**
	 * 获取e商宝行内流水号For 前置 added by kangqiong
	 * @return 交易流水号（7位） + 渠道号 + 外围系统
	 */
//	public static String getESERNONEW(){
//		String sn="";
//		String zero="";
//		String qudaono="12";
//		String xitongno="016";
//		String eflowno="";
//		String dt=StringUtil.getCurrentTime();
//		Connection conn =null;
//		DBExec exec = null;
//		try{
//			conn =APSystem.getConnection();
//			exec =  new DBExec();
//			String sql="select esb_seq.nextVal as sn from dual";
//			exec.setConnection(conn);
//			exec.prepareStatement(sql);
//			exec.executeQuery();
//			while (exec.resultSet.next()) {
//				sn = StringUtil.Trim(exec.getString("sn"));
//			}
//			exec.close();
//			int len=sn.length();
//			for(int i=0;i<6-len;i++){
//				zero+="0";
//			}
//		}catch (Exception e) {
//			LOG.error("获取E商宝流水号异常：", e);
//		}finally{
//			if(exec != null)exec.close();
//			APSystem.returnConnection(conn);	
//		}
//		eflowno=dt+zero+sn;
//		
//		return eflowno.substring(13, 20)+qudaono+xitongno;
//	}
	
	/**
	 * 右补空格
	 * @param field 字段
	 * @param len	字段最大长度
	 * @return	组合后的字段
	 * @throws Exception
	 */
	public static String fixField(String field,int len) throws Exception{
		field=StringUtil.Trim(field);
		byte[] bs=field.getBytes();
		if(bs.length>len)
			throw new Exception("字段本身已大于其最大长度！");
		StringBuffer ff =new StringBuffer("");
		int lp=len-bs.length;
		for(int i=0;i<lp;i++){
			ff.append(" ");
		}
		String res=field+ff;
		return res;
	}
	/**
	 * 右补空格_不去左右空格
	 * @param field 字段
	 * @param len	字段最大长度
	 * @return	组合后的字段
	 * @throws Exception
	 */
	public static String fixField1(String field,int len) throws Exception{
		byte[] bs=field.getBytes();
		if(bs.length>len)
			throw new Exception("字段本身已大于其最大长度！");
		StringBuffer ff =new StringBuffer("");
		int lp=len-bs.length;
		for(int i=0;i<lp;i++){
			ff.append(" ");
		}
		String res=field+ff;
		return res;
	}
	/**
	 * 左补字符
	 * @param field 字段
	 * @param len	字段最大长度
	 * @return	组合后的字段
	 * @throws Exception
	 */
	public static String leftfixField(String field,int len,String setchar) throws Exception{
		field=StringUtil.Trim(field);
		byte[] bs=field.getBytes();
		if(bs.length>len)
			throw new Exception("字段本身已大于其最大长度！");
		StringBuffer ff =new StringBuffer("");
		int lp=len-bs.length;
		for(int i=0;i<lp;i++){
			ff.append(setchar);
		}
		String res=ff+field;
		return res;
	}
	
	/**
	 * 去除字符串两边空格
	 * @param str
	 * @return
	 */
	public static String Trim(Object str){
		return str == null ? "" : str.toString().trim();
	}
	/**
	 * 数字化格式函数
	 * @param  number: 格式化前数字;
	 * @param  decimalDigits: 小数位;
	 * @return: 以三位一组逗号分隔字符串;
	 */
	public static String format(double number, int decimalDigits) {
		if (number == 0d) number = 0d;
		
		boolean flag=false;
		if(decimalDigits < 0) {
//			LOG.debug("小数位数不能小于0");
			return "";
		}
		
		String pattern = "###,###,###,###,###,###";
		if(decimalDigits > 0) {
			flag=true;
			pattern += ".";
			for(int i=0;i<decimalDigits;i++) {
				pattern += "0";
			}
		}
		
		DecimalFormat df = new DecimalFormat(pattern);
		if (number <= -1d){
			return df.format(number);
		}else if (number > -1d && number < 0d){
			return "-0"+df.format(number).substring(1);
		}else if (number >= 0d && number < 1d){
			if(flag==true){
				return "0"+df.format(number);
			}else{
				return df.format(number);
			}
		}else{
			return df.format(number);
		}
	}
	
	/**
	 * 数字格式化函数
	 * @param  number: 格式化前的字符串(是一个数字);
	 * @param  decimalDigits: 小数位数;
	 * @return: 三位一组以逗号分割的字符串,如果为null,或空串或只有空格的字符串,返回空串;
	 */
	public static String format(String s, int decimalDigits) {
		if(s == null) return "";
		s = s.trim();
		if(s.equals("&nbsp;")) return "";
		if(s.length() == 0) return "";
		double number = Double.parseDouble(s);
		return format(number,decimalDigits);
	}
	
	/**
	 * 日期格式化函数
	 * @param  date: 格式化前的字符串,长度必必须8,且是YYYYMMDD格式;
	 *         ??    如果为null,或空串或只有空格的字符串,返回空串;
	 *         ??    如果长度是不为8的字符串,返回空串;
	 * @return 格式: YYYY/MM/DD 的字符串;
	 */
	public static String formatDate(String date) {
		if(date == null) return "";
		date = date.trim();
		if(date.equals("&nbsp;")) return "";
		if (date.length() == 0 || date.length() != 8) return date;
		date = date.substring(0,4) + "/" + date.substring(4,6) + "/" + date.substring(6,8);
		return date;
	}
	/**
	 * YYYYMMDDHHmmss  ===> YYYY/MM/DD HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatDateTime(String date) {
		if(date == null) return "";
		date = date.trim();
		if(date.equals("&nbsp;")) return "";
		if (date.length() == 0 || date.length() != 14) return date;
		date = date.substring(0,4) + "/" + date.substring(4,6) 
				+ "/" + date.substring(6,8)
				+" "+date.substring(8,10)
				+":"+date.substring(10,12)
				+":"+date.substring(12,14);
		return date;
	}
	public static double parseDouble(String str){
		try{
			return Double.parseDouble(str);
		}catch(Exception e){
			return 0d;
		}
	}
	
	public static String formats(double number){
	    String str = "0.00";
	    
	    if (number == 0d) number = 0d;
		
	    String num = Double.toString(number); 
	    int i = num.indexOf('.');
	    if (i <= 0){
	        str = format(number, 2);
	    }else{
	        int j = num.length();
	        int k = num.substring(i+1,j).length();
	        if (k <= 2)
	            str = format(number, 2);
	        else
	            str = format(number, 3);
	    }
	    return str;
	}
	
	public static String formatDouble(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
			"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return String.valueOf(b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue());
		
	}
	
	
	
	/**
	 * null or "" =====>&nbsp;
	 * @param str
	 * @return
	 */
	public static String fomatStrForJsp(String str){		
		if( str==null || str.trim().equals("")) return "&nbsp;";
		return str;
	}
	
	/**
	 * 
	 * @param number:以科学技数法表示的double型数值
	 * @param decimalDigits:显示的小数位数
	 * @return 以文本格式显示示double型值
	 */
	public static String formatEDouble(double number, int decimalDigits) {
		if (number == 0d) number = 0d;
		
		boolean flag=false;
		if(decimalDigits < 0) {
//			LOG.debug("小数位数不能小于0.");
			return "";
		}
		
		String pattern = "##################";
		if(decimalDigits > 0) {
			flag=true;
			pattern += ".";
			for(int i=0;i<decimalDigits;i++) {
				pattern += "0";
			}
		}
		
		DecimalFormat df = new DecimalFormat(pattern);
		if (number <= -1d){
			return df.format(number);
		}else if (number > -1d && number < 0d){
			return "-0"+df.format(number).substring(1);
		}else if (number >= 0d && number < 1d){
			if(flag==true){
				return "0"+df.format(number);
			}else{
				return df.format(number);
			}
		}else{
			return df.format(number);
		}
	}
	
	public static boolean IsEmptyStr(String s) {
		int i = 0;
		if (s == null)
			return true;
		if (s.length() == 0)
			return true;
		i = s.length();
		for (int j = 0; j < i; j++) {
			char c = s.charAt(j);
			if (c != '\t' && c != '\n' && c != '\r' && c != ' ')
				return false;
		}
		return true;
	}
	/**
	 * 描述：判断开始日期和结束日期是否跨过一个月
	 * @author sunjinfu
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static boolean isDateValid(String startDate,String endDate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		long t1 = sdf.parse(startDate).getTime();
		long t2 = sdf.parse(endDate).getTime();
		long oneMonth = 31*24*60*60*1000l;
		if((t2 - t1) > oneMonth){
			return true;
		}
		return false;
	}
	/**
	 * CR1960新增快捷支付签约查询日期验证为32天
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static boolean isDateValid32(String startDate,String endDate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		long t1 = sdf.parse(startDate).getTime();
		long t2 = sdf.parse(endDate).getTime();
		long oneMonth = 32*24*60*60*1000l;
		if((t2 - t1)+1 > oneMonth){
			return true;
		}
		return false;
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
//			LOG.error("计算两个日期差几天异常：", e);
			throw e;
		} 
		return cha;
	}
	
	/**
	 * 
	 * @author:qidp
	 * @time:2012-8-2
	 * @reason:计算两个时间差多少秒
	 * @param:只支持yyyyMMddHHmmss格式
	 * @return：int 秒数
	 */
	public static long getDiffMinBetweenTwoDate(String firstDate,String secondDate) throws Exception {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMddHHmmss");//计算两天之差
		Date date1=null;
		Date date2=null;
		long cha=0;
		try {
			date1 = myFormatter.parse(firstDate);//起始日期
			date2 = myFormatter.parse(secondDate);//终止日期
			long  seconds=date1.getTime()-date2.getTime();//起始日期-终止日期=毫秒 
		    cha=(long)(seconds/(1000));
		} catch (Exception e) {
//			LOG.error("计算两个时间差多少秒异常：", e);
			throw e;
		} 
		return cha;
	}
	
	
	/**
	 * 
	 * @time:2014-06-10
	 * @reason:计算两个时间差多少毫秒
	 * @param:只支持yyyyMMddHHmmssSSS格式
	 * @return：long 毫秒
	 */
	public static long getDiffMinBetweenTwoMilliSecond(String firstDate,String secondDate) throws Exception {
		if(firstDate.compareTo(secondDate)>=0)
			return 0l;
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");//计算两天之差
		Date date1=null;
		Date date2=null;
		long  seconds = 0l;
		try {
			date1 = myFormatter.parse(firstDate);//起始日期
			date2 = myFormatter.parse(secondDate);//终止日期
			seconds=date2.getTime()-date1.getTime();//毫秒 
		} catch (Exception e) {
			throw e;
		}	
		 return seconds;
	}

	
	/**
	 * 
	 * @param amt
	 * @return
	 */
	public static String getAmtForYuan(String amt){
		if("".equals(amt) || null == amt){
			return "0";
		}
		long temp = Long.parseLong(amt);
		amt = temp+"";
		if(amt.length() == 1){
			amt="0.0"+amt;
		}else if(amt.length() == 2){
			amt = "0." + amt ;
		}else if (amt.length()>=3){
			amt =amt.substring(0, amt.length()-2)+"."+amt.substring(amt.length()-2);
		}
		return amt;
	}
	/**
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d,int day){
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE)+day);
		return now.getTime();
	}
	
	/**
	 * 获取系统日期前一天
	 * @return
	 */
	public static String beforeSystemDate(){
		  Calendar cal = Calendar.getInstance();
	      cal.add(Calendar.DATE, -1);
	      SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
	      return myFormatter.format(cal.getTime());
	}
	
	/**
     * 
     * 
     * @param d
     * @param day
     * @return yyyyMMdd格式的日期
     */
    public static String getDateAfter2(Date d,int day){
            Calendar now = Calendar.getInstance();
            now.setTime(d);
            now.set(Calendar.DATE, now.get(Calendar.DATE)+day);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
            String date = sdf1.format(now.getTime());//得到日期
            return date;
    }
	
	/**
	 * 
	 * @author:wushaolin
	 * @reason:得到系统当前时间
	 * @param:默认时间格式yyyyMMddHHmmssSSS
	 * @return:
	 */
	public static String getCurrentTime3() {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String date = sdf1.format(new Date());//得到带时分秒的日期
		return date;
	}
	
	/**
	 * 
	 * @author:wushaolin
	 * @time:2013-10-12
	 * @reason:计算两个时间差多少秒
	 * @param:只支持yyyyMMddHHmmssSSS格式
	 * @return：int 秒数
	 */
	public static long getDiffMinBetweenTwoDate1(String startDate,String lastDate) throws Exception {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date1=null;
		Date date2=null;
		long  seconds=0;
		try {
			date1 = myFormatter.parse(startDate);//起始日期
			date2 = myFormatter.parse(lastDate);//终止日期
			seconds=date2.getTime()-date1.getTime();//date2-起始日期=毫秒 
		} catch (Exception e) {
//			LOG.error("计算两个时间差多少秒异常：", e);
			throw e;
		} 
		return seconds;
	}
	
	
	/**
	 * 如果date在当前日期与当期日期前推2天的范围内，返回true，范围之外返回false
	 * @param starDate 查询开始日期
	 * @param endDate  查询结束日期
	 * @return int 0--两天以外；1--代表两天以内； 2-- 开始日期在两天以内，结束日期在两天以外
	 */
	public static int checkQueryDate(String starDate,String endDate){
		int flag = 2;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DATE, now.get(Calendar.DATE)-2);
		int s_dif = sdf.format(now.getTime()).compareTo(endDate);
		if(s_dif>=0){
			//开始在两天以外，验证结束
			flag = 0;
		}else{
			//开始在两天以内，结束在两天以外
			int e_dif = sdf.format(now.getTime()).compareTo(starDate);
			if(e_dif>=0){
				flag = 2;
			}else{
				//开始在两天以内、结束在两天以内
				flag = 1;
			}
			
		}
		return flag;
	}
	/**
	 * 为文件内容中的每条记录加换行符\r\n
	 * @param fileContent 文件内容
	 * @return	组合后的文件内容
	 * @throws Exception
	 */
	public static Map<String, String> fixFileContent(String fileContent) throws Exception{
        Map <String,String> map=new HashMap <String ,String >();
        int count=0;
		StringBuffer ff =new StringBuffer();
		String[] note=fileContent.split("#");
		for(int i=0;i<note.length;i++){
			ff.append(note[i]);
			ff.append("\r\n");
			count++;
		}
		String res=ff.toString();
		String notes=Integer.toString(count);
		map.put("notes", notes);
		map.put("res",res);
		return map;
	}
	
	/*
	 * add by 赵玉帅 2015-05-16
	 * 获得两个数字字符串中较小的字符串  若遇到异常情况则返回-1
	 */
	public static String getMinNumString(String no1 , String no2) {
		try {
			if(IsEmptyStr(no1) && IsEmptyStr(no2)) return "-1" ;
			if(IsEmptyStr(no1)) return no2;
			if(IsEmptyStr(no2)) return no1;
			long n1 = Long.parseLong(no1);
			long n2 = Long.parseLong(no2);
			if(n1>n2) {
				return no2;
			} else {
				return no1;
			}
		} catch (NumberFormatException e) {
			return "-1";
		}
	}
	
	/*
	 * add by zq2015-05-27
	 * 比较两数大小
	 */
	public static Boolean compareStr(String no1 , String no2) {
		try {
			if(IsEmptyStr(no1) && IsEmptyStr(no2)) return false ;
			if(IsEmptyStr(no1)) return false;
			if(IsEmptyStr(no2)) return false;
			int n1 = Integer.parseInt(no1);
			int n2 = Integer.parseInt(no2);
			if(n1>n2) {
				System.out.println("！！！！！！！！！！！！交易金额大于大额设置");
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}
	/**
	 * 判断开始日期和结束日期是否跨过30天
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static boolean isMobDateValid(String startDate,String endDate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		long t1 = sdf.parse(startDate).getTime();
		long t2 = sdf.parse(endDate).getTime();
		long oneMonth = 30*24*60*60*1000l;
		if((t2 - t1) > oneMonth){
			return true;
		}
		return false;
	}
	
	public static String getCurrentDateTime(String patten){
		Calendar cal=Calendar.getInstance();
		Date date=cal.getTime();
		return new SimpleDateFormat(patten).format(date);
	}
	
	public static String MultiplyHundred(Object amount){
		if(amount == null){
			return "";
		}
		String tmpStr = ((new BigDecimal(amount.toString()))).multiply(BigDecimal.valueOf(100)).toString();
		tmpStr = tmpStr.indexOf(".") == -1?tmpStr:tmpStr.substring(0,tmpStr.indexOf("."));
		
		String tmp = "000000000000";
		tmpStr = tmp + tmpStr;
		return tmpStr.substring(tmpStr.length()-12);
	}
	
	
	
}
