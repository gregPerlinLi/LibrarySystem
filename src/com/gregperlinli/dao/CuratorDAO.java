package com.gregperlinli.dao;

import com.gregperlinli.bean.Curator;

import java.sql.Connection;
import java.util.List;

/**
 * @Description this interface is used to define the common operation of the "Curator" form
 * @author gregperlinli
 */
public interface CuratorDAO {
    /**
     * add object "curator" to the database
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param curator the Curator object
     * @throws Exception e
     * @Description add object "curator" to the database
     */
    void insert(Connection conn, Curator curator) throws Exception;

    /**
     * delete a record of the form by "id"
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param id the id of the curator
     * @throws Exception e
     * @Description delete a record in the form by "id"
     */
    void deleteById(Connection conn, int id) throws Exception;

    /**
     * update the record of the form by object "curator" in the memory
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param curator the Curator object
     * @throws Exception e
     * @Description update the record of the form by object "curator" in the memory
     */
    void update(Connection conn, Curator curator) throws Exception;

    /**
     * query one data of the form by "id"
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param id the id of the curator
     * @return Curator object
     * @Description query one data of the form by "id"
     */
    Curator getUserById(Connection conn, int id);

    /**
     * get all of the Curator Object in a list
     *
     * @author gregperlinli
     * @param conn connection of database
     * @return get all of the Curator Object in a list
     */
    List<Curator> getAll(Connection conn);

    /**
     * return the number of entry in the form
     *
     * @author gregperlinli
     * @param conn connection of database
     * @return the number of entry in the database
     * @Description return the number of entry in the form
     */
    Long getCount(Connection conn);
}
