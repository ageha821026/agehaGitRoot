package quics.eng.csc;
/*******************************************************************************
 * Project      : Quics Project Engine
 * FileName     : ClickStreamThread.java
 * Author       : 성정훈
 * Organization : (주) 인터넷커머스코리아
 * Version      :
 * Date Created : 2002년 9월 13일
 * -----------------------------------------------------------------------------
 * Revison History
 * who          when         	what
 *******************************************************************************/

import java.io.*;
import java.util.*;

import com.daulsoft.util.log.Syslog;


/**

**/
//정탐 - 10032	Thread 상속 금지
public class ClickStreamThread extends Thread
{


	private Object [] m_arFileList;
	//정탐 - 11883	미참조 Private 필드 정의 금지 
	private boolean   m_notify;

	//정탐 - 11764	단일 필드
	private String    m_poolName;
	//정탐 - 11764	단일 필드
	private String    m_query;

	private String    m_savePath;

	/**
	* Constructor
	*/
	public ClickStreamThread( ) {

		this.m_savePath  = "/oradata3/qslog";
		this.m_poolName  = "jdbc:weblogic:pool:CSPool";
		this.m_query     = "insert into quics_log(파일명,시간) values(?,sysdate)";
	}

	public void setFileList( Object [] arList ) {
		//정탐 - 45110496	[SP] private 배열-유형 필드에 공용 데이터 할당
		this.m_arFileList = arList;
	}

	/**
	* thread start
	*/
	public void run()
	{
		try {
			File             f;
			DataInputStream  bin = null;
			String           remotePath;
			LinkedList       lst = new LinkedList();

			for(int i=0;i<m_arFileList.length;i++) {

				try {
					//정탐 - 45110367	[SP] 경쟁 조건: 검사시점과 사용시점
					// new File 하기전 파일 상태에 대한 점검이 없음 
					//정탐 - 11835	루프에서 객체의 인스턴스 생성 금지
					f            = new File( (String)m_arFileList[i] );

					//정탐 - 45110367	[SP] 경쟁 조건: 검사시점과 사용시점
					// new File 하기전 파일 상태에 대한 점검이 없음 
					//정탐 - 45110023	[SP] 상대 디렉토리 경로 조작
					//정탐 - 11835	루프에서 객체의 인스턴스 생성 금지
					remotePath   = ( new File( m_savePath,f.getName()+".dat") ).getPath();
					//정탐 - 45110190	[SP] 정수 오버플로우
					//정탐 - 11835	루프에서 객체의 인스턴스 생성 금지
					byte [] data = new byte[ (int)f.length() ];
					//정탐 - 11835	루프에서 객체의 인스턴스 생성 금지
					bin          = new DataInputStream(
						new BufferedInputStream(
							//정탐 - 45110367	[SP] 경쟁 조건: 검사시점과 사용시점
							// new FileInputStream 하기전 파일 상태에 대한 점검이 없음 
							//정탐 - 45110023	[SP] 상대 디렉토리 경로 조작	
							new FileInputStream( f.getPath() )
						)
					);
					bin.readFully(data);

					System.out.println("####CLICKSTREAM_THREAD ["+(String)m_arFileList[i]+"]Moving File####");

					//정탐 - 45110367	[SP] 경쟁 조건: 검사시점과 사용시점
					//File을 delete하기전 파일 상태에 대한 점검이 없음 
					f.delete();

					lst.add( f.getName()+".dat" );
					//정탐 - 45110754	[SP]  적절하지않은 예외처리
				} catch (IOException ignore) {
					//정탐 - 45110497	[SP] 시스템 데이터 정보 누출
					//getMessage를 통해 시스템 데이터가 누출 될 수 있음 
					System.out.println(ignore.getMessage());
					break;
				} finally {
					//정탐 - 11716	If 문에 중괄호 사용 권장  
					//정탐 - 45110754	[SP]  적절하지않은 예외처리
					if(bin!=null) 
						try { bin.close(); } 
					    catch(Exception ignore){
					    	Syslog.error(ignore);
					    }

					//정탐 - 11729	Null의 할당
					//초기화 부분이 아닌데 null을 할당하고 있으므로 
					bin = null;
				}
			}
			//정탐 - 45110571	[SP] 항상 참인 논리식
			//정탐 - 11700	빈 (Empty) If 문 사용 금지
			if( true ) {
			}
		} finally {
			System.out.println("####ClickStreamThread END...####");
		}
	}
}