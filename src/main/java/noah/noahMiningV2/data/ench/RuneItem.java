package noah.noahMiningV2.data.ench;

import noah.noahMiningV2.pickaxe.runes.Rune;
import org.bukkit.inventory.ItemStack;

public class RuneItem extends CustomItem{

    private ItemStack item;
    private Rune rune;

    private RuneItem(ItemStack item, Rune rune){
        this.item = item;
        this.rune = rune;
    }

    public static RuneItem of(ItemStack item, Rune rune){ return new RuneItem(item,rune); }

}
