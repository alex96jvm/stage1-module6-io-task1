package com.epam.mjc.io;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileReader {

    private static final String NAME_KEY = "name";
    private static final String AGE_KEY = "age";
    private static final String EMAIL_KEY = "email";
    private static final String PHONE_KEY = "phone";
    private static final Profile defaultProfile = new Profile("Unknown", 0, "unknown@example.com", 0L);

    public Profile getDataFromFile(File file) {

        Map<String, String> profileMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String fileLine;
            while ((fileLine = reader.readLine()) != null) {
                String[] keyValue = fileLine.split(":", 2);
                if (keyValue.length == 2) {
                    profileMap.put(keyValue[0].trim().toLowerCase(), keyValue[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла " + file.getName());
            e.printStackTrace();
            return defaultProfile;
        }

        try {
            String name = profileMap.get(NAME_KEY);
            Integer age = Integer.valueOf(profileMap.get(AGE_KEY));
            String email = profileMap.get(EMAIL_KEY);
            Long phone = Long.valueOf(profileMap.get(PHONE_KEY));
            return new Profile(name, age, email, phone);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка при преобразовании данных из файла " + file.getName());
            e.printStackTrace();
            return defaultProfile;
        }
    }
}
