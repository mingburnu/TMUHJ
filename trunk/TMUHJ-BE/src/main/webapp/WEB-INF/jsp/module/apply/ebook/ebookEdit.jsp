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
										'<a href="#" onclick="clearDetail_2();closeDetail();">關閉</a>');
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
		clearDetail_2();
		$("form#apply_ebook_save").html(saveForm);
		$("form#apply_ebook_update").html(updateForm);
	}

	//遞交表單
	function submitData() {
		closeDetail();
		clearDetail_2();
		var data = "";
		if ($("form#apply_ebook_save").length != 0) {
			data = $('#apply_ebook_save').serialize();
			goDetail("<c:url value = '/'/>crud/apply.ebook.save.action",
					'期刊-新增', data);
		} else {
			data = $('#apply_ebook_update').serialize();
			goDetail("<c:url value = '/'/>crud/apply.ebook.update.action",
					'期刊-新增', data);
		}
	}

	function addCustomer() {
		var contain = $("#div_Detail_2 .content .header .title").html();
		if (contain != '單位-新增') {
			goDetail_2("<c:url value = '/'/>crud/apply.customer.ajax.action",
					'單位-新增');
		}

		$("#div_Detail_2").show();
		UI_Resize();
		$(window).scrollTop(0);
		closeLoading();
	}

	function clearDetail_2() {
		$("#div_Detail_2 .content .header .title").html(" ");
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
		ArrayList<?> allCustomers = (ArrayList<?>) request
			.getAttribute("allCustomers");
			ArrayList<?> entityCustomers = (ArrayList<?>) request
			.getAttribute("entity.customers");
			if (entityCustomers.size() > 0) {
		for (int j = 0; j < entityCustomers.size(); j++) {
			for (int i = 0; i < allCustomers.size(); i++) {

				if (entityCustomers.get(j).equals(allCustomers.get(i))) {
					allCustomers.remove(entityCustomers.get(j));
				}
			}
		}
			}
			request.setAttribute("allCustomers", allCustomers);
	%>
	<c:choose>
		<c:when test="${empty entity.serNo }">
			<s:form namespace="/crud" action="apply.ebook.save">
				<s:hidden name="entity.serNo" />
				<table cellspacing="1" class="detail-table">
					<tr>
						<th width="130">書名<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.bookName" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">ISBN<span class="required">(&#8226;)</span></th>
						<td><input type="text" name="entity.isbn"
							id="apply_ebook_save_entity_isbn" class="input_text"
							value='<%if (request.getParameter("entity.isbn") != null) {
							out.print(request.getParameter("entity.isbn"));
						}%>'>
						</td>
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
						<td><c:choose>
								<c:when test="${(not empty rCategory)&& (rCategory == '買斷') }">
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="買斷"
										checked="checked">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">買斷</label>
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="租貸">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">租貸</label>
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="未註明">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">未註明</label>
								</c:when>
								<c:when test="${(not empty rCategory)&& (rCategory == '租貸') }">
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="買斷">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">買斷</label>
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="租貸"
										checked="checked">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">租貸</label>
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="未註明">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">未註明</label>
								</c:when>
								<c:otherwise>
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="買斷">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">買斷</label>
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="租貸">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">租貸</label>
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="未註明"
										checked="checked">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">未註明</label>
								</c:otherwise>
							</c:choose></td>
					</tr>
					<tr>
						<th width="130">資源種類</th>
						<td><c:choose>
								<c:when test="${(not empty rType)&& (rType == '期刊') }">
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="電子書">
									<label for="apply_ebook_save_resourcesBuyers_rType">電子書</label>
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="期刊"
										checked="checked">
									<label for="apply_ebook_save_resourcesBuyers_rType">期刊</label>
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="資料庫">
									<label for="apply_ebook_save_resourcesBuyers_rType">資料庫</label>
								</c:when>
								<c:when test="${(not empty rType)&& (rType == '資料庫') }">
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="電子書">
									<label for="apply_ebook_save_resourcesBuyers_rType">電子書</label>
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="期刊">
									<label for="apply_ebook_save_resourcesBuyers_rType">期刊</label>
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="資料庫"
										checked="checked">
									<label for="apply_ebook_save_resourcesBuyers_rType">資料庫</label>
								</c:when>
								<c:otherwise>
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="電子書"
										checked="checked">
									<label for="apply_ebook_save_resourcesBuyers_rType">電子書</label>
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="期刊">
									<label for="apply_ebook_save_resourcesBuyers_rType">期刊</label>
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="資料庫">
									<label for="apply_ebook_save_resourcesBuyers_rType">資料庫</label>
								</c:otherwise>
							</c:choose></td>
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
						<a class="state-default" onclick="clearDetail_2();closeDetail();">取消</a>
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
			<s:form namespace="/crud" action="apply.ebook.update">
				<s:hidden name="entity.serNo" />
				<table cellspacing="1" class="detail-table">
					<tr>
						<th width="130">書名<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.bookName" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">ISBN<span class="required">(&#8226;)</span></th>
						<td><input type="text" name="entity.isbn"
							id="apply_ebook_save_entity_isbn" class="input_text"
							value='<%if (request.getParameter("entity.isbn") != null) {
							out.print(request.getParameter("entity.isbn"));
						}%>'>
						</td>
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
						<td><c:choose>
								<c:when test="${(not empty rCategory)&& (rCategory == '買斷') }">
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="買斷"
										checked="checked">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">買斷</label>
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="租貸">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">租貸</label>
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="未註明">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">未註明</label>
								</c:when>
								<c:when test="${(not empty rCategory)&& (rCategory == '租貸') }">
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="買斷">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">買斷</label>
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="租貸"
										checked="checked">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">租貸</label>
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="未註明">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">未註明</label>
								</c:when>
								<c:otherwise>
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="買斷">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">買斷</label>
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="租貸">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">租貸</label>
									<input type="radio" name="resourcesBuyers.rCategory"
										id="apply_ebook_save_resourcesBuyers_rCategory" value="未註明"
										checked="checked">
									<label for="apply_ebook_save_resourcesBuyers_rCategory">未註明</label>
								</c:otherwise>
							</c:choose></td>
					</tr>
					<tr>
						<th width="130">資源種類</th>
						<td><c:choose>
								<c:when test="${(not empty rType)&& (rType == '期刊') }">
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="電子書">
									<label for="apply_ebook_save_resourcesBuyers_rType">電子書</label>
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="期刊"
										checked="checked">
									<label for="apply_ebook_save_resourcesBuyers_rType">期刊</label>
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="資料庫">
									<label for="apply_ebook_save_resourcesBuyers_rType">資料庫</label>
								</c:when>
								<c:when test="${(not empty rType)&& (rType == '資料庫') }">
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="電子書">
									<label for="apply_ebook_save_resourcesBuyers_rType">電子書</label>
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="期刊">
									<label for="apply_ebook_save_resourcesBuyers_rType">期刊</label>
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="資料庫"
										checked="checked">
									<label for="apply_ebook_save_resourcesBuyers_rType">資料庫</label>
								</c:when>
								<c:otherwise>
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="電子書"
										checked="checked">
									<label for="apply_ebook_save_resourcesBuyers_rType">電子書</label>
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="期刊">
									<label for="apply_ebook_save_resourcesBuyers_rType">期刊</label>
									<input type="radio" name="resourcesBuyers.rType"
										id="apply_ebook_save_resourcesBuyers_rType" value="資料庫">
									<label for="apply_ebook_save_resourcesBuyers_rType">資料庫</label>
								</c:otherwise>
							</c:choose></td>
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
						<a class="state-default" onclick="clearDetail_2();closeDetail();">取消</a>
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