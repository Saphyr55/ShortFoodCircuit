package fr.sfc.api.persistence;

public class EntityException extends RuntimeException{

    public EntityException() {

    }

    EntityException(String s) {
        super(s);
    }

    EntityException(RuntimeException e) {
        super(e);
    }

}
