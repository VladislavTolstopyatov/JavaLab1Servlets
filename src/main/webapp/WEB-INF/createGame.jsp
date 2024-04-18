<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создание игры</title>
    <style>
        <%@include file="/WEB-INF/css/createGame.css" %>
    </style>
</head>
<body>
<h1>Создание игры</h1><br>
<div class="create-game-form">
    <form action="${pageContext.request.contextPath}/createGame" method="post">
        <label for="gameTitle">Название:</label>
        <input type="text" id="gameTitle" name="gameTitle" required>

        <label for="gameDescription">Описание:</label>
        <textarea id="gameDescription" name="gameDescription" required></textarea>

        <label for="gamePrice">Цена:</label>
        <input type="number" id="gamePrice" name="gamePrice" min="0" step="0.01" required>

        <label for="releaseDate">Дата релиза:</label>
        <input type="date" id="releaseDate" name="releaseDate" required>

        <button type="submit" class="submit-button">Создать игру</button>
    </form>
    <c:if test="${not empty requestScope.message}">
        <div style="color: red"><c:out value="${requestScope.message}"/></div>
    </c:if>

    <c:if test="${not empty requestScope.notUniqueTitle}">
        <div style="color: red"><c:out value="${requestScope.notUniqueTitle}"/></div>
    </c:if>

</div>

<div class="games-button">
    <a href="${pageContext.request.contextPath}/games">Игры</a>
</div>

<c:url value="/user" var="userUrl">
    <c:param name="userId" value="${sessionScope.user.id}"/>
</c:url>
<a href="${userUrl}" class="profile-link">Перейти в личный кабинет</a>
</body>
</html>
