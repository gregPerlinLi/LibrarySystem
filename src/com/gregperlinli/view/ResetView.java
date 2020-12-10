package com.gregperlinli.view;

import com.gregperlinli.service.Login;

/**
 * @author gregperlinli
 */
public class ResetView {
    public static void reset() {
        int identity = LoginView.loginInterface();
        //noinspection AlibabaSwitchStatement
        switch (identity) {
            case 1 -> {
                System.out.println("-------- Common staff login --------");
                Login.commonStaffLogin();
            }
            case 2 -> {
                System.out.println("-------- Curator login --------");
                Login.curatorLogin();
            }
            case 3 -> {
                System.out.println("This feature is not yet open!");
                System.exit(9);
            }
            default -> {
                System.out.println("Goodbye, see you next time (^_^)v");
                System.exit(0);
            }
        }

    }
}
