<%@page contentType="text/html;charset=UTF-8"
        import="net.sevecek.videoboss.entity.Customer" %>
<%@taglib prefix="jstl"
          uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:useBean id="customer" 
             type="net.sevecek.videoboss.entity.Customer"
             scope="request"/>
<jstl:url var="cssLink" value="/css/style.css"/>
<jstl:url var="actionLink" value="/edit-customer.html">
    <jstl:param name="id" value="${customer.id}"/>
</jstl:url>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>VideoBoss</title>
    <meta http-equiv="Content-Type"
          content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" 
          type="text/css"
          href="${cssLink}"/>
</head>
<body>

    <h1>Edit the customer</h1>

    <form action="${actionLink}" method="post">
        <table>
            <tr>
                <th>Number</th>
                <td>
                    ${customer.id}
                    <input type="hidden" name="version" 
                           value="${customer.version}"/>
                    <input type="hidden" name="deleted" 
                           value="${customer.deleted}"/>
                </td>
            </tr>
            <tr>
                <th>Name</th>
                <td>
                    <input name="firstname" 
                           value="<jstl:out value='${customer.firstName}'/>"/>
                </td>
            </tr>
            <tr>
                <th>Surname</th>
                <td>
                    <input name="lastname" 
                           value="<jstl:out value='${customer.lastName}'/>"/>
                </td>
            </tr>
            <tr>
                <th>Address</th>
                <td>
                    <input name="address" 
                           value="<jstl:out value='${customer.address}'/>"/>
                </td>
            </tr>
            <tr>
                <th>&nbsp;</th>
                <td>
                    <input type="submit" value="Save"/>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>