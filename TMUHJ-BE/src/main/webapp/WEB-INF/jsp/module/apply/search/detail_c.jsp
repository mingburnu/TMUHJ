<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="/WEB-INF/jsp/layout/css.jsp" />
<jsp:include page="/WEB-INF/jsp/layout/art.jsp" />
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>跨司署電子資料庫索引查詢平台</title>
</head>

<body>
<div id="wrapper">

<jsp:include page="/WEB-INF/jsp/layout/menu.jsp" />
<div id="container">
	<div id="main_b_box">
<!-- 內容開始 -->
<div class="detail">

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_03">
  <tr>
    <td class="t_01">刊名</td>
    <td class="t_02">${journal.englishTitle }</td>
  </tr>
  <tr>
    <td class="t_01">縮寫刊名</td>
    <td class="t_02">${journal.abbreviationTitle }</td>
  </tr>
  <tr>
    <td class="t_01">ISSN</td>
    <td class="t_02">${journal.issn }</td>
  </tr>
  <tr>
    <td class="t_01">出版項</td>
    <td class="t_02">${journal.publishName }</td>
  </tr>
  <tr>
    <td class="t_01">資料庫</td>
    <td class="t_02">${journal.dbChtTitle }</td>
  </tr>
  <tr>
    <td class="t_01">版本</td>
    <td class="t_02">${journal.version }</td>
  </tr>
  <tr>
    <td class="t_01">語文</td>
    <td class="t_02">${journal.languages }</td>
  </tr>
  <tr>
    <td class="t_01">出版年</td>
    <td class="t_02">${journal.publishYear }</td>
  </tr>
  <tr>
    <td class="t_01">刊別</td>
    <td class="t_02">${journal.publication }</td>
  </tr>
  <tr>
    <td class="t_01">國會分類號</td>
    <td class="t_02">${journal.congressClassification }</td>
  </tr>
  <tr>
    <td class="t_01">標題</td>
    <td class="t_02">${journal.caption }</td>
  </tr>
  <tr>
    <td class="t_01">編號</td>
    <td class="t_02">${journal.numB }</td>
  </tr>
  <tr>
    <td class="t_01">館藏</td>
    <td class="t_02">-</td>
  </tr>
</table>

<div align="center"><a class="btn_01" href="javascript:history.go(-1);">回 上 一 頁</a></div>

</div>
<!-- 內容結束 -->
	</div>
</div>

<jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />

</div>
</body>
</html>
