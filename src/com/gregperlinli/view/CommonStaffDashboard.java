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
    private static final int MINNUM = 0, MAXNUM = 6, MINMODE = 0, MAXMODE = 4;

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
                function = MINNUM;
            }

            if ( function <= MINNUM || function >= MAXNUM ) {
                ClearScreen.clear();
                System.out.println("The number you enter is invalid, please try again!\n");
            }

        } while ( function <= MINNUM || function >= MAXNUM );

        return function;

    }

    public static int queryBookView() {
        int mode;
        do {
            System.out.println("-------- Query book mode --------");
            System.out.println("Please select a mode:");
            System.out.println("1. Query book by ISBM");
            System.out.println("2. Query book by name");
            System.out.println("3. Quit");

            try {
                mode = SCAN.nextInt();
            } catch ( Exception e ) {
                e.printStackTrace();
                mode = 0;
            }
            if ( mode <= MINMODE || mode >= MAXMODE ) {
                ClearScreen.clear();
                System.out.println("The number you enter is invalid, please try again!\n");
            }
        } while ( mode <= MINMODE || mode >= MAXMODE );

        return mode;

    }
}
