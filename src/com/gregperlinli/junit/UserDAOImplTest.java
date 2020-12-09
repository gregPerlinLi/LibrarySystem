package com.gregperlinli.junit;

import com.gregperlinli.bean.User;
import com.gregperlinli.dao.UserDAOImpl;
import com.gregperlinli.util.JDBCUtills;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author gregperlinli
 *
 * Test module:
 *         Connection conn = null;
 *         try {
 *             conn = JDBCUtills.getConnection();
 *
 *
 *
 *             System.out.println(" ");
 *         } catch ( Exception e ) {
 *             e.printStackTrace();
 *         } finally {
 *             JDBCUtills.closeResource(conn, null);
 *         }
 */
class UserDAOImplTest {

    private UserDAOImpl dao = new UserDAOImpl();

    @Test
    void insert() {
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnection();
            User user = new User(1, "XFei", 20012, "XiaoFei", "abc123");
            dao.insert(conn, user);
            System.out.println("Insert successful!!");
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }

    }

    @Test
    void deleteById() {
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnection();

            dao.deleteById(conn, 12);

            System.out.println("Delete successful!!");
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
    }

    @Test
    void update() {
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnection();
            User user = new User(13, "XQiang", 20011, "XiaoQiang", "Qiang10086");

            dao.update(conn, user);

            System.out.println("Update successful!!");
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
    }

    @Test
    void getUserById() {
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnection();

            User user = dao.getUserById(conn, 11);

            System.out.println(user);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
    }

    @Test
    void getAll() {
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnection();

            List<User> list = dao.getAll(conn);
            list.forEach(System.out::println);

            // System.out.println(" ");
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
    }

    @Test
    void getCount() {
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnection();

            Long cout = dao.getCount(conn);

            System.out.println("The number of the records in the form are: " + cout);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
    }
}