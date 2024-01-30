package controller;

import dto.ResourceDto;
import model.Board;
import model.Model;
import model.User;
import service.BoardService;
import util.ParseParams;
import service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class UserController {
    private final UserService userService = new UserService();
    private final BoardService boardService = new BoardService();
    private Map<String, BiFunction<String, Object, ResourceDto>> methodMap =  new HashMap<>();

    {
        methodMap.put("/user/create", this::generateUserResource);
        methodMap.put("/user/list.html", this::generateUserListResource);
        methodMap.put("/index.html", this::processIndexResource);
        methodMap.put("/user/form.html", this::process);
        methodMap.put("/user/login.html", this::process);
        methodMap.put("/write.html", this::processBoard);
        methodMap.put("/write", this::generateBoardResource);
        methodMap.put("/user/login", this::loginUserResource);
        methodMap.put("/qna/show", this::generateBoardDetailResource);
        methodMap.put("/user/login_failed.html", this::process);
    }

    public ResourceDto generateBoardDetailResource(String session, Object queryParams) {
        if (session == null) {
            return ResourceDto.of("/user/login.html", 302, false);
        }
        Board board = boardService.findBoard((ParseParams) queryParams);
        Model.addAttribute("board", board);
        return ResourceDto.of("/qna/show.html");
    }
    public ResourceDto generateBoardResource(String session, Object bodyData) {
        if (session == null) {
            return ResourceDto.of("/user/login.html", 302, false);
        }
        User user = userService.findUserWithSession(session);
        boardService.createBoard(user, (ParseParams)bodyData);
        return ResourceDto.of("/index.html", 302);
    }

    public ResourceDto processBoard(String session, Object path) {
        if (session == null) {
            return ResourceDto.of("/user/login.html", 302,false);
        }
        return ResourceDto.of((String) path);
    }

    public ResourceDto generateUserListResource(String session, Object path) {
        if (session == null) {
            return ResourceDto.of("/user/login.html", 302, false);
        }
        List<User> userList = userService.findAllUser();
        Model.addAttribute("userList", userList);
        return ResourceDto.of((String) path);
    }

    public ResourceDto loginUserResource(String session, Object bodyData) {
        String sessionId = userService.loginUser((ParseParams) bodyData);
        if (sessionId == null) {
            return ResourceDto.of("/user/login_failed.html", 302, false);
        }
        User user = userService.findUserWithSession(sessionId);
        Model.addAttribute("sessionId", sessionId);
        Model.addAttribute("username", user.getName());
        return ResourceDto.of("/index.html", 302);
    }

    public ResourceDto generateUserResource(String session, Object queryParams) {
        userService.createUser((ParseParams) queryParams);
        return ResourceDto.of("/index.html", 302, false);
    }

    public ResourceDto processIndexResource(String session, Object path) {
        List<Board> boardList = boardService.findAllBoard();
        Model.addAttribute("boardList", boardList);
        if (session == null) {
            return ResourceDto.of((String) path, false);
        }
        return ResourceDto.of((String) path);
    }

    public ResourceDto process(String session, Object path) {
        if (session == null) {
            return ResourceDto.of((String) path, false);
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
