package noah.noahMiningV2.commands;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import poa.poalib.shaded.NBTBlock;
import poa.poalib.shaded.NBTCompound;

public class TestOreCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p){
            if (p.getTargetBlockExact(5).getType().isAir()) return false;
            Block block = p.getTargetBlockExact(5);
            NBTCompound data = new NBTBlock(block).getData();
            data.setInteger("NoahMining;souls", 10);
        }

        return false;
    }
}
