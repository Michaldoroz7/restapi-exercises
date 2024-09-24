package pl.doroz.restapi.handler;

public class OpinionNotFoundException extends RuntimeException {
    public OpinionNotFoundException(String message) {
        super(message);
    }
}
