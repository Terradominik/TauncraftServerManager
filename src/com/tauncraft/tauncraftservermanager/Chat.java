package com.tauncraft.tauncraftservermanager;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author Dominik
 */
public class Chat {
    private static Set<Chat> allChats = new HashSet<>();
    private static short counter = 0;
    private String name;
    private Set<Player> listener = new HashSet<>();
    
    private String prefix;
    private String suffix;
    
    public Chat(String name) {
        this.name = name;
        prefix = "[" + name + "] ";
        suffix = ": ";
        allChats.add(this);
    }
    
    public Chat(String name, ChatColor color) {
        this.name = name;
        prefix = color + "[" + name + "] ";
        suffix = color + ": ";
        allChats.add(this);
    }
        
    public Chat(String name, String displayName, ChatColor color) {
        this.name = name;
        prefix = color + "[" + displayName + "] ";
        suffix = color + ": ";
        allChats.add(this);
    } 
    
    public Chat(String name, String prefix, String suffix) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        allChats.add(this);
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public String getSuffix() {
        return suffix;
    }
    
    public String getFormat() {
        return prefix + suffix;
    }
    
    public boolean equals(Chat c) {
        return name.equals(c.getName());
    }
    
    public void addPlayer(Player p) {
        listener.add(p);
    }
    
    public void addPlayer(TaunPlayer p) {
        listener.add(p.getPlayer());
    }
    
    public void addPlayer(String p) {
        listener.add(Bukkit.getPlayer(p));
    }
    
    public void removePlayer(Player p) {
        listener.remove(p);
    }
    
    public void removePlayer(TaunPlayer p) {
        listener.remove(p.getPlayer());
    }
    
    public void removePlayer(String p) {
        listener.remove(Bukkit.getPlayer(p));
    }
    
    public Set<Player> getListener() {
        return listener;
    }
    
    public static Set<Chat> getChats() {
        return allChats;
    }
    
    public String getName() {
        return name;
    }
}
