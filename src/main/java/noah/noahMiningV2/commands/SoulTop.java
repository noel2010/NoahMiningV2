package noah.noahMiningV2.commands;

import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.PlayerData;
import noah.noahMiningV2.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class SoulTop implements CommandExecutor {

    private Map<UUID, Integer> players;
    private Server server = NoahMiningV2.INSTANCE.getServer();

    private final Config conf = new Config();

    public SoulTop(){
        players = PlayerData.getPlayersSouls();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {


        List<Map.Entry<UUID, Integer>> sorted = players.entrySet().stream()
                .sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toList());

        int rank = 1;
        for(Map.Entry<UUID, Integer> entry : sorted){
            OfflinePlayer player = server.getOfflinePlayer(entry.getKey());
            String name = player.getName() != null ? player.getName() : "Unknown";
            String message = conf.getBalTopMessage().replace("{rank}", ""+rank).replace("{player}", name).replace("{balance}", String.format("%.2f", entry.getValue()));
            commandSender.sendMessage(conf.getColoredMessage(message));
            rank++;
        }

        return false;
    }
}
