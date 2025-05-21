package noah.noahMiningV2.events;

import noah.noahMiningV2.inventories.PickaxeMenu;
import noah.noahMiningV2.pickaxe.CustomPickaxe;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RightClick implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!player.isSneaking()) return;
        if (item == null || item.getType().isAir()) return;
        if (!CustomPickaxe.of(item, false).hasProperData()) return;
        if (e.getAction().isRightClick()) new PickaxeMenu(player, "enchants", CustomPickaxe.of(item)).openInventory();
    }
}
