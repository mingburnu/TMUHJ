<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
#div_Detail .content {
	/*width: 800px;*/
	position: absolute;
	top: 0px;
	/* left: 50%; */
	/* margin-left: -400px; */
	padding: 0px;
}

#div_Detail .content .header,#div_Detail .content .contain {
	width: 100%;
}
</style>
</head>
<body>
	<table cellspacing="1" class="list-table">
		<tbody>
			<tr>
				<c:forEach var="item" items="${excelWorkSheet.columns}"
					varStatus="status">
					<th>${item}</th>
				</c:forEach>
				<th></th>
			</tr>
			<c:forEach var="item" items="${excelWorkSheet.data}"
				varStatus="status">
				<tr>
					<!--<td align="center" class="td_first" nowrap><input
						type="checkbox" class="checkbox" name="checkItem"
						value="${item.serNo}"></td>-->
					<td>${item.chineseTitle }</td>
					<td>${item.englishTitle }</td>
					<td>${item.abbreviationTitle }</td>
					<td>${item.issn }</td>
					<td>${item.languages }</td>
					<td>${item.publishName }</td>
					<td>${item.publishYear }</td>
					<td>${item.publication }</td>
					<td>${item.congressClassification }</td>
					<td>${item.resourcesBuyers.startDate }</td>
					<td>${item.resourcesBuyers.maturityDate }</td>
					<td>${item.resourcesBuyers.rCategory.category }</td>
					<td align="center">${item.resourcesBuyers.rType.type }</td>
					<td>${item.resourcesBuyers.dbChtTitle }</td>
					<td align="center">${item.resourcesBuyers.dbEngTitle }</td>
					<td align="center"><c:forEach var="customer"
							items="${item.customers}" varStatus="status">
				${customer.name }
				</c:forEach></td>
					<td align="center"><c:forEach var="customer"
							items="${item.customers}" varStatus="status">
				${customer.engName }
				</c:forEach></td>
					<td align="center">${item.exist }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>