package noah.noahMiningV2.events.custom;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OreBreak extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private boolean cancelled;

    @Setter
    @Getter
    Block block;
    @Setter
    @Getter
    Location loc;
    @Setter
    @Getter
    String oreType;
    @Setter
    @Getter
    Player p;


    public OreBreak(Block block, Location loc, String oreType, Player p){
        this.block = block;
        this.loc = loc;
        this.oreType = oreType;
        this.p = p;
    }

    public static HandlerList getHandlerList(){ return HANDLER_LIST; }
    @Override
    public boolean isCancelled() { return this.cancelled; }
    @Override
    public void setCancelled(boolean cancelled){ this.cancelled = cancelled; }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
