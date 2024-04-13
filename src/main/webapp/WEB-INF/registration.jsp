<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Регистрация нового пользователя</title>
    <style>
        <%@include file="/WEB-INF/css/registration.css" %>
    </style>
</head>
<body>
<h1>Добро пожаловать в GameShop!</h1><br/>
<h1>Регистрация</h1><br/>
<div class="form-container">
    <div>Введите данные:</div>
    <form action="${pageContext.request.contextPath}/registration" method="post">
        <label for="loginId">Логин</label>
        <input type="text" name="login" id="loginId" required><br>
        <label for="passwordId">Пароль</label>
        <input type="password" name="password" id="passwordId" required><br>
        <label for="cardNumberId">Номер карты</label>
        <input type="text" name="cardNumber" id="cardNumberId" required><br>
        <c:if test="${not empty requestScope.message}">
            <div style="color: red"><c:out value="${requestScope.message}"/></div>
        </c:if>
        <button type="submit">Регистрация</button>
    </form>
</div>
</body>
</html>
