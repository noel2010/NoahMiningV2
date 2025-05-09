package noah.noahMiningV2.events;

import noah.noahMiningV2.inventories.PickaxeMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (e.getClickedInventory().getHolder(false) instanceof PickaxeMenu){
            e.setCancelled(true);
        }
    }
}
