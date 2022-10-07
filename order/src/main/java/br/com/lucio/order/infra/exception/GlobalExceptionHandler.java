package br.com.lucio.order.infra.exception;

import br.com.lucio.order.application.exception.ServiceException;
import br.com.lucio.order.infra.exception.dto.ErrorResponseDTO;
import br.com.lucio.order.infra.exception.dto.ErrorResponseFieldDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleEmptyResultDataAccessException(ConstraintViolationException ex, WebRequest request) {
        ErrorResponseFieldDTO messagesInvalidFields = ErrorResponseFieldDTO.buildMessagesInvalidFields(ex.getConstraintViolations());
        return super.handleExceptionInternal(ex, messagesInvalidFields, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler({Exception.class})
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, @Nullable Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponseDTO errorBody = ErrorResponseDTO.buildErrorResponse(exception, status, request);
        return super.handleExceptionInternal(exception, errorBody, headers, status, request);
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<Object> handleServiceException(ServiceException exception) {
        return buildErrorResponseAndHandleExceptionInternal(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return buildErrorResponseAndHandleExceptionInternal(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException exception) {
        return buildErrorResponseAndHandleExceptionInternal(exception, HttpStatus.NOT_FOUND);
    }
    private ResponseEntity<Object> buildErrorResponseAndHandleExceptionInternal(Exception exception, HttpStatus status) {
        ErrorResponseDTO errorBody = ErrorResponseDTO.buildErrorResponse(exception, status);
        return super.handleExceptionInternal(exception, errorBody, new HttpHeaders(), status, null);
    }

}
