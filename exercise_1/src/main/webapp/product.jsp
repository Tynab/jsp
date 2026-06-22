<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Form nhập và bảng dữ liệu sản phẩm của session hiện tại. --%>
<c:url var="productUrl" value="/product"/>
<c:url var="productCssUrl" value="/css/product.css"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${productCssUrl}">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
          crossorigin="anonymous">
    <title>Quản lý sản phẩm</title>
</head>
<body>
    <main class="product-box">
        <h1>QUẢN LÝ SẢN PHẨM</h1>
        <form action="${productUrl}" method="post">
            <div class="user-box">
                <input id="nameproduct" type="text" name="nameproduct"
                       value="<c:out value="${savedProductName}"/>" required>
                <label for="nameproduct">Tên sản phẩm</label>
            </div>
            <div class="user-box">
                <input id="amount" type="number" name="amount" min="1" step="1"
                       value="<c:out value="${savedAmount}"/>" required>
                <label for="amount">Số lượng</label>
            </div>
            <div class="user-box">
                <input id="price" type="number" name="price" min="0" step="0.01"
                       value="<c:out value="${savedPrice}"/>" required>
                <label for="price">Giá bán</label>
            </div>
            <c:if test="${not empty productError}">
                <p class="form-error" role="alert"><c:out value="${productError}"/></p>
            </c:if>
            <button class="action-button" type="submit">Lưu lại</button>
        </form>

        <div class="scrollbar">
            <table class="table table-dark table-hover">
                <thead>
                    <tr>
                        <th scope="col">Số thứ tự</th>
                        <th scope="col">Tên sản phẩm</th>
                        <th scope="col">Số lượng</th>
                        <th scope="col">Giá bán</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${products}" varStatus="status">
                        <tr>
                            <td><c:out value="${status.count}"/></td>
                            <td><c:out value="${product.name}"/></td>
                            <td><c:out value="${product.amount}"/></td>
                            <td><c:out value="${product.price}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
