<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript" src='<c:url value="/resources/js/jquery-1.10.2.js" />'></script>
<script type="text/javascript" src='<c:url value="/resources/js/jquery-ui-1.10.4.custom.js" />'></script>

<script type="text/javascript">

$(function() {
	
	//綁定list table滑鼠滑入滑出事件
	$(".table.table-hover tbody tr").each(function() {
		$(this).mouseover(function() {
			$(this).css("cursor", "pointer");
		});
		
		$(this).mouseout(function() {
			$(this).css("cursor", "auto");
		});
	});
	
	//綁日曆
	var dateFormat = '<spring:eval expression="@systemConfigurer.getProperty('datepicker.date.pattern')" />';
	$("[name*='Date']").datepicker({dateFormat:dateFormat});
	
	$("[name*='irthday']").datepicker({dateFormat:dateFormat,changeMonth: true,
    changeYear: true});
	
	//控制頁面的input是否disable
	<c:if test="${formModel.disableAllInput}">
		$("input:not(.return), select").each(function() {
			$(this).attr("disabled", true);
		});
	</c:if>
	
});

</script>

<%-- show error message --%>
<s:if test="hasActionErrors()">
	<div class="alert alert-error">
		<button type="button" class="close" data-dismiss="alert" onclick="$('.alert-error').hide();">&times;</button>
		<strong>錯誤訊息</strong>
		<s:actionerror />
	</div>
</s:if>

<%-- show message --%>
<s:if test="hasActionMessages()">
	<div class="alert alert-success">
		<button type="button" class="close" data-dismiss="alert" onclick="$('.alert-success').hide();">&times;</button>
		<strong>系統訊息</strong>
		<s:actionmessage/>
	</div>
</s:if>
