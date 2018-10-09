package ua.skillsup.java_reflection.homework;

import ua.skillsup.java_reflection.homework.context.Context;

import java.time.LocalDate;

public class Application {
    public static void main(String[] args) throws IllegalAccessException{
        Human human = new Human("Ivan", "Petrovich", "chess", LocalDate.of(1996, 12, 11), LocalDate.of(2011, 05, 22));
        Context context = new Context();

        System.out.println(context.getJsonSerializationString(human));
    }
}
