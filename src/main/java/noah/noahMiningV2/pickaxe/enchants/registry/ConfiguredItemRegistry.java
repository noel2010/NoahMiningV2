package noah.noahMiningV2.pickaxe.enchants.registry;

import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.ench.ConfiguredItem;
import noah.noahMiningV2.events.custom.OreBreak;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;

public class ConfiguredItemRegistry implements Listener {
    private final Map<Player, ConfiguredItem> equippedItems = new HashMap<>();
    private final Map<Player, LinkedHashSet<ConfiguredItem>> cache = new HashMap<>();
    private final EnchantmentRegistry enchantmentRegistry;

    public ConfiguredItemRegistry(){
        NoahMiningV2.INSTANCE.getServer().getPluginManager().registerEvents(this, NoahMiningV2.INSTANCE);
        enchantmentRegistry = new EnchantmentRegistry();
    }

    public void check(Player player){
        ItemStack previousItem = equippedItems.get(player).getItem();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (Objects.equals(previousItem, item)) return;
        if (previousItem != null) cacheItem(player, equippedItems.get(player));
        ConfiguredItem configuredItem = enchantmentRegistry.getConfiguredItem(item);
        if (configuredItem != null) equippedItems.put(player, configuredItem);
    }

    public void cacheItem(Player player, ConfiguredItem item){
        LinkedHashSet<ConfiguredItem> items = cache.computeIfAbsent(player, k->new LinkedHashSet<>());
        equippedItems.remove(player);
        items.add(item);
    }

    @EventHandler
    public void onBlockBreak(OreBreak event){
        Player player = event.getP();
        ConfiguredItem item = equippedItems.get(player);
        if (item != null) item.onOreBreak(event);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){check(e.getPlayer());}
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        equippedItems.remove(e.getPlayer());
        cache.remove(e.getPlayer());
    }
    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent e){check(e.getPlayer());}
}
