<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Trang đăng nhập cho tài khoản minh họa của bài tập. --%>
<c:url var="loginUrl" value="/login"/>
<c:url var="loginCssUrl" value="/css/login.css"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${loginCssUrl}">
    <title>Đăng nhập</title>
</head>
<body>
    <main class="login-box">
        <h1>Đăng nhập</h1>
        <form action="${loginUrl}" method="post" class="form">
            <div class="user-box">
                <input id="username" type="text" name="username"
                       value="<c:out value="${savedUsername}"/>" autocomplete="username" required>
                <label for="username">Tên đăng nhập</label>
            </div>
            <div class="user-box">
                <input id="password" type="password" name="password"
                       autocomplete="current-password" required>
                <label for="password">Mật khẩu</label>
            </div>
            <c:if test="${not empty loginError}">
                <p class="form-error" role="alert"><c:out value="${loginError}"/></p>
            </c:if>
            <button class="action-button" type="submit">Đăng nhập</button>
        </form>
    </main>
</body>
</html>
