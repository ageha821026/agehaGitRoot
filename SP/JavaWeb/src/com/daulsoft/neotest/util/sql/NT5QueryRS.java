package com.daulsoft.neotest.util.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Hashtable;
import java.util.Vector;

import kr.daulsoft.neotest5.logger.CLoggerDebug;

import com.daulsoft.neotest.util.NT5Util;
import com.daulsoft.util.log.Syslog;

public class NT5QueryRS
{
	private String[] arrColumnName = null;
	private String[] arrColumnTypeName = null;
	private Vector vData = new Vector();
	private int rowCnt = 0;
	private boolean boolExecuted = false;

	//정탐 - 11782	Final 필드는 반드시 Static으로 선언해야 함
	//정탐 - 11764	단일 필드
	private final String COLUMN_TYPE_BINARY = ",IMAGE,TEXT,CLOB,BLOB,";
	
	public NT5QueryRS()
	{
		
	}
	
	public void setResultSet( ResultSet rs )
	{
		//정탐 - 11711	불필요한 Return 문 사용 금지 
		if( rs == null ) { return; }
		
		try
		{
			// 필드 정보 설정.
			ResultSetMetaData rsmd = rs.getMetaData();
			int len = rsmd.getColumnCount();
			arrColumnName = new String[len];
			arrColumnTypeName = new String[len];
			for( int i = 0 ; i < len ; i++ )
			{
				arrColumnTypeName[i] = rsmd.getColumnTypeName(i+1).toUpperCase();
				arrColumnName[i] = rsmd.getColumnName(i+1);
			}
			
			// 데이타 설정.
			while( rs.next() )
			{	
				//정탐 - 11835	루프에서 객체의 인스턴스 생성 금지
				Hashtable arg = new Hashtable();
				//정탐 - 86	Loop 문 내에서 상수 할당 금지
				for( int i = 0 ; i < len ; i++ )
				{
					Object colValue = null;
					
					if( COLUMN_TYPE_BINARY.indexOf(","+arrColumnTypeName[i]+",") >= 0 )
					{
						// binary타입인 경우.
						colValue = rs.getBytes(arrColumnName[i]);
						//정탐 - 11835	루프에서 객체의 인스턴스 생성 금지
						if( colValue == null ) { colValue = new byte[0]; }
					}
					else
					{
						//정탐 -4306	Catch 문 내의 미처리 Exception 금지
						//getString의 SQLException을 처리하지 않음 
						colValue = rs.getString(arrColumnName[i]);
						if( colValue == null ) { colValue = ""; }
					}

					arg.put(arrColumnName[i], colValue);
				}

				rowCnt++;
				vData.add(arg);
			}
			
			boolExecuted = true;
		}
		//정탐 - 45110754	[SP]  적절하지않은 예외처리
		catch( Exception e )
		{
			boolExecuted = false;
			CLoggerDebug.error(e);
		}
	}
	
	public boolean isSuccess()
	{
		return boolExecuted;
	}
	
	public int getRowCnt()
	{
		return rowCnt;
	}
	
	public String[] getColumnName()
	{
		//정탐 - 45110495	[SP] 공용 메서드로부터 리턴된 private 배열-유형 필드
		//정탐 - 11843	내부 Array를 리턴하는 메소드
		return arrColumnName;
	}
	
	public String getString( String columnName, int index )
	{
		String ret = "";
		if( rowCnt < index ) { return ret; }
		else
		{
			String str = (String) ((Hashtable)vData.get(index-1)).get(columnName);
			if( NT5Util.isEmpty(str) ) { str = ""; }
			ret = str;
		}
		
		return ret;
	}
	
	public int getInt( String columnName, int index ) throws NumberFormatException
	{
		int ret = 0;
		try {
		      if( rowCnt < index ) { return ret; }
		      else
		          {
		          String str = (String) ((Hashtable)vData.get(index-1)).get(columnName);
		          if( NT5Util.isEmpty(str) ) { str = "0"; }
		          ret = Integer.parseInt(str);
		      }
		}
		catch (NumberFormatException nfe) {
			Syslog.error(nfe);
		}
		return ret;
	}
	
	public long getLong( String columnName, int index ) throws NumberFormatException
	{
		long ret = 0l;
		try {
		      if( rowCnt < index ) { return ret; }
		      else
		          {
		          String str = (String) ((Hashtable)vData.get(index-1)).get(columnName);
		          if( NT5Util.isEmpty(str) ) { str = "0"; }
		          ret = Long.parseLong(str);
		          }
		}
		catch (NumberFormatException nfe) {
			Syslog.error(nfe);
		}
		return ret;
	}
	
	public float getFloat( String columnName, int index ) throws NumberFormatException
	{
		float ret = 0f;
		try {
		     if( rowCnt < index ) { return ret; }
		     else
		         {
		         String str = (String) ((Hashtable)vData.get(index-1)).get(columnName);
		         if( NT5Util.isEmpty(str) ) { str = "0.0"; }
		         ret = Float.parseFloat(str);
		         }
		}
		catch (NumberFormatException nfe) {
			Syslog.error(nfe);
		}
		return ret;
	}
	
	public double getDouble( String columnName, int index ) throws NumberFormatException
	{
		double ret = 0d;
		try {
		     if( rowCnt < index ) { return ret; }
		     else
		         {
		         String str = (String) ((Hashtable)vData.get(index-1)).get(columnName);
		         if( NT5Util.isEmpty(str) ) { str = "0.0"; }
		         ret = Double.parseDouble(str);
		         }
		}
		catch (NumberFormatException nfe){
			Syslog.error(nfe);
		}
		return ret;
	}
	
	public byte[] getBytes( String columnName, int index )
	{
		byte[] arrByte = (byte[]) ((Hashtable)vData.get(index-1)).get(columnName);
		if( arrByte == null ) { arrByte = new byte[0]; }
		
		return arrByte;
	}
}
