<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript">
//切換查詢項目
$(document).ready(function() {
	$("select#listForm_searchCondition").change(function() {
		$("input#search").attr("name", $(this).val());
	});
});

function goSearch(){
    goMain("<%=request.getContextPath()%>/crud/apply.journal.list.action",
			"#apply_journal_list", "");
}

//新增
function goAdd(){
        goDetail('<%=request.getContextPath()%>/crud/apply.journal.query.action','期刊-新增');
}

//刪除多筆資料之函式
function goDelete() {
	//檢查資料是否已被勾選
	var IsSelected = false;
	var serNoStr = "";
	var checkbox_checked_num = 0;
	for (var i = 0; i < $(".checkbox").length; i++) {
		if ($(".checkbox").get(i).checked) {
			serNoStr = serNoStr + $(".checkbox").get(i).value + "；；";
			IsSelected = true;
			checkbox_checked_num++;
		}
	}
	
	//進行刪除動作
	if (IsSelected) {
		var f = {
                trueText:'是',
                trueFunc:function(){
                        var url = '<%=request.getContextPath()%>/crud/apply.journal.deleteChecked.action';
                        var data = $('#apply_journal_list').serialize()+'&pager.offset='+'${ds.pager.offset}'+'&pager.currentPage='+'${ds.pager.currentPage}'+'&pager.offsetPoint'+'${ds.pager.offset}';
                        goMain(url,'',data);
                },
                falseText:'否',
                falseFunc:function(){
                        //不進行刪除...
                }
        };
		goAlert('提醒','您確定要刪除所勾選的資料嗎?',f);
	} else {
		goAlert("提醒","請選擇一筆或一筆以上的資料");
	}
}

//資料檢視
function goView(serNo){
        var url = "<c:url value = '/'/>crud/apply.journal.view.action";
        var data = 'viewSerNo='+serNo;
        goDetail(url,'期刊-檢視',data);
}

//更新資料
function goUpdate(serNo) {
	goDetail('<%=request.getContextPath()%>/crud/apply.journal.query.action?'+'entity.serNo='+serNo,'期刊-修改');
}

//GoPage
function gotoPage(page){
	var totalPage = $("span.totalNum:eq(0)").html();
    var recordPerPage="${ds.pager.recordPerPage}";
    var offset=parseInt(recordPerPage)*(parseInt(page)-1);
    if(parseInt(page) < 1){
        page=1;
        offset=parseInt(recordPerPage)*(parseInt(page)-1);
    }
    else if(parseInt(page)>parseInt(totalPage)){
        page=totalPage;
        offset=parseInt(recordPerPage)*(parseInt(page)-1);
    }
    goMain('<c:url value = '/'/>crud/apply.journal.list.action','#apply_journal_list','&pager.offset='+offset+'&pager.currentPage='+page);
}

//變更顯示筆數
function chagePageSize(recordPerPage,recordPoint){
        goMain('<c:url value = '/'/>crud/apply.journal.list.action','#apply_journal_list','&recordPerPage='+recordPerPage+'&recordPoint='+recordPoint);
}
</script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>

function xxxx(){
 function getDoc(frame) {
     var doc = null;
     
     // IE8 cascading access check
     try {
         if (frame.contentWindow) {
             doc = frame.contentWindow.document;
         }
     } catch(err) {
     }

     if (doc) { // successful getting content
         return doc;
     }

     try { // simply checking may throw in ie8 under ssl or mismatched protocol
         doc = frame.contentDocument ? frame.contentDocument : frame.document;
     } catch(err) {
         // last attempt
         doc = frame.document;
     }
     return doc;
 }


	 showLoading();
	 alert( document.getElementById("apply_journal_imports"));
	var formObj = $("form#apply_journal_imports");
	var formURL = $("form#apply_journal_imports").attr("action");

if(window.FormData !== undefined)  // for HTML5 browsers
//	if(false)
	{
	
		var formData = new FormData(document.getElementById("apply_journal_imports"));
		$.ajax({
        	url: formURL,
	        type: 'POST',
			data:  formData,
			mimeType:"multipart/form-data",
			contentType: false,
    	    cache: false,
        	processData:false,
			success: function(data, textStatus, jqXHR)
		    {
					 $("#div_Detail").show();
	                    UI_Resize();
	                    $(window).scrollTop(0);
	                    $("#div_Detail .content > .header > .title").html("匯入");
	                    $("#div_Detail .content > .contain").empty().html(data);
	                    closeLoading();
		    },
		  	error: function(jqXHR, textStatus, errorThrown) 
	    	{
		  		 goAlert("結果",XMLHttpRequest.responseText);
                 closeLoading();
	    	} 	        
	   });
        e.preventDefault();
        e.unbind();
   }
   else  //for olden browsers
	{
		//generate a random id
		var  iframeId = 'unique' + (new Date().getTime());

		//create an empty iframe
		var iframe = $('<iframe src="javascript:false;" name="'+iframeId+'" />');

		//hide it
		iframe.hide();

		//set form target to iframe
		formObj.attr('target',iframeId);

		//Add iframe to body
		iframe.appendTo('body');
		iframe.load(function(e)
		{
			var doc = getDoc(iframe[0]);
			var docRoot = doc.body ? doc.body : doc.documentElement;
			var data = docRoot.innerHTML;
			 $("#div_Detail").show();
             UI_Resize();
             $(window).scrollTop(0);
             $("#div_Detail .content > .header > .title").html("匯入");
             $("#div_Detail .content > .contain").empty().html(data);
             closeLoading();
		});
	}
	
}

</script>
</head>
<body>
	<s:form action="apply.journal.list" namespace="/crud" method="post"
		onsubmit="return false;">
		<div class="tabs-box">
			<div>
				<a id="tabs-items_A" class="tabs-items-hover"
					onclick="showTabsContain('A')"><span
					class="tabs-items-hover-span">查詢</span></a>
			</div>
			<div id="TabsContain_A" class="tabs-contain">
				<table border="0" cellspacing="4" cellpadding="0">

					<tbody>
						<tr>
							<td align="left"><select name="option"
								id="listForm_searchCondition">
									<c:set var="option">
										<%=request.getParameter("option")%>
									</c:set>
									<c:choose>
										<c:when test="${option=='entity.chineseTitle' }">
											<option value="entity.chineseTitle" selected="selected">中文刊名</option>
											<option value="entity.englishTitle">英文刊名</option>
											<option value="entity.issn">ISSN</option>
										</c:when>
										<c:when test="${option=='entity.englishTitle' }">
											<option value="entity.chineseTitle">中文刊名</option>
											<option value="entity.englishTitle" selected="selected">英文刊名</option>
											<option value="entity.issn">ISSN</option>
										</c:when>
										<c:when test="${option=='entity.issn' }">
											<option value="entity.chineseTitle">中文刊名</option>
											<option value="entity.englishTitle">英文刊名</option>
											<option value="entity.issn" selected="selected">ISSN</option>
										</c:when>
										<c:otherwise>
											<option value="entity.chineseTitle">中文刊名</option>
											<option value="entity.englishTitle">英文刊名</option>
											<option value="entity.issn">ISSN</option>
										</c:otherwise>
									</c:choose>
							</select></td>
							<c:choose>
								<c:when test="${option=='entity.chineseTitle' }">
									<td align="left"><input type="text"
										name="entity.chineseTitle" maxlength="20" id="search"
										class="input_text"
										value="<%if (request
								.getParameter(request.getParameter("option")) != null) {
							out.print(request.getParameter(request
									.getParameter("option")));
						}%>">
									</td>
								</c:when>
								<c:when test="${option=='entity.englishTitle' }">
									<td align="left"><input type="text"
										name="entity.englishTitle" maxlength="20" id="search"
										class="input_text"
										value="<%if (request
								.getParameter(request.getParameter("option")) != null) {
							out.print(request.getParameter(request
									.getParameter("option")));
						}%>"></td>
								</c:when>
								<c:when test="${option=='entity.issn' }">
									<td align="left"><input type="text" name="entity.issn"
										maxlength="20" id="search" class="input_text"
										value="<%if (request
								.getParameter(request.getParameter("option")) != null) {
							out.print(request.getParameter(request
									.getParameter("option")));
						}%>"></td>
								</c:when>
								<c:otherwise>
									<td align="left"><input type="text"
										name="entity.chineseTitle" maxlength="20" id="search"
										class="input_text"></td>
								</c:otherwise>
							</c:choose>
							<td align="left"><a class="state-default"
								onclick="goSearch();">查詢</a>&nbsp;&nbsp;</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div id="div_nav">
			目前位置：<span>帳戶管理</span> &gt; <span>期刊</span>
		</div>
		<div class="list-box">
			<div class="list-buttons">
				<c:choose>
					<c:when
						test="${(not empty ds.pager.totalRecord)&& (0 ne ds.pager.totalRecord) }">
						<a class="state-default" onclick="allSelect(1);">全選</a>
						<a class="state-default" onclick="allSelect(0);">取消</a>
						<a class="state-default" onclick="goAdd();">新增</a>
						<a class="state-default" onclick="goDelete();">刪除</a>
					</c:when>
					<c:otherwise>
						<a class="state-default" onclick="goAdd();">新增</a>
					</c:otherwise>
				</c:choose>
			</div>
			<table cellspacing="1" class="list-table">
				<tbody>
					<tr>
						<td colspan="8" class="topic">基本設定</td>
					</tr>
					<tr>
						<th width="50" align="center">&nbsp;</th>
						<th>名稱</th>
						<th>種類</th>
						<th>建立者</th>
						<th>更新者</th>
						<th>操作</th>
					</tr>
					<c:forEach var="item" items="${ds.results}" varStatus="status">
						<tr>
							<td align="center" class="td_first" nowrap><input
								type="checkbox" class="checkbox" name="checkItem"
								value="${item.serNo}"></td>
							<td>${item.englishTitle }</td>
							<td align="center">${item.resourcesBuyers.rType.type }</td>
							<td>${item.resourcesBuyers.cUid }</td>
							<td align="center">${item.resourcesBuyers.uUid }</td>
							<td align="center"><a class="state-default2"
								onclick="goView(${item.serNo });"><span
									class="icon-default icon-view"></span>檢視</a> <a
								class="state-default2" onclick="goUpdate(${item.serNo});"><span
									class="icon-default icon-edit"></span>修改</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="page-box" align="right">
				<table border="0" cellspacing="0" cellpadding="0">
					<tbody>

						<c:if test="${ds.pager.totalRecord > 0 }">
							<tr>
								<td><jsp:include page="/WEB-INF/jsp/layout/pagination.jsp">
										<jsp:param name="namespace" value="/crud" />
										<jsp:param name="action" value="apply.journal.list" />
										<jsp:param name="pager" value="${ds.pager}" />
										<jsp:param name="recordPerPage"
											value="${ds.pager.recordPerPage}" />
										<jsp:param name="detail" value="0" />
									</jsp:include></td>
								<td><c:set var="pageFactor"
										value="${ds.pager.totalRecord/ds.pager.recordPerPage}" /> <c:set
										var="totalPage">
										<fmt:formatNumber type="number" pattern="#"
											value="${pageFactor+(1-(pageFactor%1))%1}" />
									</c:set> 每頁顯示 <select name="recordPerPage" id="listForm_pageSize"
									onchange="chagePageSize(this.value,${ds.pager.recordPoint })">
										<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
										<option value="5">5</option>
										<option value="10">10</option>
										<option value="20">20</option>
										<option value="50">50</option>
								</select> 筆紀錄, 第 <input id="listForm_currentPageHeader"
									value="${ds.pager.currentPage }" type="number" min="1"
									max="${totalPage }" onchange="gotoPage(this.value)"> 頁,
									共<span class="totalNum">${totalPage }</span>頁</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
			<div class="detail_note">
				<div class="detail_note_title">Note</div>
				<div class="detail_note_content">
					<c:if test="${0 eq ds.pager.totalRecord}">
						<span>查無資料</span>
					</c:if>
				</div>
			</div>
		</div>
	</s:form>

	<s:form namespace="/crud" action="apply.journal.imports"
		enctype="multipart/form-data" method="post">
		<input type="file" id="file" name="file">
		<s:submit value="IMPORT"></s:submit>
	</s:form>
	<button id="ports" class="state-default" onclick="xxxx();">匯入</button>
	<s:if test="hasActionMessages()">
		<script language="javascript" type="text/javascript">
            var msg = "";
            <s:iterator value="actionMessages">msg += '<s:property escape="false"/><br>';
            </s:iterator>;
            goAlert('訊息', msg);
        </script>
	</s:if>
	<s:if test="hasActionErrors()">
		<script language="javascript" type="text/javascript">
			var msg = "";
			<s:iterator value="actionErrors">
			msg += '<s:property escape="false"/><br>';
			</s:iterator>;
			goAlert('訊息', msg);
		</script>
	</s:if>
</body>
</html>