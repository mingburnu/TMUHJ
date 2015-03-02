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
	var importForm = "";
	$(document).ready(function() {
		saveForm = $("form#apply_ebook_save").html();
		updateForm = $("form#apply_ebook_update").html();
		importForm = $("form#apply_ebook_queue").html();
	});

	$(document).ready(
			function() {
				$("#div_Detail .content .header .close").html(
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
		clearCustomers();
		$("form#apply_ebook_save").html(saveForm);
		$("form#apply_ebook_update").html(updateForm);
		$("form#apply_ebook_queue").html(importForm);
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
			goDetail("<c:url value = '/'/>crud/apply.ebook.update.action",
					'電子書-新增', data);
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
	
	//Excel列表
	function goQueue() {
		function getDoc(frame) {
			var doc = null;

			// IE8 cascading access check
			try {
				if (frame.contentWindow) {
					doc = frame.contentWindow.document;
				}
			} catch (err) {
			}

			if (doc) { // successful getting content
				return doc;
			}

			try { // simply checking may throw in ie8 under ssl or mismatched protocol
				doc = frame.contentDocument ? frame.contentDocument
						: frame.document;
			} catch (err) {
				// last attempt
				doc = frame.document;
			}
			return doc;
		}

		showLoading();
		var formObj = $("form#apply_ebook_queue");
		var formURL = $("form#apply_ebook_queue").attr("action");

		if (window.FormData !== undefined) // for HTML5 browsers
		//			if(false)
		{

			var formData = new FormData(document
					.getElementById("apply_ebook_queue"));
			$.ajax({
				url : formURL,
				type : 'POST',
				data : formData,
				mimeType : "multipart/form-data",
				contentType : false,
				cache : false,
				processData : false,
				success : function(data, textStatus, jqXHR) {
					$("#div_Detail").show();
					UI_Resize();
					$(window).scrollTop(0);
					$("#div_Detail .content > .header > .title").html("電子書-匯入");
					$("#div_Detail .content > .contain").empty().html(data);
					closeLoading();
					
				},
				error : function(jqXHR, textStatus, errorThrown) {
					goAlert("結果", XMLHttpRequest.responseText);
					closeLoading();
				}
			});
			e.preventDefault();
			e.unbind();
		} else //for olden browsers
		{
			//generate a random id
			var iframeId = 'unique' + (new Date().getTime());

			//create an empty iframe
			var iframe = $('<iframe src="javascript:false;" name="'+iframeId+'" />');

			//hide it
			iframe.hide();

			//set form target to iframe
			formObj.attr('target', iframeId);

			//Add iframe to body
			iframe.appendTo('body');
			iframe.load(function(e) {
				var doc = getDoc(iframe[0]);
				var docRoot = doc.body ? doc.body : doc.documentElement;
				var data = docRoot.innerHTML;
				$("#div_Detail").show();
				UI_Resize();
				$(window).scrollTop(0);
				$("#div_Detail .content > .header > .title").html("電子書-匯入");
				$("#div_Detail .content > .contain").empty().html(data);
				closeLoading();
			});
		}

	}
	
	//匯出範本
	function goExport(){
		var url='<%=request.getContextPath()%>/crud/apply.ebook.exports.action';
		window.open(url, "_top");
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
		<c:when test="${(empty entity.serNo) && (empty goQueue) }">
			<s:form namespace="/crud" action="apply.ebook.save">
				<s:hidden name="entity.serNo" />
				<table cellspacing="1" class="detail-table">
					<tr>
						<th width="130">書名<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.bookName" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">ISBN<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.isbn" cssClass="input_text" /></td>
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

		<c:when test="${not empty goQueue}">
			<s:form namespace="/crud" action="apply.ebook.queue"
				enctype="multipart/form-data" method="post">
				<table cellspacing="1" class="detail-table">
					<tr>
						<th width="130">匯入檔案<span class="required">(•)</span>(<a
							href="#" onclick="goExport();">範例</a>)
						</th>
						<td><input type="file" id="file" name="file" size="50"></td>
					</tr>
				</table>
				<div class="button_box">
					<div class="detail-func-button">
						<a class="state-default" onclick="clearDetail_2();closeDetail();">取消</a>
						&nbsp;<a class="state-default" onclick="resetData();">重置</a>&nbsp;<a
							id="ports" class="state-default" onclick="goQueue();">下一步</a>
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
				ArrayList<?> allCustomers = (ArrayList<?>) request.getAttribute("allCustomers");
					ArrayList<?> entityCustomers = (ArrayList<?>) request.getAttribute("entity.customers");
					Object[] allCustomerArray=allCustomers.toArray();
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
				<s:hidden name="entity.serNo" />
				<table cellspacing="1" class="detail-table">
					<tr>
						<th width="130">書名<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.bookName" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">ISBN<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.isbn" cssClass="input_text" /></td>
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