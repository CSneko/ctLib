package org.cneko.ctlib.common.file;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class Resources {
    private File file;
    public Resources(Path jar){
        this.file = jar.toFile();
    }
    public Resources(File jar){
        this.file = jar;
    }
    public Resources(Class<?> clazz) throws URISyntaxException {
        URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
        this.file = new File(url.getFile());
    }

    /**
     * 从jar包中复制资源
     * @param resourcePath 资源目录
     * @param targetPath 目标目录
     * @throws IOException 出现IO异常
     */
    public void copyFileFromJar(String resourcePath, String targetPath) throws IOException {
        if (resourcePath == null || targetPath == null) {
            throw new IllegalArgumentException("Resource path and target path cannot be null.");
        }

        try (JarInputStream jarInputStream = new JarInputStream(new FileInputStream(this.file))) {
            JarEntry jarEntry;
            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                if (jarEntry.getName().equals(resourcePath)) {
                    try (FileOutputStream fileOutputStream = new FileOutputStream(targetPath)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = jarInputStream.read(buffer)) != -1) {
                            fileOutputStream.write(buffer, 0, bytesRead);
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * 从jar包中复制目录
     * @param resourcePath 资源目录
     * @param targetPath 目标目录
     * @throws IOException 出现IO异常
     */
    public void copyDirectoryFromJar(String resourcePath, String targetPath) throws IOException {
        if (resourcePath == null || targetPath == null) {
            throw new IllegalArgumentException("Resource path and target path cannot be null.");
        }

        try (JarInputStream jarInputStream = new JarInputStream(new FileInputStream(this.file))) {
            JarEntry jarEntry;
            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                String entryPath = jarEntry.getName();
                if (entryPath.startsWith(resourcePath + "/")) {
                    File targetDir = new File(targetPath, entryPath.substring(resourcePath.length() + 1));
                    targetDir.mkdirs(); // Create all necessary directories
                    if (!jarEntry.isDirectory()) { // If it's a file within the directory
                        try (FileOutputStream fileOutputStream = new FileOutputStream(targetDir)) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = jarInputStream.read(buffer)) != -1) {
                                fileOutputStream.write(buffer, 0, bytesRead);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 从jar包中读取文件
     * @param resourcePath 资源目录
     * @return 文件内容
     * @throws IOException IO 异常
     */
    public String readFileFromJar(String resourcePath) throws IOException {
        if (resourcePath == null) {
            throw new IllegalArgumentException("Resource path cannot be null.");
        }

        StringBuilder content = new StringBuilder();
        try (JarInputStream jarInputStream = new JarInputStream(new FileInputStream(this.file))) {
            JarEntry jarEntry;
            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                if (jarEntry.getName().equals(resourcePath)) {
                    try (InputStreamReader inputStreamReader = new InputStreamReader(jarInputStream);
                         BufferedReader reader = new BufferedReader(inputStreamReader)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                    }
                    break;
                }
            }
        }
        return content.toString().trim(); // Remove trailing newline if any
    }

    /**
     * 从jar中读取目录
     * @param resourcePath 资源目录
     */
    public MemoryFileSystem readDirectoryFromJar(String resourcePath) throws IOException {
        if (resourcePath == null) {
            throw new IllegalArgumentException("Resource path cannot be null.");
        }

        MemoryFileSystem memoryFileSystem = new MemoryFileSystem();

        try (JarInputStream jarInputStream = new JarInputStream(new FileInputStream(this.file))) {
            JarEntry jarEntry;
            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                String entryPath = jarEntry.getName();
                if (entryPath.startsWith(resourcePath + "/")) {
                    String relativePath = entryPath.substring(resourcePath.length() + 1);

                    if (!jarEntry.isDirectory()) { // If it's a file
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = jarInputStream.read(buffer)) != -1) {
                            memoryFileSystem.writeFile(relativePath, jarInputStream, buffer, bytesRead);
                        }
                    }
                }
            }
        }

        return memoryFileSystem;
    }
}
