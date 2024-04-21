package org.cneko.ctlib.common.util.base;

import com.google.gson.Gson;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    /**
     * 读取文件数据
     * @param path 文件路径
     * @return 文件内容（如果出错返回null）
     */
    public static String readFile(String path) {
        try{
            return new String(Files.readAllBytes(Paths.get(path)));
        }catch (IOException e){
            return null;
        }
    }

    /**
     * 读取文件数据
     * @param path 文件路径
     * @return 文件内容
     */
    public static String readFileWithException(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    /**
     * 文件类型检查
     */
    public static class FileChecker{
        public static enum FileTypes {
            JSON, YAML, UNKNOWN
        }

        /**
         * 检查字符串内容的文件类型
         * @param content 文件内容字符串
         * @return 文件类型
         */
        public static FileTypes checkFileType(String content) {
            if (isJson(content)) {
                return FileTypes.JSON;
            } else if (isYaml(content)) {
                return FileTypes.YAML;
            } else {
                return FileTypes.UNKNOWN;
            }
        }

        /**
         * 检查文件类型
         * @param file 文件路径
         * @return 文件类型
         */
        public static FileTypes checkFileType(File file) {
            try (Reader reader = Files.newBufferedReader(file.toPath())) {
                String content = readContent(reader);
                return checkFileType(content);
            } catch (IOException e) {
                return FileTypes.UNKNOWN;
            }
        }

        /**
         * 检查文件类型
         * @param file 文件对象
         * @return 文件类型
         */
        public static FileTypes checkFileType(Path file) {
            try (Reader reader = Files.newBufferedReader(file)) {
                String content = readContent(reader);
                return checkFileType(content);
            } catch (IOException e) {
                return FileTypes.UNKNOWN;
            }
        }


        private static String readContent(Reader reader) throws IOException {
            StringBuilder sb = new StringBuilder();
            int data;
            while ((data = reader.read()) != -1) {
                sb.append((char) data);
            }
            return sb.toString();
        }

        public static boolean isJson(String content) {
            try {
                new Gson().fromJson(content, Object.class);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public static boolean isYaml(String content) {
            try {
                new Yaml().load(content);
                return true;
            } catch (YAMLException e) {
                return false;
            }
        }

    }
}
