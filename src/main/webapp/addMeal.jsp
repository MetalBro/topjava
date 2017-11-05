<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 05.11.2017
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Meal</title>
</head>
<body>
<form method="POST" action="addMealdo?action=add" acceptCharset="UTF-8">
    <table>
        <tr>
            <td><label>Дата:</label></td>
            <td><input type="datetime-local" pattern="yyyy-MM-dd'T'HH:mm" name="dateTime"/></td>
        </tr>
        <tr>
            <td><label>Описание:</label></td>
            <td><input type="text" maxlength="255" name="description"/></td>
        </tr>
        <tr>
            <td><label>Калорийность:</label></td>
            <td><input type="number" maxlength="4" name="calories"/></td>
        </tr>
    </table>

    <input type="submit" value="Save" />
</form>
</body>
</html>
