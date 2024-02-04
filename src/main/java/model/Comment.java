package model;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment {
    private long id;
    private String writer;
    private String contents;
    private LocalDateTime createTime;

    public Comment(String writer, String contents) {
        this.writer = writer;
        this.contents = contents;
        this.createTime = LocalDateTime.now();
    }

    public String getFormattedCreateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return this.createTime.format(formatter);
    }

    public void setContentId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }
}
