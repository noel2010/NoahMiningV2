package noah.noahMiningV2.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class Config {

    private final FileConfiguration config = NoahMiningV2.INSTANCE.getConfig();
    private final Random rand = new Random();
    private final List<Material> ores = List.of(Material.COAL_ORE, Material.IRON_ORE, Material.COPPER_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE);

    public Material getItem(String enchant){
        if (Material.valueOf(config.getString("EnchantManager."+enchant+".item")).isAir()) return null;
        return Material.valueOf(config.getString("EnchantManager."+enchant+".item"));
    }
    public int getEnchantLimit(String enchant){ return config.getInt("EnchantManager."+enchant+".maxLvl");}
    public int getEnchantPrice(String enchant){ return config.getInt("EnchantManager."+enchant+".price");}
    public String getEnchantMessage(String enchant){ return config.getString("EnchantManager."+enchant+".enchMsg");}
    public double getEnchantChance(String enchant) { return config.getDouble("EnchantManager."+enchant+".chance"); }
    public int getEnchantRadius(String enchant) { if (config.getInt("EnchantManager."+enchant+".radius") > 0) return config.getInt("EnchantManager."+enchant+".radius"); return 0; }
    public double getEnchantBaseMulti(String enchant) { return config.getInt("EnchantManager."+enchant+".baseMulti"); }

    public String getBalTopMessage(){ return config.getString("messages.baltopPlayer"); }

    public String getEnchantName(String enchant) { return config.getString("EnchantManager."+enchant+".name"); }
    public List<String> getEnchantDescription(String enchant) { return config.getStringList("EnchantManager."+enchant+".description"); }

    public String getPickaxeName() { return config.getString("pickaxeName"); }
    public List<String> getPickaxeLore() { return config.getStringList("pickaxeLore"); }

    public String getSoulBalanceMsg(PlayerData pData) {
        String msg = config.getString("soulBalance");
        msg = msg.replace("{souls}", ""+pData.getSouls());
        return msg;
    }
    public String getBreakMessage() { return config.getString("oreBreak"); }

    public Map<Material, Double> getOreChances(){
        Map<Material, Double> chances = new HashMap<>();
        if (config.isConfigurationSection("oreChances")) return chances;
        int i = 0;
        Material mat = null;
        for (String key : config.getConfigurationSection("oreChances").getKeys(false)){
            try {
                if (ores.size() > i)
                    mat = ores.get(i);
                double chance = config.getDouble("oreChances."+key);
                chances.put(mat,chance);
            } catch (IllegalArgumentException | NullPointerException e){
                NoahMiningV2.INSTANCE.getLogger().severe("Invalid Material in oreChances: "+key);
                NoahMiningV2.INSTANCE.getLogger().severe("Invalid Material (Null): "+key);
            }
        }
        return chances;
    }

    private int getOreValueMax(Material ore) { return config.getInt("oreValues."+ore.toString()+".max"); }
    private int getOreValueMin(Material ore) { return config.getInt("oreValues."+ore.toString()+".min"); }
    public int getRandomOreValue(Material ore) { return rand.nextInt(getOreValueMin(ore), getOreValueMax(ore)+1); }

    public String getWorldName() { return config.getString("world"); }
    public int getRespawnTime() { return config.getInt("oreRespawn"); }

    public String getErrorMessage(String key){ return config.getString("messages."+key); }

    public double getScaleFactor() { return config.getDouble("EnchantManager.formula.scale_factor"); }
    public double getDeduct() { return config.getDouble("EnchantManager.formula.deduct"); }

    public List<String> getEnchantIDs() {
        ConfigurationSection section = config.getConfigurationSection("enchantIDs");
        if (section == null) return new ArrayList<>();
        return new ArrayList<>((Collection) section.getValues(false).values().stream()
                .map(Object::toString)
                .collect(Collectors.toList()));
    }

    public List<Double> getLootChances() {
        ConfigurationSection section = config.getConfigurationSection("lootChances");
        if (section == null) return new ArrayList<>();
        return section.getValues(false).values().stream()
                .map(obj -> {
                    if (obj instanceof Number) return ((Number) obj).doubleValue();
                    try {
                        return Double.parseDouble(obj.toString());
                    } catch (NumberFormatException e) {
                        return 0.0; // or throw, or log warning
                    }
                })
                .collect(Collectors.toList());
    }


    public List<String> getRuneIDs() { return config.getStringList("runeIDs"); }

    public Component getColoredMessage(String configMessage){ return MiniMessage.miniMessage().deserialize(configMessage); }
    public List<Component> getColoredLore(List<String> configLore) {
        List<Component> lore = new ArrayList<>();
        configLore.forEach(line->{ lore.add(MiniMessage.miniMessage().deserialize(line)); });
        return lore;
    }


    public ItemStack ConstructPickaxe(){
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta iMeta = item.getItemMeta();
        iMeta.displayName(getColoredMessage(getPickaxeName()));
        iMeta.lore(getColoredLore(getPickaxeLore()));
        iMeta.setAttributeModifiers(Material.DIAMOND_PICKAXE.getDefaultAttributeModifiers());
        iMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(iMeta);
        return item;
    }

}
