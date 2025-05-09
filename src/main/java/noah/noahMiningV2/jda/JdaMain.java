package noah.noahMiningV2.jda;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManager;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import noah.noahMiningV2.jda.commands.CommandManager;
import noah.noahMiningV2.jda.commands.EmbedTestCommand;
import noah.noahMiningV2.jda.embeds.EmbedManager;

public class JdaMain {

    private final ShardManager shardManager;

    public static JdaMain INSTANCE;
    @Getter
    private EmbedManager embedManager = new EmbedManager();

    public JdaMain(){
        INSTANCE = this;
        JDA jda = JDABuilder.createDefault("MTM1ODE3NTMyODc5ODkwMDM1NA.Gq4US1.Sy2cmgOMfdCdZ852aJi3STyvPTOnXOHs0P04oE")
                .build();
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault("MTM1ODE3NTMyODc5ODkwMDM1NA.Gq4US1.Sy2cmgOMfdCdZ852aJi3STyvPTOnXOHs0P04oE");
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.customStatus("Starting App!"));
        builder.enableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT);
        shardManager = builder.build();

        registerEvents();
    }

    private void registerEvents(){
        shardManager.addEventListener(new CommandManager(), new EmbedTestCommand());
    }

}
