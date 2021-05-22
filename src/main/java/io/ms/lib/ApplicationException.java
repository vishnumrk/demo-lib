package io.ms.lib;

public class ApplicationException extends ContextedRuntimeException {

    private ErrorCode code;
    private Object[] params;
    private Throwable cause;

    public ApplicationException(Exception ex) {
        this.code = ErrorCodeEnum.INTERNAL;
        this.cause = ex;
    }

    public ApplicationException(ErrorCode code, Throwable cause) {
        super(cause);
        this.code = code;
        this.cause = cause;
    }

    public ApplicationException(ErrorCode code, Throwable cause, Object... params) {
        this.code = code;
        this.cause = cause;
        this.params = params;
    }

    public ApplicationException(ErrorCode code, Exception ex, String message, Object... params) {
        super(message);
        this.code = code;
        this.cause=  ex;
        this.params = params;
    }

    public ErrorCode getCode() {
        return code;
    }
}
