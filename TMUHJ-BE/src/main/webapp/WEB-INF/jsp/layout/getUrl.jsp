<%
out.println(request.getRequestURL());

out.println(request.getAttribute("startDate"));
request.getSession().removeAttribute("startDate");
out.println(request.getSession().getAttribute("startDate"));
%>