package bai_tap_nop.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import bai_tap_nop.model.GameState.GuessResult;
import bai_tap_nop.model.Player;

import static bai_tap_nop.service.GameConstant.BIGGER;
import static bai_tap_nop.service.GameConstant.CORRECT;
import static bai_tap_nop.service.GameConstant.DUPLICATE;
import static bai_tap_nop.service.GameConstant.MAX_PLAYER_NAME_LENGTH;
import static bai_tap_nop.service.GameConstant.SMALLER;

/**
 * Cung cấp nghiệp vụ dùng chung của game: chuẩn hóa tên, diễn giải kết quả và
 * quản lý bảng xếp hạng toàn ứng dụng theo cách an toàn khi có nhiều request.
 */
public final class GameMethod {
    private static final List<Player> PLAYERS = new ArrayList<>();
    private static final Comparator<Player> RANKING_ORDER = Comparator
            .comparingInt(Player::getCounter)
            .thenComparing(Player::getPlayerName, String.CASE_INSENSITIVE_ORDER);

    private GameMethod() {
        throw new AssertionError("Không khởi tạo lớp tiện ích.");
    }

    /** Chuẩn hóa khoảng trắng trong tên trước khi kiểm tra hoặc lưu trữ. */
    public static String normalizePlayerName(String name) {
        return name == null ? "" : name.trim().replaceAll("\\s+", " ");
    }

    /** Kiểm tra tên có nội dung và không vượt quá giới hạn giao diện. */
    public static boolean isValidPlayerName(String name) {
        return name != null && !name.isBlank() && name.length() <= MAX_PLAYER_NAME_LENGTH;
    }

    /** Kiểm tra tên đã có trên bảng xếp hạng, không phân biệt hoa thường. */
    public static synchronized boolean playerExists(String name) {
        return PLAYERS.stream().anyMatch(player -> player.getPlayerName().equalsIgnoreCase(name));
    }

    /** Ghi thành tích mới và thay thế thành tích cũ của cùng người chơi nếu có. */
    public static synchronized void saveScore(String name, int attemptCount) {
        Player updatedScore = new Player(name, attemptCount);
        PLAYERS.removeIf(player -> player.getPlayerName().equalsIgnoreCase(name));
        PLAYERS.add(updatedScore);
        PLAYERS.sort(RANKING_ORDER);
    }

    /** Trả về snapshot bất biến để JSP không quan sát danh sách đang bị thay đổi. */
    public static synchronized List<Player> getPlayersRanking() {
        return List.copyOf(PLAYERS);
    }

    /** Chuyển kết quả nghiệp vụ thành thông báo tiếng Việt cho giao diện. */
    public static String answerMessage(GuessResult result) {
        return switch (result) {
            case SMALLER -> SMALLER;
            case BIGGER -> BIGGER;
            case CORRECT -> CORRECT;
            case DUPLICATE -> DUPLICATE;
        };
    }
}
