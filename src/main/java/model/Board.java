package model;

import java.awt.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createTime;
    private List<Comment> commentList = new ArrayList<>();

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

    public void changeEnterTag() {
        this.contents = contents.replace("\r\n", "<br>");
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public long getId() {
        return id;
    }

    public String getFormattedCreateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return this.createTime.format(formatter);
    }

    public void setBoardId(long id) {
        this.id = id;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }
}
