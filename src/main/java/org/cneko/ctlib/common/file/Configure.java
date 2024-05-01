package org.cneko.ctlib.common.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Configure {
    Object get(String path);

    void set(String path, Object value);

    void save() throws IOException;

    String getString(String path);

    List<String> getStringList(String path);

    float getFloat(String path);

    double getDouble(String path);

    int getInt(String path);

    boolean getBoolean(String path);

    boolean getBoolean(String path, boolean defValue);

    boolean isSet(String path);

    boolean contains(String path);

    ArrayList<Integer> getIntList(String path);

    ArrayList<Double> getDoubleList(String path);

    ArrayList<Float> getFloatList(String path);

    ArrayList<Object> getList(String path);

    String toString();

    boolean equals(Object obj);

    boolean equalsCaseIgnoreCase(Object obj);

}
