package com.gregperlinli.dao;

import com.gregperlinli.bean.Book;

import java.sql.Connection;
import java.util.List;

/**
 * @Description this interface is used to define the common operation of the "Book" form
 * @author gregperlinli
 */
public interface BookDAO {
    /**
     * add object "book" to the database
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param book the Book object
     * @throws Exception e
     * @Description add object "book" to the database
     */
    void insert(Connection conn, Book book) throws Exception;

    /**
     * delete a record of the form by "id"
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param id the id of the book
     * @throws Exception e
     * @Description delete a record in the form by "id"
     */
    void deleteById(Connection conn, int id) throws Exception;

    /**
     * update the record of the form by object "book" in the memory
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param book the Book object
     * @throws Exception e
     * @Description update the record of the form by object "book" in the memory
     */
    void update(Connection conn, Book book) throws Exception;

    /**
     * query one data of the form by "id"
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param id the id of the book
     * @return Book object
     * @Description query one data of the form by "id"
     */
    Book getBookById(Connection conn, int id);

    /**
     * query one data of the form by "isbm"
     *
     * @param conn connection of database
     * @param isbm the isbm of the book
     * @return Book object
     * @Description query one data of the form by "isbm"
     */
    Book getBookByIsbm(Connection conn, int isbm);

    /**
     * get all of the Book Object in a list
     *
     * @author gregperlinli
     * @param conn connection of database
     * @return get all of the Book Object in a list
     */
    List<Book> getAll(Connection conn);

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
