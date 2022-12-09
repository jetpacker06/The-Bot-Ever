package com.jetpacker06.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jetpacker06.TheBotEver;
import com.jetpacker06.util.Color;
import com.jetpacker06.util.Util;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;

import java.io.FileWriter;
import java.io.IOException;

import static com.jetpacker06.TheBotEver.log;

public class SaveData {
    /**
     * @param user A String or a User object.
     * @return The wins of that player.
     * @throws IOException if idfk
     * @throws IllegalArgumentException if the object is not a user or string
     */
    public static int getUserJackboxWins(Object user) throws IOException {
        String contents = Util.readFile(TheBotEver.saveFilePath);
        String complete_username = "";
        if (user instanceof String) {
            complete_username = ((String) user);
        } else if (user instanceof User) {
            complete_username = Util.getUsername(((User) user));
        } else {
            throw new IllegalArgumentException("Must be a string or a user.");
        }
        JsonObject jackbox_wins_json_object = new Gson().fromJson(contents, JsonObject.class).get("jackbox_wins").getAsJsonObject();
        if (jackbox_wins_json_object.has(complete_username)) {
            return jackbox_wins_json_object.get(complete_username).getAsInt();
        }
        return 0;
    }
    public static void saveFile(JsonObject tosave) throws IOException {
        FileWriter writer = new FileWriter(TheBotEver.saveFilePath);
        writer.write(Util.JSONObjectToString(tosave));
        writer.close();
    }
    public static JsonElement getKey(String key) throws IOException {
        String contents = Util.readFile(TheBotEver.saveFilePath);
        return new Gson().fromJson(contents, JsonObject.class).get(key);
    }
    public static void addJackboxWin(User user) throws IOException {
        String contents = Util.readFile(TheBotEver.saveFilePath);
        String complete_username = Util.getUsername(user);
        JsonObject entireJson = new Gson().fromJson(contents, JsonObject.class);
        int currentWins;
        if (entireJson.get("jackbox_wins").getAsJsonObject().has(complete_username)) {
            currentWins = entireJson.get("jackbox_wins").getAsJsonObject().get(complete_username).getAsInt();
        } else {
            currentWins = 0;
        }
        entireJson.get("jackbox_wins").getAsJsonObject().addProperty(complete_username, currentWins + 1);
        saveFile(entireJson);
        TheBotEver.jackboxMessage.editMessage(new MessageBuilder().setEmbeds(getJackboxEmbed()).build()).queue();
    }

    public static MessageEmbed getJackboxEmbed() {
        try {
            EmbedBuilder builder = Util.coloredEmbedBuilder(Color.BLUE);
            builder.setTitle("JackBox Wins");
            for (String username : Save.get.getAsJsonObject().keySet().toArray(new String[0])) {
                builder.addField(username, String.valueOf(getUserJackboxWins(username)), true);
            }
            return builder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Message initJackboxMessage() {
        TextChannel channel = TheBotEver.guild.getTextChannelById(TheBotEver.useTestServer ? 1013461862244941886L : 1013472481874550874L);
        return channel.retrieveMessageById(1013475023320793200L).complete();
    }
}
