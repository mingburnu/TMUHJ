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
	$(document).ready(
			function() {
				$("input[name='type']").change(
						function() {
							var item = $("input[name='type']:checked").val();
							$("form").attr("id", "apply_" + item + "_focus");
							$("form").attr("name", "apply_" + item + "_focus");
							$("form").attr(
									"action",
									"<c:url value = '/'/>" + "crud/apply."
											+ item + ".focus.action");
							$("input[type='hidden']").removeAttr("name");
							$('input#' + item).attr("name", "option");
						});
			});

	$(document).ready(function() {
		do_event();
		showType('database');
	});

	function clickItem(arg) {
		var currentValue = $(arg).html();
		$(arg).parent().parent().parent().find('input[type="hidden"]').attr(
				"value", currentValue);
		$(arg).parent().parent().parent().find("div a").html(currentValue);
	}
	function do_event() {
		$('.select_01 div a').click(function(e) {
			$('.select_01 ul').hide();
			$(this).parent().next('ul').show();

			return false;
		});

		$('body').click(function(e) {
			$('.select_01 ul').hide();
		});
	}
	function showType(arg) {
		$('.select_database').hide();
		$('.select_ebook').hide();
		$('.select_journal').hide();
		//
		$('.select_' + arg).show();
	}

	function form_reset() {
		$("input[name='keywords']").val("");
	}
	function form_sumbit() {
		var msg = "";
		if ($(".v_keyword").val().trim() == "") {
			msg += "．請輸入關鍵字。";
		}
		if (msg != "") {
			$(".v_keyword").val("");
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
							<s:form action="apply.database.focus" namespace="/crud"
								method="post">
								<table width="100%" border="0" cellpadding="0" cellspacing="0"
									class="table_02">
									<tr valign="middle">
										<td class="t_01"><img
											src="<c:url value = '/'/>resources/images/txt_01.png"></td>
										<td class="t_02"><label><input name="type"
												class="v_type" value="database" type="radio"
												onClick="showType(this.value);" checked> 資料庫</label> <label><input
												name="type" class="v_type" value="ebook" type="radio"
												onClick="showType(this.value);"> 電子書</label> <label><input
												name="type" class="v_type" value="journal" type="radio"
												onClick="showType(this.value);"> 期刊</label></td>
									</tr>
									<tr valign="middle">
										<td class="t_01"><img
											src="<c:url value = '/'/>resources/images/txt_02.png"></td>
										<td class="t_02">

											<table border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														<div class="select_01 select_database">
															<input type="hidden" value="中文題名" name="option"
																id="database" />
															<div>
																<a href="javascript:void(0);">中文題名</a>
															</div>
															<ul>
																<li><a href="javascript:void(0);"
																	onClick="clickItem(this);">中文題名</a></li>
																<li><a href="javascript:void(0);"
																	onClick="clickItem(this);">英文題名</a></li>
																<li><a href="javascript:void(0);"
																	onClick="clickItem(this);">出版社</a></li>
																<li><a href="javascript:void(0);"
																	onClick="clickItem(this);">內容描述</a></li>
															</ul>
														</div>

														<div class="select_01 select_ebook">
															<input type="hidden" value="書名" id="ebook" />
															<div>
																<a href="javascript:void(0);">書名</a>
															</div>
															<ul>
																<li><a href="javascript:void(0);"
																	onClick="clickItem(this);">書名</a></li>
																<li><a href="javascript:void(0);"
																	onClick="clickItem(this);">ISBN</a></li>
																<li><a href="javascript:void(0);"
																	onClick="clickItem(this);">出版社</a></li>
																<li><a href="javascript:void(0);"
																	onClick="clickItem(this);">作者</a></li>
															</ul>
														</div>

														<div class="select_01 select_journal">
															<input type="hidden" value="中文刊名" id="journal" />
															<div>
																<a href="javascript:void(0);">中文刊名</a>
															</div>
															<ul>
																<li><a href="javascript:void(0);"
																	onClick="clickItem(this);">中文刊名</a></li>
																<li><a href="javascript:void(0);"
																	onClick="clickItem(this);">英文刊名</a></li>
																<li><a href="javascript:void(0);"
																	onClick="clickItem(this);">英文縮寫</a></li>
																<li><a href="javascript:void(0);"
																	onClick="clickItem(this);">出版商</a></li>
																<li><a href="javascript:void(0);"
																	onClick="clickItem(this);">ISSN</a></li>
															</ul>
														</div>

													</td>
													<td><div class="input_01">
															<span><input class="v_keyword" type="text"
																name="keywords" /></span>
														</div></td>
												</tr>
											</table>

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
