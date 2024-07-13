package org.cneko.ctlib.common.file;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AssetsFile {
    private MemoryFileSystem files;
    public AssetsFile(MemoryFileSystem memoryFileSystem){
        this.files = memoryFileSystem;
    }
    public AssetsFile(Resources resources,String modID) throws IOException {
        this.files = resources.readDirectoryFromJar("assets/"+modID);
    }
    public LanguageAssets getLanguageAssets() throws IOException {
        return new LanguageAssets(files.readDirectory("lang/"));
    }
    public interface Assets{
    }

    public static class LanguageAssets implements Assets{
        private MemoryFileSystem files;
        private String defaultLanguage;
        public LanguageAssets(MemoryFileSystem memoryFileSystem){
            this.files = memoryFileSystem;
            this.defaultLanguage = "en_us";
        }
        public void setDefaultLanguage(@NotNull String lang){
            this.defaultLanguage = lang;
        }
        public String get(@NotNull String key,@NotNull String lang){
            try {
                String file = null;
                try {
                    file = files.readFile(lang + ".json", null);
                } catch (IOException e) {
                    return key;
                }
                JsonConfiguration json = JsonConfiguration.of(file);
                String value = json.getString(key);
                if (value == null && !lang.equals("en_us")) {
                    // 如果值为null,则返回英语
                    String EnglishFile = files.readFile("en_us.json", null);
                    JsonConfiguration EnglishJson = JsonConfiguration.of(EnglishFile);
                    value = EnglishJson.getString(key);
                }
                if (value == null) {
                    // 如果值为null,则返回key
                    value = key;
                }
                return value;
            }catch (Exception e){
                return key;
            }
        }
        public String get(@NotNull String key){
            return get(key,defaultLanguage);
        }


    }
}
