package fr.sfc.framework.persistence.exception;

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
