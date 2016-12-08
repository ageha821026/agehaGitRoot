package com.daulsoft.neotest;

public class NT5Constant
{
	public static final String SESSION_KEY_DEFAULT_USER = "13_user_user";
	public static final String SESSION_KEY_DEFAULT_ADMIN = "12_admin_admin";
	public static final int ADMIN_USR_ID = 12;
	
	public static final int DEFAULT_FOLDER_ID = 2;
	
	public static final String QUERY_ORDER_BY_ID = "ID";
	public static final String QUERY_ORDER_BY_NM = "NM";
	public static final String QUERY_ORDER_BY_ORD = "ORD";

	public static final String RET_CODE_SUCCESS = "Success"; // 쿼리 실행 성공.
	public static final String RET_CODE_ERROR = "ERROR"; // 쿼리 실행 실패.
	public static final String RET_CODE_LIMIT = "LIMIT"; // 쿼리 실행은 문제없으나 Trasaction처리를 할 수 없는 경우. ITEM이 존재하는 폴더/분류 삭제 등.
	public static final String RET_CODE_NO_ARG = "ARG"; // 조건 데이타 오류. ID값이 없는 경우 등.
	public static final String RET_CODE_ID_DUPLICATE = "DUPLICATE"; // 등록시 ID값 중복
	public static final String RET_CODE_NO_RESULT = "NORESULT"; // 등록시 ID값 중복
	
	// 멀티답안 구분자.
	// 정탐 - 11842	빈 (Empty) String 문자 더하기 연산 금지 
	public static final String SIMIL_ANS_SEP = "" + (char)5;
	// 정탐 - 11842	빈 (Empty) String 문자 더하기 연산 금지 
	public static final String MULTI_ANS_SEP = "" + (char)7;

	// SITE
	public static final String LOGIN_KEY_USERID = "neotest.base.userid";
	public static final String LOGIN_KEY_USERNM = "neotest.base.usernm";
	public static final String LOGIN_KEY_USERTP = "neotest.base.usertp";
}
