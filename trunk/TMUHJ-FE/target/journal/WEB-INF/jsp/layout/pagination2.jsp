<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 

<div class="pagination">
	<c:set var="recordPerPage" value="${pager.recordPerPage}" />
	<c:set var="totalRecord" value="${pager.totalRecord}" />
	<c:set var="currentPage" value="${pager.currentPage}" />
	<c:set var="factor" value="${totalRecord / recordPerPage}" />
	<c:set var="lastPage" value="${factor + (1 - (factor % 1)) % 1}" />
	
	<c:set var="goToPage">
		<c:url value="${param.namespace}/${param.action}.action?entity.code=${param.code}&entity.regId=${param.regId}XXX&" />
	</c:set>
	
	<pg:pager url="${goToPage}" 
			items="${totalRecord}" maxPageItems="${recordPerPage}" maxIndexPages="5">
		<pg:index>
		 
			<ul>
				<pg:prev ifnull="true">
					<c:choose>
						<c:when test="${1 eq currentPage}">
							<li class="disabled">
								<a href="#" onclick="return false;">Prev</a>
							</li>
						</c:when>
						<c:otherwise>
							<li>
								<a href="${pageUrl}&pager.currentPage=${pageNumber}">Prev</a>
							</li>
						</c:otherwise>
					</c:choose>
				</pg:prev>
	
				<pg:pages>
					<c:choose>
						<c:when test="${pageNumber eq currentPage}">
							<li class="active">
								<a href="#" onclick="return false;">${pageNumber}</a>
							</li>
						</c:when>
						<c:otherwise>
							<li>
								<a href="${pageUrl}&pager.currentPage=${pageNumber}">${pageNumber}</a>
							</li>
						</c:otherwise>
					</c:choose>
				</pg:pages>
	
				<pg:next ifnull="true">
					<c:choose>
						<c:when test="${lastPage eq currentPage}">
							<li class="disabled">
								<a href="#" onclick="return false;">Next</a>
							</li>
						</c:when>
						<c:otherwise>
							<li>
								<a href="${pageUrl}&pager.currentPage=${pageNumber}">Next</a>
							</li>
						</c:otherwise>
					</c:choose>
				</pg:next>
			</ul>
		
		</pg:index>
	</pg:pager>
	
</div>