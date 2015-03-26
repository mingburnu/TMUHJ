<%@ page import="java.util.*"%>
<%@ page import="com.asiaworld.*"%>
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
		saveForm = $("form#apply_accountNumber_save").html();
		updateForm = $("form#apply_accountNumber_update").html();
		importForm = $("form#apply_accountNumber_queue").html();
	});
	
	$(document).ready(function() {
		$("select#apply_accountNumber_save_cusSerNo").children().each(function(){
		    if ($(this).val()=="${entity.cusSerNo}"){
		        this.selected = true; 
		    }
		});
		
		$("select#apply_accountNumber_save_role").children().each(function(){
		    if ($(this).text()=="${entity.role.role}"){
		        this.selected = true; 
		    }
		});
		
		$("select#apply_accountNumber_save_status").children().each(function(){
		    if ($(this).text()=="${entity.status.status}"){
		        this.selected = true; 
		    }
		});
		
		$("select#apply_accountNumber_update_cusSerNo").children().each(function(){
		    if ($(this).val()=="${entity.cusSerNo}"){
		        this.selected = true; 
		    }
		});
		
		$("select#apply_accountNumber_update_role").children().each(function(){
		    if ($(this).text()=="${entity.role.role}"){
		        this.selected = true; 
		    }
		});
		
		$("select#apply_accountNumber_update_status").children().each(function(){
		    if ($(this).text()=="${entity.status.status}"){
		        this.selected = true; 
		    }
		});
	});
	
	//重設所有欄位(清空)
	function resetData() {
		$("form#apply_accountNumber_save").html(saveForm);
		$("form#apply_accountNumber_update").html(updateForm);
		$("form#apply_accountNumber_queue").html(importForm);
	}

	//遞交表單
	function submitData() {
		closeDetail();
		var data = "";
		if ($("form#apply_accountNumber_save").length != 0) {
			data = $('#apply_accountNumber_save').serialize();
			goDetail(
					"<c:url value = '/'/>crud/apply.accountNumber.save.action",
					'帳戶-新增', data);
		} else {
			data = $('#apply_accountNumber_update').serialize();
			goDetail(
					"<c:url value = '/'/>crud/apply.accountNumber.update.action?entity.serNo=${entity.serNo}",
					'帳戶-修改', data);
		}
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
		//alert(document.getElementById("apply_accountNumber_queue"));
		var formObj = $("form#apply_accountNumber_queue");
		var formURL = $("form#apply_accountNumber_queue").attr("action");

		if (window.FormData !== undefined) // for HTML5 browsers
		//			if(false)
		{

			var formData = new FormData(document
					.getElementById("apply_accountNumber_queue"));
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
					$("#div_Detail .content > .header > .title").html("帳戶-匯入");
					$("#div_Detail .content > .contain").empty().html(data);
					closeLoading();

				},
				error : function(jqXHR, textStatus, errorThrown) {
					goAlert("結果", XMLHttpRequest.responseText);
					closeLoading();
				}
			});
			
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
				$("#div_Detail .content > .header > .title").html("帳戶-匯入");
				$("#div_Detail .content > .contain").empty().html(data);
				closeLoading();
			});
		}

	}
	
	//匯出範本
	function goExport(){
		var url='<%=request.getContextPath()%>/crud/apply.accountNumber.exports.action';
		window.open(url, "_top");
	}
</script>
</head>
<body>
	<c:choose>
		<c:when test="${(empty entity.serNo) && (empty goQueue) }">
			<s:form namespace="/crud" action="apply.accountNumber.save">
				<table cellspacing="1" class="detail-table">
					<tr>
						<th width="130">用戶代碼<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.userId" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">用戶密碼<span class="required">(&#8226;)</span></th>
						<td><s:password name="entity.userPw" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">用戶姓名<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.userName" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">用戶名稱<span class="required">(&#8226;)</span></th>
						<td><c:choose>
								<c:when test="${login.role.role == '管理員' }">
									<s:select name="cusSerNo" cssClass="input_text"
										list="dsCustomer.results" listKey="serNo" listValue="name" />
								</c:when>
								<c:otherwise>
									<s:select headerValue="--用戶名稱--" headerKey="0" name="cusSerNo"
										cssClass="input_text" list="dsCustomer.results"
										listKey="serNo" listValue="name" />
								</c:otherwise>
							</c:choose></td>
					</tr>
					<tr>
						<th width="130">帳戶角色</th>
						<td><select name="role" id="apply_accountNumber_save_role"
							class="input_text">
								<c:forEach var="item" items="${roleList}" varStatus="status">
									<option value="${item.role }">${item.role }</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<th width="130">狀態</th>
						<td><select name="status"
							id="apply_accountNumber_save_status" class="input_text">
								<c:forEach var="item" items="${statusList}" varStatus="status">
									<option value="${item.status }">${item.status }</option>
								</c:forEach>
						</select></td>
					</tr>
				</table>
				<div class="button_box">
					<div class="detail-func-button">
						<a class="state-default" onclick="closeDetail();">取消</a> &nbsp;<a
							class="state-default" onclick="resetData();">重設</a>&nbsp; <a
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
		</c:when>

		<c:when test="${not empty goQueue}">
			<s:form namespace="/crud" action="apply.accountNumber.queue"
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
						<a class="state-default" onclick="closeDetail();">取消</a> &nbsp;<a
							class="state-default" onclick="resetData();">重置</a>&nbsp;<a
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
			<s:form namespace="/crud" action="apply.accountNumber.update">
				<table cellspacing="1" class="detail-table">
					<tr>
						<th width="130">用戶代碼<span class="required">(&#8226;)</span></th>
						<td>${entity.userId }</td>
					</tr>
					<tr>
						<th width="130">用戶密碼</th>
						<td><s:password name="entity.userPw" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">用戶姓名<span class="required">(&#8226;)</span></th>
						<td><s:textfield name="entity.userName" cssClass="input_text" /></td>
					</tr>
					<tr>
						<th width="130">用戶名稱<span class="required">(&#8226;)</span></th>
						<td><c:choose>
								<c:when test="${login.role.role == '管理員' }">
									<s:select name="cusSerNo" cssClass="input_text"
										list="dsCustomer.results" listKey="serNo" listValue="name" />
								</c:when>
								<c:otherwise>
									<s:select headerValue="--用戶名稱--" headerKey="0" name="cusSerNo"
										cssClass="input_text" list="dsCustomer.results"
										listKey="serNo" listValue="name" />
								</c:otherwise>
							</c:choose></td>
					</tr>
					<tr>
						<th width="130">帳戶角色</th>
						<td><select name="role" id="apply_accountNumber_save_role"
							class="input_text">
								<c:forEach var="item" items="${roleList}" varStatus="status">
									<option value="${item.role }">${item.role }</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<th width="130">狀態</th>
						<td><select name="status"
							id="apply_accountNumber_save_status" class="input_text">
								<c:forEach var="item" items="${statusList}" varStatus="status">
									<option value="${item.status }">${item.status }</option>
								</c:forEach>
						</select></td>
					</tr>
				</table>
				<div class="button_box">
					<div class="detail-func-button">
						<a class="state-default" onclick="closeDetail();">取消</a> &nbsp;<a
							class="state-default" onclick="resetData();">重設</a>&nbsp; <a
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
		</c:otherwise>
	</c:choose>

	<s:if test="hasActionErrors()">
		<script language="javascript" type="text/javascript">
			var msg = "";
			<s:iterator value="actionErrors">msg += '<s:property escape="false"/><br>';
			</s:iterator>;
			goAlert('訊息', msg);
		</script>
	</s:if>
</body>
</html>