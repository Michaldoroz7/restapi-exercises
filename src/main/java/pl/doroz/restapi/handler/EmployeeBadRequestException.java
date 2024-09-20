package pl.doroz.restapi.handler;

public class EmployeeBadRequestException extends RuntimeException {
    public EmployeeBadRequestException(String message) {
        super(message);
    }
}
