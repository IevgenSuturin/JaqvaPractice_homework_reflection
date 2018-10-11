package ua.skillsup.java_reflection.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.skillsup.java_reflection.homework.context.Context;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceTest {
    static Context context = new Context();

    @DisplayName("Parsing Object")
    @Test
    public void testCase1() throws IllegalAccessException {
        //Given
        final String expectedJsonString = "{\"firstName\":\"Ivan\", \"birthDate\":\"11-12-1996\", \"fun\":\"chess\", \"marryDate\":\"2011-05-22\"}";

        //When
        Human human = new Human("Ivan", "Petrovich", "chess", LocalDate.of(1996, 12, 11), LocalDate.of(2011, 5, 22));

        //Then
        assertThat(context.getJsonSerializationString(human)).isEqualTo(expectedJsonString);
    }

    @DisplayName("Parsing String")
    @Test
    public void testCase2() throws InstantiationException, IllegalAccessException {
        //Given
        final Human expectedHuman = new Human("Ivan", null, "chess", LocalDate.of(1996, 12, 11), LocalDate.of(2011, 5, 22));

        //When
        String jsonString = "{\"firstName\":\"Ivan\", \"birthDate\":\"11-12-1996\", \"fun\":\"chess\", \"marryDate\":\"2011-05-22\"}";

        //Than
        assertThat(expectedHuman).isEqualToComparingFieldByField(context.getObjectByJsonString(jsonString, Human.class));

    }
}
