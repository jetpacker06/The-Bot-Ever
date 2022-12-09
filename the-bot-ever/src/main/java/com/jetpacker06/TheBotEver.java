package com.jetpacker06;

import com.jetpacker06.command.Commands;
import com.jetpacker06.json.SaveData;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.Timer;

public class TheBotEver {
    public static GenericEvent recentEvent;
    public static Timer timer = new Timer();
    public static SlashCommandInteractionEvent recentCommandEvent;
    public static String BOT_KEY = System.getenv("TheBotEverDiscordBotKey");
    public static Guild guild;
    public static Message jackboxMessage;
    public static String saveFilePath = "saved_data.json";
    public static final boolean useTestServer = false;
    private static final boolean logSpamMode = true;
    public static JDA jda;

    public static void main(String[] args) throws LoginException, InterruptedException {
        log("hello");
        jda = JDABuilder.createDefault(BOT_KEY)
        .setActivity(Activity.watching("closely."))
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .setStatus(OnlineStatus.ONLINE)
        .addEventListeners(new Commands())
        .build()
        .awaitReady();

        guild = useTestServer ? jda.getGuildById(945662624224382998L) : jda.getGuildById(1012378583332814898L);
        jackboxMessage = SaveData.initJackboxMessage();

        Commands.registerSlashCommands(jda);

    }
    public static void log(Object message) {
        if (logSpamMode) {
            System.out.println(message);
        }
    }
}