package exercise_2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Xác thực tài khoản minh họa và lưu trạng thái đăng nhập trong HTTP session.
 */
@SuppressWarnings("serial")
@WebServlet("/login")
public class LoginController extends HttpServlet {
    static final String AUTHENTICATED = "authenticated";
    private static final String DEMO_USERNAME = "admin";
    private static final String DEMO_PASSWORD = "123456";

    /** Hiển thị form hoặc chuyển thẳng tới trang chào nếu session đã đăng nhập. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && Boolean.TRUE.equals(session.getAttribute(AUTHENTICATED))) {
            resp.sendRedirect(req.getContextPath() + "/welcome");
            return;
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    /** Kiểm tra thông tin đăng nhập ở phía server và tạo session khi thành công. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String userName = req.getParameter("username");
        String password = req.getParameter("password");

        if (DEMO_USERNAME.equals(userName) && DEMO_PASSWORD.equals(password)) {
            req.getSession(true).setAttribute(AUTHENTICATED, Boolean.TRUE);
            resp.sendRedirect(req.getContextPath() + "/welcome");
            return;
        }

        req.setAttribute("loginError", "Tên đăng nhập hoặc mật khẩu không đúng.");
        req.setAttribute("savedUsername", userName);
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
