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
function goSearch(){
    goMain("<%=request.getContextPath()%>/crud/apply.beLogs.list.action",
			"#apply_beLogs_list", "");
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
    goMain('<c:url value = '/'/>crud/apply.beLogs.paginate.action','#apply_beLogs_list','&pager.offset='+offset+'&pager.currentPage='+page);
}

//變更顯示筆數
function chagePageSize(recordPerPage,recordPoint){
        goMain('<c:url value = '/'/>crud/apply.beLogs.paginate.action','#apply_beLogs_list','&recordPerPage='+recordPerPage+'&recordPoint='+recordPoint);
}

//匯出
function goExport(){
	var data=$("#apply_beLogs_list").serialize();
	var url='<%=request.getContextPath()%>/crud/apply.beLogs.exports.action?'+ data;
	window.open(url, "_top");
}
</script>
</head>
<body>
	<s:form action="apply.beLogs.list" namespace="/crud" method="post"
		onsubmit="return false;">
		<div class="tabs-box">
			<div>
				<a id="tabs-items_A" class="tabs-items-hover"
					onclick="showTabsContain('A')"><span
					class="tabs-items-hover-span">查詢</span></a>
			</div>
			<div id="TabsContain_A" class="tabs-contain">
				<table cellspacing="4" cellpadding="0" border="0">
					<c:set var="customer">
					</c:set>
					<tbody>
						<tr>
							<th align="right">查詢統計範圍：</th>
							<td align="left"><input type="date" name="start"
								class="input_text" value="${startDate }"> 至&nbsp;&nbsp<input
								type="date" name="end" class="input_text" value="${endDate }"></td>
						</tr>
						<tr>
							<th align="right">用戶名稱：</th>
							<td><input type="text" class="input_text" name="customer"
								value="<%if (request.getParameter("customer") != null) {
					out.print(request.getParameter("customer"));
				}%>">
								<a class="state-default" onclick="goSearch()">查詢</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div id="div_nav">
			目前位置：<span>統計資訊</span> &gt; <span>登入次數統計</span>
		</div>
		<div class="list-box">
			<div class="list-buttons">
				<c:choose>
					<c:when
						test="${(not empty ds.pager.totalRecord)&& (0 ne ds.pager.totalRecord) }">
						<a class="state-default" onclick="goExport()">匯出</a>
					</c:when>

				</c:choose>
			</div>
			<table cellspacing="1" class="list-table">
				<tbody>
					<tr>
						<td colspan="8" class="topic">基本設定</td>
					</tr>
					<tr>
						<th>年月</th>
						<th>名次</th>
						<th>帳號</th>
						<th>用戶姓名</th>
						<th>用戶身分</th>
						<th>客戶名稱</th>
						<th>狀態</th>
						<th>次數</th>
					</tr>
					<c:forEach var="item" items="${ds.results}" varStatus="status">
						<tr>
							<td>${startDate }~${endDate }</td>
							<td align="center">${item.rank }</td>
							<td>${item.accountNumber.userId }</td>
							<td align="center">${item.accountNumber.userName }</td>
							<td align="center">${item.accountNumber.role.role }</td>
							<td align="center">${item.customer.name }</td>
							<td>${item.accountNumber.status.status }</td>
							<td>${item.count }</td>
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
										<jsp:param name="action" value="apply.beLogs.paginate" />
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
			<s:iterator value="actionErrors">msg += '<s:property escape="false"/><br>';</s:iterator>;
			goAlert('訊息', msg);
		</script>
	</s:if>
	<iframe name="iframe1" style="display: none;"></iframe>
</body>
</html>