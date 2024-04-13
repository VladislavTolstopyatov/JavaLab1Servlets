<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Покупка игры</title>
    <style>
        <%@include file="/WEB-INF/css/buyGame.css" %>
    </style>
</head>
<body>
<h1>Добро пожаловать в GameShop!</h1><br/>
<h1>Покупка игры <c:out value="${sessionScope.gameTitle}"/></h1><br/>
<div class="buy-game-form">
    <form action="${pageContext.request.contextPath}/buyGame" method="post">
        <div class="form-group">
            <label for="userBalance">Ваш баланс:</label>
            <input type="text" id="userBalance" name="userBalance" value="${sessionScope.user.balance}" readonly>
        </div>
        <div class="form-group">
            <label for="gamePrice">Стоимость игры:</label>
            <input type="text" id="gamePrice" name="gamePrice" value="${sessionScope.gamePrice}" readonly>
        </div>
        <div class="form-group">
            <button type="submit" class="buy-button">Купить</button>
        </div>
        <c:if test="${not empty requestScope.message}">
            <div style="color: red"><c:out value="${requestScope.message}"/></div>
        </c:if>
    </form>
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
