package noah.noahMiningV2.events;

import noah.noahMiningV2.data.PlayerData;
import noah.noahMiningV2.inventories.PickaxeMenu;
import noah.noahMiningV2.pickaxe.CustomPickaxe;
import noah.noahMiningV2.pickaxe.enchants.ConfiguredEnchant;
import noah.noahMiningV2.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InventoryClick implements Listener {

    private final Config conf = new Config();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getHolder(false) instanceof PickaxeMenu menu){
            e.setCancelled(true);
            NamespacedKey enchantID = menu.getEnchantID();
            ItemStack item = e.getInventory().getItem(e.getSlot());
            String id;
            if (item == null) return;
            PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
            if(data.has(enchantID)) {
                id = data.get(enchantID, PersistentDataType.STRING);
            } else {
                return;
            }
            Player player = (Player) e.getWhoClicked();
            PlayerData pData = new PlayerData(player.getUniqueId());
            CustomPickaxe pickaxe = menu.getPickaxe();
            int price = conf.getCalculatedPrice(pickaxe.getSpecificEnchant(id));
            if (pickaxe.getSpecificEnchant(id).getLevel() >= pickaxe.getSpecificEnchant(id).getEnchant().getMaxLevel()){
                player.closeInventory();
                player.sendMessage(conf.getColoredMessage(conf.getErrorMessage("maxLevel")));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO,1f,1f);
                return;
            }
            if(pData.getSouls() >= price) {
                pData.removeSouls(price);
                pickaxe.upgradeEnchant(id);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1f,1f);
                new PickaxeMenu(player,"enchants",pickaxe).openInventory();
            } else {
                player.closeInventory();
                player.sendMessage(conf.getColoredMessage(conf.getErrorMessage("insufficientFunds")));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1,1f);
                return;
            }
        }
    }

}
