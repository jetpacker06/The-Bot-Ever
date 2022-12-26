package com.jetpacker06.thebotever.command.commands;

import com.jetpacker06.thebotever.util.Channels;
import com.jetpacker06.thebotever.util.Emojis;
import com.jetpacker06.thebotever.util.Util;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;

public class PlanCommand extends Command{
    @Override
    public String getName() {
        return "plan";
    }

    @Override
    public String getDescription() {
        return "Plan an event.";
    }

    @Override
    public boolean forFriendsOnly() {
        return true;
    }

    @Override
    public ArrayList<OptionData> getOptions() {
        ArrayList<OptionData> toReturn = new ArrayList<>();
        toReturn.add(stringOption("event", "What is the event?", true));
        toReturn.add(stringOption("when", "When is the event?", false));
        toReturn.add(stringOption("where", "Where is the event?", false));
        toReturn.add(stringOption("bring", "What should be brought?", false));
        toReturn.add(stringOption("extrainformation", "Extra information?", false));
        toReturn.add(boolOption("ping", "Should the bot ping everyone?", false));
        toReturn.add(boolOption("role", "Should a role be created for the event? WIP", false));
        return toReturn;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        EmbedBuilder builder = Util.blueBuilder();
        builder.setTitle(getStrOp("event"));
        builder.addField("Organizer", event.getMember().getEffectiveName(), false);
        builder.addField("Event", getStrOp("event"), false);

        if (optionExists("where"))
            builder.addField("Where", getStrOp("where"), false);
        if (optionExists("when"))
            builder.addField("When", getStrOp("when"), false);
        if (optionExists("bring"))
            builder.addField("Bring", getStrOp("bring"), false);
        if (optionExists("extrainformation"))
            builder.addField("Extra Information", getStrOp("extrainformation"), true);

        MessageBuilder m = new MessageBuilder();
        if (boolOrElse("ping", false))
            m.append("||@everyone||");
        m.setEmbeds(builder.build());
        TextChannel channel;

        if (isTheBoysServer(event)) {
            channel = Channels.plans;
        } else {
            channel = Channels.jgeneral;
        }

        channel.sendMessage(m.build()).queue((message) -> {
            message.addReaction(Emojis.UP_ARROW).queue();
            message.addReaction(Emojis.DOWN_ARROW).queue();
        });
    }
}
