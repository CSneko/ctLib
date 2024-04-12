package org.cneko.ctlib.common.file;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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

    public YamlConfiguration(File file) throws IOException {
        this(file.toPath());
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
            System.out.println(e.getMessage());
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

    public void save(Path targetPath) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        try (OutputStream out = Files.newOutputStream(targetPath)) {
            yaml.dump(data, new OutputStreamWriter(out));
        }
    }

    public void save(File targetFile) throws IOException {
        save(targetFile.toPath());
    }

    public String getString(String path) {
        return (String) get(path);
    }

    public List<String> getStringList(String path) {
        return (List<String>) get(path);
    }

    public float getFloat(String path) {
        Object value = get(path);
        return (value instanceof Float) ? (Float) value : 0;
    }

    public double getDouble(String path) {
        Object value = get(path);
        return (value instanceof Double) ? (Double) value : 0;
    }

    public int getInt(String path) {
        Object value = get(path);
        return (value instanceof Integer) ? (Integer) value : 0;
    }

    public boolean getBoolean(String path) {
        Object value = get(path);
        return (value instanceof Boolean) && (Boolean) value;
    }

    public boolean getBoolean(String path, boolean defValue) {
        Object value = get(path);
        return (value instanceof Boolean) ? (Boolean) value : defValue;
    }

    public boolean isSet(String path) {
        return get(path) != null;
    }

    public boolean contains(String path) {
        return isSet(path);
    }

    public ArrayList<Integer> getIntList(String path) {
        Object value = get(path);
        if (value instanceof List) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Object obj : (List<?>) value) {
                if (obj instanceof Integer) {
                    list.add((Integer) obj);
                }
            }
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<Double> getDoubleList(String path) {
        Object value = get(path);
        if (value instanceof List) {
            ArrayList<Double> list = new ArrayList<>();
            for (Object obj : (List<?>) value) {
                if (obj instanceof Double) {
                    list.add((Double) obj);
                }
            }
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<Float> getFloatList(String path) {
        Object value = get(path);
        if (value instanceof List) {
            ArrayList<Float> list = new ArrayList<>();
            for (Object obj : (List<?>) value) {
                if (obj instanceof Float) {
                    list.add((Float) obj);
                }
            }
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<Object> getList(String path) {
        Object value = get(path);
        if (value instanceof List) {
            return new ArrayList<>((List<?>) value);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        return yaml.dump(data);
    }

    @Override
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

}
