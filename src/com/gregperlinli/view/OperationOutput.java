package com.gregperlinli.view;

import com.gregperlinli.bean.Book;
import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.dao.CommonStaffDAOImpl;
import com.gregperlinli.util.EmptyUtil;
import com.gregperlinli.util.JDBCUtills;

import java.sql.Connection;
import java.util.List;

/**
 * @author gregperlinli
 */
public class OperationOutput {
    public static void queryOneOutput(Book book) {
        System.out.println("\nResult:");
        System.out.println("--------------------------------------------------------------");
        if ( EmptyUtil.isEmpty(book) ) {
            System.out.println("The book you want to query is not exist!");
        } else {
            System.out.println(book);
        }
        System.out.println("--------------------------------------------------------------");
    }

    public static void queryMultiOutput(List<Book> list, String name) {
        System.out.println("\nResult:");
        System.out.println("--------------------------------------------------------------");
        if ( list == null || list.size() == 0 ) {
            System.out.println("There is no books!");
        } else {
            list.forEach(System.out::println);
            System.out.println("\nThere are " + list.size() + " books with this " + name + ".");
        }
        System.out.println("--------------------------------------------------------------\n");
    }

    public static void queryAllOutput(List<Book> list) {
        System.out.println("\nResult:");
        System.out.println("--------------------------------------------------------------");
        if ( list == null || list.size() == 0 ) {
            System.out.println("There is no books!");
        } else {
            list.forEach(System.out::println);
            System.out.println("\nThere are " + list.size() + " books in the library.");
        }
        System.out.println("--------------------------------------------------------------\n");
    }

    public static List<CommonStaff> listStaff() {
        CommonStaffDAOImpl csDao = new CommonStaffDAOImpl();
        List<CommonStaff> list = null;
        Long countStaff = null;
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnectionWithPool();
            list = csDao.getAll(conn);
            countStaff = csDao.getCount(conn);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
        System.out.println("The staffs in the library are as fallows:");
        System.out.println("--------------------------------------------------------------");
        if (list != null) {
            list.forEach(System.out::println);
        } else {
            System.out.println("There are no staff in the library.");
        }
        System.out.println("--------------------------------------------------------------");
        System.out.println("There are " + countStaff + " staffs in the library.\n");

        return list;
    }
}
