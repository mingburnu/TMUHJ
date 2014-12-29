<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript">
//切換查詢項目
$(document).ready(function() {
	$("select#listForm_searchCondition").change(function() {
		$("input#search").attr("name", $(this).val());
	});
});

//刪除後引導頁面
$(document).ready(function() {
	if($("table.list-table:eq(0) tbody tr").length==2&&$("form#apply_customer_list input#listForm_currentPageHeader").val()>1){
		 gotoPage($("form#apply_customer_list input#listForm_currentPageHeader").val()-1);
	};
});

	function goSearch(){
	    goMain("<%=request.getContextPath()%>/crud/apply.customer.list.action",
				"#apply_customer_list", "");
	}
	
	//新增
	function goAdd(){
	        goDetail('<%=request.getContextPath()%>/crud/apply.customer.query.action','客戶-新增');
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
		
		//進行刪除動作
		if (IsSelected) {
			var f = {
                    trueText:'是',
                    trueFunc:function(){
                            var url = '<%=request.getContextPath()%>/crud/apply.customer.deleteChecked.action';
                            var data = $('#apply_customer_list').serialize()+'&pager.offset='+'${ds.pager.offset}'+'&pager.currentPage='+'${ds.pager.currentPage}'+'&pager.offsetPoint'+'${ds.pager.offset}';
                            goMain(url,'',data);
                    },
                    falseText:'否',
                    falseFunc:function(){
                            //不進行刪除...
                    }
            };
			goAlert('提醒','您確定要刪除所勾選的資料嗎?',f);
		} else {
			goAlert("提醒","請選擇一筆或一筆以上的資料");
		}
	}
	
	//資料檢視
	function goView(serNo){
	        var url = "<c:url value = '/'/>crud/apply.customer.view.action";
	        var data = 'viewSerNo='+serNo;
	        goDetail(url,'用戶-檢視',data);
	}
	
	//更新資料
	function goUpdate(serNo) {
		goDetail('<%=request.getContextPath()%>/crud/apply.customer.query.action?'+'entity.serNo='+serNo,'客戶-修改');
	}
	
	//單筆刪除
	function goDel(serNo){
		var f = {
			trueText:'是',
			trueFunc:function(){
	                        var url = '<c:url value = '/'/>crud/apply.customer.delete.action';
	                        var data =$('#apply_customer_list').serialize()+'&entity.serNo='+serNo+'&pager.offset='+'${ds.pager.offset}'+'&pager.currentPage='+'${ds.pager.currentPage}'+'&pager.offsetPoint'+'${ds.pager.offset}';
	                        goMain(url,'',data);
			},
			falseText:'否',
			falseFunc:function(){
				//不進行刪除...
			}
		};
		goAlert('提醒','確定要刪除此筆資料嗎?',f);
	}
	
	//IP Range管理
	function goIpRangeManager(serNo){
	    var url = '<c:url value = '/'/>crud/apply.ipRange.list.action';
	    var data = 'entity.cusSerNo='+serNo;
	    goDetail(url,'客戶-IP Range管理',data);
	}
	
	//GoPage
	function gotoPage(page){
		var totalPage = $("span.totalNum:eq(0)").html();
	    var recordPerPage="${ds.pager.recordPerPage}";
	    var offset=parseInt(recordPerPage)*(parseInt(page)-1);
	    if(parseInt(page) < 1){
	        page=1;
	        offset=parseInt(recordPerPage)*(parseInt(page)-1);
	    }
	    else if(parseInt(page)>parseInt(totalPage)){
	        page=totalPage;
	        offset=parseInt(recordPerPage)*(parseInt(page)-1);
	    }
	    goMain('<c:url value = '/'/>crud/apply.customer.list.action','#apply_customer_list','&pager.offset='+offset+'&pager.currentPage='+page);
	}
	
	//變更顯示筆數
    function chagePageSize(recordPerPage,recordPoint){
            goMain('<c:url value = '/'/>crud/apply.customer.list.action','#apply_customer_list','&recordPerPage='+recordPerPage+'&recordPoint='+recordPoint);
    }
	
</script>
</head>
<body>
	<s:form action="apply.customer.list" namespace="/crud" method="post"
		onsubmit="return false;">
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
									<c:set var="option">
										<%=request.getParameter("option")%>
									</c:set>
									<c:choose>
										<c:when test="${option=='entity.name' }">
											<option value="entity.name" selected="selected">用戶名稱</option>
											<option value="entity.engName">英文名稱</option>
										</c:when>
										<c:when test="${option=='entity.engName' }">
											<option value="entity.name">用戶名稱</option>
											<option value="entity.engName" selected="selected">英文名稱</option>
										</c:when>
										<c:otherwise>
											<option value="entity.name">用戶名稱</option>
											<option value="entity.engName">英文名稱</option>
										</c:otherwise>
									</c:choose>
							</select></td>
							<c:choose>
								<c:when test="${option=='entity.name' }">
									<td align="left"><input type="text" name="entity.name"
										maxlength="20" id="search" class="input_text"
										value="<%if (request
								.getParameter(request.getParameter("option")) != null) {
							out.print(request.getParameter(request
									.getParameter("option")));
						}%>">
									</td>
								</c:when>
								<c:when test="${option=='entity.engName' }">
									<td align="left"><input type="text" name="entity.engName"
										maxlength="20" id="search" class="input_text"
										value="<%if (request
								.getParameter(request.getParameter("option")) != null) {
							out.print(request.getParameter(request
									.getParameter("option")));
						}%>"></td>
								</c:when>
								<c:otherwise>
									<td align="left"><input type="text" name="entity.name"
										maxlength="20" id="search" class="input_text">
									</td>
								</c:otherwise>
							</c:choose>
							<td align="left"><a class="state-default"
								onclick="goSearch();">查詢</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div id="div_nav">
			目前位置：<span>客戶管理</span> &gt; <span>基本設定</span>
		</div>
		<div class="list-box">
			<div class="list-buttons">
				<c:choose>
					<c:when test="${(not empty ds.pager.totalRecord)&& (0 ne ds.pager.totalRecord) }">
						<a class="state-default" onclick="allSelect(1);">全選</a>
						<a class="state-default" onclick="allSelect(0);">取消</a>
						<a class="state-default" onclick="goAdd();">新增</a>
						<a class="state-default" onclick="goDelete();">刪除</a>
					</c:when>
					<c:otherwise>
						<a class="state-default" onclick="goAdd();">新增</a>
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
					<c:forEach var="item" items="${ds.results}" varStatus="status">
						<tr>
							<td align="center" class="td_first" nowrap><c:choose>
									<c:when test="${1 eq  item.serNo }"></c:when>
									<c:otherwise>
										<input type="checkbox" class="checkbox" name="checkItem"
											value="${item.serNo}">
									</c:otherwise>
								</c:choose></td>
							<td>${item.name }</td>
							<td align="center">${item.tel }</td>
							<td>${item.address }</td>
							<td align="center"><c:choose>
									<c:when test="${1 eq  item.serNo }">
										<a class="state-default2" onclick="goView(${item.serNo });"><span
											class="icon-default icon-view"></span>檢視</a>
										<a class="state-default2" onclick="goUpdate(${item.serNo});"><span
											class="icon-default icon-edit"></span>修改</a>
									</c:when>
									<c:otherwise>
										<a class="state-default2" onclick="goView(${item.serNo });"><span
											class="icon-default icon-view"></span>檢視</a>
										<a class="state-default2" onclick="goUpdate(${item.serNo});"><span
											class="icon-default icon-edit"></span>修改</a>
										<a class="state-default2" onclick="goDel(${item.serNo});"><span
											class="icon-default icon-delete"></span>刪除</a>
										<a class="state-default2"
											onclick="goIpRangeManager(${item.serNo});">IP Range管理</a>
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="page-box" align="right">
				<table border="0" cellspacing="0" cellpadding="0">
					<tbody>

						<c:if test="${ds.pager.totalRecord > 0 }">
							<tr>
								<td><jsp:include page="/WEB-INF/jsp/layout/pagination.jsp">
										<jsp:param name="namespace" value="/crud" />
										<jsp:param name="action" value="apply.customer.list" />
										<jsp:param name="pager" value="${ds.pager}" />
										<jsp:param name="recordPerPage"
											value="${ds.pager.recordPerPage}" />
										<jsp:param name="detail" value="0" />
									</jsp:include></td>
								<td><c:set var="pageFactor"
										value="${ds.pager.totalRecord/ds.pager.recordPerPage}" /> <c:set
										var="totalPage">
										<fmt:formatNumber type="number" pattern="#"
											value="${pageFactor+(1-(pageFactor%1))%1}" />
									</c:set> 每頁顯示 <select name="recordPerPage" id="listForm_pageSize"
									onchange="chagePageSize(this.value,${ds.pager.recordPoint })">
										<option value="${ds.pager.recordPerPage}">${ds.pager.recordPerPage}</option>
										<option value="5">5</option>
										<option value="10">10</option>
										<option value="20">20</option>
										<option value="50">50</option>
								</select> 筆紀錄, 第 <input id="listForm_currentPageHeader"
									value="${ds.pager.currentPage }" type="number" min="1"
									max="${totalPage }" onchange="gotoPage(this.value)"> 頁,
									共<span class="totalNum">${totalPage }</span>頁</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
			<div class="detail_note">
				<div class="detail_note_title">Note</div>
				<div class="detail_note_content">
				<c:if test="${0 eq ds.pager.totalRecord}">
				<span>查無資料</span>
				</c:if>
				</div>
			</div>
		</div>
	</s:form>

	<s:if test="hasActionMessages()">
		<script language="javascript" type="text/javascript">
            var msg = "";
            <s:iterator value="actionMessages">
                msg += '<s:property escape="false"/><br>';
            </s:iterator>;
            goAlert('訊息', msg);
        </script>
	</s:if>
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