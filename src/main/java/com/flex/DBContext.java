package com.flex;
import java.sql.*;
import java.util.Properties;

public class DBContext {
    protected Connection dbConnection;
    protected Statement stmt;
    public boolean Connect(String connectionString) throws SQLException {
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "");
        dbConnection = DriverManager.getConnection(connectionString, info);
        return dbConnection == null;
    }
    protected ResultSet Query(String query) throws SQLException {
        try {
            stmt = dbConnection.createStatement();

            // executing SELECT query
            return stmt.executeQuery(query);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            if(stmt != null)
                stmt.close();
        }
        return null;
    }
    protected void close() throws SQLException {
        stmt.close();
    }
}