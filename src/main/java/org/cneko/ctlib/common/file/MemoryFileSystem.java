package org.cneko.ctlib.common.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MemoryFileSystem {

    private final Map<String, byte[]> files;
    private final Map<String, String> directories;

    public MemoryFileSystem() {
        this.files = new HashMap<>();
        this.directories = new HashMap<>();
    }

    /**
     * Creates a directory in the memory file system.
     *
     * @param path The path of the directory to create.
     */
    public void createDirectory(String path) {
        directories.put(path, "");
    }

    /**
     * Writes a file to the memory file system.
     *
     * @param path The path of the file to write.
     * @param inputStream The InputStream containing the file content.
     * @param buffer A byte buffer for reading data.
     * @param bytesRead The number of bytes read from the InputStream (passed for efficiency).
     * @throws IOException If an I/O error occurs.
     */
    public void writeFile(String path, InputStream inputStream, byte[] buffer, int bytesRead) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        files.put(path, outputStream.toByteArray());
    }

    /**
     * Reads the content of a file from the memory file system.
     *
     * @param path The path of the file to read.
     * @return The byte array containing the file content, or null if the file doesn't exist.
     */
    public byte[] readFile(String path) {
        return files.get(path);
    }

    /**
     * Checks if a path exists in the memory file system (either as a file or directory).
     *
     * @param path The path to check.
     * @return True if the path exists, false otherwise.
     */
    public boolean exists(String path) {
        return files.containsKey(path) || directories.containsKey(path);
    }

    /**
     * Reads a subdirectory from the current memory file system and returns a new MemoryFileSystem object containing the subdirectory structure and file content.
     *
     * @param subdirectoryPath The path of the subdirectory within the current memory file system.
     * @return A new MemoryFileSystem object representing the subdirectory structure and file content.
     * @throws IOException If an I/O error occurs (highly unlikely in this context).
     */
    public MemoryFileSystem readDirectory(String subdirectoryPath) throws IOException {
        if (subdirectoryPath == null) {
            throw new IllegalArgumentException("Subdirectory path cannot be null.");
        }

        if (!subdirectoryPath.isEmpty() && !subdirectoryPath.startsWith("/")) {
            subdirectoryPath = "/" + subdirectoryPath;
        }

        MemoryFileSystem subdirectoryFileSystem = new MemoryFileSystem();

        for (Map.Entry<String, byte[]> entry : files.entrySet()) {
            String filePath = entry.getKey();
            if (filePath.startsWith(subdirectoryPath + "/")) {
                String relativePath = filePath.substring(subdirectoryPath.length() + 1);
                subdirectoryFileSystem.files.put(relativePath, entry.getValue());
            }
        }

        for (Map.Entry<String, String> entry : directories.entrySet()) {
            String dirPath = entry.getKey();
            if (dirPath.startsWith(subdirectoryPath + "/")) {
                String relativePath = dirPath.substring(subdirectoryPath.length() + 1);
                subdirectoryFileSystem.directories.put(relativePath, entry.getValue());
            }
        }

        return subdirectoryFileSystem;
    }
}