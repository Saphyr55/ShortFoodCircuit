package fr.sfc.persistence;

public class PersistenceAnnotationPresentException extends RuntimeException {

    public PersistenceAnnotationPresentException() {
       super();
    }

    public PersistenceAnnotationPresentException(String s) {
        super(s);
    }
}
