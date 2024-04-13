<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Кабинет пользователя</title>
    <style>
        <%@include file="/WEB-INF/css/userCabinet.css" %>
    </style>
</head>
<body>
<div class="user-profile">
    <div class="profile-info">
        <p>Личные данные</p>
        <p>Логин: <span>${sessionScope.user.login}</span></p>
        <p>Номер карты: <span>${sessionScope.user.cardNumber}</span></p>
        <p>Денежный баланс: <span>${sessionScope.user.balance}</span></p>
    </div>
    <div class="purchase-history">
        <h2>История покупок</h2>
        <c:choose>
            <c:when test="${not empty requestScope.userPurchases}">
                <c:forEach var="purchase" items="${requestScope.userPurchases}">
                    <ul>
                        <li>
                            <p>Дата покупки: <c:out value="${purchase.dateOfPurchase}"/></p>
                            <p>Игра: <c:out value="${purchase.gameTitle}"/></p>
                            <p>Ключ активации: <c:out value="${purchase.keyStr}"/></p>
                            <p>Цена игры: <c:out value="${sessionScope.gamePrice}"/></p>
                            <p class="thank-you">Спасибо за покупку, приятной игры!</p>
                        </li>
                    </ul>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>История покупок отсутствует.</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<div class="games-button">
    <a href="${pageContext.request.contextPath}/games">Игры</a>
</div>
</body>
</html>