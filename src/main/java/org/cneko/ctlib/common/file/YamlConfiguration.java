package org.cneko.ctlib.common.file;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class YamlConfiguration {
    private final Map<String, Object> data;
    private final Path path;

    public YamlConfiguration(Path path) throws IOException {
        this.path = path;
        if (Files.exists(path)) {
            try (InputStream in = Files.newInputStream(path)) {
                Yaml yaml = new Yaml();
                data = yaml.load(in);
            }
        } else {
            data = new LinkedHashMap<>();
        }
    }

    public YamlConfiguration(String yamlContent) {
        Yaml yaml = new Yaml();
        data = yaml.load(yamlContent);
        this.path = null;
    }

    public Object get(String path) {
        String[] keys = path.split("\\.");
        Map<String, Object> current = data;
        for (int i = 0; i < keys.length - 1; i++) {
            current = (Map<String, Object>) current.get(keys[i]);
            if (current == null) {
                return null;
            }
        }
        return current.get(keys[keys.length - 1]);
    }

    public void set(String path, Object value) {
        String[] keys = path.split("\\.");
        Map<String, Object> current = data;
        for (int i = 0; i < keys.length - 1; i++) {
            current = (Map<String, Object>) current.computeIfAbsent(keys[i], k -> new LinkedHashMap<>());
        }
        current.put(keys[keys.length - 1], value);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() throws IOException {
        if (path == null) {
            return;
        }
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        try (OutputStream out = Files.newOutputStream(path)) {
            yaml.dump(data, new OutputStreamWriter(out));
        }
    }

    public String getString(String path) {
        return (String) get(path);
    }

    public List<String> getStringList(String path) {
        return (List<String>) get(path);
    }

    public int getInt(String path) {
        Object value = get(path);
        if(value != null){
            return (Integer) value;
        }else {
            return 0;
        }
    }

    public boolean getBoolean(String path) {
        Object value = get(path);
        if(value != null){
            return (Boolean) value;
        }else {
            return false;
        }
    }

    @Override
    public String toString() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        return yaml.dump(data);
    }
}
