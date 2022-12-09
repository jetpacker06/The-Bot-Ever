package com.jetpacker06.command;

import com.jetpacker06.TheBotEver;
import com.jetpacker06.json.Save;
import com.jetpacker06.json.SaveData;
import com.jetpacker06.util.Util;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Commands extends ListenerAdapter {
    @Override
    public void onGenericEvent(@NotNull GenericEvent event) {
        TheBotEver.recentEvent = event;
        if (event instanceof SlashCommandInteractionEvent) {
            TheBotEver.recentCommandEvent = (SlashCommandInteractionEvent) event;
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandSent = event.getName();
        if (Util.isStringInList(commandSent, slashCommandsMap.keySet().toArray(new String[0]))) {
            slashCommandsMap.get(commandSent).run(event);
        }
    }

    public static Map<String, CommandTask> slashCommandsMap = new HashMap<>();
    public static void registerSlashCommands(JDA jda) {
        CommandListUpdateAction commands = jda.updateCommands();
         addCommand(commands,
            "jackboxwin",
            "Add a win to a player.",
            (event) -> {
                Save.saveData("jackboxwins", Save.getInt("jackboxwins", event.getGuild(), event.getOption("winner").getAsUser()), event.getGuild(), event.getUser());

                TheBotEver.jackboxMessage.editMessage(new MessageBuilder().setEmbeds(SaveData.getJackboxEmbed()).build()).queue();
            },
            new CommandField(OptionType.USER, "winner", "The user to add the win to.", true)
        );
        addCommand(commands,
            "kys",
            "Send a KYS gif",
            (event) -> {
                event.reply(new MessageBuilder().setEmbeds(Util.createImageEmbed(Util.kys_list[new Random().nextInt(Util.kys_list.length)])).build()).queue();
            }
        );
        addCommand(commands,
            "jacobxlawrence",
            "jacob x lawrence",
            (event) -> {
                event.reply(new MessageBuilder().setEmbeds(Util.createImageEmbed("https://cdn.discordapp.com/attachments/1012378583773233162/1013312028695334942/unknown.png")).build()).queue();
            }
        );
        commands.queue();
    }

    public static void addCommand(CommandListUpdateAction commands, String name, String description, CommandTask runnable, CommandField... fields) {
        SlashCommandData command = net.dv8tion.jda.api.interactions.commands.build.Commands.slash(name, description);
        for (CommandField field : fields) {
            command.addOption(field.optionType(), field.name(), field.description(), field.required());
        }
        commands.addCommands(command);
        slashCommandsMap.put(name, runnable);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        //Use this to send the base jackboxembed
        if (event.getMessage().getContentRaw().equals("sendjackboxembed")) {
            Message toSend = new MessageBuilder().setEmbeds(SaveData.getJackboxEmbed()).build();
            event.getMessage().getChannel().sendMessage(toSend).queue();
            TheBotEver.jackboxMessage = toSend;
        }
    }
}
