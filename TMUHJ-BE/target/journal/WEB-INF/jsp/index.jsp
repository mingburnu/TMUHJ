<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/jsp/layout/css.jsp" />
<jsp:include page="/WEB-INF/jsp/layout/art.jsp" />
<!DOCTYPE HTML>
<html>
<head>
<title>跨司署電子資料庫索引查詢平台</title>
<script type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				$("input[name='type']").change(
						function() {
							var item = $("input[name='type']:checked").val();
							$("form").attr("id", "apply_" + item + "_query");
							$("form").attr("name", "apply_" + item + "_query");
							$("form").attr(
									"action",
									"<c:url value = '/'/>" + "crud/apply."
											+ item + ".query.action");
						});
			});

	function form_reset() {
		$("input[name='keywords']").val("");
	}
	function form_sumbit() {
		var msg = "";
		if ($(".v_keyword").val() == "") {
			msg += "．請輸入關鍵字。";
		}
		if (msg != "") {
			alert(msg);
		} else {
			$("form").submit();
		}
	}
</script>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="/WEB-INF/jsp/layout/menu.jsp" />
		<div id="container">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="table_container">
				<tr valign="top">
					<td class="L">
						<div id="main_box">
							<!-- 內容開始 -->
							<s:form action="apply.database.query" namespace="/crud"
								method="post">
								<table width="100%" border="0" cellpadding="0" cellspacing="0"
									class="table_02">
									<tr valign="middle">
										<td class="t_01"><img
											src="<c:url value = '/'/>resources/images/txt_01.png"></td>
										<td class="t_02"><label><input name="type"
												class="v_type" value="database" type="radio" checked>
												資料庫</label> <label><input name="type" class="v_type"
												value="ebook" type="radio"> 電子書</label> <label><input
												name="type" class="v_type" value="journal" type="radio">
												期刊</label> <label><input name="type" class="v_type"
												value="customer" type="radio"> 單位名稱</label></td>
									</tr>
									<tr valign="middle">
										<td class="t_01"><img
											src="<c:url value = '/'/>resources/images/txt_02.png"></td>
										<td class="t_02">
											<div class="input_01">
												<span><input class="v_keyword" name="keywords"
													type="text" /></span>
											</div>
										</td>
									</tr>
									<tr valign="middle">
										<td class="t_03" colspan="2" align="center"><a
											class="btn_01" href="javascript:void(0);"
											onClick="form_reset();">重 新 填 寫</a> <a class="btn_01"
											href="#" onClick="form_sumbit();">開 始 查 詢</a></td>
									</tr>
								</table>
							</s:form>

							<!-- 內容結束 -->
						</div>
					</td>
					<td class="R"><jsp:include
							page="/WEB-INF/jsp/layout/sideBox.jsp" /></td>
				</tr>
			</table>
		</div>
		<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
	</div>
</body>
</html>
