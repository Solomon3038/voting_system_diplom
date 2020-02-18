package com.voting.system.project.util.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExceptionResponse {
    private Date timestamp;
    private String path;
    private ErrorType type;
    private String[] details;

    public ExceptionResponse(Date timestamp, String path, ErrorType type, String... details) {
        this.timestamp = timestamp;
        this.path = path;
        this.type = type;
        this.details = details;
    }
}

