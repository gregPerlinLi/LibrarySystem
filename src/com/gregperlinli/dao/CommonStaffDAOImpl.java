package com.gregperlinli.dao;

import com.gregperlinli.bean.CommonStaff;

import java.sql.Connection;
import java.util.List;

/**
 * @Description Implements of the interface "CommonStaffDAO"
 *              used to define the common operation of the "CommonStaff" form
 * @author gregperlinli
 */
public class CommonStaffDAOImpl extends BaseDAO implements CommonStaffDAO {
    @Override
    public void insert(Connection conn, CommonStaff commonStaff) throws Exception {
        String sql = "insert into CommonStaff(staffName, uid, gender, phoneNum)values(?,?,?,?)";
        update(conn, sql, commonStaff.getStaffName(), commonStaff.getUid(), commonStaff.getGender(), commonStaff.getPhoneNum());
    }

    @Override
    public void deleteById(Connection conn, int id) throws Exception {
        String sql = "delete into User where id = ?";
        update(conn, sql, id);
    }

    @Override
    public void update(Connection conn, CommonStaff commonStaff) throws Exception {

    }

    @Override
    public CommonStaff getCommonStaffById(Connection conn, int id) {
        return null;
    }

    @Override
    public List<CommonStaff> getAll(Connection conn) {
        return null;
    }

    @Override
    public Long getCount(Connection conn) {
        return null;
    }
}
