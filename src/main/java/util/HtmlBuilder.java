package util;

import content.BoardContent;
import content.UserContent;
import dto.ResourceDto;
import model.Board;
import model.Model;
import model.User;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class HtmlBuilder {
    public static String changeShowHtmlFile(ResourceDto resource, String bodyString) {
        if (resource.getPath().contains("/qna/show")) {
            Board board = (Board) Model.getAttribute("board").get();
            bodyString = bodyString.replace("{{boardDetail}}",
                    BoardContent.BOARD_DEFAIL.getText(board.getTitle(), board.getFormattedCreateTime(), board.getContents()));
        }
        return bodyString;
    }

    public static String changeIndexHtmlFile(ResourceDto resource, String bodyString) {
        if (resource.getPath().contains("/index.html")) {
            List<Board> boardList = (List<Board>) Model.getAttribute("boardList")
                    .orElse(Collections.emptyList());

            if (boardList.size() == 0) {
                bodyString = bodyString.replace("{{boardData}}","");
            } else {
                for (Board board : boardList) {
                    bodyString = bodyString.replace("{{boardData}}",
                            BoardContent.BOARD.getText(board.getId(), board.getTitle(), board.getFormattedCreateTime(), board.getWriter()));
                }
            }
        }
        return bodyString;
    }

    public static String changeUserListHtmlFile(ResourceDto resource, String bodyString) {
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
        String bodyString = new String(bodyData);
        if (resource.isIsloggined()) {
            bodyString = bodyString.replace("{{menu}}",
                    UserContent.LOGIN.getText(String.valueOf(Model.getAttribute("username").get())));
        } else {
            bodyString = bodyString.replace("{{menu}}",
                    UserContent.NON_LOGIN.getText());
        }
        return bodyString;
    }

}
