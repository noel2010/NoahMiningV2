package noah.noahMiningV2.pickaxe.enchants;

import org.bukkit.event.block.BlockBreakEvent;

public interface Enchant {
    void onBlockBreak(BlockBreakEvent event, int level);
}
