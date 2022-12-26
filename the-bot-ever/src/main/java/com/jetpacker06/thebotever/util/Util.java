package com.jetpacker06.thebotever.util;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import static com.jetpacker06.thebotever.TheBotEver.log;


public class Util {
    public static String[] emotes_list = {
        "https://c.tenor.com/awnbsntmYkoAAAAd/fortnite-dance-fortnite-emote.gif",
        "https://c.tenor.com/vjDSipHhsjAAAAAd/fortnite-snake-eyes.gif",
        "https://thumbs.gfycat.com/AmazingViciousElephantbeetle-size_restricted.gif",
        "https://i.gifer.com/fy2C.gif",
        "https://www.icegif.com/wp-content/uploads/fortnite-icegif-14.gif",
        "https://media4.giphy.com/media/SG5paY6WxH6Ki2lWys/200.gif",
        "https://cdn.mobilesyrup.com/wp-content/uploads/2020/02/fortnite-rickroll-emote.gif"
    };
    public static String[] kys_list = {
        "https://c.tenor.com/HyplAGaP7bAAAAAC/bill-nye-consider-the-following.gif",
        "https://c.tenor.com/2huFMTUd_I8AAAAd/kill-yourself.gif",
        "https://c.tenor.com/ptdTp18uyecAAAAC/kys-kill-yourself.gif",
        "https://c.tenor.com/PbmqcSotcP4AAAAS/kill-yourself.gif",
        "https://c.tenor.com/pcaBSX7U470AAAAd/jetstream-sam-sam.gif"
    };
    public static Object random_from_array(Object[] array) {
        return array[new Random().nextInt(array.length)];
    }

    public static boolean isStringInList(String thing, String[] list) {
        boolean flag = false;
        for (String s : list) {
            if (Objects.equals(s, thing)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    public static void sendMessage(String message, TextChannel channel) {
        channel.sendMessage(message).queue();
    }
    public static EmbedBuilder blueBuilder() {
        return new EmbedBuilder().setColor(Colors.BLUE);
    }
    public static EmbedBuilder coloredEmbedBuilder(int color) {
        return new EmbedBuilder().setColor(color);
    }
    public static MessageEmbed createImageEmbed(String url) {
        return blueBuilder().setImage(url).build();
    }
    public static void logJSONObject(JsonObject obj) {
        log(new GsonBuilder().setPrettyPrinting().create().toJson(obj));
    }
    public static String getUsername(User user) {
        return user.getName() + "#" + user.getDiscriminator();
    }
    public static String readFile(String path) {
        try {
            new File(path).createNewFile();
            File data_file = new File(path);
            Scanner fileScanner = new Scanner(data_file);
            StringBuilder contents = new StringBuilder();
            while (fileScanner.hasNext()) {
                contents.append(fileScanner.next());
            }
            fileScanner.close();
            return contents.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static String JSONObjectToString(JsonObject json) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(json);
    }
}
