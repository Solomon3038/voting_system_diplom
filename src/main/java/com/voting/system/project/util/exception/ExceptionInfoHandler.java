package com.voting.system.project.util.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

@Log4j2
@ControllerAdvice
@RestController
public class ExceptionInfoHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotExistException.class})
    public final ResponseEntity<?> handleNotExistException(NotExistException ex, WebRequest request) throws Exception {
        return getResponseEntity(request, ex.getMessage(), ErrorType.DATA_NOT_EXIST, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({VoteException.class})
    public final ResponseEntity<?> handleVoteException(VoteException ex, WebRequest request) throws Exception {
        return getResponseEntity(request, ex.getMessage(), ErrorType.VOTE_ERROR, HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler({IllegalRequestDataException.class})
    public final ResponseEntity<?> handleVoteException(IllegalRequestDataException ex, WebRequest request) throws Exception {
        return getResponseEntity(request, ex.getMessage(), ErrorType.VALIDATION_ERROR, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public final ResponseEntity<?> handleVoteException(ConstraintViolationException ex, WebRequest request) throws Exception {
        return getResponseEntity(request, ex.getMessage(), ErrorType.VALIDATION_ERROR, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public final ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) throws Exception {
        log.error(ex.getMessage());
        String[] details = ex.getMessage().split(";");
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), request.getDescription(false), ErrorType.DATA_ERROR, details);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT); //409
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        return getResponseEntity(request, ex.getMessage(), ErrorType.APP_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage());
        String[] details = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> Arrays.stream(fe.toString().split(";")))
                .flatMap(Function.identity())
                .toArray(String[]::new);
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), request.getDescription(false), ErrorType.VALIDATION_ERROR, details);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<?> getResponseEntity(WebRequest request, String message, ErrorType validationError, HttpStatus unprocessableEntity) {
        log.error(message);
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), request.getDescription(false), validationError, message);
        return new ResponseEntity<>(exceptionResponse, unprocessableEntity);
    }
}