package noah.noahMiningV2.utils;

import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.events.custom.OreRespawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import poa.poalib.shaded.NBTBlock;
import poa.poalib.shaded.NBTCompound;

import java.util.*;

public class OreUtils {

    private static final Config conf = new Config();
    private static Map<Material, Double> oreChances = conf.getOreChances();

    public static void respawnOre(Location loc) {
        normalizeChances(oreChances);
        Material chosenOre = pickRandomOre(oreChances);
        Bukkit.broadcastMessage("Chose ore");
        if (chosenOre != null) {
            OreRespawn rEvent = new OreRespawn(loc.getBlock(), loc, chosenOre);
            rEvent.callEvent();
            Bukkit.broadcastMessage("Event called");
            if (!rEvent.isCancelled()) {
                loc.getBlock().setType(chosenOre);
                if (NoahMiningV2.mined.contains(loc)) NoahMiningV2.mined.remove(loc);
                Bukkit.broadcastMessage("Respawned");
            }
        }else{
            NoahMiningV2.INSTANCE.getLogger().severe("-------------------------");
            NoahMiningV2.INSTANCE.getLogger().severe("Selected Ore is null.");
            NoahMiningV2.INSTANCE.getLogger().severe("-------------------------");
        }
    }

    private static String oreTranslator(Material ore){
        return switch (ore){
            case COAL_ORE -> "coal";
            case IRON_ORE -> "iron";
            case COPPER_ORE -> "copper";
            case GOLD_ORE -> "gold";
            case DIAMOND_ORE -> "diamond";
            case EMERALD_ORE -> "emerald";
            default -> "null";
        };
    }

    public static void respawnSpecificOre(Location loc, Material ore){
        OreRespawn rEvent = new OreRespawn(loc.getBlock(), loc, ore);
        rEvent.callEvent();
        if (!rEvent.isCancelled()) {
            loc.getBlock().setType(ore);
        }
    }

    private static void normalizeChances(Map<Material, Double> chances) {
        double total = chances.values().stream().mapToDouble(Double::doubleValue).sum();
        if (total > 100.0) {
            for (Map.Entry<Material, Double> entry : chances.entrySet()) {
                chances.put(entry.getKey(), (entry.getValue() / total) * 100.0);
            }
        }
    }

    private static Material pickRandomOre(Map<Material, Double> chances) {
        Random random = new Random();
        double roll = random.nextDouble() * 100.0;
        double cumulative = 0.0;

        for (Map.Entry<Material, Double> entry : chances.entrySet()) {
            cumulative += entry.getValue();
            if (roll <= cumulative) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static List<Material> getOres(){
        List<Material> ores = new ArrayList<>();
        ores.add(Material.COAL_ORE);
        ores.add(Material.IRON_ORE);
        ores.add(Material.COPPER_ORE);
        ores.add(Material.GOLD_ORE);
        ores.add(Material.DIAMOND_ORE);
        ores.add(Material.EMERALD_ORE);
        return ores;
    }
}
