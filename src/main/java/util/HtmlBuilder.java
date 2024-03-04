package util;

import content.BoardContent;
import content.UserContent;
import dto.ResourceDto;
import model.Board;
import model.Comment;
import model.Model;
import model.User;

import java.util.*;

public class HtmlBuilder {

    public static String changeProfileHtmlFile(ResourceDto resource, String bodyString) {
        // 프로필 변환
        if (resource.getPath().contains("/user/profile")) {
            User user = (User) Model.getAttribute("user").get();
            int start = bodyString.indexOf("<!--postStart-->");
            int end = bodyString.indexOf("<!--postEnd-->");
            String itemTemplate = bodyString.substring(start, end);

            String listItem = itemTemplate.replace("{{userName}}", user.getName())
                    .replace("{{userEmail}}", user.getEmail());
            listItem = replaceHeadline(listItem, "postStart");

            bodyString = bodyString.substring(0, start) + listItem + bodyString.substring(end);
        }
        return bodyString;
    }

    private static String replaceHeadline(String listItem, String headLine) {
        listItem = removeAnnotationSymbols(listItem);
        listItem = listItem.replace(headLine, "");
        return listItem;
    }

    public static String changeShowHtmlFile(ResourceDto resource, String bodyString) {
        Board board = null;
        if (resource.getPath().contains("/qna/show")) {
            // 게시글 세부사항 동적 변환
            board = (Board) Model.getAttribute("board").get();
            int start = bodyString.indexOf("<!--postStart-->");
            int end = bodyString.indexOf("<!--postEnd-->");
            String itemTemplate = bodyString.substring(start, end);

            String listItem = itemTemplate.replace("{{title}}", board.getTitle())
                    .replace("{{author}}", board.getWriter())
                    .replace("{{writeTime}}", board.getFormattedCreateTime())
                    .replace("{{content}}", board.getContents());
            listItem = replaceHeadline(listItem, "postStart");

            bodyString = bodyString.substring(0, start) + listItem + bodyString.substring(end);
        }

        if (resource.getPath().contains("/qna/show")) {
            List<Comment> commentList = (List<Comment>) Model.getAttribute("commentList")
                    .orElse(Collections.emptyList());

            // 댓글 갯수 동적 변환
            bodyString = bodyString.replace("{{commentSize}}",
                    BoardContent.BOARD_COUNT.getText(commentList.size()));

            // 댓글 동적 변환
            int start = bodyString.indexOf("<!--forStart-->");
            int end = bodyString.indexOf("<!--forEnd-->");
            String itemTemplate = bodyString.substring(start, end);

            StringBuilder itemHtml = new StringBuilder();
            for (Comment comment : commentList) {
                String listItem = itemTemplate.replace("{{bordId}}", String.valueOf(comment.getId()))
                        .replace("{{writer}}", comment.getWriter())
                        .replace("{{writeTime}}", comment.getFormattedCreateTime())
                        .replace("{{content}}", comment.getContents())
                        .replace("{{commentCount}}", String.valueOf(Optional.ofNullable(commentList)
                                .orElse(Collections.emptyList()).size()));
                listItem = replaceHeadline(listItem, "forStart");

                itemHtml.append(listItem);
            }
            bodyString = bodyString.substring(0, start) + itemHtml + bodyString.substring(end);

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

            int start = bodyString.indexOf("<!--forStart-->");
            int end = bodyString.indexOf("<!--forEnd-->");
            String itemTemplate = bodyString.substring(start, end);

            StringBuilder itemHtml = new StringBuilder();
            for (Board board : boardList) {
                String listItem = itemTemplate.replace("{{bordId}}", String.valueOf(board.getId()))
                        .replace("{{boardTitle}}", board.getTitle())
                        .replace("{{writeTime}}", board.getFormattedCreateTime())
                        .replace("{{author}}", board.getWriter())
                        .replace("{{commentCount}}", String.valueOf(board.getCommentList().size()));
                listItem = replaceHeadline(listItem, "forStart");

                itemHtml.append(listItem);
            }
            bodyString = bodyString.substring(0, start) + itemHtml + bodyString.substring(end);
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

    public static String changeMenuHtmlFile(ResourceDto resource, String bodyString) {
        // 메뉴 바 변환
        if (resource.isLoggined()) {
            bodyString = bodyString.replace("{{menu}}",
                    UserContent.LOGIN.getText(String.valueOf(Model.getAttribute("username").get())));
        } else {
            bodyString = bodyString.replace("{{menu}}",
                    UserContent.NON_LOGIN.getText());
        }
        return bodyString;
    }

    private static String removeAnnotationSymbols(String line) {
        line = line.replace("<!--", "");
        line = line.replace("-->", "");
        return line;
    }

}
