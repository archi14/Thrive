package exceptions;

public class InvalidProjectStatus extends Exception{
    public InvalidProjectStatus(String message) {
        super(message);
    }
}
