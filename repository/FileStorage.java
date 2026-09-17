package com.library.repository;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {

    public static List<String> readLines(String path) {
        List<String> lines = new ArrayList<>();
        Path p = Paths.get(path);
        try {
            if (!Files.exists(p)) {
                Files.createDirectories(p.getParent());
                Files.createFile(p);
                return lines;
            }
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty()) lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + path, e);
        }
        return lines;
    }

    public static void writeLines(String path, List<String> lines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + path, e);
        }
    }

    public static void appendLine(String path, String line) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error appending to file: " + path, e);
        }
    }
}
