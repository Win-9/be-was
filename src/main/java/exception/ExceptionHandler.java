package exception;

import response.CommonResponse;

public class ExceptionHandler {
    public static CommonResponse handleGeneralException(SourceException e){
        return CommonResponse.onFail(e.getErrorCode().getHttpStatus(), e.getMessage());
    }
}
