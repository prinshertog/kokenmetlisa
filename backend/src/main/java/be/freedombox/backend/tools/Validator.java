package be.freedombox.backend.tools;

public class Validator {
    public static String initCap(String input) {
        return input.toLowerCase().substring(0, 1).toUpperCase() + input.toLowerCase().substring(1);
    }
}
