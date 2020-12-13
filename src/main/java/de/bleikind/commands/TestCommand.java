package de.bleikind.commands;

import com.google.common.collect.Lists;
import de.bleikind.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * This is a dummy command to create simple commands without automatic package scan.
 * Constructor:
 * 1. name of the plugin (example: /test)
 * 2. description
 * 3. Default permission (example: )
 */

public class TestCommand extends CommandTemplate{

    public TestCommand() {
        super("test", "A command template.", Main.getInstance().getProperties().get("project.name") + ".test");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage(ChatColor.YELLOW + "Successfully tested.");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String name, String[] args) {
        return Lists.newArrayList("test-arg1", "another-test-arg", "just-one-more");
    }
}
