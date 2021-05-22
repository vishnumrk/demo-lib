package io.ms.lib;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ContextedRuntimeException extends RuntimeException {

    private Map<String, Object> exceptionContext;

    public ContextedRuntimeException() {
        super();
    }

    public ContextedRuntimeException(String message) {
        super(message);
    }

    public ContextedRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContextedRuntimeException(Throwable cause) {
        super(cause);
    }

    protected ContextedRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    @Override
    public synchronized Throwable initCause(Throwable cause) {
        return super.initCause(cause);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        super.setStackTrace(stackTrace);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    public ContextedRuntimeException addContextValue(String label, Object value){
        if (exceptionContext == null) {
            exceptionContext = new HashMap<>();
        }
        exceptionContext.put(label, value);
        return this;
    }

    public Map<String, Object> getExceptionContext() {
        return exceptionContext;
    }
}
