package com.tauncraft.tauncraftservermanager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Dominik
 */
public class TaunPlayer {
    public Chat writeChat;
    public LinkedList<Chat> listeningChats;
    
    private int taunpoints;
    private Rang rang;
    private String name;
    
    private static Map<String, TaunPlayer> tp = new HashMap<>();
    
    public TaunPlayer(String name, int taunpoints, Rang rang) {
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
    
    public String getName() {
        return name;
    }
    
    public Map<String, TaunPlayer> getMap() {
        return tp;
    }
}
