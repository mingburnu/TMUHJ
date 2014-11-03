<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="header">&nbsp;</div>
<div id="menu_box">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left"><a class="menu_01"
				href="<c:url value = '/'/>page/home.action">&nbsp;</a> <a
				class="menu_02" href="<c:url value = '/'/>page/query.action">&nbsp;</a>
				<c:choose>
					<c:when
						test="${(login.role =='使用者') && (login.customer.contactUserName=='未登入用戶')}">
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