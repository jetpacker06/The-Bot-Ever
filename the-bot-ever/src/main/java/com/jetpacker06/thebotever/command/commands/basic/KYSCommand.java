package com.jetpacker06.thebotever.command.commands.basic;

import com.jetpacker06.thebotever.command.commands.Command;
import com.jetpacker06.thebotever.util.Util;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class KYSCommand extends Command {
    @Override
    public String getName() {
        return "kys";
    }
    @Override
    public String getDescription() {
        return "Send a KYS gif.";
    }

    @Override
    public boolean forFriendsOnly() {
        return true;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.replyEmbeds(Util.createImageEmbed(Util.randomFromArray(kys_list))).queue();
    }


    public static String[] kys_list = {
            "https://c.tenor.com/HyplAGaP7bAAAAAC/bill-nye-consider-the-following.gif",
            "https://c.tenor.com/2huFMTUd_I8AAAAd/kill-yourself.gif",
            "https://c.tenor.com/ptdTp18uyecAAAAC/kys-kill-yourself.gif",
            "https://c.tenor.com/PbmqcSotcP4AAAAS/kill-yourself.gif",
            "https://c.tenor.com/pcaBSX7U470AAAAd/jetstream-sam-sam.gif"
    };
}
