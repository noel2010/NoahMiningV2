package noah.noahMiningV2;

import noah.noahMiningV2.commands.Souls;
import noah.noahMiningV2.commands.SoulsAdmin;
import noah.noahMiningV2.commands.Test;
import noah.noahMiningV2.events.*;
import noah.noahMiningV2.placeholder.Placeholder;
import noah.noahMiningV2.utils.OreUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class NoahMiningV2 extends JavaPlugin {

    public static NoahMiningV2 INSTANCE;
    File dFolder = new File(getDataFolder(), "data");
    public File getdFolder(){ return dFolder; }
    public static Set<Location> mined = new HashSet<>();
    File data = new File(getDataFolder(), "data.yml");
    YamlConfiguration dataConfig;


    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        PluginManager pm = Bukkit.getPluginManager();
        if (!getDataFolder().exists())
            getDataFolder().mkdirs();
        if (!dFolder.exists())
            dFolder.mkdirs();
        if (!data.exists()) {
            try {
                data.createNewFile();
            } catch (IOException e) {
                getLogger().severe("File failed to create.");
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(data);

        if (pm.isPluginEnabled("PlaceholderAPI"))
            new Placeholder().register();

        getCommand("souladmin").setExecutor(new SoulsAdmin());
        getCommand("souls").setExecutor(new Souls());
        getCommand("test").setExecutor(new Test());

        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new BlockBreak(), this);
        pm.registerEvents(new BreakOre(), this);
        pm.registerEvents(new BlockPlace(), this);

        pm.registerEvents(new Funny(), this);

        if (dataConfig != null && dataConfig.isConfigurationSection("minedOres")) {
            for (String key : dataConfig.getConfigurationSection("minedOres").getKeys(false)) {
                String world = dataConfig.getString("minedOres." + key + ".world");
                int x = dataConfig.getInt("minedOres." + key + ".x");
                int y = dataConfig.getInt("minedOres." + key + ".y");
                int z = dataConfig.getInt("minedOres." + key + ".z");
                Location loc = new Location(Bukkit.getWorld(world), x, y, z);
                OreUtils.respawnOre(loc);
            }
            dataConfig.set("minedOres", null);
            try {
                dataConfig.save(data);
            } catch (IOException e) {
                getLogger().severe("Failed to save mined data.");
            }
        }
    }

    @Override
    public void onDisable() {
        int i = 0;
        for (Location loc : mined){
            String path = "mined."+i;
            dataConfig.set(path+".world", loc.getWorld().getName());
            dataConfig.set(path+".x", loc.getBlockX());
            dataConfig.set(path+".y", loc.getBlockY());
            dataConfig.set(path+".z", loc.getBlockZ());
            i++;
        }
        try {
            dataConfig.save(data);
        } catch (IOException e) {
            getLogger().severe("Failed to save mined data.");
        }
    }
}
