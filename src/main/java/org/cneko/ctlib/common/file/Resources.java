package org.cneko.ctlib.common.file;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
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
                    String relativePath = entryPath.substring(resourcePath.length() + 1);
                    File targetFile = new File(targetPath, relativePath);

                    // Ensure target directory exists
                    File targetDir = targetFile.getParentFile();
                    if (targetDir != null && !targetDir.exists()) {
                        targetDir.mkdirs(); // Create all necessary directories
                    }

                    if (jarEntry.isDirectory()) {
                        // Create empty directory if it's a directory in JAR
                        targetFile.mkdirs();
                    } else {
                        // Copy file content
                        try (FileOutputStream fileOutputStream = new FileOutputStream(targetFile)) {
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

        // Create a temporary directory to extract directory structure (can be replaced with in-memory approach)
        String tempDirPrefix = "memory-fs-";
        File tempDir = null;
        while (tempDir == null) { // Retry until successful creation
            try {
                tempDir = File.createTempFile(tempDirPrefix, null);
            } catch (IOException e) {
                // Handle potential exception during temporary file creation (e.g., retry logic)
                System.err.println("Failed to create temporary file: " + e.getMessage());
            }
        }

        tempDir.delete(); // Convert temporary file to directory
        tempDir.mkdirs();

        try {
            // Extract directory structure to temporary directory
            copyDirectoryFromJar(resourcePath, tempDir.getAbsolutePath());
            this.tempDir = tempDir;
            // Build MemoryFileSystem from extracted structure (alternative approach possible)
            buildMemoryFileSystemFromDir(tempDir, memoryFileSystem);
        } finally {
            // Clean up temporary directory (if using temporary directory approach)
            deleteDirectory(tempDir);
        }

        return memoryFileSystem;
    }
    private File tempDir;
    private void buildMemoryFileSystemFromDir(File dir, MemoryFileSystem memoryFileSystem) throws IOException {
        for (File file : dir.listFiles()) {
            String name = file.getName();
            if(file.isDirectory()){
                buildMemoryFileSystemFromDir(file,memoryFileSystem);
            }else {
                name = file.getPath();
                name = name.replace(tempDir.getAbsolutePath(), "");
                name = name.substring(1);
                memoryFileSystem.addFile(name, Files.readAllBytes(file.toPath()));
            }
        }
    }








    private void deleteDirectory(File directory) throws IOException {
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                deleteDirectory(file);
            }
        }
        if (!directory.delete()) {
            throw new IOException("Failed to delete directory: " + directory);
        }
    }




}
