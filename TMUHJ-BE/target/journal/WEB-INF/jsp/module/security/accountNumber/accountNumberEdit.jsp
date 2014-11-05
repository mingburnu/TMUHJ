<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <s:set var="theme" value="'simple'" scope="page" /> --%>

<jsp:include page="/WEB-INF/jsp/layout/javascript.jsp" />
<jsp:include page="/WEB-INF/jsp/layout/css.jsp" />

<!DOCTYPE HTML>
<html>

<head>

<script type="text/javascript">
	$(function() {

	})
</script>

<title>索引系統</title>
</head>

<body class="page-padding">

	<fieldset>
		<legend id="cool">使用者設定</legend>
		<s:form namespace="/crud" method="post" cssClass="form-horizontal">
			<table class="form-table">
				<s:hidden name="entity.serNo" />
				<tr>
					<td class="form-label"><label class="label label-important">使用者代號</label></td>
					<td><s:textfield name="entity.userId" cssClass="input-medium" /></td>
				</tr>

				<%-- 新增頁增加密碼欄位 --%>
				<c:if test="${empty entity.serNo}">
					<tr>
						<td class="form-label"><label class="label label-important">使用者密碼</label></td>
						<td><s:password name="entity.userPw" cssClass="input-medium" /></td>
					</tr>
				</c:if>

				<tr>
					<td class="form-label"><label class="label">使用者姓名</label></td>
					<td><s:textfield name="entity.userName"
							cssClass="input-medium" /></td>
				</tr>
				<tr>
					<td class="form-label"><label class="label">使用者角色</label></td>
					<td><s:select name="entity.role" id="select"
							cssClass="input-medium" list="@j.index.core.enums.Role@values()"
							listKey="name()" listValue="role" /></td>
				</tr>
				<tr>
					<td class="form-label"><label class="label">使用者信箱</label></td>
					<td><input type="email" name="entity.email"
						id="security_accountNumber_query_entity_email" class="input-medium"></td>
				</tr>
				<tr>
					<td class="form-label"><label class="label">使用者狀態</label></td>
					<td><s:select name="entity.status" id="select"
							cssClass="input-medium"
							list="@j.index.core.enums.Status@values()" listKey="name()"
							listValue="status" /></td>
				</tr>
				<tr>
					<td colspan="2" class="form-button">
						<div class="btn-group">
							<c:choose>
								<c:when test="${empty entity.serNo}">
									<s:submit cssClass="btn btn-success"
										action="security.accountNumber.save" value="新增" />
								</c:when>
								<c:otherwise>
									<s:submit cssClass="btn btn-success"
										action="security.accountNumber.update" value="修改" />
								</c:otherwise>
							</c:choose>
							<s:submit cssClass="btn btn-danger"
								action="security.accountNumber.delete" value="刪除" />
							<input type="button" class="btn btn-inverse return" value="關閉"
								onclick="document.location='<s:url namespace="/crud" action="security.accountNumber.list" />'" />
						</div>
					</td>
				</tr>
			</table>
		</s:form>
	</fieldset>

</body>
</html>