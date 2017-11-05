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

        input[type=submit] {
            width: 100%;
            height: 100%;
            text-align: center;
            border: none;
            border-radius: 15px;
            box-shadow: 0 9px #999;
            /*background-color: #4CAF50;*/
            background-color: white;
            cursor: pointer;
        }

        input[type=submit]:hover {
            /*background-color: #3e8e41;*/
            background-color: gray;
        }

        input[type=submit]:active {
            /*background-color: #3e8e41;*/
            background-color: gray;
            box-shadow: 0 5px #666;
            transform: translateY(4px);
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
            <%--<td><a href="addMeal.jsp">Add</a></td>--%>
            <td><form method="GET" action="addMeal.jsp"><input type="submit" value="Add" align=""/></form></td>
            <td><form method="POST" action="editMeal?action=preEdit&editMealId=${mealItem.id}"><input type="submit" value="Edit"/></form></td>
            <td><form method="POST" action="deleteMealdo?action=delete&deleteMealId=${mealItem.id}"><input type="submit" value="Delete"/></form></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
