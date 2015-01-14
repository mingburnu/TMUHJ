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
		if ($("form#apply_accountNumber_list input#listForm_currentPageHeader")
				.val() != null) {
			gotoPage($(
					"form#apply_accountNumber_list input#listForm_currentPageHeader")
					.val());
		} else {
			goSearch();
		}
	}
</script>
</head>
<body>
	<c:choose>
		<c:when test="${empty successCount }">
			<table cellspacing="1" class="detail-table">
				<tbody>
					<tr>
						<th width="130">用戶代碼</th>
						<td>${entity.userId }</td>

					</tr>
					<tr>
						<th width="130">用戶姓名</th>
						<td>${entity.userName }</td>
					</tr>
					<tr>
						<th width="130">客戶名稱</th>
						<td>${entity.customer.name }</td>
					</tr>
					<tr>
						<th width="130">Email</th>
						<td>${entity.customer.email }</td>
					</tr>
					<tr>
						<th width="130">權限</th>
						<td>${entity.role.role }</td>
					</tr>
					<tr>
						<th width="130">狀態</th>
						<td>${entity.status }</td>
					</tr>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
	成功筆數:${successCount}
	</c:otherwise>
	</c:choose>
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