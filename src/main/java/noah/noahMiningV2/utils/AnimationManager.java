package noah.noahMiningV2.utils;

import noah.noahMiningV2.NoahMiningV2;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class AnimationManager {

    public static void playExcavationAnimation(Block block) {
        Location loc = block.getLocation().add(0.5, 0.5, 0.5);
        BlockData data = block.getBlockData();

        BlockDisplay display = block.getWorld().spawn(loc, BlockDisplay.class);
        display.setBlock(data);
        display.setBillboard(Display.Billboard.CENTER);

        Vector3f translation = new Vector3f(0, 0, 0);
        Quaternionf rotation = new Quaternionf();
        Vector3f scale = new Vector3f(1, 1, 1);

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                ticks++;

                translation.y += 0.03f;
                Transformation transformation = new Transformation(
                        new Vector3f(translation),
                        new Quaternionf(rotation),
                        new Vector3f(scale),
                        new Quaternionf()
                );
                display.setTransformation(transformation);

                if (ticks >= 15) {
                    Location loc = display.getLocation();
                    block.getWorld().spawnParticle(
                            Particle.EXPLOSION,
                            loc,
                            40,
                            0.4, 0.4, 0.4,
                            0.1,
                            data
                    );
                    block.getWorld().playSound(
                            loc,
                            Sound.ENTITY_GENERIC_EXPLODE,
                            0.7f,
                            1.2f
                    );

                    display.remove();
                    cancel();
                }
            }
        }.runTaskTimer(NoahMiningV2.INSTANCE, 0L, 1L);
    }
}
