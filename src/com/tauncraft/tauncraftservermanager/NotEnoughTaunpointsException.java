package com.tauncraft.tauncraftservermanager;

/**
 * Eine Exception welche geworfen wird,
 * sobald ein Spieler zu wenige Taunpoints
 * hat um eine Transaktion durchführen zu
 * können
 * 
 * @author Terradominik
 * @version 0.2
 */
public class NotEnoughTaunpointsException extends Exception {

    /**
     * Konstruktor
     */
    public NotEnoughTaunpointsException() {
    }
    
    /**
     * Gibt die Exceptionnachricht aus
     * @return die Exceptionnachricht
     */
    @Override
    public String getMessage() {
        return "Du hast nicht genügend Taunpoints";
    }
    
}
