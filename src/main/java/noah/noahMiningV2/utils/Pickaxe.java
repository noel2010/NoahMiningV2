package noah.noahMiningV2.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import poa.poalib.shaded.NBT;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Pickaxe {

    private ItemStack i = new ItemStack(Material.DIAMOND_PICKAXE);
    private final static Config conf = new Config();

    private void setNBT(ItemStack i){
        NBT.modify(i, nbt->{
            List<String> enchants = List.of("fortune", "keyFinder", "ior",
                                 "excavator", "dupe", "greed");
            for (String ench : enchants)
                nbt.setInteger("enchants;"+ench, 0);
            nbt.setBoolean("noahMining;pickaxe", true);
        });
    }
    private List<String> getEnchants(ItemStack i){
        return NBT.get(i, nbt->{
            Set<String> keys = nbt.getKeys();
            List<String> enchants = new ArrayList<>();
            for (String key : keys)
                if (key.startsWith("enchant;"))
                    enchants.add(key.replace("enchant;", ""));
            return enchants;
        });
    }
    private List<Integer> getEnchantsLevels(ItemStack i){
        return NBT.get(i, nbt->{
            Set<String> keys = nbt.getKeys();
            List<Integer> levels = new ArrayList<>();
            for (String key : keys)
                if (key.startsWith("enchant;"))
                    levels.add(nbt.getInteger(key));
            return levels;
        });
    }

    public ItemStack getPickaxe(){
        setNBT(i);
        ItemMeta iMeta = i.getItemMeta();
        iMeta.displayName(conf.getColoredMessage(conf.getPickaxeName()));
        iMeta.lore(conf.getColoredLore(conf.getPickaxeLore()));
        return i;
    }

}
