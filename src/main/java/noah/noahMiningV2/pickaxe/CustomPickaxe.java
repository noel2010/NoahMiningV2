package noah.noahMiningV2.pickaxe;

import lombok.Getter;
import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.utils.Config;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomPickaxe {

    @Getter
    private ItemStack pickaxeItem;

    private NamespacedKey enchantKey = new NamespacedKey(NoahMiningV2.INSTANCE, "enchant");
    private final Config conf = new Config();

    private CustomPickaxe(ItemStack item){
        pickaxeItem = item;
        setDefaultEnchants();
    }

    private CustomPickaxe(ItemStack item, Map<String, Integer> enchants){
        item.getItemMeta().getPersistentDataContainer().set(enchantKey, PersistentDataType.STRING, MapToStringEnchants(enchants));
        pickaxeItem = item;
    }

    public static CustomPickaxe of(ItemStack item){ return new CustomPickaxe(item); }

    public Map<String, Integer> getEnchants(){
        if (!pickaxeItem.getItemMeta().getPersistentDataContainer().has(enchantKey)) return null;
        PersistentDataContainer pdc = pickaxeItem.getItemMeta().getPersistentDataContainer();
        String storedData = pdc.get(enchantKey, PersistentDataType.STRING);
        return parseEnchants(storedData);
    }

    private Map<String, Integer> parseEnchants(String data){
        Map<String, Integer> enchants = new HashMap<>();
        if (data==null || data.isEmpty()) return enchants;

        String[] pairs = data.split(";");
        for (String pair : pairs){
            String[] parts = pair.split(":");
            if (parts.length == 2) enchants.put(parts[0], Integer.parseInt(parts[1]));
        }
        return enchants;
    }

    private void setDefaultEnchants(){
        PersistentDataContainer pdc = pickaxeItem.getItemMeta().getPersistentDataContainer();
        List<String> enchantIDs = conf.getEnchantIDs();
        Map<String, Integer> enchants = new HashMap<>();
        for (String id : enchantIDs) enchants.put(id, 0);
        pdc.set(enchantKey, PersistentDataType.STRING, MapToStringEnchants(enchants));
    }

    public void updateEnchants(){
        PersistentDataContainer pdc = pickaxeItem.getItemMeta().getPersistentDataContainer();
        if (!pdc.has(enchantKey)) return;
        List<String> enchantIDs = conf.getEnchantIDs();
        Map<String, Integer> enchants = getEnchants();
        List<String> enchantList = enchants.keySet().stream().toList();
        if (enchantIDs.size() == enchantList.size()) return;

        for (String enchant : enchantIDs){
            if (enchantList.contains(enchant)) continue;
            enchants.put(enchant, 0);
        }
        pdc.set(enchantKey, PersistentDataType.STRING, MapToStringEnchants(enchants));
    }

    private String MapToStringEnchants(Map<String, Integer> enchants){
        return enchants.entrySet().stream()
                .map(entry->entry.getKey()+":"+entry.getValue())
                .collect(Collectors.joining(";"));
    }

}
