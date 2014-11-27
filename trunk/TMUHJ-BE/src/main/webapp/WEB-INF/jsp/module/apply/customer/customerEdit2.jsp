<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改使用者</title>
<link id="style_main" rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/default.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/common.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	});
<%--function check() {
		// 密碼檢核
		var inputPasswdObj = $("#updateForm input[name='userinfo_passwd']");
		if (trim(inputPasswdObj.val()).length == 0) {
			myAlert("密碼不可空白！");
			inputPasswdObj.focus();
			return false;
		}

		return true;
	}
	function send() {
		if (!check())
			return;

		// do update...
		var argData = $('#updateForm').serialize();
		ajaxPost({
			url : "crud/apply.customer.update.action",
			data : argData,
			success : function(msg) {
				// myAlert(msg);
				location.href = $("input[name='lastURL']").val();
			},
			failure : function(msg) {
				myAlert(msg);
			}
		});
	}
	function showLeader(value) {
		var jQueryObj = $("select[name='userinfo_parentId']");
		if (value == '4') {
			jQueryObj.show();
			jQueryObj.removeAttr("disabled");
		} else {
			jQueryObj.hide();
			jQueryObj.attr("disabled", "disabled");
		}

	}--%>
	$(document).ready(
			function() {
				$("a.button_01").click(
						function() {
							var formId = $("form").attr("id");
							if (formId == "apply_customer_update") {
								$.post($("form#apply_customer_update").attr(
										"action"), $(
										"form#apply_customer_update")
										.serialize(), function() {
									$("form#apply_customer_update").submit();
								});
							} else {
								$.post($("form#apply_customer_save").attr(
										"action"),
										$("form#apply_customer_save")
												.serialize(), function() {
											$("form#apply_customer_save")
													.submit();
										});
							}
						});
			});
</script>
</head>

<body>
	<%@include file="/WEB-INF/jsp/layout/header.jsp"%>
	<%@include file="/WEB-INF/jsp/layout/menu.jsp"%>
	<s:hidden name="lastURL" />
	<div id="contaner">
		<div id="contaner_box">
			<c:choose>
				<c:when test="${empty entity.serNo}">
					<div class="pageTitle">新增用戶</div>
				</c:when>
				<c:otherwise>
					<div class="pageTitle">修改用戶</div>
				</c:otherwise>
			</c:choose>
			<div class="content_box">
				<table cellspacing="0" cellpadding="0" class="input_form"
					width="400">
					<tr>
						<td><c:choose>
								<c:when test="${empty entity.serNo}">
									<s:form namespace="/crud" action="apply.customer.save">
										<s:hidden name="entity.serNo" />
										<table cellspacing="1" width="100%">
											<tr>
												<td align="right" width="40">用戶代碼</td>
												<td align="left"><s:textfield name="entity.engName"
														cssClass="input_text" /></td>
											</tr>

											<tr>
												<td align="right">用戶名稱</td>
												<td align="left"><s:textfield name="entity.name"
														cssClass="input_text" /></td>
											</tr>
											<tr>
												<td align="right">聯絡人</td>
												<td align="left"><s:textfield
														name="entity.contactUserName" cssClass="input_text" /></td>
											</tr>
											<tr>
												<td align="right">電話</td>
												<td align="left"><s:textfield name="entity.tel"
														cssClass="input_text" /></td>
											</tr>
											<tr>
												<td align="right">Email</td>
												<td align="left"><s:textfield name="entity.tel"
														cssClass="input_text" /></td>
											</tr>
										</table>
									</s:form>
								</c:when>
								<c:otherwise>
									<s:form namespace="/crud" action="apply.customer.update">
										<s:hidden name="entity.serNo" />
										<table cellspacing="1" width="100%">
											<tr>
												<td align="right" width="40">用戶代碼</td>
												<td align="left"><s:textfield name="entity.engName"
														cssClass="input_text" /></td>
											</tr>

											<tr>
												<td align="right">用戶名稱</td>
												<td align="left"><s:textfield name="entity.name"
														cssClass="input_text" /></td>
											</tr>
											<tr>
												<td align="right">聯絡人</td>
												<td align="left"><s:textfield
														name="entity.contactUserName" cssClass="input_text" /></td>
											</tr>
											<tr>
												<td align="right">電話</td>
												<td align="left"><s:textfield name="entity.tel"
														cssClass="input_text" /></td>
											</tr>
											<tr>
												<td align="right">Email</td>
												<td align="left"><s:textfield name="entity.tel"
														cssClass="input_text" /></td>
											</tr>
										</table>
									</s:form>
								</c:otherwise>
							</c:choose></td>
					</tr>
				</table>

				<div class="button_box">



					<%--<c:choose>
						<c:when test="${empty entity.serNo}">
							<s:submit cssClass="button_02" action="apply.customer.save"
								value="新增" />
						</c:when>
					</c:choose>--%>
					<a class="button_02" href="<s:property value="lastURL"/>"><span>取消</span></a>
					&nbsp; <a class="button_01"><span>送出</span></a>
					<!-- <a class="button_01" href="javascript:send();"><span>送出</span></a> -->
				</div>
			</div>
		</div>
	</div>
</body>
</html>