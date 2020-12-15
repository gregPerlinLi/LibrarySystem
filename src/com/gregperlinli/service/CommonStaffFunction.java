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
import java.util.List;
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
                System.out.println("\nResult:");
                System.out.println("--------------------------------------------------------------");
                if ( EmptyUtil.isEmpty(book) ) {
                    System.out.println("The book you want to query is not exist!");
                } else {
                    System.out.println(book);
                }
                System.out.println("--------------------------------------------------------------");
                selectMode(user, cs);
            } case 2 -> {
                Book book = queryBookWithName();
                System.out.println("\nResult:");
                System.out.println("--------------------------------------------------------------");
                if ( EmptyUtil.isEmpty(book) ) {
                    System.out.println("The book you want to query is not exist!");
                } else {
                    System.out.println(book);
                }
                System.out.println("-------------------------------------------------------------\n");
                selectMode(user, cs);
            } case 3 -> {
                List<Book> list = queryBookWithCategory();
                System.out.println("\nResult:");
                System.out.println("--------------------------------------------------------------");
                if ( list == null || list.size() == 0 ) {
                    System.out.println("There is no books!");
                } else {
                    list.forEach(System.out::println);
                    System.out.println("\nThere are " + list.size() + " books with this category.");
                }
                System.out.println("--------------------------------------------------------------\n");
                selectMode(user, cs);
            } case 4 -> {
                List<Book> list = queryBookWithAuthor();
                System.out.println("\nResult:");
                System.out.println("--------------------------------------------------------------");
                if ( list == null || list.size() == 0 ) {
                    System.out.println("There is no books!");
                } else {
                    list.forEach(System.out::println);
                    System.out.println("\nThere are " + list.size() + " books with this author.");
                }
                System.out.println("--------------------------------------------------------------\n");
                selectMode(user, cs);
            } case 5 -> {
                List<Book> list = queryAllBooks();
                System.out.println("\nResult:");
                System.out.println("--------------------------------------------------------------");
                if ( list == null || list.size() == 0 ) {
                    System.out.println("There is no books!");
                } else {
                    list.forEach(System.out::println);
                    System.out.println("\nThere are " + list.size() + " books in the library.");
                }
                System.out.println("--------------------------------------------------------------\n");
                selectMode(user, cs);
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

    public static Book queryBookWithName() {
        System.out.println("Please enter the book name: ");
        String name = SCAN.nextLine();
        Book book = null;
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnectionWithPool();
            book = BOOK_DAO.getBookByName(conn, name);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
        return book;
    }

    private static List<Book> queryBookWithCategory() {
        System.out.println("Please enter the category: ");
        String category = SCAN.nextLine();
        List<Book> list = null;
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnectionWithPool();
            list = BOOK_DAO.getBookByCategory(conn, category);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }

        return list;
    }

    private static List<Book> queryBookWithAuthor() {
        System.out.println("Please enter the author: ");
        String author = SCAN.nextLine();
        List<Book> list = null;
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnectionWithPool();
            list = BOOK_DAO.getBookByAuthor(conn, author);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }

        return list;
    }

    private static List<Book> queryAllBooks() {
        Connection conn = null;
        List<Book> list = null;
        try {
            conn = JDBCUtills.getConnectionWithPool();
            list = BOOK_DAO.getAll(conn);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }

        return list;
    }

}
