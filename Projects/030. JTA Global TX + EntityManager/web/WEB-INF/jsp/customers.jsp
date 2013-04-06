<%@taglib prefix="jstl"
          uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="net.sevecek.videoboss.entity.Customer" %>
<%@page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:useBean id="customers"
             type="java.util.List<net.sevecek.videoboss.entity.Customer>"
             scope="request"/>
<jstl:url var="cssLink" value="/css/style.css"/>
<jstl:url var="newCustomerLink" value="/new-customer.html"/>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>VideoBoss</title>
    <meta http-equiv="Content-Type"
          content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css"
          href="${cssLink}"/>
</head>
<body>

    <h1>List of customers</h1>

    <h3>
        <a href="${newCustomerLink}">
            Add a customer
        </a>
    </h3>

    <table>
        <tr>
            <th>Number</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Address</th>
            <th>Action</th>
        </tr>

        <jstl:forEach var="customer" items="${customers}">
            <tr>
                <td>${customer.id}</td>
                <td><jstl:out value="${customer.firstName}"/></td>
                <td><jstl:out value="${customer.lastName}"/></td>
                <td><jstl:out value="${customer.address}"/></td>
                <td>
                    <jstl:url value="/edit-customer.html" var="editLink">
                        <jstl:param name="id" value="${customer.id}"/>
                    </jstl:url>
                    <jstl:url value="/delete-customer.html" var="deleteLink">
                        <jstl:param name="id" value="${customer.id}"/>
                        <jstl:param name="version" value="${customer.version}"/>
                    </jstl:url>
                    <a href="${editLink}">Edit</a>
                    <a href="${deleteLink}">Delete</a>
                </td>
            </tr>
        </jstl:forEach>
    </table>

</body>
</html>