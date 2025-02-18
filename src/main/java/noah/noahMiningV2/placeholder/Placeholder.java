package noah.noahMiningV2.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.PlayerData;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class Placeholder extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "NoahMining";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Noel";
    }

    @Override
    public @NotNull String getVersion() {
        return "1";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params){
        if (params.equalsIgnoreCase("souls"))
            return ""+(new PlayerData(player.getUniqueId())).getSouls();
        return ""+0;
    }
}
