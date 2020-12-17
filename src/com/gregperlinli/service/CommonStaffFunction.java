package com.gregperlinli.service;

import com.gregperlinli.bean.Book;
import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.User;
import com.gregperlinli.dao.BookDAOImpl;
import com.gregperlinli.util.EmptyUtil;
import com.gregperlinli.util.JDBCUtills;
import com.gregperlinli.view.ClearScreen;
import com.gregperlinli.view.CommonStaffDashboard;
import com.gregperlinli.view.CommonStaffOutput;
import com.gregperlinli.view.ResetView;
import com.mysql.cj.x.protobuf.MysqlxSession;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * @author gregperlinli
 */
public class CommonStaffFunction {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final BookDAOImpl BOOK_DAO = new BookDAOImpl();

    public static void selectQueryMode(User user, CommonStaff cs) {
        int mode = CommonStaffDashboard.queryBookView();
        //noinspection AlibabaSwitchStatement
        switch (mode) {
            case 1 -> {
                Book book = queryBookWithIsbm();
                CommonStaffOutput.queryOneOutput(book);
                selectQueryMode(user, cs);
            } case 2 -> {
                Book book = queryBookWithName();
                CommonStaffOutput.queryOneOutput(book);
                selectQueryMode(user, cs);
            } case 3 -> {
                List<Book> list = queryBookWithCategory();
                CommonStaffOutput.queryMultiOutput(list, "category");
                selectQueryMode(user, cs);
            } case 4 -> {
                List<Book> list = queryBookWithAuthor();
                CommonStaffOutput.queryMultiOutput(list, "author");
                selectQueryMode(user, cs);
            } case 5 -> {
                List<Book> list = queryAllBooks();
                CommonStaffOutput.queryAllOutput(list);
                selectQueryMode(user, cs);
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

    public static void selectUpdateMode(User user, CommonStaff cs) {
        int mode = CommonStaffDashboard.updateBookView();
        //noinspection AlibabaSwitchStatement
        switch ( mode ) {
            case 1 -> {
                addBook(user, cs);
                selectUpdateMode(user, cs);
            } case 2 -> {
                deleteBook(user, cs);
                selectUpdateMode(user, cs);
            } case 3 -> {
                selectUpdateMode(user, cs);
            } default -> {
                ResetView.resetCommonStaff(user, cs);
            }
        }
    }

    public static void addBook(User user, CommonStaff cs) {
        Book book = new Book();

        try {
            System.out.println("Please enter the ISBM:");
            // TODO: Attention to absorb the return key
            String isbm = SCAN.next();
            isbm += SCAN.nextLine();
            book.setIsbm(isbm);
            System.out.println("Please enter the name:");
            book.setName(SCAN.nextLine());
            System.out.println("Please enter the category:");
            book.setCategory(SCAN.nextLine());
            System.out.println("Please enter the remainNum:");
            book.setRemainNum(SCAN.nextInt());
            System.out.println("Please enter the author:");
            String author = SCAN.next();
            author += SCAN.nextLine();
            book.setAuthor(author);
            System.out.println("Please enter the price:");
            book.setPrice(SCAN.nextBigDecimal());

        } catch ( Exception e ) {
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The value you enter is invalid, please try again!\n");
            SCAN.nextLine();
            addBook(user, cs);

        }

        System.out.println("Inspecting the book you want to add...");
        boolean isEmpty = EmptyUtil.isAddBookEmpty(book);
        if ( isEmpty ) {
            System.out.println("\nThe book you want to add is wrong, please try again!\n");
            addBook(user, cs);
        }
        boolean isRepeat = EmptyUtil.isAddBookRepeat(book);
        if ( isRepeat ) {
            System.out.println("\nThe book you want to add is wrong, please try again!\n");
            addBook(user, cs);
        }
        System.out.println("Inspection passed, please correct the following information:");
        System.out.println(book + "\n");
        System.out.println("If correct, please enter 1. If there is something wrong, please enter 2. If you don't want to add anything, please enter another numbers.");
        int confirm;
        try {
            confirm = SCAN.nextInt();
        } catch ( Exception e ) {
            e.printStackTrace();
            confirm = 3;
            SCAN.nextLine();
        }
        if ( confirm == 2 ) {
            addBook(user, cs);
        } else if ( confirm != 1 ) {
            selectUpdateMode(user, cs);
        }
        System.out.println("Adding the book");
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnectionWithPool();
            BOOK_DAO.insert(conn, book);
            System.out.println("Adding successful, the following is the book you have insert:");
            Book bookHaveAdded = BOOK_DAO.getBookByIsbm(conn, book.getIsbm());
            System.out.println(bookHaveAdded);
        } catch ( Exception e ) {
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("Add fail, please try again");
            JDBCUtills.closeResource(conn, null);
            addBook(user, cs);
        } finally {
            JDBCUtills.closeResource(conn, null);
            selectUpdateMode(user, cs);
        }
    }

    public static void deleteBook(User user, CommonStaff cs) {
        int deleteBookId = 0;
        Connection conn = null;
        try {
            System.out.println("\nThis is all the books in the library:");
            System.out.println("-------------------------------------------------------------------------------");
            conn = JDBCUtills.getConnectionWithPool();
            List<Book> list = BOOK_DAO.getAll(conn);
            list.forEach(System.out::println);
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Please enter the ID of the book you want to delete(if you want to cancel, please enter -1):");
            deleteBookId = SCAN.nextInt();
            if ( deleteBookId == -1 ) {
                System.out.println("Delete canceled!");
                selectUpdateMode(user, cs);
            } else {
                BOOK_DAO.deleteById(conn, deleteBookId);
            }
        } catch ( InputMismatchException e ) {
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you enter is valid, please try again!");
            JDBCUtills.closeResource(conn, null);
            deleteBook(user, cs);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
        System.out.println("Delete success!");
        System.out.println("Do you want to delete other books?\n1. Yes\n2. No");
        int isRepeat = 2;
        try {
            isRepeat = SCAN.nextInt();
        } catch ( Exception e ) {
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you enter is invalid!");
            selectUpdateMode(user, cs);
        }
        if ( isRepeat == 1 ) {
            deleteBook(user, cs);
        } else {
            selectUpdateMode(user, cs);
        }
    }
}
