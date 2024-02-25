package org.cneko.ctlib.common.file;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class JsonConfiguration {
    private Map<String, Object> configData;
    private Gson gson;
    private Path filePath;

    public JsonConfiguration(Path filePath) throws IOException {
        this.filePath = filePath;
        gson = new Gson(); // 初始化 Gson 对象
        configData = loadConfigData(filePath);
    }

    public JsonConfiguration(String jsonString) {
        gson = new Gson(); // 初始化 Gson 对象
        if (jsonString == null) {
            configData = new HashMap<>();
        } else {
            configData = gson.fromJson(jsonString, new TypeToken<Map<String, Object>>() {}.getType());
        }
    }

    private Map<String, Object> loadConfigData(Path filePath) throws IOException {
        if (Files.exists(filePath)) {
            try (FileReader reader = new FileReader(filePath.toFile())) {
                return gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());
            }
        } else {
            return new HashMap<>();
        }
    }

    public String getString(String key) {
        return (String) configData.get(key);
    }

    public int getInt(String key) {
        return (int) configData.get(key);
    }

    public double getDouble(String key) {
        return (double) configData.get(key);
    }

    public Object get(String key) {
        return configData.get(key);
    }

    public void set(String key, Object value) {
        configData.put(key, value);
    }

    public void save() throws IOException {
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            gson.toJson(configData, writer);
        }
    }
}
