package noah.noahMiningV2.data.ench;

import lombok.Getter;
import noah.noahMiningV2.events.BreakOre;
import noah.noahMiningV2.events.custom.OreBreak;
import noah.noahMiningV2.pickaxe.enchants.Enchant;
import noah.noahMiningV2.pickaxe.runes.Rune;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ConfiguredItem {
    @Getter
    private final ItemStack item;
    private final List<ConfiguredEnchant> enchants;
    private final Rune rune;

    private ConfiguredItem(ItemStack item, List<ConfiguredEnchant> enchants, Rune rune){
        this.item = item;
        this.enchants = enchants;
        this.rune = rune;
    }

    public static ConfiguredItem of(ItemStack item, List<ConfiguredEnchant> enchants, Rune rune){ return new ConfiguredItem(item, enchants, rune); }
    public static ConfiguredItem of(ItemStack item, Map<Enchant, Integer> enchants, Rune rune){
        List<ConfiguredEnchant> enchantments = new ArrayList<>();
        for (Map.Entry<Enchant, Integer> entry : enchants.entrySet())
            enchantments.add(ConfiguredEnchant.of(entry.getKey(), entry.getValue()));
        return new ConfiguredItem(item, enchantments, rune);
    }

    public void onOreBreak(OreBreak event){
        for (ConfiguredEnchant enchant : enchants)
            if (enchant.getEnchantment() instanceof Enchant enchantment)
                enchantment.onOreBreak(event, enchant.getLevel());
        if(rune!=null) rune.onRuneActivate();
    }
}
