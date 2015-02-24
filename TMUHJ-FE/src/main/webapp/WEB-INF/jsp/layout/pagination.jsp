<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
function gotoPage(page){
	var totalPage = "${totalPage}";
    var recordPerPage="${ds.pager.recordPerPage}";
    var offset=parseInt(recordPerPage)*(parseInt(page)-1);
    if(parseInt(page) < 1){
        page=1;
        offset=parseInt(recordPerPage)*(parseInt(page)-1);
    }
    else if(parseInt(page)>parseInt(totalPage)){
        page=parseInt(totalPage);
        offset=parseInt(recordPerPage)*(parseInt(page)-1);
    }
    
    $('input[name=recordPoint]:eq(0)').remove();
	var action = $("form").attr("action");
	var data = $("form:eq(0)").serialize(); 
	var pageParameter = '?pager.offset=' + offset
			+ '&pager.currentPage=' + parseInt(page) + "&";

	var url=action + pageParameter + data;
	$.ajax({url: url, success: function(result){
            $("#container").html(result);
        }});
	$("body").scrollTop(0);
}

function upperChangeSize(recordPerPage) {
	var action = $("form").attr("action");
	var data = $("form:eq(0)").serialize();

	var recordPoint = '${ds.pager.recordPoint}';
	var newPage = Math.floor(parseInt(recordPoint)
			/ parseInt(recordPerPage) + 1);
	var newOffset = parseInt(recordPerPage) * (newPage - 1);
	var pageParameter = '?pager.offset=' + newOffset
			+ '&pager.currentPage=' + newPage + "&";

	var url=action + pageParameter + data;
	$.ajax({url: url, success: function(result){
        $("#container").html(result);
    }});
	$("body").scrollTop(0);
}

function bottomChangeSize(recordPerPage) {
	var action = $("form").attr("action");
	var data = $("form:eq(1)").serialize();

	var recordPoint = '${ds.pager.recordPoint}';
	var newPage = Math.floor(parseInt(recordPoint)
			/ parseInt(recordPerPage) + 1);
	var newOffset = parseInt(recordPerPage) * (newPage - 1);
	var pageParameter = '?pager.offset=' + newOffset
			+ '&pager.currentPage=' + newPage + "&";

	var url=action + pageParameter + data;
	$.ajax({url: url, success: function(result){
            $("#container").html(result);
    }});
	$("body").scrollTop(0);
}
    
</script>

<c:set var="recordPerPage" value="${pager.recordPerPage}" />
<c:set var="totalRecord" value="${pager.totalRecord}" />
<c:set var="currentPage" value="${pager.currentPage}" />
<c:set var="factor" value="${totalRecord / recordPerPage}" />
<c:set var="lastPage" value="${factor + (1 - (factor % 1)) % 1}" />

<pg:pager url="${goToPage}" items="${totalRecord}"
	maxPageItems="${recordPerPage}" maxIndexPages="5">
	<pg:index>

		<pg:prev ifnull="true">
			<c:choose>
				<c:when test="${1 eq currentPage}">
					<a class="bb" href="#" onclick="return false;">&nbsp;</a>

				</c:when>
				<c:otherwise>
					<a class="bb" onclick="gotoPage(1)">&nbsp;</a>
				</c:otherwise>
			</c:choose>
		</pg:prev>
		<pg:prev ifnull="true">
			<c:choose>
				<c:when test="${1 eq currentPage}">
					<a class="b" href="#" onclick="return false;">&nbsp;</a>

				</c:when>
				<c:otherwise>
					<a class="b" onclick="gotoPage(${pageNumber})">&nbsp;</a>

				</c:otherwise>
			</c:choose>
		</pg:prev>
		<pg:pages>
			<c:choose>
				<c:when test="${pageNumber eq currentPage}">
					<a class="p" href="#" onclick="return false;">${pageNumber}</a>

				</c:when>
				<c:otherwise>
					<a class="p" onclick="gotoPage(${pageNumber})">${pageNumber}</a>

				</c:otherwise>
			</c:choose>
		</pg:pages>
		<pg:next ifnull="true">
			<c:choose>
				<c:when test="${lastPage eq currentPage}">
					<a class="n" href="#" onclick="return false;">&nbsp;</a>

				</c:when>
				<c:otherwise>
					<a class="n" onclick="gotoPage(${pageNumber})">&nbsp;</a>

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
						onclick='gotoPage(<fmt:formatNumber type="number" 
            pattern="0" value="${lastPage}" />)'>&nbsp;</a>

				</c:otherwise>
			</c:choose>
		</pg:next>

	</pg:index>
</pg:pager>