<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/jsp/layout/msg.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		changeFormAction();
	});

	function changeFormAction() {
		var item = $("input[type='radio']:checked").val();
		$("form").attr("id", "apply_" + item + "_list");
		$("form").attr("name", "apply_" + item + "_list");
		$("form").attr("action",
				"<c:url value = '/'/>" + "crud/apply." + item + ".list.action");
	}

	function form_reset() {
		$("input[name='entity.indexTerm']").val("");
	}

	function form_sumbit() {
		var url = $("form").attr("action") + "?" + $("form").serialize();
		$.ajax({
			url : url,
			success : function(result) {
				$("#container").html(result);
			}
		});
	}
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="table_container">
	<tr valign="top">
		<td class="L">
			<div id="main_box">
				<!-- 內容開始 -->

				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="table_02">
					<tr valign="middle">
						<td class="t_01"><img
							src="<c:url value = '/'/>resources/images/txt_01.png"></td>
						<td class="t_02"><c:choose>
								<c:when test="${empty item }">
									<s:radio name="item" cssClass="v_type"
										onchange="changeFormAction()"
										list="#{'database':' 資料庫','ebook':' 電子書','journal':' 期刊','customer':' 單位名稱' }"
										value="'database'" />
								</c:when>
								<c:otherwise>
									<s:radio name="item" cssClass="v_type"
										onchange="changeFormAction()"
										list="#{'database':' 資料庫','ebook':' 電子書','journal':' 期刊','customer':' 單位名稱' }"
										value="%{item}" />
								</c:otherwise>
							</c:choose></td>
					</tr>
					<tr valign="middle">
						<td class="t_01"><img
							src="<c:url value = '/'/>resources/images/txt_02.png"></td>
						<td class="t_02"><s:form action="apply.database.list"
								namespace="/crud" method="post" onsubmit="return false;">
								<div class="input_01">
									<span> <s:textfield cssClass="v_keyword"
											name="entity.indexTerm" />
									</span>
								</div>
							</s:form></td>
					</tr>
					<tr valign="middle">
						<td class="t_03" colspan="2" align="center"><a class="btn_01"
							href="javascript:void(0);" onClick="form_reset();">重 新 填 寫</a> <a
							class="btn_01" href="#" onClick="form_sumbit();">開 始 查 詢</a></td>
					</tr>
				</table>

				<!-- 內容結束 -->
			</div>
		</td>
		<td class="R"><jsp:include page="/WEB-INF/jsp/layout/sideBox.jsp" /></td>
	</tr>
</table>