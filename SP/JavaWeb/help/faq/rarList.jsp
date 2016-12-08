<%--
 - Copyright : SPECTRA INC.
 - @author : yshwang
 - @create date : 2012-05-23
 - @description : keyword Search List page
--%>
<%@page import="java.io.UnsupportedEncodingException"%>
<%@page contentType="text/html; charset=euc-kr" %>
<%@ include file="../include/CommonHeader.jsp" %>
<%@ include file="../login/LoginCheck.jsp" %>
<%@page import="java.util.*" %>
<%@page import="spectra.base.web.ParamMap" %>
<%@page import="spectra.ee.web.template.BoardNavigatorByCount"%>
<%@page import="net.sf.json.*"%>
<%@page import="java.util.logging.*"%>
<jsp:useBean id="kbManager" class="spectra.ee.web.kb.service.KbManager" />
<jsp:useBean id="nodeManager" class="spectra.ee.web.node.service.NodeManager" />
<jsp:useBean id="replyManager" class="spectra.ee.web.common.service.ReplyManager" />

<%
	ParamMap req = new ParamMap(request);

	int pageNo = req.getInt("pageNo", 1);  															// 페이지 번호
	String paramParentNodeId = req.getString("parentNodeId", "");										// 1단계 카테고리
	String paramNodeId = req.getString("nodeId", "");													// 2단계 카테고리
	String paramNodeIdPath = req.getString("nodeIdPath", "");											// 2단계 카테고리 (하위 검색 포함)
	String paramQuestion = req.getString("question", "");												// 검색어
	String paramIsSearchLog = req.getString("isSearchLog", "true");										// 검색 로그를 남길 것인지 여부
	String paramIsKeywordLog = req.getString("isKeywordLog", "true");									// 키워드 로그를 남길 것인지 여부
	String paramLogId = req.getString("log_id", Util.getGuid());										// 로그ID
	int rowsPerPage = Util.str2i(WebConfig.getEnvValue("FAQ_PAGE_ROW_COUNT"), 10);						// 한 페이지 당 리스트 개수

	// 필요시 사용 가능
    //int searchResultCount = Util.str2i(WebConfig.getEnvValue("FAQ_SEARCH_RESULT_COUNT"), 10);			// 최대 검색수
    //String serviceType = req.getParam("serviceType", "SVFAQ");										// 서비스 타입 , FAQ or RAR
    //String surveyType = req.getParam("surveyType", "SCHAF");											// 설문 타입

    if (paramQuestion == null || paramQuestion.trim().length() == 0)
    {
        WebUtil.sendRedirect(out, "검색어가 없습니다.", "../faqList.jsp");
      //오탐 -	11711	불필요한 Return 문 사용 금지
      //break의 의미로 사용 
        return;
    }

    // 카테고리 검색을 할 경우, 해당 카테고리 아이디의 이름을 가지고 온다.
    String parentNodeName = "통합검색";

    if (paramParentNodeId != null && paramParentNodeId.length() > 0)
    {
    	ParamMap map = nodeManager.selectByPK(paramParentNodeId);
    	parentNodeName = map.getString("node_name");
    }

    String question = null;
    try
    {
        question = java.net.URLDecoder.decode(paramQuestion, "UTF-8");
    }
    catch(UnsupportedEncodingException e)
    {
    	Logger.getLogger(e.toString());
    }

    // *** 검색을 위한 파라미터 맵 세팅 start
    ParamMap searchMap = new ParamMap();
    /** 검색 범위
    	0, 검색범위는 제목과 내용, 태그(연관키워드)를 포함해서 모두 검색한다.
		1, 검색범위는 제목과 내용 모두 검색한다.
 		2, 검색범위는 제목만을 검색한다.
		3, 검색범위는 내용만을 검색한다.
		4, 검색범위는 제목과 내용, 태그(연관키워드), 첨부파일을 포함해서 모두 검색한다.
	*/
	searchMap.put("searchRange", "0");
    /** 검색결과 타입
    	1, SearchKb, SearchRawKb 에 모든 필드에 대한 데이터를 담아서 리턴한다.
  		2, SearchKb, SearchRawKb 에 모든 필드에 대한 데이터를 담아서 리턴하지만<br>
    		제목과 내용을 정해진 규칙에 의해 일부 데이터까지만 리턴한다.
    	11, SearchKb(kb_id, node_id, title, contents, node_path) SearchRawKb(qna_id,
    		node_id, question_title, question_contents, answer_title, answer_contents,
    		node_path) 값을 가진 배열을 리턴한다.
    	12, SearchKb(kb_id, node_id, title, contents, node_path) SearchRawKb(qna_id,
    		node_id, question_title, question_contents, answer_title, answer_contents,
    		node_path) 값을 가진 배열을 리턴한다. 제목과 내용을 정해진 규칙에 의해 일부 데이터까지만 리턴한다.
    	21, SearchKb(kb_id, node_id, title, node_path) SearchRawKb(qna_id, node_id,
    		question_title, answer_title, node_path) 값을 가진 배열을 리턴한다.
  	*/
  	searchMap.put("searchReturnType", "1");
	// 검색 카테고리
	if( StringUtil.isNotBlank(paramNodeId) )
	{
		searchMap.put("nodeId", paramNodeId);
	}
	else
	{
		searchMap.put("nodeIdPath", paramNodeIdPath);
	}
	// FAQ 및  지식에 대한 서비스 타입 플래그
	searchMap.put("serviceType", "SVFAQ");
	// 검색 로그 저장 flag
	searchMap.put("isSearchLog", paramIsSearchLog);
	// 키워드 로그 저장 flag
	searchMap.put("isKeywordLog", paramIsKeywordLog);
	// 검색 결과가 있는 경우에만 조회수를 가져오기
	searchMap.put("isSearchHitCount", "true");
	// 검색엔진 Volume (기본적으로 v01)
	searchMap.put("volume", "v01");
	// 자동완성기능에서 입력한 단어의 위치를 나타낸다. 0 : 앞부분, 1 : 뒷부분 : 2 : 앞 또는 뒷부분.
	searchMap.put("flag", "2");
	// 검색용 질문 키워드
	searchMap.put("question", paramQuestion);
	// 검색 페이지 NO
	searchMap.put("pageNo", Integer.toString(pageNo));
	// 검색 관련 인코딩 (한글관련)
	// "a2k" ascii(8859_1) -> ksc(KSC5601);
	// "a2u" ascii(8859_1) -> UTF-8;
	// "k2a", "u2a", "u2k", "k2u"
    // 서버에 따라 셋팅을 다시 해주어야 함.
	searchMap.put("langFlag", "u2u");
	// 검색 로그 (ex: -4ce3768f:137723d1032:-7ff9133765151)
	searchMap.put("searchLogId", paramLogId);
	//*** 검색을 위한 파라미터 맵 세팅 end

	// 검색 실행
	int startNo = 0;
	int totalCount = 0;
	String searchLogId = "";
	String keyword = "";
	String keywordLogId = "";
	ArrayList rarList = null;

	ParamMap resultMap = null;

	try
	{
		resultMap = kbManager.searchKb(searchMap);
	}
	//오탐 -	45110754	[SP]  적절하지않은 예외처리
	catch(Exception e)
	{
		Logger.getLogger(e.toString());
	}

	if(resultMap != null && "SUCCESS".equals((String)resultMap.get("RESULT")))
	{
 		startNo = resultMap.getInt("START_NO");
		totalCount = resultMap.getInt("TOTAL_COUNT");

		searchLogId = resultMap.getString("SEARCH_LOG_ID");
		keyword = resultMap.getString("KEYWORD");
		keywordLogId = resultMap.getString("KEYWORD_LOG_ID");

		rarList = (ArrayList)resultMap.get("LIST");
	}

%>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8" >
<title>eNomix Enterprise</title>
<link rel="stylesheet" type="text/css" href="../css/style.css" media="all" >
<script language="JavaScript" type="text/JavaScript" src="../js/jquery-1.7.2.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../js/rarList.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../js/rar.js"></script>
<script language="javascript">
/**
 * jquery, init
 */
$(document).ready(function(){
	$("#textQuestion").val("<%=question%>").focus();
});
</script>
</head>

<body>
	<div id="warp">
		<div class="subTitle">
			<h2 class="title">FAQ</h2>
			<p class="stl"><font class="font_blue">|</font> 자주 문의하시는 질문과 답변입니다.</p>
		</div>

		<%@ include file="./rarForm.jsp" %>

		<div class="category">
			<p class="st"><font class="font_blue">Total</font> : <strong><%=totalCount%></strong> 건</p>
		</div>

		<!--- RAR 리스트 : 시작 --->
		<p class="mgb20"></p>
			<div class="boardtype_list02">
				<table cellspacing="0" class="boardtype2" summary="게시판의 글제목 리스트">
				<caption>게시판 리스트</caption>
				<colgroup>
					<col width="5%">
					<col width="15%">
					<col width="5%">
				</colgroup>
				<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">제목</th>
					<th scope="col">조회수</th>
				</tr>
				</thead>
				<tbody>
<%


/*
	//json control source -s-
  	if(!"FAIL".equals(jsResult) && jsResult != null)
	{
			for (Iterator i = ja.iterator(); i.hasNext();)
			{ 	  jsStartNo++;
			      JSONObject ob = (JSONObject) i.next();
			      String strKbId = StringUtil.defaultString(ob.getString("KB_ID"), "");
			      out.println("<tr><td>"+jsStartNo+"</td>");
			      String strTitle = "<td class=\"title\"><a href=\"#\" onclick=\"faqClick('"+StringUtil.defaultString(ob.getString("KB_ID"), "")+"')\">"+StringUtil.defaultString(ob.getString("TITLE"), "")+"</a></td>";
			      out.println(strTitle);
			      out.println("<td>"+StringUtil.defaultString(ob.getString("HIT_COUNT"), "0")+"</td></tr>");

			}
	}
	//json control source -e-
*/

	if (rarList != null && rarList.size() > 0)
	{
		StringBuffer sb = new StringBuffer(100);
		ParamMap replyParam = new ParamMap();
		for(int i=0; i < rarList.size() ; i++)
		{
			Map kbMap = (Map)rarList.get(i);
			startNo++;

			out.println("<tr><td>"+startNo+"</td>");

			/*
			   댓글 카운트 기능
			*/
			replyParam.put("kbId", (String)kbMap.get("KB_ID"));
			replyParam.put("serviceType", "SVFAQ");

			String replyCount = (String)replyManager.selectReplyCount(replyParam);
			if(!"0".equals(replyCount))
			{
				replyCount = " ["+replyCount+"]";
			}
			//정탐 -	11700	빈 (Empty) If 문 사용 금지
			else
			{
// 				replyCount ="";
			}
			sb.append("<td class=\"title\"><a href=\"javascript:;\" onclick=\"faqClick('");
			//오탐 -	11864	비효율적인 StringBuffer 사용 금지
			//+연산자 없음 
			sb.append(StringUtil.defaultString((String)kbMap.get("KB_ID"), ""));
			sb.append("')\">");
			//오탐 -	11864	비효율적인 StringBuffer 사용 금지
			//+연산자 없음 
			sb.append(StringUtil.defaultString((String)kbMap.get("TITLE"), ""));
			sb.append(replyCount);
			sb.append("</a></td>");

			out.println(sb.toString());
			out.println("<td>"+kbMap.get("HIT_COUNT")+"</td></tr>");
	    }
	}
	else
    {
          out.println("<tr><td></td><td>검색 결과가 없습니다.</td><td></td></tr>");
    }
%>
				</tbody>
				</table>
				<p class="bline"></p>
			</div>
			<div class="paginate">
<%
    if (rarList != null && rarList.size() > 0)
    {
  		BoardNavigatorByCount nav = new BoardNavigatorByCount(totalCount, rowsPerPage, pageNo);
 	    nav.setToFirstSymbol("<img src=\"../images/btn_pg1_l.gif\" alt=\"처음\">");
 	    nav.setPreviousSymbol("<img src=\"../images/btn_pg2_l.gif\" alt=\"이전\">");
 	    nav.setCurrHighlight("<strong class=\"first-child\">", "</strong>");                        // 활성화 번호 강조 태그
 	    nav.setForwardSymbol("<img src=\"../images/btn_pg2_r.gif\" alt=\"다음\">");
 	    nav.setToEndSymbol("<img src=\"../images/btn_pg1_r.gif\" alt=\"끝\">");
 	    nav.setUnitCount(10);
   	    out.println(nav.getLinkHtmlByTable(true, true, false, null));
    }
%>
			</div>
			<!--- RAR 리스트 : 끝 --->
		</div>
<!-- 	//정탐 -	45110352	[SP] 사이트 간 요청 위조	 -->
	<form name="listForm" id="listForm" method="get" action="rarList.jsp">
	  <input type="hidden" name="pageNo" id="pageNo" value="<%=pageNo%>" />
	  <input type="hidden" name="question" id="question" value="<%=paramQuestion%>" />
	  <input type="hidden" name="parentNodeId" id="parentNodeId" value="<%=paramParentNodeId%>" />
	  <input type="hidden" name="nodeId" id="nodeId" value="<%=paramNodeId%>" />
	  <input type="hidden" name="nodeIdPath" id="nodeIdPath" value="<%=paramNodeIdPath%>" />
	  <input type="hidden" name="kbId" id="kbId"/>
	  <input type="hidden" name="surveyType" id="surveyType" value="SCHAF" />
	  <input type="hidden" name="rarTotalCount" id="rarTotalCount" />
	  <input type="hidden" name="log_id" id="log_id" value="<%=paramLogId%>" id="log_id" />
	  <input type="hidden" name="isSearchLog" id="isSearchLog" value="false" />
	  <input type="hidden" name="isKeywordLog" id="isKeywordLog" value="false" />
	</form>
    </body>
</html>
