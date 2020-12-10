package com.gregperlinli.dao;

import com.gregperlinli.bean.User;

import java.sql.Connection;
import java.util.List;

/**
 * @Description Implements of the interface "UserDAO"
 *              used to define the common operation of the "User" form
 * @author gregperlinli
 */
public class UserDAOImpl extends BaseDAO<User> implements UserDAO {

    @Override
    public void insert(Connection conn, User user) throws Exception {
        String sql = "insert into User(account, uid, userName, password)values(?,?,?,?)";
        update(conn, sql, user.getAccount(), user.getUid(), user.getUserName(), user.getPassword());
    }

    @Override
    public void deleteById(Connection conn, int id) throws Exception {
        String sql = "delete from User where id = ?";
        update(conn, sql, id);
    }

    @Override
    public void update(Connection conn, User user) throws Exception {
        String sql = "update User set account = ?, uid = ?, userName = ?, password = ? where id = ?";
        update(conn, sql, user.getAccount(), user.getUid(), user.getUserName(), user.getPassword(), user.getId());
    }

    @Override
    public User getUserById(Connection conn, int id) {
        String sql = "select id, account, uid, userName, password from User where id = ?";
        return getQuery(conn, sql, id);
    }

    @Override
    public User getUserByAccount(Connection conn, String account) {
        String sql = "select id, account, uid, userName, password from User where account = ?";
        return getQuery(conn, sql, account);
    }

    @Override
    public List<User> getAll(Connection conn) {
        String sql = "select id, account, uid, userName, password from User";
        return getMultiQuery(conn, sql);
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from User";
        return getValue(conn, sql);
    }
}
