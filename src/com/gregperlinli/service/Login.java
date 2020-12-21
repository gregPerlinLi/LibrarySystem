package com.gregperlinli.service;

import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.Curator;
import com.gregperlinli.bean.User;
import com.gregperlinli.dao.CommonStaffDAOImpl;
import com.gregperlinli.dao.CuratorDAOImpl;
import com.gregperlinli.dao.UserDAOImpl;
import com.gregperlinli.view.ClearScreen;
import com.gregperlinli.util.EmptyUtil;
import com.gregperlinli.util.JDBCUtills;
import com.gregperlinli.view.ResetView;

import java.sql.Connection;
import java.util.Scanner;

/**
 * @author gregperlinli
 */
public class Login {
    private static final Scanner SCAN = new Scanner(System.in);
    static Connection conn = null;

    public static void commonStaffLogin() {
        try {
            System.out.println("Account:");
            String account = SCAN.nextLine();
            System.out.println("Password:");
            String password = SCAN.nextLine();

            // Connect to JDBC server
            conn = JDBCUtills.getConnectionWithPool();

            // Get the uid from form User and CommonStaff
            User user = new UserDAOImpl().getUserByAccount(conn, account);
            CommonStaff cs = new CommonStaffDAOImpl().getCommonStaffByUid(conn, user.getUid());

            if ( EmptyUtil.isEmpty(cs) ) {
                System.out.println("The account is not exist!!");
                JDBCUtills.closeResource(conn, null);
                ClearScreen.clear();
                ResetView.resetLogin();
            } else {
                // Verify the password
                if ( password.equals(user.getPassword()) ) {
                    System.out.println("\nLogin success!");
                    System.out.println("Welcome " + user.getAccount() + "!");
                    JDBCUtills.closeResource(conn, null);
                    try {
                        Thread.sleep(1500);
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }

                    // Go to the commonStaff view
                    ResetView.resetCommonStaff(user, cs);
                } else {
                    JDBCUtills.closeResource(conn, null);
                    ClearScreen.clear();
                    System.out.println("Your password is wrong, please try again!");
                    ResetView.resetLogin();
                }
            }


        } catch ( Exception e ) {
            e.printStackTrace();
            JDBCUtills.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("The account is not exist!!");
            ResetView.resetLogin();
        }
        // System.out.println("UserName: " + userName + " Password: " + password);
    }

    public static void curatorLogin() {
        try {
            System.out.println("Account:");
            String account = SCAN.nextLine();
            System.out.println("Password:");
            String password = SCAN.nextLine();

            // Connect to JDBC server
            conn = JDBCUtills.getConnectionWithPool();

            // Get the uid from form User and CommonStaff
            User user = new UserDAOImpl().getUserByAccount(conn, account);
            Curator ct = new CuratorDAOImpl().getCuratorByUid(conn, user.getUid());

            if ( EmptyUtil.isEmpty(ct) ) {
                System.out.println("The account is not exist!!");
                JDBCUtills.closeResource(conn, null);
                ClearScreen.clear();
                ResetView.resetLogin();
            } else {

                // Verify the password
                if ( password.equals(user.getPassword()) ) {
                    System.out.println("\nLogin success!");
                    System.out.println("Welcome " + user.getAccount() + "!");
                    JDBCUtills.closeResource(conn, null);
                    try {
                        Thread.sleep(1500);
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }

                    // Go to the Curator view
                    ResetView.resetCurator(user, ct);
                } else {
                    JDBCUtills.closeResource(conn, null);
                    ClearScreen.clear();
                    System.out.println("Your password is wrong, please try again!");
                    ResetView.resetLogin();
                }
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            JDBCUtills.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("The account is not exist!!");
            ResetView.resetLogin();
        }
        // System.out.println("UserName: " + userName + " Password: " + password);
    }
}
