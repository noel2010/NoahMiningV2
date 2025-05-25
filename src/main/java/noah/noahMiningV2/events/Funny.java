package noah.noahMiningV2.events;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class Funny implements Listener {
    @EventHandler
    public void onErrorThrowPotion(PlayerLaunchProjectileEvent e){
        if (e.getPlayer().getUniqueId() == Bukkit.getOfflinePlayer("Error5O1").getUniqueId()){
            if (e.getProjectile() instanceof ThrownPotion pot){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onErrorJoin(AsyncPlayerPreLoginEvent e){
        if (e.getPlayerProfile().getName().equalsIgnoreCase("error5o1"))
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
    }
}
