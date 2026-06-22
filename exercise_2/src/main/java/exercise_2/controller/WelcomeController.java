package exercise_2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Bảo vệ trang chào bằng trạng thái đăng nhập và xử lý thao tác đăng xuất.
 */
@SuppressWarnings("serial")
@WebServlet("/welcome")
public class WelcomeController extends HttpServlet {

    /** Chỉ hiển thị trang chào cho session đã được xác thực. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !Boolean.TRUE.equals(session.getAttribute(LoginController.AUTHENTICATED))) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.getRequestDispatcher("/welcome.jsp").forward(req, resp);
    }

    /** Hủy session hiện tại rồi quay về màn hình đăng nhập. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
