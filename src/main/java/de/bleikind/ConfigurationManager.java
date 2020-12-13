package de.bleikind;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;

@RequiredArgsConstructor
public class ConfigurationManager {

    @Getter
    private final FileConfiguration fileConfiguration;

    public void check(){
        //TODO custom global plugin settings.
        fileConfiguration.options().copyDefaults(true);
        Main.getInstance().saveConfig();
    }

}
