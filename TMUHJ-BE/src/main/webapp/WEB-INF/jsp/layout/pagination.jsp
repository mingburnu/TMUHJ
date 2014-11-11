<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="recordPerPage" value="${pager.recordPerPage}" />
<c:set var="totalRecord" value="${pager.totalRecord}" />
<c:set var="currentPage" value="${pager.currentPage}" />
<c:set var="factor" value="${totalRecord / recordPerPage}" />
<c:set var="lastPage" value="${factor + (1 - (factor % 1)) % 1}" />

<c:set var="goToPage">
	<c:url value="${param.namespace}/${param.action}.action" />
</c:set>

<pg:pager url="${goToPage}" items="${totalRecord}"
	maxPageItems="${recordPerPage}" maxIndexPages="10">
	<pg:index>
		<pg:prev ifnull="true">
			<c:choose>
				<c:when test="${1 eq currentPage}">
					<a class="pages_a_site" href="#" onclick="return false;">第一頁</a>
				</c:when>
				<c:otherwise>
					<a class="pages_a" href="${pageUrl}&pager.currentPage=1">第一頁</a>

				</c:otherwise>
			</c:choose>
		</pg:prev>
		<pg:prev ifnull="true">
			<c:choose>
				<c:when test="${1 eq currentPage}">
					<a class="pages_a_site" href="#" onclick="return false;">前十頁</a>
				</c:when>
				<c:otherwise>
					<a class="pages_a"
						href="${pageUrl}&pager.currentPage=${pageNumber}">前十頁</a>

				</c:otherwise>
			</c:choose>
		</pg:prev>
		<pg:pages>
			<c:choose>
				<c:when test="${pageNumber eq currentPage}">
					<a class="pages_a_site" href="#" onclick="return false;">${pageNumber}</a>
				</c:when>
				<c:otherwise>
					<a class="pages_a"
						href="${pageUrl}&pager.currentPage=${pageNumber}">${pageNumber}</a>
				</c:otherwise>
			</c:choose>
		</pg:pages>
		<pg:next ifnull="true">
			<c:choose>
				<c:when test="${lastPage eq currentPage}">
					<a class="pages_a_site" href="#" onclick="return false;">後十頁</a>
				</c:when>
				<c:otherwise>
					<a class="pages_a"
						href="${pageUrl}&pager.currentPage=${pageNumber}">後十頁</a>
				</c:otherwise>
			</c:choose>
		</pg:next>
		<pg:next ifnull="true">
			<c:choose>
				<c:when test="${lastPage eq currentPage}">
					<a class="pages_a_site" href="#" onclick="return false;">最後頁</a>
				</c:when>
				<c:otherwise>
					<a class="pages_a"
						href="${pageUrl}&pager.currentPage=<fmt:formatNumber type="number" 
            pattern="0" value="${lastPage}" />">最後頁</a>
				</c:otherwise>
			</c:choose>
		</pg:next>

	</pg:index>
</pg:pager>
