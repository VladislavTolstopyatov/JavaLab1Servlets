package exceptions;

public class GameNotFoundException extends Throwable{
    public GameNotFoundException(String message) {
        super(message);
    }
}
