package com.daulsoft.neotest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.daulsoft.neotest5.common.CNTException;
import kr.daulsoft.neotest5.logger.CLoggerDebug;

import com.daulsoft.neotest.util.common.Blowfish;
import com.daulsoft.neotest.NT5Constant;
import com.daulsoft.neotest.util.sql.NT5QueryRS;
import com.daulsoft.util.log.Syslog;

public class NT5Util
{
	/**
	 * ResultSet으로 부터 조회된 데이타들을 XML구조로 생성한다.
	 * 조회된 모든 필드에 대해서 XML 데이타 구조 생성.
	 * 
	 * @param queryRS
	 * @return
	 */
	public static String createResultXML( NT5QueryRS queryRS ) throws NullPointerException
	{
		String retXML = "";
		try {
		     String[] arrColumnName = queryRS.getColumnName();
		     retXML = createResultXML(queryRS, arrColumnName);
		} 
		catch (NullPointerException npe) {
			Syslog.error(npe);
		}
		return retXML;
	}

	/**
	 * ResultSet으로 부터 조회된 데이타들을 XML구조로 생성한다.
	 * 지정된 arrColumnName 배열에 있는 필드에 대해서만 XML 데이타 구조 생성.
	 * 
	 * @param rs
	 * @param arrColumnName
	 * @return
	 */
	public static String createResultXML( NT5QueryRS queryRS, String[] arrColumnName ) throws NullPointerException
	{
		//정탐 - 11873	StringBuffer 선언보다 더 큰 용량을 사용하는 경우 
		StringBuffer sb = new StringBuffer();
		
		try
		{
			int len = arrColumnName.length;
			
			sb.append("<DATA_RESULT>");
			sb.append("<RET_CODE>");
			sb.append(NT5Constant.RET_CODE_SUCCESS);
			sb.append("</RET_CODE>");
			sb.append("<DATALIST>");
			int dataCnt = queryRS.getRowCnt();
			String sColName = "";
			String sColValue = "";
			for( int r = 1 ; r <= dataCnt ; r++ )
			{
				sb.append("<DATA>");
				//정탐 - 86	Loop 문 내에서 상수 할당 금지
				for( int i = 0 ; i < len ; i++ )
				{
					sColName = arrColumnName[i];
					sColValue = queryRS.getString(sColName, r);
					//정탐 - 76	4회 이상 String Concat 사용 금지
					//정탐 - 11864	비효율적인 StringBuffer 사용 금지 
					//StringBuffer과 + 를 함께 사용할 때 
					sb.append('<' + sColName + "><![CDATA[" + sColValue + "]]></" + sColName + '>');
				}
				sb.append("</DATA>");
			}
			sb.append("</DATALIST>");
			sb.append("<DATA_LENGTH>");
			sb.append(dataCnt);
			sb.append("</DATA_LENGTH>");
			sb.append("</DATA_RESULT>");
		}
		catch( NullPointerException e )
		{
			if( sb.length() > 0 ) {sb.delete(0, sb.length());}
			
			return createCodeXML(NT5Constant.RET_CODE_ERROR, "");
		}
		
		return sb.toString();
	}
	
	public static String createResultXML( Hashtable htXML ) throws NullPointerException
	{
		//정탐 - 11873	StringBuffer 선언보다 더 큰 용량을 사용하는 경우 
		StringBuffer sb = new StringBuffer();
		
		try
		{
			sb.append("<DATA_RESULT>");
			sb.append("<RET_CODE>");
			sb.append(NT5Constant.RET_CODE_SUCCESS);
			sb.append("</RET_CODE>");
			sb.append("<DATALIST>");
			sb.append("<DATA>");
			
			String sColName = "";
			String sColValue = "";
			Iterator iter = htXML.keySet().iterator();
			while( iter.hasNext() )
			{
				sColName = (String) iter.next();
				sColValue = (String) htXML.get(sColName);
				//정탐 - 76	4회 이상 String Concat 사용 금지
				//정탐 - 11864	비효율적인 StringBuffer 사용 금지 
				//StringBuffer과 + 를 함께 사용할 때
				sb.append('<' + sColName + "><![CDATA[" + sColValue + "]]></" + sColName + '>');
			}
			
			sb.append("</DATA>");
			sb.append("</DATALIST>");
			
			sb.append("<DATA_LENGTH>1</DATA_LENGTH>");
			
			sb.append("</DATA_RESULT>");
		}
		catch( NullPointerException ne )
		{
			if( sb.length() > 0 ) { sb.delete(0, sb.length()); }
			
			return createCodeXML(NT5Constant.RET_CODE_ERROR, "");
		}
		
		return sb.toString();
	}
	
	/**
	 * 코드만 존재하는 XML 생성.
	 * 
	 * @param retCode
	 * @param sID Transaction처리인 경우 등록/수정/삭제시 사용된 ID.
	 * @return
	 */
	public static String createCodeXML( String retCode )
	{
		return createCodeXML(retCode, "");
	}
	
	/**
	 * 코드만 존재하는 XML 생성.
	 * 
	 * @param retCode
	 * @param sID Transaction처리인 경우 등록/수정/삭제시 사용된 ID.
	 * @return
	 */
	public static String createCodeXML( String retCode, String sID )
	{
		//정탐 - 11873	StringBuffer 선언보다 더 큰 용량을 사용하는 경우 
		StringBuffer sb = new StringBuffer();

		sb.append("<DATA_RESULT>");
		sb.append("<RET_CODE>");
		sb.append(retCode);
		sb.append("</RET_CODE>");
		sb.append("<DATALIST><DATA><ID>");
		sb.append(sID);
		sb.append("</ID></DATA></DATALIST>");
		sb.append("</DATA_RESULT>");
		
		return sb.toString();
	}

	/**
	 * 문자열 타입의 날짜값을 sDateFormat포맷의 Date객체로 변환.
	 * 
	 * @param _dateString
	 * @return
	 */
    public static Date stringToDate(String sDate, String sDateFormat)
    {
        try{ return new java.sql.Timestamp((new SimpleDateFormat(sDateFormat)).parse(sDate).getTime()); }
        catch(ParseException e){ throw new CNTException("Date format is not valid"); }
    }
    
    /**
     * 문자열이 null 또는 공백인지 체크.
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty( String str )
    {
		if( str == null || "".equals(str) ) { return true; }
    	else { return false; }
    }

    /**
     * GUID 생성.
     * @return
     */
    public static String getGUID()
    {
    	String vmid = null;
    	String GUID = null;
    	//정탐 - 11842	빈 (Empty) String 문자 더하기 연산 금지 
    	vmid = (new java.rmi.dgc.VMID()+"");
    	String limit_str = vmid.replaceAll(":", "");
		//정탐 - 76	4회 이상 String Concat 사용 금지
    	GUID = limit_str.substring(0,8)+"-"+limit_str.substring(8,12)+"-"+limit_str.substring(12,16)+"-"+limit_str.substring(16,20)+"-"+limit_str.substring(20,32);
    	return GUID;
    }
    
    /**
     * 값이 없거나 공백으로만 된 문자열인 경우 지정한 문자열로 교체한다.
     * 
     * @param str
     * @param replaceStr
     * @return
     */
    public static String replaceEmptyString( String str, String replaceStr )
    {
    	String retStr = "";
    	
    	if( !isEmpty(str) )
    	{
			if( "".equals(str.trim()) ) { retStr = replaceStr; }
        	else { retStr = str; }
    	}
    	else { retStr = replaceStr; }
    	
    	return retStr;
    }

    /**
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
        //st.hasMoreTokens()의 return값은 boolean이므로 정수 오버 플로우가 있을 수 없음.
        for(int i =0; st.hasMoreTokens(); i++){
        	tempID = st.nextToken();
        	al.add(i,tempID);
        }
        return al;
    }

	/**
	 * 쿠키를 설정한다.
	 *
	 * @param response
	 * @param key
	 * @param value
	 */
	public static void setCookie( HttpServletResponse response, String cookieDomain, String key, String value )
	{
		setCookie( response, cookieDomain, key, value, -1 );
	}

	/**
	 * 유지시간을 가지는 쿠키를 설정한다.
	 * 쿠키의 키-값 모두 암호화 됨.
	 *
	 * @param response
	 * @param key
	 * @param value
	 * @param maxAge 쿠키를 유지할 시간[sec]. 0보다 작은 경우 셋팅을 하지 않는다.
	 */
	public static void setCookie( HttpServletResponse response, String domain, String key, String value, int maxAge )
	{
		try{
			value = Blowfish.encryptToHexString(value);
			//정탐 - 4306	Catch 문 내의 미처리 Exception 금지
			//Blofish.encryptToHexSting()메소드는 IllegalBlockSizeException를 Throws 하므로 try~Catch에서 IllegalBlockSizeException를 처리했어야함.
			key = Blowfish.encryptToHexString(key);
		}
		//정탐 - 45110754	[SP]  적절하지않은 예외처리
		catch( Exception e )
		{
			CLoggerDebug.error(e);
		}
		
		ArrayList alDomain = stringToArray(domain, ",");
		for( int i = 0 ; i < alDomain.size() ; i++ )
		{
			//정탐 - 11835	루프에서 객체의 인스턴스 생성 금지
			Cookie cookie = new Cookie(key, value);
			cookie.setDomain((String) alDomain.get(i));
			cookie.setPath("/");
			//정탐 - 45119316	[SP] 쿠키보안: 영속적인 쿠키
			//setMaxAge 설정시 외부에서 입력받은값을 그대로 사용하고 있음 . 
			if( maxAge >= 0 ) { cookie.setMaxAge(maxAge); }
			//정탐 - 45110614	[SP] HTTPS 세션내에 보안속성없는 민감한 쿠키
			response.addCookie(cookie);
		}
	}

	/**
	 * 쿠키에 저장된 정보 구한다.
	 *
	 * @param request
	 * @return
	 */
	public static String getCookieValue( HttpServletRequest request, String key )
	{
		String retValue = "";
		String encKey = "";
		try{
			encKey = Blowfish.encryptToHexString(key);
		}//정탐 - 45110754	[SP]  적절하지않은 예외처리
		catch( Exception e )
		{
			CLoggerDebug.error(e);
		}

		// 로그인 정보 조회.
		Cookie[] cookies = request.getCookies();
		int cookieCnt = 0;
		if( cookies != null ) { cookieCnt = cookies.length; }
		for( int i = 0 ; i < cookieCnt ; i++ )
		{			
			Cookie cookie = cookies[i];
			//정탐 - 45110807	[SP] 보호메커니즘을 우회할 수 있는 입력값 변조
			//인증이 필요한 경우에는 cookie가 아닌 session을 사용하는것을 권장
			if( cookie.getName().equals(encKey) && cookie.getValue().length() > 0 )
			{
				retValue = cookie.getValue();
				//정탐 - 86	Loop 문 내에서 상수 할당 금지
				if( retValue == null ) { retValue = ""; }
				//정탐 - 11759	리터럴은 항상 비교문의 왼쪽에 배치
				else if( !retValue.equals("") )
				{
					try{
						//정탐 - 4306	Catch 문 내의 미처리 Exception 금지
						//Blofish.encryptFromHexSting()메소드는 IllegalBlockSizeException를 Throws 하므로 try~Catch에서 IllegalBlockSizeException를 처리했어야함.
						retValue = Blowfish.decryptFromHexString(retValue);
					}
					//정탐 - 45110754	[SP]  적절하지않은 예외처리
					catch( Exception e )
					{
						CLoggerDebug.error(e);
					}
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
	 * 지정된 키의 Cookie를 삭제한다.
	 *
	 * @param response
	 * @param key
	 */
	public static void deleteCookie( HttpServletResponse response, String cookieDomain, String key )
	{
		setCookie( response, cookieDomain, key, "", 0 );
	}
	
	public static String getClientIP( HttpServletRequest req )
	{
		return req.getRemoteAddr();
	}
	
	public static String createResultJson( NT5QueryRS queryRS )
	{
		return createResultJson(queryRS, queryRS.getColumnName());
	}
		
	public static String createResultJson( NT5QueryRS queryRS, String[] arrColumnName )
	{
		//정탐 - 11873	StringBuffer 선언보다 더 큰 용량을 사용하는 경우 
		StringBuffer sb = new StringBuffer();
		
		int len = arrColumnName.length;
		sb.append('[');

		int dataCnt = queryRS.getRowCnt();
		String sColName = "";
		String sColValue = "";
		for( int r = 1 ; r <= dataCnt ; r++ )
		{
			if( r > 1 ) { sb.append(','); }
			sb.append('{');
			//정탐 - 86	Loop 문 내에서 상수 할당 금지
			for( int i = 0 ; i < len ; i++ )
			{
				sColName = arrColumnName[i];
				sColValue = queryRS.getString(sColName, r);
				if( i > 0 ) { sb.append(','); }
				//정탐 - 76	4회 이상 String Concat 사용 금지
				//정탐 - 11864	비효율적인 StringBuffer 사용 금지 
				//StringBuffer과 + 를 함께 사용할 때
				sb.append(sColName + ":\"" + sColValue.replaceAll("\\\"", "&quot;") + "\"");
			}
			sb.append('}');
		}
		sb.append(']');
		
		return sb.toString();
	}
}
