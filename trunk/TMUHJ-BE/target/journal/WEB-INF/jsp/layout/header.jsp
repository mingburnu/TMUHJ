<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
div.top.bar {
	display: inline;
}
</style>
<c:if test="${empty login.userId}">
	<%
		String root = request.getContextPath();
			response.sendRedirect(root + "/authorization/userEntry.action");
	%>
</c:if>
<div class="top bar">登入者：</div>
<div id="userId" class="top bar">${login.userId}</div>
<div class="top bar">-</div>
<div id="userName" class="top bar">${login.userName}</div>