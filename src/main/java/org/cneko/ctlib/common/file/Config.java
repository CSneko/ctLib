package org.cneko.ctlib.common.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config implements Configure{
    private final Configure config;
    public Config(Configure config){
        this.config = config;
    }

    @Override
    public Object get(String path) {
        return config.get(path);
    }

    @Override
    public void set(String path, Object value) {
        config.set(path, value);
    }

    @Override
    public void save() throws IOException {
        config.save();
    }

    @Override
    public String getString(String path) {
        return config.getString(path);
    }

    @Override
    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    @Override
    public float getFloat(String path) {
        return config.getFloat(path);
    }

    @Override
    public double getDouble(String path) {
        return config.getDouble(path);
    }

    @Override
    public int getInt(String path) {
        return config.getInt(path);
    }

    @Override
    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    @Override
    public boolean getBoolean(String path, boolean defValue) {
        return config.getBoolean(path, defValue);
    }

    @Override
    public boolean isSet(String path) {
        return config.isSet(path);
    }

    @Override
    public boolean contains(String path) {
        return config.contains(path);
    }

    @Override
    public ArrayList<Integer> getIntList(String path) {
        return config.getIntList(path);
    }

    @Override
    public ArrayList<Double> getDoubleList(String path) {
        return config.getDoubleList(path);
    }

    @Override
    public ArrayList<Float> getFloatList(String path) {
        return config.getFloatList(path);
    }

    @Override
    public ArrayList<Object> getList(String path) {
        return config.getList(path);
    }

    @Override
    public String toString() {
        return config.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return config.equals(obj);
    }

    @Override
    public boolean equalsCaseIgnoreCase(Object obj) {
        return config.equalsCaseIgnoreCase(obj);
    }

    @Override
    public Config toConfig() {
        return this;
    }
}
