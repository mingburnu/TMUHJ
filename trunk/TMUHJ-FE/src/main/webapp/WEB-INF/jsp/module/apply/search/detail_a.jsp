<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function goBack(){
	var url="${backURL}";
	console.log(url);
	$.ajax({url: url, success: function(result){
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
				<c:if test="${not empty database.dbChtTitle}">
					<tr>
						<td class="t_01">題名</td>
						<td class="t_02">${database.dbChtTitle }</td>
					</tr>
				</c:if>
				<c:if test="${not empty database.includedSpecies}">
					<tr>
						<td class="t_01">類型</td>
						<td class="t_02">${database.includedSpecies }</td>
					</tr>
				</c:if>
				<c:if test="${not empty database.content}">
					<tr>
						<td class="t_01">出版社</td>
						<td class="t_02">${database.content }</td>
					</tr>
				</c:if>
				<c:if test="${not empty database.dbChtTitle}">
					<tr>
						<td class="t_01">內容描述</td>
						<td class="t_02">${database.dbChtTitle }</td>
					</tr>
				</c:if>
				<c:if test="${not empty database.indexedYears }">
					<tr>
						<td class="t_01">收錄年代</td>
						<td class="t_02">${database.indexedYears }</td>
					</tr>
				</c:if>
				<c:if
					test="${(not empty resourcesBuyers.startDate)||(not empty resourcesBuyers.maturityDate)}">
					<tr>
						<td class="t_01">起訂日期</td>
						<td class="t_02">${resourcesBuyers.startDate }~${resourcesBuyers.maturityDate }</td>
					</tr>
				</c:if>
				<c:if test="${not empty database.topic}">
					<tr>
						<td class="t_01">主題</td>
						<td class="t_02">${database.topic }</td>
					</tr>
				</c:if>
				<c:if test="${not empty database.classification}">
					<tr>
						<td class="t_01">分類</td>
						<td class="t_02">${database.classification }</td>
					</tr>
				</c:if>
				<c:if test="${not empty ownerNames}">
					<tr>
						<td class="t_01">館藏</td>
						<td class="t_02">${ownerNames }</td>
					</tr>
				</c:if>
			</table>

			<div align="center">
				<a class="btn_01" href="javascript:goBack();">回 上 一 頁</a>
			</div>

		</div>
		<!-- 內容結束 -->
	</div>