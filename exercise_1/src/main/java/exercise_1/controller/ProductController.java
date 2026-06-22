package exercise_1.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exercise_1.pojo.Product;

/**
 * Tiếp nhận sản phẩm và giữ danh sách theo từng HTTP session, tránh để dữ liệu
 * của người dùng này xuất hiện trong phiên làm việc của người dùng khác.
 */
@SuppressWarnings("serial")
@WebServlet("/product")
public class ProductController extends HttpServlet {
    private static final String PRODUCT_LIST = "productList";

    /** Hiển thị danh sách sản phẩm hiện có trong session. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        renderProducts(req, resp);
    }

    /** Kiểm tra dữ liệu form, thêm sản phẩm hợp lệ rồi dùng redirect để tránh gửi lặp. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = normalize(req.getParameter("nameproduct"));
        String rawAmount = req.getParameter("amount");
        String rawPrice = req.getParameter("price");

        req.setAttribute("savedProductName", name);
        req.setAttribute("savedAmount", rawAmount);
        req.setAttribute("savedPrice", rawPrice);

        try {
            int amount = Integer.parseInt(rawAmount);
            double price = Double.parseDouble(rawPrice);
            Product product = new Product(name, amount, price);
            getProducts(req.getSession(true)).add(product);
            resp.sendRedirect(req.getContextPath() + "/product");
        } catch (IllegalArgumentException exception) {
            req.setAttribute("productError", "Vui lòng nhập tên, số lượng dương và giá bán không âm.");
            renderProducts(req, resp);
        }
    }

    private void renderProducts(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("products", List.copyOf(getProducts(req.getSession(true))));
        req.getRequestDispatcher("/product.jsp").forward(req, resp);
    }

    @SuppressWarnings("unchecked")
    private List<Product> getProducts(HttpSession session) {
        Object storedProducts = session.getAttribute(PRODUCT_LIST);
        if (storedProducts instanceof List<?>) {
            return (List<Product>) storedProducts;
        }

        List<Product> products = new ArrayList<>();
        session.setAttribute(PRODUCT_LIST, products);
        return products;
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().replaceAll("\\s+", " ");
    }
}
