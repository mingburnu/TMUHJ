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
</head>
<body>

	<div id="wrapper">
		<jsp:include page="/WEB-INF/jsp/layout/menu.jsp" />

		<div id="container">
			<div id="main_b_box">
				<!-- 內容開始 -->
				<div class="result">

					<c:choose>
						<c:when test="${not empty query}">
							<div class="pager">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<s:form action="apply.journal.query.action">
											<input type="hidden" name="recordPoint"
												value="${ds.pager.recordPoint}">
											<td align="left" class="p_01">共 <strong>${ds.pager.totalRecord}</strong>
												筆記錄， 每頁顯示筆數 <select name="recordPerPage"
												id="apply_journal_query_action_recordPerPage"
												onchange="upperChangeSize(this.value);">
													<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
													<option value="5">5</option>
													<option value="10">10</option>
													<option value="20">20</option>
													<option value="50">50</option>
													<option value="100">100</option>
											</select> <input type="hidden" name="keywords" value="${keywords }" />
											</td>
										</s:form>
										<td align="right" class="p_02"><c:if
												test="${ds.pager.totalRecord > 0 }"><jsp:include
													page="/WEB-INF/jsp/layout/pagination.jsp">
													<jsp:param name="namespace" value="/crud" />
													<jsp:param name="action" value="apply.journal.query" />
													<jsp:param name="pager" value="${ds.pager}" />
													<jsp:param name="keywords" value="${keywords}" />
													<jsp:param name="recordPerPage"
														value="${ds.pager.recordPerPage}" />
												</jsp:include></c:if></td>
									</tr>
								</table>
							</div>
						</c:when>
						<c:when test="${not empty owner}">
							<div class="pager">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<s:form action="apply.journal.owner.action">
											<input type="hidden" name="recordPoint"
												value="${ds.pager.recordPoint}">
											<td align="left" class="p_01">共 <strong>${ds.pager.totalRecord}</strong>
												筆記錄， 每頁顯示筆數 <select name="recordPerPage"
												id="apply_journal_owner_action_recordPerPage"
												onchange="upperChangeSize(this.value);">
													<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
													<option value="5">5</option>
													<option value="10">10</option>
													<option value="20">20</option>
													<option value="50">50</option>
													<option value="100">100</option>
											</select> <input type="hidden" name="cusSerNo" value="${cusSerNo }" />
											</td>
										</s:form>
										<td align="right" class="p_02"><c:if
												test="${ds.pager.totalRecord > 0 }"><jsp:include
													page="/WEB-INF/jsp/layout/pagination.jsp">
													<jsp:param name="namespace" value="/crud" />
													<jsp:param name="action" value="apply.journal.owner" />
													<jsp:param name="pager" value="${ds.pager}" />
													<jsp:param name="cusSerNo" value="${cusSerNo}" />
													<jsp:param name="recordPerPage"
														value="${ds.pager.recordPerPage}" />
												</jsp:include></c:if></td>
									</tr>
								</table>
							</div>
						</c:when>
						<c:when test="${not empty focus}">
							<div class="pager">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<s:form action="apply.journal.focus.action">
											<input type="hidden" name="recordPoint"
												value="${ds.pager.recordPoint}">
											<td align="left" class="p_01">共 <strong>${ds.pager.totalRecord}</strong>
												筆記錄， 每頁顯示筆數 <select name="recordPerPage"
												id="apply_journal_focus_action_recordPerPage"
												onchange="upperChangeSize(this.value);">
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
										<td align="right" class="p_02"><c:if
												test="${ds.pager.totalRecord > 0 }"><jsp:include
													page="/WEB-INF/jsp/layout/pagination.jsp">
													<jsp:param name="namespace" value="/crud" />
													<jsp:param name="action" value="apply.journal.focus" />
													<jsp:param name="pager" value="${ds.pager}" />
													<jsp:param name="keywords" value="${keywords}" />
													<jsp:param name="option" value="${option}" />
													<jsp:param name="recordPerPage"
														value="${ds.pager.recordPerPage}" />
												</jsp:include></c:if></td>
									</tr>
								</table>
							</div>
						</c:when>
					</c:choose>

					<div class="list">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr valign="top">
								<th width="40">序號</th>
								<th width="563">刊名</th>
								<th width="212">出版項</th>
								<th width="65">出版年</th>
							</tr>
							<c:forEach var="item" items="${ds.results}" varStatus="status">
								<c:set var="num" scope="session" value="${(status.index+1)%2}" />
								<c:set var="orderInt" scope="session"
									value="${ds.pager.offset+(status.index+1)}" />
								<c:set var="jDetail">
									<s:url namespace="/crud" action="apply.journal.list">
										<s:param name="serNo">${item.serNo}</s:param>
									</s:url>
								</c:set>
								<c:choose>
									<c:when test="${num > 0}">
										<tr valign="top">
											<td>${orderInt}</td>
											<c:choose>
												<c:when test="${not empty item.englishTitle}">
													<td><a href="${jDetail}">${item.englishTitle}</a></td>
												</c:when>
												<c:otherwise>
													<td><a href="${jDetail}">${item.chineseTitle}</a></td>
												</c:otherwise>
											</c:choose>
											<td><c:choose>
													<c:when test="${not empty item.publishName}">${item.publishName}</c:when>
													<c:otherwise>N/A</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${not empty item.publishYear}">${item.publishYear}</c:when>
													<c:otherwise>N/A</c:otherwise>
												</c:choose></td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr valign="top" class="odd">
											<td>${orderInt}</td>
											<c:choose>
												<c:when test="${not empty item.englishTitle}">
													<td><a href="${jDetail}">${item.englishTitle}</a></td>
												</c:when>
												<c:otherwise>
													<td><a href="${jDetail}">${item.chineseTitle}</a></td>
												</c:otherwise>
											</c:choose>
											<td><c:choose>
													<c:when test="${not empty item.publishName}">${item.publishName}</c:when>
													<c:otherwise>N/A</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${not empty item.publishYear}">${item.publishYear}</c:when>
													<c:otherwise>N/A</c:otherwise>
												</c:choose></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</table>
					</div>

					<c:choose>
						<c:when test="${not empty query}">
							<div class="pager">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<s:form action="apply.journal.query.action">
											<input type="hidden" name="recordPoint"
												value="${ds.pager.recordPoint}">
											<td align="left" class="p_01">共 <strong>${ds.pager.totalRecord}</strong>
												筆記錄， 每頁顯示筆數 <select name="recordPerPage"
												id="apply_journal_query_action_recordPerPage"
												onchange="bottomChangeSize(this.value);">
													<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
													<option value="5">5</option>
													<option value="10">10</option>
													<option value="20">20</option>
													<option value="50">50</option>
													<option value="100">100</option>
											</select> <input type="hidden" name="keywords" value="${keywords }" />

											</td>
										</s:form>
										<td align="right" class="p_02"><c:if
												test="${ds.pager.totalRecord > 0 }"><jsp:include
													page="/WEB-INF/jsp/layout/pagination.jsp">
													<jsp:param name="namespace" value="/crud" />
													<jsp:param name="action" value="apply.journal.query" />
													<jsp:param name="pager" value="${ds.pager}" />
													<jsp:param name="keywords" value="${keywords}" />
													<jsp:param name="recordPerPage"
														value="${ds.pager.recordPerPage}" />
												</jsp:include></c:if></td>
									</tr>
								</table>
							</div>
						</c:when>
						<c:when test="${not empty owner}">
							<div class="pager">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<s:form action="apply.journal.owner.action">
											<input type="hidden" name="recordPoint"
												value="${ds.pager.recordPoint}">
											<td align="left" class="p_01">共 <strong>${ds.pager.totalRecord}</strong>
												筆記錄， 每頁顯示筆數 <select name="recordPerPage"
												id="apply_journal_owner_action_recordPerPage"
												onchange="bottomChangeSize(this.value);">
													<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
													<option value="5">5</option>
													<option value="10">10</option>
													<option value="20">20</option>
													<option value="50">50</option>
													<option value="100">100</option>
											</select> <input type="hidden" name="cusSerNo" value="${cusSerNo }" />

											</td>
										</s:form>
										<td align="right" class="p_02"><c:if
												test="${ds.pager.totalRecord > 0 }"><jsp:include
													page="/WEB-INF/jsp/layout/pagination.jsp">
													<jsp:param name="namespace" value="/crud" />
													<jsp:param name="action" value="apply.journal.owner" />
													<jsp:param name="pager" value="${ds.pager}" />
													<jsp:param name="cusSerNo" value="${cusSerNo}" />
													<jsp:param name="recordPerPage"
														value="${ds.pager.recordPerPage}" />
												</jsp:include></c:if></td>
									</tr>
								</table>
							</div>
						</c:when>
						<c:when test="${not empty focus}">
							<div class="pager">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<s:form action="apply.journal.focus.action">
											<input type="hidden" name="recordPoint"
												value="${ds.pager.recordPoint}">
											<td align="left" class="p_01">共 <strong>${ds.pager.totalRecord}</strong>
												筆記錄， 每頁顯示筆數 <select name="recordPerPage"
												id="apply_journal_focus_action_recordPerPage"
												onchange="bottomChangeSize(this.value);">
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
										<td align="right" class="p_02"><c:if
												test="${ds.pager.totalRecord > 0 }"><jsp:include
													page="/WEB-INF/jsp/layout/pagination.jsp">
													<jsp:param name="namespace" value="/crud" />
													<jsp:param name="action" value="apply.journal.focus" />
													<jsp:param name="pager" value="${ds.pager}" />
													<jsp:param name="keywords" value="${keywords}" />
													<jsp:param name="option" value="${option}" />
													<jsp:param name="recordPerPage"
														value="${ds.pager.recordPerPage}" />
												</jsp:include></c:if></td>
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
