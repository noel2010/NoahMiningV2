package noah.noahMiningV2.inventories;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import noah.noahMiningV2.pickaxe.CustomPickaxe;
import noah.noahMiningV2.pickaxe.enchants.ConfiguredEnchant;
import noah.noahMiningV2.pickaxe.enchants.EnchantManager;
import noah.noahMiningV2.pickaxe.enchants.infr.Enchant;
import noah.noahMiningV2.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PickaxeMenu implements InventoryHolder {

    private final EnchantManager enchantManager = new EnchantManager();
    private final Config conf = new Config();

    Inventory inv;
    Player p;
    CustomPickaxe pick;

    public PickaxeMenu(Player p, String menu, CustomPickaxe pick){
        this.p = p;
        this.pick = pick;
        inv = Bukkit.createInventory(this, 9*4, Component.text("Upgrade Menu"));

        ItemStack enchants = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = enchants.getItemMeta();
        meta.displayName(MiniMessage.miniMessage().deserialize("<#6302b3>Enchants"));
        enchants.setItemMeta(meta);

        ItemStack runes = new ItemStack(Material.FIREWORK_STAR);
        ItemMeta meta1 = runes.getItemMeta();
        meta1.displayName(MiniMessage.miniMessage().deserialize("<#ed8a00>Runes"));
        meta1.lore((List<Component>) List.of(MiniMessage.miniMessage().deserialize("<DARK_RED><BOLD>COMING SOON.")));
        runes.setItemMeta(meta1);

        inv.setItem(9, enchants);
        inv.setItem(18, runes);

        switch (menu){
            case "enchants" -> {
                List<ItemStack> items = getItemEnchantList();
                int i = 0;
                for (int slot = 0; slot < 9*4; slot++) {
                    if (inv.getItem(slot) != null) continue;
                    inv.setItem(slot, items.get(i));
                    i++;
                }
            }
        }

    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }

    public void openInventory(){
        p.openInventory(inv);
    }

    private List<ItemStack> getItemEnchantList(){
        Map<String, Enchant> enchants = enchantManager.getEnchants();
        List<ItemStack> items = new ArrayList<>();
        ItemStack enchItem = new ItemStack(Material.ENCHANTED_BOOK);
        for (Map.Entry<String, Enchant> entry : enchants.entrySet()){
            ItemMeta meta = enchItem.getItemMeta();
            meta.displayName(MiniMessage.miniMessage().deserialize("<white>"+entry.getValue().getName()));

            List<String> lore = conf.getEnchantDescription(entry.getValue().getID());
            lore.replaceAll(str->str.replace("{maxLvl}", ""+getMaxLevel(entry.getValue())));
            lore.replaceAll(str->str.replace("{level}", ""+getLevel(entry.getValue())));

            List<Component> coloredLore = conf.getColoredLore(lore);
            meta.lore(coloredLore);
            enchItem.setItemMeta(meta);

            items.add(enchItem);
        }
        return items;
    }

    private List<Enchant> getPickaxeEnchantList(){
        List<Enchant> enchants = new ArrayList<>();
        if (pick == null) return enchants;
        for (ConfiguredEnchant confEnch : pick.getConfiguredEnchants()){
            enchants.add(confEnch.getEnchant());
        }
        return enchants;
    }

    private int getMaxLevel(Enchant enchant){
        if (pick==null) return 0;
        return enchant.getMaxLevel();
    }
    private int getLevel(Enchant enchant){
        if (pick==null) return 0;
        List<ConfiguredEnchant> configuredEnchants = pick.getConfiguredEnchants();
        for (ConfiguredEnchant confEnch : configuredEnchants){
            if (confEnch.getEnchant().getID() == enchant.getID()){
                return confEnch.getLevel();
            }
        }
        return 0;
    }
}
