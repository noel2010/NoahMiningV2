package noah.noahMiningV2.events.custom;

import lombok.Getter;
import lombok.Setter;
import noah.noahMiningV2.pickaxe.enchants.infr.Enchant;
import noah.noahMiningV2.pickaxe.runes.infr.Rune;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ActivateEnchant extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    boolean cancelled;

    @Getter
    @Setter
    Enchant enchant;
    @Getter
    @Setter
    Player player;

    public ActivateEnchant(Enchant enchant, Player player){
        this.enchant = enchant;
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
