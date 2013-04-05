package com.tauncraft.tauncraftservermanager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * @author
 * Dominik
 */
public class Ports {
    public TauncraftServerManager plugin;
    private static Map<String,Location> ports = new HashMap<>();
    
    
    public Ports(TauncraftServerManager plugin) {
        this.plugin = plugin;
        loadPorts();
    }
    
    public static Location getPort(String name) {
        return ports.get(name);
    }
    
    public static boolean addPort(String name, Location loc) {
        String sql;
        boolean update = ports.containsKey(name);
        if (update) {
            sql = "UPDATE ports SET name=?,world=?,x=?,y=?,z=?,yaw=?,pitch=? WHERE name=?";
        } else {
            sql = "INSERT INTO ports (name,world,x,y,z,yaw,pitch) VALUES (?,?,?,?,?,?,?);";
        }

        PreparedStatement ps = DatabaseManager.prepareStatement(sql);
        try {
            ps.setString(1, name);
            ps.setString(2, loc.getWorld().toString());
            ps.setInt(3, loc.getBlockX());
            ps.setInt(4, loc.getBlockY());
            ps.setInt(5, loc.getBlockZ());
            ps.setFloat(6, loc.getYaw());
            ps.setFloat(7, loc.getPitch());
            if (update) ps.setString(8, name);
                
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Fehler beim Speichern der Ports: " + name + " " + loc.toString());
            return false;
        }
        ports.put(name, loc);
        return true;
    }

    public static boolean removePort(String name) {
        String sql = "DELETE FROM ports WHERE name=?";
        PreparedStatement ps = DatabaseManager.prepareStatement(sql);
        try {
            ps.setString(1, name);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Fehler beim Löschen der Ports: " + name);
            return false;
        }
        ports.remove(name);
        return true;
    }
    
    private void loadPorts() {
        Statement stmnt = DatabaseManager.getStatement();
        try {
            ResultSet rs = stmnt.executeQuery("SELECT * FROM ports");
            
            String name;
            World world;
            double x,y,z;
            float yaw,pitch;
            
            while(rs.next()) {
                name = rs.getString(2);
                world = plugin.getServer().getWorld(rs.getString(3));
                if (world == null) System.out.println("Fehler beim Laden der Ports: Welt " + rs.getString(3) + " von Port " + name);
                else {
                    x = rs.getInt(4);
                    y = rs.getInt(5);
                    z = rs.getInt(6);
                    
                    yaw = rs.getFloat(7);
                    pitch = rs.getFloat(8);
                    
                    ports.put(name, new Location(world,x,y,z,yaw,pitch));
                }
            }
            
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Fehler beim Laden der Ports: " + ex.getMessage());
        }
    }
}
