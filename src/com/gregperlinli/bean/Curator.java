package com.gregperlinli.bean;

/**
 * @author gregperlinli
 */
public class Curator {
    private int id;
    private String curatorName;
    private int uid;
    private String gender;
    private String phoneNum;
    private String email;
    private int authority;

    public Curator(int id, String curatorName, int uid, String gender, String phoneNum, String email, int authority) {
        super();
        this.id = id;
        this.curatorName = curatorName;
        this.uid = uid;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.email = email;
        this.authority = authority;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return /*"Curator{" +*/
                "id=" + id +
                ",\t curatorName='" + curatorName + '\'' +
                ",\t uid=" + uid +
                ",\t gender='" + gender + '\'' +
                ",\t phoneNum='" + phoneNum + '\'' +
                ",\t email='" + email + '\'' +
                ",\t authority=" + authority /*+
                '}'*/;
    }

}
