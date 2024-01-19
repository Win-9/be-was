package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import controller.FrontController;
import controller.UserController;
import exception.SourceException;
import model.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ResponseBuilder;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    static Lock writeLock = lock.writeLock();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
//        public static final Map<String, Function<P, R>> REQUEST_MAP = new HashMap<>();

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            RequestHeader requestHeader = readRequest(br);

            UserController controller = FrontController.getController(requestHeader.getPath());

            CommonResponse response = getResponse(requestHeader, controller);
            ResponseBuilder.sendResponse(dos, response.getBody(), response.getHttpStatus());
        } catch (ClassNotFoundException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static CommonResponse getResponse(RequestHeader requestHeader, UserController controller) throws IOException {
        CommonResponse response = null;
        try {
            byte[] bytes = PathHandler.responseResource(requestHeader.getMethod(), requestHeader.getPath(), controller);
            response = CommonResponse.onOk(bytes);
        } catch (SourceException e) {
            response = CommonResponse.onFail(e.getErrorCode().getStatus(), e.getMessage());
        } finally {
            return response;
        }
    }

    private RequestHeader readRequest(BufferedReader br) throws IOException, ClassNotFoundException {
        writeLock.lock();
        RequestHeader requestHeader = getRequestUrl(br);
        String line;

        logger.debug("===== request start =====");
        while ((line = br.readLine()) != null) {
            if (line.isBlank()) {
                break;
            }
            parseHeader(line, requestHeader);
        }
        requestHeader.printHeader();
        logger.debug("===== request end =====");
        writeLock.unlock();

        return requestHeader;
    }

    private static RequestHeader getRequestUrl(BufferedReader br) throws IOException {
        String line = URLDecoder.decode(br.readLine(), StandardCharsets.UTF_8);
        String[] firstHeader = line.split(" ");
        return new RequestHeader(firstHeader[0], firstHeader[1], firstHeader[2]);
    }

    private void parseHeader(String line, RequestHeader requestHeader) {
        String[] header = line.split(": ");
        String parseKey = header[0].replace("-", "");
        String key = parseKey.substring(0, 1).toLowerCase() + parseKey.substring(1);
        String value = header[1];
        RequestHeader.setHeader(requestHeader, key, value);
    }

    private URL getResource(String path) {
        URL resource = getClass().getClassLoader().getResource(path);
        return resource;
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
