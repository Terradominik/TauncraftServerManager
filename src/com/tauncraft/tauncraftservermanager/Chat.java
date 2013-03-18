package com.tauncraft.tauncraftservermanager;

import org.bukkit.ChatColor;

/**
 * @author Dominik
 */
public class Chat {
    private static short counter = 0;
    private short id;
    
    private String prefix;
    private String suffix;
    
    public Chat(String name) {
        id=counter++;
        prefix = "[" + name + "] ";
        suffix = ": ";
    }
    
    public Chat(String name, ChatColor color) {
        id=counter++;
        prefix = color + "[" + name + "] ";
        suffix = color + ": ";
    }
    
    public Chat(String prefix, String suffix) {
        id=counter++;
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
    public short getId() {
        return id;
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
        return id == c.getId();
    }
}
