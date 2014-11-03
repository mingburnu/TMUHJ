<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/jsp/layout/css.jsp" />
<jsp:include page="/WEB-INF/jsp/layout/art.jsp" />
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
				<div class="security">
					<p>
						<strong>個人資料之蒐集及運用</strong><br /> 將依個人資料保護法及相關法令之規定，不會任意對其他第三者揭露<br />
						<br />
						使用本網站時，本網站將自動收集下列資訊：日期和時間、您所擷取之網頁、您所在之網址、您的瀏覽器種類、您對本網站網頁所做行動（如下載等）及成功與否。這些資訊僅用為改善本網站之效能參考。<br />
						<br /> 監測對本網站造成重大負荷的網址上的行為。<br /> <br />
					</p>
					<p>
						<strong>資訊安全權責與教育訓練</strong><br />
						對處理敏感性、機密性資料之人員及因工作需要須賦予系統管理權限之人員，妥適分工，分散權責並建立評估及考核制度，及視需要建立人員相互支援制度。<br />
						<br /> 對離（休、停）職人員，依據人員離（休、停）職之處理程序辦理，並立即取消使用各項系統資源之權限。<br /> <br />
						依角色及職能為基礎，針對不同層級工作人員，視實際需要辦理資訊安全教育訓練及宣導，促使員工瞭解資訊安全的重要性，各種可能的安全風險，以提高員工資訊安全意識，遵守資訊安全規定。<br />
						<br />
					</p>
					<p>
						<strong>資訊安全作業及保護</strong><br />
						建立處理資訊安全事件之作業程序，並賦予相關人員必要的責任，以便迅速有效處理資訊安全事件。 <br /> <br />
						建立資訊設施及系統的變更管理通報機制，以免造成系統安全上的漏洞。 <br /> <br />
						依據電腦處理個人資料保護法之相關規定，審慎處理及保護個人資訊。 <br /> <br />
						建立系統備援設施，定期執行必要的資料、軟體備份及備援作業，以備發生災害或儲存媒體失效時，可迅速回復正常作業。<br /> <br />
					</p>
					<p>
						<strong>網路安全管理</strong><br />
						與外界網路連接之網點，設立防火牆控管外界與內部網路之資料傳輸及資源存取，並執行嚴謹的身分辨識作業。 <br /> <br />
						機密性及敏感性的資料或文件，不存放在對外開放的資訊系統中，機密性文件不以電子郵件傳送。 <br /> <br />
						定期對內部網路資訊安全設施與防毒進行查核，並更新防毒系統之病毒碼，及各項安全措施。<br /> <br />
					</p>
					<p>
						<strong>系統存取控制管理</strong><br /> 視作業系統及安全管理需求訂定通行密碼核發及變更程序並作成記錄。 <br />
						<br /> 登入各作業系統時，依各級人員執行任務所必要之系統存取權限，由系統管理人員設定賦予權限之帳號與密碼，並定期更新。
					</p>
				</div>

				<!-- 內容結束 -->
			</div>
		</div>
		<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
	</div>
</body>
</html>