package noah.noahMiningV2.pickaxe.enchants.impl;

import noah.noahMiningV2.pickaxe.enchants.ConfiguredEnchant;
import noah.noahMiningV2.pickaxe.enchants.infr.VoidEnchant;
import noah.noahMiningV2.utils.Config;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import poa.poalib.economy.Economy;

import java.util.Random;

public class Greed implements VoidEnchant {

    private final Config conf = new Config();
    private final Random rand = new Random();

    @Override
    public void trigger(Location loc, Player p, double EnchChanceDecrease, ConfiguredEnchant enchant) {
        double baseC = Math.min(conf.getEnchantChance(getID())+(0.03*enchant.getLevel())+EnchChanceDecrease,1.0);
        double c = rand.nextDouble();
        if(!(c <= baseC)) return;
        int amt = rand.nextInt(1, 10000);
        Economy.setBalance(p, Economy.getBalance(p)+amt);
        String message = conf.getEnchantMessage(getID());
        message = message.replace("{money}", ""+amt);
        p.sendMessage(conf.getColoredMessage(message));
    }

    @Override
    public String getName() {
        return "Greed";
    }

    @Override
    public String getID() {
        return "greed";
    }

    @Override
    public int getMaxLevel() {
        return conf.getEnchantLimit(getID());
    }
}
