package com.kumojin.gestevent.web.error;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kumojin.gestevent.service.NameAlreadyUsedException;

@ControllerAdvice
public class WebResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { BadRequestException.class, NameAlreadyUsedException.class })
    public ResponseEntity<Object> handleBadRequestException(
            RuntimeException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleDefaultException(Exception ex, WebRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        List<String> messages = ex.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        return buildResponse("Validation error", messages, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponse(String message, List<String> fieldErrors, HttpStatus httpStatus) {
        ErrorAttributes errorAttributes = ErrorAttributes.builder()
                .timestamp(Instant.now())
                .status(httpStatus.value())
                .message(message)
                .fieldErrors(fieldErrors)
                .build();
        return new ResponseEntity<>(errorAttributes, httpStatus);
    }

    private ResponseEntity<Object> buildResponse(String message, HttpStatus httpStatus) {
        ErrorAttributes errorAttributes = ErrorAttributes.builder()
                .timestamp(Instant.now())
                .status(httpStatus.value())
                .message(message)
                .build();
        return new ResponseEntity<>(errorAttributes, httpStatus);
    }
}
