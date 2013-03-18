package com.tauncraft.tauncraftservermanager;

/**
 * @author Dominik
 */
public class NotEnoughTaunpointsException extends Exception {

    public NotEnoughTaunpointsException() {
    }
    
    @Override
    public String getMessage() {
        return "Du hast nicht genügend Taunpoints";
    }
    
}
