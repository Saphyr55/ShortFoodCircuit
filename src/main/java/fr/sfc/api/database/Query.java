package fr.sfc.api.database;

import java.sql.ResultSet;

/**
 *
 */
public interface Query extends AutoCloseable {

    /**
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
     * @return
     */
    ResultSet query();

    /**
     *
     */
    void execute();

    /**
     * @return
     */
    ResultSet getResultSet();

    /**
     * @return
     */
    String getRequest();

    void executeAndClose();

}
