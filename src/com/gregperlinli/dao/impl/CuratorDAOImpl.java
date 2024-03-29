package com.gregperlinli.dao.impl;

import com.gregperlinli.bean.Curator;
import com.gregperlinli.dao.BaseDAO;
import com.gregperlinli.dao.CuratorDAO;

import java.sql.Connection;
import java.util.List;

/**
 * @Description Implements of the interface "CuratorDAO"
 *              used to define the common operation of the "Curator" form
 * @author gregperlinli
 */
public class CuratorDAOImpl extends BaseDAO<Curator> implements CuratorDAO {
    @Override
    public void insert(Connection conn, Curator curator) throws Exception {

    }

    @Override
    public void deleteById(Connection conn, int id) throws Exception {

    }

    @Override
    public void update(Connection conn, Curator curator) throws Exception {

    }

    @Override
    public Curator getCuratorById(Connection conn, int id) {
        return null;
    }

    @Override
    public Curator getCuratorByUid(Connection conn, int uid) {
        String sql = "select id, curatorName, uid, gender, phoneNum, email, authority from Curator where uid = ?";
        return getQuery(conn, sql, uid);
    }

    @Override
    public List<Curator> getAll(Connection conn) {
        return null;
    }

    @Override
    public Long getCount(Connection conn) {
        return null;
    }
}
