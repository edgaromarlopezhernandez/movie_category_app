package com.movies.exceptions;

import com.movies.utils.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@ControllerAdvice
@RestController
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request){
        System.out.println("************************ERROR EXCEPTION************************");
        log.error(exception.getMessage());
        log.error(String.valueOf(exception.getStackTrace()));
        log.error(request.getDescription(true));
        log.error(request.getContextPath());
        System.out.println("************************ERROR EXCEPTION************************");
        return new CustomResponse(false, "[Exception]Internal Server Error", null).createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IncorrectDataBadRequestException.class)
    public ResponseEntity<Object> handleBadRequestExceptions(IncorrectDataBadRequestException exception, WebRequest request){
        log.info(request.getDescription(true), request.getContextPath(), request.getHeaderNames());//Check out all request options
        return new CustomResponse(false, exception.getMessage(), null).createResponse(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundExceptions(DataNotFoundException exception, WebRequest request){
        log.info(request.getDescription(true), request.getContextPath());// check other features of webRequest
        return new CustomResponse(false, exception.getMessage(), null).createResponse(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> handleGeneralExceptions(GeneralException exception, WebRequest request){
        log.error("************************ERROR GENERAL EXCEPTION************************");
        log.error(exception.getMessage());
        log.error(String.valueOf(exception.getStackTrace()));
        log.error(request.getDescription(true));
        log.error(request.getContextPath());
        log.error("************************ERROR GENERAL EXCEPTION************************");
        return new CustomResponse(false, "[General exception]Internal Server Error", null).createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}