package webserver;

import dto.ResourceDto;
import exception.SourceException;
import util.ErrorCode;
import util.HtmlBuilder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class ResourceHandler {

    public static byte[] resolveResource(ResourceDto resource) throws IOException {
        String resourcePath = getResourcePath(resource.getPath());
        FileInputStream fis = new FileInputStream(resourcePath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bodyData = changeResourceToByte(fis, bos);
        if (!resourcePath.contains(".html")) {
            return bodyData;
        }

        // 로그인시의 뷰 수정
        String bodyString = new String(bodyData);
        bodyString = HtmlBuilder.changeMenuHtmlFile(resource, bodyString);
        bodyString = HtmlBuilder.changeUserListHtmlFile(resource, bodyString);
        bodyString = HtmlBuilder.changeIndexHtmlFile(resource, bodyString);
        bodyString = HtmlBuilder.changeShowHtmlFile(resource, bodyString);
        bodyString = HtmlBuilder.changeProfileHtmlFile(resource, bodyString);

        return bodyString.getBytes();
    }

    // resourceToByte
    public static byte[] changeResourceToByte(FileInputStream fis, ByteArrayOutputStream bos) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, read);
        }
        fis.close();
        return bos.toByteArray();
    }

    private static String getResourcePath(String path) {
        String sourceDirectory = path.contains(".html") ? "./templates" : "./static";
        URL resource = ResourceHandler.class.getClassLoader().getResource(sourceDirectory + path);
        if (resource == null) {
            throw new SourceException(ErrorCode.NOT_VALID_PATH);
        }
        return resource.getPath();
    }

    // errorPage Load
    public static byte[] getErrorResource(ResourceDto resource, String message) throws IOException {
        String resourcePath = getResourcePath(resource.getPath());
        FileInputStream fis = new FileInputStream(resourcePath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bodyData = changeResourceToByte(fis, bos);
        String bodyString = new String(bodyData);

        bodyString = bodyString.replace("{{errormessage}}", "<p>" + message + "</p>");

        return bodyString.getBytes();
    }
}