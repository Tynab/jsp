package exercise_1.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Hiển thị trang giới thiệu và chuyển người dùng sang màn hình quản lý sản phẩm.
 */
@SuppressWarnings("serial")
@WebServlet("/home")
public class HomeController extends HttpServlet {

    /** Chuyển request đến giao diện trang chủ. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }

    /** Điều hướng sang servlet sản phẩm khi người dùng chọn tiếp tục. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/product");
    }
}
