package me.ryzeon.notifier.utils.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;,
import com.google.gson.stream.JsonReader;

import me.ryzeon.notifier.Notifier;

/**
 * @Created by Ryzeon
 * @File: JsonConfig.java
 * @Date: Jan 26, 2022 @ 1:26:46 PM
 * @Twitter: @Ryzeon_ 😎
 * @Github: github.ryzeon.me
 */
public class JsonConfig {

    private final static Gson gson = Notifier.getInstance().getGson();

    public static <T> T loadConfig(Class<T> clazz) throws FileNotFoundException, IOException, InstantiationException, IllegalAccessException {
        String fileName = clazz.getSimpleName().toLowerCase() + ".json";
        File file = new File(fileName);

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("[JsonConfig] File not found: " + fileName);
            System.out.println("[JsonConfig] Creating new file: " + fileName);

            // Create the file
            file.createNewFile();
            // Create a new instance of the class
            Object toSerialize = clazz.newInstance();
            // Write class objecs to the file in json format
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(gson.toJson(toSerialize));
            }

        }
        System.out.println("[JsonConfig] Loading file: " + fileName);
        try (JsonReader reader = new JsonReader(new FileReader(file))) {
            return gson.fromJson(reader, clazz);
        }
    }

    public static void save(Object toSave) {
        String fileName = toSave.getClass().getSimpleName().toLowerCase() + ".json";
        File file = new File(fileName);

        System.out.println("[JsonConfig] Saving file: " + fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(gson.toJson(toSave));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}