package com.testinglaboratory.testingbasics.exercises;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*TODO EXERCISE create tests checking:
   - number of letters in your first name
   - equality of length of your first and last name
   - equality of your first and last name
   - your first name having more than 3 letters
 */
public class BasicsExerciseTest {
    String firstName = "Bartosz";
    String lastName = "Dziedzic";



    @Test
    public void lettersInName(){
        assertThat(firstName.length()).as("Wyjebało się.").isEqualTo(7);
    }

    @Test
    public void lettersInFirstAndLast(){
        assertThat(firstName.length()).isEqualTo(lastName.length());
    }

    @Test
    public void firstAndLastNamesEqual(){
        assertEquals(firstName,lastName);
    }

    @Test
    public void firstNameHasMoreThanThreeLetters(){
        assertThat(firstName).hasSizeGreaterThan(3);
    }
}