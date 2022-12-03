package fr.sfc.api.persistence.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {

    }

    public EntityNotFoundException(String s) {
        super(s);
    }

    public EntityNotFoundException(RuntimeException e) {
        super(e);
    }

}
