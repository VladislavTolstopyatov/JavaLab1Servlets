<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменение игры</title>
    <style>
        <%@include file="/WEB-INF/css/updateGame.css" %>
    </style>
</head>
<body>
<h1>Добро пожаловать в GameShop!</h1><br/>
<h1>Изменение игры <c:out value="${requestScope.title}"/></h1><br/>
<div class="update-game-form">
    <form action="${pageContext.request.contextPath}/updateGame" method="post">
        <input type="hidden" name="title" value="${requestScope.title}"/>
        <label for="currentPrice">Текущая цена:</label>
        <input type="text" id="currentPrice" name="currentPrice" value="${requestScope.gameDto.price}" readonly>
        <label for="newPrice">Новая цена:</label>
        <input type="number" id="newPrice" name="newPrice" min="0" step="0.01" required>
        <button type="submit" class="submit-button">Подтвердить изменение</button>
    </form>
    <c:if test="${not empty requestScope.message}">
        <div style="color: red"><c:out value="${requestScope.message}"/></div>
    </c:if>

    <div class="games-button">
        <a href="${pageContext.request.contextPath}/games">Игры</a>
    </div>

    <c:url value="/user" var="userUrl">
        <c:param name="userId" value="${sessionScope.user.id}"/>
    </c:url>
    <a href="${userUrl}" class="profile-link">Перейти в личный кабинет</a>
</div>
</body>
</html>
