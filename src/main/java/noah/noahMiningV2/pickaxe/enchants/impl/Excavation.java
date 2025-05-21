package noah.noahMiningV2.pickaxe.enchants.impl;

import com.destroystokyo.paper.ParticleBuilder;
import net.kyori.adventure.text.minimessage.MiniMessage;
import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.PlayerData;
import noah.noahMiningV2.pickaxe.enchants.ConfiguredEnchant;
import noah.noahMiningV2.pickaxe.enchants.infr.VoidEnchant;
import noah.noahMiningV2.utils.Config;
import noah.noahMiningV2.utils.OreUtils;
import org.bukkit.*;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Excavation implements VoidEnchant {

    private final Config conf = new Config();
    private final Random rand = new Random();


    @Override
    public void trigger(Location loc, Player p, double EnchChanceDecrease, ConfiguredEnchant enchant) {
        double baseC = Math.min(conf.getEnchantChance(getID())+(0.03*enchant.getLevel())+EnchChanceDecrease, 1.0);
        double c = rand.nextDouble();
        int radius = conf.getEnchantRadius(getID());
        if(!(c <= baseC)) return;

        World world = loc.getWorld();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        List<Location> oreList = new ArrayList<>();
        for(int xC = -radius; xC <= radius; xC++){
            for (int zC = -radius; zC <= radius; zC++){
                for (int yC = -radius; yC <= radius; yC++){
                    if(loc.getBlock().getType().isAir()) continue;
                    if (OreUtils.getOres().contains(loc.getBlock().getType())) oreList.add(loc);
                }
            }
        }
        int mined = 0;
        int souls = 0;
        for(Location location : oreList){
            if (!(rand.nextDouble() <= 0.40)) continue;
            souls += conf.getRandomOreValue(location.getBlock().getType());
            mined++;
            Particle explosion = new ParticleBuilder(Particle.EXPLOSION).particle();
            location.getWorld().spawnParticle(explosion, location, 25);
            location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE,1f,1f);
            location.getBlock().setType(Material.BEDROCK);
            NoahMiningV2.mined.add(location);
            OreUtils.respawnOre(location);
        }
        PlayerData data = new PlayerData(p.getUniqueId());
        data.addSouls(souls);
        String message = conf.getEnchantMessage(getID());
        message = message.replace("{souls}", ""+souls).replace("{mined}", ""+mined);
        if(mined > 1)
            p.sendMessage(conf.getColoredMessage(message));
        else
            p.sendMessage(MiniMessage.miniMessage().deserialize("<RED>Unlucky, excavator activated but there were no ores around."));
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
