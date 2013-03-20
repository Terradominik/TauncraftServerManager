package com.tauncraft.tauncraftservermanager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Dominik
 */
public class TaunPlayer {
    public Chat writeChat;
    public HashSet<Chat> listeningChats;
    
    private int taunpoints;
    private Rang rang;
    private String name;
    private int id;
    
    private static Map<String, TaunPlayer> tp = new HashMap<>();
    
    public TaunPlayer(int id, String name, int taunpoints, Rang rang) {
        this.id = id;
        this.name = name;
        this.taunpoints = taunpoints;
        this.rang = rang;
        tp.put(name, this);
    }
    
    public void addTaunpoints(int value) {
        if (value < 0) throw new IllegalArgumentException();
        this.taunpoints += taunpoints;
    }
    
    public void removeTaunpoints(int value) throws NotEnoughTaunpointsException {
        if (value < 0) throw new IllegalArgumentException();
        if (taunpoints - value >= 0) {
            taunpoints -= value;
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
        return taunpoints;
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
}
