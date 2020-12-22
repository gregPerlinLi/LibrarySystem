package com.gregperlinli.service.curator;

import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.Curator;
import com.gregperlinli.bean.User;
import com.gregperlinli.dao.CommonStaffDAOImpl;
import com.gregperlinli.util.JDBCUtills;
import com.gregperlinli.view.ClearScreen;
import com.gregperlinli.view.CuratorDashboard;
import com.gregperlinli.view.OperationOutput;
import com.gregperlinli.view.ResetView;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * @author gregperlinli
 */
public class CuratorStaffFunction {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final CommonStaffDAOImpl COMMON_STAFF_DAO = new CommonStaffDAOImpl();

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
