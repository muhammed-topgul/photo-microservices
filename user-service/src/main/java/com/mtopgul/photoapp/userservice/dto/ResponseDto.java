package com.mtopgul.photoapp.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Map;

/**
 * @author muhammed-topgul
 * @since 10/10/2023 08:43
 */
public class ResponseDto {
    @Getter
    @Setter
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response {
        private int statusCode;
        private String statusMessage;

        private Response(int statusCode, String statusMessage) {
            this.statusCode = statusCode;
            this.statusMessage = statusMessage;
        }
    }

    @Getter
    @Setter
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Error extends Response {
        private String apiPath;
        private Date errorTime;
        private String exceptionClass;
        private Map<String, String> errors;

        private Error(String apiPath, int statusCode, String statusMessage, Date errorTime) {
            super(statusCode, statusMessage);
            this.apiPath = apiPath;
            this.errorTime = errorTime;
        }
    }

    @Getter
    @Setter
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Success extends Response {
        private Object body;

        private Success(Object body, HttpStatus httpStatus) {
            super(httpStatus.value(), httpStatus.getReasonPhrase());
            this.body = body;
        }
    }

    public static Success newSuccess(Object body, HttpStatus httpStatus) {
        return new Success(body, httpStatus);
    }

    public static Error newError(String apiPath, int statusCode, String statusMessage, Date errorTime) {
        return new Error(apiPath, statusCode, statusMessage, errorTime);
    }
}
