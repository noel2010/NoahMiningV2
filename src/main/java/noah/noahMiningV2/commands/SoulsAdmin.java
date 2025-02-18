package noah.noahMiningV2.commands;

import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SoulsAdmin implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // souladmin <player> <set/get/remove> [<amt>]
        Player p = (sender instanceof Player) ? (Player) sender : null;

        if (args.length < 1 || args.length > 3){
            if (p != null)
                p.sendMessage("<red>Usage: /souladmin <player> <set/get/remove>");
            else
                NoahMiningV2.INSTANCE.getLogger().warning("Usage: /souladmin <player> <set/get/remove>");
            return false;
        } else {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (target == null)
                return false;
            PlayerData pData = new PlayerData(target.getUniqueId());
            switch (args[1]){
                case "set" -> {
                    if (args.length == 3)
                        pData.setSouls(Integer.parseInt(args[2]));
                }
                case "get" -> {
                    if (args.length == 2)
                        p.sendMessage("Souls of target: "+pData.getSouls());
                }
                case "remove" -> {
                    if (args.length == 3)
                        pData.removeSouls(Integer.parseInt(args[2]));
                }
            }
        }


        return false;
    }
}
