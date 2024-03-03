package controller;

import dto.ResourceDto;
import model.Board;
import model.Model;
import model.User;
import service.BoardService;
import service.CommentService;
import service.UserService;
import util.ParseParams;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class BoardController implements Controller{
    private final BoardService boardService = new BoardService();
    private final UserService userService = new UserService();
    private final CommentService commentService = new CommentService();

    private Map<String, BiFunction<String, Object, ResourceDto>> methodMap =  new HashMap<>();

    {
        methodMap.put("/write.html", this::processBoard);
        methodMap.put("/write", this::generateBoardResource);
        methodMap.put("/board/comment", this::generateCommentResource);
        methodMap.put("/qna/show", this::generateBoardDetailResource);
    }

    public ResourceDto generateCommentResource(String session, Object bodyData) {
        if (session == null) {
            return ResourceDto.ofWithStatusAndNoLogin("/user/login.html", 302, false);
        }
        Board board = boardService.findBoard((ParseParams) bodyData);
        User user = userService.findUserWithSession(session);
        commentService.createComment(user, board, (ParseParams) bodyData);
        Model.addAttribute("board", board);
        Model.addAttribute("commentList", board.getCommentList());
        return ResourceDto.ofWithStatus("/index.html", 302);
    }

    public ResourceDto generateBoardDetailResource(String session, Object queryParams) {
        if (session == null) {
            return ResourceDto.ofWithStatusAndNoLogin("/user/login.html", 302, false);
        }
        Board board = boardService.findBoard((ParseParams) queryParams);
        Model.addAttribute("board", board);
        Model.addAttribute("commentList", board.getCommentList());
        return ResourceDto.of("/qna/show.html");
    }

    public ResourceDto generateBoardResource(String session, Object bodyData) {
        if (session == null) {
            return ResourceDto.ofWithStatusAndNoLogin("/user/login.html", 302, false);
        }
        User user = userService.findUserWithSession(session);
        boardService.createBoard(user, (ParseParams)bodyData);
        return ResourceDto.ofWithStatus("/index.html", 302);
    }

    public ResourceDto processBoard(String session, Object path) {
        if (session == null) {
            return ResourceDto.ofWithStatusAndNoLogin("/user/login.html", 302,false);
        }
        return ResourceDto.of((String) path);
    }

    public BiFunction<String, Object, ResourceDto> getCorrectMethod(String path) {
        for (String key : methodMap.keySet()) {
            if (path.contains(key) && path.contains("?")) {
                return methodMap.get(key);
            }
            else if (path.matches(key)) {
                return methodMap.get(key);
            }
        }
        return null;
    }
}
