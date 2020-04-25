package pl.moras.housemanagement.exceptions;

public class WrongHousePasswordException extends RuntimeException {

    public WrongHousePasswordException() {
        super("Podane hasło dla domu jest błędne");
    }
}
