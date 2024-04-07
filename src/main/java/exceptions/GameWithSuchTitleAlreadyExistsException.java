package exceptions;

public class GameWithSuchTitleAlreadyExistsException extends Throwable{
    public GameWithSuchTitleAlreadyExistsException(String message) {
        super(message);
    }
}
