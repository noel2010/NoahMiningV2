package noah.noahMiningV2.jda.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import noah.noahMiningV2.events.custom.SpecialEventStart;
import noah.noahMiningV2.events.custom.enums.EventTypes;

import java.awt.*;
import java.time.Instant;

public class EmbedManager {


    public MessageEmbed eventStart(SpecialEventStart event){
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("Event has started")
                .setDescription("The event has started")
                .setTimestamp(Instant.now())
                .setColor(Color.decode("#751c9e"));

        return embed.build();
    }
}
