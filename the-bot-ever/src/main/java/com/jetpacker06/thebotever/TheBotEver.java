package com.jetpacker06.thebotever;

import com.jetpacker06.thebotever.command.CommandInit;
import com.jetpacker06.thebotever.command.Commands;
import com.jetpacker06.thebotever.command.commands.role.RoleMenuCommand;
import com.jetpacker06.thebotever.util.Channels;
import com.jetpacker06.thebotever.util.Guilds;
import com.jetpacker06.thebotever.util.Util;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.reflections.Reflections;

import javax.security.auth.login.LoginException;

public class TheBotEver {
    public static Reflections reflections = new Reflections("com.jetpacker06");
    public static GenericEvent recentEvent;
    public static SlashCommandInteractionEvent recentCommandEvent;
    public static final String BOT_KEY = System.getenv().containsKey("TheBotEverDiscordBotKey") ? System.getenv("TheBotEverDiscordBotKey") : Util.readFile("botkey.txt");
    private static boolean logSpamMode = false;
    public static JDA jda;

    public static void main(String[] args) throws LoginException, InterruptedException {
        if (System.getenv().containsKey("DEVMODE")) {
            logSpamMode = Boolean.parseBoolean(System.getenv("DEVMODE"));
        }

        log("Booted up!");
        jda = JDABuilder.createDefault(BOT_KEY)
        .setActivity(Activity.watching("closely."))
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .setStatus(OnlineStatus.ONLINE)
        .addEventListeners(new Commands(), new RoleMenuCommand())
        .build()
        .awaitReady();

        Guilds.theBoys = jda.getGuildById(1012378583332814898L);
        Guilds.testServer = jda.getGuildById(945662624224382998L);
        Channels.plans = jda.getTextChannelById(1050915980269854730L);
        Channels.roles = jda.getTextChannelById(1055512912451612752L);
        Channels.jgeneral = jda.getTextChannelById(945662624673189899L);

        CommandInit.registerSlashCommands();

    }
    public static void log(Object message) {
        if (logSpamMode) {
            System.out.println(message);
        }
    }
}