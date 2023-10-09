package com.mtopgul.photoapp.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author muhammed-topgul
 * @since 20/09/2023 09:29
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {
    private String apiPath;
    private int errorCode;
    private String errorMessage;
    private Date errorTime;
    private String exceptionClass;
    private Map<String, String> errors;
}
