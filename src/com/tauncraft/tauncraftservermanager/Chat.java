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
    private String name;
    private Set<Player> listener = new HashSet<>();
    private Set<Rang> raenge;
    
    private String prefix;
    private String suffix;
    
    public Chat(String name, String prefix, String suffix, Set<Rang> raenge) {
        System.out.println("Neuer Chat registriert: " + name + " | " + prefix + " | " + suffix + " | " + raenge);
        this.name = name;
        this.prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        this.suffix = ChatColor.translateAlternateColorCodes('&', suffix);
        this.raenge = raenge;
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
    
    public boolean addPlayer(Player p) {
        TaunPlayer tp = TaunPlayer.get(p);
        if (raenge.contains(tp.getRang()) || raenge.size() == 0) {
            listener.add(p);
            return true;
        }
        return false;
    }
    
    public boolean addPlayer(TaunPlayer tp) {
        if (raenge.contains(tp.getRang()) || raenge.size() == 0) {
            listener.add(tp.getPlayer());
            return true;
        }
        return false;
    }
    
    public boolean addPlayer(String p) {
        TaunPlayer tp = TaunPlayer.get(p);
        if (raenge.contains(tp.getRang()) || raenge.size() == 0) {
            listener.add(tp.getPlayer());
            return true;
        }
        return false;
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
    
    public Set<Rang> getRaenge() {
        return raenge;
    }
}
