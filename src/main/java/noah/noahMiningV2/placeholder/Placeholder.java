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
        if (params.equalsIgnoreCase("souls_formatted"))
            return formatNumber((double) (new PlayerData(player.getUniqueId()).getSouls()), 0);
        return "N/A";
    }

    private char[] c = new char[]{'K', 'M', 'B', 'T', 'Q'};
    private String formatNumber(double n, int iteration) {
        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) %10 == 0;
        return (d < 1000?
                ((d > 99.9 || isRound || (!isRound && d > 9.99)?
                        (int) d * 10 / 10 : d + ""
                ) + "" + c[iteration])
                : formatNumber(d, iteration+1));

    }
}
