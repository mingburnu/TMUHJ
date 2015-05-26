<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String str = request.getParameter("test");
out.println(str);
out.println("<h1>www</h1>");

%>
<form action="<%=request.getContextPath() %>/testFilter.jsp" method="get">
<input type="text" name="test" >
<button>click</button>
</form>
</body>
</html>