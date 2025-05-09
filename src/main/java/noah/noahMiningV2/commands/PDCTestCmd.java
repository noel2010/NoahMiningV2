package noah.noahMiningV2.commands;

import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.pickaxe.CustomPickaxe;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class PDCTestCmd implements CommandExecutor {
    private final NamespacedKey enchantKey = new NamespacedKey(NoahMiningV2.INSTANCE, "enchant");
    private final NamespacedKey runeKey = new NamespacedKey(NoahMiningV2.INSTANCE, "rune");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p){
            ItemStack item = p.getInventory().getItemInMainHand();
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
            if (!pdc.has(enchantKey) && !pdc.has(runeKey)) return false;
            p.sendMessage(pdc.getOrDefault(enchantKey, PersistentDataType.STRING, ""));
            p.sendMessage(pdc.getOrDefault(runeKey, PersistentDataType.STRING, ""));
            CustomPickaxe.of(item,false).sendEnchants();
        }

        return false;
    }
}
