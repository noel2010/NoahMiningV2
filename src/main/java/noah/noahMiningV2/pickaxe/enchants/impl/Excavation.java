package noah.noahMiningV2.pickaxe.enchants.impl;

import noah.noahMiningV2.pickaxe.enchants.infr.VoidEnchant;
import noah.noahMiningV2.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Excavation implements VoidEnchant {

    private final Config conf = new Config();

    @Override
    public void trigger(Location loc, Player p, double EnchChanceDecrease) {
        Bukkit.broadcastMessage("Excavation activated");
    }

    @Override
    public String getName() {
        return "Excavator";
    }

    @Override
    public String getID() {
        return "excavator";
    }

    @Override
    public int getMaxLevel() {
        return conf.getEnchantLimit(getID());
    }
}
