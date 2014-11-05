<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/jsp/layout/javascript.jsp" />
<jsp:include page="/WEB-INF/jsp/layout/css.jsp" />

<!DOCTYPE HTML>
<html>
<head>
<title>跨司署電子資料庫索引查詢平台</title>
</head>

<body>
	<h1>ERROR!</h1>
	<h2>發生錯誤 please check the console's log!</h2>
	<div class="alert alert-error">
		<s:property value="%{exception.message}" />
		<br />
		<s:property value="%{exceptionStack}" />
	</div>
</body>
</html>
