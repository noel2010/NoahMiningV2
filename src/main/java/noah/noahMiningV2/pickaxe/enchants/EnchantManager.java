package noah.noahMiningV2.pickaxe.enchants;

import lombok.Getter;
import lombok.Setter;
import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.pickaxe.CustomPickaxe;
import noah.noahMiningV2.pickaxe.enchants.impl.*;
import noah.noahMiningV2.pickaxe.enchants.infr.Enchant;
import noah.noahMiningV2.utils.Config;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantManager {

    public EnchantManager() {registerDefault();}

    private final Config conf = new Config();
    private final NamespacedKey enchantKey = new NamespacedKey(NoahMiningV2.INSTANCE, "enchant");
    private final List<String> enchantIDs = conf.getEnchantIDs();

    @Getter
    private Map<String, Enchant> enchants = new HashMap<>();
    private void register(Enchant ench){ enchants.put(ench.getID(),ench); }
    private void registerDefault(){
        register(new Fortune());
        register(new Excavation());
        register(new KeyFinder());
        register(new Duplicator());
        register(new Greed());
    }


}
