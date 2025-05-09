package noah.noahMiningV2.events.custom;

import lombok.Getter;
import lombok.Setter;
import noah.noahMiningV2.events.custom.enums.EventTypes;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SpecialEventStart extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Setter
    @Getter
    EventTypes eventType;


    public static HandlerList getHandlerList(){ return HANDLER_LIST; }
    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
