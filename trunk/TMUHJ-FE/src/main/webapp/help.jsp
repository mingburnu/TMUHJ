<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/jsp/layout/css.jsp" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
				<div class="help">

					<div class="help_txt">
						<div>
							<strong>說明</strong>
						</div>
						<div>一、建議在檔案名稱按滑鼠右鍵用「另存目標」下載後再開啟。</div>
						<div>
							二、如果PDF檔無法開啟，請至Adobe官網下載 <a target="_blank"
								href="http://get.adobe.com/tw/reader/">Adobe Reader</a> 並安裝。
						</div>
					</div>

					<div class="help_item">
						<div>
							<strong>前台使用說明</strong>
						</div>
						<div class="text">
							直接下載 <a href="<c:url value = '/'/>resources/download/跨司署電子資源索引檢索平台_前台使用說明_v1.pdf">跨司署電子資源索引檢索平台_前台使用說明_v1.pdf</a>
							<span>(1.51.MB)</span>
						</div>
					</div>

					<div class="help_item">
						<div>
							<strong>管理系統使用說明</strong>
						</div>
						<div class="text">
							直接下載 <a href="<c:url value = '/'/>resources/download/跨司署電子資源索引檢索平台_管理系統使用說明_v1.pdf">跨司署電子資源索引檢索平台_管理系統使用說明_v1.pdf</a>
							<span>(1.50.MB)</span>
						</div>
					</div>

				</div>
				<!-- 內容結束 -->
			</div>
		</div>
		<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
	</div>
</body>
</html>
