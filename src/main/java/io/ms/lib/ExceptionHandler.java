package io.ms.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    private MessageSource messageSource;


    public ExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({MissingServletRequestParameterException.class})
    public final ResponseEntity<Object> handleException(MissingServletRequestParameterException ex, WebRequest request) throws Exception {
        log.error(ex.getMessage(),ex);
        Error errorResponse = new Error();
        errorResponse.setCode(ErrorCodeEnum.INVALID_ARGUMENT);
        errorResponse.setTarget(ex.getParameterName());
        log.error(ex.getParameterType());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ConstraintViolationException.class})
    public final ResponseEntity<Object> handleException(ConstraintViolationException ex, WebRequest request) throws Exception {
        log.error(ex.getMessage(),ex);
        Error errorResponse = new Error();
        errorResponse.setCode(ErrorCodeEnum.BAD_REQUEST);

        List<Error> errorList = ex.getConstraintViolations().stream().map(constraintViolation -> {
            Error error = new Error();
            error.setCode(ErrorCodeEnum.BAD_REQUEST);
            error.setMessage(constraintViolation.getMessage());
            error.setTarget(constraintViolation.getPropertyPath().toString());
            return error;
        }).collect(Collectors.toList());


        errorResponse.setDetails(errorList);
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ApplicationException.class})
    public final ResponseEntity<Object> handleException(ApplicationException ex, WebRequest request) throws Exception {
        log.error(ex.getMessage(),ex);

        Error errorResponse = new Error();
        errorResponse.setCode(ex.getCode());
        errorResponse.setMessage(messageSource.getMessage(ex.getCode().name(), null, Locale.US));
        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) throws Exception {
        log.error(ex.getMessage(),ex);

        Error errorResponse = new Error();
        errorResponse.setCode(ErrorCodeEnum.INTERNAL);
        errorResponse.setMessage(messageSource.getMessage(ErrorCodeEnum.INTERNAL.name(), null, Locale.US));
        return new ResponseEntity<Object>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}