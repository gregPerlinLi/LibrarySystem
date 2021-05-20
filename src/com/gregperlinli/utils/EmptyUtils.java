package com.gregperlinli.utils;

import com.gregperlinli.bean.Book;
import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.User;
import com.gregperlinli.dao.impl.BookDAOImpl;
import com.gregperlinli.dao.impl.CommonStaffDAOImpl;
import com.gregperlinli.dao.impl.UserDAOImpl;

import java.sql.Connection;
import java.util.List;

/**
 * @author gregperlinli
 */
public class EmptyUtils {
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
            return "".equals(((String) obj).trim());
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
        if (book.getIsbn().isBlank()) {
            System.out.println("Warning: The ISBN you enter is empty!");
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
        return wrongTimes > 0;
    }

    public static boolean isAddBookRepeat(Book book) {
        final BookDAOImpl BOOK_DAO = new BookDAOImpl();
        int wrongTimes = 0;
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            Book currentBookByIsbn = BOOK_DAO.getBookByIsbn(conn, book.getIsbn());
            Book currentBookByName = BOOK_DAO.getBookByName(conn, book.getName());
            if ( isNotEmpty(currentBookByIsbn) ) {
                System.out.println("Warning: The ISBN you entered is already exist!");
                ++wrongTimes;
            }
            if ( isNotEmpty(currentBookByName) ) {
                System.out.println("Warning: The name you entered is already exist!");
                ++wrongTimes;
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
        return wrongTimes > 0;
    }

    public static boolean isUpdateBookRepeat(Connection conn, Book book, boolean isNotIsbn, boolean isNotName ) throws Exception {
        final BookDAOImpl BOOK_DAO = new BookDAOImpl();
        int wrongTimes = 0;

        conn = JDBCUtils.getConnectionWithPool();

        if ( !isNotIsbn ) {
            Book currentBook = BOOK_DAO.getBookByIsbn(conn, book.getIsbn());
            if ( EmptyUtils.isNotEmpty(currentBook) ) {
                System.out.println("Warning: The ISBN you entered is already exist!");
                ++wrongTimes;
            }
        }
        if ( !isNotName ) {
            Book currentBook = BOOK_DAO.getBookByName(conn, book.getName());
            if ( EmptyUtils.isNotEmpty(currentBook)) {
                System.out.println("Warning: The name you entered is already exist!");
                ++wrongTimes;
            }
        }

        return wrongTimes != 0;
    }

    public static boolean isStaffEmpty(User newUser, CommonStaff newCs) {
        int wrongTimes = 0;
        if  (newUser.getAccount().isBlank()) {
            System.out.println("Warning: The account you entered is empty!");
            ++wrongTimes;
        }
        if (newUser.getUid() == 0 || newCs.getUid() == 0) {
            System.out.println("Warning: The UID you entered is empty!");
            ++wrongTimes;
        }
        if (newUser.getUserName().isBlank() || newCs.getStaffName().isBlank()) {
            System.out.println("WarningL The name you entered is empty!");
            ++wrongTimes;
        }
        if (newUser.getPassword().isBlank()) {
            System.out.println("The password you entered is empty!");
            ++wrongTimes;
        }

        return wrongTimes != 0;
    }

    public static boolean isAddStaffRepeat(User newUser, CommonStaff newCs) {
        final UserDAOImpl USER_DAO = new UserDAOImpl();
        final CommonStaffDAOImpl COMMON_STAFF_DAO = new CommonStaffDAOImpl();
        int wrongTimes = 0;
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();

            User currentUserByAccount = USER_DAO.getUserByAccount(conn, newUser.getAccount());
            User currentUserByName = USER_DAO.getUserByName(conn, newUser.getUserName());
            User currentUserByUid = USER_DAO.getUserByUid(conn, newUser.getUid());

            CommonStaff currentCsByName = COMMON_STAFF_DAO.getCommonStaffByName(conn, newCs.getStaffName());
            CommonStaff currentCsByUid = COMMON_STAFF_DAO.getCommonStaffByUid(conn, newCs.getUid());

            if ( isNotEmpty(currentUserByAccount) ) {
                System.out.println("Warning: The account you entered is already exist!");
                ++wrongTimes;
            }
            if ( isNotEmpty(currentUserByName) || isNotEmpty(currentCsByName) ) {
                System.out.println("Warning: The name you entered is already exist!");
                ++wrongTimes;
            }
            if ( isNotEmpty(currentUserByUid) || isNotEmpty(currentCsByUid) ) {
                System.out.println("Warning: The UID you entered is already exist!");
                ++wrongTimes;
            }
            
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
        return wrongTimes > 0;
    }

    public static boolean isUpdateStaffRepeat(Connection conn, User staffUser, CommonStaff cs, boolean isNotName, boolean isNotUid, boolean isNotAccount) throws Exception {
        final UserDAOImpl USER_DAO = new UserDAOImpl();
        final CommonStaffDAOImpl COMMON_STAFF_DAO = new CommonStaffDAOImpl();
        int wrongTimes = 0;

        if (!isNotName) {
            User currentUser = USER_DAO.getUserByName(conn, staffUser.getUserName());
            CommonStaff currentStaff = COMMON_STAFF_DAO.getCommonStaffByName(conn, cs.getStaffName());
            if (EmptyUtils.isNotEmpty(currentUser) || EmptyUtils.isNotEmpty(currentStaff)) {
                System.out.println("Warning: The name you entered is already exist!");
                ++wrongTimes;
            }
        }
        if (!isNotUid) {
            User currentUser = USER_DAO.getUserByUid(conn, staffUser.getUid());
            CommonStaff currentStaff = COMMON_STAFF_DAO.getCommonStaffByUid(conn, cs.getUid());
            if (EmptyUtils.isNotEmpty(currentUser) || EmptyUtils.isNotEmpty(currentStaff)) {
                System.out.println("Warning: The UID you entered is already exist!");
                ++wrongTimes;
            }
        }
        if (!isNotAccount) {
            User currentUser = USER_DAO.getUserByAccount(conn, staffUser.getAccount());
            if (EmptyUtils.isNotEmpty(currentUser)) {
                System.out.println("Warning! The account you entered is already exist!");
                ++wrongTimes;
            }
        }

        return wrongTimes != 0;
    }
}
