package fr.sfc.framework.database;

import java.sql.ResultSet;

/**
 *
 */
public interface Query extends AutoCloseable {

    /**
     * @param value
     * @return
     */
    Query withParameter(Object value);

    /**
     *
     */
    void prepare();

    /**
     * @return
     */
    ResultSet executeQuery();

    /**
     *
     */
    void executeUpdate();

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
