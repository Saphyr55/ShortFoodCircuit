package fr.sfc.api.persistence;

public class PersistenceAnnotationPresentException extends RuntimeException {

    public PersistenceAnnotationPresentException() {
       super();
    }

    public PersistenceAnnotationPresentException(String s) {
        super(s);
    }
}
