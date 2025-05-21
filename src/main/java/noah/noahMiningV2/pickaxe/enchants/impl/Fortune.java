package noah.noahMiningV2.pickaxe.enchants.impl;

import noah.noahMiningV2.pickaxe.enchants.ConfiguredEnchant;
import noah.noahMiningV2.pickaxe.enchants.infr.ReturnEnchant;
import noah.noahMiningV2.utils.Config;
import noah.noahMiningV2.utils.OreUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Fortune implements ReturnEnchant {

    private final Config conf = new Config();

    @Override
    public int trigger(Location loc, Player p, double EnchChanceDecrease, ConfiguredEnchant enchant) {
        int souls = 0;
        if (OreUtils.getOres().contains(loc.getBlock().getType()))
            souls = conf.getRandomOreValue(loc.getBlock().getType());
        souls = (int) (souls+(souls*(conf.getEnchantBaseMulti(getID())*enchant.getLevel())));
        return souls;
    }

    @Override
    public String getName() {
        return "Fortune";
    }

    @Override
    public String getID() {
        return "fortune";
    }

    @Override
    public int getMaxLevel() {
        return conf.getEnchantLimit(getID());
    }
}
