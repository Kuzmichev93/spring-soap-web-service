package com.data;

import com.example.WebServiceConfig;
//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CofigurationBD {
    private static final Logger logger = LoggerFactory.getLogger(CofigurationBD.class);
    public String DB_URL;
    public String USER;
    public String PASS;
    Statement stmt;
    Connection conn;
    public CofigurationBD(String url,String user,String pass) throws SQLException {
        this.DB_URL = url;
        this.USER = user;
        this.PASS = pass;
        connectbd();
    }



    public void connectbd() throws SQLException {
        this.conn = DriverManager.getConnection(DB_URL,USER,PASS);
        this.stmt = conn.createStatement();
        logger.info("Соединение установлено");
    }
}
