package com.gregperlinli.service;

import com.gregperlinli.bean.Book;
import com.gregperlinli.bean.User;
import com.gregperlinli.dao.impl.BookDAOImpl;
import com.gregperlinli.dao.UserDAO;
import com.gregperlinli.dao.impl.UserDAOImpl;
import com.gregperlinli.utils.JDBCUtils;
import com.gregperlinli.utils.MD5Encrypt;
import com.gregperlinli.view.ClearScreen;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author gregperlinli
 */
public class GenericFunction {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final BookDAOImpl BOOK_DAO = new BookDAOImpl();

    public static Book queryBookWithIsbn() {
        System.out.println("Please enter the ISBN: ");
        String isbn = SCAN.nextLine();
        Book book = null;
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            book = BOOK_DAO.getBookByIsbn(conn, isbn);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
        return book;
    }

    public static Book queryBookWithName() {
        System.out.println("Please enter the book name: ");
        String name = SCAN.nextLine();
        Book book = null;
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            book = BOOK_DAO.getBookByName(conn, name);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
        return book;
    }

    public static List<Book> queryBookWithCategory() {
        System.out.println("Please enter the category: ");
        String category = SCAN.nextLine();
        List<Book> list = null;
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            list = BOOK_DAO.getBookByCategory(conn, category);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }

        return list;
    }

    public static List<Book> queryBookWithAuthor() {
        System.out.println("Please enter the author: ");
        String author = SCAN.nextLine();
        List<Book> list = null;
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            list = BOOK_DAO.getBookByAuthor(conn, author);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }

        return list;
    }

    public static List<Book> queryAllBooks() {
        Connection conn = null;
        List<Book> list = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            list = BOOK_DAO.getAll(conn);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
                                      
        return list;
    }

    public static void enterNewBookData(Book book) throws Exception {

        System.out.println("Please enter the ISBN:");
        // TODO: Attention to absorb the return key
        String isbn = SCAN.next();
        isbn += SCAN.nextLine();
        book.setIsbn(isbn);
        System.out.println("Please enter the name:");
        book.setName(SCAN.nextLine());
        System.out.println("Please enter the category:");
        book.setCategory(SCAN.nextLine());
        System.out.println("Please enter the remainNum:");
        book.setRemainNum(SCAN.nextInt());
        SCAN.nextLine();
        System.out.println("Please enter the author:");
        String author = SCAN.nextLine();
        book.setAuthor(author);
        System.out.println("Please enter the price:");
        book.setPrice(SCAN.nextBigDecimal());
        SCAN.nextLine();
    }

    public static int enterDeleteBookData(Connection conn) throws Exception {
        System.out.println("\nThis is all the books in the library:");
        System.out.println("-------------------------------------------------------------------------------");
        List<Book> list = BOOK_DAO.getAll(conn);
        list.forEach(System.out::println);
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Please enter the ID of the book you want to delete(if you want to cancel, please enter -1):");
        int deleteBookId = SCAN.nextInt();
        SCAN.nextLine();
        return deleteBookId;
    }

    public static boolean[] inputUpdateData(Book book) throws Exception {
        System.out.println("The book you select is follow: ");
        System.out.println(book + "\n");

        boolean[] isNotList = new boolean[2];
        // 0:isNotIsbn, 1:isNotName
        isNotList[0] = false;
        isNotList[1] = false;

        System.out.println("Please enter new ISBN (If you don't want to change, please empty):");
        String newIsbn = SCAN.nextLine();
        if (newIsbn.isBlank()) {
            isNotList[0] = true;
        } else {
            book.setIsbn(newIsbn);
        }
        System.out.println("Please enter new name (If you don't want to change, please empty):");
        String newName = SCAN.nextLine();
        if (newName.isBlank()) {
            isNotList[1] = true;
        } else {
            book.setName(newName);
        }
        System.out.println("Please enter new category (If you don't want to change, please empty):");
        String newCategory = SCAN.nextLine();
        if ( !newCategory.isBlank() ) {
            book.setCategory(newCategory);
        }
        System.out.println("Please enter new remainNum (If you don't want to change, please empty):");
        String newRemainNum = SCAN.nextLine();
        if ( !newRemainNum.isBlank() ) {
            book.setRemainNum(Integer.parseInt(newRemainNum));
        }
        System.out.println("Please enter new price (If you don't want to change, please empty:");
        String newPrice = SCAN.nextLine();
        if ( !newPrice.isBlank() ) {
            BigDecimal newPriceByDec = new BigDecimal(newPrice);
            newPriceByDec = newPriceByDec.setScale(2, RoundingMode.HALF_UP);
            book.setPrice(newPriceByDec);
        }
        System.out.println("Please enter new author (If you don't want to change, please empty):");
        String newAuthor = SCAN.nextLine();
        if ( !newAuthor.isBlank() ) {
            book.setAuthor(newAuthor);
        }

        return isNotList;
    }

    public static boolean isEnoughBook(Book book) {
        if ( book.getRemainNum() <= 0 ) {
            System.out.println("The book you want to lend is empty, please try again in a later time!");
            return false;
        } else {
            return true;
        }
    }

    public static void changePassword(User user) {
        ClearScreen.clear();
        System.out.println("Please enter the current password:");
        String curPassword = SCAN.nextLine();
        if (Objects.equals(MD5Encrypt.stringMD5(curPassword), user.getPassword())) {
            Connection conn = null;
            boolean isPasswordCorrect = false;
            try {
                conn = JDBCUtils.getConnectionWithPool();
                do {
                    System.out.println("Please enter the new password:");
                    String newFirPassword = SCAN.nextLine();
                    System.out.println("Please confirm you password again:");
                    String newSecPassword = SCAN.nextLine();
                    if ( !newFirPassword.equals(newSecPassword) ) {
                        System.out.println("The two password you entered are incorrect, please try again!");
                    }
                    if ( newFirPassword.equals(newSecPassword) ) {
                        isPasswordCorrect = true;
                        user.setPassword(MD5Encrypt.stringMD5(newFirPassword));
                        UserDAO userDAO = new UserDAOImpl();
                        userDAO.update(conn, user);
                        System.out.println("Tha password have been changed!");
                    }
                } while ( !isPasswordCorrect );
            } catch ( Exception e ) {
                e.printStackTrace();
            } finally {
                JDBCUtils.closeResource(conn, null);
            }

        } else {
            System.out.println("Your password is wrong, please try again!");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
