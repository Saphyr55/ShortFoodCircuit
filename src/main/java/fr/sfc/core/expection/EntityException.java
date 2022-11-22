package fr.sfc.core.expection;

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
