package com.gregperlinli.dao;

import com.gregperlinli.bean.CommonStaff;

import java.sql.Connection;
import java.util.List;

/**
 * @Description Implements of the interface "CommonStaffDAO"
 *              used to define the common operation of the "CommonStaff" form
 * @author gregperlinli
 */
public class CommonStaffDAOImpl extends BaseDAO<CommonStaff> implements CommonStaffDAO {
    @Override
    public void insert(Connection conn, CommonStaff commonStaff) throws Exception {
        String sql = "insert into CommonStaff(staffName, uid, gender, phoneNum)values(?,?,?,?)";
        update(conn, sql, commonStaff.getStaffName(), commonStaff.getUid(), commonStaff.getGender(), commonStaff.getPhoneNum());
    }

    @Override
    public void deleteById(Connection conn, int id) throws Exception {
        String sql = "delete from CommonStaff where id = ?";
        update(conn, sql, id);
    }

    @Override
    public void update(Connection conn, CommonStaff commonStaff) throws Exception {
        String sql = "update CommonStaff set staffName = ?, uid = ?, gender = ?, phoneNum = ? where id = ?";
        update(conn, sql, commonStaff.getStaffName(), commonStaff.getUid(), commonStaff.getGender(), commonStaff.getPhoneNum(), commonStaff.getId());
    }

    @Override
    public void promoteStaff(Connection conn, CommonStaff commonStaff) throws Exception {
        String sql = "update CommonStaff set authority = ? where id = ?";
        update(conn, sql, commonStaff.getAuthority() + 10, commonStaff.getId());
    }

    @Override
    public void demoteStaff(Connection conn, CommonStaff commonStaff) throws Exception {
        String sql = "update CommonStaff set authority = ? where id = ?";
        update(conn, sql, commonStaff.getAuthority() - 10, commonStaff.getId());
    }

    @Override
    public CommonStaff getCommonStaffById(Connection conn, int id) {
        String sql = "select id, staffName, uid, gender, phoneNum, authority from CommonStaff where id = ?";
        return getQuery(conn, sql, id);
    }

    @Override
    public CommonStaff getCommonStaffByUid(Connection conn, int uid) {
        String sql = "select id, staffName, uid, gender, phoneNum, authority from CommonStaff where uid = ?";
        return getQuery(conn, sql, uid);
    }

    @Override
    public CommonStaff getCommonStaffByName(Connection conn, String staffName) {
        String sql = "select id, staffName, uid, gender, phoneNum, authority from CommonStaff where binary staffName = ?";
        return getQuery(conn, sql, staffName);
    }

    @Override
    public List<CommonStaff> getCommonStaffsByGender(Connection conn, String gender) {
        String sql = "select id, staffName, uid, gender, phoneNum, authority from CommonStaff where binary gender = ?";
        return getMultiQuery(conn, sql, gender);
    }

    @Override
    public List<CommonStaff> getAll(Connection conn) {
        String sql = "select id, staffName, uid, gender, phoneNum, authority from CommonStaff";
        return getMultiQuery(conn, sql);
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from CommonStaff";
        return getValue(conn, sql);
    }
}
