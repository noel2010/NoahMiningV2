package noah.noahMiningV2.events;

import noah.noahMiningV2.events.custom.OreBreak;
import noah.noahMiningV2.pickaxe.CustomPickaxe;
import noah.noahMiningV2.utils.OreUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class BreakOre implements Listener {
    @EventHandler
    public void onOreBreak(OreBreak e){
        Player p = e.getP();
        ItemStack item = p.getInventory().getItemInMainHand();
        if (!OreUtils.getOres().contains(e.getBlock().getType())) return;
        if (CustomPickaxe.of(item).hasEnchants()) CustomPickaxe.of(item).triggerPickaxe(p, e.getLoc());
    }
}
