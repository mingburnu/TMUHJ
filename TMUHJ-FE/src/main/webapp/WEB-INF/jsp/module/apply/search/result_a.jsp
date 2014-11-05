<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="/WEB-INF/jsp/layout/css.jsp" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>跨司署電子資料庫索引查詢平台</title>
<script type="text/javascript" src="jquery-1.7.2.min.js">
	
</script>
<script type="text/javascript">
	$(document).ready(function() {
		//
	});
</script>
</head>

<body>
	<div id="wrapper">
		<jsp:include page="/WEB-INF/jsp/layout/menu.jsp" />

		<div id="container">
			<div id="main_b_box">
				<!-- 內容開始 -->
				<div class="result">

					<c:choose>
						<c:when
							test="${(empty cusSerNo) && (not empty keywords) && (empty option)}">
							<div class="pager">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<s:form action="apply.database.query.action">
											<td align="left" class="p_01">共 <strong>${ds.pager.totalRecord}</strong>
												筆記錄， 每頁顯示筆數 <select name="recordPerPage"
												id="apply_database_query_action_recordPerPage"
												onchange="document.getElementById('apply_database_query_action');this.form.submit();">
													<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
													<option value="5">5</option>
													<option value="10">10</option>
													<option value="20">20</option>
													<option value="50">50</option>
													<option value="100">100</option>
											</select> <input type="hidden" name="keywords" value="${keywords }" />

											</td>
										</s:form>
										<td align="right" class="p_02"><jsp:include
												page="/WEB-INF/jsp/layout/pagination.jsp">
												<jsp:param name="namespace" value="/crud" />
												<jsp:param name="action" value="apply.database.query" />
												<jsp:param name="pager" value="${ds.pager}" />
												<jsp:param name="keywords" value="${keywords}" />
												<jsp:param name="recordPerPage"
													value="${ds.pager.recordPerPage}" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						</c:when>
						<c:when
							test="${(empty keywords) && (empty option) && (not empty cusSerNo)}">
							<div class="pager">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<s:form action="apply.database.ownerDb.action">
											<td align="left" class="p_01">共 <strong>${ds.pager.totalRecord}</strong>
												筆記錄， 每頁顯示筆數 <select name="recordPerPage"
												id="apply_database_ownerDb_action_recordPerPage"
												onchange="document.getElementById('apply_database_ownerDb_action');this.form.submit();">
													<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
													<option value="5">5</option>
													<option value="10">10</option>
													<option value="20">20</option>
													<option value="50">50</option>
													<option value="100">100</option>
											</select> <input type="hidden" name="cusSerNo" value="${cusSerNo }" />

											</td>
										</s:form>
										<td align="right" class="p_02"><jsp:include
												page="/WEB-INF/jsp/layout/pagination.jsp">
												<jsp:param name="namespace" value="/crud" />
												<jsp:param name="action" value="apply.database.ownerDb" />
												<jsp:param name="pager" value="${ds.pager}" />
												<jsp:param name="cusSerNo" value="${cusSerNo}" />
												<jsp:param name="recordPerPage"
													value="${ds.pager.recordPerPage}" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						</c:when>
						<c:when
							test="${(not empty keywords) && (not empty option) && (empty cusSerNo)}">
							<div class="pager">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<s:form action="apply.database.focus.action">
											<td align="left" class="p_01">共 <strong>${ds.pager.totalRecord}</strong>
												筆記錄， 每頁顯示筆數 <select name="recordPerPage"
												id="apply_database_focus_action_recordPerPage"
												onchange="document.getElementById('apply_database_focus_action');this.form.submit();">
													<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
													<option value="5">5</option>
													<option value="10">10</option>
													<option value="20">20</option>
													<option value="50">50</option>
													<option value="100">100</option>
											</select> <input type="hidden" name="option" value="${option }" /> <input
												type="hidden" name="keywords" value="${keywords }" />
											</td>
										</s:form>
										<td align="right" class="p_02"><jsp:include
												page="/WEB-INF/jsp/layout/pagination.jsp">
												<jsp:param name="namespace" value="/crud" />
												<jsp:param name="action" value="apply.database.focus" />
												<jsp:param name="pager" value="${ds.pager}" />
												<jsp:param name="keywords" value="${keywords}" />
												<jsp:param name="option" value="${option}" />
												<jsp:param name="recordPerPage"
													value="${ds.pager.recordPerPage}" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						</c:when>
					</c:choose>

					<div class="list">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr valign="top">
								<th width="40">序號</th>
								<th>題名</th>
								<th>出版社</th>
								<th>收錄年代</th>
							</tr>
							<c:forEach var="item" items="${ds.results}" varStatus="status">
								<c:set var="num" scope="session" value="${(status.index+1)%2}" />
								<c:set var="orderInt" scope="session"
									value="${ds.pager.offset+(status.index+1)}" />
								<c:set var="dDetail">
									<s:url namespace="/crud" action="apply.database.list">
										<s:param name="serNo">${item.serNo}</s:param>
									</s:url>
								</c:set>
								<c:choose>
									<c:when test="${num > 0}">
										<tr valign="top">
											<td>${orderInt}</td>
											<c:choose>
												<c:when test="${not empty item.dbEngTitle}">
													<td><a href="${dDetail}">${item.dbEngTitle}</a></td>
												</c:when>
												<c:otherwise>
													<td><a href="${dDetail}">${item.dbChtTitle}</a></td>
												</c:otherwise>
											</c:choose>
											<td>${item.publishName}</td>
											<td>${item.indexedYears}</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr valign="top" class="odd">
											<td>${orderInt}</td>
											<c:choose>
												<c:when test="${not empty item.dbEngTitle}">
													<td><a href="${dDetail}">${item.dbEngTitle}</a></td>
												</c:when>
												<c:otherwise>
													<td><a href="${dDetail}">${item.dbChtTitle}</a></td>
												</c:otherwise>
											</c:choose>
											<td>${item.publishName}</td>
											<td>${item.indexedYears}</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</table>
					</div>

					<c:choose>
						<c:when
							test="${(empty cusSerNo) && (not empty keywords) && (empty option)}">
							<div class="pager">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<s:form action="apply.database.query.action">
											<td align="left" class="p_01">共 <strong>${ds.pager.totalRecord}</strong>
												筆記錄， 每頁顯示筆數 <select name="recordPerPage"
												id="apply_database_query_action_recordPerPage"
												onchange="document.getElementById('apply_database_query_action');this.form.submit();">
													<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
													<option value="5">5</option>
													<option value="10">10</option>
													<option value="20">20</option>
													<option value="50">50</option>
													<option value="100">100</option>
											</select> <input type="hidden" name="keywords" value="${keywords }" />

											</td>
										</s:form>
										<td align="right" class="p_02"><jsp:include
												page="/WEB-INF/jsp/layout/pagination.jsp">
												<jsp:param name="namespace" value="/crud" />
												<jsp:param name="action" value="apply.database.query" />
												<jsp:param name="pager" value="${ds.pager}" />
												<jsp:param name="keywords" value="${keywords}" />
												<jsp:param name="recordPerPage"
													value="${ds.pager.recordPerPage}" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						</c:when>
						<c:when
							test="${(empty keywords) && (empty option) && (not empty cusSerNo)}">
							<div class="pager">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<s:form action="apply.database.ownerDb.action">
											<td align="left" class="p_01">共 <strong>${ds.pager.totalRecord}</strong>
												筆記錄， 每頁顯示筆數 <select name="recordPerPage"
												id="apply_database_ownerDb_action_recordPerPage"
												onchange="document.getElementById('apply_database_ownerDb_action');this.form.submit();">
													<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
													<option value="5">5</option>
													<option value="10">10</option>
													<option value="20">20</option>
													<option value="50">50</option>
													<option value="100">100</option>
											</select> <input type="hidden" name="cusSerNo" value="${cusSerNo }" />

											</td>
										</s:form>
										<td align="right" class="p_02"><jsp:include
												page="/WEB-INF/jsp/layout/pagination.jsp">
												<jsp:param name="namespace" value="/crud" />
												<jsp:param name="action" value="apply.database.ownerDb" />
												<jsp:param name="pager" value="${ds.pager}" />
												<jsp:param name="cusSerNo" value="${cusSerNo}" />
												<jsp:param name="recordPerPage"
													value="${ds.pager.recordPerPage}" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						</c:when>
						<c:when
							test="${(not empty keywords) && (not empty option) && (empty cusSerNo)}">
							<div class="pager">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<s:form action="apply.database.focus.action">
											<td align="left" class="p_01">共 <strong>${ds.pager.totalRecord}</strong>
												筆記錄， 每頁顯示筆數 <select name="recordPerPage"
												id="apply_database_focus_action_recordPerPage"
												onchange="document.getElementById('apply_database_focus_action');this.form.submit();">
													<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
													<option value="5">5</option>
													<option value="10">10</option>
													<option value="20">20</option>
													<option value="50">50</option>
													<option value="100">100</option>
											</select> <input type="hidden" name="option" value="${option }" /> <input
												type="hidden" name="keywords" value="${keywords }" />
											</td>
										</s:form>
										<td align="right" class="p_02"><jsp:include
												page="/WEB-INF/jsp/layout/pagination.jsp">
												<jsp:param name="namespace" value="/crud" />
												<jsp:param name="action" value="apply.database.ownerDb" />
												<jsp:param name="pager" value="${ds.pager}" />
												<jsp:param name="keywords" value="${keywords}" />
												<jsp:param name="option" value="${option}" />
												<jsp:param name="recordPerPage"
													value="${ds.pager.recordPerPage}" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						</c:when>
					</c:choose>

				</div>
				<!-- 內容結束 -->
			</div>
		</div>

		<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />

	</div>
</body>
</html>
