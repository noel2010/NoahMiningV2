package noah.noahMiningV2.pickaxe.enchants.impl;

import noah.noahMiningV2.pickaxe.enchants.ConfiguredEnchant;
import noah.noahMiningV2.pickaxe.enchants.infr.ReturnEnchant;
import noah.noahMiningV2.utils.Config;
import noah.noahMiningV2.utils.OreUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

public class Duplicator implements ReturnEnchant {

    private final Config conf = new Config();
    private final Random rand = new Random();

    @Override
    public int trigger(Location loc, Player p, double EnchChanceDecrease, ConfiguredEnchant enchant) {
        double baseC = Math.min(conf.getEnchantChance(getID())+EnchChanceDecrease, 1.0);
        double c = rand.nextDouble();
        if(!(c <= baseC)) return 0;
        if (OreUtils.getOres().contains(loc.getBlock().getType())) return conf.getRandomOreValue(loc.getBlock().getType());
        return 0;
    }

    @Override
    public String getName() {
        return "Duplicator";
    }

    @Override
    public String getID() {
        return "duplicator";
    }

    @Override
    public int getMaxLevel() {
        return conf.getEnchantLimit(getID());
    }
}
