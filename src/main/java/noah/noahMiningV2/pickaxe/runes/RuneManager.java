package noah.noahMiningV2.pickaxe.runes;

import lombok.Getter;
import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.pickaxe.runes.impl.BlankRune;
import noah.noahMiningV2.pickaxe.runes.infr.Rune;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuneManager {
    private NamespacedKey runeKey = new NamespacedKey(NoahMiningV2.INSTANCE, "rune");
    @Getter
    private Map<String, Rune> runes = new HashMap<>();

    private void register(String id, Rune rune){ runes.put(id, rune); }
    private void registerDefault(){
        register("blank", new BlankRune());
    }
}
