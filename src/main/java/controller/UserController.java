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

public class UserController implements Controller{
    private final UserService userService = new UserService();
    private final BoardService boardService = new BoardService();
    private Map<String, BiFunction<String, Object, ResourceDto>> methodMap =  new HashMap<>();

    {
        methodMap.put("/user/create", this::generateUserResource);
        methodMap.put("/user/list.html", this::generateUserListResource);
        methodMap.put("/index.html", this::processIndexResource);
        methodMap.put("/user/form.html", this::process);
        methodMap.put("/user/login.html", this::process);
        methodMap.put("/user/login", this::loginUserResource);
        methodMap.put("/user/login_failed.html", this::process);
        methodMap.put("/user/profile.html", this::generateUserProfileResource);
        methodMap.put("/user/logout", this::generateLogoutResource);
    }

    /**
     * @GET
     * User Logout
     * @param session
     * @param path
     */
    public ResourceDto generateLogoutResource(String session, Object path) {
        if (session == null) {
            return ResourceDto.ofWithStatusAndNoLogin("/user/login.html", 302, false);
        }
        userService.removeSession(session);
        Model.removeAttribute("sessionId");
        return ResourceDto.ofWithStatusAndNoLogin("/index.html", 302,false);
    }

    /**
     * @GET
     * User MyProfile
     * @param session
     * @param path
     */
    public ResourceDto generateUserProfileResource(String session, Object path) {
        if (session == null) {
            return ResourceDto.ofWithStatusAndNoLogin("/user/login.html", 302, false);
        }
        User user = userService.findUserWithSession(session);
        Model.addAttribute("user", user);
        return ResourceDto.of((String) path);
    }

    /**
     * @GET
     * User List
     * @param session
     * @param path
     */
    public ResourceDto generateUserListResource(String session, Object path) {
        if (session == null) {
            return ResourceDto.ofWithStatusAndNoLogin("/user/login.html", 302, false);
        }
        List<User> userList = userService.findAllUser();
        Model.addAttribute("userList", userList);
        return ResourceDto.of((String) path);
    }

    /**
     * @POST
     * User Login
     * @param session
     * @param bodyData
     */
    public ResourceDto loginUserResource(String session, Object bodyData) {
        String sessionId = userService.loginUser((ParseParams) bodyData);
        if (sessionId == null) {
            return ResourceDto.ofWithStatusAndNoLogin("/user/login_failed.html", 302, false);
        }
        User user = userService.findUserWithSession(sessionId);
        Model.addAttribute("sessionId", sessionId);
        Model.addAttribute("username", user.getName());
        return ResourceDto.ofWithStatus("/index.html", 302);
    }

    /**
     * @POST
     * User Create
     * @param session
     * @param queryParams
     */
    public ResourceDto generateUserResource(String session, Object queryParams) {
        userService.createUser((ParseParams) queryParams);
        return ResourceDto.ofWithStatusAndNoLogin("/index.html", 302, false);
    }

    /**
     * @GET
     * Load Main Page
     * @param session
     * @param path
     */
    public ResourceDto processIndexResource(String session, Object path) {
        List<Board> boardList = boardService.findAllBoard();
        Model.addAttribute("boardList", boardList);
        if (session == null) {
            return ResourceDto.ofWithNoLogin((String) path, false);
        }
        return ResourceDto.of((String) path);
    }

    /**
     * @GET
     * Load Static Page
     * Page: form.html, login.html
     * @param session
     * @param path
     */
    public ResourceDto process(String session, Object path) {
        if (session == null) {
            return ResourceDto.ofWithNoLogin((String) path, false);
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
