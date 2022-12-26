package com.jetpacker06.thebotever.command;

import com.jetpacker06.thebotever.TheBotEver;
import com.jetpacker06.thebotever.util.Channels;
import com.jetpacker06.thebotever.util.Emojis;
import com.jetpacker06.thebotever.util.Guilds;
import com.jetpacker06.thebotever.util.Util;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@SuppressWarnings("ResultOfMethodCallIgnored")
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
        if (CommandUtil.notInCorrectServer(event)) return;
        String commandSent = event.getName();
        if (Util.isStringInList(commandSent, slashCommandsMap.keySet().toArray(new String[0]))) {
            slashCommandsMap.get(commandSent).run(event);
        }
    }

    public static Map<String, CommandTask> slashCommandsMap = new HashMap<>();

    //This is always null except while registerSlashCommands() is actively executing
    public static CommandListUpdateAction clua = null;
    public static void registerSlashCommands() {
        clua = TheBotEver.jda.updateCommands();
        addCommand(
                "kys",
                "Send a KYS gif",
                (event) -> {
                    event.reply(new MessageBuilder().setEmbeds(Util.createImageEmbed(Util.kys_list[new Random().nextInt(Util.kys_list.length)])).build()).queue();
                }
        );
        addCommand(
                "jacobxlawrence",
                "jacob x lawrence",
                (event) -> {
                    event.reply(new MessageBuilder().setEmbeds(Util.createImageEmbed("https://cdn.discordapp.com/attachments/1012378583773233162/1013312028695334942/unknown.png")).build()).queue();
                }
        );
        addCommand(
                "precise",
                "Very precise calculations.",
                (event) -> {
                    event.reply(new MessageBuilder().setEmbeds(Util.createImageEmbed("https://media.tenor.com/t5xwKNKc2cIAAAAd/very-very-precise-steve-kornacki.gif")).build()).queue();
                }
        );
        addCommand("emote", "Do an emote.", (event) -> {
                event.reply(new MessageBuilder().setEmbeds(Util.createImageEmbed(Util.emotes_list[new Random().nextInt(Util.emotes_list.length)])).build()).queue();
        });
        addCommand("think", "Hmm...", (event) -> {
                event.deferReply().queue();
        });
        addCommand(
                "role-menu",
                "Create a role selection menu.",
                (event) -> {},
                stringOption("roles", "Names of the roles separated by a semicolon.", true),
                stringOption("title", "Message title", true),
                stringOption("description", "Optional text.", false)
        );
        addCommand(
                "plan",
                "Plan an event",
                (event) -> {
                    event.deferReply().queue();
                    EmbedBuilder builder = Util.blueBuilder();
                    builder.setTitle(getStrOp("event"));
                    builder.addField("Organizer", event.getMember().getEffectiveName(), false);

                    builder.addField("Event", getStrOp("event"), false);
                    if (optionExists("where")) builder.addField("Where", getStrOp("where"), false);
                    if (optionExists("when")) builder.addField("When", getStrOp("when"), false);
                    if (optionExists("bring")) builder.addField("Bring", getStrOp("bring"), false);
                    if (optionExists("extrainformation")) builder.addField("Extra Information", getStrOp("extrainformation"), true);

                    MessageBuilder m = new MessageBuilder();
                    if (boolOrElse("ping", false)) m.append("||@everyone||");
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
                    event.getHook().deleteOriginal().queue();
                },
                stringOption("event", "What is the event?", true),
                stringOption("when", "When is the event?", false),
                stringOption("where", "Where is the event?", false),
                stringOption("bring", "What should be brought?", false),
                stringOption("extrainformation", "Extra information?", false),
                boolOption("ping", "Should the bot ping everyone?", false),
                boolOption("role", "Should a role be created for the event? WIP", false)
        );

        clua.queue();
        clua = null;
    }

    public static void addCommand(String name, String description, CommandTask runnable, OptionData... fields) {
        SlashCommandData command = net.dv8tion.jda.api.interactions.commands.build.Commands.slash(name, description);
        for (OptionData field : fields) {
            command.addOption(field.getType(), field.getName(), field.getDescription(), field.isRequired());
        }
        clua.addCommands(command);
        slashCommandsMap.put(name, runnable);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
    }

    public static boolean optionExists(String name) {
        try {
            return TheBotEver.recentCommandEvent.getOption(name) != null;
        } catch (Exception ignored) {}
        return false;
    }

    public static String getStrOp(String optionName) {
        return Objects.requireNonNull(TheBotEver.recentCommandEvent.getOption(optionName)).getAsString();
    }
    public static boolean getBoolOp(String optionName) {
        return Objects.requireNonNull(TheBotEver.recentCommandEvent.getOption(optionName)).getAsBoolean();
    }
    public static int getIntOp(String optionName) {
        return Objects.requireNonNull(TheBotEver.recentCommandEvent.getOption(optionName)).getAsInt();
    }

    public static int intOrElse(String name, int backup) {
        if (optionExists(name)) {
            return getIntOp(name);
        }
        return backup;
    }
    public static String strOrElse(String name, String backup) {
        if (optionExists(name)) {
            return getStrOp(name);
        }
        return backup;
    }
    public static boolean boolOrElse(String name, boolean backup) {
        if (optionExists(name)) {
            return getBoolOp(name);
        }
        return backup;
    }
    public static boolean isTheBoysServer(SlashCommandInteractionEvent event) {
        return Objects.requireNonNull(event.getGuild()).getIdLong() == Guilds.theBoys.getIdLong();
    }
    public static boolean isTestServer() {
        return Objects.requireNonNull(TheBotEver.recentCommandEvent.getGuild()).getIdLong() == Guilds.testServer.getIdLong();
    }
    public static OptionData stringOption(String name, String description, boolean required) {
        return new OptionData(OptionType.STRING, name, description, required);
    }
    public static OptionData intOption(String name, String description, boolean required) {
        return new OptionData(OptionType.INTEGER, name, description, required);
    }
    public static OptionData boolOption(String name, String description, boolean required) {
        return new OptionData(OptionType.BOOLEAN, name, description, required);
    }
}
