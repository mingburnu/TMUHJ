<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
@charset "utf-8";
/* CSS Document */
body {
	margin: 0;
	padding: 0;
	background: #fff url('<c:url value = "/"/>resources/images/bg.png') 0 0
		repeat-x;
	overflow-y: scroll;
	*overflow-y: auto;
}

.input_01 {
	width: 400px;
	height: 35px;
	background: url('<c:url value = "/"/>resources/images/input_01.png') 0 0
		no-repeat;
	margin: 0;
	padding: 0 0 0 10px;
}

.input_01 span {
	display: block;
	background: url('<c:url value = "/"/>resources/images/input_01.png')
		100% 0 no-repeat;
	margin: 0;
	padding: 5px 10px 5px 0;
}

.select_01 div {
	height: 35px;
	background: url('<c:url value = "/"/>resources/images/input_03.png') 0 0
		no-repeat;
	margin: 0;
	padding: 0 0 0 10px;
}

.select_01 div a {
	display: block;
	height: 35px;
	line-height: 35px;
	background: url('<c:url value = "/"/>resources/images/input_03.png')
		100% 0 no-repeat;
	margin: 0;
	padding: 0 35px 0 0;
	text-decoration: none;
}

.btn_01 {
	display: -moz-inline-stack;
	display: inline-block;
	text-decoration: none;
	width: 100px;
	height: 27px;
	line-height: 27px;
	margin: 0 10px 0 0;
	padding: 0;
	font-size: 13px;
	color: #fff;
	background: url('<c:url value = "/"/>resources/images/btn_01.png') 0 0
		no-repeat;
	text-align: center;
}

.btn_01:hover {
	background: url('<c:url value = "/"/>resources/images/btn_01.png') 0
		-30px no-repeat;
}

#header {
	width: 960px;
	position: relative;
	left: 50%;
	margin-left: -480px;
	padding: 0;
	/**/
	background: url('<c:url value = "/"/>resources/images/header.png') 0 0
		no-repeat;
	height: 200px;
}

#menu_box .menu_01 {
	display: block;
	float: left;
	width: 130px;
	height: 35px;
	margin: 0 0 0 10px;
	background: url('<c:url value = "/"/>resources/images/menu_01.png') 0 0
		no-repeat;
	text-decoration: none;
	cursor: pointer;
}

#menu_box .menu_02 {
	display: block;
	float: left;
	width: 130px;
	height: 35px;
	margin: 0 0 0 10px;
	background: url('<c:url value = "/"/>resources/images/menu_02.png') 0 0
		no-repeat;
	text-decoration: none;
	cursor: pointer;
}

#menu_box .menu_03 {
	display: block;
	float: left;
	width: 130px;
	height: 35px;
	margin: 0 0 0 10px;
	background: url('<c:url value = "/"/>resources/images/menu_03.png') 0 0
		no-repeat;
	text-decoration: none;
	cursor: pointer;
}

.table_01 .t_01 {
	height: 30px;
	background: url('<c:url value = "/"/>resources/images/login_bg.png') 0 0
		no-repeat;
	margin: 0;
	padding: 0 10px;
	font-size: 13px;
}

.table_01 .t_02 a {
	margin: 0;
	padding: 0;
	display: block;
	width: 50px;
	height: 30px;
	background: url('<c:url value = "/"/>resources/images/login_btn.png') 0
		0 no-repeat;
	text-decoration: none;
}

.pager .bb {
	background: url('<c:url value = "/"/>resources/images/btn_02.png') 0 0
		no-repeat;
	display: inline-block;
	width: 40px;
	height: 27px;
	line-height: 27px;
	text-decoration: none;
}

.pager .bb:hover {
	background: url('<c:url value = "/"/>resources/images/btn_02.png') 0
		-30px no-repeat;
}

.pager .b {
	background: url('<c:url value = "/"/>resources/images/btn_02.png') -50px
		0 no-repeat;
	display: inline-block;
	width: 40px;
	height: 27px;
	line-height: 27px;
	text-decoration: none;
}

.pager .b:hover {
	background: url('<c:url value = "/"/>resources/images/btn_02.png') -50px
		-30px no-repeat;
}

.pager .p {
	display: inline-block;
	color: #e1510b;
	text-decoration: none;
	padding: 0 5px;
}

.pager .p:hover {
	color: #f6783a;
}

.pager .n {
	background: url('<c:url value = "/"/>resources/images/btn_02.png')
		-100px 0 no-repeat;
	display: inline-block;
	width: 40px;
	height: 27px;
	line-height: 27px;
	text-decoration: none;
}

.pager .n:hover {
	background: url('<c:url value = "/"/>resources/images/btn_02.png')
		-100px -30px no-repeat;
}

.pager .nn {
	background: url('<c:url value = "/"/>resources/images/btn_02.png')
		-150px 0 no-repeat;
	display: inline-block;
	width: 40px;
	height: 27px;
	line-height: 27px;
	text-decoration: none;
}

.pager .nn:hover {
	background: url('<c:url value = "/"/>resources/images/btn_02.png')
		-150px -30px no-repeat;
}
</style>