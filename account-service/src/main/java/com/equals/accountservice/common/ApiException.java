package com.equals.accountservice.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiException extends RuntimeException{

    private final ExceptionDetail exceptionDetail;
    private final int status;

    public ApiException(int status, String message, ExceptionDetail exceptionDetail) {
        super(message);
        this.status = status;
        this.exceptionDetail = exceptionDetail;
    }
}
