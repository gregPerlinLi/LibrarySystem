package com.gregperlinli.view;

import com.gregperlinli.util.ClearScreen;

import java.util.Scanner;

/**
 * @author gregperlinli
 */
public class LoginView {
    public static int loginInterface() {
        /* System.out.println("***************** Welcome to library manage system *********************\n");*/
        System.out.println();
        try {
            Thread.sleep(1500);
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        int identity = 0;
        do {
            System.out.println("Please select your identity");
            System.out.println("1. Common staff");
            System.out.println("2. Curator");
            System.out.println("3. Administrator(Not yet open)");
            System.out.println("4. Close the system");

            Scanner scan = new Scanner(System.in);
            identity = scan.nextInt();

            if ( identity <= 0 || identity >= 5 ) {
                ClearScreen.clear();
                System.out.println("***************** Welcome to library manage system *********************\n");
                System.out.println("The number you enter is invalid, please try again!\n");
            }
        } while ( identity <= 0 || identity >= 5 );
        return identity;
    }
}
