<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/jsp/layout/javascript.jsp" />
<jsp:include page="/WEB-INF/jsp/layout/css.jsp" />

<!DOCTYPE HTML>
<html>

<head>
<title>索引系統</title>
</head>

<body class="page-padding">
	<jsp:include page="/WEB-INF/jsp/layout/menu.jsp" />
	<fieldset>
		<legend>使用者管理</legend>

		<%-- 搜尋區塊 --%>
		<s:form action="security.accountNumber.list" namespace="/crud" method="post"
			cssClass="form-search">
			<table class="form-table">
				<tr>
					<td class="form-label"><label class="label">使用者代號</label></td>
					<td><s:textfield name="entity.userId"
							cssClass="input-medium search-query" /></td>
					<td class="form-label"><label class="label">使用者姓名</label></td>
					<td><s:textfield name="entity.userName"
							cssClass="input-medium search-query" /></td>
				</tr>
				<tr>
					<td colspan="4" align="right">
						<div class="btn-group">
							<s:submit cssClass="btn btn-info" value="搜尋" />
							<input type="button" class="btn btn-success" value="新增"
								onclick="document.location='<s:url namespace="/crud" action="security.accountNumber.query" />'" />
						</div>
					</td>
				</tr>
			</table>
		</s:form>

		<%-- 分頁 --%>
		<table style="width: 100%">
			<tr>
				<td><jsp:include page="/WEB-INF/jsp/layout/pagination.jsp">
						<jsp:param name="namespace" value="/crud" />
						<jsp:param name="action" value="security.accountNumber.list" />
						<jsp:param name="pager" value="${ds.pager}" />
					</jsp:include></td>
			</tr>
		</table>

		<%-- List區塊 --%>
		<table class="table table-hover" style="width: 100%">
			<thead>
				<tr class="list-head">
					<th>使用者代號</th>
					<th>使用者姓名</th>
					<th>使用者角色</th>
					<th>使用者信箱</th>
					<th>使用者狀態</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${ds.results}" varStatus="status">
					<c:set var="editPage">
						<s:url namespace="/crud" action="security.accountNumber.query">
							<s:param name="entity.serNo">${item.serNo}</s:param>
						</s:url>
					</c:set>
					<tr class="list-item${status.index mod 2}"
						onmouseup="document.location='${editPage}'">
						<td>${item.userId}</td>
						<td>${item.userName}</td>
						<td>${item.role.role}</td>
						<td>${item.email}</td>
						<td>${item.status.status}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
</body>
</html>