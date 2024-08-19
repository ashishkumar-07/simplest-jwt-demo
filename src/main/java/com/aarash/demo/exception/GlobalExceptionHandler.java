package com.aarash.demo.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    ProblemDetail handleNotFoundException(BadRequestException e) {
        log.error("inside global exception handler. error message : {}", e.getMessage());
        e.printStackTrace();
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({UnAuthorizedException.class, AccessDeniedException.class})
    ProblemDetail handleUnAuthorizedException(Exception e) {
        log.error("inside global exception handler. error message : {}", e.getMessage());
        e.printStackTrace();
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

//    @ExceptionHandler(ConstraintViolationException.class)
//    public ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
//        log.error("inside global exception handler. error message : {}", e.getMessage());
//        e.printStackTrace();
//        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("inside global exception handler. error message : {}", e.getMessage());
        e.printStackTrace();
        String errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(a -> a.getField() + ":" + a.getDefaultMessage())
                .reduce((a, b) -> String.join("|", a))
                .orElse("");

        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail handlerMethodValidationException(HandlerMethodValidationException e) {
        log.error("inside global exception handler. error message : {}", e.getMessage());
        e.printStackTrace();
        String errors =  e.getAllValidationResults()
                .stream()
                .map(a -> a.getResolvableErrors())
                .flatMap(a -> a.stream())
                .map(a -> a.getDefaultMessage())
                .reduce((a, b) -> String.join("|", a))
                .orElse("");
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleOtherException(Exception e) {
        log.error("inside global exception handler. error message : {}", e.getMessage());
        e.printStackTrace();
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}