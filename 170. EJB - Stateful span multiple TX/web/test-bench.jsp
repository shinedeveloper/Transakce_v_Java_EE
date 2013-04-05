<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="cs" lang="cs">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>JSP page</title>
</head>
<body>
    <h1>Web Application (<%= request.getContextPath() %>) is running</h1>
    <h2>Still-alive random value: <%= (int) (Math.random()*100D) %></h2>

    <h2>Operation 1</h2>
    <iframe src="operation1.html" width="1000" height="300"></iframe>

    <br/>
    <br/>
    <br/>

    <h2>Operation 2</h2>
    <iframe src="operation2.html" width="1000" height="300"></iframe>

</body>
</html>