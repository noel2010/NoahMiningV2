package noah.noahMiningV2.events;

import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.PlayerData;
import org.bukkit.Bukkit;
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
        if (!player.hasPlayedBefore()){
            PlayerData pData = new PlayerData(player.getUniqueId());
        }
    }
}
