/**
 * @(#)CommonUtil.java
 *
 * @version 1.0    2004-12-10
 * @author 김승일
 *
 * Copyright 2004-2005 by Daul Soft, Inc. All rights reserved.
 */

package com.daulsoft.ets.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.daulsoft.common.utility.cipher.Blowfish;
import kr.daulsoft.neotest5.common.CNTException;

import com.daulsoft.core.EzSet;
import com.daulsoft.core.env.Config;
import com.daulsoft.core.env.ServerContext;
import com.daulsoft.ets.ConstDef;
import com.daulsoft.file.FileUtil;
import com.daulsoft.neotest.NT5Constant;
import com.daulsoft.sql.ResultObject;
import com.daulsoft.util.CodeCombo;
import com.daulsoft.util.KeyGenerator;
import com.daulsoft.util.StringUtil;
import com.daulsoft.util.lang.a;
import com.daulsoft.util.lang.ezDate;
import com.daulsoft.util.lang.ezString;
import com.daulsoft.util.log.Syslog;


public class CommonUtil{
//미탐 - 165	[샘플] 함수(메소드)의 주석에 'Contents'을 반드시 입력
    /**
	 * <PRE>
	 * 문자열로 부터 주어진 char가 위치한 마지막 위치 이후 문자열을 구한다.
	 * 예로 파일 확장자를 구할때.
	 * </PRE>
	 *
	 * @param String strFileName
	 * @param String fildChar
	 * @return	String
	 */
    public static String extendDivision( String strFileName, String findChar ){

		String strExtend="";
		if( strExtend == null || findChar == null ) { return strExtend; }
		int intFileLen = strFileName.length();
		int intdot = strFileName.lastIndexOf(findChar);
		if( intdot >= 0 ) { strExtend = strFileName.substring( intdot + 1, intFileLen);}
  		return strExtend;
	}
    /**
	 * Contents : 
	 * <PRE>
	 * 업로드된 파일 삭제
	 * default노드값에 해당하는 디렉토리에 지정된 파일을 삭제한다.
	 * </PRE>
	 * @param String sAttachNo
	 * @return
	 */
	public static void deleteTempFile(String sAttachNo){

		deleteFile( sAttachNo, "/properties/etc/uploadPath/default" );
	}
    /**
     * Contents : 
	 * <PRE>
	 * 업로드된 파일 삭제
	 * 인자로 받은 path정보가 있는 노드값에 해당하는 디렉토리에 지정된 파일을 삭제한다.
	 * </PRE>
	 * @param String sAttachNo
	 * @return
	 */
	public static void deleteUserFile( String sAttachNo ){

		deleteFile( sAttachNo, "/properties/etc/uploadPath/USER_FILE" );
	}
	/**
	 * Contents : 
	 * <PRE>
	 * 업로드된 파일 삭제
	 * 인자로 받은 path정보가 있는 노드값에 해당하는 디렉토리에 지정된 파일을 삭제한다.
	 * </PRE>
	 * @param String sAttachNo
	 * @param pathNodeNm ezWorks.xml에 지정된 파일경로값을 갖는 node명.
	 * @return
	 */
	public static void deleteFile( String sAttachNo, String nodePath ){

		// 서버 업로드 경로 지정
    	String sServerPath = (String)((Config)ServerContext.getInstance()
				.get(ServerContext.CTX_CONFIG))
				.getString(nodePath);
    	if( FileUtil.fileExist(sServerPath + sAttachNo) ) { FileUtil.delete(sServerPath + sAttachNo);}

	}
	/**
	 * Contents : 
	 * <PRE>
	 * 업로드된 파일 삭제
	 * 인자로 받은 path정보가 있는 노드값에 해당하는 디렉토리에 지정된 파일을 삭제한다.
	 * </PRE>
	 * @param String sAttachNo
	 * @param pathNodeNm ezWorks.xml에 지정된 파일경로값을 갖는 node명.
	 * @return
	 */
	public static void deleteFiles(String sFilePath){
		if( FileUtil.fileExist(sFilePath) ) { FileUtil.delete(sFilePath); }

	}
	/**
	 * Contents : 
	 * 파일 이동.
	 * default노드에 지정된 디렉토리에서 USER_FILE노드에 지정된 디렉토리로 파일을 옮긴다.
	 *
	 * @param fromFileNm
	 * @param toFileNm
	 */
	public static void transFile( String fromFileNm, String toFileNm ){

		transFile( fromFileNm, toFileNm, "/properties/etc/uploadPath/USER_FILE" );
	}
	/**
	 * Contents : 
	 * 파일 이동.
	 * default노드에 지정된 디렉토리에서 인자로 받은 node명이 갖는 디렉토리로 파일을 옮긴다.
	 *
	 * @param fromFileNm
	 * @param toFileNm
	 * @param pathNodeNm 파일이 옮겨질 디렉토리 정보를 갖는 node명.
	 * @param addDirPath 기본 Path정보 뒤에 추가로 붙을 디렉토리 경로.
	 */
	public static void transFile( String fromFileNm, String toFileNm, String nodePath ){

		String savePath = (String)((Config)ServerContext.getInstance()
				.get(ServerContext.CTX_CONFIG))
				.getString(nodePath);
		
		String tempPath = (String)((Config)ServerContext.getInstance()
				.get(ServerContext.CTX_CONFIG))
				.getString("/properties/etc/uploadPath/default");
		
		transFile(fromFileNm, tempPath, toFileNm, savePath);
	}

	public static void transFile( String fromFileNm, String fromFilePath, String toFileNm, String toFilePath )
	{
		File file = new File(toFilePath);
		if( !file.exists() ) { file.mkdirs(); } // 해당 디렉토리가 없으면 생성한다.
		if( FileUtil.fileExist(toFilePath + toFileNm) ) { FileUtil.delete(toFilePath + toFileNm); }

        // 파일을 옮기고, 정상 처리 된 경우 temp에 있는 첨부파일을 삭제한다.
        if( FileUtil.move(fromFilePath + fromFileNm, toFilePath + toFileNm) ){
        	FileUtil.delete(fromFilePath + fromFileNm);
        }
	}
	/**
	 * Contents : 
	 * <PRE>
	 * GET방식으로 전송시 특수문자에 대한 처리를 위한 메소드.
	 *
	 * </PRE>
	 * @param String strFileName
	 * @param String fildChar
	 * @return	String
	 */
	public String setReplace(String content)
    {
		String newContent = "";

		newContent = ezString.replace( content, "\\n", "\n");
		newContent = ezString.replace( newContent, "percent", "%");
		newContent = ezString.replace( newContent, "sharp", "#");
		newContent = ezString.replace( newContent, "and_abbreviated", "&");

		return StringUtil.toDBChar(newContent);
    }
	/**
	 * Contents : 
     * 111,112,113 형태로 들오온 String을 String[]로 반환
     *
     * @param id
     * @return
     */
    public static ArrayList stringToArray(String id, String divChar ){
        // 문항-평가지 관계 테이블에 입력
        StringTokenizer st = new StringTokenizer(id, divChar);
        String tempID = null;
        ArrayList al = new ArrayList();
        //오탐 - 45110190	[SP] 정수 오버플로우 
        //st.hasMoreTokens()는 return type이 boolean이기 때문에 정수 오버 플로우가 날수 없다 - ageha 
        for(int i =0; st.hasMoreTokens(); i++){
        	tempID = st.nextToken();
        	al.add(i,tempID);
        }
        return al;
    }
    /**
     * Contents : 
     * ArrayList에 담긴 내용을 111,112,113 형태로 전환.
     *
     * @param al
     * @return
     */
    public static String arrayToString( String[] arr, String divChar ){
    	StringBuffer tempId = new StringBuffer();
    	int len = arr.length;
    	for( int i = 0 ; i < len ; i++ )
    	{
    		if(i != 0) { tempId.append(divChar); }
    		tempId.append(arr[i]);
    	}
    	return tempId.toString();
    }
    /**
     * Contents : 
	 * 문자열 두개를 비교하여 정렬시 우선되는 값 여부를 체크한다.
	 *
	 * @param diff1
	 * @param diff2
	 * @return 첫번째 인자가 우선되면 true, 아니면 false
	 */
	public static boolean diffString(String diff1, String diff2){
		if( diff1==null || "".equals(diff1) || diff2==null || "".equals(diff2) ) { return false; }

		char[] arrCh1 = diff1.toCharArray();
		char[] arrCh2 = diff2.toCharArray();

		int len1 = arrCh1.length;
		int len2 = arrCh2.length;
		int len = len1 > len2 ? len2 : len1;

		for( int i = 0 ; i < len ; i++ ){
			if( arrCh1[i] > arrCh2[i] ) { return false; }
			else if( arrCh1[i] < arrCh2[i] ) { return true; }
		}

		return len1 > len2 ? false : true;
	}
	/**
	 * Contents : 
	 * 오늘 년도를  구한다.
	 */
	public static int getYear()
	{
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	/**
	 * Contents : 
	 * 오늘 날짜를 구한다.
	 * @throws a 
	 */
	public static String getDate( String dateFormat ) throws a, NullPointerException{
		ezDate currDate = ezDate.getInstance();
		String today = "";
		today = currDate.getDate(dateFormat);
		return today;
	}
	/**
	 * Contents : 
	 * KeyGenerator를 통한 랜덤한 키를 구한다.
	 *
	 * @return
	 * @throws Throwable 
	 */
	public static String getKey() throws Throwable{
		String key = "";
		//정탐 - 4306	Catch 문 내의 미처리 Exception 금지
		KeyGenerator keyGen = KeyGenerator.getInstance();
		key = keyGen.next();
		return key;
	}
	/**
	 * Contents : 
	 * KeyGenerator를 통한 순차적인 키를 구한다.
	 *
	 * @return
	 * @throws Throwable 
	 */
	public static String getSequence() throws Throwable {
		String key = "";
		//정탐 - 4306	Catch 문 내의 미처리 Exception 금지
		KeyGenerator keyGen = KeyGenerator.getInstance();
		key = keyGen.sequence();
		return key;
	}
	/**
	 * Contents : 
	 * 문자열이 null이거나 empty string인지 확인한다.
	 *
	 * @param str 확인할 문자열
	 * @return null이거나 empty string이면 true, 아니면 false를 return한다.
	 */
	public static boolean isEmpty( String str ){

		boolean bool = false;
		if( str == null || "".equals(str) ) { bool = true; }

		return bool;
	}
	/**
	 * Contents : 
	 * HTML로 작성된 문자열 중 '>', '<'를 '&lt;', '&gt;'로 변환한다.
	 *
	 * @param html
	 * @return
	 */
	public static String tagToString( String html, boolean enterProc ){

		String retValue = html.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll(" ", "&nbsp;");
		if( enterProc ) { retValue = retValue.replaceAll("\n", "<br>"); }

		return retValue;
	}
	/**
	 * Contents : 
	 * 쿠키를 설정한다.
	 *
	 * @param response
	 * @param key
	 * @param value
	 * @throws Exception 
	 */
	public static void setCookie( HttpServletResponse response, String cookieDomain, String key, String value ) 
	    throws Exception
	{
		setCookie( response, cookieDomain, key, value, -1 );
	}
	/**
	 * Contents : 
	 * 유지시간을 가지는 쿠키를 설정한다.
	 * 쿠키의 키-값 모두 암호화 됨.
	 *
	 * @param response
	 * @param key
	 * @param value
	 * @param maxAge 쿠키를 유지할 시간[sec]. 0보다 작은 경우 셋팅을 하지 않는다.
	 * @throws Exception 
	 */
	public static void setCookie( HttpServletResponse response, String domain, String key, String value, int maxAge ) 
	       throws Exception
	{
		byte[] value_byte = value.getBytes();
		byte[] key_byte = key.getBytes();
		value = Blowfish.decryptBytes2String(value_byte);
		key = Blowfish.encryptBytes2String(key_byte);
		
		ArrayList alDomain = stringToArray(domain, ",");
		for( int i = 0 ; i < alDomain.size() ; i++ )
		{
			//정탐 - 11835	루프에서 객체의 인스턴스 생성 금지
			Cookie cookie = new Cookie(key, value);
			cookie.setDomain((String) alDomain.get(i));
			cookie.setPath("/");
			//정탐 - 45119316	[SP] 쿠키보안: 영속적인 쿠키
			//setMaxAge(유효시한 설정시 외부 입력값인 maxAge에 대한 검증 없이 setMaxAge를 하여 유효시한을 설정) 
			if( maxAge >= 0 ) { cookie.setMaxAge(maxAge); }
			//정탐 - 45110614	[SP] HTTPS 세션내에 보안속성없는 민감한 쿠키
			response.addCookie(cookie);
		}
	}
	/**
	 * Contents : 
	 * 쿠키에 저장된 정보 구한다.
	 *
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public static String getCookieValue( HttpServletRequest request, String key ) throws Exception
	{
		String retValue = "";
		String encKey = "";
		byte[] key_byte = key.getBytes();
		byte[] retValue_byte = retValue.getBytes();
		encKey = Blowfish.encryptBytes2String(key_byte);

		// 로그인 정보 조회.
		Cookie[] cookies = request.getCookies();
		int cookieCnt = 0;
		if( cookies != null ) { cookieCnt = cookies.length; }
		for( int i = 0 ; i < cookieCnt ; i++ )
		{			
			Cookie cookie = cookies[i];
			//정탐 - 45110807	[SP] 보호메커니즘을 우회할 수 있는 입력값 변조
			//Session이 아닌 cookie를 사용함으로써 사용자의 조작이 가능함.
			if( cookie.getName().equals(encKey) && cookie.getValue().length() > 0 )
			{
				retValue = cookie.getValue();
				//정탐 - 86	Loop 문 내에서 상수 할당 금지
				if( retValue == null ) { retValue = ""; }
				//정탐 - 11759	리터럴은 항상 비교문의 왼쪽에 배치
				else if( !retValue.equals("") )
				{
						retValue = Blowfish.decryptBytes2String(retValue_byte);
				}

				break;
			}
		}
		if( "".equals(retValue) )
		{
			HttpSession ses = request.getSession(true);
			if( ses != null )
			{
				retValue = (String) ses.getAttribute(key);
				if( retValue == null ) { retValue = ""; }
			}
		}

		return retValue;
	}
	/**
	 * Contents : 지정된 키의 Cookie를 삭제한다.
	 *
	 * @param response
	 * @param key
	 * @throws Exception 
	 */
	public static void deleteCookie( HttpServletResponse response, String cookieDomain, String key ) throws Exception
	{
		setCookie( response, cookieDomain, key, "", 0 );
	}
	/**
	 * Contents : Cookie 체크.
	 * @param request
	 * @throws Exception 
	 */
	public static void checkCookie( HttpServletRequest request ) throws Exception
	{
		// 로그인 정보 조회.
		Cookie[] cookies = request.getCookies();
		int cookieCnt = 0;
		if( cookies != null ) { cookieCnt = cookies.length; }
		for( int i = 0 ; i < cookieCnt ; i++ )
		{			
			Cookie cookie = cookies[i];
			String ckName = cookie.getName();
			String ckValue = cookie.getValue();
			byte[] ckName_byte = ckName.getBytes();
			byte[] ckValue_byte = ckValue.getBytes();
			
			//정탐 - 11759	리터럴은 항상 비교문의 왼쪽에 배치
			if( !ckValue.equals("") )
			{
				ckName = Blowfish.decryptBytes2String(ckName_byte);
				ckValue = Blowfish.decryptBytes2String(ckValue_byte);
			}
		}
	}
	/**
	 * Contents : 세션 타임아웃을 체크한다.
	 *
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean checkTime( long time1, long time2 )
	{
		long betweenMilliSec = time2 - time1;
		double betweenMin = betweenMilliSec / 1000 / 60;
		if( betweenMin < ConstDef.SESSION_TIMEOUT ) { return true; }
		//정탐 - 11715	If~Else 문에 중괄호 사용 권장
		else return false;
	}
	/**
	 * Contents : 디렉토리내의 파일복사
	 *
	 * @param sourceLocation 복사할 디렉토리
	 * @param targetLocation 타켓 디렉토리
	 * @throws IOException
	 */
	public static void copyDirectory(File sourceLocation , File targetLocation) throws IOException
	{
		if (sourceLocation.isDirectory())
		{
			if (!targetLocation.exists()) { targetLocation.mkdir(); }

		    String[] children = sourceLocation.list();
		    for (int i=0; i<children.length; i++)
		    {
				//정탐 - 11835	루프에서 객체의 인스턴스 생성 금지
		        copyDirectory(new File(sourceLocation, children[i]),
		    			//정탐 - 11835	루프에서 객체의 인스턴스 생성 금지
		                new File(targetLocation, children[i]));
		    }
		}
		else if (targetLocation.isDirectory())
		{
			//정탐 - 45110036	[SP] 절대 디렉토리 경로 조작
	        copyDirectory(sourceLocation, new File(targetLocation, sourceLocation.getName()));
		}
		else
		{
		    InputStream in = null;
		    OutputStream out = null;

		    try
		    {
			    in = new FileInputStream(sourceLocation);
			    out = new FileOutputStream(targetLocation);
			    
			    byte[] buf = new byte[1024];
			    int len;
			    //정탐 - 11733	연산에 대입문 사용 금지 
			    //while 조건안에 =으로 대입문이 있으므로 
			    while ((len = in.read(buf)) > 0)
			    {
			        out.write(buf, 0, len);
			    }
		    }
		    catch( IOException ioe )
		    {
		    	//정탐 - 11856	Exception의 재 Throw 금지
		    	throw ioe;
		    }finally{
		    	if(in !=null){
		    	in.close();
		    	}
		    	if(out !=null){
		    	out.close();
		    	}
		    }
		}
	}
	/**
	 * Contents : DB에 입력된 날짜 데이타를 기본형태로 변형한다.
	 * 입력한 날짜값이 empty인 경우 '&nbsp'값을 return.
	 *
	 * @param date
	 * @return
	 * @throws Throwable 
	 * @throws ParseException 
	 */
	public static String convertDate( String date ) throws ParseException, Throwable
	{
		//정탐 - 4306	Catch 문 내의 미처리 Exception 금지
		//convertDate() 메소드는 throws Throwable, ParseException 하고 있으므로 
		//try ~ Catch문에서 메소드를 호출해야 하며
		//Catch문에서 Exception에 대한 처리가 있어야 함
		return convertDate( date, "yyyyMMdd", "default" );
	}

	public static String convertDate( String date, String fromFmt, String toFrm ) 
	       throws Throwable, ParseException, a, NullPointerException
	{
		String retDate = "";
        try { 
		      if( !isEmpty(date) )
		         {
				    com.daulsoft.util.lang.ezDate currDate = com.daulsoft.util.lang.ezDate.getInstance();
       				retDate = currDate.convertString(date, fromFmt, toFrm);
		          }
        }
        catch (NullPointerException ne) {
        	Syslog.error(ne);
        }
        catch (a a) {
        	Syslog.error(a);
        }
        catch (ParseException pe) {
        	Syslog.error(pe);
        }

		return retDate;
	}
	/**
	 * Contents : 문자열에 지정된 자리 수 만큼 왼쪽편에 지정된 char를 붙인다.
	 *
	 * @param str
	 * @param len
	 * @param addChar
	 * @return
	 */
	public static String leftPadding( String str, int len, String addChar )
	{
		String retString = str;

		int inputLen = retString.length();
		for( int i = inputLen ; i < len ; i++ )
		{
			retString = addChar + retString;
		}

		return retString;
	}
	/**
	 * Contents : 페이징 처리를 하는 목록에서 row시작 값을 구한다.
	 * 사용 -> <%= row-- %> 로 해당 TD에 사용.
	 *
	 * @param totRowCnt
	 * @param rowCnt
	 * @param iPage
	 * @return
	 */
	public static int startRow( int totRowCnt, int lineCnt, int iPage )
	{
		int row = totRowCnt - ((iPage-1) * lineCnt);
		if( row > totRowCnt ) { row = totRowCnt; }

		return row;
	}
	/**
	 * Contents : 페이징 처리를 하는 목록에서 row시작 값을 구한다.
	 * 사용 -> <%= row-- %> 로 해당 TD에 사용.
	 *
	 * @param totRowCnt
	 * @param rowCnt
	 * @param iPage
	 * @return
	 */
	public static int totPage( int totRowCnt, int lineCnt )
	{
		int totPage = totRowCnt / lineCnt;
		if( totRowCnt % lineCnt > 0 ) { ++totPage; }

		return totPage;
	}
	/**
	 * Contents : request parameter정보 체크.
	 * 
	 * @param req
	 */
	public static void checkRequestParam( HttpServletRequest req )
	{
		//정탐 - 11873	StringBuffer 선언보다 더 큰 용량을 사용하는 경우
		//StringBuffer의 크기를 지정하지 않으면 기본이 16이므로 
		StringBuffer sbInfo =new StringBuffer();
		
		sbInfo.append("\r\n\r\n=== request parameter info ===");
		Enumeration e = req.getParameterNames();
		while( e.hasMoreElements() )
		{
			String key = (String) e.nextElement();
			//정탐 - 11864	비효율적인 StringBuffer 사용 금지 
			//StringBuffer와 +를 같이 사용하면 StringBuffer 사용이 비효율적임. 
			sbInfo.append(key + " = " + (req.getParameterValues(key)[0]) );
		}

		sbInfo.append("\r\n\r\n=== request attribute info ===");
		Enumeration eAttribute = req.getAttributeNames();
		while( eAttribute.hasMoreElements() )
		{
			String key = (String) eAttribute.nextElement();
			//정탐 - 11864	비효율적인 StringBuffer 사용 금지 
			//StringBuffer와 +를 같이 사용하면 StringBuffer 사용이 비효율적임. 
			sbInfo.append(key + " = " + (req.getAttribute(key)) );
		}

		sbInfo.append("\r\n\r\n=== request header info ===");
		Enumeration eHeader = req.getHeaderNames();
		while( eHeader.hasMoreElements() )
		{
			String key = (String) eHeader.nextElement();
			//정탐 - 11864	비효율적인 StringBuffer 사용 금지 
			//StringBuffer와 +를 같이 사용하면 StringBuffer 사용이 비효율적임. 
			sbInfo.append(key + " = " + (req.getHeader(key)) );
		}
		
		sbInfo.append("\r\n\r\n");

		Syslog.debug(sbInfo.toString());
	}
	/**
     * Contents : NT5에 DATE 필드에 등록시 사용.
     * 
     * String을 default format에 맞춰 Date 로 반환
     * @param _dateString
     * @return
     */
    public static Date stringToDate(String _dateString) {
        return stringToDate(_dateString, "yyyy-MM-dd HH:mm:ss");
    }
    /**
     * Contents : NT5에 DATE 필드에 등록시 사용.
     * 
     * 요청한 Date format으로 String 값을 Date으로 반환 한다.
     * @param _dateString
     * @return
     */
    public static Date stringToDate(String _dateString, String sDateFormat) {
    	
        try {       
        	return new java.sql.Timestamp((new SimpleDateFormat(sDateFormat)).parse(_dateString).getTime());           
        } catch (ParseException e) {
            throw new CNTException("String Format is not valid");
        }
    }
    /****
     * Contents : 문자열 내에 넘어온 구분자로 잘라서 배열로 만들어준다.(split 과 같은 기능)
     * @param sPatternString
     * @param sDel
     * @return
     */
    public static String[] convertString2ArrayByDel( String sPatternString, String sDel)
	{
		StringTokenizer st = new StringTokenizer( sPatternString, sDel);
		ArrayList alReturnArray = new ArrayList();
		
		if( st.countTokens() > 0)
		{
			while( st.hasMoreTokens())
			{
				alReturnArray.add( st.nextToken());
			}
		}
		else
		{
			//정탐 - 11761	Null 대신 빈 (Empty) Array 리턴
			return null;
		}
		
		return ( String[])alReturnArray.toArray( new String[ alReturnArray.size()]);
	}
    /**
     * Contents : 배열을 지정한 구분자로 구분된 문자열로 만든다.
     * 
     * @param arrUser
     * @param sDelimiter
     * @param bIncludeTail 마지막에 붙은 구분자 제거 여부.
     * @return
     */
    public static  String convertArray2StringByDel( String[] arrUser, String sDelimiter, boolean bIncludeTail)
	{
		StringBuffer sbStr = new StringBuffer();
		
		for (int i = 0; i < arrUser.length; i++) 
		{
			sbStr.append( arrUser[ i]).append( sDelimiter);
		}
		
		if( !bIncludeTail)
		{
			sbStr.delete( sbStr.length() - sDelimiter.length(), sbStr.length()); 
		}
		
		return sbStr.toString();
	}
    /****
     * Contents : 두 개의 배열의 값을 하나로 만드어준다.
     * @param obj1 합쳐질 배열에 앞에 들어갈 문자배열
     * @param obj2 합쳐질 배열에 뒤에 들어갈 문자배열
     * @return 결합된 배열을 리턴한다.
     */
    public static String[] concatenateStringArray( String[] obj1, String[] obj2)
    {
    	
    	String[] arrStr = null;
    	//정탐 - 11748	혼란스러운 3항 연산
    	int cnt1 = obj1 != null ? obj1.length : 0;
    	//정탐 - 11748	혼란스러운 3항 연산
    	int cnt2 = obj2 != null ? obj2.length : 0;
    	
    	arrStr = new String[ cnt1 + cnt2];
    	
    	for ( int i = 0; i < obj1.length; i++) 
    	{
    		arrStr[ i] = obj1[ i];
		}
    	
    	for (int i = obj1.length; i < arrStr.length; i++) 
    	{
    		arrStr[ i] = obj2[ i - obj1.length];
		}
    	
    	return arrStr;
    }
    /**
     * Contents : 문자열내 지정 Index 위치에 다른 문자열을 삽입한다.
     * 
     * @param sText
     * @param iLocation
     * @param sInsertString
     * @return
     */
    public static String insertStringOfIndex( String sText, int iLocation, String sInsertString)
    {
    	StringBuffer sbReturn = new StringBuffer();

    	if( sText != null && sText.length() > 0 && sText.length() >= iLocation)
    	{
	    	String sTemp = sText.substring( 0, iLocation);
	    	
	    	sbReturn = new StringBuffer( sTemp);
	    	sbReturn.append( sInsertString).append( sText.substring( iLocation));
    	}
    	else
    	{
    		sbReturn.append( sText);
    	}
    	
    	return sbReturn.toString();
    }
    /**
     * Contents : 공백 문자열인 경우 지정한 문자열로 교체한다.
     * 
     * @param str
     * @param replaceStr
     * @param ignoreSpace str이 공백으로만 되어 있을 경우 replaceStr로 교체할지 여부.
     * @return
     */
    public static String replaceEmptyString( String str, String replaceStr, boolean ignoreSpace )
    {
    	String retStr = "";
    	
    	if( !CommonUtil.isEmpty(str) )
    	{
    		if( ignoreSpace && "".equals(str.trim()) ) { retStr = replaceStr; }
        	else { retStr = str; }
    	}
    	else { retStr = replaceStr; }
    	
    	return retStr;
    }
    
    ////////////////////////////////////////////  이하 프로젝트에 맞도록 수정하여 사용될 UTIL ////////////////////////////////////
    /**
     * Contents : 현재 권한에 맞는 forward_page값을 return한다.
     * forward_page가 layout페이지로 이용될 경우임.
     * 
     * @param es
     * @return
     */
    public static String getForwardPage( EzSet es )
    {
    	String sForwardPage = "";
//    	if( isAdmin(es) ) sForwardPage = ConstDef.FORWARD_PAGE_ADMIN;
//    	else sForwardPage = ConstDef.FORWARD_PAGE_USER;
    	
    	sForwardPage = ConstDef.FORWARD_PAGE_USER;
    	
    	return sForwardPage;
    }
    /**
     * Contents : 수퍼관리자 여부.
     * 
     * @param es
     * @return
     */
    public static boolean isAdmin( EzSet es )
    {
    	boolean retBool = false;
    	
    	if( es != null )
    	{
	    	String loginUsrPerm = es.getStringParam(ConstDef.LOGIN_KEY_USERTP);
	    	if( loginUsrPerm.indexOf(ConstDef.USER_TP_ADMIN) >= 0 ) { retBool = true; }
    	}
    	
    	return retBool;
    }
    /**
     * Contents : 운영자 여부.
     * 
     * @param es
     * @return
     */
    public static boolean isOperator( EzSet es )
    {
    	boolean retBool = false;
    	
    	if( es != null )
    	{
	    	String loginUsrPerm = es.getStringParam(ConstDef.LOGIN_KEY_USERTP);
	    	if( loginUsrPerm.indexOf(ConstDef.USER_TP_OPERATOR) >= 0 ) { retBool = true; }
    	}
    	
    	return retBool;
    }
    /**
     * Contents : 교수자 여부.
     * 
     * @param es
     * @return
     */
    public static boolean isTeacher( EzSet es )
    {
    	boolean retBool = false;
    	
    	if( es != null )
    	{
	    	String loginUsrPerm = es.getStringParam(ConstDef.LOGIN_KEY_USERTP);
	    	if( loginUsrPerm.indexOf(ConstDef.USER_TP_TEACHER) >= 0 ) { retBool = true; }
    	}
    	
    	return retBool;
    }
    /**
     * Contents : 조교 여부.
     * 
     * @param es
     * @return
     */
    public static boolean isAssist( EzSet es )
    {
    	boolean retBool = false;
    	
    	if( es != null )
    	{
	    	String loginUsrPerm = es.getStringParam(ConstDef.LOGIN_KEY_USERTP);
	    	if( loginUsrPerm.indexOf(ConstDef.USER_TP_ASSIST) >= 0 ) { retBool = true; }
    	}
    	
    	return retBool;
    }
    /**
     * Contents : 튜터 여부.
     * 학생인데 조교역할을 하는 사람.
     * 
     * @param es
     * @return
     */
    public static boolean isTutor( EzSet es )
    {
    	boolean retBool = false;
    	
    	if( es != null )
    	{
	    	String loginUsrPerm = es.getStringParam(ConstDef.LOGIN_KEY_USERTP);
	    	if( loginUsrPerm.indexOf(ConstDef.USER_TP_TUTOR) >= 0 ) { retBool = true; }
    	}
    	
    	return retBool;
    }
    /**
     * Contents : 학생 여부.
     * 
     * @param es
     * @return
     */
    public static boolean isStudent( EzSet es )
    {
    	boolean retBool = false;
    	
    	if( es != null )
    	{
	    	String loginUsrPerm = es.getStringParam(ConstDef.LOGIN_KEY_USERTP);
	    	if( loginUsrPerm.indexOf(ConstDef.USER_TP_STU) >= 0 ) { retBool = true; }
    	}
    	
    	return retBool;
    }
    /**
     * Contents : 학부모 여부.
     * 
     * @param es
     * @return
     */
    public static boolean isParent( EzSet es )
    {
    	boolean retBool = false;
    	
    	if( es != null )
    	{
	    	String loginUsrPerm = es.getStringParam(ConstDef.LOGIN_KEY_USERTP);
	    	if( loginUsrPerm.indexOf(ConstDef.USER_TP_PARENT) >= 0 ) { retBool = true; }
    	}
    	
    	return retBool;
    }
    /**
     * Contents : 일반사용자 여부.
     * 
     * @param es
     * @return
     */
    public static boolean isUser( EzSet es )
    {
    	boolean retBool = false;
    	
    	if( es != null )
    	{
	    	String loginUsrPerm = es.getStringParam(ConstDef.LOGIN_KEY_USERTP);
	    	if( loginUsrPerm.indexOf(ConstDef.USER_TP_USER) >= 0 ) { retBool = true; }
    	}
    	
    	return retBool;
    }
    
    public static String createAjaxXML( ResultObject ro, String[] arrColumnName )
    {
		//정탐 - 11873	StringBuffer 선언보다 더 큰 용량을 사용하는 경우
		//StringBuffer의 크기를 지정하지 않으면 기본이 16이므로 
		StringBuffer sb = new StringBuffer();
		
			int len = arrColumnName.length;
			
			sb.append("<DATA_RESULT>");

			//정탐 - 11864	비효율적인 StringBuffer 사용 금지 
			//StringBuffer와 +를 같이 사용하면 StringBuffer 사용이 비효율적임. 
			sb.append("<RET_CODE>" + NT5Constant.RET_CODE_SUCCESS + "</RET_CODE>");

			sb.append("<DATALIST>");
			int dataCnt = (int) ro.getRowCount();
			String sColName = "";
			String sColValue = "";
			for( int r = 1 ; r <= dataCnt ; r++ )
			{
				sb.append("<DATA>");
				//정탐 - 86	Loop 문 내에서 상수 할당 금지
				for( int i = 0 ; i < len ; i++ )
				{
					sColName = arrColumnName[i];
					sColValue = ro.getString(sColName, r);
					//정탐 - 86	Loop 문 내에서 상수 할당 금지
					if( sColValue == null ) { sColValue = ""; }
					//정탐 - 76	4회 이상 String Concat 사용 금지
					//정탐 - 11864	비효율적인 StringBuffer 사용 금지 
					//StringBuffer와 +를 같이 사용하면 StringBuffer 사용이 비효율적임. 
					sb.append('<' + sColName + "><![CDATA[" + sColValue + "]]></" + sColName + '>');
				}
				sb.append("</DATA>");
			}
			sb.append("</DATALIST>");
			//정탐 - 11864	비효율적인 StringBuffer 사용 금지 
			//StringBuffer와 +를 같이 사용하면 StringBuffer 사용이 비효율적임. 
			sb.append("<DATA_LENGTH>" + dataCnt + "</DATA_LENGTH>");
			
			sb.append("</DATA_RESULT>");
			if( sb.length() > 0 ) { sb.delete(0, sb.length()); }
		
		return sb.toString();
	}
    /**
     * Contents : EzSet 키-값검색(첫번째값)
     * 
     * baseColumnValue값과 동일한 baseColumnName필드의 값을 찾아,
     * 해당 row index에서 findColumnName필드에 대한 값을 찾아서 return.
     * 맨 처음 발견된 값을 return하고 종료.
     * 
     * @param ez
     * @param baseColumnName
     * @param baseColumnValue
     * @param findColumnName
     * @return
     */
    public static String getFindEzSetValue(EzSet ez, String baseColumnName, String baseColumnValue, String findColumnName){
		if( ez != null ){ 
			int rowCnt = ez.getResultCount();
			if( rowCnt <= 0 ){ 
				return "";
			}else if(rowCnt > 0){
				//오탐 - 45110190	[SP] 정수 오버플로우 
				//위 라인에서 rowCnt가 0보다 클  때라는 조건문이 보인다. - ageha 
				for(int i=1; i<=rowCnt; i++)
				{
					String value = ez.getString(baseColumnName,i);
					if(value.equals(baseColumnValue)){
						return ez.getString(findColumnName, i);
					}
				}
			}
		}
		return "";
    }
    
    public static String createCodeCombo( String patternName, String groupCode, String comboName, String comboText,
    		String defaultValue, String functionName, String functionValue )
    {
    	if( isEmpty(functionName) ) { functionName = "onChange"; }
    	if( isEmpty(functionValue) ) { functionValue = "return false"; }
    	
    	CodeCombo combo = new CodeCombo(patternName, groupCode, comboName);
    	if( !isEmpty(comboText) ) { combo.insertOption(comboText, ""); }
    	if( !isEmpty(defaultValue) ) { combo.setDefault(defaultValue, CodeCombo.BY_VALUE ); }
    	String sComboClsScmId = combo.genComboBox(functionName, functionValue);
    	
    	return sComboClsScmId;
    }
    /**
	 * Contents : 과목코드(=강의실코드, 학수번호(6)+분반(2))를 학수번호와 분반으로 분리한다.
	 * 
	 * @param sLecCd
	 * @return
	 */
	public static Hashtable getDivLec( String sLecCd )
	{
		Hashtable ht = new Hashtable();
		String lecSubjCd = ""; // 학수번호
		String lecDivCd = ""; // 분반
		
		if( sLecCd.length() > 6 )
		{
			lecSubjCd = sLecCd.substring(0, 6);
			lecDivCd = sLecCd.substring(6);
		}
		else if( sLecCd.length() == 6 ) { lecSubjCd = sLecCd; }
		
		ht.put("SUBJ", lecSubjCd);
		ht.put("DIV", lecDivCd);
		
		return ht;
	}
	
	public static String getClientIp( HttpServletRequest req )
	{
		return req.getRemoteAddr();
	}
}

