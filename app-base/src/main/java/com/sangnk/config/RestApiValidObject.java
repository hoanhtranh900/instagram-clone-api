package com.sangnk.config;

import com.sangnk.core.dto.response.ResponseData;
import com.sangnk.core.utils.UtilsHttp;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * TODO: write you class description here
 *
 * @author
 */

@RestControllerAdvice
public class RestApiValidObject extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ResponseData response = ResponseData.builder().code(status.value()).path(path).message(ex.getMessage()).time(new Date()).build();
        return new ResponseEntity<>(response, UtilsHttp.getHeaders(headers), status);
    }

}
