<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript"
	src="<c:url value = '/'/>resources/js/jquery-1.7.2.min.js">
	
</script>

<c:set var="recordPerPage" value="${pager.recordPerPage}" />
<c:set var="totalRecord" value="${pager.totalRecord}" />
<c:set var="currentPage" value="${pager.currentPage}" />
<c:set var="factor" value="${totalRecord / recordPerPage}" />
<c:set var="lastPage" value="${factor + (1 - (factor % 1)) % 1}" />

<c:choose>
	<c:when
		test="${(empty cusSerNo) && (not empty keywords) && (empty option)}">
		<c:set var="goToPage">
			<c:url value="${param.namespace}/${param.action}.action" />
		</c:set>
		<c:set var="queryParameter">
		&recordPerPage=${param.recordPerPage }&keywords=${param.keywords}
		</c:set>
	</c:when>
	<c:when
		test="${(empty keywords) && (empty option) && (not empty cusSerNo)}">
		<c:set var="goToPage">
			<c:url value="${param.namespace}/${param.action}.action" />
		</c:set>
		<c:set var="queryParameter">
		&recordPerPage=${param.recordPerPage }&cusSerNo=${param.cusSerNo}
		</c:set>
	</c:when>
	<c:when
		test="${(not empty keywords) && (not empty option) && (empty cusSerNo)}">
		<c:set var="goToPage">
			<c:url value="${param.namespace}/${param.action}.action" />
		</c:set>
		<c:set var="queryParameter">
		&recordPerPage=${param.recordPerPage }&option=${param.option}&keywords=${param.keywords}
		</c:set>
	</c:when>
</c:choose>

<pg:pager url="${goToPage}" items="${totalRecord}"
	maxPageItems="${recordPerPage}" maxIndexPages="5">
	<pg:index>

		<pg:prev ifnull="true">
			<c:choose>
				<c:when test="${1 eq currentPage}">
					<a class="bb" href="#" onclick="return false;">&nbsp;</a>

				</c:when>
				<c:otherwise>
					<a class="bb"
						href="${pageUrl}&pager.currentPage=1${queryParameter}">&nbsp;</a>
				</c:otherwise>
			</c:choose>
		</pg:prev>
		<pg:prev ifnull="true">
			<c:choose>
				<c:when test="${1 eq currentPage}">
					<a class="b" href="#" onclick="return false;">&nbsp;</a>

				</c:when>
				<c:otherwise>
					<a class="b"
						href="${pageUrl}&pager.currentPage=${pageNumber}${queryParameter}">&nbsp;</a>

				</c:otherwise>
			</c:choose>
		</pg:prev>
		<pg:pages>
			<c:choose>
				<c:when test="${pageNumber eq currentPage}">
					<a class="p" href="#" onclick="return false;">${pageNumber}</a>

				</c:when>
				<c:otherwise>
					<a class="p"
						href="${pageUrl}&pager.currentPage=${pageNumber}${queryParameter}">${pageNumber}</a>

				</c:otherwise>
			</c:choose>
		</pg:pages>
		<pg:next ifnull="true">
			<c:choose>
				<c:when test="${lastPage eq currentPage}">
					<a class="n" href="#" onclick="return false;">&nbsp;</a>

				</c:when>
				<c:otherwise>
					<a class="n"
						href="${pageUrl}&pager.currentPage=${pageNumber}${queryParameter}">&nbsp;</a>

				</c:otherwise>
			</c:choose>
		</pg:next>
		<pg:next ifnull="true">
			<c:choose>
				<c:when test="${lastPage eq currentPage}">
					<a class="nn" href="#" onclick="return false;">&nbsp;</a>

				</c:when>
				<c:otherwise>
					<a class="nn"
						href="${pageUrl}&pager.currentPage=<fmt:formatNumber type="number" 
            pattern="0" value="${lastPage}" />${queryParameter}">&nbsp;</a>

				</c:otherwise>
			</c:choose>
		</pg:next>

	</pg:index>
</pg:pager>
<script type="text/javascript">
	$(document).ready(
			function() {
				var bb = $("a.bb").attr("href");
				var bbCut = bb.split("pager.offset");
				var parameter = "${queryParameter}";
				var newBb = bbCut[0] + "pager.offset=0&pager.currentPage=1"
						+ parameter;

				var nn = $("a.nn").attr("href");
				var nnCut = nn.split("pager.offset");

				var total = "${pager.totalRecord}";
				var recordPerPage = "${pager.recordPerPage}";
				var lastPageFloat = total / recordPerPage;
				var lastPage = Math.ceil(lastPageFloat);
				var lastOffset = (lastPage - 1) * recordPerPage;
				var newNn = nnCut[0] + "&pager.offset=" + lastOffset
						+ "&pager.currentPage=" + lastPage + parameter;

				$("a.bb").attr("href", newBb);
				$("a.nn").attr("href", newNn);

			});

	
</script>