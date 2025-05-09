package noah.noahMiningV2.pickaxe.runes.infr;

import noah.noahMiningV2.pickaxe.runes.RuneTypes;
import org.bukkit.entity.Player;

public interface ReturnRune extends Rune{
    double activate(Player p);
    RuneTypes getType();
}
