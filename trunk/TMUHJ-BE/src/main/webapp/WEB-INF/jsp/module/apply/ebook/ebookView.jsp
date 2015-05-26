<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<c:choose>
	<c:when test="${empty viewSerNo}">
		<script type="text/javascript">
			//關閉並更新上一層資料
			function closeDetail_ToQuery() {
				$("#div_Detail").hide();
				UI_Resize();
				if ($("form#apply_ebook_list input#listForm_currentPageHeader")
						.val() != null) {
					gotoPage($(
							"form#apply_ebook_list input#listForm_currentPageHeader")
							.val());
					resetCloseDetail();
				} else {
					goSearch();
					resetCloseDetail();
				}
			}

			function closeDetail() {
				$("#div_Detail").hide();
				UI_Resize();
				if ($("form#apply_ebook_list input#listForm_currentPageHeader")
						.val() != null) {
					gotoPage($(
							"form#apply_ebook_list input#listForm_currentPageHeader")
							.val());
					resetCloseDetail();
				} else {
					goSearch();
					resetCloseDetail();
				}
			}
		</script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			function closeDetail_ToQuery() {
				closeDetail();
			}
		</script>
	</c:otherwise>
</c:choose>
</head>
<body>
	<c:choose>
		<c:when test="${empty successCount }">
			<table cellspacing="1" class="detail-table">
				<tbody>
					<tr>
						<th width="130">書名<span class="required">(&#8226;)</span></th>
						<td><c:out value="${entity.bookName }" /></td>
					</tr>
					<tr>
						<th width="130">ISBN<span class="required">(&#8226;)</span></th>
						<td>${entity.isbn }</td>
					</tr>
					<tr>
						<th width="130">出版社</th>
						<td><c:out value="${entity.publishName }" /></td>
					</tr>
					<tr>
						<th width="130">作者</th>
						<td><c:out value="${entity.autherName }" /></td>
					</tr>
					<tr>
						<th width="130">語文</th>
						<td><c:out value="${entity.languages }" /></td>
					</tr>
					<tr>
						<th width="130">中國圖書分類法</th>
						<td><c:out value="${entity.cnClassBzStr }" /></td>
					</tr>
					<tr>
						<th width="130">杜威十進分類法</th>
						<td><c:out value="${entity.bookInfoIntegral }" /></td>
					</tr>
					<tr>
						<th width="130">起始日</th>
						<td><c:out value="${entity.resourcesBuyers.startDate}" /></td>
					</tr>
					<tr>
						<th width="130">到期日</th>
						<td><c:out value="${entity.resourcesBuyers.maturityDate}" /></td>
					</tr>
					<tr>
						<th width="130">資源類型</th>
						<td>${entity.resourcesBuyers.rCategory}</td>
					</tr>
					<tr>
						<th width="130">資源種類</th>
						<td>${entity.resourcesBuyers.rType}</td>
					</tr>
					<tr>
						<th width="130">資料庫中文題名</th>
						<td><c:out value="${entity.resourcesBuyers.dbChtTitle}" /></td>
					</tr>
					<tr>
						<th width="130">資料庫英文題名</th>
						<td><c:out value="${entity.resourcesBuyers.dbEngTitle}" /></td>
					</tr>
					<tr>
						<th width="130">購買單位名稱</th>
						<td><c:forEach var="item" items="${entity.customers}"
								varStatus="status">
								<div>
									<c:out value="${item.name}" />
								</div>
							</c:forEach></td>
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
			<s:iterator value="actionMessages">msg += '<s:property escape="true"/>\n';
			</s:iterator>;
			goAlert('訊息', msg);
		</script>
	</s:if>
</body>
</html>