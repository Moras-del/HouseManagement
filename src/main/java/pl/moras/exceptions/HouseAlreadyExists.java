package pl.moras.exceptions;

public class HouseAlreadyExists extends RuntimeException {

    public HouseAlreadyExists() {
        super("Podana nazwa domu jest już zajęta");
    }

}
