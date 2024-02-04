package exception;

import dto.ResourceDto;
import response.CommonResponse;
import webserver.ResourceHandler;

import java.io.IOException;

public class ExceptionHandler {
    public static CommonResponse handleGeneralException(SourceException e, ResourceDto resource) throws IOException {
        byte[] errorByte = ResourceHandler.getErrorResource(resource, e.getMessage());
        return CommonResponse.onFail(e.getErrorCode().getHttpStatus(), errorByte);
    }
}
