package com.gregperlinli.service.curator;

import com.gregperlinli.bean.CommonStaff;
import com.gregperlinli.bean.Curator;
import com.gregperlinli.bean.User;
import com.gregperlinli.dao.impl.CommonStaffDAOImpl;
import com.gregperlinli.dao.impl.UserDAOImpl;
import com.gregperlinli.utils.EmptyUtils;
import com.gregperlinli.utils.JDBCUtils;
import com.gregperlinli.view.ClearScreen;
import com.gregperlinli.view.CuratorDashboard;
import com.gregperlinli.view.OperationOutput;
import com.gregperlinli.view.ResetView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

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
                queryByUid();
                selectQueryMode(user, ct);
            } case 2 -> {
                queryByName();
                selectQueryMode(user, ct);
            } case 3 -> {
                queryByAccount();
                selectQueryMode(user, ct);
            } case 4 -> {
                queryByGender();
                selectQueryMode(user, ct);
            } case 5 -> {
                queryAll();
                selectQueryMode(user, ct);
            } default -> {
                ResetView.resetCurator(user, ct);
            }
        }
    }

    public static void queryByUid() {
        System.out.println("Please enter the UID:");
        int uid = SCAN.nextInt();
        SCAN.nextLine();
        User staffUser;
        CommonStaff cs;
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            staffUser = USER_DAO.getUserByUid(conn, uid);
            cs = COMMON_STAFF_DAO.getCommonStaffByUid(conn, uid);
            OperationOutput.queryOneStaffOutput(staffUser, cs);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    public static void queryByName() {
        System.out.println("Please enter the name:");
        String name = SCAN.nextLine();
        User staffUser;
        CommonStaff cs;
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            staffUser = USER_DAO.getUserByName(conn, name);
            cs = COMMON_STAFF_DAO.getCommonStaffByName(conn, name);
            OperationOutput.queryOneStaffOutput(staffUser, cs);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    public static void queryByAccount() {
        System.out.println("Please enter the account:");
        String account = SCAN.nextLine();
        User staffUser;
        CommonStaff cs;
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            staffUser = USER_DAO.getUserByAccount(conn, account);
            cs = COMMON_STAFF_DAO.getCommonStaffByUid(conn, staffUser.getUid());
            OperationOutput.queryOneStaffOutput(staffUser, cs);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    public static void queryByGender() {
        final String MALE = "M", FEMALE = "F";
        String gender;
        boolean isGenderCorrect = false;
        do {
            System.out.println("Please enter the gender(if male, please enter M; if female, please enter F)");
            gender = SCAN.nextLine();
            if ( !MALE.equals(gender) && !FEMALE.equals(gender) ) {
                System.out.println("The gender you entered is invalid, please try again!");
                isGenderCorrect = false;
            }
            if ( MALE.equals(gender) || FEMALE.equals(gender) ) {
                isGenderCorrect = true;
            }
        } while ( !isGenderCorrect );
        List<User> listStaffUser = new ArrayList<>();
        List<CommonStaff> listCs;
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            listCs = COMMON_STAFF_DAO.getCommonStaffsByGender(conn, gender);
            for ( CommonStaff cs : listCs ) {
                User staffUser = USER_DAO.getUserByUid(conn, cs.getUid());
                listStaffUser.add(staffUser);
            }
            OperationOutput.queryMultiStaffsOutput(listStaffUser, listCs, gender);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }

    }

    public static void queryAll() {
        Connection conn = null;
        List<User> listStaffUser = new ArrayList<>();
        List<CommonStaff> listCs = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            listCs = COMMON_STAFF_DAO.getAll(conn);
            for ( CommonStaff cs : listCs ) {
                User staffUser = USER_DAO.getUserByUid(conn, cs.getUid());
                listStaffUser.add(staffUser);
            }
            OperationOutput.queryAllStaffsOutput(listStaffUser, listCs);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
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
                deleteStaff(user, ct);
                selectUpdateMode(user, ct);
            } case 3 -> {
                updateStaff(user, ct);
                selectUpdateMode(user, ct);
            } default -> {
                ResetView.resetCurator(user, ct);
            }
        }
    }

    public static void addStaff(User user, Curator ct) {
        CommonStaff newCs = new CommonStaff();
        User newUser = new User();

        try{
            enterStaffData(user, ct, newUser, newCs);
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
            conn = JDBCUtils.getConnectionWithPool();
            conn.setAutoCommit(false);

            USER_DAO.insert(conn, newUser);
            COMMON_STAFF_DAO.insert(conn, newCs);

            conn.commit();

            System.out.println("Add successful!");
        } catch ( Exception e ) {
            e.printStackTrace();
            try {
                Objects.requireNonNull(conn).rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            ClearScreen.clear();
            System.out.println("Add failed, please try again!");
            JDBCUtils.closeResource(conn, null);
            addStaff(user, ct);
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    public static void enterStaffData(User user, Curator ct , User newUser, CommonStaff newCs) throws Exception {
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

    public static void deleteStaff(User user, Curator ct) {
        int deleteStaffId;
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            conn.setAutoCommit(false);

            System.out.println("\nThis is all common staffs in the library:");
            System.out.println("-------------------------------------------------------------------------------");
            List<CommonStaff> list = COMMON_STAFF_DAO.getAll(conn);
            list.forEach(System.out::println);
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Please enter the ID of the staff you want to delete(if you want to cancel, please enter -1):");
            deleteStaffId = SCAN.nextInt();
            SCAN.nextLine();

            if ( deleteStaffId == -1 ) {
                System.out.println("Delete cancelled!");
                selectUpdateMode(user, ct);
            }

            CommonStaff deleteStaff = COMMON_STAFF_DAO.getCommonStaffById(conn, deleteStaffId);
            COMMON_STAFF_DAO.deleteById(conn, deleteStaffId);
            USER_DAO.deleteByUid(conn, deleteStaff.getUid());
            conn.commit();
            System.out.println("Delete success!");
        } catch ( InputMismatchException e ) {
            SCAN.nextLine();
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you entered is invalid, please try again!");
            JDBCUtils.closeResource(conn, null);
            deleteStaff(user, ct);
        } catch ( Exception e ) {
            e.printStackTrace();
            try {
                Objects.requireNonNull(conn).rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    public static void updateStaff(User user, Curator ct) {
        int updateStaffId;
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionWithPool();
            conn.setAutoCommit(false);

            System.out.println("\nThis is all common staffs in the library:");;
            System.out.println("-------------------------------------------------------------------------------");
            List<CommonStaff> list = COMMON_STAFF_DAO.getAll(conn);
            list.forEach(System.out::println);
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Please enter the ID of the staff you want to update(if you want to cancel, please enter -1):");
            updateStaffId = SCAN.nextInt();
            SCAN.nextLine();

            if ( updateStaffId == -1 ) {
                System.out.println("Update cancelled!");
                selectUpdateMode(user, ct);
            }

            User staffUser;
            for ( CommonStaff cs : list ) {
                if ( cs.getId() == updateStaffId ) {
                    staffUser = USER_DAO.getUserByUid(conn, cs.getUid());
                    // int oldUid = staffUser.getUid();
                    boolean isNotName,isNotUid,  isNotAccount, isNotPassword;
                    boolean[] isNotList = enterUpdateData(staffUser, cs);
                    isNotName = isNotList[0];
                    isNotUid = isNotList[1];
                    isNotAccount = isNotList[2];
                    // check
                    System.out.println("Inspecting the data you entered...");
                    boolean isRepeat = EmptyUtils.isUpdateStaffRepeat(conn, staffUser, cs, isNotName, isNotUid, isNotAccount);
                    if ( isRepeat ) {
                        System.out.println("\nThe staff you want to update is wrong, please try again!\n");
                        updateStaff(user, ct);
                    }

                    System.out.println("Inspection passed, now updating the staff...");

                    USER_DAO.update(conn, staffUser);
                    COMMON_STAFF_DAO.update(conn, cs);
                    conn.commit();

                    System.out.println("Update successful");
                }
            }
        } catch ( InputMismatchException e ) {
            SCAN.nextLine();
            e.printStackTrace();
            ClearScreen.clear();
            System.out.println("The number you entered is invalid, please try again!");
            JDBCUtils.closeResource(conn, null);
            updateStaff(user, ct);
        } catch ( Exception e ) {
            e.printStackTrace();
            try {
                Objects.requireNonNull(conn).rollback();
            } catch ( SQLException e1 ) {
                e.printStackTrace();
            }
            ClearScreen.clear();
            System.out.println("Update fail, please try again!");
            JDBCUtils.closeResource(conn, null);
            updateStaff(user, ct);
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    public static boolean[] enterUpdateData(User staffUser, CommonStaff cs) {
        final String MALE = "M", FEMALE = "F";

        System.out.println("The staff you select is follow:");
        System.out.println(staffUser);
        System.out.println(cs + "\n");

        boolean[] isNotList = new boolean[4];
        // 0:isNotName, 1:isNotUid, 2:isNotAccount, 3:isNotPassword
        for ( int i = 0; i < isNotList.length; i++ ) {
            isNotList[i] = false;
        }

        System.out.println("Please enter new name (If you don't want to change, please empty):");
        String newName = SCAN.nextLine();
        if ( newName.isBlank() ) {
            isNotList[0] = true;
        } else {
            staffUser.setUserName(newName);
            cs.setStaffName(newName);
        }
        System.out.println("Please enter new UID (If you don't want to change, please empty):");
        String newUid = SCAN.nextLine();
        if ( newUid.isBlank() ) {
            isNotList[1] = true;
        } else {
            staffUser.setUid(Integer.parseInt(newUid));
            cs.setUid(Integer.parseInt(newUid));
        }
        System.out.println("Please enter new Account (If you don't want to change, please empty):");
        String newAccount = SCAN.nextLine();
        if ( newAccount.isBlank() ) {
            isNotList[2] = true;
        } else {
            staffUser.setAccount(newAccount);
        }
        boolean isPasswordCorrect = false;
        do {
            System.out.println("Please enter new password (If you don't want to change, please empty):");
            String newFirPassword = SCAN.nextLine();
            if ( newFirPassword.isBlank() ) {
                break;
            }
            System.out.println("Please confirm the password again:");
            String newSecPassword = SCAN.nextLine();
            if ( !newFirPassword.equals(newSecPassword) ) {
                System.out.println("The two password you entered are incorrect, please try again!");
            }
            if ( newFirPassword.equals(newSecPassword) ) {
                isPasswordCorrect = true;
                staffUser.setPassword(newFirPassword);
            }
        } while ( !isPasswordCorrect );
        boolean isGenderCorrect = false;
        do {
            System.out.println("Please enter new gender (If you don't want to change, please empty):\nif male, please enter M; if female, please enter F");
            String newGender = SCAN.nextLine();
            if ( newGender.isBlank() ) {
                break;
            }
            if ( !MALE.equals(newGender) && !FEMALE.equals(newGender) ) {
                System.out.println("The gender you entered is invalid, please try again!");
                isGenderCorrect = false;
            }
            if ( MALE.equals(newGender) || FEMALE.equals(newGender) ) {
                isGenderCorrect = true;
                cs.setGender(newGender);
            }
        } while ( !isGenderCorrect );
        System.out.println("Please enter new phone number (If you don't want to change, please empty):");
        String newPhoneNum = SCAN.nextLine();
        if ( !newPhoneNum.isBlank() ) {
            cs.setPhoneNum(newPhoneNum);
        }

        return isNotList;
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
            conn = JDBCUtils.getConnectionWithPool();
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
            JDBCUtils.closeResource(conn, null);
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
            conn = JDBCUtils.getConnectionWithPool();
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
            JDBCUtils.closeResource(conn, null);
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
