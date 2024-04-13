<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Аутентификация</title>
    <style>
        <%@include file="/WEB-INF/css/loginStyle.css" %>
    </style>
</head>
<body>
<h1>Добро пожаловать в GameShop!</h1><br/>
<div class="login-form-container">
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label>
            Логин
            <input type="text" name="login">
        </label>
        <br>
        <label>
            Пороль
            <input type="password" name="password">
        </label>
        <br>
        <button type="submit">Авторизоваться</button>
        <c:if test="${not empty requestScope.message}">
            <div style="color: red"><c:out value="${requestScope.message}"/></div>
        </c:if>
    </form>
</div>
    <form action="${pageContext.request.contextPath}/registration" method="get">
        <button type="submit" class="register-btn">Регистрация</button>
    </form>
</body>
</html>