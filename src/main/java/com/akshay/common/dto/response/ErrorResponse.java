package com.akshay.common.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 123454321L;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss"
    )
    private Date timestamp;
    private int code;
    private String status;
    private String message;

    public ErrorResponse() {
        this.timestamp = new Date();
    }

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this();
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public int getCode() {
        return this.code;
    }

    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss"
    )
    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
