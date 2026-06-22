package bai_tap_nop.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bai_tap_nop.model.GameState;
import bai_tap_nop.service.GameMethod;

/**
 * Điều khiển trang đăng ký người chơi và hiển thị bảng xếp hạng.
 */
@SuppressWarnings("serial")
@WebServlet("/index")
public class IndexController extends HttpServlet {
    private static final String PENDING_PLAYER_NAME = "pendingPlayerName";
    static final String GAME_STATE = "gameState";

    /** Hiển thị form đăng ký cùng snapshot mới nhất của bảng xếp hạng. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        renderIndex(req, resp);
    }

    /** Kiểm tra tên hoặc bắt đầu ván mới theo nút được gửi từ form. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("submit");

        if ("check".equals(action)) {
            checkPlayerName(req, resp);
            return;
        }
        if ("go".equals(action) || "verify".equals(action)) {
            startGame(req, resp);
            return;
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thao tác không hợp lệ.");
    }

    private void checkPlayerName(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String playerName = GameMethod.normalizePlayerName(req.getParameter("playerName"));
        req.setAttribute("savedName", playerName);

        if (!GameMethod.isValidPlayerName(playerName)) {
            req.setAttribute("nameError", "Tên người chơi phải có từ 1 đến 20 ký tự.");
            renderIndex(req, resp);
            return;
        }

        req.getSession(true).setAttribute(PENDING_PLAYER_NAME, playerName);
        req.setAttribute("playerState", GameMethod.playerExists(playerName) ? "REGISTERED" : "UNREGISTERED");
        renderIndex(req, resp);
    }

    private void startGame(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        String playerName = session == null ? null : (String) session.getAttribute(PENDING_PLAYER_NAME);
        if (!GameMethod.isValidPlayerName(GameMethod.normalizePlayerName(playerName))) {
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }

        session.setAttribute(GAME_STATE, new GameState(playerName));
        session.removeAttribute(PENDING_PLAYER_NAME);
        resp.sendRedirect(req.getContextPath() + "/game");
    }

    private void renderIndex(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("players", GameMethod.getPlayersRanking());
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
