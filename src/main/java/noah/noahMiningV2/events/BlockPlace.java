package noah.noahMiningV2.events;

import noah.noahMiningV2.inventories.OreSelect;
import noah.noahMiningV2.utils.Config;
import noah.noahMiningV2.utils.OreUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import poa.poalib.shaded.NBT;

public class BlockPlace implements Listener {

    private static final Config conf = new Config();
    private static final String worldName = conf.getWorldName();

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if (e.getBlock().getWorld() != Bukkit.getWorld(worldName))
            return;
        Player p = e.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();
        if (!NBT.get(i, nbt -> (boolean) nbt.hasTag("orePlacer")))
            return;
        e.setCancelled(true);
        Location loc = e.getBlock().getLocation();
        OreSelect os = new OreSelect(p, loc);
    }
}
