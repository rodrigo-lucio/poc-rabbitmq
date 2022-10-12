package br.com.lucio.order.application.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

}
