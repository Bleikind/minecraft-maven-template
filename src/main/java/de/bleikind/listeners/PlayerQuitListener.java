package de.bleikind.listeners;

import de.bleikind.user.UserData;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        UserData.save(e.getPlayer().getUniqueId());
        e.setQuitMessage(ChatColor.RED + "[-] " + ChatColor.GRAY + e.getPlayer().getDisplayName());
    }

}
