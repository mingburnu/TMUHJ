<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="esapi"
	uri="http://www.owasp.org/index.php/Category:OWASP_Enterprise_Security_API"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var contain = $("#div_Detail_2 .content .header .title")
								.html();
						if (contain != '單位-新增') {
							goCustomers(
									"<c:url value = '/'/>crud/apply.customer.box.action",
									'單位-新增');
						}
					});

	$(document)
			.ready(
					function() {
						$("#div_Detail .content .header .close")
								.html(
										'<a href="#" onclick="clearCustomers();closeDetail();">關閉</a>');
					});

	$(document).ready(function() {
		$("img#minus").click(function() {
			value = $(this).next().val();
			$(this).next().attr("name", "");
			$(this).parent().hide();

			$("input#customer_unit").each(function() {
				if ($(this).val() == value) {
					$(this).attr("checked", false);
				}
			});
		});
	});

	//重設所有欄位(清空)
	function resetData() {
		goDetail('<%=request.getContextPath()%>/crud/apply.ebook.edit.action?'
				+ 'entity.serNo=${entity.serNo}', '電子書-修改');
	}

	//遞交表單
	function submitData() {
		var data = $('#apply_ebook_update').serialize();
		closeDetail();
		clearCustomers();
		goDetail(
				"<c:url value = '/'/>crud/apply.ebook.update.action?entity.serNo=${entity.serNo}",
				'電子書-修改', data);
	}
</script>
<style type="text/css">
#div_Detail_2 {
	display: none;
}

img#add,img#minus {
	position: relative;
	top: 5px;
	left: 5px;
}

input#customer_name {
	background-color: #aaaaaa;
}
</style>
</head>
<body>
	<%
		String isbn = "";
		if (request.getParameter("entity.isbn") != null) {
			isbn = request.getParameter("entity.isbn");
		} else {
			if (request.getAttribute("entity.isbn") != null) {
				isbn = request.getAttribute("entity.isbn").toString();
			}
		}
	%>
	<s:form namespace="/crud" action="apply.ebook.update">
		<table cellspacing="1" class="detail-table">
			<tr>
				<th width="130">書名<span class="required">(&#8226;)</span></th>
				<td><s:textfield name="entity.bookName" cssClass="input_text" /></td>
			</tr>
			<tr>
				<th width="130">ISBN<span class="required">(&#8226;)</span></th>
				<td><input type="text" name="entity.isbn" class="input_text"
					id="apply_ebook_update_entity_isbn"
					value="<esapi:encodeForHTMLAttribute><%=isbn%></esapi:encodeForHTMLAttribute>">
				</td>
			</tr>
			<tr>
				<th width="130">出版社</th>
				<td><s:textfield name="entity.publishName"
						cssClass="input_text" /></td>
			</tr>
			<tr>
				<th width="130">作者</th>
				<td><s:textfield name="entity.autherName" cssClass="input_text" /></td>
			</tr>
			<tr>
				<th width="130">系列叢書名</th>
				<td><s:textfield name="entity.uppeName" cssClass="input_text" /></td>
			</tr>
			<tr>
				<th width="130">出版日期</th>
				<td><s:textfield name="entity.pubDate" cssClass="input_text" /></td>
			</tr>
			<tr>
				<th width="130">語文</th>
				<td><s:textfield name="entity.languages" cssClass="input_text" /></td>
			</tr>
			<tr>
				<th width="130">中國圖書分類碼</th>
				<td><s:textfield name="entity.cnClassBzStr"
						cssClass="input_text" /></td>
			</tr>
			<tr>
				<th width="130">杜威十進位分類號</th>
				<td><s:textfield name="entity.bookInfoIntegral"
						cssClass="input_text" /></td>
			</tr>
			<tr>
				<th width="130">起始日</th>
				<td><s:textfield name="entity.resourcesBuyers.startDate"
						cssClass="input_text" /></td>
			</tr>
			<tr>
				<th width="130">到期日</th>
				<td><s:textfield name="entity.resourcesBuyers.maturityDate"
						cssClass="input_text" /></td>
			</tr>
			<tr>
				<th width="130">資源類型</th>
				<td><s:radio name="entity.resourcesBuyers.category"
						list="categoryList" listKey="name()" listValue="category" /></td>
			</tr>
			<tr>
				<th width="130">資源種類</th>
				<td><s:radio name="entity.resourcesBuyers.type"
						list="@com.asiaworld.tmuhj.module.apply.enums.Type@values()"
						listKey="name()" listValue="type" /></td>
			</tr>
			<tr>
				<th width="130">資料庫中文題名</th>
				<td><s:textfield name="entity.resourcesBuyers.dbChtTitle"
						cssClass="input_text" /></td>
			</tr>
			<tr>
				<th width="130">資料庫英文題名</th>
				<td><s:textfield name="entity.resourcesBuyers.dbEngTitle"
						cssClass="input_text" /></td>
			</tr>
			<tr>
				<th width="130">購買單位<span class="required">(&#8226;)</span></th>
				<td><input type="text" id="customer_name" class="input_text"
					disabled="disabled" value="增加單位"><img id="add"
					src="<c:url value = '/'/>resources/images/add.png"
					onclick="addCustomer();"> <c:forEach var="item"
						items="${entity.customers}" varStatus="status2">
						<div style="">
							<input class="input_text" disabled="disabled"
								value="${item.name}"><img id="minus"
								src="<c:url value = '/'/>resources/images/minus.png"><input
								id="unit" type="hidden" value="${item.serNo }"
								name="entity.cusSerNo">
						</div>
					</c:forEach> <c:forEach var="item" items="${uncheckCustomers}"
						varStatus="status">
						<div style="display: none;">
							<input class="input_text" disabled="disabled"
								value="${item.name}"><img id="minus"
								src="<c:url value = '/'/>resources/images/minus.png"><input
								id="unit" type="hidden" value="${item.serNo }">
						</div>
					</c:forEach></td>
			</tr>
		</table>
		<div class="button_box">
			<div class="detail-func-button">
				<a class="state-default" onclick="clearCustomers();closeDetail();">取消</a>
				&nbsp;<a class="state-default" onclick="resetData();">重設</a>&nbsp; <a
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
	<jsp:include page="/WEB-INF/jsp/layout/msg.jsp" />
</body>
</html>