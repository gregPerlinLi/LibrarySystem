package com.gregperlinli.view;

import com.gregperlinli.bean.Book;
import com.gregperlinli.util.EmptyUtil;

import java.util.List;

/**
 * @author gregperlinli
 */
public class CommonStaffOutput {
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
}
