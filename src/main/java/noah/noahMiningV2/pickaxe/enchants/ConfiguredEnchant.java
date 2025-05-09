package noah.noahMiningV2.pickaxe.enchants;

import lombok.Getter;
import noah.noahMiningV2.pickaxe.enchants.infr.Enchant;

public class ConfiguredEnchant {
    @Getter
    private Enchant enchant;
    @Getter
    private int level;

    private ConfiguredEnchant(Enchant enchant, int level){
        this.enchant = enchant;
        this.level = level;
    }
    public static ConfiguredEnchant of(Enchant enchant, int level){
        return new ConfiguredEnchant(enchant,level);
    }
    public void upgrade(){
        if (level == enchant.getMaxLevel()) return;
        level++;
    }
    public boolean hasUpgradedOnce(){ return level > 0; }
}
