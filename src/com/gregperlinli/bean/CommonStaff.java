package com.gregperlinli.bean;

/**
 * @author gregperlinli
 */
public class CommonStaff {
    public final int JURISDICTION = 50;

    private int id;
    private String staffName;
    private int uid;
    private String gender;
    private String phoneNum;

    public CommonStaff(int id, String staffName, int uid, String gender, String phoneNum) {
        super();
        this.id = id;
        this.staffName = staffName;
        this.uid = uid;
        this.gender = gender;
        this.phoneNum = phoneNum;
    }

    public CommonStaff() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "CommonStaff{" +
                "id=" + id +
                ", staffName='" + staffName + '\'' +
                ", uid=" + uid +
                ", gender=" + gender +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
