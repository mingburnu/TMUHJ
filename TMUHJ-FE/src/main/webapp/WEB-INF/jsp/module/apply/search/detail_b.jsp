<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	function goBack() {
		var url = "${backURL}";
		$.ajax({
			url : url,
			success : function(result) {
				$("#container").html(result);
			}
		});
	}
</script>
<div id="main_b_box">
	<!-- 內容開始 -->
	<div class="detail">

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="table_03">
			<c:if test="${not empty ebook.bookName}">
				<tr>
					<td class="t_01">題名</td>
					<td class="t_02"><c:out value="${ebook.bookName}" /></td>
				</tr>
			</c:if>
			<c:if
				test="${(not empty ebook.autherName) || (not empty ebook.authers) }">
				<tr>
					<td class="t_01">作者</td>
					<td class="t_02"><c:out value="${ebook.autherName}" />,<c:out
							value="${ebook.authers}" /></td>
				</tr>
			</c:if>
			<c:if test="${not empty ebook.publishName}">
				<tr>
					<td class="t_01">出版社</td>
					<td class="t_02"><c:out value="${ebook.publishName}" /></td>
				</tr>
			</c:if>
			<c:if test="${ebook.version > 0}">
				<tr>
					<td class="t_01">版本</td>
					<td class="t_02"><c:out value="${ebook.version}" /></td>
				</tr>
			</c:if>
			<c:if test="${not empty ebook.isbn}">
				<tr>
					<td class="t_01">ISBN</td>
					<td class="t_02"><c:out value="${ebook.isbn}" /></td>
				</tr>
			</c:if>
			<c:if test="${not empty ebook.style}">
				<tr>
					<td class="t_01">類型</td>
					<td class="t_02"><c:out value="${ebook.style}" /></td>
				</tr>
			</c:if>
			<c:if test="${not empty ebook.publication}">
				<tr>
					<td class="t_01">出版地</td>
					<td class="t_02"><c:out value="${ebook.publication}" /></td>
				</tr>
			</c:if>
			<c:if test="${not empty ebook.pubDate}">
				<tr>
					<td class="t_01">出版日期</td>
					<td class="t_02"><c:out value="${ebook.pubDate}" /></td>
				</tr>
			</c:if>
			<c:if test="${not empty ebook.languages}">
				<tr>
					<td class="t_01">語文</td>
					<td class="t_02"><c:out value="${ebook.languages}" /></td>
				</tr>
			</c:if>
			<c:if test="${not empty ebook.bookInfoIntegral}">
				<tr>
					<td class="t_01">美國國家<BR />圖書館分類
					</td>
					<td class="t_02"><c:out value="${ebook.bookInfoIntegral}" /></td>
				</tr>
			</c:if>
			<c:if test="${not empty ownerNames}">
				<tr>
					<td class="t_01">館藏</td>
					<td class="t_02"><c:out value="${ownerNames }" /></td>
				</tr>
			</c:if>
		</table>

		<div align="center">
			<a class="btn_01" href="javascript:goBack();">回 上 一 頁</a>
		</div>

	</div>
	<!-- 內容結束 -->
</div>