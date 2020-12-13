package de.bleikind.listeners;

import de.bleikind.Main;
import de.bleikind.user.UserData;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        UserData.load(e.getPlayer());

        e.setJoinMessage(ChatColor.GREEN + "[+]" + ChatColor.GRAY + e.getPlayer().getDisplayName());
        e.getPlayer().sendMessage(ChatColor.YELLOW + "Willkommen auf " + Main.getInstance().getProperties().get("plugin.serverName"));
    }
}