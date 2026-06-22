package bai_tap_nop.model;

import java.io.Serializable;

/**
 * Lưu kết quả đã hoàn thành của một người chơi để hiển thị trên bảng xếp hạng.
 * Đối tượng bất biến giúp dữ liệu không bị thay đổi sau khi đã ghi nhận.
 */
public final class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String playerName;
    private final int counter;

    /**
     * Tạo kết quả của người chơi.
     *
     * @param playerName tên đã được chuẩn hóa
     * @param counter số lượt đoán, phải lớn hơn 0
     */
    public Player(String playerName, int counter) {
        if (playerName == null || playerName.isBlank()) {
            throw new IllegalArgumentException("Tên người chơi không được để trống.");
        }
        if (counter <= 0) {
            throw new IllegalArgumentException("Số lượt đoán phải lớn hơn 0.");
        }
        this.playerName = playerName;
        this.counter = counter;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getCounter() {
        return counter;
    }
}
