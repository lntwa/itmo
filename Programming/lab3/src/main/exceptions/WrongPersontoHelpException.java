package main.exceptions;

public class WrongPersontoHelpException extends RuntimeException {
    public WrongPersontoHelpException(String message) {
        super(message);
    }
}
