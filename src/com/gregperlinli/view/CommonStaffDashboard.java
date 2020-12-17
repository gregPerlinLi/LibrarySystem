package com.gregperlinli.view;

import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.User;
import com.gregperlinli.util.GetTime;

import java.util.Scanner;

/**
 * @author gregperlinli
 */
public class CommonStaffDashboard {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final int MIN_NUM = 0, MAX_NUM = 6,
                             MIN_QUERY_MODE = 0, MAX_QUERY_MODE = 7,
                             MIN_UPDATE_MODE = 0, MAX_UPDATE_MODE = 5;

    public static int commonStaffView(User user, CommonStaff cs) {
        ClearScreen.clear();

        int function;
        do {
            System.out.println("*************** Common staff dashboard ***************");
            System.out.println("Good " + GetTime.getMorAftEveNig() + "!");
            System.out.printf("Account: %s     Uid: %d\n\n", cs.getStaffName(), user.getUid());

            System.out.println("Please select a function:");
            System.out.println("1. Query book information");
            System.out.println("2. Manage book information");
            System.out.println("3. Lend books");
            System.out.println("4. Return books");
            System.out.println("5. Cancellation account");

            try {
                function = SCAN.nextInt();
            } catch ( Exception e ) {
                e.printStackTrace();
                function = MIN_NUM;
            }

            if ( function <= MIN_NUM || function >= MAX_NUM ) {
                ClearScreen.clear();
                System.out.println("The number you enter is invalid, please try again!\n");
            }

        } while ( function <= MIN_NUM || function >= MAX_NUM );

        return function;

    }

    public static int queryBookView() {
        int mode;
        do {
            System.out.println("\n-------- Query book mode --------");
            System.out.println("Please select a mode:");
            System.out.println("1. Query book by ISBM");
            System.out.println("2. Query book by name");
            System.out.println("3. Query books by category");
            System.out.println("4. Query books by author");
            System.out.println("5. Query all books");
            System.out.println("6. Quit");

            try {
                mode = SCAN.nextInt();
            } catch ( Exception e ) {
                e.printStackTrace();
                mode = 0;
            }
            if ( mode <= MIN_QUERY_MODE || mode >= MAX_QUERY_MODE ) {
                ClearScreen.clear();
                System.out.println("The number you enter is invalid, please try again!\n");
            }
        } while ( mode <= MIN_QUERY_MODE || mode >= MAX_QUERY_MODE );

        return mode;

    }

    public static int updateBookView() {
        int mode;
        do {
            System.out.println("\n-------- Update book mode --------");
            System.out.println("Please select a mode:");
            System.out.println("1. Add a book");
            System.out.println("2. Delete a book");
            System.out.println("3. Update a book");
            System.out.println("4. Quit");

            try {
                mode = SCAN.nextInt();
            } catch ( Exception e ) {
                e.printStackTrace();
                mode = 0;
            }
            if ( mode <= MIN_UPDATE_MODE || mode >= MAX_UPDATE_MODE ) {
                ClearScreen.clear();
                System.out.println("The number you enter is invalid, please try again!\n");
            }
        } while( mode <= MIN_UPDATE_MODE || mode >= MAX_UPDATE_MODE );
        return mode;
    }
}
