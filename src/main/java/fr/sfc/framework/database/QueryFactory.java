package fr.sfc.framework.database;

import java.lang.reflect.Method;

/**
 *
 */
public interface QueryFactory {

    /**
     * Permet de créer un query builder.
     *
     * @return query builder
     */
    QueryBuilder createQueryBuilder();

    /**
     * Créer une query depuis une requête.
     *
     * @param request
     * @return query
     */
    Query createQuery(String request);

    /**
     * Créer un query native, depuis une requête.<br>
     * Pouvant remplacer les %s par les valeurs.
     *
     * @param request requête
     * @param values valeurs
     * @return query
     */
    Query createNativeQuery(String request, Object... values);

    /**
     * Créer une query, depuis une methode contenant l'annotation NativeQuery.<br>
     * Pouvant remplacer les %s par les valeurs.
     *
     * @param method methode avec l'annotation native query
     * @param values valeurs
     * @return query
     */
    Query createNativeQuery(Method method, Object... values);

    /**
     * Créer un query native, depuis le nom de l'attribut contenant l'annotation NativeQuery.<br>
     * Associer avec le class de l'attribut.<br>
     * Pouvant remplacer les %s par les valeurs.
     *
     * @param nameField nom de l'attribut avec l'annotation native query
     * @param qClass la classe contenant l'attribut
     * @param values valeurs
     * @return query
     */
    Query createNativeQuery(String nameField, Class<?> qClass, Object... values);

    /**
     * Créer une query, depuis une methode contenant l'annotation MagicQuery.<br>
     * Pouvant remplacer les `?` par les valeurs, les mots (id, table) sont des mots clefs, ne
     * peuvent être utilisé.
     *
     * @param method methode avec l'annotation native query
     * @param values valeurs
     * @return query
     */
    Query createMagicQuery(Method method, Object... values);

    /**
     * Créer une query, depuis le nom de l'attribut contenant l'annotation NativeQuery.
     * Associer avec le class de l'attribut.<br>
     * Pouvant remplacer les `?` par les valeurs.<br>
     * Les mots (id, table) sont des mots clefs, ne peuvent être utilisé.
     *
     * @param nameField nom de l'attribut avec l'annotation native query
     * @param qClass la classe contenant l'attribut
     * @param values valeurs
     * @return query
     */
    Query createMagicQuery(String nameField, Class<?> qClass, Object... values);

    
}
