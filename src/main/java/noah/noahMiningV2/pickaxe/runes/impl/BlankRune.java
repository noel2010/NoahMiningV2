package noah.noahMiningV2.pickaxe.runes.impl;

import noah.noahMiningV2.pickaxe.runes.infr.Rune;

public class BlankRune implements Rune {
    @Override
    public String getName() {
        return "Blank";
    }

    @Override
    public String getID() {
        return "blank";
    }
}
