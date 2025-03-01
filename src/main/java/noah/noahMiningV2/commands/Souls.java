package noah.noahMiningV2.commands;

import net.kyori.adventure.text.Component;
import noah.noahMiningV2.data.PlayerData;
import noah.noahMiningV2.utils.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Souls implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p){
            PlayerData pData = new PlayerData(p.getUniqueId());
            Config config = new Config();
            Component msg = config.getColoredMessage(config.getSoulBalanceMsg(pData));
            p.sendMessage(msg);
        }

        return false;
    }
}
