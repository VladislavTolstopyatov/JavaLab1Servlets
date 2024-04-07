package exceptions;

public class LoginAlreadyRegisteredException extends Throwable{
    public LoginAlreadyRegisteredException(String message) {
        super(message);
    }
}
