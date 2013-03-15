package com.tauncraft.tauncraftservermanager.datenbank;

import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Dominik
 */
public class Taunpoints {
    private TauncraftServerManager plugin;
    private Connection con;
    private static PreparedStatement get;
    
    public Taunpoints(TauncraftServerManager plugin, Connection con) {
        this.plugin = plugin;
        this.con = con;
        try {
            get = con.prepareStatement("SELECT taunpoints FROM spieler WHERE name='?'");
        } catch (SQLException ex) {
            Logger.getLogger(Taunpoints.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static int get(String name, int wert) {
        try {
            get.setString(1, name);
            ResultSet rs = get.executeQuery();
            return rs.getInt("taunpoints");
        } catch (SQLException ex) {
            Logger.getLogger(Taunpoints.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } 
    }
    
}

