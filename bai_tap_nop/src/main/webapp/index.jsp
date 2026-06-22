<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Trang đăng ký người chơi và bảng xếp hạng của game đoán số. --%>
<c:url var="indexUrl" value="/index"/>
<c:url var="indexCssUrl" value="/css/index.css"/>
<c:url var="loginImageUrl" value="/img/animation-log.gif"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${indexCssUrl}">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
          crossorigin="anonymous">
    <title>Game Đoán Số</title>
</head>
<body>
    <main class="vertical-center">
        <div class="container game-container">
            <img src="${loginImageUrl}" alt="Nhân vật đang đăng nhập vào game">
            <h1 data-text="*Game*Đoán*Số*">*Game*Đoán*Số*</h1>
            <p>(<span>Nhập tên người chơi để bắt đầu trò chơi</span>)</p>

            <form action="${indexUrl}" method="post">
                <label class="visually-hidden" for="playerName">Tên người chơi</label>
                <input id="playerName" name="playerName"
                       value="<c:out value="${savedName}"/>"
                       class="form-control" placeholder="Tên người chơi"
                       maxlength="20" required autofocus>
                <c:if test="${not empty nameError}">
                    <p class="form-error" role="alert"><c:out value="${nameError}"/></p>
                </c:if>
                <div class="btn-start">
                    <button type="submit" name="submit" value="check">Bắt đầu</button>
                </div>

                <div class="modal fade" id="registrationSuccess" data-bs-backdrop="static"
                     data-bs-keyboard="false" tabindex="-1"
                     aria-labelledby="registrationSuccessTitle" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2 class="modal-title fs-5" id="registrationSuccessTitle">Thông báo</h2>
                            </div>
                            <div class="modal-body">Đăng ký thành công!</div>
                            <div class="modal-footer">
                                <button name="submit" value="go" type="submit" class="btn btn-primary">
                                    Tiếp theo
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="replacePlayer" data-bs-backdrop="static"
                     data-bs-keyboard="false" tabindex="-1"
                     aria-labelledby="replacePlayerTitle" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2 class="modal-title fs-5" id="replacePlayerTitle">Xác nhận</h2>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Đóng"></button>
                            </div>
                            <div class="modal-body">
                                Người chơi đã có thành tích. Bạn có muốn chơi lại để thay thế kết quả cũ?
                            </div>
                            <div class="modal-footer">
                                <button name="submit" value="verify" type="submit" class="btn btn-primary">
                                    Đồng ý
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

            <section class="board" aria-labelledby="rankingTitle">
                <h2 id="rankingTitle">Bảng xếp hạng</h2>
                <div class="scrollbar">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Hạng</th>
                                <th scope="col">Tên người chơi</th>
                                <th scope="col">Số lần đoán</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="player" items="${players}" varStatus="status">
                                <tr>
                                    <td><c:out value="${status.count}"/></td>
                                    <td><c:out value="${player.playerName}"/></td>
                                    <td><c:out value="${player.counter}"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
            crossorigin="anonymous"></script>
    <c:if test="${not empty playerState}">
        <script>
            // Trạng thái do controller cấp quyết định hộp thoại xác nhận cần mở.
            const playerDialogId = "${playerState}" === "REGISTERED" ? "replacePlayer" : "registrationSuccess";
            bootstrap.Modal.getOrCreateInstance(document.getElementById(playerDialogId)).show();
        </script>
    </c:if>
</body>
</html>
