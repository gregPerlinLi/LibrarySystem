package com.gregperlinli.service;

import com.gregperlinli.bean.Book;
import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.User;
import com.gregperlinli.dao.impl.BookDAOImpl;
import com.gregperlinli.utils.EmptyUtils;
import com.gregperlinli.utils.JDBCUtils;
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
                Book book = GenericFunction.queryBookWithIsbn();
                OperationOutput.queryOneBookOutput(book);
                selectQueryMode(user, cs);
            } case 2 -> {
                Book book = GenericFunction.queryBookWithName();
                OperationOutput.queryOneBookOutput(book);
                selectQueryMode(user, cs);
            } case 3 -> {
                List<Book> list = GenericFunction.queryBookWithCategory();
                OperationOutput.queryMultiBooksOutput(list, "category");
                selectQueryMode(user, cs);
            } case 4 -> {
                List<Book> list = GenericFunction.queryBookWithAuthor();
                OperationOutput.queryMultiBooksOutput(list, "author");
                selectQueryMode(user, cs);
            } case 5 -> {
                List<Book> list = GenericFunction.queryAllBooks();
                OperationOutput.queryAllBooksOutput(list);
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
        boolean isEmpty = EmptyUtils.isAddBookEmpty(book);
        if ( isEmpty ) {
            System.out.println("\nThe book you want to add is wrong, please try again!\n");
            addBook(user, cs);
        }
        boolean isRepeat = EmptyUtils.isAddBookRepeat(book);
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
            conn = JDBCUtils.getConnectionWithPool();
            BOOK_DAO.insert(conn, book);
            System.out.println("Adding successful, the following is the book you have insert:");
            Book bookHaveAdded = BOOK_DAO.getBookByIsbn(conn, book.getIsbn());
            System.out.println(bookHaveAdded);
        } catch ( Exception e ) {
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("Add fail, please try again");
            JDBCUtils.closeResource(conn, null);
            addBook(user, cs);
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    public static void deleteBook(User user, CommonStaff cs) {
        int deleteBookId;
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            deleteBookId = GenericFunction.enterDeleteBookData(conn);
            if ( deleteBookId == -1 ) {
                System.out.println("Delete canceled!");
                selectUpdateMode(user, cs);
            }
            BOOK_DAO.deleteById(conn, deleteBookId);
        } catch ( InputMismatchException e ) {
            SCAN.nextLine();
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you enter is invalid, please try again!");
            JDBCUtils.closeResource(conn, null);
            deleteBook(user, cs);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
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
            conn = JDBCUtils.getConnectionWithPool();
            List<Book> list = BOOK_DAO.getAll(conn);
            list.forEach(System.out::println);
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Please enter the ID of the book you want to update(if you want to cancel, please enter -1):");
            updateBookId = SCAN.nextInt();
            SCAN.nextLine();
            if ( updateBookId == -1 ) {
                System.out.println("Update canceled!");
                selectUpdateMode(user, cs);
            }
            for (Book book : list) {
                if (book.getId() == updateBookId) {
                    boolean isNotIsbn, isNotName;
                    boolean[] isNotList = GenericFunction.inputUpdateData(book);
                    isNotIsbn = isNotList[0];
                    isNotName = isNotList[1];
                    // check
                    System.out.println("Inspecting the data you entered...");
                    boolean isRepeat = EmptyUtils.isUpdateBookRepeat(conn, book, isNotIsbn, isNotName);
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

                    BOOK_DAO.update(conn, book);
                    System.out.println("Updating successful, the following is the book you have insert:");
                    Book bookHaveUpdated = BOOK_DAO.getBookByIsbn(conn, book.getIsbn());
                    System.out.println(bookHaveUpdated);
                }
            }
        } catch ( InputMismatchException e ) {
            SCAN.nextLine();
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you enter is valid, please try again!");
            JDBCUtils.closeResource(conn, null);
            updateBook(user, cs);
        } catch ( Exception e ) {
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("Update fail, please try again");
            JDBCUtils.closeResource(conn, null);
            updateBook(user, cs);
        } finally {
            JDBCUtils.closeResource(conn, null);
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
                lendBookByIsbn(user, cs);
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
            conn = JDBCUtils.getConnectionWithPool();
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
            JDBCUtils.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("Lend fail, please try again!");
            lendBookById(user, cs);
        } finally {
            JDBCUtils.closeResource(conn, null);
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

    public static void lendBookByIsbn(User user, CommonStaff cs) {
        String lendBookIsbn;
        Connection conn = null;
        Book book;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            System.out.println("Please enter the ISBN:(if you want to cancel, please enter -1)");
            lendBookIsbn = SCAN.nextLine();
            book = BOOK_DAO.getBookByIsbn(conn, lendBookIsbn);
            if ( "-1".equals(lendBookIsbn) ) {
                System.out.println("Lend canceled!");
                selectLendBookMode(user, cs);
            } else {
                if ( GenericFunction.isEnoughBook(book) ) {
                    BOOK_DAO.lendByIsbn(conn, book, lendBookIsbn);
                } else {
                    selectLendBookMode(user, cs);
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            JDBCUtils.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("The ISBN you enter is not exist, please try again!");
            selectLendBookMode(user, cs);
        } finally {
            JDBCUtils.closeResource(conn, null);
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
            lendBookByIsbn(user, cs);
        } else {
            selectLendBookMode(user, cs);
        }
    }

    public static void lendBookByName(User user, CommonStaff cs) {
        String lendBookName;
        Connection conn = null;
        Book book;
        try {
            conn = JDBCUtils.getConnectionWithPool();
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
            JDBCUtils.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("The name you enter is not exist, please try again!");
            selectLendBookMode(user, cs);e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
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
                returnBookByIsbn(user, cs);
                selectReturnBookMode(user, cs);
            } case 3 -> {
                returnBookByName(user, cs);
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
            conn = JDBCUtils.getConnectionWithPool();
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
                        BOOK_DAO.returnById(conn, book, returnBookId);
                    }
                }
            }
        } catch ( Exception e ) {
            SCAN.nextLine();
            e.printStackTrace();
            JDBCUtils.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("Return fail, please try again!");
            returnBookById(user, cs);
        } finally {
            JDBCUtils.closeResource(conn, null);
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

    public static void returnBookByIsbn(User user, CommonStaff cs) {
        String returnBookIsbn;
        Connection conn = null;
        Book book;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            System.out.println("Please enter the ISBN:(if you want to cancel, please enter -1)");
            returnBookIsbn = SCAN.nextLine();
            book = BOOK_DAO.getBookByIsbn(conn, returnBookIsbn);
            if ( "-1".equals(returnBookIsbn) ) {
                System.out.println("Return canceled!");
                selectReturnBookMode(user, cs);
            } else {
                    BOOK_DAO.returnByIsbn(conn, book, returnBookIsbn);
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            JDBCUtils.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("The ISBN you enter is not exist, please try again!");
            selectReturnBookMode(user, cs);
        } finally {
            JDBCUtils.closeResource(conn, null);
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
            returnBookByIsbn(user, cs);
        } else {
            selectReturnBookMode(user, cs);
        }
    }

    public static void returnBookByName(User user, CommonStaff cs) {
        String returnBookName;
        Connection conn = null;
        Book book;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            System.out.println("Please enter the name:(if you want to cancel, please enter -1)");
            returnBookName = SCAN.nextLine();
            book = BOOK_DAO.getBookByName(conn, returnBookName);
            if ( "-1".equals(returnBookName) ) {
                System.out.println("Return canceled!");
                selectReturnBookMode(user, cs);
            } else {
                if ( GenericFunction.isEnoughBook(book) ) {
                    BOOK_DAO.returnByName(conn, book, returnBookName);
                } else {
                    selectReturnBookMode(user, cs);
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            JDBCUtils.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("The name you enter is not exist, please try again!");
            selectReturnBookMode(user, cs);e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
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
            returnBookByName(user, cs);
        } else {
            selectReturnBookMode(user, cs);
        }
    }

}
