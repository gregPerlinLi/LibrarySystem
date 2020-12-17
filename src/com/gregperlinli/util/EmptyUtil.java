package com.gregperlinli.util;

import com.gregperlinli.bean.Book;
import com.gregperlinli.dao.BookDAO;
import com.gregperlinli.dao.BookDAOImpl;

import java.sql.Connection;
import java.util.List;

/**
 * @author gregperlinli
 */
public class EmptyUtil {
    /**
     * @Description judge the object is null
     * @author gregperlinli
     * @param obj object name
     * @return is null
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null)
        {
            return true;
        }
        if ((obj instanceof List))
        {
            return ((List) obj).size() == 0;
        }
        if ((obj instanceof String))
        {
            return ((String) obj).trim().equals("");
        }
        return false;
    }

    /**
     * @Description judge the object isn't null
     *
     * @param obj object name
     * @return isn't null
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isAddBookEmpty(Book book) {
        int wrongTimes = 0;
        if (book.getIsbm().isBlank()) {
            System.out.println("Warning: The ISBM you enter is empty!");
            ++wrongTimes;
        }
        if (book.getName().isBlank()) {
            System.out.println("Warning: The name you enter is empty!");
            ++wrongTimes;
        }
        if (book.getCategory().isBlank()) {
            System.out.println("Warning: The category you enter is empty!");
            ++wrongTimes;
        }
        if (book.getAuthor().isBlank()) {
            System.out.println("Warning: The author you enter is empty!");
            ++wrongTimes;
        }
        if (wrongTimes > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAddBookRepeat(Book book) {
        final BookDAOImpl BOOK_DAO = new BookDAOImpl();
        int wrongTimes = 0;
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnectionWithPool();
            Book currentBookByIsbm = BOOK_DAO.getBookByIsbm(conn, book.getIsbm());
            Book currentBookByName = BOOK_DAO.getBookByName(conn, book.getName());
            if ( isNotEmpty(currentBookByIsbm) ) {
                System.out.println("Warning: The ISBM you entered is already exist!");
                ++wrongTimes;
            }
            if ( isNotEmpty(currentBookByName) ) {
                System.out.println("Warning: The name you entered is already exist!");
                ++wrongTimes;
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
        if ( wrongTimes > 0 ) {
            return true;
        } else {
            return false;
        }
    }
}
