package com.gregperlinli.dao;

import com.gregperlinli.bean.CommonStaff;

import java.sql.Connection;
import java.util.List;

/**
 * @Description this interface is used to define the common operation of the "CommonStaff" form
 * @author gregperlinli
 */
public interface CommonStaffDAO {

    /**
     * add object "commonStaff" to the database
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param commonStaff the CommonStaff object
     * @throws Exception e
     * @Description add object "commonStaff" to the database
     */
    void insert(Connection conn, CommonStaff commonStaff) throws Exception;

    /**
     * delete a record of the form by "id"
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param id the id of the user
     * @throws Exception e
     * @Description delete a record in the form by "id"
     */
    void deleteById(Connection conn, int id) throws Exception;

    /**
     * update the record of the form by object "user" in the memory
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param commonStaff the CommonStaff object
     * @throws Exception e
     * @Description update the record of the form by object "user" in the memory
     */
    void update(Connection conn, CommonStaff commonStaff) throws Exception;

    /**
     * promote the authority of the staff
     *
     * @author gregperlinli
     * @param conn connection of the database
     * @param commonStaff the CommonStaff object
     * @throws Exception e
     * @Description promote the authority of the staff
     */
    void promoteStaff(Connection conn, CommonStaff commonStaff) throws Exception;

    /**
     * query one data of the form by "id"
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param id the id of the commonStaff
     * @return CommonStaff object
     * @Description query one data of the form by "id"
     */
    CommonStaff getCommonStaffById(Connection conn, int id);

    /**
     * query one data of the form by "uid"
     *
     * @author gregperlinli
     * @param conn connection of database
     * @param uid the uid of the commonStaff
     * @return CommonStaff object
     * @Description query one data of the form by "uid"
     */
    CommonStaff getCommonStaffByUid(Connection conn, int uid);

    /**
     * get all of the CommonStaff Object in a list
     *
     * @author gregperlinli
     * @param conn connection of database
     * @return get all of the CommonStaff Object in a list
     */
    List<CommonStaff> getAll(Connection conn);

    /**
     * return the number of entry in the form
     *
     * @author gregperlinli
     * @param conn connection of database
     * @return the number of entry in the database
     * @Description return the number of entry in the form
     */
    Long getCount(Connection conn);
}
