package dto;

import util.HttpStatus;

public class ResourceDto {
    private String path;
    private HttpStatus httpStatus = HttpStatus.OK;
    private boolean isLoggined = true;

    public ResourceDto(String path) {
        this.path = path;
    }

    public ResourceDto(String path, HttpStatus httpStatus) {
        this.path = path;
        this.httpStatus = httpStatus;
    }

    public ResourceDto(String path, boolean isLoggined) {
        this.path = path;
        this.isLoggined = isLoggined;
    }

    public ResourceDto(String path, HttpStatus httpStatus, boolean isLoggined) {
        this.path = path;
        this.httpStatus = httpStatus;
        this.isLoggined = isLoggined;
    }

    public static ResourceDto of(String path) {
        return new ResourceDto(path);
    }

    public static ResourceDto ofWithStatus(String path, int status) {
        HttpStatus httpStatus = HttpStatus.findByStatusCode(status);
        return new ResourceDto(path, httpStatus);
    }

    public static ResourceDto ofWithNoLogin(String path, boolean isLoggined) {
        return new ResourceDto(path, isLoggined);
    }

    public static ResourceDto ofWithStatusAndNoLogin(String path, int status, boolean isLoggined) {
        HttpStatus httpStatus = HttpStatus.findByStatusCode(status);
        return new ResourceDto(path, httpStatus, isLoggined);
    }

    public String getPath() {
        return path;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getExtension() {
        int dotIndex = path.lastIndexOf(".");
        return path.substring(dotIndex + 1);
    }

    public boolean isLoggined() {
        return isLoggined;
    }
}
