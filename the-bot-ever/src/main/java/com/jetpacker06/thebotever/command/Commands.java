package com.jetpacker06.thebotever.command;

import com.jetpacker06.thebotever.TheBotEver;
import com.jetpacker06.thebotever.command.commands.Command;
import com.jetpacker06.thebotever.util.Guilds;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class Commands extends ListenerAdapter {
    public static HashMap<String, com.jetpacker06.thebotever.command.commands.Command> commands = new HashMap<>();
    @Override
    public void onGenericEvent(@NotNull GenericEvent event) {
        TheBotEver.recentEvent = event;
        if (event instanceof SlashCommandInteractionEvent) {
            TheBotEver.recentCommandEvent = (SlashCommandInteractionEvent) event;
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String name = event.getName();
        if (!commands.containsKey(name)) return;
        if (commands.get(name).forFriendsOnly() & CommandUtil.notInCorrectServer(event)) return;
        commands.get(name).execute(event);
    }

    public static Set<Class<? extends com.jetpacker06.thebotever.command.commands.Command>> getCommandClasses() {
        return TheBotEver.reflections.getSubTypesOf(Command.class);
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
    public static boolean optionExists(String name) {
        try {
            return TheBotEver.recentCommandEvent.getOption(name) != null;
        } catch (Exception ignored) {}
        return false;
    }
}