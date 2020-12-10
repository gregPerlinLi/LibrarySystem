package com.gregperlinli.dao;

import com.gregperlinli.bean.User;

import java.sql.Connection;
import java.util.List;

/**
 * @Description this interface is used to define the common operation of the "User" form
 * @author gregperlinli
 */
public interface UserDAO {
    /**
     * add object "user" to the database
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param user the User object
     * @throws Exception e
     * @Description add object "user" to the database
     */
    void insert(Connection conn, User user) throws Exception;

    /**
     * delete a record of the form by "id"
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param id the id of the user
     * @throws Exception e
     * @Description delete a record in the form by "id"
     */
    void deleteById(Connection conn, int id) throws Exception;

    /**
     * update the record of the form by object "user" in the memory
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param user the User object
     * @throws Exception e
     * @Description update the record of the form by object "user" in the memory
     */
    void update(Connection conn, User user) throws Exception;

    /**
     * query one data of the form by "id"
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param id the id of the user
     * @return User object
     * @Description query one data of the form by "id"
     */
    User getUserById(Connection conn, int id);

    /**
     * query one data of the form by "userName"
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param userName the userName of the user
     * @return User object
     * @Description query one data of the form by "userName"
     */
    User getUserByAccount(Connection conn, String userName);

    /**
     * get all of the User Object in a list
     *
     * @author gregperlinli
     * @param conn connection of database
     * @return get all of the User Object in a list
     */
    List<User> getAll(Connection conn);

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
