package ua.skillsup.java_reflection.homework;

import ua.skillsup.java_reflection.homework.services.CustomDateFormat;
import ua.skillsup.java_reflection.homework.services.JsonValue;

import java.time.LocalDate;

public class Human {
    private String firstName;
    private String lastName;
    @JsonValue(name = "fun")
    private String hobby;
    @CustomDateFormat(format = "dd-MM-yyyy")
    private LocalDate birthDate;
    private LocalDate marryDate;

    public Human() {
    }

    @Override
    public String toString() {
        return "Human{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hobby='" + hobby + '\'' +
                ", birthDate=" + birthDate +
                ", marryDate=" + marryDate +
                '}';
    }

    public Human(String firstName, String lastName, String hobby, LocalDate birthDate, LocalDate marryDate) {
        this.firstName = firstName;
        this.lastName = null;
        this.hobby = hobby;
        this.birthDate = birthDate;
        this.marryDate = marryDate;
    }
}
