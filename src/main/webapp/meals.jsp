<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <style>
        table {
            font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        /*tr:nth-child(even){background-color: #f2f2f2;}*/

        tr:hover {background-color: #ddd;}

        th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #00BFFF;
            color: white;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<br>
<table border="1px" align="center">
    <thead style="background:#00BFFF">
    <tr>
        <th>id</th><th>dateTime</th><th>Description</th><th>Calories</th><th>Exceed</th><th colspan="3"></th>
    </tr>
    </thead>

    <c:forEach var="mealItem" items="${mealWithExceededList}">
        <tr bgcolor="${mealItem.exceed == false ? "white" : "red"}">
            <td>${mealItem.id}</td>
            <td>${mealItem.dateTime.format(dateTimeFormatter)}</td>
            <td>${mealItem.description}</td>
            <td>${mealItem.calories}</td>
            <td>${mealItem.exceed}</td>
            <td><a href="/addMeal.jsp">Add</a></td>
            <td><a href="/editMeal.jsp?editMealId=${mealItem.id}">Edit</a></td>
            <td><a href="/deleteMeal.do?action=delete&deleteMealId=${mealItem.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
