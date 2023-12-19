package exceptions;

public class InvalidName extends RuntimeException{
    public InvalidName(String message) {
        super(message);
    }
}
