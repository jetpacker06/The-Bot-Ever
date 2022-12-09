package com.jetpacker06.command;

import net.dv8tion.jda.api.interactions.commands.OptionType;

public record CommandField(OptionType optionType, String name, String description, boolean required) {
}
