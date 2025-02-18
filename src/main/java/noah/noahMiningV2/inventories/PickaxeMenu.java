package noah.noahMiningV2.inventories;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class PickaxeMenu implements InventoryHolder {

    Inventory inv;
    Player p;

    public PickaxeMenu(Player p, String menu){
        this.p = p;
        inv = Bukkit.createInventory(this, 54, Component.text(""));
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }

    public void openInventory(Player p){
        p.openInventory(inv);
    }
}
