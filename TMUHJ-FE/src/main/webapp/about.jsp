<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/jsp/layout/css.jsp" />
<!DOCTYPE HTML>
<html>
<head>
<title>跨司署電子資料庫索引查詢平台</title>
<script type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//
	});
</script>
</head>

<body>
	<c:if test="${empty login.userId}">
		<%
			response.sendRedirect(request.getContextPath()
						+ "/authorization/userEntry.action");
		%>
	</c:if>
	<div id="wrapper">
		<jsp:include page="/WEB-INF/jsp/layout/menu.jsp" />
		<div id="container">
			<div id="main_b_box">
				<!-- 內容開始 -->
				建置中...
				<!-- 內容結束 -->
			</div>
		</div>
		<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
	</div>
</body>
</html>
