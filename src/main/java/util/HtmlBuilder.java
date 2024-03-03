package util;

import content.BoardContent;
import content.UserContent;
import dto.ResourceDto;
import model.Board;
import model.Comment;
import model.Model;
import model.User;

import java.util.Collections;
import java.util.List;

public class HtmlBuilder {

    public static String changeProfileHtmlFile(ResourceDto resource, String bodyString) {
        // 프로필 변환
        if (resource.getPath().contains("/user/profile")) {
            User user = (User) Model.getAttribute("user").get();
            bodyString = bodyString.replace("{{profileData}}",
                    UserContent.USER_PROFILE.getText(user.getName(), user.getEmail()));
        }
        return bodyString;
    }

    public static String changeShowHtmlFile(ResourceDto resource, String bodyString) {
        Board board = null;
        if (resource.getPath().contains("/qna/show")) {
            board = (Board) Model.getAttribute("board").get();
            bodyString = bodyString.replace("{{boardDetail}}",
                    BoardContent.BOARD_DEFAIL.getText(board.getTitle(),board.getWriter(),
                            board.getFormattedCreateTime(), board.getContents()));
        }

        if (resource.getPath().contains("/qna/show")) {
            List<Comment> commentList = (List<Comment>) Model.getAttribute("commentList")
                    .orElse(Collections.emptyList());

            // 댓글 갯수 동적 변환
            bodyString = bodyString.replace("{{commentSize}}",
                    BoardContent.BOARD_COUNT.getText(commentList.size()));

            // 댓글 동적 변환
            if (commentList.size() == 0) {
                bodyString = bodyString.replace("{{commentData}}","");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Comment comment : commentList) {
                    sb.append(BoardContent.BOARD_COMMENT.getText(comment.getWriter(), comment.getFormattedCreateTime(),
                            comment.getContents()));
                }
                bodyString = bodyString.replace("{{commentData}}", sb);
            }

            // 댓글 쓰기
            bodyString = bodyString.replace("{{form}}",BoardContent.COMMENT_FORM.getText(board.getId()));

        }
        return bodyString;
    }

    public static String changeIndexHtmlFile(ResourceDto resource, String bodyString) {
        // 게시글 리스트 변환
        if (resource.getPath().contains("/index.html")) {
            List<Board> boardList = (List<Board>) Model.getAttribute("boardList")
                    .orElse(Collections.emptyList());

            if (boardList.size() == 0) {
                bodyString = bodyString.replace("{{boardData}}","");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Board board : boardList) {
                    sb.append(BoardContent.BOARD.getText(board.getId(), board.getTitle()
                            , board.getFormattedCreateTime(), board.getWriter(), board.getId()));
                }
                bodyString = bodyString.replace("{{boardData}}", sb);
            }
        }
        return bodyString;
    }

    public static String changeUserListHtmlFile(ResourceDto resource, String bodyString) {
        // 사용자 리스트 변환
        if (resource.getPath().contains("/user/list")) {
            List<User> userList = (List<User>) Model.getAttribute("userList").get();

            for (int i = 3; i < userList.size() + 3; i++) {
                User user = userList.get(i - 3);
                bodyString = bodyString.replace("{{data}}",
                        UserContent.USER_LIST.getText(i, user.getUserId(), user.getName(), user.getEmail()));
            }
        }
        return bodyString;
    }

    public static String changeMenuHtmlFile(ResourceDto resource, byte[] bodyData) {
        // 메뉴 바 변환
        String bodyString = new String(bodyData);
        if (resource.isLoggined()) {
            bodyString = bodyString.replace("{{menu}}",
                    UserContent.LOGIN.getText(String.valueOf(Model.getAttribute("username").get())));
        } else {
            bodyString = bodyString.replace("{{menu}}",
                    UserContent.NON_LOGIN.getText());
        }
        return bodyString;
    }

}
