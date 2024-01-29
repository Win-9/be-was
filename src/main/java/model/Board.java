package model;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class Board {
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createTime;

    public Board() {
        this.createTime = LocalDateTime.now();
    }

    public static void setBoard(Board board, String key, String value) {
        Class<Board> userClazz = Board.class;
        try {
            Field declaredField = userClazz.getDeclaredField(key);
            declaredField.setAccessible(true);
            declaredField.set(board, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
