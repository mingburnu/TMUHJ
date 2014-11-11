<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>使用者列表</title>
<link id="style_main" rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/default.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/common.js"></script>
<style type="text/css">
.table_browse th {
	font-size: 16px;
}

.table_browse td {
	font-size: 16px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		//registerListForm("listForm", true); // 含排序的樣式調整
	});

	function jumpPage() {
		var jumpToPage = $("input[type='number']").val();
		var recordPerPage = "${ds.pager.recordPerPage}";
		var offset = recordPerPage * (jumpToPage - 1);
		window.location = "<c:url value = '/'/>crud/apply.customer.list.action?pager.offset="
				+ offset + "&pager.currentPage=" + jumpToPage;
	}
	function confirmDel() {
		var checkedObj = $("input[name='checkItem']:checked");
		if (checkedObj.length == 0) {
			myAlert("無勾選項目！");
			return;
		}

		var r = confirm("請確認是否刪除資料？");

		if (r == true) {
			// do delete...
			$.post($("#apply_customer_delete").attr("action"), $(
					"#apply_customer_delete").serialize(), function() {
				$("#apply_customer_delete").submit();
			});
		}
	}
</script>
</head>
<body>
	<%@include file="/WEB-INF/jsp/layout/header.jsp"%>
	<%@include file="/WEB-INF/jsp/layout/menu.jsp"%>

	<%-- List區塊 --%>
	<div id="contaner">
		<div id="contaner_box">
			<div class="pageTitle">查詢資料列表</div>
			<div style="width: 700px;">
				<div class="content_box">
					<!--列表上方區塊 start -->
					<div class="table_browse_top">
						<a class="button_02" href="crud/apply.customer.query.action"><span>新增</span></a>
						<a class="button_02" href="javascript:confirmDel();"><span>刪除勾選項目</span></a>
					</div>
					<!--列表上方區塊 end -->

					<!--表格內容 start -->
					<s:form action="apply.customer.delete" namespace="/crud"
						method="post">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="table_browse">
							<thead>
								<tr>
									<th class="th_first"></th>
									<th>用戶代碼</th>
									<th>用戶名稱</th>
									<th width="90">聯絡人</th>
									<th width="80">建立者ID</th>
									<th width="80">最後修改者</th>
									<th width="100"></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${ds.results}" varStatus="status">
									<c:set var="editPage">
										<s:url namespace="/crud" action="apply.customer.query">
											<s:param name="entity.serNo">${item.serNo}</s:param>
										</s:url>
									</c:set>
									<tr>
										<td align="left" class="td_first" nowrap><input
											type="checkbox" name="checkItem" value="${item.serNo}"></td>
										<td align="left" nowrap>${item.engName }&nbsp;</td>
										<td align="left" nowrap>${item.name }&nbsp;</td>
										<td align="left" nowrap>${item.contactUserName }&nbsp;</td>
										<td align="left" nowrap>${item.cUid }&nbsp;</td>
										<td align="left" nowrap>${item.uUid }&nbsp;</td>
										<td align="center" nowrap><a class="button_02"
											href="${editPage}"><span>修改</span></a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</s:form>
					<!--表格內容 end -->

					<!--分頁 start -->
					<div class="table_browse_pages">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="left" class="p_02"><jsp:include
										page="/WEB-INF/jsp/layout/pagination.jsp">
										<jsp:param name="namespace" value="/crud" />
										<jsp:param name="action" value="apply.customer.list" />
										<jsp:param name="pager" value="${ds.pager}" />
										<jsp:param name="recordPerPage"
											value="${ds.pager.recordPerPage}" />
									</jsp:include></td>
								<td align="right" class="p_01"><c:set var="pageFactor"
										value="${ds.pager.totalRecord/ds.pager.recordPerPage}" /> <c:set
										var="totalPage">
										<fmt:formatNumber type="number" pattern="#"
											value="${pageFactor+(1-(pageFactor%1))%1}" />
									</c:set> <input value="${ds.pager.currentPage }" type="number"
									name="page" min="1" max="${totalPage }" onchange="jumpPage()">/${totalPage }，共
									<span class="total_num">${ds.pager.totalRecord}</span>筆資料</td>
							</tr>
						</table>
					</div>
					<!--分頁 end -->
				</div>
			</div>
		</div>
	</div>
</body>
</html>