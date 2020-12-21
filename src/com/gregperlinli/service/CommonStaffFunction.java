package com.gregperlinli.service;

import com.gregperlinli.bean.Book;
import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.User;
import com.gregperlinli.dao.BookDAOImpl;
import com.gregperlinli.util.EmptyUtil;
import com.gregperlinli.util.JDBCUtills;
import com.gregperlinli.view.*;

import java.sql.Connection;
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
                Book book = GenericFunction.queryBookWithIsbm();
                CommonStaffOutput.queryOneOutput(book);
                selectQueryMode(user, cs);
            } case 2 -> {
                Book book = GenericFunction.queryBookWithName();
                CommonStaffOutput.queryOneOutput(book);
                selectQueryMode(user, cs);
            } case 3 -> {
                List<Book> list = GenericFunction.queryBookWithCategory();
                CommonStaffOutput.queryMultiOutput(list, "category");
                selectQueryMode(user, cs);
            } case 4 -> {
                List<Book> list = GenericFunction.queryBookWithAuthor();
                CommonStaffOutput.queryMultiOutput(list, "author");
                selectQueryMode(user, cs);
            } case 5 -> {
                List<Book> list = GenericFunction.queryAllBooks();
                CommonStaffOutput.queryAllOutput(list);
                selectQueryMode(user, cs);
            } default -> {
                ResetView.resetCommonStaff(user, cs);
            }
        }
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
                updateBook(user, cs);
                selectUpdateMode(user, cs);
            } default -> {
                ResetView.resetCommonStaff(user, cs);
            }
        }
    }

    public static void addBook(User user, CommonStaff cs) {
        Book book = new Book();

        try {
            GenericFunction.enterNewBookData(book);
        } catch ( Exception e ) {
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The value you enter is invalid, please try again!\n");
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
            SCAN.nextLine();
        } catch ( Exception e ) {
            SCAN.nextLine();
            e.printStackTrace();
            confirm = 3;
        }
        if ( confirm == 2 ) {
            addBook(user, cs);
        } else if ( confirm != 1 ) {
            selectUpdateMode(user, cs);
        }
        System.out.println("Adding the book...");
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
        int deleteBookId;
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnectionWithPool();
            deleteBookId = GenericFunction.enterDeleteBookData(conn);
            if ( deleteBookId == -1 ) {
                System.out.println("Delete canceled!");
                selectUpdateMode(user, cs);
            } else {
                BOOK_DAO.deleteById(conn, deleteBookId);
            }
        } catch ( InputMismatchException e ) {
            SCAN.nextLine();
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
            SCAN.nextLine();
        } catch ( Exception e ) {
            SCAN.nextLine();
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

    public static void updateBook(User user, CommonStaff cs) {
        int updateBookId;
        Connection conn = null;
        try {
            System.out.println("\nThis is all the books in the library:");
            System.out.println("-------------------------------------------------------------------------------");
            conn = JDBCUtills.getConnectionWithPool();
            List<Book> list = BOOK_DAO.getAll(conn);
            list.forEach(System.out::println);
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Please enter the ID of the book you want to update(if you want to cancel, please enter -1):");
            updateBookId = SCAN.nextInt();
            SCAN.nextLine();
            if ( updateBookId == -1 ) {
                System.out.println("Update canceled!");
                selectUpdateMode(user, cs);
            } else {
                for (Book book : list) {
                    if (book.getId() == updateBookId) {
                        boolean isNotIsbm, isNotName;

                        boolean[] isNotList = GenericFunction.inputUpdateData(book);
                        isNotIsbm = isNotList[0];
                        isNotName = isNotList[1];

                        // check
                        System.out.println("Inspecting the data you entered...");
                        boolean isRepeat = EmptyUtil.isUpdateBookRepeat(book, isNotIsbm, isNotName);
                        if (isRepeat) {
                            System.out.println("\nThe book you want to update is wrong, please try again!\n");
                            updateBook(user, cs);
                        }
                        System.out.println("Inspection passed, please correct the following information:");
                        System.out.println(book);
                        System.out.println("If correct, please enter 1. If there is something wrong, please enter 2. If you don't want to add anything, please enter another numbers.");
                        int confirm;
                        try {
                            confirm = SCAN.nextInt();
                            SCAN.nextLine();
                        } catch (Exception e) {
                            SCAN.nextLine();
                            e.printStackTrace();
                            confirm = 3;
                        }
                        if (confirm == 2) {
                            updateBook(user, cs);
                        } else if (confirm != 1) {
                            selectUpdateMode(user, cs);
                        }
                        System.out.println("Updating the book...");

                        try {
                            conn = JDBCUtills.getConnectionWithPool();
                            BOOK_DAO.update(conn, book);
                            System.out.println("Updating successful, the following is the book you have insert:");
                            Book bookHaveUpdated = BOOK_DAO.getBookByIsbm(conn, book.getIsbm());
                            System.out.println(bookHaveUpdated);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ClearScreen.clear();
                            System.out.println("Update fail, please try again");
                            JDBCUtills.closeResource(conn, null);
                            updateBook(user, cs);
                        } finally {
                            JDBCUtills.closeResource(conn, null);
                            selectUpdateMode(user, cs);
                        }
                    }
                }
            }
        } catch ( InputMismatchException e ) {
            SCAN.nextLine();
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you enter is valid, please try again!");
            JDBCUtills.closeResource(conn, null);
            updateBook(user, cs);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }

        System.out.println("Update success!");
        System.out.println("Do you want to update other books?\n1. Yes\n2. No");
        int isRepeat = 2;
        try {
            isRepeat = SCAN.nextInt();
            SCAN.nextLine();
        } catch ( Exception e ) {
            SCAN.nextLine();
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you enter is invalid!");
            selectUpdateMode(user, cs);
        }
        if ( isRepeat == 1 ) {
            updateBook(user, cs);
        } else {
            selectUpdateMode(user, cs);
        }
    }

    public static void selectLendBookMode(User user, CommonStaff cs) {
        int mode = CommonStaffDashboard.lendBookView();
        //noinspection AlibabaSwitchStatement
        switch ( mode ) {
            case 1 -> {
                lendBookById(user, cs);
                selectLendBookMode(user, cs);
            } case 2 -> {
                lendBookByIsbm(user, cs);
                selectLendBookMode(user, cs);
            } case 3 -> {
                lendBookByName(user, cs);
                selectLendBookMode(user, cs);
            } default -> {
                ResetView.resetCommonStaff(user, cs);
            }
        }
    }

    public static void lendBookById(User user, CommonStaff cs) {
        int lendBookId;
        Connection conn = null;
        try {
            System.out.println("\nThis is all the books in the library:");
            System.out.println("-------------------------------------------------------------------------------");
            conn = JDBCUtills.getConnectionWithPool();
            List<Book> list = BOOK_DAO.getAll(conn);
            list.forEach(System.out::println);
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Please enter the ID of the book you want to lend(if you want to cancel, please enter -1):");
            lendBookId = SCAN.nextInt();
            SCAN.nextLine();
            if (lendBookId == -1) {
                System.out.println("Lend canceled!");
                selectLendBookMode(user, cs);
            } else {
                for ( Book book : list ) {
                    if (book.getId() == lendBookId) {
                        if ( GenericFunction.isEnoughBook(book) ) {
                            BOOK_DAO.lendById(conn, book, lendBookId);
                        } else {
                            selectLendBookMode(user, cs);
                        }
                    }
                }
            }
        } catch ( Exception e ) {
            SCAN.nextLine();
            e.printStackTrace();
            JDBCUtills.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("Lend fail, please try again!");
            lendBookById(user, cs);
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
        System.out.println("Lend successful!");
        System.out.println("Do you want to lend other books?\n1. Yes\n2. No");
        int isRepeat = 2;
        try {
            isRepeat = SCAN.nextInt();
            SCAN.nextLine();
        } catch ( Exception e ) {
            SCAN.nextLine();
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you enter is invalid!");
            selectLendBookMode(user, cs);
        }
        if ( isRepeat == 1 ) {
            lendBookById(user, cs);
        } else {
            selectLendBookMode(user, cs);
        }
    }

    public static void lendBookByIsbm(User user, CommonStaff cs) {
        String lendBookIsbm;
        Connection conn = null;
        Book book;
        try {
            conn = JDBCUtills.getConnectionWithPool();
            System.out.println("Please enter the ISBM:(if you want to cancel, please enter -1)");
            lendBookIsbm = SCAN.nextLine();
            book = BOOK_DAO.getBookByIsbm(conn, lendBookIsbm);
            if ( "-1".equals(lendBookIsbm) ) {
                System.out.println("Lend canceled!");
                selectLendBookMode(user, cs);
            } else {
                if ( GenericFunction.isEnoughBook(book) ) {
                    BOOK_DAO.lendByIsbm(conn, book, lendBookIsbm);
                } else {
                    selectLendBookMode(user, cs);
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            JDBCUtills.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("The ISBM you enter is not exist, please try again!");
            selectLendBookMode(user, cs);
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
        System.out.println("Lend successful!");
        System.out.println("Do you want to lend other books?\n1. Yes\n2. No");
        int isRepeat = 2;
        try {
            isRepeat = SCAN.nextInt();
            SCAN.nextLine();
        } catch ( Exception e ) {
            SCAN.nextLine();
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you enter is invalid!");
            selectLendBookMode(user, cs);
        }
        if ( isRepeat == 1 ) {
            lendBookByIsbm(user, cs);
        } else {
            selectLendBookMode(user, cs);
        }
    }

    public static void lendBookByName(User user, CommonStaff cs) {
        String lendBookName;
        Connection conn = null;
        Book book;
        try {
            conn = JDBCUtills.getConnectionWithPool();
            System.out.println("Please enter the name:(if you want to cancel, please enter -1)");
            lendBookName = SCAN.nextLine();
            book = BOOK_DAO.getBookByName(conn, lendBookName);
            if ( "-1".equals(lendBookName) ) {
                System.out.println("Lend canceled!");
                selectLendBookMode(user, cs);
            } else {
                if ( GenericFunction.isEnoughBook(book) ) {
                    BOOK_DAO.lendByName(conn, book, lendBookName);
                } else {
                    selectLendBookMode(user, cs);
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            JDBCUtills.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("The name you enter is not exist, please try again!");
            selectLendBookMode(user, cs);e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
        System.out.println("Lend successful!");
        System.out.println("Do you want to lend other books?\n1. Yes\n2. No");
        int isRepeat = 2;
        try {
            isRepeat = SCAN.nextInt();
            SCAN.nextLine();
        } catch ( Exception e ) {
            SCAN.nextLine();
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you enter is invalid!");
            selectLendBookMode(user, cs);
        }
        if ( isRepeat == 1 ) {
            lendBookByName(user, cs);
        } else {
            selectLendBookMode(user, cs);
        }
    }


    public static void selectReturnBookMode(User user, CommonStaff cs) {
        int mode = CommonStaffDashboard.returnBookView();
        //noinspection AlibabaSwitchStatement
        switch ( mode ) {
            case 1 -> {
                returnBookById(user, cs);
                selectReturnBookMode(user, cs);
            } case 2 -> {
                lendBookByIsbm(user, cs);
                selectReturnBookMode(user, cs);
            } case 3 -> {
                lendBookByName(user, cs);
                selectReturnBookMode(user, cs);
            } default -> {
                ResetView.resetCommonStaff(user, cs);
            }
        }
    }

    public static void returnBookById(User user, CommonStaff cs) {
        int returnBookId;
        Connection conn = null;
        try {
            System.out.println("\nThis is all the books in the library:");
            System.out.println("-------------------------------------------------------------------------------");
            conn = JDBCUtills.getConnectionWithPool();
            List<Book> list = BOOK_DAO.getAll(conn);
            list.forEach(System.out::println);
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Please enter the ID of the book you want to return(if you want to cancel, please enter -1):");
            returnBookId = SCAN.nextInt();
            SCAN.nextLine();
            if (returnBookId == -1) {
                System.out.println("Return canceled!");
                selectReturnBookMode(user, cs);
            } else {
                for ( Book book : list ) {
                    if (book.getId() == returnBookId) {
                        if ( GenericFunction.isEnoughBook(book) ) {
                            BOOK_DAO.returnById(conn, book, returnBookId);
                        } else {
                            selectReturnBookMode(user, cs);
                        }
                    }
                }
            }
        } catch ( Exception e ) {
            SCAN.nextLine();
            e.printStackTrace();
            JDBCUtills.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("Return fail, please try again!");
            returnBookById(user, cs);
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
        System.out.println("Return successful!");
        System.out.println("Do you want to return other books?\n1. Yes\n2. No");
        int isRepeat = 2;
        try {
            isRepeat = SCAN.nextInt();
            SCAN.nextLine();
        } catch ( Exception e ) {
            SCAN.nextLine();
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you enter is invalid!");
            selectReturnBookMode(user, cs);
        }
        if ( isRepeat == 1 ) {
            returnBookById(user, cs);
        } else {
            selectReturnBookMode(user, cs);
        }
    }
}
