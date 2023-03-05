package com.akshay.checkout.advisory;

import com.akshay.common.dto.response.ErrorResponse;
import com.akshay.common.dto.response.Response;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> errorList = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        Response<String> response = Response.<String>builder()
                .error(new ErrorResponse(HttpStatus.BAD_REQUEST, String.join(" :: ", errorList)))
                .build();
        int statusCode = response.getError()!=null ? response.getError().getCode() : 200;
        return response;
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageConversionException.class)
    public Response<String> handleMessageConversionException(HttpMessageConversionException exception) {
        Response<String> response = Response.<String>builder()
                .error(new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage()))
                .build();
        int statusCode = response.getError()!=null ? response.getError().getCode() : 200;
        return response;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response<String> handleGenericException(Exception exception) {
        Response<String> response = Response.<String>builder()
                .error(new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage()))
                .build();
        int statusCode = response.getError()!=null ? response.getError().getCode() : 200;
        return response;
    }
}
