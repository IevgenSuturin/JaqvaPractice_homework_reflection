package ua.skillsup.java_reflection.homework;

import ua.skillsup.java_reflection.homework.context.Context;

import java.time.LocalDate;

public class Application {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Human human = new Human("Ivan", "Petrovich", "chess", LocalDate.of(1996, 12, 11), LocalDate.of(2011, 05, 22));
        System.out.println("Initial human:");
        System.out.println(human);

        Context context = new Context();

        String jsonString = context.getJsonSerializationString(human);
        System.out.println("jsonString:");
        System.out.println(jsonString);

        Human recoverHuman = (Human) context.getObjectByJsonString(jsonString, Human.class);
        System.out.println("Recovered human:");
        System.out.println(recoverHuman);
    }
}
