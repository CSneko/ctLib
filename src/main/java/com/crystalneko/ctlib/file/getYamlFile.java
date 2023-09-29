package com.crystalneko.ctlib.file;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class getYamlFile {
    /**
     * 获取yaml文件的顶级键，如果顶级键的某个子键不为null
     * @param yamlPath yaml文件路径
     * @param notNullKey 要求不为null的键
     */
    public static String[] getYamlFileIfKeyNotNull(String yamlPath,String notNullKey){
        InputStream input = null;
        try {
            input = new FileInputStream(yamlPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Yaml yaml = new Yaml();
        Map<String, Object> map = yaml.load(input);
        List<String> topLevelKeys = new ArrayList<>(map.keySet());
        topLevelKeys.removeIf(key -> map.get(key) == null || (map.get(key) instanceof Map && ((Map<?, ?>) map.get(key)).containsKey(notNullKey) && ((Map<?, ?>) map.get(key)).get(notNullKey) == null));
        String[] keysArray = topLevelKeys.toArray(new String[0]);
        return keysArray;
    }
}
