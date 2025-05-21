package noah.noahMiningV2.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.ItemData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CItemManager implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (commandSender instanceof Player p){
            if (args.length == 2){
                ItemData itemData = NoahMiningV2.INSTANCE.getItemData();
                switch(args[0]){
                    case "get" -> {
                        if(itemData.getItem(args[1]) != null)
                            p.getInventory().addItem(itemData.getItem(args[1]));
                        else
                            p.sendMessage(MiniMessage.miniMessage().deserialize("<red>Invalid Item ID"));
                    }
                    case "remove" -> {
                        itemData.removeItem(args[1]);
                    }
                    case "add" -> {
                        ItemStack item;
                        if (!p.getInventory().getItemInMainHand().getType().isAir())
                            item = p.getInventory().getItemInMainHand();
                        else {
                            p.sendMessage(MiniMessage.miniMessage().deserialize("<red>Error: Please hold an actual item."));
                            return false;
                        }
                        itemData.addItem(args[1], item);
                    }
                }
            } else
                p.sendMessage(MiniMessage.miniMessage().deserialize("<red>Usage: /im add/remove/get <id>"));
        }
        return false;
    }
}
