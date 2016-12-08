/**
 * @(#)ConstDef.java
 *
 * @version 1.0    2008. 03. 08.  
 * @author Dennis
 * 
 * Copyright 1998-2008 by Daul Soft, Inc. All rights reserved.
 */
package com.daulsoft.ets;

import kr.daulsoft.neotest5.data.CLeftTimeType;
import kr.daulsoft.neotest5.webext.NTInitServlet;

import com.daulsoft.core.env.Config;
import com.daulsoft.core.env.ServerContext;

/**
 * 웹 공통 상수 class
 */
//미탐 - 162	[샘플] 프로그램 주석에 개발자(@author)는 반드시 명기
public class ConstDef{
	
	/* *************************** Project에 맞게 수정해서 사용 START *****************/

	/* 로그인 정보 (COOKIE or SESSION) START */
	
	/** 사용자 이름 */
	public static final String LOGIN_KEY_USERNAME = "com.daulsoft.fpexam.username";

	/** 사용자 ID. */
	public static final String LOGIN_KEY_USERID = "com.daulsoft.fpexam.userid";

	/** 사용자 타입 */
	public static final String LOGIN_KEY_USERTP = "com.daulsoft.fpexam.usertp";

	/** NeoTest 사용자 타입 */
	public static final String LOGIN_KEY_NT5USRID = "com.daulsoft.fpexam.nt5usrid";
	
	/* 강의실 관련 쿠키/세션 값은 메뉴별로 LMS에서 호출되는 경우에만 로그인시 저장 처리 됨 */

	/** 강의실 키 */
	public static final String LOGIN_KEY_LECKEY = "com.daulsoft.fpexam.leckey";

	/** 강의실 명 */
	public static final String LOGIN_KEY_LECNM = "com.daulsoft.fpexam.lecnm";

	/** 강의 년도 */
	public static final String LOGIN_KEY_LECYEAR = "com.daulsoft.fpexam.lecyear";

	/** 강의 학기 */
	public static final String LOGIN_KEY_LECTERM = "com.daulsoft.fpexam.lecterm";

	/** 강의 코드 */
	public static final String LOGIN_KEY_LECCD = "com.daulsoft.fpexam.leccd";

	/** 강의실내 사용자 권한 */
	public static final String LOGIN_KEY_LECMEMTP = "com.daulsoft.fpexam.lecmemtp";
	
	/* 시스템 2개 이상 동시 연동을 하는 경우 호출 구분 */
	/** 호출자 구분 값 */
	public static final String LOGIN_KEY_CALLER = "com.daulsoft.fpexam.caller";
	/** 모바일 APP에서 호출시 APP URN값. */
	public static final String LOGIN_KEY_MOBILE_APPURN = "com.daulsoft.fpexam.urn";
	
	/* 한화생명 */
	/** 내근여부 */
	public static final String LOGIN_KEY_DESKJOB_YN = "com.daulsoft.fpexam.deskjobyn";
	/** CM여부 */
	public static final String LOGIN_KEY_CM_YN = "com.daulsoft.fpexam.cmyn";
	
	/** 생생노하우 과정분류 코드 [v_lec_cls.cd1] */
	public static final String LEC_CLS_SANGSANG = "4";
	
	/** 사이트 권한 **/
	public static final String USER_TP_ADMIN = "M"; // 관리자
	public static final String USER_TP_OPERATOR = "O"; // 운영자
	public static final String USER_TP_TEACHER = "T"; // 교수자
	public static final String USER_TP_ASSIST = "A"; // 조교
	public static final String USER_TP_TUTOR = "B"; // 튜터
	public static final String USER_TP_STU = "S"; // 학생
	public static final String USER_TP_PARENT = "P"; // 학부모
	public static final String USER_TP_USER = "U"; // 일반
	
	/* 분류 */	

	/* 공통코드 */
	/* 네오 웹보드 라이센스키 */
	public static final String NEO_WEBOARD_LICENSE_KEY = (String)((Config)ServerContext.getInstance().get(ServerContext.CTX_CONFIG))
															.getString("/properties/site/WEBOARD_LICENSE");
	
	/* ezSet Key */
		/** jsp/common/proc_i.jsp 호출시 사용되는 EzSet key값 */
	public static final String EZSET_KEY_PROC = "RESULT_PROC";

		/** jsp/common/excel.jsp 호출시 사용되는 EzSet key값 */
	public static final String EZSET_KEY_EXCEL = "RESULT_EXCEL";
	
		/** 분류체계선택 팝업 호출시 사용되는 EzSet key값 */
	public static final String EZSET_KEY_CLSCD = "POPUP_CLSCD";
	
		/** 공통코드선택 팝업 호출시 사용되는 EzSet key값 */
	public static final String EZSET_KEY_CODE = "POPUP_CODE";
	
	/* LMS 연동 관련 */
		/** 연동이 없는 경우 Dummy 강의실 키 */
	public static final String DUMMY_LEC_KEY = "LecKey";
		/** 연동이 없는 경우 Dummy 강의실 코드 */
	public static final String DUMMY_LEC_CD = "LecCd";
		/** 연동이 없는 경우 Dummy 강의실 년도 */
	public static final String DUMMY_LEC_YEAR = "9999";
		/** 연동이 없는 경우 Dummy 강의실 학기/기수 */
	public static final String DUMMY_LEC_TERM = "9";
		/** 연동이 없는 경우 Dummy 강의실 시험 주차 */
	public static final String DUMMY_LEC_EXAM_WEEK = "9";

	public static final String CURR_LEC_KEY = "currLecKey";
	public static final String CURR_LEC_CD = "currLecCd";
	public static final String CURR_LEC_YEAR = "currLecYear";
	public static final String CURR_LEC_TERM = "currLecTerm";
	public static final String CURR_LEC_EXAM_WEEK = "currLecExamWeek";

		/** 강의실 상태 코드 - 운영중 */
	public static final String LEC_STATUS_ON = "1";
		/** 강의실 멤버 상태 코드 - 정상 */
	public static final String LEC_MEM_STATUS_ON = "1";
		/** 사용자 상태 코드 - 정상 */
	public static final String LEC_USR_STATUS_ON = "1";

	/* *************************** Project에 맞게 수정해서 사용 END *****************/
	
	/* 평가 LAYOUT 정보 START */
    /** 단문항 조판 전체 Layout */
	public static final String PRNT_LAYOUT_QST = "<LayoutItem bVisibleBody=\"Y\" iSpacingPiece=\"56\" Type=\"LayoutPaperOneQuestion\"><Border bDraw=\"N\" color=\"16777215\" /><BackGround bDraw=\"N\" color=\"16777215\" /><Padding l=\"566\" r=\"566\" t=\"566\" b=\"566\" /><pt x=\"0\" y=\"0\" /><sz w=\"10773\" h=\"8471\" /><SizeInfo iColumnCount=\"1\" iSpacingPage=\"283\" iSpacingPiece=\"56\" iWidthQuestionNumber=\"396\" iWidthQuestionAnswerNumber=\"340\"><SizePage w=\"11905\" h=\"16837\" /><PaddingPage l=\"566\" r=\"566\" t=\"566\" b=\"566\" /><PaddingColumn l=\"283\" r=\"283\" t=\"283\" b=\"283\" /></SizeInfo><LayoutPaperItemSet>@QST_LAYOUT</LayoutPaperItemSet></LayoutItem>";
    /** 단문항 응시 - 문항 Layout */
    public static final String PRNT_APY_ITEM_QST = "<LayoutItem bVisibleBody=\"Y\" iSpacingPiece=\"56\" iWidthQuestionNumber=\"396\" iWidthQuestionAnswerNumber=\"340\" bVisibleHint=\"N\" bVisibleComment=\"N\" bVisibleUserAnswer=\"Y\" bVisibleCorrectAnswer=\"N\" bVisibleScore=\"N\" bVisiblePoint=\"Y\"><Border bDraw=\"N\" color=\"16777215\" /><BackGround bDraw=\"N\" color=\"16777215\" /><Padding l=\"56\" r=\"56\" t=\"56\" b=\"283\" /><pt x=\"0\" y=\"0\" /><sz w=\"10095\" h=\"2358\" /></LayoutItem>";
    /** 단문항 결과 - 문항 Layout */
    public static final String PRNT_RET_ITEM_QST = "<LayoutItem bVisibleBody=\"Y\" iSpacingPiece=\"56\" iWidthQuestionNumber=\"396\" iWidthQuestionAnswerNumber=\"340\" bVisibleHint=\"N\" bVisibleComment=\"Y\" bVisibleUserAnswer=\"Y\" bVisibleCorrectAnswer=\"Y\" bVisibleScore=\"Y\" bVisiblePoint=\"Y\"><Border bDraw=\"N\" color=\"16777215\" /><BackGround bDraw=\"N\" color=\"16777215\" /><Padding l=\"56\" r=\"56\" t=\"56\" b=\"283\" /><pt x=\"0\" y=\"0\" /><sz w=\"10095\" h=\"2358\" /></LayoutItem>";
    /** 단문항 조판명 */
    public static final String PRNT_TP_NM_QST = "단문항";
    /** 단문항 응시 조판명 */
    public static final String PRNT_TP_NM_QST_APY = "단문항응시";
    /** 단문항 결과 조판명 */
    public static final String PRNT_TP_NM_QST_RET = "단문항결과";
    
    /** 시험지 전체 조판 Layout */
	public static final String PRNT_LAYOUT_PAP = "<LayoutItem bVisibleBody=\"Y\" iSpacingPiece=\"56\" ePageNumberPosition=\"PNP_BOTTOM_CENTER\" Type=\"LayoutPaperPages\" iHeader1Height=\"1020\" iHeader2Height=\"340\" iFooterHeight=\"680\"><Border bDraw=\"N\" color=\"16777215\" /><BackGround bDraw=\"N\" color=\"16777215\" /><Padding l=\"0\" r=\"0\" t=\"0\" b=\"0\" /><pt x=\"0\" y=\"0\" /><sz w=\"11905\" h=\"16893\" /><SizeInfo iColumnCount=\"2\" iSpacingPage=\"283\" iSpacingPiece=\"56\" iWidthQuestionNumber=\"396\" iWidthQuestionAnswerNumber=\"340\"><SizePage w=\"11905\" h=\"16837\" /><PaddingPage l=\"564\" r=\"564\" t=\"564\" b=\"564\" /><PaddingColumn l=\"283\" r=\"283\" t=\"283\" b=\"283\" /></SizeInfo><LayoutPaperItemSet Type=\"LayoutPaperItemSet\"><Border bDraw=\"N\" color=\"16777215\" /><BackGround bDraw=\"N\" color=\"16777215\" /><Padding l=\"0\" r=\"0\" t=\"0\" b=\"0\" /><pt x=\"0\" y=\"0\" /><sz w=\"4822\" h=\"23841\" />@QST_LAYOUT</LayoutPaperItemSet></LayoutItem>";
    /** 시험지 응시 - 문항 Layout */
    public static final String PRNT_APY_ITEM_PAP = "<LayoutItem bVisibleBody=\"Y\" iSpacingPiece=\"56\" iWidthQuestionNumber=\"396\" iWidthQuestionAnswerNumber=\"340\" bVisibleHint=\"N\" bVisibleComment=\"N\" bVisibleUserAnswer=\"Y\" bVisibleCorrectAnswer=\"N\" bVisibleScore=\"N\" bVisiblePoint=\"N\"><Border bDraw=\"N\" color=\"16777215\" /><BackGround bDraw=\"N\" color=\"16777215\" /><Padding l=\"56\" r=\"56\" t=\"56\" b=\"283\" /><pt x=\"0\" y=\"1237\" /><sz w=\"4710\" h=\"898\" /></LayoutItem>";
    /** 시험지 결과 - 문항 Layout */
    public static final String PRNT_RET_ITEM_PAP = "<LayoutItem bVisibleBody=\"Y\" iSpacingPiece=\"56\" iWidthQuestionNumber=\"396\" iWidthQuestionAnswerNumber=\"340\" bVisibleHint=\"Y\" bVisibleComment=\"Y\" bVisibleUserAnswer=\"Y\" bVisibleCorrectAnswer=\"Y\" bVisibleScore=\"Y\" bVisiblePoint=\"Y\"><Border bDraw=\"N\" color=\"16777215\" /><BackGround bDraw=\"N\" color=\"16777215\" /><Padding l=\"56\" r=\"56\" t=\"56\" b=\"222\" /><pt x=\"0\" y=\"0\" /><sz w=\"4708\" h=\"2942\" /></LayoutItem>";
    /** 시험지 조판명 */
    public static final String PRNT_TP_NM_PAP = "시험지";
    /** 시험지 응시 조판명 */
    public static final String PRNT_TP_NM_PAP_APY = "시험지응시";
    /** 시험지 결과 조판명 */
    public static final String PRNT_TP_NM_PAP_RET = "시험지결과";
	/* 평가 LAYOUT 정보 END */

	public static final int POPUP_LINE_COUNT = 10;
	public static final int POPUP_PAGE_COUNT = 10;
	public static final int DEFAULT_LINE_COUNT = 10;
	public static final int DEFAULT_PAGE_COUNT = 10;

	public static final String DEFAULT_SCR_ROW = "20";	// 게시물수 셀렉트 박스 기본값
	
	/* Blowfish 암호화 key */
	public static final String XML_ENCRYPT_KEY = "neotest5";

	/** 숨긴 relay(message) 페이지 <br> value : /jsp/common/relay.jsp */
	public static final String RELAY_PAGE = "/daul/jsp/common/relay.jsp";

	/** Layout 페이지 */
	public static final String FORWARD_PAGE_USER = "/daul/jsp/layoutPopup.jsp";
	public static final String FORWARD_PAGE_ADMIN = "/daul/jsp/layoutAdmin.jsp";
	public static final String FORWARD_PAGE_POPUP = "/daul/jsp/layoutPopup.jsp";

	public static final String FORWARD_DEMO = "/daul/jsp/demo/layout.jsp";
	public static final String FORWARD_DEMO_POPUP = "/daul/jsp/demo/popup/layoutPopup.jsp";

	/* 코드매니져 그룹 명. SystemSQL.xml에 정의되어 있는 pattern name속성값. */
	public static final String CODE_GROUP_NAME = "code";
	public static final String COMM_CD = "comm_cd";

	/** 세션 타임아웃 <br> value : 60 [분] */
	public static final int SESSION_TIMEOUT = 60;

	/** request 기본 parameters. input필드로 넘기지 않아도 request에 기본적으로 설정되는 parameter key임. */
	//검출 - 156	[샘플] 멤버 변수의 이름은 대문자로 시작해야 함
	public static final String defaultReqData = ",screen,javax.servlet.forward.request_uri,javax.servlet.forward.context_path,javax.servlet.forward.servlet_path,";
	
	/* *************************************** 이하 NT5관련 상수 ************************************/

	/* 남은시간 체크 방법 */
	public static final String LFT_TIME_TP_USED = CLeftTimeType.ELeftTimeType.Used; // 사용한 시간 만큼만 차감.
	public static final String LFT_TIME_TP_ABSOLUTE = CLeftTimeType.ELeftTimeType.Absolute; // 최초 시작일과 현재시간 기준으로 차감.

	/* NT5 Config File Path */
	public static final String NT5_CONFIG_XML_PATH = NTInitServlet.getConfigFilePath();
	
	/* 저작도구 기본 관리자 계정 */
	public static final String SESS_SESSIONKEY = "12_admin_admin";

	/* ITEM 구분 */
	public static final String ITEM_QST = "Q";
	public static final String ITEM_QST_MULTI = "QM";
	public static final String ITEM_QST_SHORT = "QS";
	public static final String ITEM_QST_ESSAY = "QE";
	public static final String ITEM_PAPER = "P";
	public static final String ITEM_EXAM = "E";
	public static final String ITEM_EXAMSET = "ES";
	public static final String ITEM_STUDYBOOK = "SB";
	public static final String ITEM_SUMMARY = "S";
	public static final String ITEM_PSG = "PSG";
	
	/* 그룹이 지정 폴더에 대해 가질 기본 권한 */
	public static final String FLD_GRP_PERM = "FP0001,FP0002,FP0003,FP0004,FP0005,FP0007,FP0009,FP0010";

	/* 사용자 권한 */
	public static final String RIGHT_LOGIN = "UP0001";
	public static final String RIGHT_SYSTEM_MGT = "UP0002";
	public static final String RIGHT_CATEGORY_MGT = "UP0003";
	public static final String RIGHT_USER_MGT = "UP0004";
	public static final String RIGHT_AUTHORING = "UP0005";
	public static final String RIGHT_AUTHORING_PLAN = "UP0006";
	public static final String RIGHT_SUPERVISION = "UP0007";
	public static final String RIGHT_SUPERVISION_PLAN = "UP0008";
	public static final String RIGHT_AUTH_EXAM = "UP0009";
	public static final String RIGHT_EXAM_SCORE_MGT = "UP0010";
	public static final String RIGHT_EXAM_STATISTICS = "UP0011";
	public static final String RIGHT_EXAM_APPLY = "UP0012";

	/* 채점 프로시져 두번째 인자값 */
	public static final String SCORE_PROC_SHORT = "SHORT";
	public static final String SCORE_PROC_LONG = "LONG";

	/* 날짜 관련 기본 종료 일자 */
	public static final String BOARD_DEFAULT_END_DM = "2099-12-31";
	public static final String BOARD_DEFAULT_END_DTM = "2099-12-31 11:59:59";
	
	/* 자료권한*/
	public static final String PERM_CD_ONE  = "IP0001"; 
	public static final String PERM_CD_TWO  = "IP0002"; 
	
	/* 응시 상태. NT5_EXAM_ANS.APY_EXAM_STAT */
	public static final String EXAMINATION_NONE = "N"; 		//미응시
	public static final String EXAMINATION_ING = "A"; 		//응시중
	public static final String EXAMINATION_OK = "F"; 		//제출
	public static final String EXAMINATION_RESULT_ING = "E";//채점중
	public static final String EXAMINATION_RESULT_OK = "S";	//채점완료	
	
	/* 엔진 CLASS호출 결과 코드, 메시지 Key */
	public static final String	AR_RESULT_OBJECE_KEY		= "RET";
	public static final String	AR_RESPONSE_MESSAGE_KEY		= "RESPONSE_MESSAGE_KEY";
	
	/* 시험초기화시 암호 설정 */
	public static final String EXAM_INIT_PWD = "exam";
}

