<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <section>
        <p>Укажите время для выборки</p>
        <form method="post" action="meals?action=filterdate">
            <dl>
                <dt>DateStart:</dt>
                <dd><input type="date" name="dateStart"></dd>
            </dl>
            <dl>
                <dt>DateEnd:</dt>
                <dd><input type="date" name="dateEnd"></dd>
            </dl>
            <dl>
                <dt>TimeStart:</dt>
                <dd><input type="time" name="timeStart"></dd>
            </dl>
            <dl>
                <dt>TimeEnd:</dt>
                <dd><input type="time" name="timeEnd"></dd>
            </dl>
            <button type="submit">Save</button>
        </form>
        </section>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>${meal.userId}</td>
                <td><a href="meals?action=update&id=${meal.id}&userId=${meal.userId}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}&userId=${meal.userId}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>