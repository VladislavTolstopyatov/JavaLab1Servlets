<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создание ключа</title>
    <style>
        <%@include file="/WEB-INF/css/createKey.css" %>
    </style>
</head>
<body>
<h1>Создание ключа для игры</h1><br>
<h1>${requestScope.title}</h1><br>

<form action="${pageContext.request.contextPath}/createKey" method="post" class="key-form">
    <label for="keyStr">Ключ:</label>
    <input type="text" id="keyStr" name="keyStr" required>
    <input type="hidden" name="title" value="${requestScope.title}">
    <button type="submit">Создать ключ</button>
</form>


<div class="games-button">
    <a href="${pageContext.request.contextPath}/games">Игры</a>
</div>

<c:url value="/user" var="userUrl">
    <c:param name="userId" value="${sessionScope.user.id}"/>
</c:url>
<a href="${userUrl}" class="profile-link">Перейти в личный кабинет</a>

</body>
</html>
