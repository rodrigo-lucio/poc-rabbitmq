package br.com.lucio.order.infra.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO {

    private OffsetDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public static ErrorResponseDTO buildErrorResponse(Exception exception, HttpStatus status, WebRequest request) {
        return ErrorResponseDTO.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(exception.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
    }
    public static ErrorResponseDTO buildErrorResponse(Exception exception, HttpStatus status) {
        return ErrorResponseDTO.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }
}
