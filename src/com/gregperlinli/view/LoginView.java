package com.gregperlinli.view;

import java.util.Scanner;

/**
 * @author gregperlinli
 */
public class LoginView {
    private static final int MINNUM = 0, MAXNUM = 5;

    public static int loginInterface() {

        /* System.out.println("***************** Welcome to library manage system *********************\n");*/
        System.out.println();
        try {
            Thread.sleep(1500);
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        int identity;
        do {
            System.out.println("Please select your identity");
            System.out.println("1. Common staff");
            System.out.println("2. Curator");
            System.out.println("3. Administrator(Not yet open)");
            System.out.println("4. Close the system");

            Scanner scan = new Scanner(System.in);

            try {
                identity = scan.nextInt();
            } catch ( Exception e ) {
                e.printStackTrace();
                identity = MINNUM;
            }

            if ( identity <= MINNUM || identity >= MAXNUM ) {
                ClearScreen.clear();
                System.out.println("The number you enter is invalid, please try again!\n");
                System.out.println("***************** Welcome to library manage system *********************\n");
            }
        } while ( identity <= MINNUM || identity >= MAXNUM );
        return identity;
    }
}
