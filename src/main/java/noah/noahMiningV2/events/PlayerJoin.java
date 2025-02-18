package noah.noahMiningV2.events;

import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.PlayerData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        PlayerData pData = null;
        if (!player.hasPlayedBefore()){
            pData = new PlayerData(player.getUniqueId());
        }
        if (pData == null)
            return;
        File dataFile = new File(NoahMiningV2.INSTANCE.getDataFolder().getParent() + "NoahMining/PlayerData"+ player);
        if (dataFile.exists()){
            YamlConfiguration dConfig = YamlConfiguration.loadConfiguration(dataFile);
            if (!dConfig.contains("tokens"))
                return;
            pData.setSouls(dConfig.getInt("tokens"));
        }
    }
}
