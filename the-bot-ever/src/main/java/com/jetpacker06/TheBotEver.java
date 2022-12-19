package com.jetpacker06;

import com.jetpacker06.command.Commands;
import com.jetpacker06.util.Channels;
import com.jetpacker06.util.Guilds;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class TheBotEver {
    public static GenericEvent recentEvent;
    public static SlashCommandInteractionEvent recentCommandEvent;
    public static final String BOT_KEY = System.getenv("TheBotEverDiscordBotKey");
    public static boolean useTestServer = false;
    private static boolean logSpamMode = false;
    public static JDA jda;

    public static void main(String[] args) throws LoginException, InterruptedException {
        if (System.getenv().containsKey("DEVMODE")) {
            if (Boolean.parseBoolean(System.getenv("DEVMODE"))) {
                useTestServer = true;
                logSpamMode = true;
            }
        }
        log("hello");
        jda = JDABuilder.createDefault(BOT_KEY)
        .setActivity(Activity.watching("closely."))
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .setStatus(OnlineStatus.ONLINE)
        .addEventListeners(new Commands())
        .build()
        .awaitReady();

        Guilds.theBoys = jda.getGuildById(1012378583332814898L);
        Guilds.testServer = jda.getGuildById(945662624224382998L);
        Channels.plans = jda.getTextChannelById(1050915980269854730L);
        Channels.jgeneral = jda.getTextChannelById(945662624673189899L);

        Commands.registerSlashCommands();

    }
    public static void log(Object message) {
        if (logSpamMode) {
            System.out.println(message);
        }
    }
}