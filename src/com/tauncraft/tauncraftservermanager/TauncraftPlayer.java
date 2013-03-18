package com.tauncraft.tauncraftservermanager;

import java.util.LinkedList;

/**
 * @author Dominik
 */
public class TauncraftPlayer {
    public Chat writeChat;
    public LinkedList<Chat> listeningChats;
    
    private int taunpoints;
    private Rang rang;
    private String name;
    
    public TauncraftPlayer(String name, int taunpoints, Rang rang) {
        this.name = name;
        this.taunpoints = taunpoints;
        this.rang = rang;
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
}
