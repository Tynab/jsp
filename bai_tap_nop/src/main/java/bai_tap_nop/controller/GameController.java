package bai_tap_nop.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bai_tap_nop.model.GameState;
import bai_tap_nop.model.GameState.GuessResult;
import bai_tap_nop.service.GameMethod;

import static bai_tap_nop.service.GameConstant.MAX_NUMBER;
import static bai_tap_nop.service.GameConstant.MIN_NUMBER;

/**
 * Điều khiển một ván đoán số. Mỗi HTTP session sở hữu một {@link GameState}
 * riêng nên các người chơi đồng thời không thể làm thay đổi đáp án của nhau.
 */
@SuppressWarnings("serial")
@WebServlet("/game")
public class GameController extends HttpServlet {

    /** Chỉ cho phép mở trang khi người dùng đã đăng ký và có ván đang chạy. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameState gameState = getGameState(req);
        if (gameState == null) {
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }
        if (gameState.isCompleted()) {
            req.setAttribute("gameState", GuessResult.CORRECT.name());
            req.setAttribute("botRep", GameMethod.answerMessage(GuessResult.CORRECT));
        }
        req.getRequestDispatcher("/game.jsp").forward(req, resp);
    }

    /** Xử lý một lần đoán hoặc ghi thành tích sau khi ván chơi kết thúc. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("submit");

        if ("checkin".equals(action)) {
            checkGuess(req, resp);
            return;
        }
        if ("checkout".equals(action)) {
            finishGame(req, resp);
            return;
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thao tác không hợp lệ.");
    }

    private void checkGuess(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        GameState gameState = getGameState(req);
        if (gameState == null) {
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }
        if (gameState.isCompleted()) {
            req.setAttribute("gameState", GuessResult.CORRECT.name());
            req.setAttribute("botRep", GameMethod.answerMessage(GuessResult.CORRECT));
            req.getRequestDispatcher("/game.jsp").forward(req, resp);
            return;
        }

        String rawGuess = req.getParameter("numGuess");
        try {
            int guess = Integer.parseInt(rawGuess);
            if (guess < MIN_NUMBER || guess > MAX_NUMBER) {
                throw new NumberFormatException("Ngoài phạm vi");
            }

            GuessResult result = gameState.guess(guess);
            req.setAttribute("savedNum", guess);
            req.setAttribute("botRep", GameMethod.answerMessage(result));
            req.setAttribute("gameState", result.name());
        } catch (NumberFormatException exception) {
            req.setAttribute("inputError", "Vui lòng nhập số nguyên từ 1 đến 1000.");
        }
        req.getRequestDispatcher("/game.jsp").forward(req, resp);
    }

    private void finishGame(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        GameState gameState = getGameState(req);
        if (session == null || gameState == null || !gameState.isCompleted()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ván chơi chưa hoàn thành.");
            return;
        }

        GameMethod.saveScore(gameState.getPlayerName(), gameState.getAttemptCount());
        session.removeAttribute(IndexController.GAME_STATE);
        resp.sendRedirect(req.getContextPath() + "/index");
    }

    private GameState getGameState(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        Object state = session == null ? null : session.getAttribute(IndexController.GAME_STATE);
        return state instanceof GameState ? (GameState) state : null;
    }
}
