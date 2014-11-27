<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>跨司署電子資料庫索引查詢平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link href="<c:url value = '/'/>resources/css/air-ui-cms.css"
	rel="stylesheet" type="text/css" />
<link href="<c:url value = '/'/>resources/css/jquery.autocomplete.css"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value = '/'/>resources/css/smoothness/jquery-ui-1.7.2.custom.css"
	rel="stylesheet" type="text/css" />
<link href="<c:url value = '/'/>resources/css/jquery.datepick.css"
	rel="stylesheet" type="text/css" />
<title>跨司署電子資料庫索引查詢平台</title>
<script language="javascript" type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery-1.3.2.js">
	
</script>
<script language="javascript" type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery-ui-1.7.2.custom.min.js">
	
</script>
<script language="javascript" type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery.bgiframe.pack.js">
	
</script>
<script language="javascript" type="text/javascript"
	src="<c:url value = '/'/>resources/js/common.js">
	
</script>
<script language="javascript" type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery.form.js">
	
</script>
<script language="javascript" type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery.autocomplete.js">
	
</script>
<script language="javascript" type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery.datepick.js">
	
</script>
<script type="text/javascript">
	//全選之函式
	function allSelect(action) {
		$(document).ready(function() {
			for (var i = 0; i < $(".checkbox").length; i++) {
				if (action == 1) {
					$(".checkbox").get(i).checked = true;
				} else {
					$(".checkbox").get(i).checked = false;
				}
			}
		});
	}

	//刪除多筆資料之函式
	function goDelete() {
		//檢查資料是否已被勾選
		var IsSelected = false;
		var serNoStr = "";
		var checkbox_checked_num = 0;
		for (var i = 0; i < $(".checkbox").length; i++) {
			if ($(".checkbox").get(i).checked) {
				serNoStr = serNoStr + $(".checkbox").get(i).value + "；；";
				IsSelected = true;
				checkbox_checked_num++;
			}
		}
		if (!IsSelected) {
			$("#div_Alert .content > .header > .title").html("提醒");
			$("#div_Alert .content > .contain").html("請選擇一筆或一筆以上的資料");
			$("#div_Alert .content > .func-button").html(
					'<a class="state-default" onclick="closeAlert();">關閉</a>');
			$("#div_Alert").show();
		} else {
			$("#div_Alert .content > .header > .title").html("提醒");
			$("#div_Alert .content > .contain").html("您確定要刪除所勾選的資料嗎?");
			$("#div_Alert .content > .func-button")
					.html(
							'<a id="true_btn" class="state-default" onclick="closeAlert();">是</a>&nbsp;&nbsp;'
									+ '<a id="false_btn" class="state-default" onclick="closeAlert();">否</a>&nbsp;&nbsp;');
			$("#div_Alert").show();

		}
	}

	//修改用戶
	function goUpdate() {
		$("div#div_Detail").show();
		$("form#apply_customer_save").hide();
	}

	//重設所有欄位(清空)
	function resetData() {
		$("table tr td input").val("");
	}

	//用戶查詢選項
	$(document).ready(function() {
		$("select#listForm_searchCondition").change(function() {
			$("input#search").attr("name", $(this).val());
		});
	});

	function showLoading() {
		$("div#div_Loading").show();
	}

	$(document).ready(function() {
		showMenuItems('1');
	});

	$(document).ready(function() {

	});
</script>
<style type="text/css">
input:-webkit-autofill {
	-webkit-box-shadow: 0 0 0px 1000px #ffffff inset;
}
</style>
</head>
<body>
	<div id="div-wrapper">
		<jsp:include page="/WEB-INF/jsp/layout/header.jsp" />
		<div id="div-middle">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<td align="left" valign="top" width="175"><jsp:include
								page="/WEB-INF/jsp/layout/menu.jsp" /></td>
						<td align="left" valign="top">
							<div id="div-contain">

								<s:form action="apply.customer.list" namespace="/crud"
									method="post">
									<input type="hidden" name="totalPage" value="9"
										id="listForm_totalPage">
									<div class="tabs-box">
										<div>
											<a id="tabs-items_A" class="tabs-items-hover"
												onclick="showTabsContain('A')"><span
												class="tabs-items-hover-span">查詢</span></a>
										</div>
										<div id="TabsContain_A" class="tabs-contain">
											<table border="0" cellspacing="4" cellpadding="0">

												<tbody>
													<tr>
														<td align="left"><select name="option"
															id="listForm_searchCondition">
																<option value="entity.name">用戶名稱</option>
																<option value="entity.engName">英文名稱</option>
														</select></td>
														<td align="left"><input type="text"
															name="entity.name" maxlength="20" id="search"
															class="input_text"></td>
														<td align="left"><button class="state-default"
																onclick="showLoading();">查詢</button></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</s:form>
								<form>
									<div id="div_nav">
										目前位置：<span>客戶管理</span> &gt; <span>基本設定</span>
									</div>
									<div class="list-box">
										<div class="list-buttons">
											<c:choose>
												<c:when test="${not empty ds.pager.totalRecord }">
													<a class="state-default" onclick="allSelect(1);">全選</a>
													<a class="state-default" onclick="allSelect(0);">取消</a>
													<a class="state-default"
														href="<c:url value = '/'/>crud/apply.customer.query.action">新增</a>
													<a class="state-default" onclick="goDelete();">刪除</a>
												</c:when>
												<c:otherwise>
													<a class="state-default"
														href="<c:url value = '/'/>crud/apply.customer.query.action">新增</a>
												</c:otherwise>
											</c:choose>
										</div>
										<table cellspacing="1" class="list-table">
											<tbody>
												<tr>
													<td colspan="5" class="topic">基本設定</td>
												</tr>
												<tr>
													<th width="50" align="center">&nbsp;</th>
													<th>用戶名稱</th>
													<th>電話</th>
													<th>地址</th>
													<th>操作</th>
												</tr>
												<c:forEach var="item" items="${ds.results}"
													varStatus="status">
													<tr>
														<td align="center" class="td_first" nowrap><c:choose>
																<c:when test="${1 eq  item.serNo }"></c:when>
																<c:otherwise>
																	<input type="checkbox" class="checkbox"
																		name="checkItem" value="${item.serNo}">
																</c:otherwise>
															</c:choose></td>
														<td>${item.name }</td>
														<td align="center">${item.tel }</td>
														<td>${item.address }</td>
														<td align="center"><c:choose>
																<c:when test="${1 eq  item.serNo }">
																	<a class="state-default2" onclick="goView('1');"><span
																		class="icon-default icon-view"></span>檢視</a>
																	<a class="state-default2" onclick="goUpdate('1');"><span
																		class="icon-default icon-edit"></span>修改</a>
																</c:when>
																<c:otherwise>
																	<a class="state-default2" onclick="goView('7');"><span
																		class="icon-default icon-view"></span>檢視</a>
																	<a class="state-default2" onclick="goUpdate('7');"><span
																		class="icon-default icon-edit"></span>修改</a>
																	<a class="state-default2"
																		onclick="goDel('7','碩亞高中',1);"><span
																		class="icon-default icon-delete"></span>刪除</a>
																	<a class="state-default2"
																		onclick="goIpRangeManager('7');">IP Range管理</a>
																</c:otherwise>
															</c:choose></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<div class="page-box" align="right">
											<table border="0" cellspacing="0" cellpadding="0">

												<tbody>
													<tr>

														<td>&nbsp;&nbsp; <a class="state-default"
															href="javascript:gotoPage(2);">下一頁</a> &nbsp;&nbsp;
														</td>
														<td>每頁顯示 <select name="pageSize"
															id="listForm_pageSize" onchange="chagePageSize()">
																<option value="10">10</option>
																<option value="20">20</option>
																<option value="50" selected="selected">50</option>


														</select> 筆記錄, 第 <select name="currentPageHeader" size="1"
															id="listForm_currentPageHeader"
															onchange="gotoPage(this.value)">
																<option value="1" selected="selected">1</option>
																<option value="2">2</option>
																<option value="3">3</option>
																<option value="4">4</option>
																<option value="5">5</option>
																<option value="6">6</option>
																<option value="7">7</option>
																<option value="8">8</option>
																<option value="9">9</option>


														</select> 頁, 共<span class="totalNum">9</span>頁
														</td>

													</tr>
													<tr>
														<td align="left" class="p_02"><jsp:include
																page="/WEB-INF/jsp/layout/pagination.jsp">
																<jsp:param name="namespace" value="/crud" />
																<jsp:param name="action" value="apply.customer.list" />
																<jsp:param name="pager" value="${ds.pager}" />
																<jsp:param name="recordPerPage"
																	value="${ds.pager.recordPerPage}" />
															</jsp:include></td>
														<td align="right" class="p_01"><c:set
																var="pageFactor"
																value="${ds.pager.totalRecord/ds.pager.recordPerPage}" />
															<c:set var="totalPage">
																<fmt:formatNumber type="number" pattern="#"
																	value="${pageFactor+(1-(pageFactor%1))%1}" />
															</c:set> <input value="${ds.pager.currentPage }" type="number"
															name="page" min="1" max="${totalPage }"
															onchange="jumpPage()">/${totalPage }，共 <span
															class="total_num">${ds.pager.totalRecord}</span>筆資料</td>
													</tr>
												</tbody>
											</table>
										</div>
										<div class="detail_note">
											<div class="detail_note_title">Note</div>
											<div class="detail_note_content"></div>
										</div>
									</div>
								</form>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="div-footer">© 版權聲明:本系統版權歸碩亞數碼所有</div>
	</div>

	<div id="div_Detail" style="${addShow}">
		<div class="overlay"
			style="width: 1423px; height: 100vh; top: 0px; left: 0px;"></div>
		<div class="content">
			<div class="header">
				<div class="title">客戶-新增</div>
				<div class="close">
					<a href="#" onclick="closeDetail();">關閉</a>
				</div>
			</div>
			<div class="contain">
				<div id="contaner">
					<div id="contaner_box">
						<div class="content_box">
							<table cellspacing="0" cellpadding="0" class="input_form"
								width="400">
								<s:form namespace="/crud" action="apply.customer.save">
									<s:hidden name="entity.serNo" />
									<table cellspacing="1" class="detail-table">
										<tr>
											<th width="130">用戶名稱<span class="required">(&#8226;)</span></th>
											<td><s:textfield name="entity.name"
													cssClass="input_text" /></td>
										</tr>
										<tr>
											<th width="130">用戶英文名稱</th>
											<td><s:textfield name="entity.engName"
													cssClass="input_text" /></td>
										</tr>
										<tr>
											<th width="130">聯絡人</th>
											<td><s:textfield name="entity.contactUserName"
													cssClass="input_text" /></td>
										</tr>
										<tr>
											<th width="130">地址</th>
											<td><s:textfield name="entity.address"
													cssClass="input_text" /></td>
										</tr>
										<tr>
											<th width="130">電話</th>
											<td><s:textfield name="entity.tel" cssClass="input_text" /></td>
										</tr>
										<tr>
											<th width="130">Email</th>
											<td><s:textfield name="entity.email"
													cssClass="input_text" /></td>
										</tr>
									</table>
									<div class="button_box">
										<div class="detail-func-button">
											<a class="state-default" onclick="closeDetail();resetData();">取消</a>
											&nbsp;<a class="state-default" onclick="resetData();">重設</a>&nbsp;
											<button class="state-default" onclick="showLoading();">確認</button>
										</div>
									</div>
									<div class="detail_note">
										<div class="detail_note_title">Note</div>
										<div class="detail_note_content">
											<span class="required">(&#8226;)</span>為必填欄位
										</div>
									</div>
								</s:form>

							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="div_Detail_2">
		<div class="overlay"
			style="width: 1423px; height: 383px; top: 0px; left: 0px;"></div>
		<div class="content">
			<div class="header">
				<div class="title"></div>
				<div class="close">
					<a href="#" onclick="closeDetail_2();">關閉</a>
				</div>
			</div>
			<div class="contain">
				<s:form namespace="/crud" action="apply.customer.update">
					<s:hidden name="entity.serNo" />
					<table cellspacing="1" class="detail-table">
						<tr>
							<th width="130">用戶名稱<span class="required">(&#8226;)</span></th>
							<td><s:textfield name="entity.name" cssClass="input_text" /></td>
						</tr>
						<tr>
							<th width="130">用戶英文名稱</th>
							<td><s:textfield name="entity.engName" cssClass="input_text" /></td>
						</tr>
						<tr>
							<th width="130">聯絡人</th>
							<td><s:textfield name="entity.contactUserName"
									cssClass="input_text" /></td>
						</tr>
						<tr>
							<th width="130">地址</th>
							<td><s:textfield name="entity.address" cssClass="input_text" /></td>
						</tr>
						<tr>
							<th width="130">電話</th>
							<td><s:textfield name="entity.tel" cssClass="input_text" /></td>
						</tr>
						<tr>
							<th>Email</th>
							<td><s:textfield name="entity.tel" cssClass="input_text" /></td>
						</tr>
					</table>
					<div class="button_box">
						<div class="detail-func-button">
							<a class="state-default" onclick="closeDetail();">取消</a> &nbsp;<a
								class="state-default" onclick="resetData();">重設</a>&nbsp;
							<button type="submit" id="button" class="state-default">確認</button>
						</div>
					</div>
					<div class="detail_note">
						<div class="detail_note_title">Note</div>
						<div class="detail_note_content">
							<span class="required">(&#8226;)</span>為必填欄位
						</div>
					</div>
				</s:form>
			</div>
		</div>
	</div>

	<div id="div_Customers" style="${displayShow}">
		<div class="overlay"
			style="width: 1423px; height: 100vh; top: 0px; left: 0px;"></div>
		<div class="content">
			<div class="header">
				<div class="title">${title}</div>
				<div class="close">
					<a href="#" onclick="closeCustomers();">&nbsp;</a>
				</div>
			</div>
			<div class="contain">
				<table cellspacing="1" class="detail-table">
					<tbody>
						<tr>
							<th width="130">用戶名稱</th>
							<td>${customer.name }</td>

						</tr>
						<tr>
							<th width="130">用戶英文名稱</th>
							<td>${customer.engName }</td>
						</tr>
						<tr>
							<th width="130">聯絡人</th>
							<td>${customer.contactUserName }</td>
						</tr>
						<tr>
							<th width="130">地址</th>
							<td>${customer.address }</td>
						</tr>
						<tr>
							<th width="130">電話</th>
							<td>${customer.tel }</td>
						</tr>
						<tr>
							<th width="130">E-Mail</th>
							<td>${customer.email }</td>
						</tr>
					</tbody>
				</table>
				<div class="detail-func-button">
					<a class="state-default" onclick="closeCustomers();">關閉</a>
				</div>
			</div>
		</div>
	</div>


	<div id="div_Alert" style="${alertShow}">
		<div class="overlay"
			style="width: 1423px; height: 100vh; top: 0px; left: 0px;"></div>
		<div class="content">
			<div class="header">
				<div class="title">訊息</div>
				<div class="close">
					<a href="#" onclick="closeAlert();">&nbsp;</a>
				</div>
			</div>
			<div class="contain">
				<c:if test="${not empty success }">${success }<br>
				</c:if>
				<c:if test="${not empty space }">${space }<br>
				</c:if>
				<c:if test="${not empty nameRepeat }">${nameRepeat }<br>
				</c:if>
			</div>
			<div class="func-button">
				<a class="state-default" onclick="closeAlert();">關閉</a>
			</div>
		</div>
	</div>

	<div id="div_Loading" style="display: none;">
		<div class="overlay"
			style="width: 1423px; height: 100vh; top: 0px; left: 0px;"></div>
		<div class="content">
			<span class="div_loading_icon"></span>
		</div>
	</div>

	<div style="display: none;"></div>
</body>
</html>