<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Games</title>
    <style>
        <%@include file="/WEB-INF/css/style.css" %>
    </style>
</head>
<body>

<h1>Добро пожаловать в GameShop!</h1><br/>
<h2>Все доступные игры</h2><br/>
<c:choose>
    <c:when test="${not empty requestScope.games}">
        <c:forEach var="game" items="${requestScope.games}">
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
<%--                            <input type="hidden" name="userId" value="${sessionScope.user.id}" />--%>
                            <input type="hidden" name="gameTitle" value="${game.title}" />
                            <input type="hidden" name="gamePrice" value="${game.price}" />
                            <button type="submit" class="buy-button" role="button">Купить</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <button class="buy-button">Нет в наличии</button
                    </c:otherwise>
                </c:choose>
            </div>
            <hr/>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p>Список игр пуст.</p>
    </c:otherwise>
</c:choose>
<div class="auth-button-container">
    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <!-- Если пользователь авторизован, показываем кнопку "Выйти" -->
            <p>Добро пожаловать, <c:out value="${sessionScope.user.login}"/></p>
            <c:url value="/user" var="userUrl">
                <c:param name="userId" value="${sessionScope.user.id}"/>
            </c:url>
            <a href="${userUrl}" class="profile-link">Перейти в личный кабинет</a>
            <form action="${pageContext.request.contextPath}/logout" method="get">
                <input type="submit" value="Выйти" class="auth-button"/>
            </form>
        </c:when>
        <c:otherwise>
            <!-- Если пользователь не авторизован, показываем кнопку "Авторизоваться" -->
            <form action="${pageContext.request.contextPath}/login" method="post">
                <input type="submit" value="Авторизоваться" class="auth-button"/>
            </form>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>

<%--<c:if test="${empty sessionScope.user}">--%>
<%--    <c:redirect url="${pageContext.request.contextPath}/login"/>--%>
<%--</c:if>--%>