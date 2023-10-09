package com.mtopgul.photoapp.userservice.exception;

import com.mtopgul.photoapp.userservice.dto.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.BINDING_ERRORS;
import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.EXCEPTION;
import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

/**
 * @author muhammed-topgul
 * @since 21/09/2023 09:46
 */
@RestController
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorController {
    private final Environment environment;
    private final ErrorAttributes errorAttributes;
    private final ErrorAttributeOptions errorAttributeOptions = ErrorAttributeOptions.of(EXCEPTION, MESSAGE, BINDING_ERRORS);

    @RequestMapping("/error")
    public ResponseEntity<ErrorResponseDto> handleException(WebRequest webRequest) {
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(webRequest, errorAttributeOptions);
        String message = (String) attributes.get("message");
        String path = (String) attributes.get("path");
        int statusCode = (int) attributes.get("status");
        HttpStatus httpStatus = getHttpStatus(statusCode);
        ErrorResponseDto responseDto = ErrorResponseDto.builder()
                .apiPath(path)
                .errorCode(httpStatus.value())
                .errorMessage(message)
                .errorTime((Date) attributes.get("timestamp"))
                .build();
        if (environment.matchesProfiles("dev")) {
            responseDto.setExceptionClass((String) attributes.get("exception"));
        }
        if (attributes.containsKey("errors")) {
            @SuppressWarnings("unchecked")
            List<FieldError> fieldErrors = (List<FieldError>) attributes.get("errors");
            Map<String, String> errors = new TreeMap<>();
            for (FieldError fieldError : fieldErrors) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            responseDto.setErrors(errors);
            responseDto.setErrorMessage((String) attributes.get("error"));
        }
        return ResponseEntity.status(httpStatus).body(responseDto);
    }

    private HttpStatus getHttpStatus(int statusCode) {
        HttpStatus httpStatus = HttpStatus.resolve(statusCode);
        return Objects.nonNull(httpStatus) ? httpStatus : HttpStatus.BAD_REQUEST;
    }
}
