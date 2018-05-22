package dk.eviggladegulve.sagsstyring;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
@SuppressWarnings("Duplicates")
public class AccessDB {
    static AccessDB instance = new AccessDB();
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://35.230.137.104:3306/eggNormaliseret?useSSL=false";
    static Connection con;

    private AccessDB() {
    }

    /**
     * we want to use JDBC protocol, mysql DBMS , the google cloud platform with
     * the database eggNormaliseret
     *
     * This method connects to the database with the provided username and password
     *
     * @return Connection
     */

    public  static Connection getConnection() {
        con = null;
        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DATABASE_URL, "root", "keapassword");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }


    /**
     *  This method gets an instance of the AccessDB class
     * @return instance
     */
    public static AccessDB getInstance() {
        return instance;
    }














}