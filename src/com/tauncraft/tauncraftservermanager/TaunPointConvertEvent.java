package com.tauncraft.tauncraftservermanager;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
/**
 * Konvertiert die Taunpoints von plugins
 * @author
 * Dominik
 */
public class TaunPointConvertEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String message;
    private TaunPlayer tp;

    public TaunPointConvertEvent(TaunPlayer tp) {
        System.out.println("Starte Convertierungsvorgang für " + tp.getName());
        this.tp = tp;
    }
    
    public TaunPlayer getTaunPlayer() {
        return tp;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
