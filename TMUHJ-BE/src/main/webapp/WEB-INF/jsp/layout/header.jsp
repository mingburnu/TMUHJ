<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="header">
	<table width="100%" border="0">
		<tr>
			<td align="left" nowrap><a href="<%=request.getContextPath()%>/"><img
					src="<%=request.getContextPath()%>/resources/images/header.jpg"
					alt="回到首頁" border="0" /></a></td>
			<td align="right" valign="top" nowrap>
				<div class="infomation">
					${login.userName }（${login.userId }） <a class="button_03"
						href='<s:url namespace="/authorization" action="logout" />'><span>登出</span></a>
					<a class="button_01" href="javascript:openModifyPasswdForm();"><span>密碼變更</span></a>
				</div>
			</td>
		</tr>
	</table>
</div>
