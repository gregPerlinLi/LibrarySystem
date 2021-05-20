package com.gregperlinli.test;

import com.gregperlinli.bean.User;
import com.gregperlinli.dao.impl.UserDAOImpl;
import com.gregperlinli.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

/**
 * @author gregperlinli
 *
 * Test module:
 *         Connection conn = null;
 *         try {
 *             conn = JDBCUtils.getConnection();
 *
 *
 *
 *             System.out.println(" ");
 *         } catch ( Exception e ) {
 *             e.printStackTrace();
 *         } finally {
 *             JDBCUtils.closeResource(conn, null);
 *         }
 */
class UserDAOImplTest {

    private UserDAOImpl dao = new UserDAOImpl();

    @Test
    void insert() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            User user = new User(1, "XFei", 20012, "XiaoFei", "abc123");
            dao.insert(conn, user);
            System.out.println("Insert successful!!");
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }

    }

    @Test
    void deleteById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            dao.deleteById(conn, 12);

            System.out.println("Delete successful!!");
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    @Test
    void update() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            User user = new User(13, "XQiang", 20011, "XiaoQiang", "Qiang10086");

            dao.update(conn, user);

            System.out.println("Update successful!!");
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    @Test
    void getUserById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();

            User user = dao.getUserById(conn, 11);

            System.out.println(user);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    @Test
    void getUserByUserName() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            User user = dao.getUserByName(conn, "XMi");

            System.out.println(user);
        } catch ( Exception e ) {
            JDBCUtils.closeResource(conn, null);
        }
    }

    @Test
    void getAll() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            List<User> list = dao.getAll(conn);
            list.forEach(System.out::println);

            // System.out.println(" ");
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    @Test
    void getCount() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            Long cout = dao.getCount(conn);

            System.out.println("The number of the records in the form are: " + cout);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }
}