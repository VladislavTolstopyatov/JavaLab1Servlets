<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Все ключи</title>
    <style>
        <%@include file="/WEB-INF/css/getAllKeys.css" %>
    </style>
</head>
<body>
<h1>Ключи игры ${requestScope.title}</h1>
<div class="game-keys-container">
    <c:choose>
        <c:when test="${not empty requestScope.keys}">
            <c:forEach var="key" items="${requestScope.keys}">
                <ul>
                    <li>
                        <p>Ключ: <c:out value="${key.keyStr}"/></p>
                        <form action="${pageContext.request.contextPath}/deleteKey" method="get">
                            <input type="hidden" name="Id" value="${key.id}">
                            <input type="hidden" name="title" value="${requestScope.title}">
                            <button type="submit" class="delete-button">Удалить</button>
                        </form>
                    </li>
                </ul>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <h1>Ключей нет</h1><br>
        </c:otherwise>
    </c:choose>
</div>

<div class="create-key-button-container">
    <form action="${pageContext.request.contextPath}/createKey" method="get">
        <input type="hidden" name="title" value="${requestScope.title}">
        <button type="submit" class="create-key-button">Создать ключ</button>
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
