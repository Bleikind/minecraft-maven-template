package de.bleikind.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

@AllArgsConstructor
public abstract class CommandTemplate implements CommandExecutor, TabCompleter {
    @Getter
    private final String name, description, permission;
}
