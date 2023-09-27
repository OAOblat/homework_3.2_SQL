package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    private static Faker faker = new Faker(new Locale("en"));

    public static AuthInfo getAuthInfoDemo1() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getAuthInfoDemo2() {
        return new AuthInfo("petya", "123qwerty");
    }

    private static String generateRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomLogin() {
        return generateRandomLogin();
    }

    public static String getPasswordForRandomLogin() {
        return "$2a$10$t/ok0touExVRogjk4svquOe2SEFhclzqoT3WDyUTBYhysmGMeZzBm";
    }

    public static String getID() {
        return "3";
    }

    public static AuthInfo getRandomUser() {
        return SQLHelper.addNewUser();
    }

    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(faker.numerify("######"));
    }

    @Value
    public static class VerificationCode {
        String code;
    }
}
