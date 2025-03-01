package noah.noahMiningV2.events;

import noah.noahMiningV2.events.custom.OreBreak;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import poa.poalib.shaded.NBTBlock;
import poa.poalib.shaded.NBTCompound;

public class BreakOre implements Listener {
    @EventHandler
    public void onOreBreak(OreBreak e){
        NBTCompound data = new NBTBlock(e.getBlock()).getData();
        if (data.hasTag("souls"))
            Bukkit.broadcastMessage("Has souls");
    }
}
