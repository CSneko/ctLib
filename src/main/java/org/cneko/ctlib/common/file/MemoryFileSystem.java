package org.cneko.ctlib.common.file;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MemoryFileSystem {

    private final Map<String, byte[]> files;

    public MemoryFileSystem() {
        this.files = new HashMap<>();
    }
    public Map<String, byte[]> getFiles() {
        return files;
    }

    /**
     * Adds a file to the memory file system.
     *
     * @param filename The filename (including path information if applicable).
     * @param content The byte content of the file (can be null for entries from JAR).
     */
    public void addFile(String filename, byte[] content) {
        this.files.put(filename, content);
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
    public String readFile(String path, @Nullable String charset) throws IOException {
        byte[] content = readFile(path);
        if (content == null) {
            return null;
        }
        return new String(content, charset != null ? charset : "UTF-8");
    }

    /**
     * Checks if a path exists in the memory file system (either as a file or directory).
     *
     * @param path The path to check.
     * @return True if the path exists, false otherwise.
     */
    public boolean exists(String path) {
        return files.containsKey(path);
    }

    /**
     * Reads a subdirectory from the current memory file system and returns a new MemoryFileSystem object containing the subdirectory structure and file content.
     *
     * @param subdirectoryPath The path of the subdirectory within the current memory file system.
     * @return A new MemoryFileSystem object representing the subdirectory structure and file content.
     */
    public MemoryFileSystem readDirectory(String subdirectoryPath) throws IOException {
        if (subdirectoryPath == null) {
            throw new IllegalArgumentException("Subdirectory path cannot be null.");
        }

        if (subdirectoryPath.isEmpty()) {
            subdirectoryPath = ""; // Handle empty subdirectory path
        }

        MemoryFileSystem subdirectoryFileSystem = new MemoryFileSystem();

        // Iterate through existing files
        for (Map.Entry<String, byte[]> entry : files.entrySet()) {
            String filePath = entry.getKey();

            // Check if the filename starts with the subdirectory path (excluding the trailing slash, if any)
            if (filePath.startsWith(subdirectoryPath)) {
                // Extract filename without the subdirectory path
                int pathLength = subdirectoryPath.length();
                String filename = filePath.substring(pathLength);
                subdirectoryFileSystem.files.put(filename, entry.getValue());
            }
        }

        return subdirectoryFileSystem;
    }


}