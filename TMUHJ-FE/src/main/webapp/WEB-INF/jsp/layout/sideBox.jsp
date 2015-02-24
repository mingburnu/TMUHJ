<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="side_box">
	<a onclick="goURL('<c:url value = '/'/>about.jsp')"><img
		src="<c:url value = '/'/>resources/images/about.png"></a> <a
		onclick="goURL('<c:url value = '/'/>contact.jsp')"><img
		src="<c:url value = '/'/>resources/images/contact.png"></a> <a
		onclick="goURL('<c:url value = '/'/>help.jsp')"><img
		src="<c:url value = '/'/>resources/images/help.png"></a>
</div>
<div id="ad_box">
	<a class="ad_title" href="#"><img
		src="<c:url value = '/'/>resources/images/ad_title.png"></a> <a
		class="ad" target="_blank" href="http://www.mohw.gov.tw/"><img
		src="<c:url value = '/'/>resources/images/ad_01.png"></a> <a
		class="ad" target="_blank" href="http://www.hpa.gov.tw/"><img
		src="<c:url value = '/'/>resources/images/ad_02.png"></a> <a
		class="ad" target="_blank" href="http://www.cdc.gov.tw/"><img
		src="<c:url value = '/'/>resources/images/ad_03.png"></a>
</div>