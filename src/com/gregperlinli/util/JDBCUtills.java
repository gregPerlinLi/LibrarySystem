package com.gregperlinli.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author gregperlinli
 * @Description Tools for operating the database
 */
public class JDBCUtills {

    /* Database connection method */

    /**
     * @author gregPerlinLi
     * @Description Get the link of the database
     * @return Connection
     * @throws Exception e
     */
    public static Connection getConnection() throws Exception {

        InputStream is = DatabaseConnect.class.getClassLoader().getResourceAsStream("com/gregperlinli/util/jdbc.properties");

        Properties pros = new Properties();
        pros.load(is);

        final String USER = pros.getProperty("user"),
                PASSWORD = pros.getProperty("password"),
                URL = pros.getProperty("url"),
                DRIVER_CLASS = pros.getProperty("driverClass");

        // load the Driver
        Class.forName(DRIVER_CLASS);

        // get connection
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        // System.out.println("Connection status: " + conn);

        return conn;
    }

    /**
     * @author gregperlinli
     * @param conn the connection of the database
     * @param ps the statement of the database
     * @Description close the resource of the database in update mode
     */
    public static void closeResource(Connection conn, Statement ps) {
        try {
            if ( ps != null ) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if ( conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* TODO: Attention! the following connection method is using with database connection pools */

    /**
     * Using Druid database connection pool
     * The database connection pool just one can be provided
     */
    private static DataSource source;
    static {
        try {
            Properties pros = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("./com/gregperlinli/util/druid.properties");
            pros.load(is);
            source = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author gregperlinli
     * @return connection with database connection pool "Druid"
     * @throws Exception e
     * @Description using c3p0 database connection pool to get connection
     */
    public static Connection getConnectionWithPool() throws Exception {
        // return CPDS.getConnection();

        return source.getConnection();

    }


    /* Close resource method */

    /**
     * @author gregperlinli
     * @param conn the connection of the database
     * @param ps the statement of the database
     * @param rs the result set of the database
     * @Description close the resource of the database in query mode
     */
    public static void closeResource(Connection conn, Statement ps, ResultSet rs) {
        try {
            if ( ps != null ) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if ( conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if ( rs != null ) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
