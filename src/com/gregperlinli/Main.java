package com.gregperlinli;

import com.gregperlinli.util.JDBCUtills;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
        Connection conn = JDBCUtills.getConnection();


    }
}
