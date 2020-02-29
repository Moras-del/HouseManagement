package pl.moras.exceptions;

public class HouseNotFoundException extends RuntimeException {
    public HouseNotFoundException(String message) {
        super("Nie znaleziono domu o nazwie "+message);
    }
}
