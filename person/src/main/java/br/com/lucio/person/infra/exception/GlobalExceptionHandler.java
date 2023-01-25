package br.com.lucio.person.infra.exception;

import br.com.lucio.person.application.exception.ResourceNotFoundException;
import br.com.lucio.person.application.exception.ServiceException;
import br.com.lucio.person.infra.exception.dto.ErrorResponseDTO;
import br.com.lucio.person.infra.exception.dto.ErrorResponseFieldDTO;
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
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        ErrorResponseFieldDTO messagesInvalidFields = ErrorResponseFieldDTO.buildMessagesInvalidFields(ex.getConstraintViolations());
        return super.handleExceptionInternal(ex, messagesInvalidFields, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    @Override
    public ResponseEntity<Object> handleExceptionInternal(Exception exception, @Nullable Object body,
                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponseDTO errorBody = ErrorResponseDTO.buildErrorResponse(exception, status, request);
        return super.handleExceptionInternal(exception, errorBody, headers, status, request);
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<Object> handleServiceException(ServiceException exception) {
        return buildErrorResponseAndHandleException(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return buildErrorResponseAndHandleException(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGeneralException(Exception exception) {
        return buildErrorResponseAndHandleException(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildErrorResponseAndHandleException(Exception exception, HttpStatus status) {
        ErrorResponseDTO errorBody = ErrorResponseDTO.buildErrorResponse(exception, status);
        return super.handleExceptionInternal(exception, errorBody, new HttpHeaders(), status, null);
    }

}
