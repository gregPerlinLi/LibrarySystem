package com.gregperlinli.view;

import com.gregperlinli.bean.Curator;
import com.gregperlinli.bean.User;
import com.gregperlinli.dao.CommonStaffDAOImpl;
import com.gregperlinli.dao.UserDAOImpl;
import com.gregperlinli.util.GetTime;
import com.gregperlinli.util.JDBCUtills;

import java.sql.Connection;
import java.util.Scanner;

/**
 * @author gregperlinli
 */
public class CuratorDashboard {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final int MIN_NUM = 0, MAX_NUM = 9,
            MIN_QUERY_MODE = 0, MAX_QUERY_MODE = 7,
            MIN_UPDATE_MODE = 0, MAX_UPDATE_MODE = 5,
            MIN_LEND_MODE = 0, MAX_LEND_MODE = 5,
            MIN_RETURN_MODE = 0, MAX_RETURN_MODE = 5,
            MIN_STAFF_QUERY_MODE = 0, MAX_STAFF_QUERY_MODE = 7,
            MIN_STAFF_UPDATE_MODE = 0, MAX_STAFF_UPDATE_MODE = 5,
            MIN_SCHEDULE_MODE = 0, MAX_SCHEDULE_MODE = 5;

    public static int curatorView(User user, Curator ct) {
        final CommonStaffDAOImpl COMMON_STAFF_DAO = new CommonStaffDAOImpl();
        final UserDAOImpl USER_DAO = new UserDAOImpl();

        ClearScreen.clear();

        int function;
        do {
            System.out.println("***************** Curator dashboard *****************");
            System.out.println("Good " + GetTime.getMorAftEveNig() + "!");
            System.out.printf("Account: %s     Uid: %d     Authority: %d\n", ct.getCuratorName(), user.getUid(), ct.getAuthority());
            Connection conn = null;
            try {
                conn = JDBCUtills.getConnectionWithPool();
                System.out.printf("There are %d common staffs and %d users in the library\n\n", COMMON_STAFF_DAO.getCount(conn), USER_DAO.getCount(conn));
            } catch ( Exception e ) {
                e.printStackTrace();
            } finally {
                JDBCUtills.closeResource(conn, null);
            }

            System.out.println("Please select a function:");
            System.out.println("-------- Book Manage --------");
            System.out.println("1. Query book information");
            System.out.println("2. Manage book information");
            System.out.println("3. Lend books");
            System.out.println("4. Return books");
            System.out.println("-------- Common Staff Manage --------");
            System.out.println("5. Query common staff");
            System.out.println("6. Manage common staff");
            System.out.println("7. Scheduling common staff");
            System.out.println("-------- Others --------");
            System.out.println("8. Cancellation account");

            try {
                function = SCAN.nextInt();
                SCAN.nextLine();
            } catch ( Exception e ) {
                SCAN.nextLine();
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
                SCAN.nextLine();
            } catch ( Exception e ) {
                SCAN.nextLine();
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
                SCAN.nextLine();
            } catch ( Exception e ) {
                SCAN.nextLine();
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

    public static int lendBookView() {
        int mode;
        do {
            System.out.println("\n-------- Lend book mode --------");
            System.out.println("Please select a mode:");
            System.out.println("1. Lend book by ID");
            System.out.println("2. Lend book by ISBM");
            System.out.println("3. Lend book by name");
            System.out.println("4. Quit");
            try {
                mode = SCAN.nextInt();
                SCAN.nextLine();
            } catch (Exception e) {
                SCAN.nextLine();
                e.printStackTrace();
                mode = 0;
            }
            if ( mode <= MIN_LEND_MODE || mode >= MAX_LEND_MODE ) {
                ClearScreen.clear();
                System.out.println("The number you enter is invalid, please try again!");
            }
        } while ( mode <= MIN_LEND_MODE || mode >= MAX_LEND_MODE );

        return mode;
    }

    public static int returnBookView() {
        int mode;
        do {
            System.out.println("\n-------- Return book mode --------");
            System.out.println("Please select a mode:");
            System.out.println("1. Return book by ID");
            System.out.println("2. Return book by ISBM");
            System.out.println("3. Return book by name");
            System.out.println("4. Quit");
            try {
                mode = SCAN.nextInt();
                SCAN.nextLine();
            } catch (Exception e) {
                SCAN.nextLine();
                e.printStackTrace();
                mode = 0;
            }
            if ( mode <= MIN_RETURN_MODE || mode >= MAX_RETURN_MODE ) {
                ClearScreen.clear();
                System.out.println("The number you enter is invalid, please try again!");
            }
        } while ( mode <= MIN_RETURN_MODE || mode >= MAX_RETURN_MODE );

        return mode;
    }

    public static int queryStaffView() {
        int mode;
        do {
            System.out.println("\n-------- Query staff mode --------");
            System.out.println("Please select a mode:");
            System.out.println("1. Query staff by uid");
            System.out.println("2. Query staff by name");
            System.out.println("3. Query staff by account");
            System.out.println("4. Query staff by gender");
            System.out.println("5. Query all");
            System.out.println("6. Quit");
            try {
                mode = SCAN.nextInt();
                SCAN.nextLine();
            } catch ( Exception e ) {
                SCAN.nextLine();
                e.printStackTrace();
                mode = 0;
            }
            if ( mode <= MIN_STAFF_QUERY_MODE || mode >+ MAX_STAFF_QUERY_MODE ) {
                ClearScreen.clear();
                System.out.println("The number you enter is invalid, please try again!");
            }
        } while ( mode <= MIN_STAFF_QUERY_MODE || mode >+ MAX_STAFF_QUERY_MODE );

        return mode;
    }

    public static int updateStaffView() {
        int mode;
        do {
            System.out.println("\n-------- Update staff mode --------");
            System.out.println("Please select a mode:");
            System.out.println("1. Add a staff");
            System.out.println("2. Delete a staff");
            System.out.println("3. Update a staff");
            System.out.println("4. Quit");
            try {
                mode = SCAN.nextInt();
                SCAN.nextLine();
            } catch ( Exception e ) {
                SCAN.nextLine();
                e.printStackTrace();
                mode = 0;
            }
            if ( mode <= MIN_STAFF_UPDATE_MODE || mode >+ MAX_STAFF_UPDATE_MODE ) {
                ClearScreen.clear();
                System.out.println("The number you enter is invalid, please try again!");
            }
        } while ( mode <= MIN_STAFF_UPDATE_MODE || mode >+ MAX_STAFF_UPDATE_MODE );

        return mode;
    }

    public static int scheduleView() {
        int mode;
        do {
            System.out.println("\n-------- Schedule staff mode --------");
            System.out.println("Please select a mode:");
            System.out.println("1. Promotion");
            System.out.println("2. Demotion");
            System.out.println("3. Staff scheduling");
            System.out.println("4. Quit");
            try {
                mode = SCAN.nextInt();
                SCAN.nextLine();
            } catch ( Exception e ) {
                SCAN.nextLine();
                e.printStackTrace();
                mode = 0;
            }
            if ( mode <= MIN_SCHEDULE_MODE || mode >= MAX_SCHEDULE_MODE ) {
                ClearScreen.clear();
                System.out.println("The number you enter is invalid, please try again!");
            }
        } while ( mode <= MIN_SCHEDULE_MODE || mode >= MAX_SCHEDULE_MODE );

        return mode;
    }
}
