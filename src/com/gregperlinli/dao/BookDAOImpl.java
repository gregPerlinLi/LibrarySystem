package com.gregperlinli.dao;

import com.gregperlinli.bean.Book;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.DelayQueue;

/**
 * @Description Implements of the interface "BookDAO"
 *              used to define the common operation of the "Book" form
 * @author gregperlinli
 */
public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {
    @Override
    public void insert(Connection conn, Book book) throws Exception {
        String sql = "insert into Book(isbm, name, category, remainNum, price, author)values(?,?,?,?,?,?)";
        update(conn, sql, book.getIsbm(), book.getName(), book.getCategory(), book.getRemainNum(), book.getPrice(), book.getAuthor());
    }

    @Override
    public void deleteById(Connection conn, int id) throws Exception {
        String sql = "delete from Book where id = ?";
        update(conn, sql, id);
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
    public Book getBookByName(Connection conn, String name) {
        String sql = "select id, isbm, name, category, remainNum, price, author from Book where name = ?";
        return getQuery(conn, sql, name);
    }

    @Override
    public List<Book> getBookByCategory(Connection conn, String category) {
        String sql = "select id, isbm, name, category, remainNum, price, author from Book where category = ?";
        return getMultiQuery(conn, sql, category);
    }

    @Override
    public List<Book> getBookByAuthor(Connection conn, String author) {
        String sql = "select id, isbm, name, category, remainNum, price, author from Book where author = ?";
        return getMultiQuery(conn, sql, author);
    }

    @Override
    public List<Book> getAll(Connection conn) {
        String sql = "select id, isbm, name, category, remainNum, price, author from Book";
        return getMultiQuery(conn, sql);
    }

    @Override
    public Long getCount(Connection conn) {
        return null;
    }
}
