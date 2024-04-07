<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Games</title>
</head>
<body>

<h1>Добро пожаловать в GameShop!</h1><br/>
<h2>Все доступные игры</h2><br/>

<c:forEach var="game" items="${requestScope.games}">
    <ul>

        <li>Название: <c:out value="${game.title}"/></li>
        <li>ID: <c:out value="${game.id}"/></li>
        <li>Описание: <c:out value="${game.description}"/></li>
        <li>Цена: <c:out value="${game.price}"/></li>
    </ul>
    <hr/>

</c:forEach>
</body>
</html>
