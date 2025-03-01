package noah.noahMiningV2.events;

import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.PlayerData;
import noah.noahMiningV2.events.custom.OreBreak;
import noah.noahMiningV2.utils.Config;
import noah.noahMiningV2.utils.OreUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import poa.poalib.shaded.NBT;
import poa.poalib.shaded.NBTBlock;
import poa.poalib.shaded.NBTCompound;

public class BlockBreak implements Listener {
    private final static Config conf = new Config();
    private static String worldName = conf.getWorldName();
    private static int respawnTime = conf.getRespawnTime();
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if (e.getBlock().getWorld() == Bukkit.getWorld(worldName))
            return;
        Bukkit.broadcastMessage("Correct World");
        NBTCompound data = new NBTBlock(e.getBlock()).getData();
        if (!data.hasTag("NoahMining;souls"))
            return;
        Bukkit.broadcastMessage("Correct data");
        e.setCancelled(true);
        Player p = e.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();
        if (i.getType() == Material.AIR || i == null)
            return;
        if (!isPickaxe(i)){
            p.sendMessage(conf.getErrorMessage("noPickaxeHeld"));
            return;
        }
        Bukkit.broadcastMessage("Correct Pickaxe");

        Location loc = e.getBlock().getLocation();
        OreBreak rEvent = new OreBreak(e.getBlock(), loc, "", p);
        rEvent.callEvent();
        if (!e.isCancelled()){
            Bukkit.broadcastMessage("Event not cancelled");
            e.getBlock().setType(Material.BEDROCK);
            Bukkit.getScheduler().runTaskLater(NoahMiningV2.INSTANCE, ()->{
                OreUtils.respawnOre(loc);
            }, 20L*respawnTime);
        }
    }

    private boolean isPickaxe(ItemStack i){
        if (NBT.get(i, nbt->(boolean)nbt.hasTag("NoahMining;Pickaxe")))
            return true;
        return false;
    }
}
