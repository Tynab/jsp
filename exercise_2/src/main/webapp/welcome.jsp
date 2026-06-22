<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Trang chào chỉ hiển thị sau khi servlet xác nhận session đã đăng nhập. --%>
<c:url var="welcomeUrl" value="/welcome"/>
<c:url var="welcomeCssUrl" value="/css/welcome.css"/>
<c:url var="welcomeVideoUrl" value="/video/background.mp4"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${welcomeCssUrl}">
    <title>Chào mừng</title>
</head>
<body>
    <main class="main">
        <video autoplay muted loop playsinline id="video" aria-hidden="true">
            <source src="${welcomeVideoUrl}" type="video/mp4">
        </video>
        <div class="overlay"></div>
        <div class="heading">
            <h1 class="head">CHÀO MỪNG ĐẾN <span>TRANG WEB</span></h1>
            <p class="sub">Bạn đã đăng nhập thành công</p>
            <form action="${welcomeUrl}" method="post">
                <div class="btns">
                    <button type="submit">Đăng xuất</button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>
