<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	$(document).ready(
			function() {
				$("input[name='type']").change(
						function() {
							var item = $("input[name='type']:checked").val();
							$("form").attr("id", "apply_" + item + "_list");
							$("form").attr("name", "apply_" + item + "_list");
							$("form").attr(
									"action",
									"<c:url value = '/'/>" + "crud/apply."
											+ item + ".list.action");
						});
			});
	
	$(document).ready(function() {
		$("input.v_type").each(function(){
			if ($(this).val()=="${type}"){
		        this.checked = true;
		    	var item = $("input[name='type']:checked").val();
				$("form").attr("id", "apply_" + item + "_list");
				$("form").attr("name", "apply_" + item + "_list");
				$("form").attr(
						"action",
						"<c:url value = '/'/>" + "crud/apply."
								+ item + ".list.action");
		    }
		});
	});

	function form_reset() {
		$("input[name='keywords']").val("");
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
				<s:form action="apply.database.list" namespace="/crud" method="post"
					onsubmit="return false;">
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
								onClick="form_reset();">重 新 填 寫</a> <a class="btn_01" href="#"
								onClick="form_sumbit();">開 始 查 詢</a></td>
						</tr>
					</table>
				</s:form>

				<!-- 內容結束 -->
			</div>
		</td>
		<td class="R"><jsp:include page="/WEB-INF/jsp/layout/sideBox.jsp" /></td>
	</tr>
</table>