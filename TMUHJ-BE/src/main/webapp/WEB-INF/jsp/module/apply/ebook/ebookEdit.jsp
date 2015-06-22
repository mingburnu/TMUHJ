<%@ page import="java.util.*"%>
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
	<c:when test="${empty isbn}">
		<c:set var="isbn">
			<c:out value="${entity.isbn }"></c:out>
		</c:set>
	</c:when>
	<c:otherwise>
		<c:set var="isbn">
			<c:out value="${isbn }"></c:out>
		</c:set>
	</c:otherwise>
</c:choose>
<script type="text/javascript">
	var saveForm = "";
	var updateForm = "";
	$(document).ready(function() {
		saveForm = $("form#apply_ebook_save").html();
		updateForm = $("form#apply_ebook_update").html();
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

	$(document).ready(
			function() {
				$("input#apply_ebook_save_resourcesBuyers_rCategory").each(
						function() {
							if ($(this).val() == "未註明") {
								this.checked = true;
							}
						});

				$("input#apply_ebook_save_resourcesBuyers_rType").each(
						function() {
							if ($(this).val() == "電子書") {
								this.checked = true;
							}
						});

				$("input#apply_ebook_update_resourcesBuyers_rCategory").each(
						function() {
							if ($(this).val() == "未註明") {
								this.checked = true;
							}
						});

				$("input#apply_ebook_update_resourcesBuyers_rType").each(
						function() {
							if ($(this).val() == "電子書") {
								this.checked = true;
							}
						});
			});

	$(document).ready(
			function() {
				$("input#apply_ebook_save_resourcesBuyers_rCategory").each(
						function() {
							if ($(this).val() == "${rCategory}") {
								this.checked = true;
							}
						});

				$("input#apply_ebook_save_resourcesBuyers_rType").each(
						function() {
							if ($(this).val() == "${rType}") {
								this.checked = true;
							}
						});

				$("input#apply_ebook_update_resourcesBuyers_rCategory").each(
						function() {
							if ($(this).val() == "${rCategory}") {
								this.checked = true;
							}
						});

				$("input#apply_ebook_update_resourcesBuyers_rType").each(
						function() {
							if ($(this).val() == "${rType}") {
								this.checked = true;
							}
						});

				$("input#apply_ebook_save_isbn").val("${isbn}");
				$("input#apply_ebook_update_isbn").val("${isbn}");
			});

	//重設所有欄位(清空)
	function resetData() {
		clearCustomers();
		$("form#apply_ebook_save").html(saveForm);
		$("form#apply_ebook_update").html(updateForm);
	}

	//遞交表單
	function submitData() {
		closeDetail();
		clearCustomers();
		var data = "";
		if ($("form#apply_ebook_save").length != 0) {
			data = $('#apply_ebook_save').serialize();
			goDetail("<c:url value = '/'/>crud/apply.ebook.save.action",
					'電子書-新增', data);
		} else {
			data = $('#apply_ebook_update').serialize();
			goDetail(
					"<c:url value = '/'/>crud/apply.ebook.update.action?entity.serNo=${entity.serNo}",
					'電子書-修改', data);
		}
	}

	function addCustomer() {
		var contain = $("#div_Customers .content .header .title").html();
		if (contain != '單位-新增') {
			goCustomers("<c:url value = '/'/>crud/apply.customer.ajax.action",
					'單位-新增');
		}

		$("#div_Customers").show();
		UI_Resize();
		$(window).scrollTop(0);
		closeLoading();
	}

	function clearCustomers() {
		$("#div_Customers .content .header .title").html("");
		$("#div_Customers .content .contain").html("");
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
	<c:choose>
		<c:when test="${empty entity.serNo }">
			<s:form namespace="/crud" action="apply.ebook.save">
				<table cellspacing="1" class="detail-table">
					<tr>
						<th width="130">書名<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.bookName" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">ISBN<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="isbn" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">出版社</th>
						<td><s:textfield name="entity.publishName"
								cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">作者</th>
						<td><s:textfield name="entity.autherName"
								cssClass="input_text" /></td>
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
						<td><s:textfield name="entity.languages"
								cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">中國圖書分類碼</th>
						<td><s:textfield name="entity.cnClassBzStr"
								cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">杜威十進分類法</th>
						<td><s:textfield name="entity.bookInfoIntegral"
								cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">起始日</th>
						<td><s:textfield name="resourcesBuyers.startDate"
								cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">到期日</th>
						<td><s:textfield name="resourcesBuyers.maturityDate"
								cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">資源類型</th>
						<td><c:forEach var="item" items="${categoryList}"
								varStatus="status">
								<input type="radio" name="rCategory"
									id="apply_ebook_save_resourcesBuyers_rCategory"
									value="${item.category }">
								<label for="apply_ebook_save_resourcesBuyers_rCategory">${item.category }</label>
							</c:forEach></td>
					</tr>
					<tr>
						<th width="130">資源種類</th>
						<td><c:forEach var="item" items="${typeList}"
								varStatus="status">
								<input type="radio" name="rType"
									id="apply_ebook_save_resourcesBuyers_rType"
									value="${item.type }">
								<label for="apply_ebook_save_resourcesBuyers_rType">${item.type }</label>
							</c:forEach></td>
					</tr>
					<tr>
						<th width="130">資料庫中文題名</th>
						<td><s:textfield name="resourcesBuyers.dbChtTitle"
								cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">資料庫英文題名</th>
						<td><s:textfield name="resourcesBuyers.dbEngTitle"
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
										id="unit" type="hidden" value="${item.serNo }" name="cusSerNo">
								</div>
							</c:forEach> <c:forEach var="item" items="${allCustomers}" varStatus="status">
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
						&nbsp;<a class="state-default" onclick="resetData();">重設</a>&nbsp;
						<a class="state-default" onclick="submitData();">確認</a>
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
			<%
				ArrayList<?> allCustomers = (ArrayList<?>) request
								.getAttribute("allCustomers");
						ArrayList<?> entityCustomers = (ArrayList<?>) request
								.getAttribute("entity.customers");
						Object[] allCustomerArray = allCustomers.toArray();
						if (entityCustomers.size() > 0) {
							for (int j = 0; j < entityCustomers.size(); j++) {
								if (allCustomers.contains(entityCustomers.get(j))) {
									allCustomers.remove(entityCustomers.get(j));
								}
							}
						}

						request.setAttribute("allCustomers", allCustomers);
			%>
			<s:form namespace="/crud" action="apply.ebook.update">
				<table cellspacing="1" class="detail-table">
					<tr>
						<th width="130">書名<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.bookName" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">ISBN<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="isbn" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">出版社</th>
						<td><s:textfield name="entity.publishName"
								cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">作者</th>
						<td><s:textfield name="entity.autherName"
								cssClass="input_text" /></td>
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
						<td><s:textfield name="entity.languages"
								cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">中國圖書分類碼</th>
						<td><s:textfield name="entity.cnClassBzStr"
								cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">美國國家圖書館類碼</th>
						<td><s:textfield name="entity.bookInfoIntegral"
								cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">起始日</th>
						<td><s:textfield name="resourcesBuyers.startDate"
								cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">到期日</th>
						<td><s:textfield name="resourcesBuyers.maturityDate"
								cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">資源類型</th>
						<td><c:forEach var="item" items="${categoryList}"
								varStatus="status">
								<input type="radio" name="rCategory"
									id="apply_ebook_save_resourcesBuyers_rCategory"
									value="${item.category }">
								<label for="apply_ebook_save_resourcesBuyers_rCategory">${item.category }</label>
							</c:forEach></td>
					</tr>
					<tr>
						<th width="130">資源種類</th>
						<td><c:forEach var="item" items="${typeList}"
								varStatus="status">
								<input type="radio" name="rType"
									id="apply_ebook_save_resourcesBuyers_rType"
									value="${item.type }">
								<label for="apply_ebook_save_resourcesBuyers_rType">${item.type }</label>
							</c:forEach></td>
					</tr>
					<tr>
						<th width="130">資料庫中文題名</th>
						<td><s:textfield name="resourcesBuyers.dbChtTitle"
								cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">資料庫英文題名</th>
						<td><s:textfield name="resourcesBuyers.dbEngTitle"
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
										id="unit" type="hidden" value="${item.serNo }" name="cusSerNo">
								</div>
							</c:forEach> <c:forEach var="item" items="${allCustomers}" varStatus="status">
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
						&nbsp;<a class="state-default" onclick="resetData();">重設</a>&nbsp;
						<a class="state-default" onclick="submitData();">確認</a>
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
	<jsp:include page="/WEB-INF/jsp/layout/msg.jsp" />
</body>
</html>