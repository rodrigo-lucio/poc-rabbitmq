package br.com.lucio.order.application.exception;

public class ServiceException extends RuntimeException {
    private String message;

    public ServiceException(String message) {
        super();
        this.message = message;
    }

    public String getMensagem() {
        return message;
    }

    @Override
    public String toString() {
        return this.getClass().toString();
    }

}
