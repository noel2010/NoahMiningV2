package noah.noahMiningV2.commands;

import noah.noahMiningV2.utils.AnimationManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AnimationTestCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p){
            Block block = p.getTargetBlockExact(10);
            AnimationManager.playExcavationAnimation(block);
        }
        return false;
    }
}
