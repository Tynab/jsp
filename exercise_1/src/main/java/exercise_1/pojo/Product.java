package exercise_1.pojo;

import java.io.Serializable;

/**
 * Mô tả một sản phẩm gồm tên, số lượng và giá bán.
 */
public final class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final int amount;
    private final double price;

    /** Tạo sản phẩm sau khi dữ liệu đầu vào đã được kiểm tra. */
    public Product(String name, int amount, double price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tên sản phẩm không được để trống.");
        }
        if (amount <= 0 || price < 0) {
            throw new IllegalArgumentException("Số lượng hoặc giá bán không hợp lệ.");
        }
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }
}
