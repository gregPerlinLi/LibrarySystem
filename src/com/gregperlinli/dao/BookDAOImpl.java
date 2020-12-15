package com.gregperlinli.dao;

import com.gregperlinli.bean.Book;

import java.sql.Connection;
import java.util.List;

/**
 * @Description Implements of the interface "BookDAO"
 *              used to define the common operation of the "Book" form
 * @author gregperlinli
 */
public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {
    @Override
    public void insert(Connection conn, Book book) throws Exception {

    }

    @Override
    public void deleteById(Connection conn, int id) throws Exception {

    }

    @Override
    public void update(Connection conn, Book book) throws Exception {

    }

    @Override
    public Book getBookById(Connection conn, int id) {
        return null;
    }

    @Override
    public Book getBookByIsbm(Connection conn, String isbm) {
        String sql = "select id, isbm, name, category, remainNum, price, author from Book where isbm = ?";
        return getQuery(conn, sql, isbm);
    }

    @Override
    public List<Book> getAll(Connection conn) {
        return null;
    }

    @Override
    public Long getCount(Connection conn) {
        return null;
    }
}
