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
	//關閉並更新上一層資料

	function closeDetail_ToQuery() {
		$("#div_Detail").hide();
		UI_Resize();
		if ($("form#apply_customer_list input#listForm_currentPageHeader")
				.val() != null) {
			gotoPage($(
					"form#apply_customer_list input#listForm_currentPageHeader")
					.val());
		} else {
			goSearch();
		}
	}
</script>

</head>
<body>
	<table cellspacing="1" class="detail-table">
		<tbody>
			<tr>
				<th width="130">用戶名稱</th>
				<td>${entity.name }</td>

			</tr>
			<tr>
				<th width="130">用戶英文名稱</th>
				<td>${entity.engName }</td>
			</tr>
			<tr>
				<th width="130">聯絡人</th>
				<td>${entity.contactUserName }</td>
			</tr>
			<tr>
				<th width="130">地址</th>
				<td>${entity.address }</td>
			</tr>
			<tr>
				<th width="130">電話</th>
				<td>${entity.tel }</td>
			</tr>
			<tr>
				<th width="130">E-Mail</th>
				<td>${entity.email }</td>
			</tr>
		</tbody>
	</table>
	<div class="detail-func-button">
		<a class="state-default" onclick="closeDetail_ToQuery();">關閉</a>
	</div>
	<s:if test="hasActionMessages()">
		<script language="javascript" type="text/javascript">
			var msg = "";
			<s:iterator value="actionMessages">msg += '<s:property escape="false"/>\n';
			</s:iterator>;
			goAlert('訊息', msg);
		</script>
	</s:if>
</body>
</html>