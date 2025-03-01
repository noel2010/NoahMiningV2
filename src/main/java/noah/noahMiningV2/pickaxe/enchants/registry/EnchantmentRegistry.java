package noah.noahMiningV2.pickaxe.enchants.registry;

import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.ench.ConfiguredItem;
import noah.noahMiningV2.pickaxe.enchants.Enchant;
import noah.noahMiningV2.pickaxe.enchants.FortuneEnchant;
import noah.noahMiningV2.pickaxe.enchants.KeyfinderEnchant;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentRegistry {
    private final Map<String, Enchant> enchantments = new HashMap<>();
    public EnchantmentRegistry(){registerDefaults();}

    public void register(String id, Enchant enchatment){enchantments.put(id, enchatment)}
    public ConfiguredItem getConfiguredItem(ItemStack item){
        if (item == null || !item.hasItemMeta()) return null;

        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(NoahMiningV2.INSTANCE, "custom_enchants");
        if (!data.has(key, PersistentDataType.STRING)) return null;

        String storedEnchantments = data.get(key, PersistentDataType.STRING);
        Map<String, Integer> enchantLevels = parseEnchantments(storedEnchantments);
        Map<Enchant, Integer> extracted = new HashMap<>();
        for (Map.Entry<String, Integer> entry : enchantLevels.entrySet()){
            Enchant enchantment = enchantments.get(entry.getKey());
            if (enchantment != null) extracted.put(enchantment, entry.getValue());
        }
        return ConfiguredItem.of(item,extracted);
    }

    private Map<String, Integer> parseEnchantments(String storedData){
        Map<String, Integer> enchantments = new HashMap<>();
        if (storedData == null || storedData.isEmpty()) return enchantments;

        String[] pairs = storedData.split(";");
        for (String pair : pairs){
            String[] parts = pair.split(":");
            if (parts.length == 2)
                enchantments.put(parts[0], Integer.parseInt(parts[1]));
        }
        return enchantments;
    }

    private void registerDefaults(){
        register("Fortune", new FortuneEnchant());
        register("Key_Finder", new KeyfinderEnchant());
    }
}
