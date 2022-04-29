package exceptions;

public class ReceipeNotFoundException extends RuntimeException {
    public ReceipeNotFoundException(String message) {
        super(message);
    }
}
