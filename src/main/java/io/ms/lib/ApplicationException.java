package io.ms.lib;

public class ApplicationException extends RuntimeException {

    private ErrorCode code;
    private Throwable cause;

    public ApplicationException(Exception ex) {
        this.code = ErrorCodeEnum.INTERNAL;
        this.cause = ex;
    }

    public ApplicationException(ErrorCode code) {
        this.code = code;
    }

    public ApplicationException(Exception ex, String message, ErrorCode code) {
        super(message);
        this.code = code;
        this.cause=  ex;
    }

    public ApplicationException(String message, Throwable cause, ErrorCode code) {
        super(message, cause);
        this.code = code;
        this.cause = cause;
    }

    public ApplicationException(Throwable cause, ErrorCode code) {
        super(cause);
        this.code = code;
        this.cause = cause;

    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.cause = cause;

    }

    public ErrorCode getCode() {
        return code;
    }
}
