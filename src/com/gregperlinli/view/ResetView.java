package com.gregperlinli.view;

import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.Curator;
import com.gregperlinli.bean.User;
import com.gregperlinli.service.CommonStaffFunction;
import com.gregperlinli.service.CuratorFunction;
import com.gregperlinli.service.Login;

/**
 * @author gregperlinli
 */
public class ResetView {
    public static void resetLogin() {
        int identity = LoginView.loginInterface();
        //noinspection AlibabaSwitchStatement
        switch (identity) {
            case 1 -> {
                System.out.println("-------- Common staff login --------");
                Login.commonStaffLogin();
            } case 2 -> {
                System.out.println("-------- Curator login --------");
                Login.curatorLogin();
            } case 3 -> {
                System.out.println("This feature is not yet open!");
                System.exit(9);
            } default -> {
                System.out.println("Goodbye, see you next time (^_^)v");
                System.exit(0);
            }
        }

    }

    public static void resetCommonStaff(User user, CommonStaff cs) {
        int function = CommonStaffDashboard.commonStaffView(user, cs);
        //noinspection AlibabaSwitchStatement
        switch (function) {
            case 1 -> {
                CommonStaffFunction.selectQueryMode(user, cs);
            } case 2 -> {
                CommonStaffFunction.selectUpdateMode(user, cs);
            } case 3 -> {
                CommonStaffFunction.selectLendBookMode(user, cs);
            } case 4 -> {
                CommonStaffFunction.selectReturnBookMode(user, cs);
            } default -> {
                System.out.println("Cancellation success!!");
                try {
                    Thread.sleep(1500);
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
                ClearScreen.clear();
                System.out.println("***************** Welcome to library manage system *********************\n");
                ResetView.resetLogin();
            }

        }

    }

    public static void resetCurator(User user, Curator ct) {
        int function = CuratorDashboard.curatorView(user, ct);
        //noinspection AlibabaSwitchStatement
        switch (function) {
            case 1 -> {
                CuratorFunction.selectQueryMode(user, ct);
            } case 2 -> {
                CuratorFunction.selectUpdateMode(user, ct);
            } case 3 -> {
                CuratorFunction.selectLendBookMode(user, ct);
            } case 4 -> {
                CuratorFunction.selectReturnBookMode(user, ct);
            } case 5 -> {
                // Add common staff
            } case 6 -> {
                // Delete common staff
            } case 7 -> {
                // Scheduling common staff
            } default -> {
                System.out.println("Cancellation success!!");
                try {
                    Thread.sleep(1500);
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
                ClearScreen.clear();
                System.out.println("***************** Welcome to library manage system *********************\n");
                ResetView.resetLogin();
            }

        }

    }
}
