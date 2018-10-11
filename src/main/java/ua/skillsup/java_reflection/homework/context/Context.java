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
    private final String separator = ", ";
    private final String divider = ":";

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
                .map(entry -> "\""+entry.getKey() +"\""+divider+"\""+ entry.getValue()+"\"")
                .collect(Collectors.joining(separator));
        return "{"+elements+"}";
    }


    private Map<String, String> getJsonSerialisationMap(String jsonString){
        Map<String, String> jsonMap = new HashMap<>();

        for (String item: jsonString.substring(1, jsonString.length()-1).split(separator)) {
            jsonMap.put(item.substring(1, item.indexOf(divider)-1), item.substring(item.indexOf(divider)+2, item.length()-1));
        }
        return jsonMap;
    }

    public Object getObjectByJsonString(String jsonString, Class<?> aClass) throws IllegalAccessException, InstantiationException {
        Map<String, String> jsonMap = getJsonSerialisationMap(jsonString);

        Object object = aClass.newInstance();

        String fieldName;
        for (Field aField: aClass.getDeclaredFields()) {
            aField.setAccessible(true);
            fieldName = aField.getName();
            if (aField.isAnnotationPresent(JsonValue.class)) {
                fieldName = aField.getAnnotation(JsonValue.class).name();
            }

            if(aField.getType().equals(LocalDate.class) ) {
                if (aField.isAnnotationPresent(CustomDateFormat.class)){
                     DateTimeFormatter formatter = DateTimeFormatter.ofPattern(aField.getAnnotation(CustomDateFormat.class).format());
                     aField.set(object, LocalDate.parse(jsonMap.get(fieldName), formatter));
                }
                else{
                    aField.set(object, LocalDate.parse(jsonMap.get(fieldName)));
                }
            }
            else {
                aField.set(object, jsonMap.get(fieldName));
            }
        }

        return object;
    }
}
