package entities;

public class ExceptionHandler extends Exception {
    private final String message;

    public ExceptionHandler(String message) {
        super(message);
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }
}
