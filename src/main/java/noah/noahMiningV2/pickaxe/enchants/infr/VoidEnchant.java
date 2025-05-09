package noah.noahMiningV2.pickaxe.enchants.infr;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface VoidEnchant extends Enchant {
    void trigger(Location loc, Player p, double EnchChanceDecrease);
}
