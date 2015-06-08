<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function view(serNo){
	var action=$("form:eq(0)").attr("action");
	var pageParameter="?pager.currentPage="+"${ds.pager.currentPage}"+"&pager.offset="+"${ds.pager.offset}";
	var data=$("form:eq(0)").serialize();
	var backURL=action.concat(pageParameter,"&",data);
	backURL=backURL.replace(/\?/g,"？").replace(/\&/g,"＆");
	var url="<%=request.getContextPath()%>"+"/crud/apply.ebook.view.action?serNo="+serNo+"&currentURL="+backURL;
		$.ajax({
			url: url,
			success: function(result){
	        $("#container").html(result);
	    }
		});
	}
</script>
<style>
.list td a:hover {
	cursor: pointer;
}
</style>
<div id="main_b_box">
	<!-- 內容開始 -->
	<div class="result">

		<c:choose>
			<c:when test="${not empty list}">
				<div class="pager">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="left" class="p_01"><s:form
									action="apply.ebook.list.action">
									<input type="hidden" name="recordPoint"
										value="${ds.pager.recordPoint}">共 <strong>${ds.pager.totalRecord}</strong>
												筆記錄， 每頁顯示筆數 <select name="recordPerPage"
										id="apply_ebook_list_action_recordPerPage"
										onchange="upperChangeSize(this.value);">
										<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
										<option value="5">5</option>
										<option value="10">10</option>
										<option value="20">20</option>
										<option value="50">50</option>
										<option value="100">100</option>
									</select>
									<c:set var="keywords">
										<c:out value="${keywords }"></c:out>
									</c:set>
									<input type="hidden" name="keywords" value="${keywords }" />
								</s:form></td>
							<td align="right" class="p_02"><c:if
									test="${ds.pager.totalRecord > 0 }"><jsp:include
										page="/WEB-INF/jsp/layout/pagination.jsp">
										<jsp:param name="namespace" value="/crud" />
										<jsp:param name="action" value="apply.ebook.list" />
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
							<td align="left" class="p_01"><s:form
									action="apply.ebook.owner.action">
									<input type="hidden" name="recordPoint"
										value="${ds.pager.recordPoint}">共 <strong>${ds.pager.totalRecord}</strong>
									筆記錄， 每頁顯示筆數 <select name="recordPerPage"
										id="apply_ebook_owner_action_recordPerPage"
										onchange="upperChangeSize(this.value);">
										<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
										<option value="5">5</option>
										<option value="10">10</option>
										<option value="20">20</option>
										<option value="50">50</option>
										<option value="100">100</option>
									</select>
									<input type="hidden" name="cusSerNo" value="${cusSerNo }" />
								</s:form></td>
							<td align="right" class="p_02"><c:if
									test="${ds.pager.totalRecord > 0 }"><jsp:include
										page="/WEB-INF/jsp/layout/pagination.jsp">
										<jsp:param name="namespace" value="/crud" />
										<jsp:param name="action" value="apply.ebook.owner" />
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
							<td align="left" class="p_01"><s:form
									action="apply.ebook.focus.action">
									<input type="hidden" name="recordPoint"
										value="${ds.pager.recordPoint}">共 <strong>${ds.pager.totalRecord}</strong>
									筆記錄， 每頁顯示筆數 <select name="recordPerPage"
										id="apply_ebook_focus_action_recordPerPage"
										onchange="upperChangeSize(this.value);">
										<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
										<option value="5">5</option>
										<option value="10">10</option>
										<option value="20">20</option>
										<option value="50">50</option>
										<option value="100">100</option>
									</select>
									<c:set var="option">
										<c:out value="${option }"></c:out>
									</c:set>
									<c:set var="keywords">
										<c:out value="${keywords }"></c:out>
									</c:set>
									<input type="hidden" name="option" value="${option }" />
									<input type="hidden" name="keywords" value="${keywords }" />
								</s:form></td>
							<td align="right" class="p_02"><c:if
									test="${ds.pager.totalRecord > 0 }"><jsp:include
										page="/WEB-INF/jsp/layout/pagination.jsp">
										<jsp:param name="namespace" value="/crud" />
										<jsp:param name="action" value="apply.ebook.focus" />
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
					<th width="517 px">題名</th>
					<th width="171 px">作者</th>
					<th width="152 px">出版社</th>
				</tr>
				<c:forEach var="item" items="${ds.results}" varStatus="status">
					<c:set var="num" scope="session" value="${(status.index+1)%2}" />
					<c:set var="orderInt" scope="session"
						value="${ds.pager.offset+(status.index+1)}" />
					<c:choose>
						<c:when test="${num > 0}">
							<tr valign="top">
								<td>${orderInt}</td>
								<td><a onclick="view(${item.serNo})"><c:out value="${item.bookName}"/></a></td>
								<td><c:out value="${item.autherName}"/></td>
								<td><c:out value="${item.publishName}"/></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr valign="top" class="odd">
								<td>${orderInt}</td>
								<td><a onclick="view(${item.serNo})"><c:out value="${item.bookName}"/></a></td>
								<td><c:out value="${item.autherName}"/></td>
								<td><c:out value="${item.publishName}"/></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</table>
		</div>

		<c:choose>
			<c:when test="${not empty list}">
				<div class="pager">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="left" class="p_01"><s:form
									action="apply.ebook.list.action">
									<input type="hidden" name="recordPoint"
										value="${ds.pager.recordPoint}">共 <strong>${ds.pager.totalRecord}</strong>
									筆記錄， 每頁顯示筆數 <select name="recordPerPage"
										id="apply_ebook_list_action_recordPerPage"
										onchange="bottomChangeSize(this.value);">
										<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
										<option value="5">5</option>
										<option value="10">10</option>
										<option value="20">20</option>
										<option value="50">50</option>
										<option value="100">100</option>
									</select>
									<c:set var="keywords">
										<c:out value="${keywords }"></c:out>
									</c:set>
									<input type="hidden" name="keywords" value="${keywords }" />
								</s:form></td>
							<td align="right" class="p_02"><c:if
									test="${ds.pager.totalRecord > 0 }"><jsp:include
										page="/WEB-INF/jsp/layout/pagination.jsp">
										<jsp:param name="namespace" value="/crud" />
										<jsp:param name="action" value="apply.ebook.list" />
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
							<td align="left" class="p_01"><s:form
									action="apply.ebook.owner.action">
									<input type="hidden" name="recordPoint"
										value="${ds.pager.recordPoint}">共 <strong>${ds.pager.totalRecord}</strong>
									筆記錄， 每頁顯示筆數 <select name="recordPerPage"
										id="apply_ebook_owner_action_recordPerPage"
										onchange="bottomChangeSize(this.value);">
										<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
										<option value="5">5</option>
										<option value="10">10</option>
										<option value="20">20</option>
										<option value="50">50</option>
										<option value="100">100</option>
									</select>
									<input type="hidden" name="cusSerNo" value="${cusSerNo }" />
								</s:form></td>
							<td align="right" class="p_02"><c:if
									test="${ds.pager.totalRecord > 0 }"><jsp:include
										page="/WEB-INF/jsp/layout/pagination.jsp">
										<jsp:param name="namespace" value="/crud" />
										<jsp:param name="action" value="apply.ebook.owner" />
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
							<td align="left" class="p_01"><s:form
									action="apply.ebook.owner.action">
									<input type="hidden" name="recordPoint"
										value="${ds.pager.recordPoint}">共 <strong>${ds.pager.totalRecord}</strong>
									筆記錄， 每頁顯示筆數 <select name="recordPerPage"
										id="apply_ebook_focus_action_recordPerPage"
										onchange="bottomChangeSize(this.value);">
										<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
										<option value="5">5</option>
										<option value="10">10</option>
										<option value="20">20</option>
										<option value="50">50</option>
										<option value="100">100</option>
									</select>
									<c:set var="option">
										<c:out value="${option }"></c:out>
									</c:set>
									<c:set var="keywords">
										<c:out value="${keywords }"></c:out>
									</c:set>
									<input type="hidden" name="option" value="${option }" />
									<input type="hidden" name="keywords" value="${keywords }" />
								</s:form></td>
							<td align="right" class="p_02"><c:if
									test="${ds.pager.totalRecord > 0 }"><jsp:include
										page="/WEB-INF/jsp/layout/pagination.jsp">
										<jsp:param name="namespace" value="/crud" />
										<jsp:param name="action" value="apply.ebook.focus" />
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

