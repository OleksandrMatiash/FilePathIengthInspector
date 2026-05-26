package org.epam;

import java.io.IOException;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;


public class PathBytesCounter {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java PathBytesCounter <directory>");
            return;
        }

        Path rootDir = Paths.get(args[0]);
        if (!Files.isDirectory(rootDir)) {
            System.out.println("Provided path is not a directory.");
            return;
        }

        Map<Integer, String> totalBytesByFileName = new TreeMap<>();

        Files.walkFileTree(rootDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (!file.equals(rootDir)) {
                    String filePath = file.toString();
                    // Count bytes in UTF-8 encoding (ASCII=1, Cyrillic=2, etc.)
                    totalBytesByFileName.put(filePath.getBytes(StandardCharsets.UTF_8).length, filePath);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        System.out.println("Total UTF-8 bytes of all file paths: ");
        for (Map.Entry<Integer, String> entry : totalBytesByFileName.entrySet()) {
            System.out.println(entry.getKey() + "bytes - " + entry.getValue());
        }
    }
}