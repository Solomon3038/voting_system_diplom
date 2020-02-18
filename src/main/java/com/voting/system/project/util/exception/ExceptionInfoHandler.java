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

import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

import static com.voting.system.project.util.exception.ErrorType.*;

@Log4j2
@ControllerAdvice
@RestController
public class ExceptionInfoHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotExistException.class})
    public final ResponseEntity<?> handleNotExistException(NotExistException ex, WebRequest request) throws Exception {
        log.error(ex.getMessage());
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), request.getDescription(false), DATA_NOT_EXIST, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY); //422
    }

    @ExceptionHandler({VoteException.class})
    public final ResponseEntity<?> handleVoteException(VoteException ex, WebRequest request) throws Exception {
        log.error(ex.getMessage());
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), request.getDescription(false), VOTE_ERROR, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY); //422
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public final ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) throws Exception {
        log.error(ex.getMessage());
        String[] details = ex.getMessage().split(";");
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), request.getDescription(false), DATA_ERROR, details);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT); //409
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        log.error(ex.getMessage());
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), request.getDescription(false), APP_ERROR, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR); //500
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage());
        String[] details = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> Arrays.stream(fe.toString().split(";")))
                .flatMap(Function.identity())
                .toArray(String[]::new);
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), request.getDescription(false), VALIDATION_ERROR, details);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY); //422
    }
}