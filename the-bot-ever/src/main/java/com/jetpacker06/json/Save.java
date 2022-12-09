package com.jetpacker06.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jetpacker06.TheBotEver;
import com.jetpacker06.util.Util;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.io.FileWriter;
import java.io.IOException;

public class Save {
    private static void saveData(String key, Object value, JsonObject object) throws IOException {
        if (value instanceof Boolean) {
            object.addProperty(key, (Boolean) value);
        }
        else if (value instanceof Integer) {
            object.addProperty(key, (Integer) value);
        }
        else if (value instanceof Double) {
            object.addProperty(key, (Double) value);
        }
        else if (value instanceof String) {
            object.addProperty(key, (String) value);
        }
        FileWriter writer = new FileWriter(TheBotEver.saveFilePath);
        writer.write(Util.JSONObjectToString(object));
        writer.close();
    }
    public static void saveData(String key, Object value, Guild guild, User user) {
        try {
            saveData(key, value, new Gson().fromJson(Util.readFile(TheBotEver.saveFilePath), JsonObject.class).get(guild.getId()).getAsJsonObject().get(user.getId()).getAsJsonObject());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveData(String key, Object value, Guild guild) {
        try {
            saveData(key, value, new Gson().fromJson(Util.readFile(TheBotEver.saveFilePath), JsonObject.class).get(guild.getId()).getAsJsonObject());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveData(String key1, String key2, Object value, Guild guild) {
        try {
            saveData(key2, value, new Gson().fromJson(Util.readFile(TheBotEver.saveFilePath), JsonObject.class).get(guild.getId()).getAsJsonObject().get(key1).getAsJsonObject());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int getInt(String key, Guild guild) {
        int toReturn = -1;
        try {
            toReturn = new Gson().fromJson(Util.readFile(TheBotEver.saveFilePath), JsonObject.class).get(guild.getId()).getAsJsonObject().get(key).getAsInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    public static String getString(String key, Guild guild) {
        String toReturn = "";
        try {
            toReturn = new Gson().fromJson(Util.readFile(TheBotEver.saveFilePath), JsonObject.class).get(guild.getId()).getAsJsonObject().get(key).getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    public static double getDouble(String key, Guild guild) {
        double toReturn = 0;
        try {
            toReturn = new Gson().fromJson(Util.readFile(TheBotEver.saveFilePath), JsonObject.class).get(guild.getId()).getAsJsonObject().get(key).getAsDouble();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    public static boolean getBoolean(String key, Guild guild) {
        boolean toReturn = false;
        try {
            toReturn = new Gson().fromJson(Util.readFile(TheBotEver.saveFilePath), JsonObject.class).get(guild.getId()).getAsJsonObject().get(key).getAsBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    public static int getInt(String key, Guild guild, User user) {
        int toReturn = -1;
        try {
            toReturn = new Gson().fromJson(Util.readFile(TheBotEver.saveFilePath), JsonObject.class).get(guild.getId()).getAsJsonObject().get(user.getId()).getAsJsonObject().get(key).getAsInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    public static String getString(String key, Guild guild, User user) {
        String toReturn = "";
        try {
            toReturn = new Gson().fromJson(Util.readFile(TheBotEver.saveFilePath), JsonObject.class).get(guild.getId()).getAsJsonObject().get(user.getId()).getAsJsonObject().get(key).getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    public static double getDouble(String key, Guild guild, User user) {
        double toReturn = 0;
        try {
            toReturn = new Gson().fromJson(Util.readFile(TheBotEver.saveFilePath), JsonObject.class).get(guild.getId()).getAsJsonObject().get(user.getId()).getAsJsonObject().get(key).getAsDouble();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    public static boolean getBoolean(String key, Guild guild, User user) {
        boolean toReturn = false;
        try {
            toReturn = new Gson().fromJson(Util.readFile(TheBotEver.saveFilePath), JsonObject.class).get(guild.getId()).getAsJsonObject().get(user.getId()).getAsJsonObject().get(key).getAsBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
}
