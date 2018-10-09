package ua.skillsup.java_reflection.homework.context;

import ua.skillsup.java_reflection.homework.services.CustomDateFormat;
import ua.skillsup.java_reflection.homework.services.JsonValue;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class Context {

    private Map<String, String> getJsonSerializationMap(Object object) throws IllegalAccessException{
        Map<String, String> jsonStrings = new HashMap<>();

            Class<?> objectClass = requireNonNull(object).getClass();

            String fieldName;
            String fieldValue;
            for (Field aField : objectClass.getDeclaredFields()) {
                aField.setAccessible(true);
                fieldName = aField.getName();
                if (aField.isAnnotationPresent(JsonValue.class)) {
                    fieldName = aField.getAnnotation(JsonValue.class).name();
                }
                if (aField.get(object) instanceof LocalDate) {
                    if (aField.isAnnotationPresent(CustomDateFormat.class)) {
                        LocalDate date = (LocalDate) aField.get(object);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(aField.getAnnotation(CustomDateFormat.class).format());
                        fieldValue = date.format(formatter);
                    }
                    else {
                        fieldValue = ((LocalDate) aField.get(object)).toString();
                    }
                }
                else
                {
                    fieldValue = (String) aField.get(object);
                }

                jsonStrings.put(fieldName, fieldValue);
            }

        return jsonStrings;
    }

    public String getJsonSerializationString(Object object) throws IllegalAccessException {
        String elements = getJsonSerializationMap(object).entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .map(entry -> "\""+entry.getKey() +"\""+":"+"\""+ entry.getValue()+"\"")
                .collect(Collectors.joining(", "));
        return "{"+elements+"}";
    }

    public Object fromJson(String jsonString, Class<?> aClass) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        Object result = aClass.newInstance();
        String[] jsonElements = jsonString.substring(1, jsonString.length()-2).split(", ");
        String name;
        String value;
        for (String jsonElement: jsonElements) {
            name = jsonElement.substring(1, jsonElement.indexOf(":")-1);
            value = jsonElement.substring(jsonElement.indexOf(":")+1, jsonElement.length()-1);
            Field field = aClass.getField(name);
            field.setAccessible(true);
            if(field.getClass().equals(LocalDate.class)) {
                if (field.isAnnotationPresent(CustomDateFormat.class)){

                }
            }
            else
            {
                field.set(result, value);
            }
        }
        return null;
    }
}
