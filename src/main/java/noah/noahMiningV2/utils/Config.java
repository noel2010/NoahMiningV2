package noah.noahMiningV2.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Config {

    private final FileConfiguration config = NoahMiningV2.INSTANCE.getConfig();
    private final Random rand = new Random();

    public Material getItem(String enchant){ return Material.valueOf(config.getString("Enchant"+enchant+".item")); }
    public int getSlot(String enchant) { return config.getInt("Enchant"+enchant+".slot "); }
    public int getEnchantLimit(String enchant){ return config.getInt("Enchant"+enchant+".maxLvl");}
    public int getEnchantPrice(String enchant){ return config.getInt("Enchant"+enchant+".price");}
    public String getEnchantMessage(String enchant){ return config.getString("Enchant"+enchant+".enchMsg");}
    public double getEnchantChance(String enchant) { return config.getDouble("Enchant"+enchant+".chance"); }

    public String getEnchantName(String enchant) { return config.getString("Enchant"+enchant+".name"); }
    public List<String> getEnchantDescription(String enchant) { return config.getStringList("Enchant"+enchant+".description"); }

    public String getPickaxeName() { return config.getString("pickaxeName"); }
    public List<String> getPickaxeLore() { return config.getStringList("pickaxeLore"); }

    public String getSoulBalanceMsg(PlayerData pData) {
        String msg = config.getString("soulBalance");
        msg = msg.replace("{souls}", ""+pData.getSouls());
        return msg;
    }
    public String getBreakMessage() { return config.getString("oreBreak"); }

    public List<Double> getOreChances(){
        if (config.getDoubleList("oreChances").size() < 6 || config.getDoubleList("oreChances").size() > 6){
            NoahMiningV2.INSTANCE.getLogger().severe("All ore chances are not configured correctly.");
            return null;
        }
        return config.getDoubleList("oreChances");
    }

    public int getOreValueMax(String ore) { return config.getInt("oreValues."+ore+".max"); }
    public int getOreValueMin(String ore) { return config.getInt("oreValues."+ore+".min"); }
    public int getRandomOreValue(String ore) { return rand.nextInt(getOreValueMin(ore), getOreValueMax(ore)+1); }

    public String getWorldName() { return config.getString("world"); }
    public int getRespawnTime() { return config.getInt("oreRespawn"); }

    public String getErrorMessage(String key){ return config.getString("messages"+key); }

    public Component getColoredMessage(String configMessage){ return MiniMessage.miniMessage().deserialize(configMessage); }
    public List<Component> getColoredLore(List<String> configLore) {
        List<Component> lore = new ArrayList<>();
        configLore.forEach(line->{ lore.add(MiniMessage.miniMessage().deserialize(line)); });
        return lore;
    }
}
