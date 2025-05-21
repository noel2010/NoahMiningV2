package noah.noahMiningV2.commands;

import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.pickaxe.CustomPickaxe;
import noah.noahMiningV2.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PDCTestCmd implements CommandExecutor {
    private final NamespacedKey enchantKey = new NamespacedKey(NoahMiningV2.INSTANCE, "enchant");
    private final NamespacedKey runeKey = new NamespacedKey(NoahMiningV2.INSTANCE, "rune");
    private final Config conf = new Config();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p){
            Bukkit.broadcastMessage(""+NoahMiningV2.INSTANCE.getPluginMeta().getVersion());
        }
        return false;
    }
}
