<%@page contentType="text/html; charset=utf-8"%>
<%
	/**
 	 TOP KEYWORD 가져오기
 	 	CommonHeader.jsp 에 선언되어 있는 WEB_DOMAIN_ID를 파라미터로 이용
 	*/
 	List topKeywordList = kbManager.selectTopKeywordList(WEB_DOMAIN_ID);
 	
	/**
	@Decription 
	: TOP KEYWORD 가져올때 보여줄 갯수, 시스템정의 & 사용자정의 구분값을 시스템에서 정의의 값이 아닌 웹페이지에서 정의할 때 사용해야 할 코드
		@param WEB_DOMAIN_ID 해당 웹 도메인  
		@param topKeywordType 시스템에 설정된 TOP KEYWORD 갯수
		@param topKeywordCount 시스템에 설정된 TOP KEYWORD TYPE, (시스템정의 : TYSYS, 사용자정의 : TYUSR)
 	
	@Example
		String topKeywordType = WebConfig.getEnvValue("FAQ_KEYWORD_TOPN_SYSTEM_TYPE");
	  	int topKeywordCount = Util.str2i(StringUtil.defaultString(WebConfig.getEnvValue("FAQ_KEYWORD_TOPN_COUNT"), "5"), 5); 	// TOP KEYWORD 개수
	    
	  	List topKeywordList = kbManager.selectTopKeywordList(WEB_DOMAIN_ID, topKeywordType, topKeywordCount);
	*/
	
	StringBuffer sbKeyword = new StringBuffer(250);    
    
    // 아래 로직은 TOP Keyword 가져오기 로직으로 , 기존 구현과는 다르게 화면사에 TOP Keyword를 노출할 필요가 있을때 참조
	if (topKeywordList != null && topKeywordList.size() > 0)
    {
        TopKeyword topKeyword = null;
        String comma = ",          ";
        for (int i=0; i<topKeywordList.size(); i++)
        {
        	if(i == topKeywordList.size()-1)
            //정탐 -	11700	빈 (Empty) If 문 사용 금지
        	{
//         		comma = "";
        	}
            topKeyword = (TopKeyword)topKeywordList.get(i);
            sbKeyword.append("<a href=\"javascript:;\" onclick=\"searchFaq('");
            sbKeyword.append(topKeyword.getKeyword());
            sbKeyword.append("');\">");
            sbKeyword.append(topKeyword.getKeyword());
            sbKeyword.append(topKeyword.getKeyword());
            sbKeyword.append("</a>");
            sbKeyword.append(comma);
            sbKeyword.append(' ');
        }
    }   
   
    String optionNodeId = request.getParameter("nodeIdPath");
    String kbSearchSample = WebConfig.getEnvValue("FAQ_SEARCH_QUESTION_SAMPLE");
	kbSearchSample = StringUtil.nvl(kbSearchSample, "찾고자 하는 검색어를 입력하세요.");
%>
<form name="rarForm" id="rarForm" method="post">
<div class="search_box"> 
				<div class="tsch_area">
					<h2>검색</h2>
					<div class="search">
						<select name="nodeIdPath" id="nodeIdPath" class="select01" style="width:100px;">
							<option value="NODE0000000001" selected>통합검색</option>
<%
	List leftNodeList = null;
	leftNodeList = nodeManager.selectNodeList(WEB_ROOT_ID, WEB_ROOT_ID, "SVFAQ");
	
    if(leftNodeList != null && leftNodeList.size() > 0)
    {
        CaseInsensitiveMap map = null;
        int nCount = 1;
        for(int i = 0; i < leftNodeList.size(); i++)
        {
            map = (CaseInsensitiveMap)leftNodeList.get(i);
            out.print("<option value=\"" + map.get("NODE_ID") + "\"");
            
            if( map.get("NODE_ID").equals(optionNodeId) ){
                out.print("selected");
            }
            out.println(">" + map.get("NODE_NAME") + "</option>");
        }
    }
%>
						</select>
						<input type="text" name="textQuestion" id="textQuestion" value="<%=kbSearchSample %>" style="width:500px;" class="input01">
						<input type="hidden" name="question" id="question">
						<input type="button" name="goSearch" id="goSearch" class="board_btn01" value="&nbsp;&nbsp; 검색 &nbsp;&nbsp;">
					</div>

					<div class="choice_word">
						<span>추천검색어:</span> <%=sbKeyword.toString()%>
					</div>

				</div>			
			</div>
			<!-- input type="hidden" name="question" id="question" -->
</form>  			
</table>