<%@ page import="java.util.Locale" %>

<%@ page import="java.util.*" %>
<%@ page import="spectra.base.*" %>
<%@ page import="spectra.base.util.*" %>
<%@ page import="spectra.base.web.*" %>
<%@ page import="spectra.base.sql.ibatis.*" %>
<%@ page import="spectra.base.web.template.*" %>
<%@ page import="spectra.base.i18n.*" %>
<%@ page import="spectra.ee.*" %>
<%@ page import="spectra.ee.web.*" %>
<%@ page import="spectra.ee.web.common.service.*" %>
<%@ page import="spectra.ee.web.common.model.*" %>
<%@ page import="spectra.ee.web.kb.model.*" %>
<%@ page import="spectra.ee.web.template.*" %>
<%@ page import="spectra.ee.commons.search.model.*" %>
<%@ page import="spectra.base.licensemanager.Encrypt" %>
<%@ page import="spectra.ee.commons.qna.model.*" %>
<%@ page import="org.apache.commons.collections.map.CaseInsensitiveMap" %>

<%
    //정탐 -	4306	Catch 문 내의 미처리 Exception 금지 
    request.setCharacterEncoding("euc-kr");
    
	/* 도메인이 아닌 카테고리ID를 최상위 카테고리로 쓸 경우를 대비해 WEB_ROOT_ID 추가
	       디폴트는 도메인 아이디이지만, 카테고리를 상위로 쓸 때는 카테고리 ID를 넣어주어야 함 */

	//정탐 -	11734	로컬 변수에 대해 Final 사용 금지 
	final String WEB_ROOT_ID = WebConfig.getDomainId(); //"NODE0000000028";
    String WEB_DOMAIN_ID = WebConfig.getDomainId();
    String FAQ_PAGE_SORT_TYPE = WebConfig.getEnvValue("FAQ_PAGE_SORT_TYPE");
    String URL_WEBAPPS = WebConfig.getConfigValue("COMMON", "URL_WEBAPPS");
    String WEB_SEARCH_VOLUME = WebConfig.getEnvValue("COM_SEARCH_VOLUME");

	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");   
	response.setDateHeader("Expires",0);   
	if ("HTTP/1.1".equals(request.getProtocol()))  
	{
		response.setHeader("Cache-Control", "no-cache"); 
	}
%>

<%!
    protected String getPageNavigator(int totalCount, int rowsPerPage, int pageNo) 
    {
        BoardNavigatorBase nav = new BoardNavigatorBase(totalCount, rowsPerPage, pageNo);
        nav.setToFirstSymbol("<img src=\"../images/btn_pg1_l.gif\" alt=\"처음\">");
        nav.setPreviousSymbol("<img src=\"../images/btn_pg2_l.gif\" alt=\"이전\">");
        nav.setCurrHighlight("<b>", "</b>");                        // 활성화 번호 강조 태그 
      	nav.setForwardSymbol( "<img src=\"../images/btn_pg2_r.gif\" alt=\"다음\">");
      	nav.setToEndSymbol( "<img src=\"../images/btn_pg1_r.gif\" alt=\"마지막\">");
        nav.setUnitCount(10);
        //오탐 -	11841	String 추가 시 StringBuffer 사용 (2번 걸리며, 주석라인으로 이동함)
        nav.setStyle("style=\"padding:3px;\"");                     // [처음,이전,다음,끝] 에 들어가는 스타일 

        
        String pageNavigator = "<div class=\"paginate\">";
        pageNavigator+=nav.getLinkHtmlByTable(true, true, true, null);
        pageNavigator+="</div>";
        return pageNavigator;
    }
    
    protected int getPageCount(int totalCount, int rowsPerPage)  
    {
        int pageCount = totalCount / rowsPerPage + 1 ;
        return pageCount;
    }
    
%>

