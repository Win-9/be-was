package service;

import db.Database;
import model.Board;
import model.Comment;
import model.User;
import util.ParseParams;

import java.util.Map;

public class CommentService {
    public void createComment(User user, Board board, ParseParams bodyData) {
        Comment comment = new Comment(user.getName(), bodyData.getParamMap().get("comment"));
        Database.addComment(comment);
        board.addComment(comment);
    }
}
