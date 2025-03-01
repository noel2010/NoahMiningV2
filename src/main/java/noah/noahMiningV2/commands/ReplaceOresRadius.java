package noah.noahMiningV2.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import noah.noahMiningV2.utils.OreUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import poa.poalib.BlockUtil.Radius;

import java.util.List;

public class ReplaceOresRadius implements CommandExecutor { // /setOres <radius> <material>
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p){
            if (args.length != 2){
                p.sendMessage(MiniMessage.miniMessage().deserialize("<red>Usage: /setOres <radius> <material>"));
                return false;
            }
            Location loc = p.getLocation();
            List<Block> toBeReplaced = Radius.getCircle(loc, Integer.parseInt(args[0]));
            for (Block block : toBeReplaced){
                if (block.getType() != Material.valueOf(args[1]))
                    continue;
                OreUtils.respawnOre(block.getLocation());
            }
        }
        return false;
    }
}
