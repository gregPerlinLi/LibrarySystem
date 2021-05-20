package com.gregperlinli.view;

import com.gregperlinli.bean.Book;
import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.User;
import com.gregperlinli.dao.impl.CommonStaffDAOImpl;
import com.gregperlinli.utils.EmptyUtils;
import com.gregperlinli.utils.JDBCUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

/**
 * @author gregperlinli
 */
public class OperationOutput {
    public static void queryOneBookOutput(Book book) {
        System.out.println("\nResult:");
        System.out.println("--------------------------------------------------------------");
        if ( EmptyUtils.isEmpty(book) ) {
            System.out.println("The book you want to query is not exist!");
        } else {
            System.out.println(book);
        }
        System.out.println("--------------------------------------------------------------");
    }

    public static void queryMultiBooksOutput(List<Book> list, String name) {
        System.out.println("\nResult:");
        System.out.println("--------------------------------------------------------------");
        if ( list == null || list.size() == 0 ) {
            System.out.println("The book you want to query is not exist!");
        } else {
            list.forEach(System.out::println);
            System.out.println("\nThere are " + list.size() + " books with this " + name + ".");
        }
        System.out.println("--------------------------------------------------------------\n");
    }

    public static void queryAllBooksOutput(List<Book> list) {
        System.out.println("\nResult:");
        System.out.println("--------------------------------------------------------------");
        if ( list == null || list.size() == 0 ) {
            System.out.println("The book you want to query is not exist!");
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
            conn = JDBCUtils.getConnectionWithPool();
            list = csDao.getAll(conn);
            countStaff = csDao.getCount(conn);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
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

    public static void queryOneStaffOutput(User staffUser, CommonStaff cs) {
        System.out.println("\nResult:");
        System.out.println("--------------------------------------------------------------");
        if ( EmptyUtils.isEmpty(staffUser) || EmptyUtils.isEmpty(cs) ) {
            System.out.println("There common staff you want to query ia not exist!!");
        } else {
            System.out.println(staffUser + " " + cs);
        }
        System.out.println("--------------------------------------------------------------");

    }

    public static void queryMultiStaffsOutput(List<User> listStaffUser, List<CommonStaff> listCs, String gender) {
        final String MALE = "M", FEMALE = "F";
        System.out.println("\nResult:");
        System.out.println("--------------------------------------------------------------");
        if ( listStaffUser == null || listStaffUser.size() == 0 || listCs == null || listCs.size() == 0 ) {
            System.out.println("The staff you want to query is not exist!");
        } else {
            for ( User staffUser : listStaffUser ) {
                System.out.print(staffUser + " ");
                for ( CommonStaff cs : listCs ) {
                    if  ( staffUser.getUid() == cs.getUid() ) {
                        System.out.print(cs + "\n");
                    }
                }
            }
        }
        String outGender = null;
        if ( MALE.equals(gender) ) {
            outGender = "male";
        }
        if ( FEMALE.equals(gender) ) {
            outGender = "female";
        }
        System.out.println("\nThere are " + Objects.requireNonNull(listCs).size() + " " + outGender + " common staffs in the library");
        System.out.println("--------------------------------------------------------------");
    }

    public static void queryAllStaffsOutput(List<User> listStaffUser, List<CommonStaff> listCs) {
        System.out.println("\nResult:");
        System.out.println("--------------------------------------------------------------");
        if ( listStaffUser == null || listStaffUser.size() == 0 || listCs == null || listCs.size() == 0 ) {
            System.out.println("The staff you want to query is not exist!");
        } else {
            for ( User staffUser : listStaffUser ) {
                System.out.print(staffUser + " ");
                for ( CommonStaff cs : listCs ) {
                    if  ( staffUser.getUid() == cs.getUid() ) {
                        System.out.print(cs + "\n");
                    }
                }
            }
        }
        System.out.println("\n There are " + Objects.requireNonNull(listCs).size() + " common staffs in the library." );
        System.out.println("--------------------------------------------------------------");
    }
}
