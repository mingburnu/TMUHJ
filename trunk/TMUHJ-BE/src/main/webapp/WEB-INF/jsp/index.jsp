<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首頁 - 用戶資料查詢</title>
<link id="style_main" rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/jquery.autocomplete.css" />
<link id="style_main" rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/default.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.autocomplete.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/common.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		// 設定Panel初始化
		setPanel([ "1" ]);
		changePanel("1");
	});

	// 表單驗證
	function check() {
		/*
		// Journal ID 檢核
		var regExpWord =/[^0-9]/;
		var jIdObj = $("#conditions input[name='query_journalId']");
		if ( regExpWord.test( jIdObj.val()))
		{
		    myAlert("Journal ID 不可輸入非數字字元！");
		    jIdObj.focus();
		    return false;
		}
		 */
		return true;
	}

	function send() {
		if (!check())
			return;

		showLoading();
		$(".content_group #content_" + currentPanel + " form").submit();
	}
</script>
</head>
<body>
	<%@include file="/WEB-INF/jsp/layout/header.jsp"%>
	<div id="contaner">
		<div id="contaner_box" align="center">
			<div style="width: 465px;">
				<!--Panel start -->
				<div class="panel_box">
					<div class="title_group">
						<ul>
							<li id="title_1"><a href="javascript:changePanel('1')"><span>用戶資料查詢</span></a></li>
						</ul>
					</div>
					<div class="content_group">
						<div id="content_1">
							<form method="GET" action="journal_query.action">
								<table>

									<tr>
										<td align="right">用戶代碼</td>
										<td><input class="input_text" type="text"
											name="entity.userId" /></td>
									</tr>
									<tr>
										<td align="right">用戶名稱</td>
										<td><input class="input_text" type="text"
											name="entity.userName" /></td>
									</tr>
									<tr>
										<td align="right">建立者</td>
										<td><select name="entity.cUid">
												<option value="">全部</option>
												<c:forEach var="item" items="${allcUid}" varStatus="status">
													<option value="${item}">${item}</option>
												</c:forEach>
										</select></td>
									</tr>

								</table>

							</form>

							<!-- ${cUid}-->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/jsp/layout/menu.jsp"%>
</body>
</html>