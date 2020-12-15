package com.gregperlinli.service;

import com.gregperlinli.bean.Book;
import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.User;
import com.gregperlinli.dao.BookDAOImpl;
import com.gregperlinli.util.EmptyUtil;
import com.gregperlinli.util.JDBCUtills;
import com.gregperlinli.view.CommonStaffDashboard;
import com.gregperlinli.view.ResetView;

import java.sql.Connection;
import java.util.Scanner;

/**
 * @author gregperlinli
 */
public class CommonStaffFunction {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final BookDAOImpl BOOK_DAO = new BookDAOImpl();

    public static void selectMode(User user, CommonStaff cs) {
        int mode = CommonStaffDashboard.queryBookView();
        //noinspection AlibabaSwitchStatement
        switch (mode) {
            case 1 -> {
                Book book = queryBookWithIsbm();
                System.out.println("Result:");
                if ( EmptyUtil.isEmpty(book) ) {
                    System.out.println("The book you want to query is not exist!\n");
                } else {
                    System.out.println(book + "\n");
                }
                selectMode(user, cs);
            } case 2 -> {

            } default -> {
                ResetView.resetCommonStaff(user, cs);
            }
        }
    }

    public static Book queryBookWithIsbm() {
        System.out.println("Please enter the ISBM: ");
        String isbm = SCAN.nextLine();
        Book book = null;
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnectionWithPool();
            book = BOOK_DAO.getBookByIsbm(conn, isbm);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
        return book;
    }
}
