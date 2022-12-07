package fr.sfc.framework.persistence.exception;

public class PersistenceAnnotationNotPresentException extends RuntimeException {

    public PersistenceAnnotationNotPresentException() {
        super();
    }

    public PersistenceAnnotationNotPresentException(String s) {
        super(s);
    }
}
