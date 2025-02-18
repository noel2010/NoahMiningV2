package noah.noahMiningV2.inventories;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class OreSelect implements InventoryHolder {

    Inventory inv;
    Player p;

    public OreSelect(){
        inv = Bukkit.createInventory(this, 9, MiniMessage.miniMessage().deserialize("<red>Ore Selector"));
        ItemStack border = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.displayName(Component.text(""));
        border.setItemMeta(borderMeta);
        ItemStack random = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta randomMeta = random.getItemMeta();
        randomMeta.displayName(MiniMessage.miniMessage().deserialize("<gray>Random Ore"));
        random.setItemMeta(randomMeta);

        inv.setItem(0, border);
        inv.setItem(8, border);
        inv.setItem(7, random);
    }


    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
