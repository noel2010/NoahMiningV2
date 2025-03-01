package noah.noahMiningV2.utils;

import noah.noahMiningV2.events.custom.OreRespawn;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import poa.poalib.shaded.NBTBlock;
import poa.poalib.shaded.NBTCompound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OreUtils {

    private static Map<Material, Double> oreChances = new HashMap<>();
    private static final Config config = new Config();
    private static List<Double> chances = config.getOreChances();
    private static final Config conf = new Config();

    static {
        oreChances.put(Material.COAL_ORE, chances.getFirst());
        oreChances.put(Material.IRON_ORE, chances.get(1));
        oreChances.put(Material.COPPER_ORE, chances.get(2));
        oreChances.put(Material.GOLD_ORE, chances.get(3));
        oreChances.put(Material.DIAMOND_ORE, chances.get(4));
        oreChances.put(Material.EMERALD_ORE, chances.getLast());
    }

    public static void respawnOre(Location loc) {
        normalizeChances(oreChances);
        Material chosenOre = pickRandomOre(oreChances);
        if (chosenOre != null) {
            OreRespawn rEvent = new OreRespawn(loc.getBlock(), loc, chosenOre.toString());
            rEvent.callEvent();
            if (!rEvent.isCancelled()) {
                NBTCompound data = new NBTBlock(loc.getBlock()).getData();
                data.setInteger("NoahMining;souls", conf.getRandomOreValue(oreTranslator(chosenOre)));
                loc.getBlock().setType(chosenOre);
            }
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
        OreRespawn rEvent = new OreRespawn(loc.getBlock(), loc, ore.toString());
        rEvent.callEvent();
        if (!rEvent.isCancelled()) {
            NBTCompound data = new NBTBlock(loc.getBlock()).getData();
            data.setInteger("NoahMining;souls", conf.getRandomOreValue(oreTranslator(ore)));
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
}
