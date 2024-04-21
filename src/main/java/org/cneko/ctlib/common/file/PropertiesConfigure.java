package org.cneko.ctlib.common.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesConfigure implements Configure {
    private final Properties properties;

    public PropertiesConfigure(Path filePath) throws IOException {
        this.properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(filePath.toFile())) {
            properties.load(fileInputStream);
        }
    }

    @Override
    public Object get(String path) {
        return properties.getProperty(path);
    }

    @Override
    public void set(String path, Object value) {
        throw new UnsupportedOperationException("Setting properties is not supported.");
    }

    @Override
    public void save() throws IOException {
        throw new UnsupportedOperationException("Saving properties is not supported.");
    }

    @Override
    public String getString(String path) {
        return properties.getProperty(path);
    }

    @Override
    public List<String> getStringList(String path) {
        // Properties 文件不支持列表，直接返回空列表
        return new ArrayList<>();
    }

    @Override
    public float getFloat(String path) {
        String value = properties.getProperty(path);
        return value != null ? Float.parseFloat(value) : 0;
    }

    @Override
    public double getDouble(String path) {
        String value = properties.getProperty(path);
        return value != null ? Double.parseDouble(value) : 0;
    }

    @Override
    public int getInt(String path) {
        String value = properties.getProperty(path);
        return value != null ? Integer.parseInt(value) : 0;
    }

    @Override
    public boolean getBoolean(String path) {
        String value = properties.getProperty(path);
        return Boolean.parseBoolean(value);
    }

    @Override
    public boolean getBoolean(String path, boolean defValue) {
        String value = properties.getProperty(path);
        return value != null ? Boolean.parseBoolean(value) : defValue;
    }

    @Override
    public boolean isSet(String path) {
        return properties.containsKey(path);
    }

    @Override
    public boolean contains(String path) {
        return properties.containsKey(path);
    }

    @Override
    public ArrayList<Integer> getIntList(String path) {
        // Properties 文件不支持列表，直接返回空列表
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Double> getDoubleList(String path) {
        // Properties 文件不支持列表，直接返回空列表
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Float> getFloatList(String path) {
        // Properties 文件不支持列表，直接返回空列表
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Object> getList(String path) {
        // Properties 文件不支持列表，直接返回空列表
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return properties.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PropertiesConfigure && properties.equals(((PropertiesConfigure) obj).properties);
    }

    @Override
    public boolean equalsCaseIgnoreCase(Object obj) {
        return equals(obj); // Properties 文件没有忽略大小写的概念
    }

    @Override
    public Config toConfig() {
        return new Config(this);
    }
}