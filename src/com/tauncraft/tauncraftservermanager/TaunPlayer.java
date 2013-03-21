package com.tauncraft.tauncraftservermanager;

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
    private TauncraftServerManager plugin;
    
    private static Map<String, TaunPlayer> tp = new HashMap<>();
    
    public TaunPlayer(TauncraftServerManager plugin, int id, String name, int taunpoints, Rang rang) {
        this.plugin = plugin;
        this.id = id;
        this.name = name;
        totalTaunpoints = taunpoints;
        this.rang = rang;
        tp.put(name, this);
        for (Chat c : plugin.getDefaultChats()) c.addPlayer(name);
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
}
