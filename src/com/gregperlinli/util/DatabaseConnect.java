package com.gregperlinli.util;

// import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author gregperlinli
 * @Description the connection of the database
 */
public class DatabaseConnect {

    /**
     * Make a connection to the database
     * @throws Exception e
     */
    @Test
    public static void databaseConnection() throws Exception {
        // read basic information from properties
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
        // System.out.println(conn);

    }
}
