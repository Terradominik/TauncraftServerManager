package com.tauncraft.tauncraftservermanager;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;

/**
 * Verwaltet den Datenbankzugriff des Servers
 * 
 * @author Terradominik
 * @version 0.2
 */
public class DatabaseManager {

    private static Connection con;
    private static TauncraftServerManager plugin;

    /**
     * Konstruktor
     * @param plugin 
     */
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
                System.out.println("Error DatabaseManager:\n" + ex.getMessage());
            }
        }
    }
    
    /**
     * Schließt die Connection
     */
    public static void closeConnection() {
        try {
            con.close();
        } catch (SQLException ex) {
        }
    }
    
    /**
     * Gibt ein Statement zurück
     * 
     * @return Ein Statement
     */
    public static Statement getStatement() {
        try {
            return con.createStatement();
        } catch (SQLException ex) {
            System.out.println("Error getStatement:\n" + ex.getMessage());
        }
        return null;
    }
    
    /**
     * Erstellt ein PreparedStatement
     * und gibt dieses zurück
     * 
     * @param sql Der SQL Code für dieses PreparedStatement
     * @return Das PreparedStatement
     */
    public static PreparedStatement prepareStatement(String sql) {
        try {
            return con.prepareStatement(sql);
        } catch (SQLException ex) {
            System.out.println("Error prepareStetment:\n" + ex.getMessage());
        }
        return null;
    }
}
