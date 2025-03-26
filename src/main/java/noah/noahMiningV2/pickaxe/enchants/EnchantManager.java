package noah.noahMiningV2.pickaxe.enchants;

import lombok.Getter;
import lombok.Setter;
import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.pickaxe.CustomPickaxe;
import noah.noahMiningV2.utils.Config;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

public class EnchantManager {

    @Setter
    @Getter
    private int soulsMined;
    private Player player;

    private final Config conf = new Config();

    private NamespacedKey enchantKey = new NamespacedKey(NoahMiningV2.INSTANCE, "enchant");

    public EnchantManager(Player player){ this.player = player; soulsMined = 0; }

    private CustomPickaxe getPickaxe(ItemStack item){
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        if (!pdc.has(enchantKey)) return null;
        return CustomPickaxe.of(item);
    }

    public CustomPickaxe upgradeEnchant(CustomPickaxe pickaxe, String id){
        if(!pickaxe.getEnchants().containsKey(id)) return pickaxe;
        if (!conf.getEnchantIDs().contains(id)) return pickaxe;

        pickaxe.updateEnchants();
        return null;
    }

    public void activateEnchants(Location loc, CustomPickaxe pickaxe){

    }

}
