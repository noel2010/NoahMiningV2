package noah.noahMiningV2.pickaxe.runes;

import java.util.HashMap;
import java.util.Map;

public class RunesRegistry {
    private final Map<String, Rune> runes = new HashMap<>();
    private Rune rune;
    public RunesRegistry(){}

    public void register(String id, Rune rune){ runes.put(id,rune); }
}
