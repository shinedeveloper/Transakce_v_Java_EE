<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:useBean id="results" type="java.util.Collection<java.lang.String>" scope="request"/>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="cs" lang="cs">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>JSP page</title>
</head>
<body>

    <h1>Web Application (<%= request.getContextPath() %>) is running</h1>
    <h2>This random value must change with each refresh indicating that website is still alive: <%= (int) (Math.random()*100.0) %></h2>

    <pre>
<%
    for (String singleResult : results) {
        out.println(singleResult);
    }
%>
    </pre>

</body>
</html>