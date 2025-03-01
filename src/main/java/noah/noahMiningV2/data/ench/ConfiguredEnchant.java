package noah.noahMiningV2.data.ench;

import lombok.Getter;
import noah.noahMiningV2.pickaxe.enchants.Enchant;

public final class ConfiguredEnchant {
    @Getter
    private final Enchant enchantment;
    @Getter
    private final int level;

    private ConfiguredEnchant(Enchant enchantment, int level){
        this.enchantment = enchantment;
        this.level = level;
    }
    public static ConfiguredEnchant of(Enchant enchantment, int level){ return new ConfiguredEnchant(enchantment,level); }
}
