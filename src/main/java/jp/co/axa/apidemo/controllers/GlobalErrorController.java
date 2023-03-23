package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.exception.EntityNotFound;
import jp.co.axa.apidemo.exception.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalErrorController {

    @ExceptionHandler(EntityNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInfo entityNotFoundHandler(HttpServletRequest request, EntityNotFound exception) {
        log.error("URI [{}]  Error message - [{}]", request.getRequestURI(), exception.getMessage());
        return exception.getErrorInfo();
    }
}
