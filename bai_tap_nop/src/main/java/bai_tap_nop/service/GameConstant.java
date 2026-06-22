package bai_tap_nop.service;

/**
 * Tập trung các giới hạn và thông báo dùng chung của game đoán số.
 */
public final class GameConstant {
    public static final String SMALLER = "Số bạn vừa đoán nhỏ hơn đáp án!";
    public static final String BIGGER = "Số bạn vừa đoán lớn hơn đáp án!";
    public static final String CORRECT = "Bạn đã đoán chính xác đáp án!";
    public static final String DUPLICATE = "Số này đã được đoán, hãy chọn số khác!";
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 1000;
    public static final int MAX_PLAYER_NAME_LENGTH = 20;

    private GameConstant() {
        throw new AssertionError("Không khởi tạo lớp hằng số.");
    }
}
