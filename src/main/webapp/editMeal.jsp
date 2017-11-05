<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 05.11.2017
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Meal</title>
</head>
<body>
<form method="POST" action="editMeal.do?action=edit&id=${editMealId}" acceptCharset="UTF-8">
    <table>
        <tr>
            <td><label>Id еды в базе:</label></td>
            <td>${editMealId}/td>
        </tr>

        <tr>
            <td><label>Новая дата:</label></td>
            <td><input type="datetime" maxlength="100" name="dateTime"/></td>
        </tr>
        <tr>
            <td><label>Новое описание:</label></td>
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
