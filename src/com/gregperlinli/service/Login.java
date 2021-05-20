package com.gregperlinli.service;

import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.Curator;
import com.gregperlinli.bean.User;
import com.gregperlinli.dao.CommonStaffDAOImpl;
import com.gregperlinli.dao.CuratorDAOImpl;
import com.gregperlinli.dao.UserDAOImpl;
import com.gregperlinli.utils.JDBCUtils;
import com.gregperlinli.utils.MD5Encrypt;
import com.gregperlinli.view.ClearScreen;
import com.gregperlinli.utils.EmptyUtils;
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
            conn = JDBCUtils.getConnectionWithPool();

            // Get the uid from form User and CommonStaff
            User user = new UserDAOImpl().getUserByAccount(conn, account);
            CommonStaff cs = new CommonStaffDAOImpl().getCommonStaffByUid(conn, user.getUid());

            if ( EmptyUtils.isEmpty(cs) ) {
                JDBCUtils.closeResource(conn, null);
                ClearScreen.clear();
                System.out.println("The account is not exist!!");
                ResetView.resetLogin();
            } else {
                // Verify the password
                String encryptedPassword = MD5Encrypt.stringMD5(password);

                if ( encryptedPassword.equals(user.getPassword()) ) {
                    System.out.println("\nLogin success!");
                    System.out.println("Welcome " + user.getAccount() + "!");
                    JDBCUtils.closeResource(conn, null);
                    try {
                        Thread.sleep(1500);
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }

                    // Go to the commonStaff view
                    ResetView.resetCommonStaff(user, cs);
                } else {
                    JDBCUtils.closeResource(conn, null);
                    ClearScreen.clear();
                    System.out.println("Your password is wrong, please try again!");
                    ResetView.resetLogin();
                }
            }


        } catch ( Exception e ) {
            e.printStackTrace();
            JDBCUtils.closeResource(conn, null);
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
            conn = JDBCUtils.getConnectionWithPool();

            // Get the uid from form User and CommonStaff
            User user = new UserDAOImpl().getUserByAccount(conn, account);
            Curator ct = new CuratorDAOImpl().getCuratorByUid(conn, user.getUid());

            if ( EmptyUtils.isEmpty(ct) ) {
                JDBCUtils.closeResource(conn, null);
                ClearScreen.clear();
                System.out.println("The account is not exist!!");
                ResetView.resetLogin();
            } else {

                // Verify the password
                String encryptedPassword = MD5Encrypt.stringMD5(password);

                if ( encryptedPassword.equals(user.getPassword()) ) {
                    System.out.println("\nLogin success!");
                    System.out.println("Welcome " + user.getAccount() + "!");
                    JDBCUtils.closeResource(conn, null);
                    try {
                        Thread.sleep(1500);
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }

                    // Go to the Curator view
                    ResetView.resetCurator(user, ct);
                } else {
                    JDBCUtils.closeResource(conn, null);
                    ClearScreen.clear();
                    System.out.println("Your password is wrong, please try again!");
                    ResetView.resetLogin();
                }
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            JDBCUtils.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("The account is not exist!!");
            ResetView.resetLogin();
        }
        // System.out.println("UserName: " + userName + " Password: " + password);
    }
}
