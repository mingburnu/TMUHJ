<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript">
	var updateForm = "";
	$(document).ready(function() {
		updateForm = $("form#apply_accountNumber_update").html();
	});

	//重設所有欄位(清空)
	function resetData() {
		$("form#apply_accountNumber_save > table.detail-table tr td input")
				.val("");
		$("form#apply_accountNumber_update").html(updateForm);
	}

	//遞交表單
	function submitData() {
		closeDetail();
		var data = "";
		if ($("form#apply_accountNumber_save").length != 0) {
			data = $('#apply_accountNumber_save').serialize();
			goDetail(
					"<c:url value = '/'/>crud/apply.accountNumber.save.action",
					'帳戶-新增', data);
		} else {
			data = $('#apply_accountNumber_update').serialize();
			goDetail(
					"<c:url value = '/'/>crud/apply.accountNumber.update.action",
					'帳戶-新增', data);
		}
	}

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
			url : "crud/apply.accountNumber.update.action",
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
	
</script>
</head>
<body>
	<c:choose>
		<c:when test="${empty entity.serNo }">
			<s:form namespace="/crud" action="apply.accountNumber.save">
				<s:hidden name="entity.serNo" />
				<table cellspacing="1" class="detail-table">
					<tr>
						<th width="130">用戶代碼<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.userId" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">用戶密碼<span class="required">(&#8226;)</span></th>
						<td><s:password name="entity.userPw" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">用戶姓名<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.userName" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">用戶名稱<span class="required">(&#8226;)</span></th>
						<td><s:select headerValue="--用戶名稱--" headerKey="0" name="entity.cusSerNo" cssClass="input_text" list="dsCustomer.results" listKey="serNo" listValue="name" /></td>
					</tr>
					<tr>
						<th width="130">帳戶角色</th>
						<td><s:select headerValue="--帳戶角色--" headerKey="使用者" name="entity.role" cssClass="input_text" list="@com.asiaworld.tmuhj.core.enums.Role@values()" listValue="role" /></td>
					</tr>
					<tr>
						<th width="130">狀態</th>
						<td><s:select headerValue="--狀態--" headerKey="審核中" name="entity.status" cssClass="input_text" list="@com.asiaworld.tmuhj.core.enums.Status@values()" listValue="status" /></td>
					</tr>					
				</table>
				<div class="button_box">
					<div class="detail-func-button">
						<a class="state-default" onclick="closeDetail();">取消</a> &nbsp;<a
							class="state-default" onclick="resetData();">重設</a>&nbsp; <a
							class="state-default" onclick="submitData();">確認</a>
					</div>
				</div>
				<div class="detail_note">
					<div class="detail_note_title">Note</div>
					<div class="detail_note_content">
						<span class="required">(&#8226;)</span>為必填欄位
					</div>

				</div>
			</s:form>
		</c:when>
		<c:otherwise>
			<s:form namespace="/crud" action="apply.accountNumber.update">
				<s:hidden name="entity.serNo" />
				<table cellspacing="1" class="detail-table">
					<tr>
						<th width="130">用戶代碼<span class="required">(&#8226;)</span></th>
						<td>${entity.userId }</td>
					</tr>
					<tr>
						<th width="130">用戶密碼<span class="required">(&#8226;)</span></th>
						<td><s:password name="entity.userPw" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">用戶姓名<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.userName" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">用戶名稱</th>
						<td><s:select headerValue="--用戶名稱--" headerKey="0" name="entity.cusSerNo" cssClass="input_text" list="dsCustomer.results" listKey="serNo" listValue="name" /></td>
					</tr>
					<tr>
						<th width="130">帳戶角色</th>
						<td><s:select headerValue="--帳戶角色--" headerKey="使用者" name="entity.role" cssClass="input_text" list="@com.asiaworld.tmuhj.core.enums.Role@values()" listValue="role" /></td>
					</tr>
					<tr>
						<th width="130">狀態</th>
						<td><s:select headerValue="--狀態--" headerKey="審核中" name="entity.status" cssClass="input_text" list="@com.asiaworld.tmuhj.core.enums.Status@values()" listValue="status" /></td>
					</tr>					
				</table>
				<div class="button_box">
					<div class="detail-func-button">
						<a class="state-default" onclick="closeDetail();">取消</a> &nbsp;<a
							class="state-default" onclick="resetData();">重設</a>&nbsp; <a
							class="state-default" onclick="submitData();">確認</a>
					</div>
				</div>
				<div class="detail_note">
					<div class="detail_note_title">Note</div>
					<div class="detail_note_content">
						<span class="required">(&#8226;)</span>為必填欄位
					</div>
				</div>
			</s:form>
		</c:otherwise>
	</c:choose>

	<s:if test="hasActionErrors()">
		<script language="javascript" type="text/javascript">
			var msg = "";
			<s:iterator value="actionErrors">
			msg += '<s:property escape="false"/><br>';
			</s:iterator>;
			goAlert('訊息', msg);
		</script>
	</s:if>
</body>
</html>