package noah.noahMiningV2.inventories;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.pickaxe.CustomPickaxe;
import noah.noahMiningV2.pickaxe.enchants.ConfiguredEnchant;
import noah.noahMiningV2.pickaxe.enchants.EnchantManager;
import noah.noahMiningV2.pickaxe.enchants.infr.Enchant;
import noah.noahMiningV2.pickaxe.runes.RuneManager;
import noah.noahMiningV2.pickaxe.runes.infr.Rune;
import noah.noahMiningV2.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PickaxeMenu implements InventoryHolder {

    private final EnchantManager enchantManager = new EnchantManager();
    private final RuneManager runeManager = new RuneManager();
    private final Config conf = new Config();

    @Getter
    private final NamespacedKey enchantID = new NamespacedKey(NoahMiningV2.INSTANCE, "enchantID");

    Inventory inv;
    Player p;
    @Getter
    CustomPickaxe pickaxe;

    public PickaxeMenu(Player p, String menu, CustomPickaxe pickaxe){
        this.p = p;
        this.pickaxe = pickaxe;
        inv = Bukkit.createInventory(this, 9*5, Component.text("Upgrade Menu"));

        ItemStack enchants = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = enchants.getItemMeta();
        meta.displayName(MiniMessage.miniMessage().deserialize("<#6302b3>Enchants"));
        enchants.setItemMeta(meta);

        ItemStack runes = new ItemStack(Material.FIREWORK_STAR);
        ItemMeta meta1 = runes.getItemMeta();
        meta1.displayName(MiniMessage.miniMessage().deserialize("<#ed8a00>Runes"));
        meta1.lore((List<Component>) List.of(MiniMessage.miniMessage().deserialize("<DARK_RED><BOLD>COMING SOON.")));
        runes.setItemMeta(meta1);
        borderUI(inv);

        inv.setItem(9, enchants);
        inv.setItem(18, runes);

        switch (menu){
            case "enchants" -> {
                List<ItemStack> items = getItemEnchantList();
                int i = 0;
                for (int slot = 0; slot < 9*5; slot++) {
                    if (inv.getItem(slot) != null) continue;
                    if (items.size() > i)
                        inv.setItem(slot, items.get(i));
                    else
                        break;
                    i++;
                }
            }
            case "runes" -> {
                List<ItemStack> items = getItemRuneList();
                int i = 0;
                for (int slot = 0; slot < 9*5; slot++){
                    if (inv.getItem(slot) != null) continue;
                    if(items.size() > i)
                        inv.setItem(slot, items.get(i));
                    else
                        break;
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
        for (Map.Entry<String, Enchant> entry : enchants.entrySet()){
            Material material = conf.getItem(entry.getValue().getID());
            ItemStack enchItem;
            if(material == null)
                enchItem = new ItemStack(Material.DIRT);
            else
                enchItem = new ItemStack(material);
            ItemMeta meta = enchItem.getItemMeta();
            PersistentDataContainer data = meta.getPersistentDataContainer();
            data.set(enchantID, PersistentDataType.STRING, entry.getValue().getID());
            meta.displayName(MiniMessage.miniMessage().deserialize("<white>"+entry.getValue().getName()));

            List<String> lore = conf.getEnchantDescription(entry.getValue().getID());
            lore.replaceAll(str->str.replace("{maxLvl}", ""+getMaxLevel(entry.getValue())));
            lore.replaceAll(str->str.replace("{level}", ""+getLevel(entry.getValue())));

            List<Component> coloredLore = conf.getColoredLore(lore);
            meta.lore(coloredLore);
            enchItem.setItemMeta(meta);

            items.add(enchItem.clone());
        }
        return items;
    }

    private List<ItemStack> getItemRuneList(){
        Map<String, Rune> runes = runeManager.getRunes();
        List<ItemStack> items = new ArrayList<>();
        for (Map.Entry<String, Rune> entry : runes.entrySet()){
            ItemStack rune = new ItemStack(Material.FIREWORK_STAR);
            ItemMeta meta = rune.getItemMeta();
            meta.displayName(MiniMessage.miniMessage().deserialize("<i/><white>"+entry.getValue().getName()));

            rune.setItemMeta(meta);

            items.add(rune);
        }
        return items;
    }

    private List<Enchant> getPickaxeEnchantList(){
        List<Enchant> enchants = new ArrayList<>();
        if (pickaxe == null) return enchants;
        for (ConfiguredEnchant confEnch : pickaxe.getConfiguredEnchants()){
            enchants.add(confEnch.getEnchant());
        }
        return enchants;
    }

    private int getMaxLevel(Enchant enchant){
        if (pickaxe==null) return 0;
        return enchant.getMaxLevel();
    }
    private int getLevel(Enchant enchant){
        if (pickaxe==null) return 0;
        List<ConfiguredEnchant> configuredEnchants = pickaxe.getConfiguredEnchants();
        for (ConfiguredEnchant confEnch : configuredEnchants){
            if (confEnch.getEnchant() == enchant){
                return confEnch.getLevel();
            }
        }
        return 0;
    }

    private void borderUI(Inventory inventory){
        ItemStack borderItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = borderItem.getItemMeta();
        meta.displayName(Component.text(" "));
        borderItem.setItemMeta(meta);

        int size = inventory.getSize();
        int rows = size/9;
        for(int i = 0; i < 9; i++) {
            inv.setItem(i, borderItem);
        }
        for(int i = size-9; i < size; i++) {
            inv.setItem(i, borderItem);
        }
        for(int i = 0; i < rows; i++) {
            int leftI = i*9;
            int rightI = leftI+8;
            inv.setItem(leftI, borderItem);
            inv.setItem(rightI, borderItem);
        }
    }
}
