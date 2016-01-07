<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	function goURL(url) {
		$.ajax({
			url : url,
			success : function(result) {
				$("#container").html(result);
			}
		});
		$("body").scrollTop(0);
	}
</script>
<div id="header">
	<a name="top"></a>&nbsp;
</div>
<div id="menu_box">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left"><a class="menu_01"
				href="javascript:goURL('<c:url value = '/'/>page/query.action');">&nbsp;</a> <a
				class="menu_02"
				href="javascript:goURL('<c:url value = '/'/>page/adv_query.action');">&nbsp;</a>
				<c:choose>
					<c:when test="${(empty login.serNo)}">
						<a class="menu_03" href="<c:url value = '/'/>login.jsp">&nbsp;</a>
					</c:when>
					<c:otherwise>
						<a class="menu_03" href="#">&nbsp;</a>
					</c:otherwise>
				</c:choose></td>
			<td align="right">
				<table border="0" cellpadding="0" cellspacing="0" class="table_01">
					<tr>
						<td class="t_01">${login.customer.name}，${login.customer.contactUserName}</td>
						<td class="t_02"><a
							href='<s:url namespace="/authorization" action="logout" />'>&nbsp;</a></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>