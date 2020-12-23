package com.gregperlinli.service.curator;

import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.Curator;
import com.gregperlinli.bean.User;
import com.gregperlinli.dao.CommonStaffDAOImpl;
import com.gregperlinli.dao.UserDAOImpl;
import com.gregperlinli.util.EmptyUtils;
import com.gregperlinli.util.JDBCUtills;
import com.gregperlinli.view.ClearScreen;
import com.gregperlinli.view.CuratorDashboard;
import com.gregperlinli.view.OperationOutput;
import com.gregperlinli.view.ResetView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author gregperlinli
 */
public class CuratorStaffFunction {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final UserDAOImpl USER_DAO = new UserDAOImpl();
    private static final CommonStaffDAOImpl COMMON_STAFF_DAO = new CommonStaffDAOImpl();

    public static void selectQueryMode(User user, Curator ct) {
        int mode = CuratorDashboard.queryStaffView();
        //noinspection AlibabaSwitchStatement
        switch (mode) {
            case 1 -> {
                //query by uid
            } case 2 -> {
                //query by name
            } default -> {
                ResetView.resetCurator(user, ct);
            }
        }
    }

    public static void selectUpdateMode(User user, Curator ct) {
        int mode = CuratorDashboard.updateStaffView();
        //noinspection AlibabaSwitchStatement
        switch (mode) {
            case 1 -> {
                addStaff(user, ct);
                selectUpdateMode(user, ct);
            } case 2 -> {
                //delete a staff
            } case 3 -> {
                //update a staff
            } default -> {
                ResetView.resetCurator(user, ct);
            }
        }
    }

    public static void addStaff(User user, Curator ct) {
        CommonStaff newCs = new CommonStaff();
        User newUser = new User();

        try{
            enterStaffInform(user, ct, newUser, newCs);
        } catch ( Exception e ) {
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The value you enter is invalid, please try again!\n");
            addStaff(user, ct);
        }

        System.out.println("Inspecting the staff you want to add...");
        boolean isEmpty = EmptyUtils.isStaffEmpty(newUser, newCs);
        if ( isEmpty ) {
            System.out.println("The staff you want to add is wrong, please try again!\n");
            addStaff(user, ct);
        }
        boolean isRepeat = EmptyUtils.isAddStaffRepeat(newUser, newCs);
        if ( isRepeat) {
            System.out.println("The staff you want to add is wrong, please try again!\n");
            addStaff(user, ct);
        }
        System.out.println("Inspection passed, now adding the staff...");
        Connection conn = null;
        try {
            conn = JDBCUtills.getConnectionWithPool();
            conn.setAutoCommit(false);

            USER_DAO.insert(conn, newUser);
            COMMON_STAFF_DAO.insert(conn, newCs);

            conn.commit();

            System.out.println("Adding successful!");
        } catch ( Exception e ) {
            e.printStackTrace();
            try {
                Objects.requireNonNull(conn).rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ClearScreen.clear();
            System.out.println("Add failed, please try again!");
            JDBCUtills.closeResource(conn, null);
            addStaff(user, ct);
        } finally {
            JDBCUtills.closeResource(conn, null);
        }
    }

    public static void enterStaffInform(User user, Curator ct ,User newUser, CommonStaff newCs) throws Exception {
        final String MALE = "M", FEMALE = "F";

        System.out.println("Please enter the UID (e.g 2XXXX, if you don't want to add, please enter '-1'):");
        int uid = SCAN.nextInt();
        SCAN.nextLine();
        if ( uid == -1 ) {
            System.out.println("Add canceled!");
            selectUpdateMode(user, ct);
        }
        newUser.setUid(uid);
        newCs.setUid(uid);
        System.out.println("Please enter the staff name:");
        String staffName = SCAN.nextLine();
        newUser.setUserName(staffName);
        newCs.setStaffName(staffName);
        boolean isGenderCorrect = false;
        do {
            System.out.println("Please enter the gender (if male, please enter M; if female, please enter F):");
            String gender = SCAN.nextLine();
            if ( !MALE.equals(gender) && !FEMALE.equals(gender) ) {
                System.out.println("The gender you entered is invalid, please try again!");
                isGenderCorrect = false;
            }
            if ( MALE.equals(gender) || FEMALE.equals(gender) ) {
                isGenderCorrect = true;
                newCs.setGender(gender);
            }
        } while ( !isGenderCorrect );
        System.out.println("Please enter the Phone number:");
        Long phoneNum = SCAN.nextLong();
        SCAN.nextLine();
        newCs.setPhoneNum(Long.toString(phoneNum));
        System.out.println("Please enter the account:");
        String account = SCAN.nextLine();
        newUser.setAccount(account);
        String firPassword, secPassword;
        boolean isPasswordCorrect = false;
        do {
            System.out.println("Please enter the password:");
            firPassword = SCAN.nextLine();
            System.out.println("Please confirm you password again:");
            secPassword = SCAN.nextLine();
            if ( !firPassword.equals(secPassword) ) {
                System.out.println("The two passwords you entered are inconsistent, please try again!");
                isPasswordCorrect = false;
            }
            if ( firPassword.equals(secPassword) ) {
                isPasswordCorrect = true;
                newUser.setPassword(firPassword);
            }
        } while ( !isPasswordCorrect );
    }

    public static void selectScheduleMode(User user, Curator ct) {
        int mode = CuratorDashboard.scheduleView();
        //noinspection AlibabaSwitchStatement
        switch (mode) {
            case 1 -> {
                promoteStaff(user, ct);
                selectScheduleMode(user, ct);
            } case 2 -> {
                demoteStaff(user, ct);
                selectScheduleMode(user, ct);
            } case 3 -> {
                System.out.println("This feature is not yet open!");
                selectScheduleMode(user, ct);
            } default -> {
                ResetView.resetCurator(user, ct);
            }
        }
    }

    public  static void promoteStaff(User user, Curator ct) {
        int promoteStaffId;
        List<CommonStaff> list = OperationOutput.listStaff();
        System.out.println("Please enter the id of the staff you want to promote(if you want to cancel, please enter -1)");
        Connection conn = null;
        try {
            promoteStaffId = SCAN.nextInt();
            SCAN.nextLine();
            if ( promoteStaffId == -1 ) {
                System.out.println("Promote canceled!");
                selectScheduleMode(user, ct);
            }
            conn = JDBCUtills.getConnectionWithPool();
            for ( CommonStaff cs : list ) {
                if ( cs.getId() == promoteStaffId ) {
                    if ( cs.getAuthority() >= 90 ) {
                        System.out.println("Error: The staff " + cs.getStaffName() + "'s authority is 90, it's the max authority of the common staff!");
                        promoteStaff(user, ct);
                    }
                    COMMON_STAFF_DAO.promoteStaff(conn, cs);
                    System.out.println("Promote success, the authority of the staff " + cs.getStaffName() + " is " + ( cs.getAuthority() + 10 ) + ".\n");
                }
            }
        } catch ( InputMismatchException e ) {
            SCAN.nextLine();
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you enter is invalid, please try again");
            promoteStaff(user, ct);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }

        System.out.println("Do you want to promote other staffs?\n1. Yes\n2. No");
        int isRepeat = 2;
        try {
            isRepeat = SCAN.nextInt();
            SCAN.nextLine();
        } catch ( Exception e ) {
            SCAN.nextLine();
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you enter is invalid!");
            selectScheduleMode(user, ct);
        }
        if ( isRepeat == 1 ) {
            promoteStaff(user, ct);
        } else {
            selectScheduleMode(user, ct);
        }
    }

    public static void demoteStaff(User user, Curator ct) {
        int demoteStaffId;
        List<CommonStaff> list = OperationOutput.listStaff();
        System.out.println("Please enter the id of the staff you want to demote(if you want to cancel, please enter -1)");
        Connection conn = null;
        try {
            demoteStaffId = SCAN.nextInt();
            SCAN.nextLine();
            if ( demoteStaffId == -1 ) {
                System.out.println("Demote canceled!");
                selectScheduleMode(user, ct);
            }
            conn = JDBCUtills.getConnectionWithPool();
            for ( CommonStaff cs : list ) {
                if ( cs.getId() == demoteStaffId ) {
                    if ( cs.getAuthority() <= 10 ) {
                        System.out.println("Error: The staff " + cs.getStaffName() + "'s authority is 10, it's the min authority of the common staff!");
                        demoteStaff(user, ct);
                    }
                    COMMON_STAFF_DAO.demoteStaff(conn, cs);
                    System.out.println("Demote success, the authority of the staff " + cs.getStaffName() + " is " + ( cs.getAuthority() - 10 ) + ".\n");
                }
            }
        } catch ( InputMismatchException e ) {
            SCAN.nextLine();
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you enter is invalid, please try again");
            demoteStaff(user, ct);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(conn, null);
        }

        System.out.println("Do you want to demote other staffs?\n1. Yes\n2. No");
        int isRepeat = 2;
        try {
            isRepeat = SCAN.nextInt();
            SCAN.nextLine();
        } catch ( Exception e ) {
            SCAN.nextLine();
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you enter is invalid!");
            selectScheduleMode(user, ct);
        }
        if ( isRepeat == 1 ) {
            demoteStaff(user, ct);
        } else {
            selectScheduleMode(user, ct);
        }
    }
}
