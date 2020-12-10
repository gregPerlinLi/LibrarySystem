package com.gregperlinli.dao;

import com.gregperlinli.bean.Curator;

import java.sql.Connection;
import java.util.List;

/**
 * @Description Implements of the interface "CuratorDAO"
 *              used to define the common operation of the "Curator" form
 * @author gregperlinli
 */
public class CuratorDAOImpl extends BaseDAO implements CuratorDAO {
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
    public Curator getUserById(Connection conn, int id) {
        return null;
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
