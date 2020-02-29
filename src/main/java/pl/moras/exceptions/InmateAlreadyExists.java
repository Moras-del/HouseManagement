package pl.moras.exceptions;

public class InmateAlreadyExists extends RuntimeException {
    public InmateAlreadyExists(String inmateName, String houseName) {
        super("Nazwa "+inmateName+" jest zajęta dla domu "+houseName);
    }
}
