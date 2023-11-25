package com.equals.accountservice.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionDetail {

    private int status;
    private String message;
    private String responseCode;
    private String exception;
    private String timeStamp = LocalDateTime.now().toString();
    private Object data;

    public ExceptionDetail(int status, String message, String responseCode, String exception, Object data) {
        this.status = status;
        this.message = message;
        this.responseCode = responseCode;
        this.exception = exception;
        this.data = data;
    }
}
