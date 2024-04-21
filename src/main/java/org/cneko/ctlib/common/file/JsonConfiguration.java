package org.cneko.ctlib.common.file;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.cneko.ctlib.common.util.base.FileUtil;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonConfiguration implements Configure{
    private String original;
    private Map<String, Object> configData;
    private Gson gson;
    private Path filePath;

    public JsonConfiguration(Path filePath) throws IOException {
        this.filePath = filePath;
        gson = new Gson();
        configData = loadConfigData(filePath);
        original = FileUtil.readFileWithException(filePath.toString());
    }

    public JsonConfiguration(String jsonString) {
        gson = new Gson();
        if (jsonString == null) {
            configData = new HashMap<>();
        } else {
            configData = gson.fromJson(jsonString, new TypeToken<Map<String, Object>>() {}.getType());
        }
        original = jsonString;
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
        Object value = get(key);
        if (value instanceof String) {
            return (String) value;
        } else if (value instanceof Map || value instanceof List) {
            return new Gson().toJson(value);
        } else {
            return null;
        }
    }

    public int getInt(String key) {
        try {
            Object value = get(key);
            return value instanceof Number ? ((Number) value).intValue() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public double getDouble(String key) {
        try {
            Object value = get(key);
            return value instanceof Number ? ((Number) value).doubleValue() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public float getFloat(String key) {
        try {
            Object value = get(key);
            return value instanceof Number ? ((Number) value).floatValue() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean getBoolean(String key) {
        try {
            Object value = get(key);
            return value instanceof Boolean ? (boolean) value : false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        try {
            Object value = get(key);
            return value instanceof Boolean ? (boolean) value : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public Object get(String key) {
        // 首先尝试直接读取键值
        Object value = getValue(configData, key);
        if (value != null) {
            return value;
        }

        // 如果直接读取失败，则尝试读取位于父键值的子键值
        String parentKey = getParentKey(key);
        if (parentKey != null) {
            Object parentValue = getValue(configData, parentKey);
            if (parentValue instanceof Map) {
                return getValue(parentValue, getLastKey(key));
            }
        }

        // 如果找不到父键值，则直接返回null
        return null;
    }

    // 获取键值
    private Object getValue(Object object, String key) {
        if (object instanceof Map) {
            return ((Map<String, Object>) object).get(key);
        } else {
            return null;
        }
    }

    // 获取父键值
    private String getParentKey(String key) {
        int lastIndex = key.lastIndexOf(".");
        return lastIndex != -1 ? key.substring(0, lastIndex) : null;
    }

    // 获取最后一个键
    private String getLastKey(String key) {
        int lastIndex = key.lastIndexOf(".");
        return lastIndex != -1 ? key.substring(lastIndex + 1) : key;
    }

    @Override
    public boolean isSet(String path) {
        return get(path) != null;
    }

    public ArrayList<String> getStringList(String key) {
        Object value = get(key);
        if (value instanceof List) {
            ArrayList<String> list = new ArrayList<>();
            for (Object obj : (List<?>) value) {
                if (obj instanceof String) {
                    list.add((String) obj);
                }
            }
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<Integer> getIntList(String key) {
        Object value = get(key);
        if (value instanceof List) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Object obj : (List<?>) value) {
                if (obj instanceof Number) {
                    list.add(((Number) obj).intValue());
                }
            }
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<Double> getDoubleList(String key) {
        Object value = get(key);
        if (value instanceof List) {
            ArrayList<Double> list = new ArrayList<>();
            for (Object obj : (List<?>) value) {
                if (obj instanceof Number) {
                    list.add(((Number) obj).doubleValue());
                }
            }
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<Float> getFloatList(String key) {
        Object value = get(key);
        if (value instanceof List) {
            ArrayList<Float> list = new ArrayList<>();
            for (Object obj : (List<?>) value) {
                if (obj instanceof Number) {
                    list.add(((Number) obj).floatValue());
                }
            }
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<JsonConfiguration> getJsonList(String key) {
        Object value = get(key);
        if (value instanceof List) {
            ArrayList<JsonConfiguration> list = new ArrayList<>();
            for (Object obj : (List<?>) value) {
                if (obj instanceof Map) {
                    list.add(new JsonConfiguration(new Gson().toJson(obj)));
                }
            }
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<Object> getList(String key) {
        Object value = get(key);
        if (value instanceof List) {
            return new ArrayList<>((List<?>) value);
        } else {
            return new ArrayList<>();
        }
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    /**
     * 转换为JsonList，如果自身不是一个数组，返回空列表
     * @return Json数组
     */
    public ArrayList<JsonConfiguration> toJsonList() {
        ArrayList<JsonConfiguration> jsonList = new ArrayList<>();
        JsonElement element = new Gson().fromJson(this.toString(), JsonElement.class);
        if (element.isJsonArray()) {
            JsonArray jsonArray = element.getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    jsonList.add(new JsonConfiguration(jsonObject.toString()));
                }
            }
        }
        return jsonList;
    }


    public JsonConfiguration getJsonConfiguration(String key) {
        Object value = get(key);
        if (value instanceof Map) {
            return new JsonConfiguration(new Gson().toJson(value));
        } else {
            return null;
        }
    }

    public void set(String key, Object value) {
        configData.put(key, value);
    }

    public void save() throws IOException {
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            gson.toJson(configData, writer);
        }
    }

    public String getOriginal() {
        return original;
    }
    @Override
    public String toString() {
        return gson.toJson(configData);
    }

    public boolean equals(Object obj) {
        return obj.toString().equals(this.toString());
    }

    public boolean equalsCaseIgnoreCase(Object obj) {
        if(obj.toString() != null) {
            return obj.toString().equalsIgnoreCase(this.toString());
        }else {
            return false;
        }
    }

    @Override
    public Config toConfig() {
        return new Config(this);
    }

    /**
     * 尝试将json 转换为yaml
     * @return 转换后的Yaml
     */
    public YamlConfiguration toYaml() {
        // 将 JSON 字符串转换为 Map
        Map<String, Object> map = gson.fromJson(original, new TypeToken<Map<String, Object>>() {}.getType());

        // 设置 YAML 输出格式
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        // 将 Map 转换为 YAML 格式的字符串
        return YamlConfiguration.of(yaml.dump(map));
    }

    public static JsonConfiguration fromFile(Path filePath) throws IOException{
        return new JsonConfiguration(filePath);
    }
    public static JsonConfiguration fromFile(File file) throws IOException{
        return new JsonConfiguration(file.toPath());
    }
    public static JsonConfiguration of(String jsonString) {
        return new JsonConfiguration(jsonString);
    }

}
