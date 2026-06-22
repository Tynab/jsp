<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Trang chơi: nhận số dự đoán, hiển thị gợi ý và thông báo kết quả. --%>
<c:url var="gameUrl" value="/game"/>
<c:url var="gameCssUrl" value="/css/game.css"/>
<c:url var="gameImageUrl" value="/img/animation-run.gif"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${gameCssUrl}">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
          crossorigin="anonymous">
    <title>Game Đoán Số</title>
</head>
<body>
    <main class="vertical-center">
        <div class="container game-container">
            <img src="${gameImageUrl}" alt="Nhân vật đang suy nghĩ về đáp án">
            <h1>
                Tôi đang nghĩ tới một số từ 1 đến 1000.<br>Bạn có thể đoán được không?
            </h1>
            <form action="${gameUrl}" method="post">
                <label class="visually-hidden" for="numGuess">Số dự đoán</label>
                <input id="numGuess" name="numGuess" value="<c:out value="${savedNum}"/>"
                       class="form-control" type="number" placeholder="Số"
                       min="1" max="1000" required autofocus>
                <c:if test="${not empty inputError}">
                    <p class="form-error" role="alert"><c:out value="${inputError}"/></p>
                </c:if>
                <div class="btn-set">
                    <button type="submit" name="submit" value="checkin">Dự đoán</button>
                </div>

                <div class="modal fade" id="congratulationDialog" data-bs-backdrop="static"
                     data-bs-keyboard="false" tabindex="-1"
                     aria-labelledby="congratulationTitle" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2 class="modal-title fs-5" id="congratulationTitle">Thông báo</h2>
                            </div>
                            <div class="modal-body">Bạn thật xuất sắc!</div>
                            <div class="modal-footer">
                                <button name="submit" value="checkout" type="submit" class="btn btn-primary">
                                    Xem bảng xếp hạng
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <p class="answer" aria-live="polite"><c:out value="${botRep}"/></p>
        </div>
    </main>

    <div class="modal fade" id="duplicateDialog" data-bs-backdrop="static"
         data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="duplicateTitle" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="modal-title fs-5" id="duplicateTitle">Cảnh báo</h2>
                </div>
                <div class="modal-body">Số này đã được đoán, hãy nhập một số khác!</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
            crossorigin="anonymous"></script>
    <c:if test="${gameState == 'CORRECT' || gameState == 'DUPLICATE'}">
        <script>
            // Chỉ hai trạng thái này cần hộp thoại; SMALLER/BIGGER dùng dòng gợi ý trực tiếp.
            const gameDialogId = "${gameState}" === "CORRECT" ? "congratulationDialog" : "duplicateDialog";
            bootstrap.Modal.getOrCreateInstance(document.getElementById(gameDialogId)).show();
        </script>
    </c:if>
</body>
</html>
