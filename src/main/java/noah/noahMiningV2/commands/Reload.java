package noah.noahMiningV2.commands;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import noah.noahMiningV2.NoahMiningV2;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Reload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player p){
            NoahMiningV2.INSTANCE.reloadConfig();
            p.sendMessage(MiniMessage.miniMessage().deserialize("<green>Reloaded!"));
        }
        return false;
    }
}
