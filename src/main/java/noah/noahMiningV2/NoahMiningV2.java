package noah.noahMiningV2;

import noah.noahMiningV2.commands.SoulsAdmin;
import noah.noahMiningV2.data.PlayerData;
import noah.noahMiningV2.events.PlayerJoin;
import noah.noahMiningV2.placeholder.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class NoahMiningV2 extends JavaPlugin {

    public static NoahMiningV2 INSTANCE;
    File dFolder = new File(getDataFolder(), "data");
    public File getdFolder(){ return dFolder; }


    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        PluginManager pm = Bukkit.getPluginManager();
        if (!getDataFolder().exists())
            getDataFolder().mkdirs();
        if (!dFolder.exists())
            dFolder.mkdirs();

        if (pm.isPluginEnabled("PlaceholderAPI"))
            new Placeholder().register();

        getCommand("souladmin").setExecutor(new SoulsAdmin());

        pm.registerEvents(new PlayerJoin(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
