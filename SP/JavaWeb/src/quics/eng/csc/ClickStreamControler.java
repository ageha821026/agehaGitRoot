/*******************************************************************************
 * Project      : Quics Project Engine
 * FileName     : ClickStreamControler.java
 * Author       : 성정훈
 * Organization : (주) 인터넷커머스코리아
 * Version      :
 * Date Created : 2002년 4월 12일
 * -----------------------------------------------------------------------------
 * Revison History
 * who          when         	what
 * syk 		2003.06.10	finalize() 추가, rename하는 사이 write되는 것(사이즈가 0인 것)이 유실되는 문제 해결
*******************************************************************************/

package quics.eng.csc;

import java.io.*;
import java.util.*;
import java.net.*;

import spectra.base.util.DateUtil;

/**
 <CODE>ClickStreamControler</CODE>는 사용자의 행태를 분석하기 위해 CRM서버의 Click Stream Engine과 연동하고 정보를 저장하기 위한 Controler이다.

 일정한 건수를 한 파일에 기록하고 나면 다른 파일을 생성해서 기록합니다.
 다른 파일을 생성할 경우 Agent에 다른 파일의 이름을 보고합니다.

 파일은 property Handler에 정의된 파일명에 날자_시간분초(DD_yyyyMMddHHmmss)를 더하여 생성합니다.

 file 은 property 에 지정된 rootpath 아래에 업무 그룹별로 폴더를 만들어서 저장합니다.

 업무그룹이 BOK이고 인스턴스 아이디가 BOK01 인 경우

 rootpath/BOK/BOK01_yyyyMMddHHmmss

**/

public class ClickStreamControler
{
	// 파일명을 정의하는 날자형식
	// 정탐 - 11782	Final 필드는 반드시 Static으로 선언해야 함 
	public final String m_DateFormat = "_yyyyMMddHHmmss";

	// 파일에 쓴 클릭 건수
	private int m_clickCount = 0;

	// 한 파일에 쓸 수 있는 최대 클릭 건수
	private int m_clickLimitCount;

	private long m_changeTerm;
	//정탐 - 11764	단일 필드
	private long m_checkTime;

	private PrintWriter  m_writer;
	//정탐 - 11883	미참조 Private 필드 정의 금지 
	//정탐 - 11866	필드에 StringBuffer 사용 금지
	private StringBuffer m_sb;

	// 인스턴스 타입
	//정탐 - 11764	단일 필드
	private String m_instanceType  = "";

	// 인스턴스 아이디
	private String m_instanceCode  = "";

	// 현제 쓰고있는 파일
	private File m_currentFile = null;

	// 파일이 저장될 경로
	// 정탐 - 11764	단일 필드
	private String m_rootpath  = "";

	// 사용할 파일 경로
	private String m_filepath  = "";

	/**
	* ClickStreamControler의 생성자입니다.
	*/
	public ClickStreamControler()
	{

		this.m_checkTime       = 10000l;

		this.m_filepath        = initDirectory(m_rootpath , m_instanceType+File.separator+m_instanceCode);


		Runnable run = new Runnable() {
			public void run() {
				long startTime = System.currentTimeMillis();
				//정탐 - 12	True Loop에서 No break 금지
				//정탐 - 45110571	[SP] 항상 참인 논리식
				while(true) {
					if( System.currentTimeMillis()-startTime>=m_changeTerm || m_clickCount>=m_clickLimitCount )
					{
						//log.inf.println("###############CHECK###############");
						startTime = renameFile();
					}
					//오탐 - 4306	Catch 문 내의 미처리 Exception 금지
					//InterruptedException을 처리하지는 않았지만 Exception이 더 상위 이므로 
					//정탐 - 45110754	[SP]  적절하지않은 예외처리
					//정탐 - 45110390	[SP] 액션 없는 오류 조건 탐지 (오류 상황에 대한 처리 부재)
					try{Thread.sleep(m_checkTime);}catch(Exception e){}
					
						
				}
			}
		};

		(new Thread(run, "File Check Thread")).start();
	}

	/**
	* 입력된 데이타를 파일에 쓴다.
	* @param  o  an object writing to file
	*/
	public void setStream(String s) throws Exception
	{
		//정탐 - 4322	Synchronized에 this가 lock으로 쓰이는지 확인
		synchronized (this) {
			if( null == m_currentFile || null == m_writer || !m_currentFile.exists() ) {
				//오탐 - 45110488	[SP] 잘못된 세션으로 데이터 누출 (세션 간에 데이터 누출)
				//HttpServlet와 무관함. 
				m_currentFile = new File(m_filepath,generateFilename(m_instanceCode));

				//정탐 - 4306	Catch 문 내의 미처리 Exception 금지
				//openWriter를 사용하면서 IOException에 대한 처리가 없음
				//오탐 - 45110488	[SP] 잘못된 세션으로 데이터 누출 (세션 간에 데이터 누출)
				//HttpServlet와 무관함. 
				m_writer      = openWriter( m_currentFile.getPath() );
				m_clickCount  =  0;
			}
			m_writer.println(s);
			m_clickCount++;
		}
	}
	protected void finalize() throws Throwable
	{
		renameFile();
	}	
				
	private long renameFile() {
		//log.inf.println("ReName File");
		try
		{
			if(m_writer != null) {
				m_writer.flush();
				m_writer.close();
				//log.dbg.println("writer close");
			}
		
			if( null != m_currentFile && m_currentFile.exists() ) {
				File oldFile = new File(m_currentFile.getPath());
				//정탐 - 11729	Null의 할당
				//초기화 부분이 아닌데 null을 할당하고 있으므로 
				m_currentFile = null;

				//정탐 - 11729	Null의 할당
				//초기화 부분이 아닌데 null을 할당하고 있으므로 
				m_writer = null;
				//log.dbg.println("currentFile null");
				//오탐 - 45110023	[SP] 상대 디렉토리 경로 조작
				//oldFile은 외부 입력이 아님. 
				oldFile.renameTo( new File(oldFile.getPath()+"_WD") );
			}
			//정탐 - 45110754	[SP]  적절하지않은 예외처리		
			//정탐 - 45110390	[SP] 액션 없는 오류 조건 탐지 (오류 상황에 대한 처리 부재)
		} catch(Exception e) {
		} finally {
			//정탐 - 45110584	[SP] finally 블록 내에서 리턴
			return System.currentTimeMillis();
		}
	}


	private String initDirectory(String parent , String child)
	{
		File dir = new File(parent , child);

		if( !dir.exists() || !dir.isDirectory() )
		{
			dir.mkdirs();
		}
		return dir.getPath();
	}

	/**
	* open file
	* @param filename open filename
	*/
	private PrintWriter openWriter(String filename) throws IOException
	{
		try {
			//log.dbg.println(filename + " open ..");
			//정탐 - 45110404	[SP] 자원의 부적절한 반환
			return new PrintWriter(new BufferedWriter(new FileWriter(filename, true)) , false);
		} catch(IOException e) {
			//정탐 - 11856	Exception의 재 Throw 금지
			throw e;
		}
	}

	/**
	* 원본 파일이름을 받아서 현재 날자와 시간을 붙여 돌려준다.
	* @param    filename
	* @return   string     filename_DD_HHmmss
	*/
	private String generateFilename(String filename)
	{
		return filename;
	}
}