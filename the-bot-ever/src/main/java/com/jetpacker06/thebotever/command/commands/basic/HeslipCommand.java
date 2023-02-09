package com.jetpacker06.thebotever.command.commands.basic;

import com.jetpacker06.thebotever.command.commands.Command;
import com.jetpacker06.thebotever.util.Util;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class HeslipCommand extends Command {
    @Override
    public String getName() {
        return "heslip";
    }
    @Override
    public String getDescription() {
        return "Jumpscare warning";
    }

    @Override
    public boolean forFriendsOnly() {
        return false;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.replyEmbeds(Util.createImageEmbed(
                "https://cdn.discordapp.com/attachments/1068342911088996362/1071989873407434834/Resized_20230115_090556.jpg"
        )).queue();
    }
}
//1073070703571636435L