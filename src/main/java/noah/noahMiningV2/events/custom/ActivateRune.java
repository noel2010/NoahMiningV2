package noah.noahMiningV2.events.custom;

import lombok.Getter;
import lombok.Setter;
import noah.noahMiningV2.pickaxe.runes.infr.Rune;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ActivateRune extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    boolean cancelled;

    @Getter
    @Setter
    Rune rune;
    @Getter
    @Setter
    Player player;

    public ActivateRune(Rune rune, Player player){
        this.rune = rune;
        this.player = player;
    }

    @Override
    public boolean isCancelled() { return cancelled; }

    @Override
    public void setCancelled(boolean cancel) { cancelled = cancel; }

    @Override
    public @NotNull HandlerList getHandlers() {return HANDLER_LIST; }
    public static HandlerList getHandlerList(){ return HANDLER_LIST; }
}
