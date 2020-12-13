package de.bleikind.user;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserData {

    private static final ConcurrentHashMap<UUID, UserData> userDatas = new ConcurrentHashMap<>();

    @Getter
    private final Player player;
    private final Properties properties = new Properties();

    /**
     * Loading custom player data from a data source, if a player is joining the server.
     * @param player The joining player.
     */
    public UserData(Player player){
        this.player = player;
        //TODO load (or create if player was not found) data from .yml or database into properties.
    }

    /**
     * If a player is leaving the server --> store data to data source.
     */
    public void save(){
        //TODO save properties in a .yml file or a database. (Redis, MySql, ...)
    }


    /**
     * Get property of given player.
     * @param key The access key to property.
     * @return The value or null if not found.
     */
    public String getProperty(String key){
        return properties.getProperty(key);
    }

    /**
     * Set property of given player.
     * @param key The key to access the given data.
     * @param value The value to store.
     */
    public void setProperty(String key, String value){
        properties.setProperty(key, value);
    }

    /**
     * Loading a player to a {@link ConcurrentHashMap} from a data source.
     * @param player The joining player.
     */
    public static void load(Player player){
        userDatas.put(player.getUniqueId(), new UserData(player));
    }

    /**
     * Saving a player to data source and removing from {@link ConcurrentHashMap}.
     * @param uuid The uuid from player.
     * @return If user is found in {@link ConcurrentHashMap}.
     */
    public static boolean save(UUID uuid){
        if(userDatas.containsKey(uuid)){
            userDatas.get(uuid).save();
            userDatas.remove(uuid);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Save all players to data source.
     */
    public static void saveAll(){
        userDatas.values().parallelStream().forEach(UserData::save);
    }

}
