package noah.noahMiningV2.pickaxe.enchants.impl;

import noah.noahMiningV2.pickaxe.enchants.ConfiguredEnchant;
import noah.noahMiningV2.pickaxe.enchants.infr.VoidEnchant;
import noah.noahMiningV2.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Random;

public class KeyFinder implements VoidEnchant {

    private final Config conf = new Config();
    private final Random rand = new Random();

    @Override
    public void trigger(Location loc, Player p, double EnchChanceDecrease, ConfiguredEnchant enchant) {
        double baseC = Math.min(conf.getEnchantChance(getID())+(0.03* enchant.getLevel())+EnchChanceDecrease, 1.0);
        double c = rand.nextDouble();
        if(!(c <= baseC)) return;
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "givekey rare "+p.getName());
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1f,1f);
        String msg = conf.getEnchantName(getID());
        msg = msg.replace("{key}", "Rare");
        p.sendMessage(conf.getColoredMessage(msg));
    }

    @Override
    public String getName() {
        return "KeyFinder";
    }

    @Override
    public String getID() {
        return "keyfinder";
    }

    @Override
    public int getMaxLevel() {
        return conf.getEnchantLimit(getID());
    }
}
