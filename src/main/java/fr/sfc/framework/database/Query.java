package fr.sfc.framework.database;

import java.sql.ResultSet;

/**
 *
 */
public interface Query extends AutoCloseable {

    /**
     * Permet de remplacer les '?' avec sa valeur en paramètre
     *
     * @param value valeur du '?'
     * @return instance courante ( fluent )
     */
    Query withParameter(Object value);

    /**
     * Prepare la requête sans l'exécuter
     */
    void prepare();

    /**
     * Prepare la requête, l'exécute et retourne le result set
     *
     * @return result set
     */
    ResultSet executeQuery();

    void executeAndClose();

    /**
     * Prepare la requête et l'exécute
     * Utilisable quand la requête n'a rien à retourner
     */
    void executeUpdate();

    /**
     * Retourne le result set, si la query n'a pas été exécuter retourne null
     *
     * @return result set
     */
    ResultSet getResultSet();

    /**
     * Retourne la requête sous forme de chaine de caractères
     * Si la requête a été créer via un magic query,
     * Il retournera la requête formatée
     *
     * @return requête formatter
     */
    String getRequest();

}
