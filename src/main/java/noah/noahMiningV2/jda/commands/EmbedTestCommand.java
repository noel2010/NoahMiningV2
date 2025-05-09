package noah.noahMiningV2.jda.commands;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import noah.noahMiningV2.events.custom.SpecialEventStart;
import noah.noahMiningV2.jda.JdaMain;
import org.jetbrains.annotations.NotNull;

public class EmbedTestCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event){
        if (event.getName().equalsIgnoreCase("test_embed")){
            event.deferReply().queue();
            MessageEmbed embed = JdaMain.INSTANCE.getEmbedManager().eventStart(new SpecialEventStart());

            event.getHook().sendMessageEmbeds(embed).queue();
        }
    }
}
