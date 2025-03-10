package noah.noahMiningV2.pickaxe.enchants.registry;

import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.ench.ConfiguredItem;
import noah.noahMiningV2.pickaxe.enchants.Enchant;
import noah.noahMiningV2.pickaxe.enchants.impl.ExcavatorEnchant;
import noah.noahMiningV2.pickaxe.enchants.impl.FortuneEnchant;
import noah.noahMiningV2.pickaxe.enchants.impl.KeyfinderEnchant;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.security.spec.NamedParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnchantmentRegistry {
    private final Map<String, Enchant> enchantments = new HashMap<>();
    public EnchantmentRegistry(){registerDefaults();}

    public void register(String id, Enchant enchatment){enchantments.put(id, enchatment);}
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

    public ConfiguredItem upgradeEnchant(ItemStack item, String id){
        if (item == null || !item.hasItemMeta()) return null;

        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(NoahMiningV2.INSTANCE, "custom_enchants");
        if (!data.has(key, PersistentDataType.STRING)) return null;

        String storedEnchants = data.get(key, PersistentDataType.STRING);
        Map<String, Integer> enchantLevels = parseEnchantments(storedEnchants);
        Map<Enchant, Integer> updatedEnchants = new HashMap<>();
        for (Map.Entry<String, Integer> entry : enchantLevels.entrySet()){
            if (entry.getKey() == id){
                entry.setValue(entry.getValue()+1);
            }
            Enchant enchant = enchantments.get(entry.getKey());
            if (enchant != null) updatedEnchants.put(enchant, entry.getValue());
        }
        return ConfiguredItem.of(item,updatedEnchants);
    }

    public ItemStack applyEnchants(ItemStack item){
        if (item == null || !item.hasItemMeta()) return null;

        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(NoahMiningV2.INSTANCE, "custom_enchants");
        List<String> ids = new ArrayList<>();
        for (Map.Entry<String, Enchant> entry : enchantments.entrySet()){
            ids.add(entry.getKey());
        }
        String pdcData = ids.stream().map(id->id+":0").collect(Collectors.joining(";"));
        data.set(key, PersistentDataType.STRING, pdcData);
        return item;
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
        register("Excavator", new ExcavatorEnchant());
    }
}
