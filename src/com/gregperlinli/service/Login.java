package com.gregperlinli.service;

import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.Curator;
import com.gregperlinli.bean.User;
import com.gregperlinli.dao.CommonStaffDAOImpl;
import com.gregperlinli.dao.CuratorDAOImpl;
import com.gregperlinli.dao.UserDAOImpl;
import com.gregperlinli.util.ClearScreen;
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
            conn = JDBCUtills.getConnection();

            // Get the uid from form User and CommonStaff
            User user = new UserDAOImpl().getUserByAccount(conn, account);
            CommonStaff cs = new CommonStaffDAOImpl().getCommonStaffByUid(conn, user.getUid());

            if ( EmptyUtil.isEmpty(cs) ) {
                System.out.println("The account is not exist!!");
                JDBCUtills.closeResource(conn, null);
                ClearScreen.clear();
                ResetView.reset();
            } else {
                // Verify the password
                if ( password.equals(user.getPassword()) ) {
                    System.out.println("Login success!");
                    JDBCUtills.closeResource(conn, null);
                } else {
                    System.out.println("Your password is wrong, please try again!");
                    JDBCUtills.closeResource(conn, null);
                    ClearScreen.clear();
                    ResetView.reset();
                }
            }


        } catch ( Exception e ) {
            e.printStackTrace();
            JDBCUtills.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("The account is not exist!!");
            ResetView.reset();
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
            conn = JDBCUtills.getConnection();

            // Get the uid from form User and CommonStaff
            User user = new UserDAOImpl().getUserByAccount(conn, account);
            Curator ct = new CuratorDAOImpl().getCuratorByUid(conn, user.getUid());

            if ( EmptyUtil.isEmpty(ct) ) {
                System.out.println("The account is not exist!!");
                JDBCUtills.closeResource(conn, null);
                ClearScreen.clear();
                ResetView.reset();
            } else {

                // Verify the password
                if ( password.equals(user.getPassword()) ) {
                    System.out.println("Login success!");
                    JDBCUtills.closeResource(conn, null);
                } else {
                    System.out.println("Your password is wrong, please try again!");
                    JDBCUtills.closeResource(conn, null);
                    ClearScreen.clear();
                    ResetView.reset();
                }
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            JDBCUtills.closeResource(conn, null);
            ClearScreen.clear();
            System.out.println("The account is not exist!!");
            ResetView.reset();
        }
        // System.out.println("UserName: " + userName + " Password: " + password);
    }
}
