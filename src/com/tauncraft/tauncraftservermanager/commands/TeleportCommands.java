package com.tauncraft.tauncraftservermanager.commands;

import com.tauncraft.tauncraftservermanager.DatabaseManager;
import com.tauncraft.tauncraftservermanager.PortManager;
import com.tauncraft.tauncraftservermanager.SpielerListe;
import com.tauncraft.tauncraftservermanager.TauncraftServerManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Verwaltet alle Commands, welche mit dem Teleporten zu tun haben
 * 
 * @author Terradomninik
 * @version 0.2
 */
public class TeleportCommands implements CommandExecutor {

    private TauncraftServerManager plugin;

    /**
     * Konstruktor
     * 
     * @param plugin Referenz auf den TauncraftServerManager, falls benötigt
     */
    public TeleportCommands(TauncraftServerManager plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Wird beim ausführen eines Commands aufgerufen
     * 
     * @param sender Der Sender des Commands
     * @param cmd Das Command 
     * @param label Der Name des Commands 
     * @param args Die Parameter des Commands
     * @return Ob das Command erfolgreich war
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (SpielerListe.contains(sender.getName())) {
            plugin.send(sender, "Du kannst dieses Command nicht während einem Spiel benutzen");
            return true;
        }
        boolean istSpieler = sender instanceof Player;
        if (sender.hasPermission("taunsm.teleport." + cmd.getName())
                || sender.hasPermission("taunsm.teleport.*")
                || sender.hasPermission("taunsm.*")
                || sender.isOp() || !istSpieler) {
            if (istSpieler) {
                Player playersender = (Player) sender;
                switch (cmd.getName()) {
                    case "tp":
                        return tp(playersender, args);
                    case "s":
                        return s(playersender, args);
                    case "port":
                        return port(playersender, args);
                    case "portlist":
                        return portlist(playersender, args);
                    case "tploc":
                        return tploc(playersender, args);
                }
            } else {
                switch (cmd.getName()) {
                    case "tp":
                        return tpConsole(sender, args);
                }
            }
            plugin.send(sender, "Dieses Command wurde noch nicht implementiert");
        } 
        else plugin.send(sender, "Du hast nicht die nötigen Rechte");
        return true;
    }

    /**
     * Teleportiert den Sender zu einem anderen Spieler
     */
    private boolean tp(Player sender, String[] args) {
        if (args.length == 0) return false;
        
        else if (args.length == 1) {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target != null) {
                sender.teleport(target);
                plugin.send(sender, "Du wurdest zu " + target.getName() + " geportet");
                plugin.send(target, sender.getName() + " hat sich zu dir geportet");
                for (int a = 0; a < 5; a++) {
                    sender.getWorld().playEffect(sender.getLocation(), Effect.ENDER_SIGNAL, null);
                }
            } else
                plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
            return true;
        } else
            return tpConsole(sender, args);
    }

    /**
     * Teleportiert spieler1 zu spieler2
     */
    private boolean tpConsole(CommandSender sender, String[] args) {
        Player target1 = plugin.getServer().getPlayer(args[0]);
        Player target2 = plugin.getServer().getPlayer(args[1]);
        if (target1 != null && target2 != null) {
            target1.teleport(target2);
            plugin.send(sender, target1.getName() + " wurde zu " + target2.getName() + " geportet");
            plugin.send(target1, target2.getName() + " wurde von " + sender.getName() + " zu dir geportet");
            plugin.send(target2, sender.getName() + " hat dich zu " + target1.getName() + " geportet");
        } else {
            if (sender instanceof Player) plugin.getCommand("tploc").execute(sender, "tptploc", args);
            else plugin.send(sender, "Spieler konnten nicht gefunden werden");
        }
        return true;
    }

    /**
     * Teleportiert einen Spieler zum Sender
     */
    private boolean s(Player sender, String[] args) {
        if (args.length == 0) return false;
        
        Player target = plugin.getServer().getPlayer(args[0]);
        if (target != null) {
            target.teleport(sender);
            plugin.send(sender, target.getName() + " wurde zu dir geportet");
            plugin.send(target, sender.getName() + " hat dich zu ihm geportet");
        } else 
            plugin.send(sender, "Es ist kein Spieler mit dem Namen " + args[0] + " online");
        return true;
    }
    
    
    /**
     * Portet einen Spieler zu einer definierten Location
     */
    private boolean port(Player sender, String[] args) {
        if(args.length == 0) return false;
        if(args[0].equalsIgnoreCase("add")
                || args[0].equalsIgnoreCase("set")
                || args[0].equalsIgnoreCase("create")
                || args[0].equalsIgnoreCase("new")) {
            
            plugin.getCommand("addport").execute(sender, "port " + args[0], Arrays.copyOfRange(args, 1, args.length));
            return true;
        }
        if(args[0].equalsIgnoreCase("delete")
                || args[0].equalsIgnoreCase("remove")) {
            
            plugin.getCommand("removeport").execute(sender, "port " + args[0], Arrays.copyOfRange(args, 1, args.length));
            return true;
        }
        if (args[0].equalsIgnoreCase("list")
                || args[0].equalsIgnoreCase("liste")) {
            
            plugin.getCommand("portlist").execute(sender, "port " + args[0], Arrays.copyOfRange(args, 1, args.length));
            return true;
        }
                
        Location loc = PortManager.getPort(args[0]);
        if (loc == null) plugin.send(sender, "Es ist kein Port mit dem Namen " + args[0] + " registriert");
        else {
            sender.teleport(loc);
            plugin.send(sender, "Du wurdest erfolgreich zu " + args[0] + " geportet");
        }
        return true;
    }

    /**
     * Zeigt eine Liste aller verfügbaren Ports an
     */
    private boolean portlist(Player sender, String[] args) {
        int pageNr;
        try {
            pageNr = Integer.parseInt(args[0]);
            if (pageNr > 1) {
                plugin.send(sender, "Die Angegebene Zahl muss positiv sein");
                return true;
            }
        } catch (NumberFormatException | NullPointerException | ArrayIndexOutOfBoundsException e) {
            pageNr = 1;
        }
        String sql = "SELECT name,x,y,z,world FROM ports ORDER BY name LIMIT ?, ?";
        PreparedStatement ps = DatabaseManager.prepareStatement(sql);
        try {
            ps.setInt(1,(pageNr-1)*10);
            ps.setInt(2,(pageNr*20)-1);
            
            ResultSet rs = ps.executeQuery();
            
            plugin.send(sender, "Protliste Seite " + pageNr);
            while(rs.next()) {
                plugin.send(sender, rs.getRow() + " "
                        + rs.getString(1) + ": "
                        + "   " + rs.getString(2)
                        + "," + rs.getString(3)
                        + "," + rs.getString(4)
                        + " (" + rs.getString(5)+")");
            }
            
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Fehler beim Anzeigen der Portliste: " + ex.getMessage());
        }
          
        return true;
    }

    /**
     * Portet zu den angegebenen Koordinaten
     */
    private boolean tploc(Player sender, String[] args) {
        if (args.length < 2) return false;
        int x,y,z;
        try {
            x = Integer.parseInt(args[0]);
            if (args.length > 2) {
                y = Integer.parseInt(args[1]);
                z = Integer.parseInt(args[2]);
            }
            else {
                z = Integer.parseInt(args[1]);
                y = sender.getWorld().getHighestBlockYAt(x, z);
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        sender.teleport(new Location(sender.getWorld(),x,y,z));
        plugin.send(sender, "Du wurdest zu " + x + "," + y + "," + z + " geportet");
        return true;
    }
}
