package com.jetpacker06.thebotever.command.commands;

import com.jetpacker06.thebotever.TheBotEver;
import com.jetpacker06.thebotever.command.CommandInit;
import com.jetpacker06.thebotever.util.Guilds;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Command {
    public abstract String getName();
    public abstract String getDescription();
    public abstract boolean forFriendsOnly();

    public ArrayList<OptionData> getOptions() {
        return new ArrayList<>();
    }
    public Command() {
        SlashCommandData command = net.dv8tion.jda.api.interactions.commands.build.Commands.slash(
                this.getName(), this.getDescription()
        );
        for (OptionData option : this.getOptions()) {
            command.addOption(option.getType(), option.getName(), option.getDescription(), option.isRequired());
        }
        CommandInit.commandDataArrayList.add(command);
    }

    public abstract void execute(SlashCommandInteractionEvent event);


    String getStrOp(String optionName) {
        return Objects.requireNonNull(TheBotEver.recentCommandEvent.getOption(optionName)).getAsString();
    }
    boolean getBoolOp(String optionName) {
        return Objects.requireNonNull(TheBotEver.recentCommandEvent.getOption(optionName)).getAsBoolean();
    }
    int getIntOp(String optionName) {
        return Objects.requireNonNull(TheBotEver.recentCommandEvent.getOption(optionName)).getAsInt();
    }

    int intOrElse(String name, int backup) {
        if (optionExists(name)) {
            return getIntOp(name);
        }
        return backup;
    }
    String strOrElse(String name, String backup) {
        if (optionExists(name)) {
            return getStrOp(name);
        }
        return backup;
    }
    boolean boolOrElse(String name, boolean backup) {
        if (optionExists(name)) {
            return getBoolOp(name);
        }
        return backup;
    }
    boolean isTheBoysServer(SlashCommandInteractionEvent event) {
        return Objects.requireNonNull(event.getGuild()).getIdLong() == Guilds.theBoys.getIdLong();
    }
    boolean isTestServer() {
        return Objects.requireNonNull(TheBotEver.recentCommandEvent.getGuild()).getIdLong() == Guilds.testServer.getIdLong();
    }
    OptionData stringOption(String name, String description, boolean required) {
        return new OptionData(OptionType.STRING, name, description, required);
    }
    OptionData intOption(String name, String description, boolean required) {
        return new OptionData(OptionType.INTEGER, name, description, required);
    }
    OptionData boolOption(String name, String description, boolean required) {
        return new OptionData(OptionType.BOOLEAN, name, description, required);
    }
    boolean optionExists(String name) {
        try {
            return TheBotEver.recentCommandEvent.getOption(name) != null;
        } catch (Exception ignored) {}
        return false;
    }
}
