package com.gregperlinli.bean;

/**
 * @author gregperlinli
 */
public class Curator {
    private int id;
    private String curatorName;
    private int uid;
    private char gender;
    private String phoneNum;
    private String email;

    public Curator(int id, String curatorName, int uid, char gender, String phoneNum, String email) {
        super();
        this.id = id;
        this.curatorName = curatorName;
        this.uid = uid;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public Curator() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCuratorName() {
        return curatorName;
    }

    public void setCuratorName(String curatorName) {
        this.curatorName = curatorName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
