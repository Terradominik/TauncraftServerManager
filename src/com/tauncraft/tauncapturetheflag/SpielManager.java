package com.tauncraft.tauncapturetheflag;

public class SpielManager {

    private TaunCaptureTheFlag plugin;
    private boolean laufendesSpiel;
    private Spiel spiel;

    public SpielManager(TaunCaptureTheFlag plugin) {
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
