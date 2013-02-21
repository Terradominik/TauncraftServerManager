package com.tauncraft.tauncraftservermanager;

public class SpielManager {

    private TauncraftServerManager plugin;
    private boolean laufendesSpiel;
    private Spiel spiel;

    public SpielManager(TauncraftServerManager plugin) {
        this.plugin = plugin;
        laufendesSpiel = false;
    }
    
    public void starteSpiel() {
       if (!laufendesSpiel) {
            spiel = new Spiel();
            laufendesSpiel = true;
       }
    }
    
    public void stoppeSpiel() {
        if (laufendesSpiel) {
            spiel = null;
            plugin.getServer().getScheduler().cancelTasks(plugin);
            laufendesSpiel = false;
        }
    }
}
