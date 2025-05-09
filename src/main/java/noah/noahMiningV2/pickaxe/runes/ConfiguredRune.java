package noah.noahMiningV2.pickaxe.runes;

import lombok.Getter;
import noah.noahMiningV2.pickaxe.enchants.ConfiguredEnchant;
import noah.noahMiningV2.pickaxe.runes.impl.BlankRune;
import noah.noahMiningV2.pickaxe.runes.infr.Rune;

public class ConfiguredRune {
    @Getter
    private Rune rune;

    private ConfiguredRune(Rune rune){ this.rune = rune; }

    public static ConfiguredRune of(Rune rune){ return new ConfiguredRune(rune); }

    public boolean isBlank(){
        return rune.getID() == new BlankRune().getID();
    }
}
