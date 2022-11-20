package fr.sfc.database;

import java.sql.ResultSet;
import java.util.Optional;

/**
 *
 */
public interface Query extends AutoCloseable {

    /**
     *
     * @param param
     * @param value
     * @return
     */
    Query setParameter(String param, String value);


    /**
     *
     */
    void prepare();

    /**
     *
     * @return
     */
    Optional<ResultSet> query();

    /**
     *
     * @return
     */
    Optional<ResultSet> getResultSet();

    /**
     *
     * @return
     */
    String getRequest();

}
