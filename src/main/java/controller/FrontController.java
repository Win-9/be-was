package controller;

import dto.ResourceDto;
import exception.ExceptionHandler;
import exception.SourceException;
import response.CommonResponse;
import request.HttpRequest;
import webserver.ResourceHandler;
import webserver.PathHandler;
import webserver.RequestHeader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrontController {
    private static Map<String, Controller> controllerMap = new HashMap<>();

    static {
        // userController 매핑
        controllerMap.put("/user/create", new UserController());
        controllerMap.put("/index.html", new UserController());
        controllerMap.put("/user/form.html", new UserController());
        controllerMap.put("/user/login.html", new UserController());
        controllerMap.put("/user/login", new UserController());
        controllerMap.put("/user/list", new UserController());
        controllerMap.put("/user/profile.html", new UserController());

        // BoardController 매핑
        controllerMap.put("/write.html", new BoardController());
        controllerMap.put("/write", new BoardController());
        controllerMap.put("/qna/show", new BoardController());
    }

    public static CommonResponse service(HttpRequest httpRequest) throws IOException {
        // controller Mapping
        Controller controller = getController(httpRequest.getRequestHeader().getPath());
        CommonResponse response = getResponse(httpRequest.getRequestHeader(), httpRequest.getBody(), controller);
        response.setPath(response.getPath());
        return response;
    }

    private static CommonResponse getResponse(RequestHeader requestHeader, String body, Controller controller) {
        CommonResponse response = null;
        try {
            ResourceDto resource = PathHandler.responseResource(requestHeader, body, controller);
            byte[] bodyData = ResourceHandler.resolveResource(resource);
            response = CommonResponse.onOk(resource.getHttpStatus(), bodyData, resource.getExtension(), resource.getPath());
        } catch (SourceException e) {
            response = ExceptionHandler.handleGeneralException(e);
        } finally {
            return response;
        }
    }

    private static Controller getController(String path) {
        for (String key : controllerMap.keySet()) {
            if (path.startsWith(key)) {
                return controllerMap.get(key);
            }
        }
        return null;
    }
}
