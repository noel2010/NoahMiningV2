package noah.noahMiningV2.utils;

import noah.noahMiningV2.events.custom.OreRespawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OreRespawns {

    private static final Map<Material, Double> oreChances = new HashMap<>();

    static {
        oreChances.put(Material.COAL_ORE, 40.0);
        oreChances.put(Material.IRON_ORE, 25.0);
        oreChances.put(Material.COPPER_ORE, 15.0);
        oreChances.put(Material.GOLD_ORE, 10.0);
        oreChances.put(Material.DIAMOND_ORE, 5.0);
        oreChances.put(Material.EMERALD_ORE, 5.0);
    }

    public static void respawnOre(Location loc, Player player) {
        normalizeChances(oreChances);
        Material chosenOre = pickRandomOre(oreChances);
        if (chosenOre != null) {
            loc.getBlock().setType(chosenOre);
            OreRespawn rEvent = new OreRespawn(loc.getBlock(), loc, oreChances.toString().toLowerCase());
            rEvent.callEvent();
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
