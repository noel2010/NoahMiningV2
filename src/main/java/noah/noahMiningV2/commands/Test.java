package noah.noahMiningV2.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import noah.noahMiningV2.events.custom.OreBreak;
import noah.noahMiningV2.events.custom.OreRespawn;
import noah.noahMiningV2.utils.Pickaxe;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import poa.poalib.shaded.NBT;

public class Test implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        /*if (sender instanceof Player p){
            Location loc = new Location(Bukkit.getWorld("world"), 0, 100, 0);
            OreBreak rEvent1 = new OreBreak(loc.getBlock(), loc, "iron", p);
            OreRespawn rEvent2 = new OreRespawn(loc.getBlock(), loc, "coal");
            rEvent1.callEvent();
            rEvent2.callEvent();
            if (!rEvent1.isCancelled()){}
            if (!rEvent2.isCancelled()){}
        }*/
        if (sender instanceof Player p){
            p.getInventory().addItem(new Pickaxe().getPickaxe());
            ItemStack orePlacer = new ItemStack(Material.GREEN_WOOL);
            NBT.modify(orePlacer, nbt->{nbt.setBoolean("orePlacer", true);});
            ItemMeta opMeta = orePlacer.getItemMeta();
            opMeta.displayName(MiniMessage.miniMessage().deserialize("<green>Ore Placer"));
            orePlacer.setItemMeta(opMeta);
            p.getInventory().addItem(orePlacer);
        }
        return false;
    }
}
