package com.tauncraft.tauncraftservermanager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Dominik
 */
public class TaunPlayer {
    public Chat writeChat;
    
    private int totalTaunpoints;
    private int differTaunpoints;
    private Rang rang;
    private String name;
    private int id;
    
    private static Map<String, TaunPlayer> tp = new HashMap<>();
    
    public TaunPlayer(TauncraftServerManager plugin, int id, String name, int taunpoints, Rang rang) {
        this.id = id;
        this.name = name;
        totalTaunpoints = taunpoints;
        this.rang = rang;
        tp.put(name, this);
        System.out.println(id + ", " +  name + ", " +  taunpoints + ", " +  rang);
        writeChat = plugin.getDefaultWriteChat();
        this.setPlayerListName();
        for (Chat c : plugin.getDefaultChats()) c.addPlayer(name);
        for (Chat c : plugin.getSpecialChats(rang)) c.addPlayer(name);
    }
    
    public void addTaunpoints(int value) {
        if (value < 0) throw new IllegalArgumentException();
        totalTaunpoints += value;
        differTaunpoints += value;
    }
    
    public void removeTaunpoints(int value) throws NotEnoughTaunpointsException {
        if (value < 0) throw new IllegalArgumentException();
        if (totalTaunpoints - value >= 0) {
            totalTaunpoints -= value;
            differTaunpoints -= value;
        }
        else throw new NotEnoughTaunpointsException();
    }

    public int getID() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getTaunpoints() {
        return totalTaunpoints;
    }
    
    public int getDifferTaunpoints() {
        return differTaunpoints;
    }
    
    public Rang getRang() {
        return rang;
    }
    
    public Player getPlayer() {
        return Bukkit.getPlayer(name);
    }
    
    public static Map<String, TaunPlayer> getMap() {
        return tp;
    }
    
    public static TaunPlayer get(String name) {
        return tp.get(name);
    }
    
    public static TaunPlayer get(Player player) {
        return tp.get(player.getName());
    }
    
    public void addChat(Chat c) {
        c.addPlayer(name);
    }
    
    public void save() {
        PreparedStatement stmnt = DatabaseManager.prepareStatement("UPDATE spieler SET taunpoints=taunpoints+? WHERE id=?;");
        try {
            stmnt.setInt(1, differTaunpoints);
            stmnt.setInt(2, id);
            stmnt.executeUpdate();
            stmnt.close();
        } catch (SQLException ex) {
        }
    }
    
    public void load() {
        PreparedStatement stmnt = DatabaseManager.prepareStatement("SELECT taunPoints FROM spieler WHERE id=? LIMIT 1;");
        try {
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();
            differTaunpoints = 0;
            totalTaunpoints = rs.getInt(1);
            rs.close();
            stmnt.close();
        } catch (SQLException ex) {
        }
    }
    
    public void reload() {
        this.save();
        this.load();
    }
    
    public void setPlayerListName(){
        this.getPlayer().setPlayerListName(rang.getColor() + name);
    }
}
