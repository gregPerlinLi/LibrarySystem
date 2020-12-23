package com.gregperlinli.view;

import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.Curator;
import com.gregperlinli.bean.User;
import com.gregperlinli.service.CommonStaffFunction;
import com.gregperlinli.service.curator.CuratorBookFunction;
import com.gregperlinli.service.Login;
import com.gregperlinli.service.curator.CuratorStaffFunction;

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
                CuratorBookFunction.selectQueryMode(user, ct);
            } case 2 -> {
                CuratorBookFunction.selectUpdateMode(user, ct);
            } case 3 -> {
                CuratorBookFunction.selectLendBookMode(user, ct);
            } case 4 -> {
                CuratorBookFunction.selectReturnBookMode(user, ct);
            } case 5 -> {
                CuratorStaffFunction.selectQueryMode(user, ct);
            } case 6 -> {
                CuratorStaffFunction.selectUpdateMode(user, ct);
            } case 7 -> {
                CuratorStaffFunction.selectScheduleMode(user, ct);
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
