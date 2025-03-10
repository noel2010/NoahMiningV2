package noah.noahMiningV2.pickaxe.enchants.impl;

import com.destroystokyo.paper.ParticleBuilder;
import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.PlayerData;
import noah.noahMiningV2.events.custom.OreBreak;
import noah.noahMiningV2.pickaxe.enchants.Enchant;
import noah.noahMiningV2.utils.Config;
import noah.noahMiningV2.utils.OreUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import poa.poalib.shaded.NBTBlock;
import poa.poalib.shaded.NBTCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExcavatorEnchant implements Enchant {
    private final static Config conf = new Config();
    private static int radius = conf.getEnchantRadius("excavator");
    private final Random rand = new Random();
    private static int respawnTime = conf.getRespawnTime();
    private static String enchMsg = conf.getEnchantMessage("excavator");

    @Override
    public void onOreBreak(OreBreak event, int level) {
        if (level<1) return;
        double baseCh = Math.min(conf.getEnchantChance("excavator")+(0.03*level), 1.0);
        if (rand.nextDouble() <= baseCh){
            Location loc = event.getLoc();
            World w = loc.getWorld();
            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();
            List<Location> visited = new ArrayList<>();
            for (int xC = -radius; xC <= radius; xC++){
                for (int zC = -radius; zC <= radius; zC++){
                    for (int yC = -radius; yC <= radius; yC++){
                        Location nLoc = new Location(w, x+xC,y+yC,z+zC);
                        if (loc.getBlock().getType().isAir())
                            continue;
                        NBTCompound data = new NBTBlock(nLoc.getBlock()).getData();
                        if (data.hasTag("NoahMining;souls"))
                            if (rand.nextDouble() <= 0.30)
                                visited.add(nLoc);
                    }
                }
            }
            int souls = 0;
            int mined = 0;
            for (Location ore : visited){
                NBTCompound data = new NBTBlock(ore.getBlock()).getData();
                if (data.hasTag("NoahMining;souls")){
                    souls += data.getInteger("NoahMining;souls");
                    mined++;
                    Particle explosion = new ParticleBuilder(Particle.EXPLOSION).particle();
                    ore.getWorld().spawnParticle(explosion, ore, 25);
                    ore.getWorld().playSound(ore, Sound.ENTITY_GENERIC_EXPLODE,1f,1f);
                    Player p = event.getP();
                    OreBreak rEvent = new OreBreak(ore.getBlock(), ore, "", p);
                    rEvent.callEvent();
                    if (!rEvent.isCancelled()){
                        ore.getBlock().setType(Material.BEDROCK);
                        Bukkit.getScheduler().runTaskLater(NoahMiningV2.INSTANCE, ()->{
                            OreUtils.respawnOre(loc);
                        }, 20L*respawnTime);
                        PlayerData playerData = new PlayerData(p.getUniqueId());
                        playerData.addSouls(souls);
                        enchMsg = enchMsg.replace("{mined}", ""+mined);
                        enchMsg = enchMsg.replace("{souls}", ""+souls);
                        p.sendMessage(conf.getColoredMessage(enchMsg));
                    }
                }
            }
        }
    }
}
