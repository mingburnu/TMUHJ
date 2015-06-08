<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>跨司署電子資料庫索引查詢平台</title>
<style type="text/css">
#wrapper {
	margin: 0;
	padding: 0;
	font-size: 15px;
}

.btn_01 {
	display: -moz-inline-stack;
	display: inline-block;
	text-decoration: none;
	width: 100px;
	height: 27px;
	line-height: 27px;
	margin: 0 10px 0 0;
	padding: 0;
	font-size: 13px;
	color: #fff;
	background: url("<c:url value = '/'/>resources/images/btn_01.png") 0 0
		no-repeat;
	text-align: center;
}

.btn_01:hover {
	background: url("<c:url value = '/'/>resources/images/btn_01.png") 0
		-30px no-repeat;
}

.login_table {
	margin: 50px 0 0 0;
	padding: 5px;
	border: 1px solid #ccc;
}

.login_table th {
	margin: 0;
	padding: 0 0 10px 30px;
}

.login_table td {
	margin: 0;
	padding: 0 0 10px 0;
}

.input_02 {
	width: 200px;
	height: 35px;
	background: url("<c:url value = '/'/>resources/images/input_02.png") 0 0
		no-repeat;
	margin: 0;
	padding: 0 0 0 10px;
}

.input_02 span {
	display: block;
	background: url("<c:url value = '/'/>resources/images/input_02.png")
		100% 0 no-repeat;
	margin: 0;
	padding: 5px 10px 5px 0;
}

.input_02 span input {
	width: 100%;
	border: none;
	height: 25px;
	line-height: 25px;
	background: #fafafa;
	font-size: 15px;
}

.input_02 span input:focus {
	outline: none;
}

.footer {
	font-size: 12px;
	margin: 10px 0 0 0;
	padding: 0;
	color: #333;
}

form {
	margin-bottom: 1em;
}

input:-webkit-autofill {
	-webkit-box-shadow: 0 0 0px 1000px #fafafa inset;
}
</style>
<script type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery-1.7.2.min.js"></script>
<s:if test="hasActionErrors()">
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							var msg = "";
							<s:iterator value="actionErrors">msg += '<s:property escape="true"/>\r\n';
							</s:iterator>;
							alert(msg);
						});
	</script>
</s:if>
<script type="text/javascript">
	function form_sumbit() {
		var msg = "";
		if ($(".v_username").val() == "") {
			msg += "．請輸入您的帳號。\r\n";
		}
		if ($(".v_password").val() == "") {
			msg += "．請輸入您的密碼。\r\n";
		}
		if (msg != "") {
			alert(msg);
			return false;
		} else {
			$('#login').submit();
		}
	}
</script>
</head>
<c:if
	test="${(login.role =='系統管理員') || (login.role =='維護人員') || (login.role =='管理員') || (login.role =='使用者')}">
	<%
		response.sendRedirect(request.getContextPath()
					+ "/page/home.action");
	%>
</c:if>
<body>
	<div id="wrapper" align="center">
		<s:form action="login" namespace="/authorization" method="post"
			name="form_01">
			<table border="0" cellpadding="0" cellspacing="0" class="login_table">
				<tr>
					<td colspan="2"><img
						src="<c:url value = '/'/>resources/images/login_box_header.png"
						width="370" height="75"></td>
				</tr>
				<tr>
					<th><img src="<c:url value = '/'/>resources/images/txt_03.png"></th>
					<td><div class="input_02">
							<span><input class="v_username" type="text"
								name="user.userId" /></span>
						</div></td>
				</tr>
				<tr>
					<th><img src="<c:url value = '/'/>resources/images/txt_04.png"></th>
					<td><div class="input_02">
							<span><input class="v_password" type="password"
								name="user.userPw" /></span>
						</div></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><a class="btn_01"
						onClick="form_sumbit();">登 入</a></td>
				</tr>
			</table>
		</s:form>
		<div class="footer" align="center">
			<div>103年推動全國實證醫學科技交流與建置電子資源知識共享及時應用計畫</div>
			<div>最佳瀏覽環境：螢幕解析度 1024x768以上</div>
		</div>
	</div>
</body>
</html>