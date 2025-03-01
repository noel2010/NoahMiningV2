package noah.noahMiningV2.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class Funny implements Listener {
    @EventHandler
    public void onErrorJoin(AsyncPlayerPreLoginEvent e){
        if (e.getPlayerProfile().getName().equalsIgnoreCase("error5o1"))
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
    }
}
