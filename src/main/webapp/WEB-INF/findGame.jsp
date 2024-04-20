<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Поиск игры</title>
    <style>
        <%@include file="/WEB-INF/css/findGame.css" %>
    </style>
</head>
<body>
<h1>Добро пожаловать в GameShop!</h1><br/>
<h1>Поиск игры по названию</h1><br/>
<div class="search-container">
    <form action="${pageContext.request.contextPath}/findGame" method="post">
        <input type="text" placeholder="Введите название игры" name="gameName">
        <button type="submit">Поиск</button>
    </form>
    <c:if test="${not empty requestScope.message}">
        <div style="color: red"><c:out value="${requestScope.message}"/></div>
    </c:if>

    <c:if test="${not empty requestScope.game}">
        <div class="game-item">
            <ul>
                <li>Название: <c:out value="${game.title}"/></li>
                <li>Описание: <c:out value="${game.description}"/></li>
                <li>Цена: <c:out value="${game.price}"/> рублей</li>
                <li>Дата релиза: <c:out value="${game.dateOfRelease}"/></li>
                <li>Осталось ключей: <c:out value="${game.keysCount}"/></li>
            </ul>
            <c:choose>
                <c:when test="${fn:length(game.keys) > 0}">
                    <form action="${pageContext.request.contextPath}/buyGame" method="get">
                        <input type="hidden" name="gameTitle" value="${game.title}"/>
                        <input type="hidden" name="gamePrice" value="${game.price}"/>
                        <button type="submit" class="buy-button" role="button">Купить</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <button class="buy-button">Нет в наличии</button
                </c:otherwise>
            </c:choose>
        </div>
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <form action="${pageContext.request.contextPath}/getAllKeys" method="get">
                <button type="submit" class="getAll-button" name="title" value="${game.title}">Список
                    ключей
                </button>
            </form>
            <form action="${pageContext.request.contextPath}/updateGame" method="get">
                <button type="submit" class="change-button" name="title" value="${game.title}">Изменить</button>
            </form>
            <form action="${pageContext.request.contextPath}/deleteGame" method="get">
                <button type="submit" class="delete-button" name="title" value="${game.title}">Удалить</button>
            </form>
        </c:if>
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