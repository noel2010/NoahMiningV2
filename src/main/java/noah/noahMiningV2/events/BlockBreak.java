package noah.noahMiningV2.events;

import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.events.custom.OreBreak;
import noah.noahMiningV2.pickaxe.CustomPickaxe;
import noah.noahMiningV2.utils.Config;
import noah.noahMiningV2.utils.OreUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreak implements Listener {
    private final static Config conf = new Config();
    private static String worldName = conf.getWorldName();
    private static int respawnTime = conf.getRespawnTime();
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();
        if (i.getType() == Material.AIR || i == null) return;
        if (!CustomPickaxe.isPickaxe(i)) return;
        CustomPickaxe pickaxe = CustomPickaxe.of(i);
        if (e.getBlock().getWorld() != Bukkit.getWorld(worldName)) return;
        if (!isOre(e.getBlock().getType())) return;
        e.setCancelled(true);

        Location loc = e.getBlock().getLocation();
        OreBreak rEvent = new OreBreak(e.getBlock(), loc, "", p, pickaxe);
        rEvent.callEvent();
        if (!rEvent.isCancelled()){
            NoahMiningV2.mined.add(loc);
            e.getBlock().setType(Material.BEDROCK);
            Bukkit.getScheduler().runTaskLater(NoahMiningV2.INSTANCE, ()->{
                OreUtils.respawnOre(loc);
            }, 20L*respawnTime);
        }
    }


    private boolean isOre(Material mat){
        return OreUtils.getOres().contains(mat);
    }
}
