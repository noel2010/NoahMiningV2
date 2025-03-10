package noah.noahMiningV2.pickaxe.enchants;

import noah.noahMiningV2.events.BreakOre;
import noah.noahMiningV2.events.custom.OreBreak;
import org.bukkit.event.block.BlockBreakEvent;

public interface Enchant {
    void onOreBreak(OreBreak event, int level);
}
