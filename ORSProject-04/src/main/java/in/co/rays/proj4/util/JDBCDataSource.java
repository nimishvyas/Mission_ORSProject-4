package in.co.rays.proj4.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBCDataSource is a singleton utility class that manages database connections
 * using C3P0 connection pooling.
 * 
 * It reads database configuration from a resource bundle and initializes
 * a ComboPooledDataSource for efficient connection reuse.
 * 
 * Key features:
 * - Singleton pattern for single datasource instance
 * - Connection pooling using C3P0
 * - Centralized connection management
 * - Utility methods to safely close JDBC resources
 * 
 * Configuration keys used:
 * - driver
 * - url
 * - username
 * - password
 * - initialpoolsize
 * - acquireincrement
 * - maxpoolsize
 * 
 * This class is used by Model/DAO classes to obtain and manage database connections.
 * 
 * Note:
 * - Always close resources using provided closeConnection methods to avoid leaks.
 * 
 * @author Nimish
 */
public final class JDBCDataSource {

    /** Singleton instance of JDBCDataSource */
    private static JDBCDataSource jds = null;

    /** C3P0 connection pool datasource */
    private ComboPooledDataSource cpds = null;

    /** Resource bundle for database configuration */
    private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");

    /**
     * Private constructor to initialize connection pool.
     * 
     * Loads database configuration and sets up C3P0 datasource.
     */
    private JDBCDataSource() {

        try {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass(rb.getString("driver"));
            cpds.setJdbcUrl(rb.getString("url"));
            cpds.setUser(rb.getString("username"));
            cpds.setPassword(rb.getString("password"));
            cpds.setInitialPoolSize(Integer.parseInt(rb.getString("initialpoolsize")));
            cpds.setAcquireIncrement(Integer.parseInt(rb.getString("acquireincrement")));
            cpds.setMaxPoolSize(Integer.parseInt(rb.getString("maxpoolsize")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the singleton instance of JDBCDataSource.
     * 
     * Creates a new instance if not already initialized.
     * 
     * @return JDBCDataSource singleton instance
     */
    public static JDBCDataSource getInstance() {
        if (jds == null) {
            jds = new JDBCDataSource();
        }
        return jds;
    }

    /**
     * Provides a database connection from the connection pool.
     * 
     * @return Connection object or null if connection fails
     */
    public static Connection getConnection() {
        try {
            return getInstance().cpds.getConnection();
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Closes database resources: ResultSet, Statement, and Connection.
     * 
     * Ensures safe closure of all resources to prevent memory leaks.
     * 
     * @param conn database connection
     * @param stmt SQL statement
     * @param rs   result set
     */
    public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes database resources: Statement and Connection.
     * 
     * @param conn database connection
     * @param stmt SQL statement
     */
    public static void closeConnection(Connection conn, Statement stmt) {
        closeConnection(conn, stmt, null);
    }

    /**
     * Closes only the database connection.
     * 
     * @param conn database connection
     */
    public static void closeConnection(Connection conn) {
        closeConnection(conn, null);
    }
}