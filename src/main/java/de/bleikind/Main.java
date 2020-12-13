package de.bleikind;

import de.bleikind.commands.CommandTemplate;
import de.bleikind.user.UserData;
import de.bleikind.util.Helper;
import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Level;


public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Getter
    private final Properties properties = new Properties();

    @Getter
    private ConfigurationManager configurationManager;

    public Main() {
        instance = this;
        loadProperties(); // load maven build information.
    }

    @Override
    public void onEnable() {
        super.onEnable();
        configurationManager = new ConfigurationManager(getConfig());
        configurationManager.check(); // check global plugin properties.

        loadListeners(); // load all listeners.
        loadCommands(); // load all commands.
    }

    @Override
    public void onDisable() {
        super.onDisable();

        UserData.saveAll();
    }

    /**
     * Fetch {@link de.bleikind.listeners} package for listeners and load them.
     */
    private void loadListeners(){
        Set<Class<? extends Listener>> classes = Helper.getClasses("de.bleikind.listeners", Listener.class);

        if(classes == null){
            getLogger().log(Level.WARNING, "No listeners found.");
            return;
        }

        classes.parallelStream().forEach(c -> {
            try {
                Listener listener = c.getDeclaredConstructor().newInstance();
                getServer().getPluginManager().registerEvents(listener, this);
                getLogger().info("Loaded listener: " + listener.getClass().getSimpleName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                getLogger().log(Level.SEVERE, e.getMessage(), e);
            }
        });

    }

    /**
     * Fetch {@link de.bleikind.commands} package for commands and load them.
     * IMPORTANT: Check if command is in plugin.yml.
     */
    private void loadCommands(){
        try {

            Set<Class<? extends CommandTemplate>> classes = Helper.getClasses("de.bleikind.commands", CommandTemplate.class);
            if(classes == null){
                getLogger().log(Level.WARNING, "No commands found.");
                return;
            }

            classes.parallelStream().forEach(c -> {
                try {
                    CommandTemplate template = c.getDeclaredConstructor().newInstance();
                    PluginCommand command = getCommand(template.getName());

                    if(command == null){
                        getLogger().log(Level.WARNING, "Command " + template.getName() + " does not exist in plugin.yml.");
                        return;
                    }

                    command.setDescription(template.getDescription());
                    command.setPermission(template.getPermission());
                    command.setExecutor(template);
                    command.setTabCompleter(template);
                    getLogger().info("Loaded command: " + template.getName());
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    getLogger().log(Level.SEVERE, e.getMessage(), e);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Loading app.properties data from maven build to properties.
     */
    private void loadProperties() {
        try(InputStream stream = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            properties.load(stream);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
