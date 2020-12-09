package com.gregperlinli.dao;

import com.gregperlinli.util.JDBCUtills;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gregperlinli
 *
 * DAO: Data(base) Access Object
 * Packaged generic operation of data
 */
public abstract class BaseDAO<T> {

    private Class<T> clazz = null;

    /**
     * @Description gets the generic type in the superclass inherited by the subclass of the current BaseDAO
     * @author gregperlinli
     */
    public BaseDAO() {
        // TODO: Attention: "this" is mention to subclass object (such as User, Book) not BaseDAO object!!!
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType paramTypr = (ParameterizedType) genericSuperclass;

        // get the generic parameters of the superclass
        Type[] typeArguments = paramTypr.getActualTypeArguments();

        // get the first parameter of the generic paradigm
        clazz = (Class<T>) typeArguments[0];
    }

    /**
     * @Description  Generic add, delete and update operation (version2.0) (Consider database transaction)
     * @author gregperlinli
     * @param conn connection of database
     * @param sql sql format
     * @param args fill the placeholder(variable)
     *
     * How to output:
     * BaseDAO.update(conn, sql, ...args);
     */
    public int update(Connection conn, String sql, Object ...args) throws Exception {  // the length of variable parameters should be equal to the number of placeholders

        PreparedStatement ps = null;
        try {
            // precompiled Statement and return ps instance
            ps = conn.prepareStatement(sql);

            // fill placeholder
            for ( int i = 0; i < args.length; i++ ) {
                // Be careful to the parameter declaration exception
                ps.setObject(i+1, args[i]);
            }

            // perform operation
            return ps.executeUpdate();

        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            // close the resource
            JDBCUtills.closeResource(null, ps);
        }

        return 0;
    }


    /**
     * @Description Generic query with one object
     *              using PreparedStatement to realize the query operation by different form (version 2.0, consider to transaction)
     * @author gregperlinli
     * @param conn connection of database
     * @param sql sql format
     * @param args fill the placeholder(variable)
     * @param <T> reflect class
     * @return Class or null
     *
     * How to output:
     * ClassName className = getQuery(conn, ClassName.class, sql, ...args);
     */
    public T getQuery(Connection conn, String sql, Object...args) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();

            // get the metadata of the result set：ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // get the column of the result set through metadata
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                T t = clazz.newInstance();

                // handle each column in one row of the result set
                for (int i = 0; i < columnCount; i++) {
                    // get the value of the column
                    Object columnValue = rs.getObject(i + 1);

                    // get name of each row
                    String columnName = rsmd.getColumnLabel(i + 1);

                    // TODO: give one corresponding "columnName" of the Object "t" assignment to "columnValue" through reflex
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                return t;
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(null, ps, rs);
        }
        return null;
    }

    /**
     * @Description Generic query with multiple object
     *              using PreparedStatement to realize multi query operation by different form (version 2.0, consider to transaction)
     * @author gregperlinli
     * @param conn connection of database
     * @param sql sql format
     * @param args fill the placeholder(variable)
     * @param <T> reflect class
     * @return List or null
     *
     * How to output:
     * List<ClassName> listName = getMultiQuery(Classname.class, sql, ...args);
     *
     * How to print out:
     * listName.forEach(System.out::println);
     */
    public List<T> getMultiQuery(Connection conn, String sql, Object...args) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();

            // get the metadata of the result set：ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // get the column of the result set through metadata
            int columnCount = rsmd.getColumnCount();

            // create an aggregate object
            ArrayList<T> list = new ArrayList<T>();

            while (rs.next()) {
                T t = clazz.newInstance();

                // handle each column in one row of the result set and assignment the value to object "t"
                for (int i = 0; i < columnCount; i++) {
                    // get the value of the column
                    Object columnValue = rs.getObject(i + 1);

                    // get name of each row
                    String columnName = rsmd.getColumnLabel(i + 1);

                    // TODO: give one corresponding "columnName" of the Object "t" assignment to "columnValue" through reflex
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                list.add(t);
            }

            return list;
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(null, ps, rs);
        }
        return null;
    }

    /**
     * @Description a generic way to query the special value
     * @author gragperlinli
     * @param conn connection of database
     * @param sql sql format
     * @param args fill the placeholder
     * @param <E> Paradigms
     * @return Paradigms
     *
     * How to use：
     * Object objectName = getValue(conn, sql, ...args);
     */
    public <E> E getValue(Connection conn, String sql, Object...args) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++ ) {
                ps.setObject(i + 1, args[i]);

            }

            rs = ps.executeQuery();
            if ( rs.next() ) {
                return (E) rs.getObject(1);
            }


        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(null, ps, rs);
        }
        return null;
    }
}
