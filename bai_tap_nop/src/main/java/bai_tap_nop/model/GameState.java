package bai_tap_nop.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static bai_tap_nop.service.GameConstant.MAX_NUMBER;
import static bai_tap_nop.service.GameConstant.MIN_NUMBER;

/**
 * Giữ trạng thái của riêng một ván chơi trong HTTP session.
 * Nhờ đó đáp án và số lượt đoán không bị dùng chung giữa nhiều người truy cập.
 */
public final class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Kết quả của một lần đoán hợp lệ. */
    public enum GuessResult {
        SMALLER,
        BIGGER,
        CORRECT,
        DUPLICATE
    }

    private final String playerName;
    private final int answer;
    private final Set<Integer> guessedNumbers = new HashSet<>();
    private int attemptCount;
    private boolean completed;

    /**
     * Khởi tạo ván mới với đáp án ngẫu nhiên trong đoạn đóng [1, 1000].
     */
    public GameState(String playerName) {
        this(playerName, ThreadLocalRandom.current().nextInt(MIN_NUMBER, MAX_NUMBER + 1));
    }

    /**
     * Khởi tạo ván với đáp án xác định, thuận tiện cho kiểm thử quy tắc trò chơi.
     */
    public GameState(String playerName, int answer) {
        if (playerName == null || playerName.isBlank()) {
            throw new IllegalArgumentException("Tên người chơi không được để trống.");
        }
        if (answer < MIN_NUMBER || answer > MAX_NUMBER) {
            throw new IllegalArgumentException("Đáp án nằm ngoài phạm vi cho phép.");
        }
        this.playerName = playerName;
        this.answer = answer;
    }

    /**
     * Ghi nhận một lượt đoán và trả về quan hệ giữa số đoán với đáp án.
     * Mọi lần gửi số hợp lệ đều được tính vào thành tích, kể cả số bị lặp.
     */
    public GuessResult guess(int number) {
        if (completed) {
            throw new IllegalStateException("Ván chơi đã kết thúc.");
        }
        if (number < MIN_NUMBER || number > MAX_NUMBER) {
            throw new IllegalArgumentException("Số dự đoán nằm ngoài phạm vi cho phép.");
        }

        attemptCount++;
        if (!guessedNumbers.add(number)) {
            return GuessResult.DUPLICATE;
        }
        if (number == answer) {
            completed = true;
            return GuessResult.CORRECT;
        }
        return number < answer ? GuessResult.SMALLER : GuessResult.BIGGER;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public boolean isCompleted() {
        return completed;
    }
}
