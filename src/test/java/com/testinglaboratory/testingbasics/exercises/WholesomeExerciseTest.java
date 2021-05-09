package com.testinglaboratory.testingbasics.exercises;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

/*TODO EXERCISE
 * - Create class Toy having fields: make, name, colour, material
 *  - having methods returning greeting (including name, colour and material)
 *  - create getters and setters
 *  - write tests that put a text file with toy data to a text file in a folder
 *  - use FileManager to operate on files
 *  - remember to prepare data
 *  - remember to clean up after tests
 */
public class WholesomeExerciseTest {

    @BeforeEach
    public void createFile(){
        FileManager.createFile("MyTestFolder/toy.txt");
    }

    @BeforeAll
    public static void createFolder(){
        FileManager.createDirectory("MyTestFolder");
    }

    @AfterEach
    public void deleteCreatedFile(){
        FileManager.deleteFile("MyTestFolder/toy.txt");
    }

    @AfterAll
    public static void deleteCreatedFolder(){
        FileManager.deleteFile("MyTestFolder");
    }

    @Test
    public void basicPrintOutTest(){
        Toy toy = new Toy("Lego","Star Wars","Yoda","Green");
        System.out.println(toy.greeting());
    }

    @Test
    public void writeToFile(){
        Toy toy = new Toy("Lego","Star Wars","Yoda","Green");
        FileManager.writeToFileFile("MyTestFolder/toy.txt", toy.toString());
    }

    @Test
    public void readFile(){
        Toy toy = new Toy("Lego","Star Wars","Yoda","Green");
        FileManager.writeToFileFile("MyTestFolder/toy.txt", toy.toString());
        String toyText = FileManager.readFile("MyTestFolder/toy.txt");
        assertThat(toyText).isEqualTo(toy.toString());

    }
}