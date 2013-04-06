<%@page contentType="text/html;charset=UTF-8" %>
<%@page import="net.sevecek.videoboss.entity.Customer" %>
<%@taglib prefix="jstl"
          uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jstl:url var="cssLink" value="/css/style.css"/>
<jstl:url var="actionLink" value="/new-customer.html"/>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>VideoBoss</title>
    <meta http-equiv="Content-Type"
          content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css"
          href="${cssLink}"/>
</head>
<body>

    <h1>New customer</h1>

    <form action="${actionLink}" method="post">
        <table>
            <tr>
                <th>Name</th>
                <td>
                    <input name="firstname"/>
                </td>
            </tr>
            <tr>
                <th>Surname</th>
                <td>
                    <input name="lastname"/>
                </td>
            </tr>
            <tr>
                <th>Address</th>
                <td>
                    <input name="address"/>
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