package org.cneko.ctlib.common.file;

import java.io.IOException;
import java.util.List;

public interface Configure {
    Object get(String path);

    void set(String path, Object value);

    void save() throws IOException;

    String getString(String path);

    float getFloat(String path);

    double getDouble(String path);

    int getInt(String path);

    boolean getBoolean(String path);

    boolean getBoolean(String path, boolean defValue);

    boolean contains(String path);

    default boolean isSet(String path){
        return contains(path);
    }

    List<String> getStringList(String path);

    List<Integer> getIntList(String path);

    List<Double> getDoubleList(String path);

    List<Float> getFloatList(String path);

    List<Object> getList(String path);

    String toString();

    boolean equals(Object obj);

    boolean equalsCaseIgnoreCase(Object obj);

}
