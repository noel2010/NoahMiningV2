package noah.noahMiningV2.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import noah.noahMiningV2.pickaxe.CustomPickaxe;
import noah.noahMiningV2.pickaxe.enchants.EnchantManager;
import noah.noahMiningV2.utils.Config;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import poa.poalib.shaded.NBT;

public class PickaxeCommand implements CommandExecutor {
    private final Config conf = new Config();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p){
            ItemStack item = conf.ConstructPickaxe();
            p.getInventory().addItem(CustomPickaxe.of(item, true).getItem());
        }
        return false;
    }
}
