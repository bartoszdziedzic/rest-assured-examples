package com.testinglaboratory.testingbasics.exercises;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class FileManager {
    private static final String PROJECT_PATH = new File(".").getAbsolutePath();

    @SneakyThrows
    public static void createDirectory(String path) {
        try {
            Files.createDirectory(Paths.get(PROJECT_PATH + "/target/" + path));
        } catch (FileAlreadyExistsException faee) {
            log.info("The directory is already there mate!");
            throw new RuntimeException("YOU HAVE FAILED ME FOR THE LAST TIME! ADMIRAL...");
        }
    }

    @SneakyThrows
    public static void createFile(String path) {
        try {
            Files.createFile(Path.of(PROJECT_PATH + "/target/" + path));
        }
        catch (FileAlreadyExistsException faee){
            log.info("The file is already there mate!");
            throw new RuntimeException("YOU HAVE FAILED ME FOR THE LAST TIME! ADMIRAL...");
        }
    }

    @SneakyThrows
    public static void writeToFileFile(String path, String text) {
        BufferedWriter writer = new BufferedWriter(new FileWriter(PROJECT_PATH + "/target/" + path, true));
        writer.append(text);
        writer.close();
    }

    @SneakyThrows
    public static String readFile(String path) {
        BufferedReader reader = new BufferedReader(new FileReader(PROJECT_PATH + "/target/" + path));
        String text = reader.readLine();
        reader.close();
        return text;
    }


    public static void deleteFile(String path) {
        File file = new File(PROJECT_PATH + "/target/" + path);
        boolean result = file.delete();
        assertTrue(result);
    }
}
