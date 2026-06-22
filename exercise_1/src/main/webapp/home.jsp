<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Trang mở đầu của bài quản lý sản phẩm. --%>
<c:url var="homeUrl" value="/home"/>
<c:url var="homeCssUrl" value="/css/home.css"/>
<c:url var="backgroundVideoUrl" value="/video/background.mp4"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${homeCssUrl}">
    <title>Trang chủ</title>
</head>
<body>
    <main class="main">
        <video autoplay muted loop playsinline id="video" aria-hidden="true">
            <source src="${backgroundVideoUrl}" type="video/mp4">
        </video>
        <div class="overlay"></div>
        <div class="heading">
            <h1 class="head">CHÀO MỪNG ĐẾN <span>TRANG QUẢN LÝ</span></h1>
            <p class="sub">Theo dõi sản phẩm ngay trên trình duyệt</p>
            <form action="${homeUrl}" method="post">
                <div class="btns">
                    <button type="submit">Bắt đầu</button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>
