package io.ms.lib;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
    private ErrorCode code;
    private String message;
    private String target;
    private List<Error> details;
    private InnerError innerError;

    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public List<Error> getDetails() {
        return details;
    }

    public void setDetails(List<Error> details) {
        this.details = details;
    }

    public InnerError getInnerError() {
        return innerError;
    }

    public void setInnerError(InnerError innerError) {
        this.innerError = innerError;
    }
}
