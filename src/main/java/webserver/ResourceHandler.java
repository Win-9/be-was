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
        byte[] bodyData = changeFileToByte(fis, bos);
        if (!resourcePath.contains(".html")) {
            return bodyData;
        }

        // 로그인시의 뷰 수정
        String bodyString = HtmlBuilder.changeMenuHtmlFile(resource, bodyData);
        bodyString = HtmlBuilder.changeUserListHtmlFile(resource, bodyString);
        bodyString = HtmlBuilder.changeIndexHtmlFile(resource, bodyString);
        bodyString = HtmlBuilder.changeShowHtmlFile(resource, bodyString);
        bodyString = HtmlBuilder.changeProfileHtmlFile(resource, bodyString);

        return bodyString.getBytes();
    }

    public static byte[] changeFileToByte(FileInputStream fis, ByteArrayOutputStream bos) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, read);
        }
        fis.close();
        byte[] bodyData = bos.toByteArray();
        return bodyData;
    }

    private static String getResourcePath(String path) {
        String sourceDirectory = path.contains(".html") ? "./templates" : "./static";
        URL resource = ResourceHandler.class.getClassLoader().getResource(sourceDirectory + path);
        if (resource == null) {
            throw new SourceException(ErrorCode.NOT_VALID_PATH);
        }
        return resource.getPath();
    }
}