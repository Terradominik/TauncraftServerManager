package com.tauncraft.tauncraftservermanager;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;

/**
 * @author Dominik
 */
public class DatabaseManager {

    private static Connection con;
    private static TauncraftServerManager plugin;

    public DatabaseManager(TauncraftServerManager plugin) {
        DatabaseManager.plugin = plugin;
        if (con == null) {
            MysqlDataSource ds = new MysqlDataSource();

            ds.setUser(plugin.getConfig().getString("datenbank.user"));
            ds.setPassword(plugin.getConfig().getString("datenbank.password"));
            ds.setServerName(plugin.getConfig().getString("datenbank.servername"));
            ds.setDatabaseName(plugin.getConfig().getString("datenbank.databasename"));
            try {
                con = ds.getConnection();
            } catch (SQLException ex) {
            }
        }
    }
    
    public static void closeConnection() {
        try {
            con.close();
        } catch (SQLException ex) {
        }
    }
    
    public static Statement getStatement() {
        try {
            return con.createStatement();
        } catch (SQLException ex) {
        }
        return null;
    }
    
    public static PreparedStatement prepareStatement(String sql) {
        try {
            return con.prepareStatement(sql);
        } catch (SQLException ex) {
        }
        return null;
    }
}
